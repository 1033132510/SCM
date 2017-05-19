package com.zzc.modules.sysmgr.enums;

import com.zzc.core.enums.EnumInterface;

/**
 * 职工类型
 * <p/>
 * Created by wufan on 2015/11/16.
 * 
 */
public enum EmployeeTypeEnum implements EnumInterface {

	regularEmployee("1", "正式员工"), contractEmployee("2", "合约员工");

	private String code;

	private String name;

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	EmployeeTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
}
