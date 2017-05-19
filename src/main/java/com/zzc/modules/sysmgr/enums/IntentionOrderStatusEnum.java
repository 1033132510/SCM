package com.zzc.modules.sysmgr.enums;

public enum IntentionOrderStatusEnum {

	/**
	 * 意向单状态：2,待支付，1待办，0已办
	 */
	TO_BE_OTHER(3),TO_BE_PAYED(2),TO_BE_PROCESSED(1), ALREADY_PROCESS(0);

	private int value;

	private IntentionOrderStatusEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
