package com.zzc.modules.sysmgr.user.supplier.supplierenum;

import com.zzc.core.enums.EnumInterface;

public enum statusTypeEnum implements EnumInterface {

	有效("1", "有效"),无效("0", "无效");

	private String code;

	private String name;

	statusTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
