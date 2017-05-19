package com.zzc.modules.sysmgr.product.web.vo;

import java.math.BigDecimal;

/**
 * 
 * @author apple
 *
 */
public class ProductPriceModel implements Cloneable {

	private String id;
	private String priceKindId;
	private BigDecimal actuallyPrice;
	private Integer status;

	public String getPriceKindId() {
		return priceKindId;
	}

	public void setPriceKindId(String priceKindId) {
		this.priceKindId = priceKindId;
	}

	public BigDecimal getActuallyPrice() {
		return actuallyPrice;
	}

	public void setActuallyPrice(BigDecimal actuallyPrice) {
		this.actuallyPrice = actuallyPrice;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
