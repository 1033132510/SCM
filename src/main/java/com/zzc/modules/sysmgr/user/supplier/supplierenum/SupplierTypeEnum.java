package com.zzc.modules.sysmgr.user.supplier.supplierenum;

public enum SupplierTypeEnum implements com.zzc.core.enums.EnumInterface {

	厂商("0", "厂商"), 代理商("1", "代理商");

	private String code;

	private String name;

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	SupplierTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
}
