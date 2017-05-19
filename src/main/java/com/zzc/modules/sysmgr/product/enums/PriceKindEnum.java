package com.zzc.modules.sysmgr.product.enums;

/**
 * 
 * @author apple
 *
 */
public enum PriceKindEnum {

	level1(1), level2(2), level3(3), recommend(4), cost(-1), standard(-2);

	private Integer value;

	public Integer getValue() {
		return this.value;
	}

	private PriceKindEnum(Integer value) {
		this.value = value;
	}
}
