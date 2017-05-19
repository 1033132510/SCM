package com.zzc.modules.sysmgr.product.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.zzc.core.entity.BaseEntity;

/**
 * 11
 * @author apple 价格等级体系
 */
@Entity
@Table(name = "ES_PRICE_KIND")
public class PriceKind extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -2120842919328087925L;

	// 价格名称
	@Column(name = "price_kind_name", unique = true)
	private String priceKindName;

	// 价格名称描述
	@Column(name = "price_kind_desc")
	private String priceKindDesc;

	@Column(name = "price_kind_type")
	private Integer priceKindType;

	public String getPriceKindName() {
		return priceKindName;
	}

	public void setPriceKindName(String priceKindName) {
		this.priceKindName = priceKindName;
	}

	public String getPriceKindDesc() {
		return priceKindDesc;
	}

	public void setPriceKindDesc(String priceKindDesc) {
		this.priceKindDesc = priceKindDesc;
	}

	public Integer getPriceKindType() {
		return priceKindType;
	}

	public void setPriceKindType(Integer priceKindType) {
		this.priceKindType = priceKindType;
	}

}
