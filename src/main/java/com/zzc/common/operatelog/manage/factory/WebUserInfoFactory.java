package com.zzc.common.operatelog.manage.factory;

import com.zzc.common.security.util.UserUtil;

/**
 * Created by IntelliJ IDEA.
 * User: wufan
 * Date: 12-12-29
 * Time: 下午5:43
 * To change this template use File | Settings | File Templates.
 */
public class WebUserInfoFactory extends IUserInfoFactory {

    @Override
    public String getOperatorId() {
        if (UserUtil.getUserFromSession() != null) {
            return UserUtil.getUserFromSession().getCurrentUserId() + "";
        }
        return null;
    }

    @Override
    public String getOperatorLoginName() {
        if (UserUtil.getUserFromSession() != null) {
            return UserUtil.getUserFromSession().getUserName() + "";
        }
        return null;
    }

    @Override
    public String getOperatorCode() {
        if (UserUtil.getUserFromSession() != null) {
            return UserUtil.getUserFromSession().getUserName() + "";
        }
        return null;
    }

    @Override
    public String getOperatorName() {
        if (UserUtil.getUserFromSession() != null) {
            return UserUtil.getUserFromSession().getDisplayUserName() + "";
        }
        return null;
    }

    @Override
    public String getOperatorIp() {
        if (UserUtil.getUserFromSession() != null) {
            return UserUtil.getUserFromSession().getLoginIp() + "";
        }
        return null;
    }
}
