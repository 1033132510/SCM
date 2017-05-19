package com.zzc.common.activiti.formType;

import org.activiti.engine.impl.form.StringFormType;

/**
 * 大文本表单字段
 */
public class BigtextFormType extends StringFormType {

    @Override
    public String getName() {
        return "bigtext";
    }

}
