package com.zzc.modules.sysmgr.enums;

import com.zzc.core.enums.EnumInterface;

public enum PurchaserLevelEnum implements EnumInterface {

	/**
	 * 用户类型；1：一级客户；2：二级客户；3：三级客户
	 */
	一级客户("1", "一级客户"), 二级客户("2", "二级客户"), 三级客户("3", "三级客户");

	private String code;

	private String name;

	PurchaserLevelEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getName() {
		return name;
	}

}
