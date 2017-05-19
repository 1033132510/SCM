package com.zzc.modules.supply.vo;

import com.zzc.modules.sysmgr.file.entity.Image;
import com.zzc.modules.sysmgr.product.web.vo.ProductPropertiesModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenjiahai on 16/2/18.
 */
public class SupplyProductSKUVO {

	// 商品ID
	private String productId;
	// 商品编码
	private String productCode;
	// 库存数量
	private Integer productNumber;
	// 单位
	private String unit;
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
	// 三级类别ID
	private String thirdLevelCateId;
	// 二级类别ID
	private String secondLevelCateId;
	// 状态
	private Integer status;
	// 商品描述
	private String productDesc;
	// 是否含税
	private Integer hasTax;
	// 是否含运费
	private Integer hasTransportation;
	// 最小起订量
	private Integer minOrderCount;

	// 商品和商品详情最终id集合
	private List<String> imageIds;
	//商品图片
	private List<String> productImageIds;
	//需要删除的商品图片
	private List<String> delProductImageIds;
	// 标价
	private BigDecimal standard;
	// 成本
	private BigDecimal cost;
	// 建议零售
	private BigDecimal recommend;
	//商品属性
	private List<ProductPropertiesModel> productPropertiesModels;

	private List<Image> images = new ArrayList<Image>();

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

	public String getThirdLevelCateId() {
		return thirdLevelCateId;
	}

	public void setThirdLevelCateId(String thirdLevelCateId) {
		this.thirdLevelCateId = thirdLevelCateId;
	}

	public String getSecondLevelCateId() {
		return secondLevelCateId;
	}

	public void setSecondLevelCateId(String secondLevelCateId) {
		this.secondLevelCateId = secondLevelCateId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public List<String> getImageIds() {
		return imageIds;
	}

	public void setImageIds(List<String> imageIds) {
		this.imageIds = imageIds;
	}

	public List<String> getProductImageIds() {
		return productImageIds;
	}

	public void setProductImageIds(List<String> productImageIds) {
		this.productImageIds = productImageIds;
	}

	public List<String> getDelProductImageIds() {
		return delProductImageIds;
	}

	public void setDelProductImageIds(List<String> delProductImageIds) {
		this.delProductImageIds = delProductImageIds;
	}

	public BigDecimal getStandard() {
		return standard;
	}

	public void setStandard(BigDecimal standard) {
		this.standard = standard;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getRecommend() {
		return recommend;
	}

	public void setRecommend(BigDecimal recommend) {
		this.recommend = recommend;
	}

	public List<ProductPropertiesModel> getProductPropertiesModels() {
		return productPropertiesModels;
	}

	public void setProductPropertiesModels(List<ProductPropertiesModel> productPropertiesModels) {
		this.productPropertiesModels = productPropertiesModels;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}
}