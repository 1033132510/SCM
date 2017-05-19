package com.zzc.common.security.service;

import java.util.ArrayList;
import java.util.List;

import com.zzc.common.security.web.user.SessionUser;
import com.zzc.modules.sysmgr.user.base.service.BaseUserService;
import com.zzc.modules.sysmgr.user.employee.service.EmployeeService;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * 用户工具类 - TODO 根据需要看是否需要进一步扩展还是废弃
 * <p/>
 * Created by wufan on 2015/10/31.
 */
public class UserUtil {

    @Autowired
    private BaseUserService baseUserService;

    @Autowired
    private EmployeeService employeeService;


    public static final String USER = "session_user";
    
    public static final String NO_DISCOUNT_CODE = "1";

    /**
     * 设置用户到session
     *
     * @param sessionUser
     */
    public static void saveUserToSession(SessionUser sessionUser) {
        SecurityUtils.getSubject().getSession().setAttribute(USER, sessionUser);
    }

    /**
     * 从Session获取当前用户信息
     *
     * @return
     */
    public static SessionUser getUserFromSession() {
        Object attribute = SecurityUtils.getSubject().getSession().getAttribute(USER);
        return attribute == null ? null : (SessionUser) attribute;
    }
    
    /**
     * 获取该用户对应的折扣等级
     * @return
     */
    public static List<String> getPriceKindModelCodeListByCustomerLevel() {
		Integer levelCode = (Integer) UserUtil.getUserFromSession().getUserDefinedParams().get("purchaserLevel");
		List<String> priceKindeModelIds = new ArrayList<String>();
		String discountCode = null;
		if (null == levelCode) {
			levelCode = 1;
		}
		switch (levelCode) {
		case 1:
			discountCode = "3";
			break;
		case 2:
			discountCode = "4";
			break;
		case 3:
			discountCode = "5";
			break;
		}
		priceKindeModelIds.add(discountCode);
		priceKindeModelIds.add(NO_DISCOUNT_CODE);
		return priceKindeModelIds;
	}

}