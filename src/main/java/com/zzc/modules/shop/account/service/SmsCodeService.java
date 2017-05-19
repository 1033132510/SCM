package com.zzc.modules.shop.account.service;

import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.shop.account.entity.SmsCode;

/**
 * Created by wufan on 2015/12/7.
 */
public interface SmsCodeService extends BaseCrudService<SmsCode> {

    /**
     * 检查验证码的有效性
     * @param smsCode
     * @return
     */
    public void checkSmsCodeValid(SmsCode smsCode);

    /**
     * 发送短信校验码
     * @param mobile
     * @return
     */
    public boolean sendSms(String mobile);
}
