package com.zzc.modules.supply.service.impl;

import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.utils.JsonUtils;
import com.zzc.modules.sysmgr.file.entity.Image;
import com.zzc.modules.sysmgr.file.service.ImageService;
import com.zzc.modules.sysmgr.product.entity.*;
import com.zzc.modules.sysmgr.product.enums.CateLevelEnum;
import com.zzc.modules.sysmgr.product.enums.PriceKindEnum;
import com.zzc.modules.sysmgr.product.enums.ProductCateTypeEnum;
import com.zzc.modules.sysmgr.product.service.*;
import com.zzc.modules.sysmgr.product.web.vo.ProductPropertiesModel;
import com.zzc.modules.supply.entity.AuditBill;
import com.zzc.modules.supply.entity.AuditRecord;
import com.zzc.modules.supply.service.ProductShelvesByApprovalService;
import com.zzc.modules.supply.vo.SupplyProductSKUVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("productShelvesByApprovalService")
public class ProductShelvesByApprovalServiceImpl implements ProductShelvesByApprovalService {

	@Autowired
	private ProductSKUService productSKUService;

	@Autowired
	private ProductPropertiesService productPropertiesService;

	@Autowired
	private ProductPriceService productPriceService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private CategoryItemService categoryItemService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PriceKindService priceKindService;

	@Autowired
	private ProductCategoryService productCategoryService;

	/**
	 * 商品信息保存
	 * @param auditBill
	 * @param auditRecord
	 * @param supplyProductSKUVO
	 * @return
	 */
	@Override
	@Transactional
	public ProductSKU productShelves(AuditBill auditBill, AuditRecord auditRecord, SupplyProductSKUVO supplyProductSKUVO) {

		ProductSKU productSKU = this.getProductSKU(auditBill, auditRecord);
		Map<String, Object> params = new HashMap<>();
//		params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		params.put("AND_EQ_productCode", productSKU.getProductCode());
		List<ProductSKU> oldProductSKUList = productSKUService.findAll(params, ProductSKU.class);
		//基础商品信息保存
		if (oldProductSKUList.isEmpty()) {
			productSKU = productSKUService.create(productSKU);
		} else {
			productSKU.setId(oldProductSKUList.get(0).getId());
			productSKU = productSKUService.update(productSKU);
		}

		//属性保存
		//先作废之前的属性
		productPropertiesService.disableProductProperties(productSKU.getId());

		List<ProductProperties> productPropertiesList = this.getProductPropertiesList(auditBill, productSKU);
		for (int i = 0; i < productPropertiesList.size(); i++) {
			productPropertiesService.create(productPropertiesList.get(i));
		}
		//价格保存
		//先作废之前的价格
		productPriceService.disableProductPrice(productSKU.getId());
		List<ProductPrice> productPricesList = this.getProductPriceList(supplyProductSKUVO, productSKU, auditRecord);
		for (int i = 0; i < productPricesList.size(); i++) {
			productPriceService.create(productPricesList.get(i));
		}
		//图片关系操作
		
		imageService.deleteByRelationId(productSKU.getId());

		List<Image> images = imageService.findByRelationId(auditBill.getSysProductSKU().getId(), CommonStatusEnum.有效.getValue());

		for (int i = 0; i < images.size(); i++) {
			Image image = new Image();
			image.setAliasName(images.get(i).getAliasName());
			image.setOriginalName(images.get(i).getOriginalName());
			image.setPath(images.get(i).getPath());
			image.setRelationType(images.get(i).getRelationType());
			image.setSortOrder(images.get(i).getSortOrder());
			image.setStatus(images.get(i).getStatus());
			image.setRelationId(productSKU.getId());
			imageService.save(image);
		}

//        imageService.buildRelationBetweenImageAndEntity(productSKUModel.getProductImageIds(), productSKU.getId());
//        imageService.destoryRelationBetweenImageAndEntity();

//        类别关系表
		if (oldProductSKUList.isEmpty()) {
			Category category = categoryService.findByPK(supplyProductSKUVO.getThirdLevelCateId());
			ProductCategory productCategoryFirst = new ProductCategory();
			productCategoryFirst.setCategory(category);
			productCategoryFirst.setLevel(CateLevelEnum.level3.getValue());
			productCategoryFirst.setType(ProductCateTypeEnum.mainKind.getValue());
			productCategoryFirst.setProductSKU(productSKU);
			productCategoryFirst.setStatus(CommonStatusEnum.有效.getValue());
			ProductCategory productCategoryFirst2 = productCategoryService.create(productCategoryFirst);

			ProductCategory productCategorySecond = new ProductCategory();
			productCategorySecond.setCategory(category.getParentCategory());
			productCategorySecond.setLevel(CateLevelEnum.level2.getValue());
			productCategorySecond.setType(ProductCateTypeEnum.assistKind.getValue());
			productCategorySecond.setProductSKU(productSKU);
			productCategorySecond.setStatus(CommonStatusEnum.有效.getValue());
			productCategoryService.create(productCategorySecond);


			ProductCategory productCategoryThird = new ProductCategory();
			productCategoryThird.setCategory(category.getParentCategory().getParentCategory());
			productCategoryThird.setLevel(CateLevelEnum.level1.getValue());
			productCategoryThird.setType(ProductCateTypeEnum.parent.getValue());
			productCategoryThird.setProductSKU(productSKU);
			productCategoryThird.setStatus(CommonStatusEnum.有效.getValue());
			productCategoryService.create(productCategoryThird);

		}
		return productSKU;

	}

