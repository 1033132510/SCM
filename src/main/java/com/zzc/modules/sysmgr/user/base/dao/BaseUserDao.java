package com.zzc.modules.sysmgr.user.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zzc.core.dao.BaseDao;
import com.zzc.modules.sysmgr.user.base.entity.BaseUser;

/**
 * Created by wufan on 2015/10/29.
 */
public interface BaseUserDao extends BaseDao<BaseUser> {

	/**
	 * 根据账户名和账户类型查找账户
	 *
	 * @param userName 账户名
	 * @param type     账户类型
	 * @return
	 */
	public BaseUser findByUserNameAndTypeAndStatus(String userName, String type, Integer status);

	/**
	 * 根据账户名查找账户
	 *
	 * @param userName 账户名
	 * @return
	 */
	public List<BaseUser> findByUserName(String userName);

	@Query(value = "select a.id from SYS_ACCOUNT a left join SYS_USER_ROLE ur on a.id = ur.user_id where a.status = 1 and ur.role_id = :roleId", nativeQuery = true)
	public List<String> findByRoleId(@Param("roleId") String roleId);

}