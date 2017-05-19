package com.zzc.common.activiti.formType;

import org.activiti.engine.form.AbstractFormType;

/**
 * Javascript表单字段
 */
public class JavascriptFormType extends AbstractFormType {

    @Override
    public String getName() {
        return "javascript";
    }

    @Override
    public Object convertFormValueToModelValue(String propertyValue) {
        return propertyValue;
    }

    @Override
    public String convertModelValueToFormValue(Object modelValue) {
        return (String) modelValue;
    }
}
