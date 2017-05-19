package com.zzc.modules.sysmgr.user.employee.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.zzc.modules.sysmgr.user.base.entity.BaseUser;
import com.zzc.modules.sysmgr.user.employee.dao.UserRoleDao;

/**
 * Created by wufan on 2015/11/4.
 */
@Repository("userRoleDao")
public class UserRoleDaoImpl implements UserRoleDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<BaseUser> findByRole(String roleCode) {
		String sql = "select tu.user_name, tu.user_pwd, tu.description from ts_user tu, ts_role tr, ts_user_role tur where tu.id = tur.user_id and tur.role_id = tr.id "
				+ "and tr.role_code = ?";

		return jdbcTemplate.query(sql, new String[] { roleCode }, new RowMapper<BaseUser>() {
			public BaseUser mapRow(ResultSet rs, int rowNum) throws SQLException {
				BaseUser user = new BaseUser();
				user.setUserName(rs.getString("user_name"));
				user.setUserPwd(rs.getString("user_pwd"));
				user.setDescription(rs.getString("description"));
				return user;
			}
		});
	}

}