	/**
	 * 商品价格列表
	 * @param supplyProductSKUVO
	 * @param productSKU
	 * @param auditRecord
	 * @return
	 */
	private List<ProductPrice> getProductPriceList(SupplyProductSKUVO supplyProductSKUVO, ProductSKU productSKU, AuditRecord auditRecord) {
//        ProductPriceModel

		List<ProductPrice> productPriceList = new ArrayList<ProductPrice>();
		for (PriceKindEnum price : PriceKindEnum.values()) {
			productPriceList.add(this.getProductPrice(supplyProductSKUVO, productSKU, price.getValue(), auditRecord));
		}
		return productPriceList;
	}

	/**
	 * 获得商品价格
	 * @param supplyProductSKUVO
	 * @param productSKU
	 * @param statLevel
	 * @param auditRecord
	 * @return
	 */
	private ProductPrice getProductPrice(SupplyProductSKUVO supplyProductSKUVO, ProductSKU productSKU, Integer statLevel, AuditRecord auditRecord) {
		ProductPrice productPrice = new ProductPrice();
		if (statLevel == PriceKindEnum.cost.getValue()) {
			productPrice.setActuallyPrice(supplyProductSKUVO.getCost());
		} else if (statLevel == PriceKindEnum.recommend.getValue()) {
			productPrice.setActuallyPrice(supplyProductSKUVO.getRecommend());
		} else {
			productPrice.setActuallyPrice(auditRecord.getStandard());
		}

		productPrice.setProductSKUId(productSKU.getId());
		productPrice.setStatus(CommonStatusEnum.有效.getValue());
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		params.put("AND_EQ_priceKindType", statLevel);
		List<PriceKind> priceKindList = priceKindService.findAll(params, PriceKind.class);
		if (!priceKindList.isEmpty()) {
			productPrice.setPriceKindModel(priceKindList.get(0));
		}
		return productPrice;
	}

	/**
	 * 获得商品信息列表
	 * @param auditBill
	 * @param productSKU
	 * @return
	 */
	private List<ProductProperties> getProductPropertiesList(AuditBill auditBill, ProductSKU productSKU) {

		SupplyProductSKUVO supplyProductSKUVO = JsonUtils.toObject(auditBill.getSysProductSKU().getProductInfos(), SupplyProductSKUVO.class);
		List<ProductPropertiesModel> productPropertiesModelList = supplyProductSKUVO.getProductPropertiesModels();
		List<ProductProperties> productPropertiesList = new ArrayList<ProductProperties>();
		for (int i = 0; i < productPropertiesModelList.size(); i++) {
			productPropertiesList.add(this.getProductProperties(productPropertiesModelList.get(i), productSKU, supplyProductSKUVO.getThirdLevelCateId()));
		}
		return productPropertiesList;
	}

