/**
 * 
 */
package com.zzc.modules.sysmgr.enums;

/**
 * @author zhangyong
 *
 */
public enum TransportationTypeEnum {

	hasTransportation(1, "含运费"), notHasTransportation(0, "不含运费");
	
	private Integer code;
	
	private String name;

	TransportationTypeEnum(Integer code, String name) {
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