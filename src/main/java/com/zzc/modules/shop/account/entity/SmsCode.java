package com.zzc.modules.shop.account.entity;

import com.zzc.core.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by wufan on 2015/12/7.
 */
@Entity
@Table(name = "SYS_SMS_CODE")
public class SmsCode extends BaseEntity implements Serializable {

    @Column(name = "code", length = 4)
    private String code;

    @Column(name = "mobile", length = 20)
    private String mobile;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
