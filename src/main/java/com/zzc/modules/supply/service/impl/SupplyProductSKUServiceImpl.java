package com.zzc.modules.supply.service.impl;

import com.zzc.common.util.ImagePathUtil;
import com.zzc.core.dao.BaseDao;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.exceptions.BizException;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.core.utils.JsonUtils;
import com.zzc.modules.supply.dao.SupplyProductSKUDao;
import com.zzc.modules.supply.entity.AuditBill;
import com.zzc.modules.supply.entity.AuditRecord;
import com.zzc.modules.supply.entity.SysProductSKU;
import com.zzc.modules.supply.service.AuditBillService;
import com.zzc.modules.supply.service.AuditRecordService;
import com.zzc.modules.supply.service.SupplyProductSKUService;
import com.zzc.modules.supply.vo.GroundingProductVO;
import com.zzc.modules.supply.vo.SupplyProductSKUVO;
import com.zzc.modules.sysmgr.constant.SystemConstants;
import com.zzc.modules.sysmgr.enums.AuditBillOperationTypeEnum;
import com.zzc.modules.sysmgr.enums.AuditRecordTypeEnum;
import com.zzc.modules.sysmgr.enums.ImageTypeEnum;
import com.zzc.modules.sysmgr.enums.SupplyAuditBillStatusEnum;
import com.zzc.modules.sysmgr.file.entity.Image;
import com.zzc.modules.sysmgr.file.service.ImageService;
import com.zzc.modules.sysmgr.product.entity.*;
import com.zzc.modules.sysmgr.product.enums.ItemTypeEnum;
import com.zzc.modules.sysmgr.product.service.*;
import com.zzc.modules.sysmgr.product.service.vo.ProductSKUBussinessVO;
import com.zzc.modules.sysmgr.product.web.vo.CategoryChargeVO;
import com.zzc.modules.sysmgr.product.web.vo.ProductPropertiesModel;
import com.zzc.modules.sysmgr.user.base.entity.Role;
import com.zzc.modules.sysmgr.user.base.service.RoleService;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.employee.service.EmployeeService;
import com.zzc.modules.sysmgr.user.supplier.service.SupplierOrgService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 供应商产品
 * Created by chenjiahai on 16/1/19.
 */
@Service
public class SupplyProductSKUServiceImpl extends BaseCrudServiceImpl<SysProductSKU> implements SupplyProductSKUService {

	private static Logger logger = LoggerFactory.getLogger(SupplyProductSKUServiceImpl.class);

	private SupplyProductSKUDao supplyProductSKUDao;

	@Autowired
	private ProductSKUService productSKUService;

	@Autowired
	private AuditBillService auditBillService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CategoryItemService categoryItemService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private SupplierOrgService supplierOrgService;

	@Autowired
	private PriceKindService priceKindService;

	@Autowired
	private ProductPropertiesService productPropertiesService;

	@Autowired
	private ProductCategoryService productcategoryservice;

	@Autowired
	private ProductPriceService productPriceService;

	@Autowired
	private CategoryChargeService categoryChargeService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private AuditRecordService auditRecordService;


	@Autowired
	public SupplyProductSKUServiceImpl(BaseDao<SysProductSKU> baseDao) {
		super(baseDao);
		this.supplyProductSKUDao = (SupplyProductSKUDao) baseDao;
	}

	/**
	 * 创建修改
	 * @param productDetail
	 * @return
	 */
	@Transactional
	@Override
	public Map<String, Object> createOrUpdateProductSKUModel(String productDetail) {
		SupplyProductSKUVO vo = JsonUtils.toObject(productDetail,
				SupplyProductSKUVO.class);
		Map<String, Object> result = checkCategoryChargeBySecondLevelCateId(vo.getSecondLevelCateId());
		SysProductSKU sysProductSKU = null;
		if((boolean) result.get("success")) {
			if (vo.getProductId() == null) {
				sysProductSKU = createSupplyProduct(vo);
				AuditBill auditBill = createAuditBill(vo, sysProductSKU);
				createAuditRecord(sysProductSKU, auditBill, vo);
			} else {
				sysProductSKU = updateSupplyProduct(vo);
				auditBillService.updateAuditBillStatusToMangerAuditing(sysProductSKU.getId());
			}
		}
		return result;

	}

