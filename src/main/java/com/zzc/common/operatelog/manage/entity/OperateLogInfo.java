package com.zzc.common.operatelog.manage.entity;


import com.zzc.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: wufan
 * Date: 12-12-29
 * Time: 下午4:56
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "SYS_OPERATE_LOG")
public class OperateLogInfo extends BaseEntity {

    @Column(name = "OPERATOR_ID", length = 32)
    private String operatorId;

    @Column(name = "OPERATOR_LOGIN_NAME", length = 30)
    private String operatorLoginName;

    @Column(name = "OPERATOR_CODE", length = 30)
    private String operatorCode;

    @Column(name = "OPERATOR_NAME", length = 30)
    private String operatorName;

    @Column(name = "OPERATOR_IP", length = 20)
    private String operatorIp;

    @Column(name = "OPERATE_TIME")
    private Date operateTime;

    @Column(name = "OPERATE_TYPE_NAME", length = 20)
    private String operateTypeName;

    @Column(name = "OPERATE_TYPE_ID", length = 10)
    private String operateTypeId;

    @Column(name = "OPERATE_METHOD", length = 20)
    private String operateMethod;

    @Column(name = "OPERATE_CLASS", length = 100)
    private String operateClass;

    @Column(name = "TARGET_OBJ_ID", length = 50)
    private String targetObjId;

    @Column(name = "TARGET_OBJ_NAME", length = 100)
    private String targetObjName;

    @Column(name = "TARGET_CLASS_NAME", length = 100)
    private String targetClassName;

    @Column(name = "DESCRIPTION", length = 20)
    private String description;

    @Column(name = "INFO", length = 4000)
    private String info;

    @Column(name = "EXTEND", length = 400)
    private String extend;

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorLoginName() {
        return operatorLoginName;
    }

    public void setOperatorLoginName(String operatorLoginName) {
        this.operatorLoginName = operatorLoginName;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorIp() {
        return operatorIp;
    }

    public void setOperatorIp(String operatorIp) {
        this.operatorIp = operatorIp;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateMethod() {
        return operateMethod;
    }

    public void setOperateMethod(String operateMethod) {
        this.operateMethod = operateMethod;
    }

    public String getTargetObjId() {
        return targetObjId;
    }

    public void setTargetObjId(String targetObjId) {
        this.targetObjId = targetObjId;
    }

    public String getTargetObjName() {
        return targetObjName;
    }

    public void setTargetObjName(String targetObjName) {
        this.targetObjName = targetObjName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getOperateTypeName() {
        return operateTypeName;
    }

    public void setOperateTypeName(String operateTypeName) {
        this.operateTypeName = operateTypeName;
    }

    public String getOperateTypeId() {
        return operateTypeId;
    }

    public void setOperateTypeId(String operateTypeId) {
        this.operateTypeId = operateTypeId;
    }

    public String getTargetClassName() {
        return targetClassName;
    }

    public void setTargetClassName(String targetClassName) {
        this.targetClassName = targetClassName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperateClass() {
        return operateClass;
    }

    public void setOperateClass(String operateClass) {
        this.operateClass = operateClass;
    }
}
