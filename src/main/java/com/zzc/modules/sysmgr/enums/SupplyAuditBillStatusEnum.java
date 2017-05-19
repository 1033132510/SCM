package com.zzc.modules.sysmgr.enums;

/**
 * Created by chenjiahai on 16/1/19.
 */
public enum SupplyAuditBillStatusEnum {

	MANAGER_AUDITING(1,"待品类管理员审批"), BOSS_AUDITING(2,"待部类总监审批"), TO_ADJUSTED(3,"待供应商调整"), PASS(4,"审批通过"), DELETE(5,"审批删除");

    private Integer code;
    private String name;
    
    SupplyAuditBillStatusEnum(Integer code, String name) {
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
