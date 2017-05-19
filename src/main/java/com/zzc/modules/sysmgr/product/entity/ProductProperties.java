package com.zzc.modules.sysmgr.product.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zzc.core.entity.BaseEntity;
import com.zzc.core.security.XssIgnoreSerializer;

/**
 * 产品和产品类别属性记录表
 * 
 * @author apple
 *
 */

@Entity
@Table(name = "ES_PRODUCT_PROPERTIES")
public class ProductProperties extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5763771282629272022L;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "item_key_id")
	private CategoryItem productCategoryItemKey;

	@Column(name = "product_category_id")
	private String productCategoryId;

	@Column(name = "product_sku_id")
	private String productSKUId;

	@Column(name = "value")
	@JsonSerialize(using = XssIgnoreSerializer.class)
	private String value;

	@Column(name = "product_properties_name")
	@JsonSerialize(using = XssIgnoreSerializer.class)
	private String productPropertiesName;

	@Column(name = "product_properties_item_type")
	private Integer itemType;

	@Column(name = "order_number")
	private Integer orderNumber;

	public CategoryItem getProductCategoryItemKey() {
		return productCategoryItemKey;
	}

	public void setProductCategoryItemKey(CategoryItem productCategoryItemKey) {
		this.productCategoryItemKey = productCategoryItemKey;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public String getProductSKUId() {
		return productSKUId;
	}

	public void setProductSKUId(String productSKUId) {
		this.productSKUId = productSKUId;
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

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

}
