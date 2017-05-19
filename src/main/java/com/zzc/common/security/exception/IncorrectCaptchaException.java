package com.zzc.common.security.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 验证码处理异常
 * Created by wufan on 2015/11/13.
 */
public class IncorrectCaptchaException  extends AuthenticationException {

    public IncorrectCaptchaException() {
        super();
    }

    public IncorrectCaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectCaptchaException(String message) {
        super(message);
    }

    public IncorrectCaptchaException(Throwable cause) {
        super(cause);
    }
}

