package com.zzc.common;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zzc.common.sms.SmsService;
import com.zzc.common.sms.SmsWithoutPlaceholder;
import com.zzc.core.BaseServiceTest;

public class SmsServiceTest extends BaseServiceTest {

	@Autowired
	private SmsService smsService;

	@Test
	public void sendSmsTest() {
		SmsWithoutPlaceholder sms = new SmsWithoutPlaceholder("18301256703",
				"111");
		smsService.sendSms(sms);

	}
}
