package com.zzc.modules.shop.account.service.impl;

import com.zzc.core.dao.BaseDao;
import com.zzc.core.exceptions.BizException;
import com.zzc.core.messageManagement.sms.utils.SMSUtils;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.core.utils.StringTools;
import com.zzc.modules.shop.account.dao.SmsCodeDao;
import com.zzc.modules.shop.account.entity.SmsCode;
import com.zzc.modules.shop.account.service.SmsCodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by wufan on 2015/12/7.
 */
@Service("smsCodeService")
public class SmsCodeServiceImpl extends BaseCrudServiceImpl<SmsCode> implements SmsCodeService {

    public static final long CODE_VALID_TIME = 30L;

    private SmsCodeDao smsCodeDao;

    @Autowired
    public SmsCodeServiceImpl(BaseDao<SmsCode> smsCodeDao) {
        super(smsCodeDao);
        this.smsCodeDao = (SmsCodeDao) smsCodeDao;
    }

    @Override
    public void checkSmsCodeValid(SmsCode smsCode) {
        SmsCode persistSmsCode = smsCodeDao.findByMobile(smsCode.getMobile());
        if (persistSmsCode != null) {
            long minute = (new Date().getTime() - persistSmsCode.getCreateTime().getTime())/(24 * 60 * 1000);
            if (CODE_VALID_TIME < minute) {
                throw new BizException("验证码已失效，请重新获取");
            } else if (!persistSmsCode.getCode().equals(smsCode.getCode())) {
                throw new BizException("验证码输入错误");
            }
            smsCodeDao.delete(persistSmsCode);
        } else {
            throw new BizException("请先获取验证码");
        }
    }

    @Override
    public boolean sendSms(String mobile) {
        Boolean sendResult = Boolean.FALSE;

        if (StringUtils.isNotEmpty(mobile)) {
            SmsCode persistSmsCode = smsCodeDao.findByMobile(mobile);
            if (persistSmsCode != null) {
                smsCodeDao.delete(persistSmsCode);
            }

            String code = StringTools.generateRandomString(4);
            SMSUtils.sendPasswordSms(mobile, code);

            SmsCode smsCode = new SmsCode();
            smsCode.setCode(code);
            smsCode.setMobile(mobile);
            smsCodeDao.save(smsCode);
            sendResult =  Boolean.TRUE;
        }

        return sendResult;
    }
}

