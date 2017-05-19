package com.zzc.modules.shop.favorite.entity;

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
 * 收藏
 * 
 * @author chenjiahai
 *
 */
@Entity
@Table(name = "ES_FAVORITE")
public class Favorite extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1369147519239021834L;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "purchaser_user_id")
	private PurchaserUser purchaserUser;

	// 产品Id
	@Column(name = "product_sku_id")
	private String productSkuId;
	
	@Column(name = "parent_product_category_id")
	private String parentProductCategoryId;

	// 产品类别Id
	@Column(name = "product_category_id")
	private String productCategoryId;

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

	public String getParentProductCategoryId() {
		return parentProductCategoryId;
	}

	public void setParentProductCategoryId(String parentProductCategoryId) {
		this.parentProductCategoryId = parentProductCategoryId;
	}
	
}