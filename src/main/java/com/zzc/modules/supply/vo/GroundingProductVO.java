package com.zzc.modules.supply.vo;

/**
 * Created by chenjiahai on 16/3/1.
 */
public class GroundingProductVO extends SupplyProductSKUVO {

	private Integer type;

	private String productSKUId;

	private String comment;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getProductSKUId() {
		return productSKUId;
	}

	public void setProductSKUId(String productSKUId) {
		this.productSKUId = productSKUId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
