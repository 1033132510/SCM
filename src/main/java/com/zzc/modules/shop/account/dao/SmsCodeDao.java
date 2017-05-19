package com.zzc.modules.shop.account.dao;


import com.zzc.core.dao.BaseDao;
import com.zzc.modules.shop.account.entity.SmsCode;

/**
 * Created by wufan on 2015/12/7.
 */
public interface SmsCodeDao extends BaseDao<SmsCode> {

    /**
     * 根据手机号码查找验证码
     * @param mobile
     * @return
     */
    public SmsCode findByMobile(String mobile);
}
