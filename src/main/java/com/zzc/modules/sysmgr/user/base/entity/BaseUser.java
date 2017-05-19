package com.zzc.modules.sysmgr.user.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zzc.core.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 系统用户基类 Created by wufan on 2015/10/31.
 */
@Entity
@Table(name = "SYS_ACCOUNT")
@Inheritance(strategy = InheritanceType.JOINED)
public class BaseUser extends BaseEntity implements Serializable {

	@Column(name = "user_name", nullable = false, length = 100)
	private String userName;

	@Column(name = "user_pwd", nullable = false, length = 200)
	private String userPwd;

	/**
	 * 用户类型；1：系统用户；2：供应商；3：采购商
	 */
	@Column(name = "type", length = 1)
	private String type;

	@Column(name = "description", length = 200)
	private String description;

	@Column(name = "last_login_time")
	private Date lastLoginTime;

	@Column(name = "last_login_ip", length = 50)
	private String lastLoginIp;

	// @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	// @JoinTable(name = "SYS_USER_ROLE", joinColumns = {@JoinColumn(name =
	// "user_id")},
	// inverseJoinColumns = {@JoinColumn(name = "role_id")})
	@JsonIgnore
	@ManyToMany(mappedBy = "userList", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<Role> roleList;

	/**
	 * default constructor
	 */
	public BaseUser() {
	}

	/**
	 * minimal constructor
	 */
	public BaseUser(String userName, String userPwd) {
		this.userName = userName;
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserPwd() {
		return this.userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return this.lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	@JsonIgnore
	public Set<String> getRoleCodes () {
		Set<String> roleCodes = new HashSet<>();
		for (Role role : roleList) {
			roleCodes.add(role.getRoleCode());
		}
		return roleCodes;
	}

}