package com.zzc.common.sms;

/**
 * 适用于没有占位符短信模板,其他格式模板父类
 * 
 * @author chenjiahai
 *
 */
public class SmsWithoutPlaceholder {

	private String mobile;

	private String key;

	public SmsWithoutPlaceholder() {
	}

	public SmsWithoutPlaceholder(String mobile, String key) {
		this.mobile = mobile;
		this.key = key;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
