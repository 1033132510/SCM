package com.zzc.modules.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zzc.core.entity.BaseEntity;

/**
 * 意向单详情
 * 
 * @author chenjiahai
 *
 */
@Entity
@Table(name = "ES_INTENTION_ORDER_ITEM")
public class IntentionOrderItem extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5160553331163933566L;

	// 所属意向订单
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "order_id")
	private IntentionOrder intentionOrder;

	// 产品Id
	@Column(name = "product_sku_id")
	private String productSKUId;

	// 产品类别Id
	@Column(name = "product_category_id")
	private String productCategoryId;

	// 下意向单时的产品价格
	@Column(name = "price")
	private BigDecimal price;

	// 数量
	@Column(name = "quantity")
	private Integer quantity;

	// 订单项总价格
	@Transient
	private BigDecimal totalPrice;

	// 产品快照信息，json格式
	@Lob
	@Column(name = "item_snap_shot")
	private String itemSnapShot;

	public String getItemSnapShot() {
		return itemSnapShot;
	}

	public void setItemSnapShot(String itemSnapShot) {
		this.itemSnapShot = itemSnapShot;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public IntentionOrder getIntentionOrder() {
		return intentionOrder;
	}

	public void setIntentionOrder(IntentionOrder intentionOrder) {
		this.intentionOrder = intentionOrder;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getProductSKUId() {
		return productSKUId;
	}

	public void setProductSKUId(String productSKUId) {
		this.productSKUId = productSKUId;
	}

	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public BigDecimal getTotalPrice() {
		return price.multiply(new BigDecimal(quantity));
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

}
