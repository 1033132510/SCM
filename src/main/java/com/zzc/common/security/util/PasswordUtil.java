package com.zzc.common.security.util;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 密码相关的工具类
 * Created by wufan on 2015/11/22.
 */
public class PasswordUtil {
    // 加密算法
    public static final String ENCRYPT_TYPE = "MD5";
    // 加密 盐
    public static final String SALT = "zzc";
    // 加密次数
    public static final int ENCRYPT_ACCOUNT = 2;
    // 账户初始密码
    public static final String INIT_PASSWORD = "888888";


    /**
     * 初始化密码
     * @return
     */
    public static String initPassword() {
        return new SimpleHash(PasswordUtil.ENCRYPT_TYPE, INIT_PASSWORD, PasswordUtil.SALT, PasswordUtil.ENCRYPT_ACCOUNT).toHex();
    }

    /**
     * 加密密码
     * @param password
     * @return
     */
    public static String encryptPassword(String password) {
        return new SimpleHash(PasswordUtil.ENCRYPT_TYPE, password, PasswordUtil.SALT, PasswordUtil.ENCRYPT_ACCOUNT).toHex();
    }

    public static void main(String[] args) {

        SimpleHash hash = new SimpleHash(PasswordUtil.ENCRYPT_TYPE, INIT_PASSWORD, PasswordUtil.SALT, PasswordUtil.ENCRYPT_ACCOUNT);
        String encodedPassword = hash.toHex();
        System.out.println(encodedPassword);

        System.out.println(PasswordUtil.encryptPassword("Zzc99999"));
    }
}
