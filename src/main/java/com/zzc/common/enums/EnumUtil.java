package com.zzc.common.enums;

import com.zzc.core.utils.JsonUtils;
import com.zzc.modules.sysmgr.enums.SexEnum;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wufan on 2015/11/14.
 */
public class EnumUtil {
    public static final String GET_CODE = "getCode";
    public static final String GET_NAME = "getName";

    public static <T> Map<String, String> toMap(Class<T> enumClass) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        try {
            Method getCode = enumClass.getMethod(GET_CODE);
            Method getName = enumClass.getMethod(GET_NAME);
            // 取到enum的所有实例
            Object[] enmes = enumClass.getEnumConstants();
            for (Object obj : enmes) {
                map.put((String) getCode.invoke(obj), (String) getName.invoke(obj));
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return map;
    }


    public static void main(String[] args) {
        Map test = EnumUtil.toMap(SexEnum.class);
        System.out.println(JsonUtils.toJson(test));
        System.out.println(SexEnum.man.getCode());
    }
}
