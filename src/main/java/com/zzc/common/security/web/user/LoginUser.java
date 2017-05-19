package com.zzc.common.security.web.user;

/**
 * 登录用户
 * Created by hadoop on 2015/11/13.
 */
public class LoginUser {

    /** 登录账户 */
    private String userName;
    /** 登录密码 */
    private String userPwd;
    /** 验证码 */
    private String captcha;
    /** 重试次数 */
    private Integer retryAmount;
    /** 账户类型 */
    private String accountType;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public Integer getRetryAmount() {
        return retryAmount;
    }

    public void setRetryAmount(Integer retryAmount) {
        this.retryAmount = retryAmount;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
