package com.zzc.common.security.util;

import com.zzc.common.security.web.user.SessionUser;
import org.apache.shiro.SecurityUtils;


/**
 * 用户session工具类
 * <p/>
 * Created by wufan on 2015/10/31.
 */
public class UserUtil {

    public static final String USER = "session_user";

    /**
     * 设置用户到session
     *
     * @param sessionUser
     */
    public static void saveUserToSession(SessionUser sessionUser) {
        SecurityUtils.getSubject().getSession().setAttribute(USER, sessionUser);
    }

    /**
     * 从Session获取当前用户信息
     *
     * @return
     */
    public static SessionUser getUserFromSession() {
        Object attribute = SecurityUtils.getSubject().getSession().getAttribute(USER);
        return attribute == null ? null : (SessionUser) attribute;
    }


    public static void main(String[] args) {
        System.out.println(PasswordUtil.initPassword());
    }

}
