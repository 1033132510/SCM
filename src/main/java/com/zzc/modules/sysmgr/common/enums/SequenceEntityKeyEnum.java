package com.zzc.modules.sysmgr.common.enums;

/**
 * 
 * @author apple
 *
 */
public enum SequenceEntityKeyEnum {

	level_1("level_1"), level_2("level_2"), level_3("level_3"), product_code("product_code");

	private String text;

	private SequenceEntityKeyEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
