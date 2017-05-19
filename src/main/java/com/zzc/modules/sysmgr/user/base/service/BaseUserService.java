package com.zzc.modules.sysmgr.user.base.service;

import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.sysmgr.enums.AccountTypeEnum;
import com.zzc.modules.sysmgr.user.base.entity.BaseUser;

import java.util.List;

/**
 * Created by wufan on 2015/10/31.
 */
public interface BaseUserService extends BaseCrudService<BaseUser> {

    /**
     * 根据角色编码查找用户
     * @param roleCode
     * @return
     */
    public List<BaseUser> findByRole(String roleCode);

    /**
     * 修改密码
     * @param userName
     * @param password
     */
    public void modifyPassword(String userName, String password, String accountType);

    /**
     * 检查账号的状态
     * @param userName
     * @param accountType
     * @return
     */
    public Boolean checkAccountIsExist(String userName, String accountType);

    /**
     * 根据账户名和账户类型查找账户
     * @param userName 账户名
     * @param accountType 账户类型
     * @return
     */
    public BaseUser findByUserNameAndAccountType(String userName, String accountType);

    /**
     * 根据账户名查找账户
     *
     * @param userName 账户名
     * @return
     */
    public BaseUser findByUserName(String userName);
    
    public List<String> findByRoleId(String roleId);

}
