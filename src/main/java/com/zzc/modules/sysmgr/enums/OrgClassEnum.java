package com.zzc.modules.sysmgr.enums;

import com.zzc.core.enums.EnumInterface;

/**
 * 组织级别枚举
 * <p/>
 * Created by wufan on 2015/11/17.
 * 
 */
public enum OrgClassEnum implements EnumInterface {

	总部("hq", "总部"), 总部部门("hqo", "总部部门"), 子公司("hqc", "子公司"), 子公司部门("hqco", "子公司部门");

	private String code;

	private String name;

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	OrgClassEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
}
