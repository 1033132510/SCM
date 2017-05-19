package com.zzc.common.security.web;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 自定义包含验证码和账户类型的token
 * Created by wufan on 2015/11/13.
 */
public class UsernamePasswordCaptchaToken extends UsernamePasswordToken {
	// 验证码字符串
	private String captcha;
	// 账户类型
	private String accountType;

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public UsernamePasswordCaptchaToken(String username, String password, String captcha) {
		super(username, password);
		this.captcha = captcha;
	}

	public UsernamePasswordCaptchaToken(String username, String password, String captcha, String accountType) {
		super(username, password);
		this.captcha = captcha;
		this.accountType = accountType;
	}

	public UsernamePasswordCaptchaToken(String username, char[] password,
	                                    boolean rememberMe, String host, String captcha, String accountType) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
		this.accountType = accountType;
	}

}