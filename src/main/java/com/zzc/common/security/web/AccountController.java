package com.zzc.common.security.web;

import com.zzc.common.security.util.PasswordUtil;
import com.zzc.common.security.util.UserUtil;
import com.zzc.common.security.web.user.SessionUser;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.exceptions.BizException;
import com.zzc.core.exceptions.constant.ExceptionConstant;
import com.zzc.core.web.common.ResultData;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.shop.account.entity.SmsCode;
import com.zzc.modules.shop.account.service.SmsCodeService;
import com.zzc.modules.sysmgr.user.base.entity.BaseUser;
import com.zzc.modules.sysmgr.user.base.service.BaseUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wufan on 2015/12/7.
 */
@Controller
@RequestMapping("/account")
class AccountController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(AccountController.class);


	@Autowired
	private BaseUserService baseUserService;
	@Autowired
	private SmsCodeService smsCodeService;
	/**
	 * 账号管理 修改密码
	 * @return
	 */
	@RequestMapping(value = "sysmgr/password", method = RequestMethod.GET)
	public String toModifyPasswordForSysmgr() {
		SessionUser sessionUser = UserUtil.getUserFromSession();
		if (sessionUser == null) {
			return "redirect:/sysmgr/login";
		}
		return "sysmgr/account/modifyPassword";
	}

	@RequestMapping(value = "supply/password", method = RequestMethod.GET)
	public String toModifyPasswordForSupply() {
		SessionUser sessionUser = UserUtil.getUserFromSession();
		if (sessionUser == null) {
			return "redirect:/supply/login";
		}
		return "supply/account/modifyPassword";
	}

	@RequestMapping(value = "/password/shop", method = RequestMethod.GET)
	public String toModifyPasswordForShop() {
		SessionUser sessionUser = UserUtil.getUserFromSession();
		if (sessionUser == null) {
			return "redirect:/shop/login";
		}
		return "shop/account/modifyPassword";
	}
	//设置密码
	@ResponseBody
	@RequestMapping(value = "sysmgr/password", method = RequestMethod.POST)
	public Boolean modifyPasswordForSysmgr(@RequestBody BaseUser user) {
		SessionUser sessionUser = UserUtil.getUserFromSession();
		if (sessionUser == null) {
			return null;
		}
		BaseUser baseUser = baseUserService.findByUserNameAndAccountType(sessionUser.getUserName(), sessionUser.getAccountType());
		if (baseUser != null) {
			baseUser.setUserPwd(PasswordUtil.encryptPassword(user.getUserPwd()));
			baseUserService.update(baseUser);
			return true;
		}
		return false;
	}

	@ResponseBody
	@RequestMapping(value = "supply/password", method = RequestMethod.POST)
	public Boolean modifyPasswordForSupply(@RequestBody BaseUser user) {
		SessionUser sessionUser = UserUtil.getUserFromSession();
		if (sessionUser == null) {
			return null;
		}
		BaseUser baseUser = baseUserService.findByUserNameAndAccountType(sessionUser.getUserName(), sessionUser.getAccountType());
		if (baseUser != null) {
			baseUser.setUserPwd(PasswordUtil.encryptPassword(user.getUserPwd()));
			baseUserService.update(baseUser);
			return true;
		}
		return false;
	}

	@ResponseBody
	@RequestMapping(value = "shop/password", method = RequestMethod.POST)
	public Boolean modifyPasswordForShop(@RequestBody BaseUser user) {
		SessionUser sessionUser = UserUtil.getUserFromSession();
		if (sessionUser == null) {
			return null;
		}
		BaseUser baseUser = baseUserService.findByUserNameAndAccountType(sessionUser.getUserName(), sessionUser.getAccountType());
		if (baseUser != null) {
			baseUser.setUserPwd(PasswordUtil.encryptPassword(user.getUserPwd()));
			baseUserService.update(baseUser);
			return true;
		}
		return false;
	}
	//验证旧密码
	@ResponseBody
	@RequestMapping(value = "sysmgr/checkPassword", method = RequestMethod.POST)
	public Boolean checkOldPasswordForSysmgr(@RequestBody String oldPassword) {
		SessionUser sessionUser = UserUtil.getUserFromSession();
		if (sessionUser != null) {
			BaseUser baseUser = baseUserService.findByUserNameAndAccountType(sessionUser.getUserName(), sessionUser.getAccountType());
			if (baseUser != null) {
				if (StringUtils.isNotEmpty(oldPassword) && PasswordUtil.encryptPassword(oldPassword).equals(baseUser.getUserPwd())) {
					return true;
				}
				return false;
			}
		}
		return false;
	}
	@ResponseBody
	@RequestMapping(value = "supply/checkPassword", method = RequestMethod.POST)
	public Boolean checkOldPasswordForSupply(@RequestBody String oldPassword) {
		SessionUser sessionUser = UserUtil.getUserFromSession();
		if (sessionUser != null) {
			BaseUser baseUser = baseUserService.findByUserNameAndAccountType(sessionUser.getUserName(), sessionUser.getAccountType());
			if (baseUser != null) {
				if (StringUtils.isNotEmpty(oldPassword) && PasswordUtil.encryptPassword(oldPassword).equals(baseUser.getUserPwd())) {
					return true;
				}
				return false;
			}
		}
		return false;
	}

	@ResponseBody
	@RequestMapping(value = "shop/checkPassword", method = RequestMethod.POST)
	public Boolean checkOldPasswordForShop(@RequestBody String oldPassword) {
		SessionUser sessionUser = UserUtil.getUserFromSession();
		if (sessionUser != null) {
			BaseUser baseUser = baseUserService.findByUserNameAndAccountType(sessionUser.getUserName(), sessionUser.getAccountType());
			if (baseUser != null) {
				if (StringUtils.isNotEmpty(oldPassword) && PasswordUtil.encryptPassword(oldPassword).equals(baseUser.getUserPwd())) {
					return true;
				}
				return false;
			}
		}
		return false;
	}

	/******************************* 账户密码找回功能 start *************************************/

	/**
	 * 验证账号是否存在
	 *
	 * @param mobile
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/password/reset/checkAccount", method = RequestMethod.POST)
	public ResultData checkAccountExsit(@RequestParam(value = "mobile", required = true) String mobile, @RequestParam(value = "accountType", required = true) String accountType) {
		try {
			boolean checkResult = baseUserService.checkAccountIsExist(mobile, accountType);
//			if (AccountTypeEnum.purcharser.getCode().equals(accountType)) {
//				checkResult = baseUserService.checkAccountIsExist(mobile, AccountTypeEnum.purcharser);
//			} else if (AccountTypeEnum.supplier.getCode().equals(accountType)) {
//				BaseUser baseUser = baseUserService.findByUserNameAndAccountType(mobile, accountType);
//				checkResult = (baseUser != null);
//				accountType = baseUser.getType();
//			} else if (AccountTypeEnum.employee.getCode().equals(accountType)) {
//				BaseUser baseUser = baseUserService.findByUserNameAndAccountType(mobile, accountType);
//				checkResult = (baseUser != null);
//				accountType = baseUser.getType();
//			}
//			Map<String, Object> resultData = new HashMap<>();
//			resultData.put("checkResult", checkResult);
//			resultData.put("accountType", accountType);
			return new ResultData(checkResult);
		} catch (BizException e) {
			return new ResultData(false, e.getMessage(), ExceptionConstant.EXCEPTION_TYPE_BIZ);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/password/reset/sendsms", method = RequestMethod.POST)
	public ResultData sendPasswordSms(@RequestBody String mobile) {
		return new ResultData(smsCodeService.sendSms(mobile));
	}

	@ResponseBody
	@RequestMapping(value = "/password/reset/checkSmsCode", method = RequestMethod.POST)
	public ResultData checkSmsCode(@RequestBody SmsCode smsCode) {
		try {
			smsCodeService.checkSmsCodeValid(smsCode);
			return new ResultData(true);
		} catch (BizException e) {
			return new ResultData(false, e.getMessage(), ExceptionConstant.EXCEPTION_TYPE_VALIDATE);
		}
	}

	@RequestMapping(value = "/password/reset", method = RequestMethod.GET)
	public String goToResetPassword(@RequestParam(value = "accountType", required = false) String accountType, ModelMap model) {
		model.addAttribute("accountType", accountType);
		return "account/resetPassword";
	}

	@ResponseBody
	@RequestMapping(value = "/password/reset/{accountType}", method = RequestMethod.POST)
	public ResultData resetPassword(@RequestBody BaseUser baseUser, @PathVariable String accountType) {
		try {
			baseUserService.modifyPassword(baseUser.getUserName(), baseUser.getUserPwd(), accountType);
			return new ResultData(true);
		} catch (BizException e) {
			return new ResultData(false, e.getMessage(), ExceptionConstant.EXCEPTION_TYPE_BIZ);
		}
	}

	/******************************* 账户密码找回功能 end *************************************/


	/**
	 * 判断账户是否已经存在
	 *
	 * @param account
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkAccountIsExist/{account}/{accountType}", method = RequestMethod.GET)
	public ResultData checkAccountIsExist(@PathVariable String account, @PathVariable String accountType) {
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_userName", account);
		params.put("AND_EQ_type", accountType);
		params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		List<BaseUser> accounts = baseUserService.findAll(params, BaseUser.class);
		if (accounts != null && accounts.size() > 0) {
			return new ResultData(true);
		} else {
			return new ResultData(false);
		}
	}

}
