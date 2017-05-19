package com.zzc.modules.sysmgr.enums;

import com.zzc.core.enums.EnumInterface;

/**
 * 性别
 * <p/>
 * Created by wufan on 2015/11/12.
 * 
 */
public enum RoleTypeEnum implements EnumInterface {

	system("0", "系统角色"), workflow("1", "流程角色");

	private String code;

	private String name;

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	RoleTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
}
