package com.zzc.modules.sysmgr.product.service.impl;

import com.zzc.common.page.PageForShow;
import com.zzc.common.security.service.UserUtil;
import com.zzc.common.util.ImagePathUtil;
import com.zzc.core.dao.BaseDao;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.exceptions.BizException;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.sysmgr.common.dao.SequenceEntityDao;
import com.zzc.modules.sysmgr.common.entity.SequenceEntity;
import com.zzc.modules.sysmgr.common.enums.SequenceEntityKeyEnum;
import com.zzc.modules.sysmgr.enums.ImageTypeEnum;
import com.zzc.modules.sysmgr.file.entity.Image;
import com.zzc.modules.sysmgr.file.service.ImageService;
import com.zzc.modules.sysmgr.product.dao.ProductSKUDao;
import com.zzc.modules.sysmgr.product.entity.*;
import com.zzc.modules.sysmgr.product.enums.ItemTypeEnum;
import com.zzc.modules.sysmgr.product.enums.ProductCateTypeEnum;
import com.zzc.modules.sysmgr.product.service.*;
import com.zzc.modules.sysmgr.product.service.vo.ProductPriceActionVO;
import com.zzc.modules.sysmgr.product.service.vo.ProductSKUBussinessVO;
import com.zzc.modules.sysmgr.product.web.vo.ProductPriceModel;
import com.zzc.modules.sysmgr.product.web.vo.ProductPropertiesModel;
import com.zzc.modules.sysmgr.product.web.vo.ProductSKUModel;
import com.zzc.modules.sysmgr.product.web.vo.ProductSearchVO;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierOrg;
import com.zzc.modules.sysmgr.user.supplier.service.SupplierOrgService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("productSKUService")
public class ProductSKUServiceImpl extends BaseCrudServiceImpl<ProductSKU>
		implements ProductSKUService {

	private static Logger logger = LoggerFactory
			.getLogger(ProductSKUServiceImpl.class);

	private ProductSKUDao productSKUDao;

	@Autowired
	private ImageService imageService;

	@Autowired
	private ProductPriceService productPriceService;

	@Autowired
	private ProductPropertiesService productPropertiesService;

	@Autowired
	private CategoryItemService categoryItemService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private SupplierOrgService supplierOrgService;

	@Autowired
	private PriceKindService priceKindService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private ProductSearchViewProxyService productSearchViewService;

	@Autowired
	private ProductCategoryMainViewProxyService productCategoryMainViewService;

	@Autowired
	private ProductPriceActionService productPriceActionService;

	@Autowired
	private SequenceEntityDao sequenceEntityDao;

	private final static Integer ORIGINAL_PRODUCT_CODE = 100999;

	@Autowired
	public ProductSKUServiceImpl(BaseDao<ProductSKU> productSKUDao) {
		super(productSKUDao);
		this.productSKUDao = (ProductSKUDao) productSKUDao;
	}

	/**
	 * 只能查询第三级的类别属性 根据第三级的类别加上产品ID 得到产品的详细信息
	 */
	@Override
	public ProductSKUBussinessVO getProductDetail(String cateId,
	                                              String productId) {
		ProductSKU sku = findByPK(productId);
		Category categorye = categoryService.findByPK(cateId);
		if (categorye.getLevel() != 3) {
			throw new BizException("查看商品详细信息必须类别等级为3");
		}
		if (categorye != null && categorye.getLevel() == 3) {
			ProductSKUBussinessVO bussinessVO = buildProductSKUBussinessVO(sku);
			bussinessVO.setCategory(categorye);
			bussinessVO.setProductCategoryItemValues(productPropertiesService
					.getProductCategoryItemValueByProductAndCategory(productId,
							cateId, CommonStatusEnum.有效.getValue()));
			bussinessVO.setProductPrices(productPriceService
					.getProductPriceByProductId(sku.getId(),
							CommonStatusEnum.有效.getValue(), null));
			setProductImages(bussinessVO, sku.getId(), null, null);
			return bussinessVO;
		} else {
			return null;
		}
	}

	/**
	 * 添加？？更新
	 */
	@Transactional
	@Override
	public void createOrUpdateProductSKUModel(ProductSKUModel model) {
		if (model.getProductId() == null
				|| model.getProductId().trim().equals("")) {
			createBussinessVO(model);
		} else {
			updateBussinessVO(model);
		}

	}

	@Override
	public PageForShow<ProductSKUBussinessVO> searchBySupplierOrg(
			String supplierOrgId, Integer status, Integer pageSize,
			Integer curPage) {
		List<ProductSKUBussinessVO> list = new ArrayList<ProductSKUBussinessVO>();
		Page<ProductCategoryMainViewProxy> page = productCategoryMainViewService
				.findBySupplierOrgId(supplierOrgId, status, pageSize, curPage);
		for (ProductCategoryMainViewProxy proxy : page.getContent()) {
			ProductSKUBussinessVO vo = getProductDetailShop(proxy
					.getProductCategoryMainView().getCateId(), proxy
					.getProductCategoryMainView().getProductId());
			list.add(vo);
		}
		PageForShow<ProductSKUBussinessVO> p = PageForShow
				.newInstanceFromSpringPage(list, curPage,
						page.getTotalElements());
		return p;
	}

	/**
	 * 商品维护得到商品列表
	 */
	@Override
	public PageForShow<ProductSKUBussinessVO> searchByCateOrName(String cateId,
	                                                             String productName, Integer status, Integer pageSize,
	                                                             Integer curPage) {
		List<ProductSKUBussinessVO> list = new ArrayList<ProductSKUBussinessVO>();
		Page<ProductCategoryMainViewProxy> page = productCategoryMainViewService
				.findByProductNameAndCateId(productName, cateId, status,
						pageSize, curPage);
		for (ProductCategoryMainViewProxy proxy : page.getContent()) {
			ProductSKUBussinessVO vo = getProductDetail(proxy
					.getProductCategoryMainView().getCateId(), proxy
					.getProductCategoryMainView().getProductId());
			list.add(vo);
		}
		PageForShow<ProductSKUBussinessVO> p = PageForShow
				.newInstanceFromSpringPage(list, curPage,
						page.getTotalElements());
		return p;
	}

	@Transactional
	@Override
	public PageForShow<ProductSKUBussinessVO> searchEx(ProductSearchVO v) {
		List<ProductSKUBussinessVO> list = new ArrayList<ProductSKUBussinessVO>();
		if (StringUtils.isNotBlank(v.getCateId())) {
			Category category = categoryService.findByPK(v.getCateId());
			v.setLevel(category.getLevel());
		}
		List<ProductSearchViewProxy> page = productSearchViewService
				.searchEx(v);
		for (ProductSearchViewProxy proxy : page) {
			ProductSKUBussinessVO vo = getProductDetailShop(proxy
					.getBrandCategoryView().getCateId(), proxy
					.getBrandCategoryView().getProductId());
			list.add(vo);
		}
		PageForShow<ProductSKUBussinessVO> p = PageForShow
				.newInstanceFromSpringPage(list, v.getCurPage(),
						productSearchViewService.searchExByCount(v));
		return p;
	}

	@Override
	public ProductSKUBussinessVO getProductDetailShop(String cateId,
	                                                  String productId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("AND_EQ_id", productId);
		ProductSKU sku = findByPK(productId);
		Category categorye = categoryService.findByPK(cateId);
		if (categorye.getLevel() != 3) {
			throw new BizException("查看商品详细信息必须类别等级为3");
		}
		if (categorye != null && categorye.getLevel() == 3) {
			ProductSKUBussinessVO bussinessVO = buildProductSKUBussinessVO(sku);
			bussinessVO.setCategory(categorye);
			setProductImages(bussinessVO, sku.getId(), sku.getBrand().getId(),
					sku.getSupplierOrg().getId());
			bussinessVO.setProductCategoryItemValues(productPropertiesService
					.getProductCategoryItemValueByProductAndCategory(productId,
							cateId, CommonStatusEnum.有效.getValue()));
			bussinessVO
					.setProductPrices(productPriceService.getProductPriceByProductId(
							sku.getId(), CommonStatusEnum.有效.getValue(),
							UserUtil.getPriceKindModelCodeListByCustomerLevel()));
			Map<String, Object> extraData = new HashMap<String, Object>();
			extraData.put("purchaserLevel", UserUtil.getUserFromSession()
					.getUserDefinedParams().get("purchaserLevel"));
			bussinessVO.setCustomData(extraData);
			return bussinessVO;
		} else {
			return null;
		}
	}

	private ProductSKU getProductSKUFromProductSKUModel(ProductSKUModel model) {
		ProductSKU productSKU = new ProductSKU();
		productSKU.setId(model.getProductId());
		productSKU.setBrand(brandService.findByPK(model.getBrandId()));
		productSKU.setFeeLogistics(model.getFeeLogistics());
		productSKU.setFeeRemark(model.getFeeRemark());
		productSKU.setProductDesc(model.getProductDesc());
		productSKU.setHasTax(model.getHasTax());
		productSKU.setUnit(model.getUnit());
		productSKU.setHasTransportation(model.getHasTransportation());
		productSKU.setProductCode(getProductCode(model.getCateId(),
				model.getSupplierOrgId()));
		productSKU.setProductName(model.getProductName());
		productSKU.setProductNumber(model.getProductNumber());
		productSKU.setStatus(model.getStatus());
		productSKU.setSupplierOrg(supplierOrgService.findByPK(model
				.getSupplierOrgId()));
		productSKU.setUnit(model.getUnit());
		productSKU.setMinOrderCount(model.getMinOrderCount());
		return productSKU;
	}

	private ProductSKU getProductSKUFromProductSKUModelForUpdate(
			ProductSKUModel model) {
		ProductSKU productSKU = findByPK(model.getProductId());
		productSKU.setProductDesc(model.getProductDesc());
		productSKU.setProductName(model.getProductName());
		productSKU.setProductNumber(model.getProductNumber());
		productSKU.setMinOrderCount(model.getMinOrderCount());
		productSKU.setStatus(model.getStatus());
		return productSKU;
	}

	private void buildRelationForProductWithPrice(ProductSKU sku,
	                                              ProductSKUModel model) {
		for (ProductPriceModel productPriceModel : model
				.getProductPriceModels()) {
			ProductPrice price = new ProductPrice();
			price.setPriceKindModel(priceKindService.findByPK(productPriceModel
					.getPriceKindId()));
			price.setActuallyPrice(productPriceModel.getActuallyPrice());
			price.setProductSKUId(sku.getId());
			price.setStatus(productPriceModel.getStatus());
			productPriceService.create(price);
		}
	}

	private void createBussinessVO(ProductSKUModel model) {
		ProductSKU productSKU = getProductSKUFromProductSKUModel(model);
		productSKU = create(productSKU);
		saveProductImages(productSKU.getId(), model);
		Category mainKindCategory = categoryService.findByPK(model.getCateId());
		if (mainKindCategory.getLevel() != 3) {
			throw new BizException("商品必须有对应的三级类别");
		} else {
			ProductCategory pc = new ProductCategory();
			pc.setCategory(mainKindCategory);
			pc.setType(ProductCateTypeEnum.mainKind.getValue());
			pc.setLevel(mainKindCategory.getLevel());
			pc.setProductSKU(productSKU);
			productCategoryService.create(pc);
			Category category = mainKindCategory.getParentCategory();
			while (category != null && category.getLevel() != 0) {
				ProductCategory productCategory = new ProductCategory();
				productCategory.setCategory(category);
				productCategory.setType(ProductCateTypeEnum.parent.getValue());
				productCategory.setLevel(category.getLevel());
				productCategory.setProductSKU(productSKU);
				productCategoryService.create(productCategory);
				category = category.getParentCategory();
			}
		}
		buildRelationForProductWithPrice(productSKU, model);
		saveProductPriceActionOfFirstTime(productSKU.getId(), mainKindCategory.getId(), model);
		for (ProductPropertiesModel productCategoryItemValueModel : model
				.getProductPropertiesModels()) {
			ProductProperties categoryPropertie = new ProductProperties();
			categoryPropertie.setProductCategoryId(model.getCateId());
			categoryPropertie.setProductSKUId(productSKU.getId());
			categoryPropertie.setStatus(CommonStatusEnum.有效.getValue());
			categoryPropertie.setValue(productCategoryItemValueModel.getValue()
					.trim());
			if (productCategoryItemValueModel.getItemType() == ItemTypeEnum.category
					.getValue()) {
				categoryPropertie.setItemType(ItemTypeEnum.category.getValue());
				CategoryItem itemKey = categoryItemService
						.findByPK(productCategoryItemValueModel
								.getProductCategoryItemKeyId());
				categoryPropertie.setProductCategoryItemKey(itemKey);
			} else {
				categoryPropertie.setItemType(ItemTypeEnum.product.getValue());
				categoryPropertie
						.setProductPropertiesName(productCategoryItemValueModel
								.getProductPropertiesName());
			}
			categoryPropertie.setOrderNumber(productCategoryItemValueModel
					.getOrder());
			productPropertiesService.create(categoryPropertie);
		}
		saveProductImages(productSKU.getId(), model);
	}

	/**
	 * 添加商品时,保存添加价格记录
	 *
	 * @param productSKUId
	 * @param productCateId
	 * @param model
	 */
	private void saveProductPriceActionOfFirstTime(String productSKUId, String productCateId,
	                                               ProductSKUModel model) {
		ProductPriceActionVO action = new ProductPriceActionVO();
		for (ProductPriceModel productPriceModel : model
				.getProductPriceModels()) {
			switch (productPriceModel.getPriceKindId()) {
				case "1":
					action.setStandard(String.valueOf(productPriceModel.getActuallyPrice()));
					break;
				case "2":
					action.setCost(String.valueOf(productPriceModel.getActuallyPrice()));
					break;
				case "3":
					action.setLevel1(String.valueOf(productPriceModel.getActuallyPrice()));
					break;
				case "4":
					action.setLevel2(String.valueOf(productPriceModel.getActuallyPrice()));
					break;
				case "5":
					action.setLevel3(String.valueOf(productPriceModel.getActuallyPrice()));
					break;
			}
		}
		action.setOperatorName(UserUtil.getUserFromSession().getDisplayUserName());
		action.setHasTax(model.getHasTax());
		action.setHasTransportation(model.getHasTransportation());
		productPriceActionService.saveProductPriceAction(productSKUId, productCateId, action);
	}

	/**
	 * 更新商品
	 *
	 * @param model
	 * @return
	 */
	private void updateBussinessVO(ProductSKUModel model) {
		ProductSKU productSKU = getProductSKUFromProductSKUModelForUpdate(model);
		update(productSKU);
		productPropertiesService.disableProductProperties(productSKU.getId());
		for (ProductPropertiesModel productCategoryItemValueModel : model
				.getProductPropertiesModels()) {
			String productPropertiesId = productCategoryItemValueModel.getId();
			if (productPropertiesId == null
					|| productPropertiesId.trim().equals("")) {
				ProductProperties productProperties = new ProductProperties();
				productProperties.setProductCategoryId(model.getCateId());
				productProperties.setProductSKUId(productSKU.getId());
				productProperties.setStatus(CommonStatusEnum.有效.getValue());
				productProperties.setValue(productCategoryItemValueModel
						.getValue());
				productProperties.setOrderNumber(productCategoryItemValueModel
						.getOrder());
				if (productCategoryItemValueModel.getItemType() == ItemTypeEnum.category
						.getValue()) {
					productProperties.setItemType(ItemTypeEnum.category
							.getValue());
					CategoryItem itemKey = categoryItemService
							.findByPK(productCategoryItemValueModel
									.getProductCategoryItemKeyId());
					productProperties.setProductCategoryItemKey(itemKey);
				} else {
					productProperties.setItemType(ItemTypeEnum.product
							.getValue());
					productProperties
							.setProductPropertiesName(productCategoryItemValueModel
									.getProductPropertiesName());
				}
				productPropertiesService.create(productProperties);
			} else {
				ProductProperties productProperties = productPropertiesService
						.findByPK(productPropertiesId);
				productProperties.setStatus(CommonStatusEnum.有效.getValue());
				productProperties.setValue(productCategoryItemValueModel
						.getValue().trim());
				productProperties
						.setProductPropertiesName(productCategoryItemValueModel
								.getProductPropertiesName());
				productProperties.setOrderNumber(productCategoryItemValueModel
						.getOrder());
				productPropertiesService.update(productProperties);
			}
		}
		saveProductImages(productSKU.getId(), model);
	}

	/**
	 * 获取产品编码
	 *
	 * @param productCategoryId
	 * @param supplierOrgId
	 * @return
	 */
	@Override
	public synchronized String getProductCode(String productCategoryId, String supplierOrgId) {
		String product_code = SequenceEntityKeyEnum.product_code.getText();
		SequenceEntity squenceEntity = sequenceEntityDao.getValueByKey(product_code);
		Integer sequenceNumber = null;
		if (squenceEntity != null) {
			sequenceNumber = squenceEntity.getSequenceNumber();
			squenceEntity.setSequenceNumber(++sequenceNumber);
			sequenceEntityDao.save(squenceEntity);
			// TODO
//			sequenceEntityDao.updateValue(product_code, ++sequenceNumber);
		} else {
			squenceEntity = new SequenceEntity();
			squenceEntity.setStatus(CommonStatusEnum.有效.getValue());
			squenceEntity.setSequenceName(product_code);
			sequenceNumber = ORIGINAL_PRODUCT_CODE;
			squenceEntity.setSequenceNumber(++sequenceNumber);
			sequenceEntityDao.save(squenceEntity);
		}
		return sequenceNumber + "";
	}

	/**
	 * transfer ProductSKU to ProductSKUBussinessVO
	 *
	 * @param sku
	 * @return
	 */
	private ProductSKUBussinessVO buildProductSKUBussinessVO(ProductSKU sku) {
		ProductSKUBussinessVO bussinessVO = new ProductSKUBussinessVO();
		bussinessVO.setBrand(sku.getBrand());
		bussinessVO.setProductDesc(sku.getProductDesc());
		bussinessVO.setStatus(sku.getStatus());
		bussinessVO.setFeeLogistics(sku.getFeeLogistics());
		bussinessVO.setFeeRemark(sku.getFeeRemark());
		bussinessVO.setProductCode(sku.getProductCode());
		bussinessVO.setProductId(sku.getId());
		bussinessVO.setProductName(sku.getProductName());
		bussinessVO.setProductNumber(sku.getProductNumber());
		bussinessVO.setSupplierOrg(sku.getSupplierOrg());
		bussinessVO.setUnit(sku.getUnit());
		bussinessVO.setMinOrderCount(sku.getMinOrderCount());
		bussinessVO.setHasTax(sku.getHasTax());
		bussinessVO.setHasTransportation(sku.getHasTransportation());
		return bussinessVO;
	}

	private void saveProductImages(String skuId, ProductSKUModel model) {
		if (model.getDelProductImageIds() != null) {
			imageService.destoryRelationBetweenImageAndEntity(model.getDelProductImageIds());
		}
		imageService.buildRelationBetweenImageAndEntity(model.getProductImageIds(),
				skuId);
	}

	@Transactional
	@Override
	public boolean updateProductStatus(String productId, Integer status) {
		ProductSKU productSKU = findByPK(productId);
		if (status == null) {
			status = CommonStatusEnum.有效.getValue();
		}
		productSKU.setStatus(status);
		update(productSKU);
		return true;
	}

	private void setProductImages(ProductSKUBussinessVO bussinessVO,
	                              String skuId, String brandId, String supplierOrgId) {
		List<Image> productImages = imageService
				.findByRelationIdAndRelationTypeAndStatusOrderByCreateTimeAsc(
						skuId, ImageTypeEnum.PRODUCT_IMAGE.getValue(),
						CommonStatusEnum.有效.getValue());
		ImagePathUtil.setImagePath(ImageTypeEnum.PRODUCT_IMAGE, productImages);
		bussinessVO.setProductImages(productImages);

		List<Image> productDescImages = imageService
				.findByRelationIdAndRelationTypeAndStatusOrderByCreateTimeAsc(
						skuId, ImageTypeEnum.PRODUCT_DESC.getValue(),
						CommonStatusEnum.有效.getValue());
		ImagePathUtil.setImagePath(ImageTypeEnum.PRODUCT_DESC,
				productDescImages);
		bussinessVO.setProductDescImages(productDescImages);

		if (StringUtils.isNotBlank(brandId)) {
			List<Image> brandLogoImages = imageService
					.findByRelationIdAndRelationType(brandId,
							ImageTypeEnum.BRAND_LOGO.getValue(),
							CommonStatusEnum.有效.getValue());
			ImagePathUtil.setImagePath(ImageTypeEnum.BRAND_LOGO,
					brandLogoImages);
			bussinessVO.setBrandLogoImages(brandLogoImages);
			List<Image> brandDescImages = imageService
					.findByRelationIdAndRelationType(brandId,
							ImageTypeEnum.BRAND_DESC.getValue(),
							CommonStatusEnum.有效.getValue());
			ImagePathUtil.setImagePath(ImageTypeEnum.BRAND_DESC,
					brandDescImages);
			bussinessVO.setBrandDescImages(brandDescImages);
		}
		if (StringUtils.isNotBlank(supplierOrgId)) {
			List<Image> honorImages = imageService
					.findByRelationIdAndRelationType(supplierOrgId,
							ImageTypeEnum.SUPPLIER_HONOR.getValue(),
							CommonStatusEnum.有效.getValue());
			ImagePathUtil.setImagePath(ImageTypeEnum.SUPPLIER_HONOR,
					honorImages);
			bussinessVO.setHonorImages(honorImages);
			List<Image> supplierLogoImages = imageService
					.findByRelationIdAndRelationType(supplierOrgId,
							ImageTypeEnum.SUPPLIER_LOGO.getValue(),
							CommonStatusEnum.有效.getValue());
			ImagePathUtil.setImagePath(ImageTypeEnum.SUPPLIER_LOGO,
					supplierLogoImages);
			bussinessVO.setSupplierLogoImages(supplierLogoImages);
		}

	}

	@Transactional
	@Override
	public void updatePriceAndSaveOperation(String productSkuId,
	                                        String productCategoryId, List<ProductPrice> productPrices,
	                                        ProductPriceActionVO productPriceActionVO, String feeRemark,
	                                        String feeLogistics, String unit, Integer hasTax, Integer hasTransportation) {
		if (CollectionUtils.isEmpty(productPrices)) {
			return;
		}
		updateRemarksAndTaxAndTransportation(productSkuId, feeRemark, feeLogistics, unit, hasTax, hasTransportation);
		updateProductPrices(productPrices);
		productPriceActionVO.setHasTax(hasTax);
		productPriceActionVO.setHasTransportation(hasTransportation);
		productPriceActionService.saveProductPriceAction(productSkuId, productCategoryId,
				productPriceActionVO);
	}

	/**
	 * 修改备注信息`是否包含运费和税
	 *
	 * @param productSkuId
	 * @param feeRemark
	 * @param feeLogistics
	 * @param hasTax
	 * @param hasTransportation
	 */
	private void updateRemarksAndTaxAndTransportation(String productSkuId, String feeRemark,
	                                                  String feeLogistics, String unit, Integer hasTax, Integer hasTransportation) {
		ProductSKU productSKU = findByPK(productSkuId);
		if (productSKU == null) {
			return;
		}
		productSKU.setFeeRemark(feeRemark);
		productSKU.setFeeLogistics(feeLogistics);
		productSKU.setHasTax(hasTax);
		productSKU.setHasTransportation(hasTransportation);
		productSKU.setUnit(unit);
		save(productSKU);
	}

	private void updateProductPrices(List<ProductPrice> productPrices) {
		ProductPrice productPriceFromDb = null;
		for (ProductPrice productPrice : productPrices) {
			productPriceFromDb = productPriceService.findByPK(productPrice
					.getId());
			if (productPriceFromDb == null) {
				continue;
			}
			productPriceFromDb
					.setActuallyPrice(productPrice.getActuallyPrice());
			productPriceService.save(productPriceFromDb);
			logger.info("【商品价格维护-修改价格，商品SKUId】"
					+ productPriceFromDb.getProductSKUId() + "【价格Id】"
					+ productPriceFromDb.getId() + "【修改为】"
					+ productPrice.getActuallyPrice());
		}
	}


	@Override
	public boolean checkRightByProductSKUId(String employeeId,
	                                        String productSKUId) {
		return productSKUDao.countByProductSKUId(productSKUId, employeeId) > 0;
	}
	
	@Override
	public boolean addBatch1000Products() {
		for(int i = 1001; i < 2001; i++) {
			ProductSKUModel model = new ProductSKUModel();
			model.setBrandId("4028808451d8c28c0151f1f6967f010d");
			model.setSupplierOrgId("4028808451d8c2550151ed5d445f082c");
			model.setCateId("ff808081516abc8201516ae834660368");
			model.setDelProductImageIds(new ArrayList<String>());
			model.setFeeLogistics("feeLogistics" + i);
			model.setFeeRemark("feeRemark" + i);
			model.setHasTax(1);
			model.setHasTransportation(1);
			model.setMinOrderCount(1);
			model.setProductDesc("productDesc" + i);
			model.setProductImageIds(new ArrayList<String>());
			model.setProductName("productName" + i);
			model.setProductNumber(i);
			model.setProductPriceModels(new ArrayList<ProductPriceModel>());
			model.setProductPropertiesModels(new ArrayList<ProductPropertiesModel>());
			model.setStatus(1);
			model.setUnit("个");
			createOrUpdateProductSKUModel(model);
		}
		return true;
	}
}