	/**
	 * 创建审核档案
	 * @param sysProductSKU
	 * @param auditBill
	 * @param vo
	 */
	private void createAuditRecord(SysProductSKU sysProductSKU, AuditBill auditBill, SupplyProductSKUVO vo) {
		AuditRecord auditRecord = new AuditRecord();
		auditRecord.setAuditBill(auditBill);
		auditRecord.setSysProductSKU(sysProductSKU);
		auditRecord.setStatus(CommonStatusEnum.有效.getValue());
		auditRecord.setHasTax(vo.getHasTax());
		auditRecord.setHasTransportation(vo.getHasTransportation());
		auditRecord.setType(AuditRecordTypeEnum.SUPPLY_USER_toADMINISTRATOR.getCode());
		auditRecordService.create(auditRecord);
	}

	/**
	 * 创建供应商商品
	 * @param vo
	 * @return
	 */
	private SysProductSKU createSupplyProduct(SupplyProductSKUVO vo) {
		SysProductSKU sysProductSKU = new SysProductSKU();
		sysProductSKU.setBrand(brandService.findByPK(vo.getBrandId()));
		sysProductSKU.setStatus(vo.getStatus());
		sysProductSKU.setProductName(vo.getProductName());
		String productCode = productSKUService.getProductCode(vo.getThirdLevelCateId(), vo.getSupplierOrgId());
		vo.setProductCode(productCode);
		sysProductSKU.setProductCode(productCode);
		sysProductSKU.setSupplierOrg(supplierOrgService.findByPK(vo.getSupplierOrgId()));
		sysProductSKU.setSecondLevelCateId(vo.getSecondLevelCateId());
		sysProductSKU = supplyProductSKUDao.save(sysProductSKU);
		vo.setProductId(sysProductSKU.getId());
		createProductProperties(vo, sysProductSKU);
		setImagesOfSupplyProductInfos(vo);
		sysProductSKU.setProductInfos(JsonUtils.toJson(vo));
		bindAndUnbindRelationBetweenImageAndEntity(vo, sysProductSKU);
		return sysProductSKU;
	}

	/**
	 * 添加图片信息
	 * @param vo
	 */
	private void setImagesOfSupplyProductInfos(SupplyProductSKUVO vo) {
		List<Image> images = new ArrayList<>(vo.getImageIds().size());
		List<Image> productImages = new ArrayList<>();
		List<Image> productDescImages = new ArrayList<>();
		for (String imageId : vo.getImageIds()) {
			Image image = imageService.findByPK(imageId);
			if (ImageTypeEnum.PRODUCT_IMAGE.getValue() == image.getRelationType()) {
				productImages.add(ImagePathUtil.setImagePath(ImageTypeEnum.PRODUCT_IMAGE, image));
			} else if (ImageTypeEnum.PRODUCT_DESC.getValue() == image.getRelationType()) {
				productDescImages.add(ImagePathUtil.setImagePath(ImageTypeEnum.PRODUCT_DESC, image));
			}
		}
		images.addAll(productImages);
		images.addAll(productDescImages);
		vo.setImages(images);
	}

	/**
	 * 添加商品属性
	 * @param vo
	 * @param sysProductSKU
	 */
	private void createProductProperties(SupplyProductSKUVO vo, SysProductSKU sysProductSKU) {
		for (ProductPropertiesModel productCategoryItemValueModel : vo
				.getProductPropertiesModels()) {
			ProductProperties categoryPropertie = new ProductProperties();
			categoryPropertie.setProductCategoryId(vo.getThirdLevelCateId());
			categoryPropertie.setProductSKUId(sysProductSKU.getId());
			categoryPropertie.setStatus(CommonStatusEnum.有效.getValue());
			categoryPropertie.setValue(productCategoryItemValueModel.getValue().trim());
			categoryPropertie.setItemType(productCategoryItemValueModel.getItemType());

			if (productCategoryItemValueModel.getItemType() == ItemTypeEnum.category.getValue()) {
				CategoryItem itemKey = categoryItemService.findByPK(productCategoryItemValueModel.getProductCategoryItemKeyId());
				categoryPropertie.setProductCategoryItemKey(itemKey);
			} else {
				categoryPropertie.setProductPropertiesName(productCategoryItemValueModel.getProductPropertiesName());
			}
			categoryPropertie.setOrderNumber(productCategoryItemValueModel.getOrder());
			productPropertiesService.create(categoryPropertie);
		}
	}

