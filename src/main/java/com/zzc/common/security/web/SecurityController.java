package com.zzc.common.security.web;

import com.zzc.common.security.constant.SecurityContants;
import com.zzc.common.security.exception.IncorrectCaptchaException;
import com.zzc.common.security.util.UserUtil;
import com.zzc.common.security.web.user.LoginUser;
import com.zzc.common.security.web.user.SessionUser;
import com.zzc.core.exceptions.BizException;
import com.zzc.core.exceptions.constant.ExceptionConstant;
import com.zzc.core.web.common.ResultData;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.sysmgr.enums.AccountTypeEnum;
import com.zzc.modules.sysmgr.user.base.entity.BaseUser;
import com.zzc.modules.sysmgr.user.base.entity.CompanyOrg;
import com.zzc.modules.sysmgr.user.base.entity.Position;
import com.zzc.modules.sysmgr.user.base.entity.UserOrgPositionRelation;
import com.zzc.modules.sysmgr.user.base.service.BaseUserService;
import com.zzc.modules.sysmgr.user.base.service.UserOrgPositionRelationService;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.purchaser.entity.PurchaserUser;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 安全控制器 - 登入，登出 Created by wufan on 2015/10/31.
 */
@Controller
public class SecurityController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(SecurityController.class);

	public static final int RETRY_AMOUNT = 3;

	@Autowired
	private BaseUserService baseUserService;

	@Autowired
	private UserOrgPositionRelationService userOrgPositionRelationService;

	/**
	 * 进入管理后台
	 *
	 * @return
	 */
	@RequestMapping(value = "/sysmgr", method = RequestMethod.GET)
	public String sysmgrIndex() {
		return "sysmgr/index";
	}

	/**
	 * 进入供应商平台管理后台
	 *
	 * @return
	 */
	@RequestMapping(value = "/supply/index", method = RequestMethod.GET)
	public String supplyIndex() {
		return "sysmgr/index";
	}

	/**
	 * 供应链进入后台登录页面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sysmgr/login", method = RequestMethod.GET)
	public String loginForm(Model model) {
		if (!model.containsAttribute("user")) {
			model.addAttribute("user", new LoginUser());
		}
		return "sysmgr/login";
	}

	/**
	 * 供应商进入后台登录页面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/supply/login", method = RequestMethod.GET)
	public String supplyLoginForm(Model model) {
		if (!model.containsAttribute("user")) {
			model.addAttribute("user", new LoginUser());
		}
		return "supply/login";
	}

	/**
	 * 后台供应链登录
	 *
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sysmgr/login", method = RequestMethod.POST)
	public ResultData systemLogin(@RequestBody LoginUser user) {
		try {
			UsernamePasswordCaptchaToken usernamePasswordCaptchaToken = new UsernamePasswordCaptchaToken(
					user.getUserName(), user.getUserPwd(), user.getCaptcha(), AccountTypeEnum.employee.getCode());

			// 增加判断验证码逻辑
			String captcha = usernamePasswordCaptchaToken.getCaptcha();
			String exitCode = (String) SecurityUtils
					.getSubject()
					.getSession()
					.getAttribute(
							com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			if (null == captcha || !captcha.equalsIgnoreCase(exitCode)) {
				return new ResultData(false, "验证码错误",
						ExceptionConstant.EXCEPTION_TYPE_AUTH);
			}

			// 验证成功
			BaseUser baseUser = baseUserService.findByUserNameAndAccountType(user.getUserName(), AccountTypeEnum.employee.getCode());
//			usernamePasswordCaptchaToken.setAccountType(baseUser.getType());
			// 进行登录验证
			SecurityUtils.getSubject().login(usernamePasswordCaptchaToken);
			// 更新session
			initUserInfoToSession(usernamePasswordCaptchaToken, baseUser);
			return new ResultData(true);

		} catch (IncorrectCaptchaException e) {
			return new ResultData(false, "验证码错误", ExceptionConstant.EXCEPTION_TYPE_AUTH);
		} catch (AuthenticationException e) {
			return new ResultData(false, "用户名或密码错误", ExceptionConstant.EXCEPTION_TYPE_AUTH);
		} catch (BizException e) {
			return new ResultData(false, e.getMessage(), ExceptionConstant.EXCEPTION_TYPE_BIZ);
		}
	}

	/**
	 * 后台供应商登录
	 *
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/supply/login", method = RequestMethod.POST)
	public ResultData supplyLogin(@RequestBody LoginUser user) {
		try {
			UsernamePasswordCaptchaToken usernamePasswordCaptchaToken = new UsernamePasswordCaptchaToken(
					user.getUserName(), user.getUserPwd(), user.getCaptcha(), AccountTypeEnum.supplier.getCode());

			// 增加判断验证码逻辑
			String captcha = usernamePasswordCaptchaToken.getCaptcha();
			String exitCode = (String) SecurityUtils
					.getSubject()
					.getSession()
					.getAttribute(
							com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			if (null == captcha || !captcha.equalsIgnoreCase(exitCode)) {
				return new ResultData(false, "验证码错误",
						ExceptionConstant.EXCEPTION_TYPE_AUTH);
			}

			// 验证成功
			BaseUser baseUser = baseUserService.findByUserNameAndAccountType(user.getUserName(), AccountTypeEnum.supplier.getCode());
//			BaseUser baseUser = baseUserService.findByUserName(user.getUserName());
//			usernamePasswordCaptchaToken.setAccountType(baseUser.getType());
			// 进行登录验证
			SecurityUtils.getSubject().login(usernamePasswordCaptchaToken);
			// 更新session
			initUserInfoToSession(usernamePasswordCaptchaToken, baseUser);
			return new ResultData(true);

		} catch (IncorrectCaptchaException e) {
			return new ResultData(false, "验证码错误", ExceptionConstant.EXCEPTION_TYPE_AUTH);
		} catch (AuthenticationException e) {
			return new ResultData(false, "用户名或密码错误", ExceptionConstant.EXCEPTION_TYPE_AUTH);
		} catch (BizException e) {
			return new ResultData(false, e.getMessage(), ExceptionConstant.EXCEPTION_TYPE_BIZ);
		}
	}

	/**
	 * 后台登出
	 *
	 * @return
	 */
	@RequestMapping(value = "/sysmgr/logout", method = RequestMethod.GET)
	public String logout() {
		// 使用权限管理工具进行用户的退出，跳出登录，给出提示信息
		Subject subject = SecurityUtils.getSubject();
		String principal = (String) subject.getPrincipal();
		if (subject.getSession() != null) {
			subject.logout();
		}

//		String[] mobileAndAccountType = principal.split("/");
//		String accountType = mobileAndAccountType[1];
//		if (AccountTypeEnum.employee.getCode().equals(accountType)) {
//			return "redirect:login";
//		} else if (AccountTypeEnum.supplier.getCode().equals(accountType)) {
//			return "redirect:/supply/login";
//		}

		return "redirect:login";
	}

	/**
	 * 后台登出
	 *
	 * @return
	 */
	@RequestMapping(value = "/supply/logout", method = RequestMethod.GET)
	public String supplyLogout() {
		// 使用权限管理工具进行用户的退出，跳出登录，给出提示信息
		Subject subject = SecurityUtils.getSubject();
		String principal = (String) subject.getPrincipal();
		if (subject.getSession() != null) {
			subject.logout();
		}

//		String[] mobileAndAccountType = principal.split("/");
//		String accountType = mobileAndAccountType[1];
//		if (AccountTypeEnum.employee.getCode().equals(accountType)) {
//			return "redirect:login";
//		} else if (AccountTypeEnum.supplier.getCode().equals(accountType)) {
//			return "redirect:/supply/login";
//		}

		return "redirect:/supply/login";
	}

	/**
	 * 进入电商首页
	 *
	 * @return
	 */
	@RequestMapping(value = "/shop", method = RequestMethod.GET)
	public String shopIndex() {
		return "shop/index";
	}

	/**
	 * 进入电商登录页面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/shop/login", method = RequestMethod.GET)
	public String toShopLoginPage(Model model) {

		if (!model.containsAttribute("user")) {
			model.addAttribute("user", new LoginUser());
		}
		return "shop/login";
	}

	/**
	 * 电商登录
	 *
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/shop/login", method = RequestMethod.POST)
	public ResultData shopLogin(@RequestBody LoginUser user) {
		try {
			UsernamePasswordCaptchaToken usernamePasswordCaptchaToken = new UsernamePasswordCaptchaToken(
					user.getUserName(), user.getUserPwd(), user.getCaptcha(), user.getAccountType());

			// 如果重试次数超过3次，则增加验证码校验
			if (RETRY_AMOUNT <= user.getRetryAmount().intValue()) {
				// 增加判断验证码逻辑
				String captcha = usernamePasswordCaptchaToken.getCaptcha();
				String exitCode = (String) SecurityUtils
						.getSubject()
						.getSession()
						.getAttribute(
								com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
				if (null == captcha || !captcha.equalsIgnoreCase(exitCode)) {
					throw new IncorrectCaptchaException("验证码错误");
				}
			}

			// 验证用户
			BaseUser baseUser = baseUserService.findByUserNameAndAccountType(user.getUserName(), user.getAccountType());
			if (baseUser != null) {
				if (!validLoginAuthority(AccountTypeEnum.purcharser.getCode(), baseUser)) {
					return new ResultData(false, "您暂无权限登录该系统", ExceptionConstant.EXCEPTION_TYPE_AUTH);
				}
				// 进行登录验证
				SecurityUtils.getSubject().login(usernamePasswordCaptchaToken);
				// 更新session
				initUserInfoToSession(usernamePasswordCaptchaToken, baseUser);
				return new ResultData(true);
			} else {
				return new ResultData(false, "不存在当前用户", ExceptionConstant.EXCEPTION_TYPE_AUTH);
			}
		} catch (IncorrectCaptchaException e) {
			return new ResultData(false, "验证码错误", ExceptionConstant.EXCEPTION_TYPE_AUTH);
		} catch (AuthenticationException e) {
			return new ResultData(false, "用户名或密码错误", ExceptionConstant.EXCEPTION_TYPE_AUTH);
		} catch (BizException e) {
			return new ResultData(false, e.getMessage(), ExceptionConstant.EXCEPTION_TYPE_BIZ);
		}
	}

	/**
	 * 电商登出
	 *
	 * @return
	 */
	@RequestMapping(value = "/shop/logout", method = RequestMethod.GET)
	public String logoutShop() {
		// 使用权限管理工具进行用户的退出，跳出登录，给出提示信息
		if (SecurityUtils.getSubject().getSession() != null) {
			SecurityUtils.getSubject().logout();
		}

		return "redirect:login";
	}

	@RequestMapping("/403")
	public String unauthorizedRole() {
		return "common/403";
	}

	/**
	 * 发送ajax请求前检查session是否过期
	 *
	 * @return
	 */
	@RequestMapping(value = "/check/{env}", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkSessionTimeout(@PathVariable("env") String env) {
		logger.info("【验证session是否过期，如过期则阻止ajax请求】" + env);

		if (UserUtil.getUserFromSession() == null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
		// TODO 获取不同环境的session
		// if (SYSTEM_ENV.equals(env)) {
		//
		// } else if (SHOP_ENV.equals(env)) {
		//
		// }
	}

	/**
	 * 判断用户是否有权限
	 *
	 * @return
	 */
	private Boolean validLoginAuthority(String accountType, BaseUser user) {
		if (accountType.equals(user.getType())) {
			return true;
		}
		return false;
	}

	/**
	 * 初始化用户信息到session
	 */
	private void initUserInfoToSession(UsernamePasswordCaptchaToken token,
	                                   BaseUser user) {
		if (user != null) {
			SessionUser sessionUser = new SessionUser();
			sessionUser.setCurrentUserId(user.getId());
			sessionUser.setRoleCodes(user.getRoleCodes());
			sessionUser.setUserName(user.getUserName());
			sessionUser.setUserPwd(user.getUserPwd());
			sessionUser.setAccountType(user.getType());
			// 设置账户基础信息
			if (AccountTypeEnum.employee.getCode().equals(user.getType())) {
				Employee employee = (Employee) user;
				sessionUser.setDisplayUserName(employee.getEmployeeName());
				sessionUser.setMobile(employee.getMobile());
				sessionUser.setEmail(employee.getEmail());
				// 设置组织/岗位信息
				Map<String, Object> params = new HashMap<>();
				params.put("AND_EQ_id.employee.id", user.getId());

				List<UserOrgPositionRelation> relationList = userOrgPositionRelationService
						.findAll(params, UserOrgPositionRelation.class);
				if (relationList != null && relationList.size() > 0) {
					CompanyOrg companyOrg = relationList.get(0).getId()
							.getCompanyOrg();
					sessionUser.setCurrentOrgId(companyOrg.getId());
					sessionUser.setCurrentOrgName(companyOrg.getOrgName());

					Position position = relationList.get(0).getId()
							.getPosition();
					sessionUser.setCurrentPositionName(position
							.getPositionName());
					sessionUser.setCurrentPositionId(position.getId());
				}

			} else if (AccountTypeEnum.purcharser.getCode().equals(
					user.getType())) {
				PurchaserUser purchaserUser = (PurchaserUser) user;

				sessionUser.setDisplayUserName(purchaserUser.getName() + ","
						+ purchaserUser.getPurchaser().getOrgName());
				sessionUser.setMobile(purchaserUser.getMobile());
				// 设置采购商的级别
				sessionUser.getUserDefinedParams().put(
						SecurityContants.PURCHASER_LEVEL,
						purchaserUser.getPurchaser().getLevel());
			} else if (AccountTypeEnum.supplier.getCode().equals(user.getType())) {
				SupplierUser supplierUser = (SupplierUser) user;
				sessionUser.setDisplayUserName(supplierUser.getSupplierUserName());
				sessionUser.setMobile(supplierUser.getContactNumber());
				sessionUser.setCurrentOrgId(supplierUser.getSupplierOrg().getId());
				sessionUser.setCurrentOrgName(supplierUser.getSupplierOrg().getOrgName());
				sessionUser.setCurrentPositionName(supplierUser.getPosition());
			}

			// 将用户信息更新到session里去
			UserUtil.saveUserToSession(sessionUser);
		}
	}
}
