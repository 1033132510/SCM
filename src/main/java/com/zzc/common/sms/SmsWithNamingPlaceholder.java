package com.zzc.common.sms;

import java.util.Map;

/**
 * 适用于{name}、{mobile}命名格式占位符模板
 * 
 * @author chenjiahai
 *
 */
public class SmsWithNamingPlaceholder extends SmsWithoutPlaceholder {

	private Map<String, Object> params;

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public SmsWithNamingPlaceholder() {
	}

	public SmsWithNamingPlaceholder(String mobile, String key,
			Map<String, Object> params) {
		super(mobile, key);
		this.params = params;
	}

}
