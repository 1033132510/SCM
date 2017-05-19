package com.zzc.common.activiti.formType;

import org.activiti.engine.form.AbstractFormType;
import org.apache.commons.lang3.ObjectUtils;

/**
 * double表单字段类型
 */
public class DoubleFormType extends AbstractFormType {

    @Override
    public String getName() {
        return "double";
    }

    @Override
    public Object convertFormValueToModelValue(String propertyValue) {
        return new Double(propertyValue);
    }

    @Override
    public String convertModelValueToFormValue(Object modelValue) {
        return ObjectUtils.toString(modelValue);
    }

}