	/**
	 * 供应商修改商品
	 * @param vo
	 * @return
	 */
	private SysProductSKU updateSupplyProduct(SupplyProductSKUVO vo) {
		SysProductSKU sysProductSKU = supplyProductSKUDao.findOne(vo.getProductId());
		productPropertiesService.disableProductProperties(sysProductSKU.getId());
		imageService.deleteByRelationId(sysProductSKU.getId());
		AuditBill auditBill = auditBillService.findAuditBillBySysProductId(sysProductSKU.getId());
		SysProductSKU newSysProductSKU = copySysProductSKU(sysProductSKU);
		sysProductSKU.setStatus(CommonStatusEnum.无效.getValue());
		update(sysProductSKU);
		newSysProductSKU.setStandard(vo.getStandard());
		newSysProductSKU = create(newSysProductSKU);
		auditBill.setSysProductSKU(newSysProductSKU);
		auditBillService.update(auditBill);
		newSysProductSKU.setStatus(vo.getStatus());
		newSysProductSKU.setProductName(vo.getProductName());
		for (ProductPropertiesModel productCategoryItemValueModel : vo
				.getProductPropertiesModels()) {
			String productPropertiesId = productCategoryItemValueModel.getId();
			if (StringUtils.isBlank(productPropertiesId)) {
				ProductProperties productProperties = new ProductProperties();
				productProperties.setProductCategoryId(vo.getThirdLevelCateId());
				productProperties.setProductSKUId(newSysProductSKU.getId());
				productProperties.setStatus(CommonStatusEnum.有效.getValue());
				productProperties.setValue(productCategoryItemValueModel.getValue());
				productProperties.setOrderNumber(productCategoryItemValueModel.getOrder());
				productProperties.setItemType(productCategoryItemValueModel.getItemType());
				if (productCategoryItemValueModel.getItemType() == ItemTypeEnum.category.getValue()) {
					CategoryItem itemKey = categoryItemService.findByPK(productCategoryItemValueModel.getProductCategoryItemKeyId());
					productProperties.setProductCategoryItemKey(itemKey);
				} else {
					productProperties.setProductPropertiesName(productCategoryItemValueModel.getProductPropertiesName());
				}
				productPropertiesService.create(productProperties);
			} else {
				ProductProperties productProperties = productPropertiesService.findByPK(productPropertiesId);
				productProperties.setStatus(CommonStatusEnum.有效.getValue());
				productProperties.setValue(productCategoryItemValueModel.getValue().trim());
				productProperties.setProductPropertiesName(productCategoryItemValueModel.getProductPropertiesName());
				productProperties.setOrderNumber(productCategoryItemValueModel.getOrder());
				productPropertiesService.update(productProperties);
			}
		}
		vo.setProductId(newSysProductSKU.getId());
		setImagesOfSupplyProductInfos(vo);
		newSysProductSKU.setProductInfos(JsonUtils.toJson(vo));
		newSysProductSKU = update(newSysProductSKU);
		bindAndUnbindRelationBetweenImageAndEntity(vo, newSysProductSKU);
		return newSysProductSKU;
	}

