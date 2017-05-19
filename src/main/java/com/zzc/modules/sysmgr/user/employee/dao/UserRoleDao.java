package com.zzc.modules.sysmgr.user.employee.dao;

import com.zzc.modules.sysmgr.user.base.entity.BaseUser;

import java.util.List;

/**
 * Created by wufan on 2015/11/4.
 */
public interface UserRoleDao {

    public List<BaseUser> findByRole(String roleCode);
    
}
