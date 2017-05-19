package com.zzc.modules.sysmgr.enums;

/**
 * Created by chenjiahai on 16/2/17.
 */
public enum AuditBillOperationTypeEnum {
	// 新增, 修改, 上架, 下架
	ADD(1), UPDATE(2), SHELVE(3), OFF_SHELVE(4);

	private int value;

	AuditBillOperationTypeEnum(int value) {
		this.value = value;
	}


	public int getValue() {
		return value;
	}
}
