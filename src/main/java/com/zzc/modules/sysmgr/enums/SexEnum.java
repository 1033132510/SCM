package com.zzc.modules.sysmgr.enums;

import com.zzc.core.enums.EnumInterface;

/**
 * 性别
 * <p/>
 * Created by wufan on 2015/11/12.
 * 
 */
public enum SexEnum implements EnumInterface {

	women("0", "女"), man("1", "男");

	private String code;

	private String name;

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	SexEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
}
