package com.zzc.common.operatelog.manage.appender;


import com.zzc.common.operatelog.manage.entity.OperateLogInfo;
import com.zzc.common.operatelog.manage.factory.IUserInfoFactory;
import com.zzc.common.operatelog.manage.factory.WebUserInfoFactory;
import com.zzc.common.operatelog.manage.service.OperateLogService;
import com.zzc.core.utils.StringTools;
import com.zzc.operatelog.common.appender.WriterAppender;
import com.zzc.operatelog.common.fields.Fields;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.Date;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: wufan
 * Date: 12-12-29
 * Time: 下午4:30
 * To change this template use File | Settings | File Templates.
 */
@Component("dbAppender")
public class DBAppender extends WriterAppender {

    private static final int MAX_INFO_LENGTH = 1000;

    @Autowired
    private OperateLogService operateLogService;

    private IUserInfoFactory userInfoFactory;

    public DBAppender() {
        super(new StringWriter());
        userInfoFactory = new WebUserInfoFactory();
    }

    @Override
    public void append(Fields fields) {
        super.append(fields);
        String result = getWriter().toString();
        //TODO 避免多次构造
        setWriter(new StringWriter());
        OperateLogInfo logInfo = this.perpareLogInfo(result);
        this.parseFieldsInfo(fields , logInfo);
        operateLogService.save(logInfo);
    }


    public OperateLogInfo perpareLogInfo(String info) {
        Map<String , String> userInfo = userInfoFactory.getUserInfo();
        OperateLogInfo logInfo = new OperateLogInfo();
        logInfo.setOperateTime(new Date());
        if (userInfo != null && userInfo.size() > 0 ) {
            if (!StringUtils.isEmpty(userInfo.get("operatorCode"))) {
                logInfo.setOperatorCode(userInfo.get("operatorCode"));
            }
            if (!StringUtils.isEmpty(userInfo.get("operatorId"))) {
                logInfo.setOperatorId(userInfo.get("operatorId") + "");
            }
            if (!StringUtils.isEmpty(userInfo.get("operatorLoginName"))) {
                logInfo.setOperatorLoginName(userInfo.get("operatorLoginName"));
            }
            if (!StringUtils.isEmpty(userInfo.get("operatorName"))) {
                logInfo.setOperatorName(userInfo.get("operatorName"));
            }
            if (!StringUtils.isEmpty(userInfo.get("operatorIp"))) {
                logInfo.setOperatorIp(userInfo.get("operatorIp"));
            }
        }
        if (!StringUtils.isEmpty(info)) {
            String result = StringTools.replaceArgValue(info);
            if (result.length() > MAX_INFO_LENGTH) {;
                result = result.substring(0 , MAX_INFO_LENGTH - 3);
                result += "...";
            }
            logInfo.setInfo(result);
        }
        return logInfo;
    }

    private void parseFieldsInfo(Fields fields , OperateLogInfo logInfo) {
        if (fields.getTargetId() != null) {
            logInfo.setTargetObjId(StringTools.replaceArgValue(fields.getTargetId().toString()));
        }
        if (!StringUtils.isEmpty(fields.getTargetName())) {
            logInfo.setTargetObjName(StringTools.replaceArgValue(fields.getTargetName()));
        }
        if (!StringUtils.isEmpty(fields.getOperateType().getName())) {
            logInfo.setOperateTypeName(fields.getOperateType().getName());
        }
        if (!StringUtils.isEmpty(fields.getDescription())) {
            logInfo.setDescription(StringTools.replaceArgValue(fields.getDescription()));
        }
        if (!StringUtils.isEmpty(fields.getOperateType().getId())) {
            logInfo.setOperateTypeId(fields.getOperateType().getId());
        }
        if (!StringUtils.isEmpty(fields.getOperateClass())) {
            logInfo.setOperateClass(fields.getOperateClass());
        }
        if (!StringUtils.isEmpty(fields.getOperateMethod())) {
            logInfo.setOperateMethod(fields.getOperateMethod());
        }
        if (!StringUtils.isEmpty(fields.getTargetClassName())) {
            logInfo.setTargetClassName(fields.getTargetClassName());
        }
    }

    @Override
    public String getFormat() {
        //first format
//        return "描述: $fields.description,\t目标对象ID: $fields.targetId,\t目标对象名称: $fields.targetName,\t操作类型: $fields.operateType ,\t执行消息: $fields.evaledText";
        //only return info
        return "$fields.evaledText";
    }

}