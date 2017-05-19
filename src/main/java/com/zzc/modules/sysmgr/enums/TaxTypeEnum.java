/**
 * 
 */
package com.zzc.modules.sysmgr.enums;

/**
 * @author zhangyong
 *
 */
public enum TaxTypeEnum {
	
	hasTax(1, "含税"), notHasTax(0, "不含税");
	
	private Integer code;
	
	private String name;

	TaxTypeEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
