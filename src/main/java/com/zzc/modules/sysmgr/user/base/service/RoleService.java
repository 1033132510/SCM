package com.zzc.modules.sysmgr.user.base.service;


import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.sysmgr.user.base.entity.BaseUser;
import com.zzc.modules.sysmgr.user.base.entity.Role;

import java.util.List;

/**
 * Created by wufan on 2015/11/1.
 */
public interface RoleService extends BaseCrudService<Role> {
    /**
     * 根据角色编码查找角色
     *
     * @param roleCode
     * @return
     */
    public Role findByRoleCode(String roleCode);

    /**
     * 根据用户查找该用户所拥有的角色编码
     * @param baseUser
     * @return
     */
    public List<Role> findRolesByUser(BaseUser baseUser);
    
    public List<String> findUserIdsByRoleId(List<String> userIds, String roleId);
    
}