	/**
	 * 拷贝
	 * @param sysProductSKU
	 * @return
	 */
	private SysProductSKU copySysProductSKU(SysProductSKU sysProductSKU) {
		SysProductSKU newSysProductSKU = new SysProductSKU();
		try {
			BeanUtils.copyProperties(newSysProductSKU, sysProductSKU);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		newSysProductSKU.setId(null);
		return newSysProductSKU;
	}

	/**
	 * 供应商添加商品
	 * @param productSKU
	 * @param vo
	 * @return
	 */
	private SysProductSKU addSupplyProduct(ProductSKU productSKU, GroundingProductVO vo) {
		SysProductSKU sysProductSKU = new SysProductSKU();
		sysProductSKU.setStatus(vo.getStatus());
		sysProductSKU.setBrand(productSKU.getBrand());
		sysProductSKU.setStandard(vo.getStandard());
		sysProductSKU.setProductCode(vo.getProductCode());
		sysProductSKU.setProductName(vo.getProductName());
		sysProductSKU.setProductSKUId(productSKU.getId());
		sysProductSKU.setSupplierOrg(productSKU.getSupplierOrg());
		sysProductSKU.setSecondLevelCateId(vo.getSecondLevelCateId());
		Category categorye = categoryService.findByPK(vo.getThirdLevelCateId());
		if (categorye.getLevel() != 3) {
			throw new BizException("查看商品详细信息必须类别等级为3");
		}
		sysProductSKU = create(sysProductSKU);
		for (ProductPropertiesModel productCategoryItemValueModel : vo
				.getProductPropertiesModels()) {
			ProductProperties productProperties = new ProductProperties();
			productProperties.setProductCategoryId(vo.getThirdLevelCateId());
			productProperties.setProductSKUId(sysProductSKU.getId());
			productProperties.setStatus(CommonStatusEnum.有效.getValue());
			productProperties.setValue(productCategoryItemValueModel.getValue());
			productProperties.setOrderNumber(productCategoryItemValueModel.getOrder());
			productProperties.setItemType(productCategoryItemValueModel.getItemType());
			if (productCategoryItemValueModel.getItemType() == ItemTypeEnum.category.getValue()) {
				CategoryItem itemKey = categoryItemService.findByPK(productCategoryItemValueModel.getProductCategoryItemKeyId());
				productProperties.setProductCategoryItemKey(itemKey);
			} else {
				productProperties.setProductPropertiesName(productCategoryItemValueModel.getProductPropertiesName());
			}
			productPropertiesService.create(productProperties);
		}

		List<String> imageIds = vo.getImageIds();
		String sysProductSKUId = sysProductSKU.getId();
		for (String imageId : imageIds) {
			Image image = imageService.findByPK(imageId);
			String relationId = image.getRelationId();
			if (StringUtils.isNotBlank(relationId)) {
				Image newImage = new Image();
				newImage.setAliasName(image.getAliasName());
				newImage.setOriginalName(image.getOriginalName());
				newImage.setPath(image.getPath());
				newImage.setRelationId(sysProductSKUId);
				newImage.setRelationType(image.getRelationType());
				newImage.setSortOrder(image.getSortOrder());
				newImage.setStatus(image.getStatus());
				imageService.save(newImage);
			} else {
				image.setRelationId(sysProductSKUId);
				imageService.update(image);
			}
		}
		setImagesOfSupplyProductInfos(vo);
		// TODO 修改vo保存的productId
		//vo.setProductId(sysProductSKUId);
		sysProductSKU.setProductInfos(JsonUtils.toJson(vo));
		return sysProductSKU;
	}

	/**
	 * 获得商品详情
	 * @param id
	 * @return
	 */
	@Override
	public ProductSKUBussinessVO getProductDetail(String id) {
		SysProductSKU sysProductSKU = findByPK(id);
		if (sysProductSKU == null) {
			return null;
		}
		return createProductBusinessVO(sysProductSKU);
	}

	/**
	 * 修改商品信息并创建审核档案
	 * @param productDetailAndComment
	 */
	@Override
	@Transactional
	public void updateProductAndCreateAuditRecord(String productDetailAndComment) {
		GroundingProductVO vo = JsonUtils.toObject(productDetailAndComment, GroundingProductVO.class);
		SysProductSKU sysProductSKU = updateSupplyProduct(vo);
		AuditBill auditBill = auditBillService.updateAuditBillStatusToMangerAuditing(sysProductSKU.getId());
		// 新增审批记录的含税, 含运费是审批记录最新一条的
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_auditBill.id", auditBill.getId());
		params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		List<AuditRecord> auditRecords = auditRecordService.findAll(params, AuditRecord.class, "DESC", "createTime");
		AuditRecord lastestAuditRecord = auditRecords.get(0);
		auditRecordService.createSupplyRecord(vo.getComment(), auditBill, sysProductSKU, lastestAuditRecord);
	}

	/**
	 * 根据id获得商品详情
	 * @param productSKUId
	 * @return
	 */
	@Override
	public ProductSKUBussinessVO getProductDetailByProductId(String productSKUId) {
		// 获取SYS_PRODUCT_SKU表中最新的一条数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("AND_EQ_productSKUId", productSKUId);
		List<SysProductSKU> sysProductSKUs = findAll(map, SysProductSKU.class, "DESC", "createTime");

		if (sysProductSKUs == null || sysProductSKUs.size() == 0) {
			return null;
		}
		return createProductBusinessVO(sysProductSKUs.get(0));
	}

	/**
	 *
	 * @param sysProductSKU
	 * @return
	 */
	private ProductSKUBussinessVO createProductBusinessVO(SysProductSKU sysProductSKU) {
		SupplyProductSKUVO supplyProductSKUVO = JsonUtils.toObject(sysProductSKU.getProductInfos(), SupplyProductSKUVO.class);
		ProductSKUBussinessVO productSKUBussinessVO = new ProductSKUBussinessVO();
		productSKUBussinessVO.setProductCode(supplyProductSKUVO.getProductCode());
		productSKUBussinessVO.setHasTax(supplyProductSKUVO.getHasTax());
		productSKUBussinessVO.setHasTransportation(supplyProductSKUVO.getHasTransportation());
		productSKUBussinessVO.setStatus(supplyProductSKUVO.getStatus());
		productSKUBussinessVO.setBrand(sysProductSKU.getBrand());
		productSKUBussinessVO.setCategory(categoryService.findByPK(supplyProductSKUVO.getThirdLevelCateId()));
		productSKUBussinessVO.setFeeLogistics(supplyProductSKUVO.getFeeLogistics());
		productSKUBussinessVO.setFeeRemark(supplyProductSKUVO.getFeeRemark());
		productSKUBussinessVO.setProductCategoryItemValues(productPropertiesService.getProductCategoryItemValueByProductAndCategory(sysProductSKU.getId(),
				supplyProductSKUVO.getThirdLevelCateId(), CommonStatusEnum.有效.getValue()));
		productSKUBussinessVO.setProductDesc(supplyProductSKUVO.getProductDesc());
		productSKUBussinessVO.setUnit(supplyProductSKUVO.getUnit());
		productSKUBussinessVO.setSupplierOrg(sysProductSKU.getSupplierOrg());
		productSKUBussinessVO.setProductName(supplyProductSKUVO.getProductName());
		productSKUBussinessVO.setProductNumber(supplyProductSKUVO.getProductNumber());
		productSKUBussinessVO.setProductPrices(createProductPrice(supplyProductSKUVO));
		productSKUBussinessVO.setMinOrderCount(supplyProductSKUVO.getMinOrderCount());
		Map<String, Object> customData = new HashMap<>();
		customData.put("model", supplyProductSKUVO);
		customData.put("id", sysProductSKU.getId());
		customData.put("standardPrice", sysProductSKU.getStandard());
		productSKUBussinessVO.setCustomData(customData);
		return productSKUBussinessVO;
	}

	/**
	 * 创建商品价格
	 * @param supplyProductSKUVO
	 * @return
	 */
	private List<ProductPrice> createProductPrice(SupplyProductSKUVO supplyProductSKUVO) {
		List<ProductPrice> productPrices = new ArrayList<>();
		BigDecimal cost = supplyProductSKUVO.getCost();
		BigDecimal standard = supplyProductSKUVO.getStandard();
		BigDecimal recommend = supplyProductSKUVO.getRecommend();
		ProductPrice costPrice = new ProductPrice();
		costPrice.setStatus(CommonStatusEnum.有效.getValue());
		costPrice.setActuallyPrice(cost);
		costPrice.setPriceKindModel(priceKindService.findByPK("2"));
		productPrices.add(costPrice);

		ProductPrice standardPrice = new ProductPrice();
		standardPrice.setStatus(CommonStatusEnum.有效.getValue());
		standardPrice.setActuallyPrice(standard);
		standardPrice.setPriceKindModel(priceKindService.findByPK("1"));
		productPrices.add(standardPrice);

		ProductPrice recommendPrice = new ProductPrice();
		recommendPrice.setStatus(CommonStatusEnum.有效.getValue());
		recommendPrice.setActuallyPrice(recommend);
		recommendPrice.setPriceKindModel(priceKindService.findByPK("6"));
		productPrices.add(recommendPrice);
		return productPrices;
	}

	private void bindAndUnbindRelationBetweenImageAndEntity(SupplyProductSKUVO vo, SysProductSKU sysProductSKU) {
		imageService.deleteByRelationId(sysProductSKU.getId());
		imageService.buildRelationBetweenImageAndEntity(vo.getImageIds(), sysProductSKU.getId());
	}

	private AuditBill createAuditBill(SupplyProductSKUVO vo, SysProductSKU sysProductSKU) {
		AuditBill auditBill = new AuditBill();
		auditBill.setAuditStatus(SupplyAuditBillStatusEnum.MANAGER_AUDITING.getCode());
		auditBill.setOperationType(AuditBillOperationTypeEnum.ADD.getValue());
		List<CategoryChargeVO> categoryChargeVOs = categoryChargeService.getCategoryChargeByCateId(vo.getSecondLevelCateId());
		for (CategoryChargeVO categoryChargeVO : categoryChargeVOs) {
			Employee employee = employeeService.findByPK(categoryChargeVO.getEmployeeId());
			if (isCategoryCharge(employee)) {
				auditBill.setAdminEmployee(employee);
			} else {
				auditBill.setDirectorEmployee(employee);
			}
		}
		auditBill.setSysProductSKU(sysProductSKU);
		auditBill.setStatus(CommonStatusEnum.有效.getValue());
		return auditBillService.create(auditBill);
	}

	private boolean isCategoryCharge(Employee employee) {
		boolean isCategoryCharge = false;
		List<Role> roles = roleService.findRolesByUser(employee);
		for (Role role : roles) {
			if (SystemConstants.CATEGORY_ADMINISTRATOR.equals(role.getRoleCode())) {
				isCategoryCharge = true;
			}
		}
		return isCategoryCharge;
	}

	@Transactional
	@Override
	public Map<String, Object> disableProduct(ProductSKU productSKU, String comment, BigDecimal costPrice, BigDecimal recommendedPrice, BigDecimal standardPrice, String cateId, Integer hasTax, Integer hasTransportation) {
		Category category = categoryService.findByPK(cateId);
		if (category.getLevel() != 3) {
			throw new BizException("查看商品详细信息必须类别等级为3");
		}
		String secondLevelCateId = category.getParentCategory().getId();
		Map<String, Object> result = checkCategoryChargeBySecondLevelCateId(secondLevelCateId);
		if(!(boolean) result.get("success")) {
			return result;
		}
		SysProductSKU sysProductSKU = new SysProductSKU();
		sysProductSKU.setStatus(CommonStatusEnum.有效.getValue());
		sysProductSKU.setBrand(productSKU.getBrand());
		sysProductSKU.setProductCode(productSKU.getProductCode());
		sysProductSKU.setProductName(productSKU.getProductName());
		sysProductSKU.setProductSKUId(productSKU.getId());
		sysProductSKU.setSupplierOrg(productSKU.getSupplierOrg());
		sysProductSKU.setStandard(standardPrice);
		sysProductSKU.setSecondLevelCateId(secondLevelCateId);

		sysProductSKU = create(sysProductSKU);
		List<Image> images = imageService.findByRelationId(productSKU.getId(), CommonStatusEnum.有效.getValue());
		List<Image> newImages = new ArrayList<Image>();
		List<String> imageIds = new ArrayList<String>();
		for (Image image : images) {
			Image newImage = new Image();
			newImage.setAliasName(image.getAliasName());
			newImage.setOriginalName(image.getOriginalName());
			newImage.setPath(image.getPath());
			newImage.setRelationId(sysProductSKU.getId());
			newImage.setRelationType(image.getRelationType());
			newImage.setSortOrder(image.getSortOrder());
			newImage.setStatus(image.getStatus());
			newImage = imageService.save(newImage);
			ImagePathUtil.setImagePath(ImageTypeEnum.getImageTypeEnumByImageTypeValue(image.getRelationType()), newImage);
			newImages.add(newImage);
			imageIds.add(newImage.getId());
		}
		GroundingProductVO vo = buildGroundingProductVO(productSKU, comment, cateId, sysProductSKU.getId());
		vo.setThirdLevelCateId(cateId);
		vo.setSecondLevelCateId(category.getParentCategory().getId());
		vo.setImageIds(imageIds);
		vo.setImages(newImages);
		vo.setType(AuditBillOperationTypeEnum.OFF_SHELVE.getValue());
		vo.setStandard(standardPrice);
		vo.setHasTax(hasTax);
		vo.setHasTransportation(hasTransportation);
		sysProductSKU.setProductInfos(JsonUtils.toJson(vo));
		update(sysProductSKU);
		createAuditBillAndRecord(sysProductSKU, productSKU, vo);
		return result;
	}

	public GroundingProductVO buildGroundingProductVO(ProductSKU productSKU, String comment, String thirdLevelCateId, String supplyProductSKUId) {
		GroundingProductVO vo = new GroundingProductVO();
		vo.setProductName(productSKU.getProductName());
		vo.setComment(comment);
		vo.setProductSKUId(productSKU.getId());
		vo.setBrandId(productSKU.getBrand().getId());
		vo.setFeeLogistics(productSKU.getFeeLogistics());
		vo.setFeeRemark(productSKU.getFeeRemark());
		vo.setHasTax(productSKU.getHasTax());
		vo.setHasTransportation(productSKU.getHasTransportation());
		vo.setMinOrderCount(productSKU.getMinOrderCount());
		vo.setUnit(productSKU.getUnit());
		vo.setStatus(productSKU.getStatus());
		vo.setProductId(supplyProductSKUId);
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		params.put("AND_EQ_productSKU.id", productSKU.getId());
		vo.setProductCode(productSKU.getProductCode());
		vo.setProductDesc(productSKU.getProductDesc());
		vo.setProductNumber(productSKU.getProductNumber());
		vo.setSupplierOrgId(productSKU.getSupplierOrg().getId());
		List<ProductProperties> productProperties = productPropertiesService.getProductCategoryItemValueByProductAndCategory(productSKU.getId(), thirdLevelCateId, CommonStatusEnum.有效.getValue());
		// TODO 抽取,去除重复?
		for (ProductProperties temp : productProperties) {
			ProductProperties newProductProperties = new ProductProperties();
			try {
				BeanUtils.copyProperties(newProductProperties, temp);
				newProductProperties.setId(null);
				newProductProperties.setProductSKUId(supplyProductSKUId);
				productPropertiesService.create(newProductProperties);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<ProductPropertiesModel> productPropertiesModels = new ArrayList<>(productProperties.size());
		for (ProductProperties productPropertie : productProperties) {
			ProductPropertiesModel productPropertiesModel = new ProductPropertiesModel();
			productPropertiesModel.setStatus(productPropertie.getStatus());
			productPropertiesModel.setItemType(productPropertie.getItemType());
			productPropertiesModel.setOrder(productPropertie.getOrderNumber());
			CategoryItem productCategoryItemKey = productPropertie.getProductCategoryItemKey();
			if (productCategoryItemKey != null) {
				productPropertiesModel.setProductCategoryItemKeyId(productPropertie.getProductCategoryItemKey().getId());
			}
			productPropertiesModel.setProductPropertiesName(productPropertie.getProductPropertiesName());
			productPropertiesModel.setValue(productPropertie.getValue());
			productPropertiesModels.add(productPropertiesModel);
		}
		vo.setProductPropertiesModels(productPropertiesModels);
		Map<String, Object> productPriceParams = new HashMap<>();
		productPriceParams.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		productPriceParams.put("AND_EQ_productSKUId", productSKU.getId());
		List<ProductPrice> productPrices = productPriceService.findAll(productPriceParams, ProductPrice.class);
		for (ProductPrice productPrice : productPrices) {
			PriceKind priceKindModel = productPrice.getPriceKindModel();
			if (priceKindModel != null) {
				String id = priceKindModel.getId();
				if ("1".equals(id)) {
					vo.setStandard(productPrice.getActuallyPrice());
				} else if ("2".equals(id)) {
					vo.setCost(productPrice.getActuallyPrice());
				} else if ("6".equals(id)) {
					vo.setRecommend(productPrice.getActuallyPrice());
				}
			}
		}
		return vo;
	}

	/**
	 * 删除审核表及档案
	 * @param auditBillId
	 */
	@Override
	@Transactional
	public void deleteProductAndAuditBillAndRecord(String auditBillId) {
		Map<String, Object> auditBillParams = new HashMap<>();
		auditBillParams.put("AND_EQ_id", auditBillId);
		auditBillParams.put("AND_EQ_auditStatus", SupplyAuditBillStatusEnum.TO_ADJUSTED.getCode());
		List<AuditBill> auditBills = auditBillService.findAll(auditBillParams, AuditBill.class);
		AuditBill auditBill = auditBills.get(0);
		auditBill.setAuditStatus(SupplyAuditBillStatusEnum.DELETE.getCode());
		auditBill.setStatus(CommonStatusEnum.无效.getValue());
		auditBillService.update(auditBill);
		Map<String, Object> auditRecordParams = new HashMap<>();
		auditRecordParams.put("AND_EQ_auditBill.id", auditBillId);
		auditRecordParams.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		List<AuditRecord> auditRecords = auditRecordService.findAll(auditRecordParams, AuditRecord.class);
		for (AuditRecord auditRecord : auditRecords) {
			auditRecord.setStatus(CommonStatusEnum.无效.getValue());
			auditRecordService.update(auditRecord);
		}
		SysProductSKU sysProductSKU = auditBill.getSysProductSKU();
		sysProductSKU.setStatus(CommonStatusEnum.无效.getValue());
		update(sysProductSKU);
	}

	/**
	 * 添加一条商品修改记录和审核意向单
	 *
	 * @param sysProductSKU
	 * @param productSKU
	 * @param vo
	 */
	private void createAuditBillAndRecord(SysProductSKU sysProductSKU, ProductSKU productSKU, GroundingProductVO vo) {
		// ----------------------------------添加一条审核意向单 begin----------------------
		AuditBill auditBill = new AuditBill();
		// 设置品类管理员
		String secondLevelCategoryId = vo.getSecondLevelCateId();
		Employee categoryAdministrator = categoryChargeService.getCategoryChargeByCategoryIdAndRoleCode(SystemConstants.CATEGORY_ADMINISTRATOR, secondLevelCategoryId);
		auditBill.setAdminEmployee(categoryAdministrator);
		// 设置部类总监
		Employee categoryMajordomo = categoryChargeService.getCategoryChargeByCategoryIdAndRoleCode(SystemConstants.CATEGORY_MAJORDOMO, secondLevelCategoryId);
		auditBill.setDirectorEmployee(categoryMajordomo);
		auditBill.setStatus(CommonStatusEnum.有效.getValue());
		auditBill.setOperationType(vo.getType());
		auditBill.setAuditStatus(SupplyAuditBillStatusEnum.MANAGER_AUDITING.getCode());
		auditBill.setSysProductSKU(sysProductSKU);
		auditBillService.save(auditBill);
		// ----------------------------------添加一条审核意向单 end----------------------
		// ----------------------------------添加一条审批记录 begin----------------------
		AuditRecord auditRecord = new AuditRecord();
		auditRecord.setStandard(vo.getStandard());
		auditRecord.setType(AuditRecordTypeEnum.SUPPLY_USER_toADMINISTRATOR.getCode());
		auditRecord.setAuditBill(auditBill);
		auditRecord.setComment(vo.getComment());
		auditRecord.setHasTax(productSKU.getHasTax());
		auditRecord.setHasTransportation(productSKU.getHasTransportation());
		auditRecord.setStatus(CommonStatusEnum.有效.getValue());
		auditRecord.setSysProductSKU(sysProductSKU);
		auditRecordService.save(auditRecord);
		// ----------------------------------添加一条审批记录 end----------------------
	}

	@Transactional
	@Override
	public Map<String, Object> updateProductSKUModelForSupplier(String groundingProduct) {
		GroundingProductVO vo = JsonUtils.toObject(groundingProduct, GroundingProductVO.class);
		Category category = categoryService.findByPK(vo.getThirdLevelCateId());
		if (category.getLevel() != 3) {
			throw new BizException("查看商品详细信息必须类别等级为3");
		}
		String secondLevelCateId = category.getParentCategory().getId();
		Map<String, Object> result = checkCategoryChargeBySecondLevelCateId(secondLevelCateId);
		if((boolean) result.get("success")) {
			ProductSKU productSKU = productSKUService.findByPK(vo.getProductSKUId());
			SysProductSKU sysProductSKU = addSupplyProduct(productSKU, vo);
			createAuditBillAndRecord(sysProductSKU, productSKU, vo);
		}
		return result;
	}

	@Override
	public SysProductSKU getLatestSysProductByProductSKUId(String productSKUId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("AND_EQ_auditStatus", SupplyAuditBillStatusEnum.PASS.getCode());
		map.put("AND_EQ_sysProductSKU.productSKUId", productSKUId);
		List<AuditBill> auditBills = auditBillService.findAll(map, AuditBill.class, "desc", "createTime");
		if (auditBills == null || auditBills.size() == 0) {
			return null;
		} else {
			return auditBills.get(0).getSysProductSKU();
		}
	}
	
	private Map<String, Object> checkCategoryChargeBySecondLevelCateId(String cateId) {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean success = false;
		List<CategoryChargeVO> categoryChargeVOs = categoryChargeService.getCategoryChargeByCateId(cateId);
		for (CategoryChargeVO categoryChargeVO : categoryChargeVOs) {
			Employee employee = employeeService.findByPK(categoryChargeVO.getEmployeeId());
			if (isCategoryCharge(employee)) {
				success = true;
				break;
			}
		}
		if(!success) {
			result.put("msg", "该商品的二级品类没有对应的品类负责人");
		}
		result.put("success", success);
		return result;
	}

}