package com.zzc.common.security.web.user;

import com.zzc.modules.sysmgr.user.base.entity.BaseUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 存储用户基本信息的session对象
 * Created by hadoop on 2015/11/12.
 */
public class SessionUser extends BaseUser {

    /**
     * 用于页面显示的用户名；
     * 后台管理显示的员工姓名，采购商显示的公司名等
     * 具体指在登录的时候根据实际情况设置
     */
    private String displayUserName;


    /** 当前用户的类型 */
    private String accountType;
    /** 当前用户的ID */
    private String currentUserId;
    /**  所属组织ID */
    private String currentOrgId;
    /** 所属组织名称 */
    private String currentOrgName;
    /**  所属岗位ID */
    private String currentPositionId;
    /** 所属岗位名称 */
    private String currentPositionName;
    /** 所属公司名称 */
    private String currentCompanyName;
    /** 手机号 */
    private String mobile;
    /** 邮箱 */
    private String email;
    /** 登录IP */
    private String loginIp;

    private Set<String> roleCodes;
    private Set<String> roleIds;

    /**
     * 自定义属性
     */
    private Map<String, Object> userDefinedParams = new HashMap<>();


    public String getDisplayUserName() {
        return displayUserName;
    }

    public void setDisplayUserName(String displayUserName) {
        this.displayUserName = displayUserName;
    }

    public String getCurrentOrgId() {
        return currentOrgId;
    }

    public void setCurrentOrgId(String currentOrgId) {
        this.currentOrgId = currentOrgId;
    }

    public String getCurrentOrgName() {
        return currentOrgName;
    }

    public void setCurrentOrgName(String currentOrgName) {
        this.currentOrgName = currentOrgName;
    }

    public String getCurrentPositionName() {
        return currentPositionName;
    }

    public void setCurrentPositionName(String currentPositionName) {
        this.currentPositionName = currentPositionName;
    }

    public String getCurrentCompanyName() {
        return currentCompanyName;
    }

    public void setCurrentCompanyName(String currentCompanyName) {
        this.currentCompanyName = currentCompanyName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(Set<String> roleCodes) {
        this.roleCodes = roleCodes;
    }

    public Set<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<String> roleIds) {
        this.roleIds = roleIds;
    }

    public String getCurrentPositionId() {
        return currentPositionId;
    }

    public void setCurrentPositionId(String currentPositionId) {
        this.currentPositionId = currentPositionId;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Map<String, Object> getUserDefinedParams() {
        return userDefinedParams;
    }

    public void setUserDefinedParams(Map<String, Object> userDefinedParams) {
        this.userDefinedParams = userDefinedParams;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
}
