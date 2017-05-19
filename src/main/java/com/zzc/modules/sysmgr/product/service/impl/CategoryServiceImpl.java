package com.zzc.modules.sysmgr.product.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zzc.common.security.util.UserUtil;
import com.zzc.common.zTree.ZTreeNode;
import com.zzc.core.dao.BaseDao;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.exceptions.BizException;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.core.utils.JsonUtils;
import com.zzc.modules.sysmgr.common.entity.SequenceEntity;
import com.zzc.modules.sysmgr.common.service.SequenceEntityService;
import com.zzc.modules.sysmgr.common.util.NumberSequenceUtil;
import com.zzc.modules.sysmgr.product.dao.CategoryChargeDao;
import com.zzc.modules.sysmgr.product.dao.CategoryDao;
import com.zzc.modules.sysmgr.product.entity.Category;
import com.zzc.modules.sysmgr.product.entity.CategoryCharge;
import com.zzc.modules.sysmgr.product.entity.CategoryItem;
import com.zzc.modules.sysmgr.product.entity.CategoryTreeViewProxy;
import com.zzc.modules.sysmgr.product.service.CategoryItemService;
import com.zzc.modules.sysmgr.product.service.CategoryService;
import com.zzc.modules.sysmgr.product.service.CategoryTreeViewProxyService;
import com.zzc.modules.sysmgr.product.service.ProductSearchViewProxyService;
import com.zzc.modules.sysmgr.product.web.vo.CategoryViewVO;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.employee.service.EmployeeService;
import com.zzc.modules.sysmgr.user.supplier.service.SupplierUserService;
import com.zzc.modules.sysmgr.util.CategoryCaCheUtil;

@Service("categoryService")
public class CategoryServiceImpl extends BaseCrudServiceImpl<Category> implements CategoryService {

	private CategoryDao productCategoryDao;

	@Autowired
	private SequenceEntityService sequenceEntityService;

	@Autowired
	private CategoryItemService categoryItemService;

	@Autowired
	private CategoryTreeViewProxyService categoryTreeViewProxyService;

	@Autowired
	private ProductSearchViewProxyService productSearchViewProxyService;
	
	@Autowired
	private CategoryChargeDao categoryChargeDao;
	
	@Resource(name = "employeeService")
	private EmployeeService employeeService;
	
	@Autowired
	private SupplierUserService supplierUserService;

	private final static String BASE_CATE_URL = "/sysmgr/category";

	@Autowired
	public CategoryServiceImpl(BaseDao<Category> productCategoryDao) {
		super(productCategoryDao);
		this.productCategoryDao = (CategoryDao) productCategoryDao;
	}

	@Override
	public Map<Category, List<Category>> cateDecidePlaneInit(String categoryId) {
		Map<Category, List<Category>> map = new HashMap<Category, List<Category>>();
		Category categoryInfo = findByPK(categoryId);
		if (categoryInfo.getStatus() == CommonStatusEnum.无效.getValue()) {
			return null;
		} else {
			List<Category> categoryInfoChildes = categoryInfo.getChildCategorys();
			for (Category categoryInfochild : categoryInfoChildes) {
				List<Category> categoryInfoGrandchild = categoryInfochild.getChildCategorys();
				map.put(categoryInfochild, categoryInfoGrandchild);
			}
		}
		return map;
	}

	@Override
	public Category getCategoryInfoWithCategoryItems(String cateId) {
		Category category = findByPK(cateId);
		List<CategoryItem> items = categoryItemService.findCategoryItemByCateId(cateId, CommonStatusEnum.有效.getValue(), "createTime", "DESC");
		category.setProductCategoryItemKey(items);
		return category;
	}

