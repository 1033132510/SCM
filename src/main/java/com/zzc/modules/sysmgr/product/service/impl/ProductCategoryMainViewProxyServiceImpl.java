package com.zzc.modules.sysmgr.product.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.zzc.common.page.PageForShow;
import com.zzc.common.security.service.UserUtil;
import com.zzc.core.dao.BaseDao;
import com.zzc.core.exceptions.BizException;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.sysmgr.product.dao.ProductCategoryMainViewProxyDao;
import com.zzc.modules.sysmgr.product.dao.ProductSKUViewDao;
import com.zzc.modules.sysmgr.product.entity.Category;
import com.zzc.modules.sysmgr.product.entity.ProductCategoryMainViewProxy;
import com.zzc.modules.sysmgr.product.service.BrandService;
import com.zzc.modules.sysmgr.product.service.CategoryChargeService;
import com.zzc.modules.sysmgr.product.service.CategoryService;
import com.zzc.modules.sysmgr.product.service.ProductCategoryMainViewProxyService;
import com.zzc.modules.sysmgr.product.web.vo.CategoryChargeVO;
import com.zzc.modules.sysmgr.product.web.vo.CategoryVO;
import com.zzc.modules.supply.vo.BrandVO;
import com.zzc.modules.sysmgr.user.base.service.BaseUserService;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierUser;
import com.zzc.modules.sysmgr.user.supplier.service.SupplierUserService;

/**
 * 
 * @author apple
 *
 */
