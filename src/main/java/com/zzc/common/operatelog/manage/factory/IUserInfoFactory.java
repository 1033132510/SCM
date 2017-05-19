package com.zzc.common.operatelog.manage.factory;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: wufan
 * Date: 12-12-29
 * Time: 下午5:42
 */
public abstract class IUserInfoFactory {

    public Map<String , String> getUserInfo() {
        Map<String , String> userInfoMap = new HashMap<String, String>();
        userInfoMap.put("operatorId" , getOperatorId());
        userInfoMap.put("operatorLoginName" , getOperatorLoginName());
        userInfoMap.put("operatorCode" , getOperatorCode());
        userInfoMap.put("operatorName" , getOperatorName());
        userInfoMap.put("operatorIp" , getOperatorIp());
        return userInfoMap;
    }

    public abstract String getOperatorId();

    public abstract String getOperatorLoginName();

    public abstract String getOperatorCode();

    public abstract String getOperatorName();

    public abstract String getOperatorIp();
    
}
