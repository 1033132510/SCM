package com.zzc.modules.sysmgr.enums;

import com.zzc.core.enums.EnumInterface;

/**
 * 在职状态
 * <p/>
 * Created by wufan on 2015/11/16.
 * 
 */
public enum EmployeeStatusEnum implements EnumInterface {

	inService("1", "在职"), dimission("2", "离职");

	private String code;

	private String name;

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	EmployeeStatusEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
}
