package com.zzc.modules.sysmgr.product.enums;

/**
 * 
 * @author apple
 *
 */
public enum ItemTypeEnum {
	/**
	 */
	product(0), category(1);

	private Integer value;

	public Integer getValue() {
		return this.value;
	}

	private ItemTypeEnum(Integer value) {
		this.value = value;
	}
}