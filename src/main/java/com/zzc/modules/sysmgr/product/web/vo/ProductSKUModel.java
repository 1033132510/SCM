package com.zzc.modules.sysmgr.product.web.vo;

import java.util.List;

/**
 * 
 * @author apple
 * 
 */
public class ProductSKUModel {
	/**
	 * 
	 */
	// 商品ID
	private String productId;
	// 商品编码
	private String productCode;
	// 商品编号
	private Integer productNumber;
	// 单位
	private String unit;

	private Integer minOrderCount;
	// 费用描述
	private String feeRemark;
	// 物流费用备注
	private String feeLogistics;
	// 产品名称
	private String productName;
	// 供应商组织ID
	private String supplierOrgId;
	// 品牌ID
	private String brandId;
	// 类别ID
	private String cateId;
	// 状态
	private Integer status;
	// 商品描述
	private String productDesc;
	// 是否含税
	private Integer hasTax;
	// 是否含运费
	private Integer hasTransportation;
	//商品图片
	private List<String> productImageIds;

	//需要删除的商品图片
	private List<String> delProductImageIds;

	//价格体系
	private List<ProductPriceModel> productPriceModels;

	//商品属性
	private List<ProductPropertiesModel> productPropertiesModels;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Integer getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(Integer productNumber) {
		this.productNumber = productNumber;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getFeeRemark() {
		return feeRemark;
	}

	public void setFeeRemark(String feeRemark) {
		this.feeRemark = feeRemark;
	}

	public String getFeeLogistics() {
		return feeLogistics;
	}

	public void setFeeLogistics(String feeLogistics) {
		this.feeLogistics = feeLogistics;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSupplierOrgId() {
		return supplierOrgId;
	}

	public void setSupplierOrgId(String supplierOrgId) {
		this.supplierOrgId = supplierOrgId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getCateId() {
		return cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<String> getProductImageIds() {
		return productImageIds;
	}

	public void setProductImageIds(List<String> productImageIds) {
		this.productImageIds = productImageIds;
	}

	public List<ProductPriceModel> getProductPriceModels() {
		return productPriceModels;
	}

	public void setProductPriceModels(List<ProductPriceModel> productPriceModels) {
		this.productPriceModels = productPriceModels;
	}

	public List<ProductPropertiesModel> getProductPropertiesModels() {
		return productPropertiesModels;
	}

	public void setProductPropertiesModels(List<ProductPropertiesModel> productPropertiesModels) {
		this.productPropertiesModels = productPropertiesModels;
	}

	public String getProductDesc() {
		return productDesc;
	}
	
	public Integer getHasTax() {
		return hasTax;
	}

	public void setHasTax(Integer hasTax) {
		this.hasTax = hasTax;
	}

	public Integer getHasTransportation() {
		return hasTransportation;
	}

	public void setHasTransportation(Integer hasTransportation) {
		this.hasTransportation = hasTransportation;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public List<String> getDelProductImageIds() {
		return delProductImageIds;
	}

	public void setDelProductImageIds(List<String> delProductImageIds) {
		this.delProductImageIds = delProductImageIds;
	}

	public Integer getMinOrderCount() {
		return minOrderCount;
	}

	public void setMinOrderCount(Integer minOrderCount) {
		this.minOrderCount = minOrderCount;
	}

}