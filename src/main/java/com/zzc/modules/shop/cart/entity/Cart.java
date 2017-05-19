package com.zzc.modules.shop.cart.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zzc.core.entity.BaseEntity;
import com.zzc.modules.sysmgr.user.purchaser.entity.PurchaserUser;

/**
 * 购物车
 * 
 * @author chenjiahai
 *
 */
@Entity
@Table(name = "ES_CART")
public class Cart extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4953201110511796911L;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "purchaser_user_id")
	private PurchaserUser purchaserUser;

	// 产品Id
	@Column(name = "product_sku_id")
	private String productSkuId;

	// 产品类别Id
	@Column(name = "product_category_id")
	private String productCategoryId;

	@Column(name = "quantity")
	private Integer quantity;

	public PurchaserUser getPurchaserUser() {
		return purchaserUser;
	}

	public void setPurchaserUser(PurchaserUser purchaserUser) {
		this.purchaserUser = purchaserUser;
	}

	public String getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(String productSkuId) {
		this.productSkuId = productSkuId;
	}

	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