	/**
	 * 获得商品属性
	 * @param productPropertiesModel
	 * @param productSKU
	 * @param categortId
	 * @return
	 */
	private ProductProperties getProductProperties(ProductPropertiesModel productPropertiesModel, ProductSKU productSKU, String categortId) {
		ProductProperties productProperties = new ProductProperties();
		productProperties.setItemType(productPropertiesModel.getItemType());
		productProperties.setOrderNumber(productPropertiesModel.getOrder());
		productProperties.setProductCategoryId(categortId);

		if (StringUtils.isNotEmpty(productPropertiesModel.getProductCategoryItemKeyId())) {
			productProperties.setProductCategoryItemKey(categoryItemService.findByPK(productPropertiesModel.getProductCategoryItemKeyId()));
		}
		productProperties.setProductPropertiesName(productPropertiesModel.getProductPropertiesName());
		productProperties.setProductSKUId(productSKU.getId());
		productProperties.setStatus(CommonStatusEnum.有效.getValue());
		productProperties.setValue(productPropertiesModel.getValue());
		return productProperties;
	}

	private ProductSKU getProductSKU(AuditBill auditBill, AuditRecord auditRecord) {
		SupplyProductSKUVO supplyProductSKUVO = JsonUtils.toObject(auditBill.getSysProductSKU().getProductInfos(), SupplyProductSKUVO.class);
		ProductSKU productSKU = new ProductSKU();
		productSKU.setBrand(auditBill.getSysProductSKU().getBrand());
		productSKU.setFeeLogistics(supplyProductSKUVO.getFeeLogistics());
		productSKU.setFeeRemark(supplyProductSKUVO.getFeeRemark());
		productSKU.setHasTax(auditRecord.getHasTax());
		productSKU.setHasTransportation(auditRecord.getHasTransportation());
		productSKU.setMinOrderCount(supplyProductSKUVO.getMinOrderCount());
		productSKU.setProductCode(auditBill.getSysProductSKU().getProductCode());
		productSKU.setProductDesc(supplyProductSKUVO.getProductDesc());
		productSKU.setProductName(supplyProductSKUVO.getProductName());
		productSKU.setProductNumber(supplyProductSKUVO.getProductNumber());
		productSKU.setStatus(CommonStatusEnum.有效.getValue());
		productSKU.setSupplierOrg(auditBill.getSysProductSKU().getSupplierOrg());
		productSKU.setUnit(supplyProductSKUVO.getUnit());
		productSKU.setHasTaxForSupplier(supplyProductSKUVO.getHasTax());
		productSKU.setHasTransportationForSupplier(supplyProductSKUVO.getHasTransportation());
		return productSKU;
	}

	/**
	 * 取消基本信息保存
	 * @param auditBill
	 * @param auditRecord
	 * @param supplyProductSKUVO
	 * @return
	 */
	@Override
	@Transactional
	public ProductSKU productOFFShelves(AuditBill auditBill, AuditRecord auditRecord, SupplyProductSKUVO supplyProductSKUVO) {
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		params.put("AND_EQ_productCode", supplyProductSKUVO.getProductCode());
		List<ProductSKU> oldProductSKUList = productSKUService.findAll(params, ProductSKU.class);
		//基础商品信息保存
		ProductSKU productSKU = new ProductSKU();
		if (!oldProductSKUList.isEmpty()) {
			productSKU = oldProductSKUList.get(0);
			productSKU.setStatus(CommonStatusEnum.无效.getValue());
			productSKU = productSKUService.update(productSKU);
		}
		return productSKU;
	}

}
