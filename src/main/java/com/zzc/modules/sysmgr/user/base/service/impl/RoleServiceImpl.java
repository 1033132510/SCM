package com.zzc.modules.sysmgr.user.base.service.impl;

import com.zzc.core.dao.BaseDao;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.sysmgr.user.base.entity.BaseUser;
import com.zzc.modules.sysmgr.user.base.entity.Role;
import com.zzc.modules.sysmgr.user.base.dao.RoleDao;
import com.zzc.modules.sysmgr.user.base.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wufan on 2015/11/1.
 */
@Service("roleService")
public class RoleServiceImpl extends BaseCrudServiceImpl<Role> implements RoleService {

    private RoleDao roleDao;
    
    @Autowired
    public RoleServiceImpl(BaseDao<Role> roleDao) {
        super(roleDao);
        this.roleDao = (RoleDao) roleDao;
    }

    @Override
    public Role findByRoleCode(String roleCode) {
        return roleDao.findByRoleCode(roleCode);
    }

    @Override
    public List<Role> findRolesByUser(BaseUser baseUser) {
        return roleDao.findByBaseUser(baseUser.getUserName());
    }
    
    @Override
    public List<String> findUserIdsByRoleId(List<String> userIds, String roleId) {
    	return roleDao.findUserIdsByRoleId(userIds, roleId);
    }
    
}