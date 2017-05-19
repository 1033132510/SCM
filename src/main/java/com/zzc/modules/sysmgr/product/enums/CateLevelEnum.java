package com.zzc.modules.sysmgr.product.enums;

/**
 * 
 * @author apple
 *
 */
public enum CateLevelEnum {

	level1(1), level2(2), level3(3);

	private Integer value;

	public Integer getValue() {
		return this.value;
	}

	private CateLevelEnum(Integer value) {
		this.value = value;
	}
}
