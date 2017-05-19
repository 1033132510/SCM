package com.zzc.modules.sysmgr.product.service.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zzc.core.security.XssIgnoreSerializer;
import com.zzc.modules.sysmgr.file.entity.Image;
import com.zzc.modules.sysmgr.product.entity.Brand;
import com.zzc.modules.sysmgr.product.entity.Category;
import com.zzc.modules.sysmgr.product.entity.ProductPrice;
import com.zzc.modules.sysmgr.product.entity.ProductProperties;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierOrg;

/**
 * 
 * @author apple
 *
 */
public class ProductSKUBussinessVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2037482095396905788L;

	private String productId;
	private String productCode;
	private String productDesc;
	private Integer hasTax;
	private Integer hasTransportation;
	private Integer productNumber;
	
	@JsonSerialize(using = XssIgnoreSerializer.class)
	private String unit;
	
	private String feeRemark;
	private String feeLogistics;
	@JsonSerialize(using = XssIgnoreSerializer.class)
	private String productName;
	private SupplierOrg supplierOrg;
	private Brand brand;
	private Integer status;
	private Category category;
	private Integer minOrderCount;
	private List<ProductPrice> productPrices;
	private List<ProductProperties> productCategoryItemValues;
	private List<Image> productImages;
	private List<Image> productDescImages;
	private List<Image> brandLogoImages;
	private List<Image> honorImages;
	private List<Image> supplierLogoImages;
	private List<Image> brandDescImages;
	private Map<String, Object> customData = new HashMap<String, Object>();

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

	public SupplierOrg getSupplierOrg() {
		return supplierOrg;
	}

	public void setSupplierOrg(SupplierOrg supplierOrg) {
		this.supplierOrg = supplierOrg;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public List<ProductPrice> getProductPrices() {
		return productPrices;
	}

	public void setProductPrices(List<ProductPrice> productPrices) {
		this.productPrices = productPrices;
	}

	public List<ProductProperties> getProductCategoryItemValues() {
		return productCategoryItemValues;
	}

	public void setProductCategoryItemValues(List<ProductProperties> productCategoryItemValues) {
		this.productCategoryItemValues = productCategoryItemValues;
	}

	public List<Image> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<Image> productImages) {
		this.productImages = productImages;
	}

	public List<Image> getProductDescImages() {
		return productDescImages;
	}

	public void setProductDescImages(List<Image> productDescImages) {
		this.productDescImages = productDescImages;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
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

	public Integer getMinOrderCount() {
		return minOrderCount;
	}

	public void setMinOrderCount(Integer minOrderCount) {
		this.minOrderCount = minOrderCount;
	}

	public Map<String, Object> getCustomData() {
		return customData;
	}

	public void setCustomData(Map<String, Object> customData) {
		this.customData = customData;
	}

	public List<Image> getBrandLogoImages() {
		return brandLogoImages;
	}

	public void setBrandLogoImages(List<Image> brandLogoImages) {
		this.brandLogoImages = brandLogoImages;
	}

	public List<Image> getHonorImages() {
		return honorImages;
	}

	public void setHonorImages(List<Image> honorImages) {
		this.honorImages = honorImages;
	}

	public List<Image> getSupplierLogoImages() {
		return supplierLogoImages;
	}

	public void setSupplierLogoImages(List<Image> supplierLogoImages) {
		this.supplierLogoImages = supplierLogoImages;
	}

	public List<Image> getBrandDescImages() {
		return brandDescImages;
	}

	public void setBrandDescImages(List<Image> brandDescImages) {
		this.brandDescImages = brandDescImages;
	}

}