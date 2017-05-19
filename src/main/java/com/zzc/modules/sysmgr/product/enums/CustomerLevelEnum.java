package com.zzc.modules.sysmgr.product.enums;

public enum CustomerLevelEnum {
	/**
	 */
	one(1), two(2), three(3);

	private Integer value;

	public Integer getValue() {
		return this.value;
	}

	private CustomerLevelEnum(Integer value) {
		this.value = value;
	}
}
