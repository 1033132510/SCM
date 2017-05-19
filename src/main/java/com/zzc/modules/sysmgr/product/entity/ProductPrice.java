package com.zzc.modules.sysmgr.product.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zzc.core.entity.BaseEntity;

/**
 * 商品价格体系模型
 * 
 * @author apple
 *
 */

@Entity
@Table(name = "ES_PRODUCT_PRICE")
public class ProductPrice extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4729146248771829233L;

	// 价格分类
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "es_price_kind_id", referencedColumnName = "id")
	private PriceKind priceKindModel;

	@Column(name = "product_sku_id")
	private String productSKUId;

	@Column(name = "actuall_price")
	private BigDecimal actuallyPrice;

	// 是否被修改过
	@Column(name = "re_modified")
	private Integer reModified;

	public PriceKind getPriceKindModel() {
		return priceKindModel;
	}

	public void setPriceKindModel(PriceKind priceKindModel) {
		this.priceKindModel = priceKindModel;
	}

	public BigDecimal getActuallyPrice() {
		return actuallyPrice;
	}

	public void setActuallyPrice(BigDecimal actuallyPrice) {
		this.actuallyPrice = actuallyPrice;
	}

	public Integer getReModified() {
		return reModified;
	}

	public void setReModified(Integer reModified) {
		this.reModified = reModified;
	}

	public String getProductSKUId() {
		return productSKUId;
	}

	public void setProductSKUId(String productSKUId) {
		this.productSKUId = productSKUId;
	}

}
