package com.zzc.modules.sysmgr.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.modules.sysmgr.common.util.ImagePathUtil;
import com.zzc.modules.sysmgr.enums.ImageTypeEnum;
import com.zzc.modules.sysmgr.file.entity.Image;
import com.zzc.modules.sysmgr.file.service.ImageService;
import com.zzc.modules.sysmgr.product.entity.Category;
import com.zzc.modules.sysmgr.product.entity.ProductSKU;
import com.zzc.modules.sysmgr.product.service.ProductPriceService;
import com.zzc.modules.sysmgr.product.service.ProductPropertiesService;
import com.zzc.modules.sysmgr.product.service.ProductSKUBussinessBuilder;
import com.zzc.modules.sysmgr.product.service.vo.ProductSKUBussinessVO;

@Service("sysmgrProductSKUBussinessBuilder")
@Scope("prototype")
@Deprecated
public class SysmgrProductSKUBussinessBuilder implements ProductSKUBussinessBuilder {

	private ProductSKUBussinessVO bussinessVO = new ProductSKUBussinessVO();

	@Autowired
	private ProductPriceService productPriceService;

	@Autowired
	private ProductPropertiesService productPropertiesService;

	@Autowired
	private ImageService imageService;

	@Override
	public void buildProductSKU(ProductSKU sku) {
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
	}

	@Override
	public void buildProductCategory(Category category) {
		bussinessVO.setCategory(category);
	}

	@Override
	public void buildProductPrice(ProductSKU sku) {
		System.err.println(productPriceService);
		productPriceService.getProductPriceByProductId(sku.getId(), CommonStatusEnum.有效.getValue(), null);
	}

	@Override
	public void buildProductProperties(ProductSKU sku, Category category) {
		bussinessVO.setProductCategoryItemValues(productPropertiesService.getProductCategoryItemValueByProductAndCategory(sku.getId(), category.getId(), CommonStatusEnum.有效.getValue()));
	}

	@Override
	public void buildProductImages(ProductSKU sku) {
		List<Image> productImages = imageService.findByRelationIdAndRelationTypeAndStatusOrderByCreateTimeAsc(sku.getId(), ImageTypeEnum.PRODUCT_IMAGE.getValue(), CommonStatusEnum.有效.getValue());
		for (Image image : productImages) {
			image.setPath(ImagePathUtil.getImageBasePath(ImageTypeEnum.PRODUCT_IMAGE, image));
		}
		bussinessVO.setProductImages(productImages);
		List<Image> productDescImages = imageService.findByRelationIdAndRelationTypeAndStatusOrderByCreateTimeAsc(sku.getId(), ImageTypeEnum.PRODUCT_DESC.getValue(), CommonStatusEnum.有效.getValue());
		for (Image image : productDescImages) {
			image.setPath(ImagePathUtil.getImageBasePath(ImageTypeEnum.PRODUCT_DESC, image));
		}
		bussinessVO.setProductDescImages(productDescImages);
	}

	@Override
	public ProductSKUBussinessVO retrieveResult() {
		return bussinessVO;
	}

}
