package com.zzc.common.security.service;

import com.zzc.common.security.web.CustomPasswordCredentialsMatcher;
import com.zzc.common.security.web.UsernamePasswordCaptchaToken;
import com.zzc.modules.sysmgr.constant.SystemConstants;
import com.zzc.modules.sysmgr.enums.AccountTypeEnum;
import com.zzc.modules.sysmgr.user.base.entity.BaseUser;
import com.zzc.modules.sysmgr.user.base.service.BaseUserService;
import com.zzc.modules.sysmgr.user.base.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


/**
 * 安全实体数据源
 * Created by wufan on 2015/10/31.
 */
public class ShiroRealm extends AuthorizingRealm {

	// 账户和账户类型间隔符
	private static final String SPACE_MARK = "/";

	@Autowired
	private BaseUserService baseUserService;

	@Autowired
	private RoleService roleService;

	@PostConstruct
	public void initCredentialsMatcher() {
		setCredentialsMatcher(new CustomPasswordCredentialsMatcher());
	}

	/**
	 * 权限认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		// 获取登录的用户名
		String loginParamsStr = (String) principalCollection.fromRealm(getName()).iterator().next();
		if (StringUtils.isNotEmpty(loginParamsStr)) {
			String[] loginParams = loginParamsStr.split(SPACE_MARK);
			if (loginParams.length == 2) {
				String loginName = loginParams[0];
				String accountType = loginParams[1];
				BaseUser user = baseUserService.findByUserNameAndAccountType(loginName, accountType);
				user.setRoleList(roleService.findRolesByUser(user));
				if (user != null) {
					SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
					Set<String> roleCodes = user.getRoleCodes();
					if (AccountTypeEnum.employee.getCode().equals(user.getType())) {
						roleCodes.add(AccountTypeEnum.employee.toString());
					} else if (AccountTypeEnum.purcharser.getCode().equals(user.getType())) {
						roleCodes.add(AccountTypeEnum.purcharser.toString());
					} else if (AccountTypeEnum.supplier.getCode().equals(user.getType())) {
						roleCodes.clear();
						roleCodes.add(AccountTypeEnum.supplier.toString());
						roleCodes.add(SystemConstants.SUPPLIER_OPERATOR);
					}
					info.setRoles(roleCodes);

					return info;
				}
			}
		}

		return null;
	}

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken) throws AuthenticationException {

		UsernamePasswordCaptchaToken token = (UsernamePasswordCaptchaToken) authenticationToken;

		BaseUser user = baseUserService.findByUserNameAndAccountType(token.getUsername(), token.getAccountType());
		if (user != null) {
			return new SimpleAuthenticationInfo(user.getUserName() + SPACE_MARK + user.getType(), user.getUserPwd(), getName());
		}
		return null;
	}
}
