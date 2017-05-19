package com.zzc.modules.sysmgr.user.base.service.impl;

import com.zzc.common.security.util.PasswordUtil;
import com.zzc.core.dao.BaseDao;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.exceptions.BizException;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.sysmgr.enums.AccountTypeEnum;
import com.zzc.modules.sysmgr.user.base.dao.BaseUserDao;
import com.zzc.modules.sysmgr.user.base.entity.BaseUser;
import com.zzc.modules.sysmgr.user.base.service.BaseUserService;
import com.zzc.modules.sysmgr.user.employee.dao.UserRoleDao;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wufan on 2015/10/31.
 */
@Service("baseUserService")
public class BaseUserServiceImpl extends BaseCrudServiceImpl<BaseUser> implements BaseUserService {

	private BaseUserDao baseUserDao;

	@Autowired
	public BaseUserServiceImpl(BaseDao<BaseUser> baseUserDao) {
		super(baseUserDao);
		this.baseUserDao = (BaseUserDao) baseUserDao;
	}

	@Autowired
	private UserRoleDao userRoleDao;

	/**
	 * 根据角色查找用户
	 *
	 * @param roleCode
	 * @return
	 */
	@Override
	public List<BaseUser> findByRole(String roleCode) {
		return userRoleDao.findByRole(roleCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void modifyPassword(String userName, String password, String accountType) {
		BaseUser baseUser = null;
		if (StringUtils.isNotEmpty(accountType)) {
			if (AccountTypeEnum.purcharser.getCode().equals(accountType)) {
				baseUser = baseUserDao.findByUserNameAndTypeAndStatus(userName, AccountTypeEnum.purcharser.getCode(), CommonStatusEnum.有效.getValue());
			} else if (AccountTypeEnum.employee.getCode().equals(accountType)) {
				baseUser = baseUserDao.findByUserNameAndTypeAndStatus(userName, AccountTypeEnum.employee.getCode(), CommonStatusEnum.有效.getValue());
			} else if (AccountTypeEnum.supplier.getCode().equals(accountType)) {
				baseUser = baseUserDao.findByUserNameAndTypeAndStatus(userName, AccountTypeEnum.supplier.getCode(), CommonStatusEnum.有效.getValue());
			} else {
				throw new BizException("系统没有找到对应的账户类型");
			}
		}

		if (baseUser != null) {
			baseUser.setUserPwd(PasswordUtil.encryptPassword(password));
			baseUserDao.save(baseUser);
		} else {
			throw new BizException("用户不存在");
		}
	}

	@Override
	public Boolean checkAccountIsExist(String userName, String accountType) {
		BaseUser baseUser = baseUserDao.findByUserNameAndTypeAndStatus(userName, accountType, CommonStatusEnum.有效.getValue());
		if (baseUser != null) {
			if (baseUser.getStatus().intValue() == CommonStatusEnum.有效.getValue().intValue()) {
				return Boolean.TRUE;
			} else {
				throw new BizException("该用户已经失效");
			}
		} else {
			throw new BizException("该用户在系统中不存在");
		}
	}

	@Override
	public BaseUser findByUserNameAndAccountType(String userName, String accountType) {
		BaseUser baseUser = baseUserDao.findByUserNameAndTypeAndStatus(userName, accountType, CommonStatusEnum.有效.getValue());

		if (baseUser != null) {
			// 如果用户失效，给出提示
			if (!isValidBaseUser(baseUser)) {
				throw new BizException("该用户已经失效");
			}
		} else {
			throw new BizException("该用户在系统中不存在");
		}

		return baseUser;
	}

	@Override
	public BaseUser findByUserName(String userName) {
		List<BaseUser> baseUsers = baseUserDao.findByUserName(userName);

		if (CollectionUtils.isEmpty(baseUsers)) {
			throw new BizException("该用户在系统中不存在");
		}
		BaseUser resultBaseUser = null;
		for (BaseUser baseUser : baseUsers) {
			if (AccountTypeEnum.purcharser.getCode().equals(baseUser.getType())) {
				continue;
			}
			if (!isValidBaseUser(baseUser)) {
				throw new BizException("该用户已经失效");
			}
			resultBaseUser = baseUser;
		}
		if (resultBaseUser == null) {
			throw new BizException("该用户不存在");
		}
		return resultBaseUser;
	}

	/**
	 * 验证账户是否有效
	 *
	 * @param baseUser
	 * @return
	 */
	private boolean isValidBaseUser(BaseUser baseUser) {
		boolean validBaseUser = true;

		if (CommonStatusEnum.无效.getValue().intValue() == baseUser.getStatus().intValue()) {
			validBaseUser = false;
		}
		return validBaseUser;
	}
	
	@Override
	public List<String> findByRoleId(String roleId) {
		return baseUserDao.findByRoleId(roleId);
	}

}