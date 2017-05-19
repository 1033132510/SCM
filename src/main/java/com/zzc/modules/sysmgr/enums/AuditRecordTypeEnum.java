package com.zzc.modules.sysmgr.enums;

/**
 * Created by chenjiahai on 16/2/23.
 */
public enum AuditRecordTypeEnum {

    CATEGORY_ADMINISTRATOR_toMAJORDOMO(1, "品类负责人向总监提交"), CATEGORY_ADMINISTRATOR_toSUPPLY_USER(2, "品类负责人向供应商"), CATEGORY_MAJORDOMO_toADMINISTRATOR(3,
            "部类总监向管理员"), CATEGORY_MAJORDOMO_toPass(4, "部类总监审批成功"), SUPPLY_USER_toADMINISTRATOR(5, "供应商人员向管理员"), SUPPLY_USER_toDelete(6, "供应商删除");

    private Integer code;

    private String name;

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    AuditRecordTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
