package com.zzc.common.sms;

/**
 * 适用于{0}、{1}索引格式占位符的模板
 * 
 * @author chenjiahai
 *
 */
public class SmsWithIndexPlaceholder extends SmsWithoutPlaceholder {

	private String[] values;

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public SmsWithIndexPlaceholder(String mobile, String key, String[] values) {
		super(mobile, key);
		this.values = values;
	}
}
