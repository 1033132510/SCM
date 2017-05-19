package com.zzc.common.activiti.util;

import java.util.Date;

/**
 * 流程表单属性数据类型
 * <p/>
 * Created by wufan on 2015/10/31.
 */
public enum PropertyType {
    S(String.class), I(Integer.class), L(Long.class), F(Float.class), N(Double.class), D(Date.class), SD(java.sql.Date.class), B(
            Boolean.class);

    private Class<?> clazz;

    private PropertyType(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getValue() {
        return clazz;
    }
}