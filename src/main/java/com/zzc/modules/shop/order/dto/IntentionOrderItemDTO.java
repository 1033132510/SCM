package com.zzc.modules.shop.order.dto;

import java.math.BigDecimal;

public class IntentionOrderItemDTO {

	private String intentionOrderId;

	private String productCode;

	private String productName;

	private String productId;

	private BigDecimal price;

	private Integer quantity;

	private String productImage;

	private Integer status;

	private BigDecimal totalPrice;

	public String getIntentionOrderId() {
		return intentionOrderId;
	}

	public void setIntentionOrderId(String intentionOrderId) {
		this.intentionOrderId = intentionOrderId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

}
