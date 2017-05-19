package com.zzc.modules.sysmgr.user.base.dao;


import com.zzc.core.dao.BaseDao;
import com.zzc.modules.sysmgr.user.base.entity.Role;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by wufan on 2015/10/29.
 */
public interface RoleDao extends BaseDao<Role> {
    /**
     * 根据角色编码查找角色
     *
     * @param roleCode
     * @return
     */
    public Role findByRoleCode(String roleCode);

    @Query("select elements(u.roleList) from BaseUser u where u.userName = :userName")
    public List<Role> findByBaseUser(@Param("userName") String userName);
    
    @Query(value = "select ur.id"
    		+ "from SYS_USER_ROLE ur where ur.role_id = :roleId and ur.id not in :userIds", nativeQuery = true)
    public List<String> findUserIdsByRoleId(@Param("userIds") List<String> userIds, @Param("roleId") String roleId);
    
    @Modifying
    @Query(value = "delete from SYS_USER_ROLE where user_id = :userId", nativeQuery = true)
    public int deleteByUserId(@Param("userId") String userId);
}