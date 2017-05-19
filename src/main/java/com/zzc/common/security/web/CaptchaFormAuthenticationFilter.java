package com.zzc.common.security.web;

import com.zzc.core.exceptions.constant.ExceptionConstant;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.common.ResultData;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * shiro验证码过滤器
 * Created by wufan on 2015/11/13.
 */
public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(CaptchaAjaxAuthenticationFilter.class);

    /*@Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if(this.isLoginRequest(request, response)) {
            if(this.isLoginSubmission(request, response)) {
                if(log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }

                return this.executeLogin(request, response);
            } else {
                if(log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }

                return true;
            }
        } else {
            if(log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the Authentication url [" + this.getLoginUrl() + "]");
            }

            if (((HttpServletRequest)request).getHeader("ajax") != null) {
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(JsonUtils.toJson(new ResultData(false, "session失效", ExceptionConstant.EXCEPTION_TYPE_SESSION_INVALID)));
            } else {
                 this.saveRequestAndRedirectToLogin(request, response);
            }
            return false;
        }
    }*/
    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

    private String captchaParam = DEFAULT_CAPTCHA_PARAM;

    public String getCaptchaParam() {

        return captchaParam;

    }

    protected String getCaptcha(ServletRequest request) {

        return WebUtils.getCleanParam(request, getCaptchaParam());

    }

    protected AuthenticationToken createToken(

            ServletRequest request, ServletResponse response) {

        String username = getUsername(request);

        String password = getPassword(request);

        String captcha = getCaptcha(request);

        boolean rememberMe = isRememberMe(request);

        String host = getHost(request);

        return new UsernamePasswordCaptchaToken(username,
                null, rememberMe, host, captcha, "");

    }
}