@Service("productCategoryMainViewProxyService")
public class ProductCategoryMainViewProxyServiceImpl extends BaseCrudServiceImpl<ProductCategoryMainViewProxy>
		implements ProductCategoryMainViewProxyService {

	private final static String ERROR_MSG_LEVEL = "类别对应的级别有误";

	@Autowired
	@Qualifier("productCategoryMainViewProxyDao")
	private ProductCategoryMainViewProxyDao productCategoryMainViewDao;
	
	@SuppressWarnings("unused")
	private ProductSKUViewDao productSKUViewDao;

	@Autowired
	private CategoryService categoryService;
	
	@Resource(name = "categoryChargeService")
	private CategoryChargeService categoryChargeService;
	
	@Resource(name = "brandService")
	private BrandService brandService;
	
	@Autowired
	private SupplierUserService supplierUserService;
	
	@Autowired
	private BaseUserService baseUserService;
	
	@Autowired
	public ProductCategoryMainViewProxyServiceImpl(BaseDao<ProductCategoryMainViewProxy> productSKUViewDao) {
		super(productSKUViewDao);
		this.productSKUViewDao = (ProductSKUViewDao) productSKUViewDao;
	}

	/**
	 * 商品维护得到商品列表
	 */
	@Override
	public Page<ProductCategoryMainViewProxy> findByProductNameAndCateId(String productName, String cateId,
			Integer status, Integer pageSize, Integer curPage) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (status != null) {
			map.put("AND_EQ_status", status);
		}
		if (StringUtils.isNotBlank(cateId)) {
			Category category = categoryService.findByPK(cateId);
			Integer level = category.getLevel();
			if (level == 1) {
				map.put("AND_EQ_productCategoryMainView.cateIdGrand", cateId);
			} else if (level == 2) {
				map.put("AND_EQ_productCategoryMainView.cateIdParent", cateId);
			} else if (level == 3) {
				map.put("AND_EQ_productCategoryMainView.cateId", cateId);
			} else {
				throw new BizException(ERROR_MSG_LEVEL);
			}
		}
		if (StringUtils.isNotBlank(productName)) {
			map.put("AND_LIKE_productCategoryMainView.productName", productName);
		}
		return findByParams(ProductCategoryMainViewProxy.class, map, curPage, pageSize, "DESC", "createTime");
	}

	@Override
	public Page<ProductCategoryMainViewProxy> findBySupplierOrgId(String supplierOrgId, Integer status,
			Integer pageSize, Integer curPage) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(supplierOrgId)) {
			map.put("AND_EQ_productCategoryMainView.supplierOrgId", supplierOrgId);
		}
		if (status != null) {
			map.put("AND_EQ_status", status);
		}
		return findByParams(ProductCategoryMainViewProxy.class, map, curPage, pageSize, "DESC", "createTime");
	}

	
	/**
	 * findByProductNameAndCateId  参考  
	 * 
	 *  增加一个关于
	 */
	@Override
	public PageForShow<ProductCategoryMainViewProxy> searchForProductManager(String employeeId, 
			String productName, String brandName, String cateId, Integer status, Integer pageSize, Integer curPage) {
		// 商品品类权限控制
		List<CategoryChargeVO> categoryChargeVOList = categoryChargeService.getCategoryChargeByEmployeeId(employeeId);
		// 如果该员工负责的品类为空，将直接返回null
		if(categoryChargeVOList != null && categoryChargeVOList.size() > 0) {
			Category category = null;
			if(StringUtils.isNoneBlank(cateId)) {
				category = categoryService.findByPK(cateId);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productName", productName);
			map.put("brandName", brandName);
			List<ProductCategoryMainViewProxy> list = 
					productCategoryMainViewDao.findListByParamsForManager(map, category, status, categoryChargeVOList, curPage, pageSize);
			long count = productCategoryMainViewDao.countByParamsForManager(map, category, status, categoryChargeVOList);
			PageForShow<ProductCategoryMainViewProxy> page = PageForShow.newInstanceFromSpringPage(list, curPage, count);
			return page;
		} else {
			return PageForShow.newInstanceFromSpringPage(new ArrayList<ProductCategoryMainViewProxy>(), curPage, 0l);
		}
	}
	
	@Override
	public PageForShow<Map<String, Object>> searchForSupplyProductManager(
			String employeeId, String productName, String brandName,
			String cateId, Integer status, Integer pageSize, Integer curPage) {
		List<String> brandNames = getBrandNameList(status),
				cateIds = getCategoryIdList(status);
		if(brandNames != null && brandNames.size() > 0 && cateIds != null && cateIds.size() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cateId", cateId);
			map.put("productName", productName);
			map.put("brandName", brandName);
			map.put("brandNames", brandNames);
			map.put("cateIds", cateIds);
			List<Object[]> list = 
					productCategoryMainViewDao.findListByParamsForSupplier(map, status, curPage, pageSize);
			long count = productCategoryMainViewDao.countByParamsForSupplier(map, status);
			List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
			for(Object[] objs : list) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("productId", objs[0]);
				m.put("productCode", objs[1]);
				m.put("productName", objs[2]);
				m.put("price", objs[3]);
				m.put("brandZHName", objs[4]);
				m.put("modifiedTime", objs[5]);
				m.put("cateId", objs[6]);
				m.put("parentCateId", objs[7]);
				mapList.add(m);
			}
			PageForShow<Map<String, Object>> page = PageForShow.newInstanceFromSpringPage(mapList, curPage, count);
			return page;
		} else {
			return PageForShow.newInstanceFromSpringPage(new ArrayList<Map<String, Object>>(), curPage, 0l);
		}
	}
	
	private String getCurrentOrgId() {
		String id = UserUtil.getUserFromSession().getCurrentUserId();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("AND_EQ_id", id);
		List<SupplierUser> supplierUsers = supplierUserService.findAll(map, SupplierUser.class);
		if(supplierUsers != null && supplierUsers.size() > 0) {
			SupplierUser supplierUser = supplierUsers.get(0);
			return supplierUser.getSupplierOrg().getId();
		} else {
			return null;
		}
	}
	
	private List<ProductCategoryMainViewProxy> getProductViewList(Integer status) {
		String orgId = getCurrentOrgId();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("AND_EQ_productCategoryMainView.supplierOrgId", orgId);
		param.put("AND_EQ_status", status);
		List<ProductCategoryMainViewProxy> productViewList = findAll(param, ProductCategoryMainViewProxy.class);
		return productViewList;
	}
	
	/**
	 * 通过当前员工orgId获取对应的品类列表
	 *
	 * @param status
	 * @return
	 */
	public List<CategoryVO> getCategoryList(Integer status) {
		List<ProductCategoryMainViewProxy> productViewList = getProductViewList(status);
		List<CategoryVO> categoryVOList = new ArrayList<CategoryVO>();
		if (productViewList != null && productViewList.size() > 0) {
			List<String> cateIds = new ArrayList<String>();
			Map<String, Object> map = new HashMap<String, Object>();
			for (ProductCategoryMainViewProxy view : productViewList) {
				String cateId = view.getProductCategoryMainView().getCateIdParent();
				if (!map.containsKey(cateId)) {
					map.put(cateId, null);
					cateIds.add(cateId);
				}
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("AND_IN_id", cateIds);
			List<Category> categoryList = categoryService.findAll(param, Category.class);
			if (categoryList != null && categoryList.size() > 0) {
				for (Category category : categoryList) {
					CategoryVO vo = new CategoryVO();
					vo.setCateName(category.getCateName());
					vo.setId(category.getId());
					categoryVOList.add(vo);
				}
			}
		}
		return categoryVOList;
	}
	
	private List<String> getCategoryIdList(Integer status) {
		List<ProductCategoryMainViewProxy> productViewList = getProductViewList(status);
		List<String> categoryIdList = new ArrayList<String>();
		if (productViewList != null && productViewList.size() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (ProductCategoryMainViewProxy view : productViewList) {
				String cateId = view.getProductCategoryMainView().getCateIdParent();
				if (!map.containsKey(cateId)) {
					map.put(cateId, null);
					categoryIdList.add(cateId);
				}
			}
		}
		return categoryIdList;
	}

	/**
	 * 通过当前员工orgId获取对应的品牌列表
	 *
	 * @param status
	 * @return
	 */
	public List<BrandVO> getBrandList(Integer status) {
		List<ProductCategoryMainViewProxy> productViewList = getProductViewList(status);
		List<BrandVO> brandVOList = new ArrayList<BrandVO>();
		if (productViewList != null && productViewList.size() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (ProductCategoryMainViewProxy view : productViewList) {
				String brandName = view.getProductCategoryMainView().getBrandZHName();
				if (!map.containsKey(brandName)) {
					map.put(brandName, null);
					BrandVO vo = new BrandVO();
					vo.setBrandZHName(brandName);
					brandVOList.add(vo);
				}
			}
		}
		return brandVOList;
	}
	
	public List<String> getBrandNameList(Integer status) {
		List<ProductCategoryMainViewProxy> productViewList = getProductViewList(status);
		List<String> brandNameList = new ArrayList<String>();
		if (productViewList != null && productViewList.size() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (ProductCategoryMainViewProxy view : productViewList) {
				String brandName = view.getProductCategoryMainView().getBrandZHName();
				if (!map.containsKey(brandName)) {
					map.put(brandName, null);
					brandNameList.add(brandName);
				}
			}
		}
		return brandNameList;
	}

}