	@Override
	public List<Category> getProductCategoryTree(String parentCategoryId, Integer level, Integer status) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (parentCategoryId != null) {
			params.put("AND_EQ_parentCategory.id", parentCategoryId);
		}
		if (status != null) {
			params.put("AND_EQ_status", status);
		}
		if (level != null) {
			params.put("AND_EQ_level", level);
		}
		return findAll(params, Category.class, "ASC", "createTime");
	}

	@Override
	public List<Category> getProductCategoryListByParentIdAndEmployeeId(String employeeId, String parentCategoryId, Integer level, Integer status) {
		List<Category> categoryList = new ArrayList<Category>();
		if (3 == level) {
			categoryList = getProductCategoryTree(parentCategoryId, level, status);
		} else {
			// 一、二级品类需要权限
			categoryList = productCategoryDao.getCategoryListByParentCateIdAndEmployeeId(employeeId, parentCategoryId, status, level);
		}
		return categoryList;
	}

	@Override
	public Long validateRepeatCateName(String cateName, Integer status, String cateId) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (cateName != null) {
			params.put("AND_EQ_cateName", cateName);
		}
		if (status != null) {
			params.put("AND_EQ_status", status);
		}
		if (cateId != null) {
			params.put("AND_NEQ_id", cateId);
		}
		return findAllCount(params, Category.class);
	}

	@Override
	public List<String> getThirdLevelCate(String cateId) {
		Category cate = findByPK(cateId);
		List<CategoryTreeViewProxy> list = new ArrayList<CategoryTreeViewProxy>();
		List<String> cateIds = new ArrayList<String>();
		if (cate.getLevel() == 3) {
			list = categoryTreeViewProxyService.findCategoryTreeViewProxyByCateId(null, null, cateId);
		}
		if (cate.getLevel() == 2) {
			list = categoryTreeViewProxyService.findCategoryTreeViewProxyByCateId(null, cateId, null);
		}
		if (cate.getLevel() == 1) {
			list = categoryTreeViewProxyService.findCategoryTreeViewProxyByCateId(cateId, null, null);
		}
		for (CategoryTreeViewProxy proxy : list) {
			if (proxy != null && proxy.getCategoryTreeView() != null && proxy.getCategoryTreeView().getLev3Id() != null) {
				cateIds.add(proxy.getCategoryTreeView().getLev3Id());
			}
		}
		return cateIds;
	}

	// 更新或者保存类别
	@Override
	@Transactional
	public ZTreeNode createOrUpdate(Category productCategory) {
		List<CategoryItem> items = productCategory.getProductCategoryItemKeys();
		// 数据校验
		for (CategoryItem ci : items) {
			ci.setItemsSources(ItemResourcesDeal(ci.getItemsSources()));
			// 不允许为空
			if (ci.getAllowedNotNull() == 1) {
				// 不允许自定义
				if (ci.getAllowedCustom() == 0) {
					if (ci.getItemsSources().split(",").length <= 0) {
						throw new BizException("如果不允许自定义并且必填，那么必须定义数据源,否则不符合输入规则");
					}
				}
			}
		}
		if (StringUtils.isBlank(productCategory.getId())) {
			return saveCategory(productCategory);
		} else {
			CategoryCaCheUtil.setDirdy(productCategory.getId());
			return updateCategory(productCategory);
		}
	}

	@Override
	public List<Category> getAllChildCate(Category category) {
		List<Category> cateList = new ArrayList<Category>();
		for (Category c : category.getChildCategorys()) {
			cateList.add(c);
			cateList.addAll(getAllChildCate(c));
		}
		return cateList;
	}

	/**
	 * 判断某个类别下是否有商品
	 */
	@Override
	public boolean exitsProductByCateId(String cateId) {
		Long count = productSearchViewProxyService.getViewCountByCateId(cateId);
		if (count != null && count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean exitsProductByCateId(String cateId, Integer level) {
		Long count = productSearchViewProxyService.getViewCountByCateId(cateId, level);
		if (count != null && count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public CategoryViewVO getCategoryInfoSimple(String cateId) {
		Category category = findByPK(cateId);
		List<CategoryItem> items = categoryItemService.findCategoryItemByCateId(cateId, CommonStatusEnum.有效.getValue(), "defaultShowNumber", "ASC");
		CategoryViewVO categoryViewVO = new CategoryViewVO();
		categoryViewVO.setCategory(category);
		categoryViewVO.setCategoryItems(items);
		return categoryViewVO;
	}

	@Override
	public ZTreeNode getCategoryInfo(String cateId) {
		Category category = findByPK(cateId);
		List<CategoryItem> items = categoryItemService.findCategoryItemByCateId(cateId, CommonStatusEnum.有效.getValue(), "createTime", "ASC");
		CategoryViewVO categoryViewVO = new CategoryViewVO();
		categoryViewVO.setCategory(category);
		categoryViewVO.setCategoryItems(items);
		return geTreeNodesFromCategoryViewVO(categoryViewVO, true);
	}

	@Override
	public List<ZTreeNode> getProductCategoryTreeJson(String employeeId, String parentCategoryId, Integer level, Integer status) {
		List<Category> list = null;
		if (level == 1 || level == 2) {
			// 一、二级品类需要权限
			list = productCategoryDao.getCategoryListByParentCateIdAndEmployeeId(employeeId, parentCategoryId, status, level);
		} else {
			list = getProductCategoryTree(parentCategoryId, level, status);
		}
		List<CategoryViewVO> vos = new ArrayList<CategoryViewVO>();
		if (list != null) {
			for (Category c : list) {
				CategoryViewVO vo = new CategoryViewVO();
				List<CategoryItem> items = categoryItemService.findCategoryItemByCateId(c.getId(), CommonStatusEnum.有效.getValue(), "createTime", "ASC");
                vo.setCategoryItems(items);
				vo.setCategory(c);
				vos.add(vo);
			}
		}
		return geTreeNodesFromItemKey(vos, false);
	}

	private void updateCategoryItemForSameCode(CategoryItem itemPage) {
		CategoryItem cateItemDb = categoryItemService.findByPK(itemPage.getId());
		// 判断名字是否被修改过
		if (!cateItemDb.getItemName().equals(itemPage.getItemName())) {
			List<CategoryItem> codeSameItems = categoryItemService.findCategoryItemByCode(cateItemDb.getItemCode());
			for (CategoryItem codeSameItem : codeSameItems) {
				if (codeSameItem.getId() != cateItemDb.getId()) {
					codeSameItem.setItemName(itemPage.getItemName());
					categoryItemService.update(codeSameItem);
				}
			}
		}
	}

	/**
	 * 保存类别
	 * 
	 * @param productCategory
	 * @return
	 */
	private ZTreeNode saveCategory(Category productCategory) {
		Category parentCatgory = findByPK(productCategory.getParentCategory().getId());
		productCategory.setLevel(parentCatgory.getLevel().intValue() + 1);
		String categoryName = productCategory.getCateName();
		Long cateForNames = validateRepeatCateName(categoryName, null, null);
		if (cateForNames > 0) {
			throw new BizException("the same cateName");
		}
		Integer level = productCategory.getLevel();
		if (level >= 4) {
			throw new BizException("类别等级最多为三级");
		}
		// 获取编码
		productCategory.setCategoryCode(getCategoryCode(productCategory));
		if (productCategory.getProductCategoryItemKeys() != null) {
			for (CategoryItem itemKey : productCategory.getProductCategoryItemKeys()) {
				if (StringUtils.isBlank(itemKey.getItemCode())) {
					itemKey.setItemCode(UUID.randomUUID().toString());
				}
				itemKey.setCategory(productCategory);
				itemKey.setStatus(CommonStatusEnum.有效.getValue());
				itemKey.setItemsSources(ItemResourcesDeal(itemKey.getItemsSources()));
			}
		}
		Category category = create(productCategory);
		CategoryViewVO categoryViewVO = new CategoryViewVO();
		List<CategoryItem> items = categoryItemService.findCategoryItemByCateId(category.getId(), CommonStatusEnum.有效.getValue(), "createTime", "ASC");
		categoryViewVO.setCategory(category);
		categoryViewVO.setCategoryItems(items);
		
		saveCategoryCharge(category, level);
		
		return geTreeNodesFromCategoryViewVO(categoryViewVO, true);
	}
	
	// 如果是一级或二级类别并且品类状态是有效，需要添加相应的权限
	private void saveCategoryCharge(Category category, Integer level) {
		if((level == 1 || level == 2) && category.getStatus() == CommonStatusEnum.有效.getValue()) {
			String employeeId = UserUtil.getUserFromSession().getCurrentUserId();
			List<CategoryCharge> categoryChargeList = categoryChargeDao.getCategoryChargeByEmployeeIdAndCateId(employeeId, category.getId());
			if(categoryChargeList.size() == 0) {
				CategoryCharge categoryCharge = new CategoryCharge();
				categoryCharge.setCategory(category);
				Employee employee = employeeService.findByPK(employeeId);
				categoryCharge.setEmployee(employee);
				categoryChargeDao.save(categoryCharge);
			}
		}
	}

	/**
	 * 更新品类时，如果status=有效，给操作人添加权限；否则，删除操作人对该品类的权限以及子品类的权限
	 * @param category
	 */
	private void saveOrDeleteCategoryCharge(Category category) {
		Integer level = category.getLevel();
		if(level == 1 || level == 2) {
			Integer status = category.getStatus(), validStatus = CommonStatusEnum.有效.getValue();
			if(status == validStatus) {
				saveCategoryCharge(category, level);
			} else {
				String cateId = category.getId();
				String employeeId = UserUtil.getUserFromSession().getCurrentUserId();
				categoryChargeDao.deleteCategoryChargeByEmployeeIdAndCateId(employeeId, cateId);
				if(level == 1) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("AND_EQ_parentCategory.id", cateId);
					List<Category> categoryList = findAll(map, Category.class);
					if(categoryList != null && categoryList.size() > 0) {
						List<String> cateIds = new ArrayList<String>();
						for(Category c : categoryList) {
							cateIds.add(c.getId());
						}
						categoryChargeDao.deleteCategoryChargeByEmployeeIdAndCateIds(employeeId, cateIds);
					}
				}
			}
		}
	}
	
	/**
	 * 更新类别
	 * 
	 * @param productCategory
	 * @return
	 */
	private ZTreeNode updateCategory(Category productCategory) {
		if (exitsProductByCateId(productCategory.getId())) {
			throw new BizException("类别下已经挂在商品不允许修改");
		}
		Long cateForNames = validateRepeatCateName(productCategory.getCateName(), null, productCategory.getId());
		if (cateForNames > 0) {
			throw new BizException("类别名称一律不能重复");
		}
		// 获取数据库类别
		Category dbProductCategory = getCategoryInfoWithCategoryItems(productCategory.getId());

		for (CategoryItem itemPage : productCategory.getProductCategoryItemKeys()) {
			// 从前台传来的item 不为空 那么对这个属性的修改同步到所有的子类中去
			if (StringUtils.isNotBlank(itemPage.getId())) {
				updateCategoryItemForSameCode(itemPage);
			} else {
				inserCascadeCategoryItem(dbProductCategory, itemPage);
			}
		}

		delCascadeCategoryItem(dbProductCategory, productCategory);
		dbProductCategory.setCateName(productCategory.getCateName());
		dbProductCategory.setProductCategoryItemKey(productCategory.getProductCategoryItemKeys());
		for (CategoryItem item : productCategory.getProductCategoryItemKeys()) {
			item.setStatus(CommonStatusEnum.有效.getValue());
			item.setCategory(dbProductCategory);
		}
		dbProductCategory.setStatus(productCategory.getStatus());
		Category category = update(dbProductCategory);

		CategoryViewVO categoryViewVO = new CategoryViewVO();
		List<CategoryItem> items = categoryItemService.findCategoryItemByCateId(category.getId(), CommonStatusEnum.有效.getValue(), "createTime", "ASC");
		categoryViewVO.setCategory(category);
		categoryViewVO.setCategoryItems(items);
		
		saveOrDeleteCategoryCharge(category);
		
		return geTreeNodesFromCategoryViewVO(categoryViewVO, true);
	}

	private void inserCascadeCategoryItem(Category category, CategoryItem itemPage) {
		// 证明这个ITEM 是新增的 需要把这个新增的添加到所有的子类里面去
		List<Category> cateListForAdd = getAllChildCate(category);
		itemPage.setItemCode(UUID.randomUUID().toString());
		for (Category c : cateListForAdd) {
			CategoryItem addForChild = new CategoryItem();
			addForChild.setAllowedCateFilter(itemPage.getAllowedCateFilter());
			addForChild.setAllowedCustom(itemPage.getAllowedCustom());
			addForChild.setAllowedMultiSelect(itemPage.getAllowedMultiSelect());
			addForChild.setAllowedNotNull(itemPage.getAllowedNotNull());
			addForChild.setCanBeChanged(itemPage.getCanBeChanged());
			addForChild.setDefaultShowNumber(itemPage.getDefaultShowNumber());
			addForChild.setItemCode(itemPage.getItemCode());
			addForChild.setItemName(itemPage.getItemName());
			if (StringUtils.isNotBlank(itemPage.getItemsSources())) {
				addForChild.setItemsSources(ItemResourcesDeal(itemPage.getItemsSources()));
			} else {
				addForChild.setItemsSources("");
			}
			addForChild.setStatus(CommonStatusEnum.有效.getValue());
			addForChild.setCanBeChanged(0);
			addForChild.setCategory(c);
			categoryItemService.create(addForChild);
		}
	}

	private void delCascadeCategoryItem(Category dbProductCategory, Category productCategory) {
		for (CategoryItem itemDb : dbProductCategory.getProductCategoryItemKeys()) {
			itemDb.setStatus(CommonStatusEnum.无效.getValue());
			boolean flag = true;
			for (CategoryItem itemPage : productCategory.getProductCategoryItemKeys()) {
				if (itemDb.getId().equals(itemPage.getId())) {
					flag = false;
				}
			}
			if (flag) {
				// 证明这个ID被删除了
				List<CategoryItem> items = categoryItemService.findCategoryItemByCode(itemDb.getItemCode());
				for (CategoryItem i : items) {
					i.setStatus(CommonStatusEnum.无效.getValue());
					categoryItemService.update(i);
				}
			}
		}
	}

	@Override
	public List<Category> getCategoryListByLevel(int level, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("AND_EQ_level", level);
		if (status != null) {
			map.put("AND_EQ_status", status);
		}
		return findAll(map, Category.class);
	}

	private List<ZTreeNode> geTreeNodesFromItemKey(List<CategoryViewVO> categorys, boolean flag) {
		List<ZTreeNode> nodes = new ArrayList<ZTreeNode>();
		if (categorys != null) {
			for (CategoryViewVO categoryViewVO : categorys) {
				ZTreeNode zTreeNode = geTreeNodesFromCategoryViewVO(categoryViewVO, flag);
				nodes.add(zTreeNode);
			}
		}
		return nodes;
	}

	private ZTreeNode geTreeNodesFromCategoryViewVO(CategoryViewVO categoryViewVO, boolean flag) {
		Category category = categoryViewVO.getCategory();
		ZTreeNode zTreeNode = new ZTreeNode();
		zTreeNode.setChildURL(BASE_CATE_URL + "/tree?status=1&parentCategoryId=" + category.getId() + "&level=" + (category.getLevel() + 1));
		zTreeNode.setName(category.getCateName());
		zTreeNode.setId(category.getId());
		// 点击的时候加载请求
		zTreeNode.setInfoURL(BASE_CATE_URL + "/getCategoryInfo?cateId=" + category.getId() + "&status=1");
		zTreeNode.setLevel(category.getLevel());
		if (category.getChildCategorys() == null || category.getChildCategorys().size() <= 0) {
			zTreeNode.setExpandBtn(false);
		}
		if (category.getLevel() == 3) {
			zTreeNode.setLast(true);
			zTreeNode.setAddBtn(false);
			zTreeNode.setExpandBtn(false);
		}
		
		Category parentCategory = category.getParentCategory();
		if (parentCategory != null) {
			zTreeNode.setpId(parentCategory.getId());
		}
		zTreeNode.getParam().put("productCategoryItemKeys", categoryViewVO.getCategoryItems());
		zTreeNode.getParam().put("parentCategory", parentCategory);
		zTreeNode.getParam().put("category", category);
		boolean hasProduct = false;
		if (flag == true) {
			if (category.getLevel() == 0) {
				hasProduct = true;
			} else {
				hasProduct = exitsProductByCateId(category.getId(), category.getLevel());
			}
		}
		zTreeNode.getParam().put("hasProduct", hasProduct);
		return zTreeNode;
	}

	private String ItemResourcesDeal(String msg) {
		return msg.replace("，", ",").trim();
	}

	/**
	 * 类别编码
	 * 
	 * @param category
	 * @return
	 */
	private String getCategoryCode(Category category) {
		Integer level = category.getLevel();
		String sequenceName = "level_" + level;
		Integer code = 0;
		SequenceEntity entity = sequenceEntityService.findSequenceEntityBySequenceName(sequenceName);
		// 该level 对应的sequence 不存在 保存一个
		if (entity == null) {
			SequenceEntity sequenceEntity = new SequenceEntity();
			sequenceEntity.setSequenceName(sequenceName);
			sequenceEntity.setStatus(1);
			sequenceEntity.setSequenceNumber(0);
			sequenceEntityService.create(sequenceEntity);
		} else {
			code = entity.getSequenceNumber() + 1;
			entity.setSequenceNumber(entity.getSequenceNumber() + 1);
			sequenceEntityService.update(entity);
		}
		StringBuilder codeBuilder = new StringBuilder();

		Category parentCategory = findByPK(category.getParentCategory().getId());
		if (parentCategory != null) {
			if (parentCategory.getCategoryCode() != null) {
				codeBuilder.append(parentCategory.getCategoryCode());
			}
		}
		while (parentCategory.getParentCategory() != null) {
			parentCategory = parentCategory.getParentCategory();
			if (parentCategory.getCategoryCode() != null) {
				codeBuilder.insert(0, parentCategory.getCategoryCode());
			}
		}
		switch (level) {
		case 1:
			return codeBuilder.append(NumberSequenceUtil.appendPrefixChar(code, 2, '0')).toString();
		case 2:
			return codeBuilder.append(NumberSequenceUtil.appendPrefixChar(code, 2, '0')).toString();
		default:
			return codeBuilder.append(NumberSequenceUtil.appendPrefixChar(code, 3, '0')).toString();
		}
	}

	@Override
	public String getShopCategoryInfo(String cateId) {
		// TODO Auto-generated method stub
		Map<String, Category> cateMap = new HashMap<String, Category>();
		Category thirdCate = new Category();
		Category secondCate = new Category();
		Category firstCate = new Category();
		Category selfCate = productCategoryDao.findOne(cateId);
		if (selfCate.getLevel().intValue() == 3) {
			firstCate = productCategoryDao.findOne(selfCate.getParentCategory().getParentCategory().getId());
			secondCate = selfCate.getParentCategory();
			thirdCate = selfCate;
		} else if (selfCate.getLevel().intValue() == 2) {
			firstCate = selfCate.getParentCategory();
			secondCate = selfCate;
		} else if (selfCate.getLevel().intValue() == 1) {
			firstCate = selfCate;
		}
		cateMap.put("thirdCate", thirdCate);
		cateMap.put("secondCate", secondCate);
		cateMap.put("firstCate", firstCate);
		return JsonUtils.toJson(cateMap);
	}
	
}