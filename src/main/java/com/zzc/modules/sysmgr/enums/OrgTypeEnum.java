package com.zzc.modules.sysmgr.enums;

import com.zzc.core.enums.EnumInterface;

/**
 * 组织类型枚举
 * <p/>
 * Created by wufan on 2015/11/17.
 * 
 */
public enum OrgTypeEnum implements EnumInterface {

	self("1", "本公司"), purchaser("2", "采购商"), supplier("3", "供应商");

	private String code;

	private String name;

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	OrgTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
}
