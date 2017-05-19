package com.zzc.modules.sysmgr.product.web.vo;

/**
 * 
 * @author famous
 *
 */
public class ProductPropertiesModel {
	// producterProperties 主键
	private String id;
	
	private String productCategoryItemKeyId;
	
	private String productCategoryId;
	
	private String value;
	
	private String productPropertiesName;
	/**
	 * 1 是 类别 0是商品
	 */
	private Integer itemType;
	// 状态
	private Integer status;
	// 排序
	private Integer order;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductCategoryItemKeyId() {
		return productCategoryItemKeyId;
	}

	public void setProductCategoryItemKeyId(String productCategoryItemKeyId) {
		this.productCategoryItemKeyId = productCategoryItemKeyId;
	}

	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getProductPropertiesName() {
		return productPropertiesName;
	}

	public void setProductPropertiesName(String productPropertiesName) {
		this.productPropertiesName = productPropertiesName;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

}
