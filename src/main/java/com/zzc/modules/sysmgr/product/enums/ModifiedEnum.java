package com.zzc.modules.sysmgr.product.enums;

/**
 * 是否被修改过
 * 
 * @author apple
 *
 */
public enum ModifiedEnum {

	/**
	 */
	changed(1), nonechanged(1);

	private Integer value;

	public Integer getValue() {
		return this.value;
	}

	private ModifiedEnum(Integer value) {
		this.value = value;
	}
}
