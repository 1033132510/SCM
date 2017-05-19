package com.zzc.modules.sysmgr.enums;

/**
 * 账户类型
 * <p/>
 * Created by wufan on 2015/11/12.
 * 
 */
public enum AccountTypeEnum implements EnumInterface {

	/**
	 * 用户类型；1：系统用户；2：供应商；3：采购商
	 */
	employee("1", "系统用户"), supplier("2", "供应商"), purcharser("3", "采购商");

	private String code;

	private String name;

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	AccountTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

}
