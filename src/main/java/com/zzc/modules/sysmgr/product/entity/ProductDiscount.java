package com.zzc.modules.sysmgr.product.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.zzc.core.entity.BaseEntity;

@Entity
@Table(name = "ES_PRODUCT_DISCOUNT")
public class ProductDiscount extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5523910160498822511L;

	@Column(name = "cate_id")
	private String cateId;

	@Column(name = "customer_level")
	private String customerLevel;

	@Column(name = "discount")
	private BigDecimal discount;

	public String getCateId() {
		return cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public String getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

}
