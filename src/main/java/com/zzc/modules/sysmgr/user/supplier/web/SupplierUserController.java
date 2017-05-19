package com.zzc.modules.sysmgr.user.supplier.web;

import com.zzc.common.enums.EnumUtil;
import com.zzc.common.security.util.PasswordUtil;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.sysmgr.enums.AccountTypeEnum;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierOrg;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierUser;
import com.zzc.modules.sysmgr.user.supplier.service.SupplierOrgService;
import com.zzc.modules.sysmgr.user.supplier.service.SupplierUserService;
import com.zzc.modules.sysmgr.user.supplier.supplierenum.statusTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 供应商用户Controller
 *
 * @author ping
 */
@Controller
@RequestMapping(value = "/sysmgr/supplierUser")
public class SupplierUserController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(SupplierOrgController.class);

	@Autowired
	private SupplierUserService supplierUserService;

	@Autowired
	private SupplierOrgService supplierOrgService;

	@RequestMapping(value = "/view/{supplierOrgId}", method = RequestMethod.GET)
	public ModelAndView getSupplierUserInfo(@PathVariable("supplierOrgId") String supplierOrgId, ModelMap model) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("AND_EQ_supplierOrg.id", supplierOrgId);
		params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		List<SupplierUser> supplierUserList = supplierUserService.findAll(params, null);
		if (!supplierUserList.isEmpty()) {
			model.put("supplierUser", supplierUserList.get(0));
		}
		Map<String, String> statusEnums = EnumUtil.toMap(statusTypeEnum.class);
		model.put("statusEnums", statusEnums);
		model.put("supplierOrgId", supplierOrgId);

		return new ModelAndView("sysmgr/supplier/supplierUserInfo", model);
	}

	/**
	 * 修改供应商
	 *
	 * @return
	 */
	@RequestMapping(value = "/saveSupplierUser", method = RequestMethod.POST)
	@ResponseBody
	public String saveSupplierOrg(@RequestParam(value = "params", required = true) String params) {
		SupplierUser supplierUser = JsonUtils.toObject(params, SupplierUser.class);
		String resultStr = "fail";

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<SupplierUser>> constraintViolations = validator.validate(supplierUser);
		for (ConstraintViolation<SupplierUser> p : constraintViolations) {
			log.debug(p.getMessage());
		}
		if (!constraintViolations.isEmpty()) {
			return resultStr;
		}

		SupplierUser resultSupplierUser = null;
		if (StringUtils.isBlank(supplierUser.getId())) { // 新增
			supplierUser.setUserPwd(PasswordUtil.encryptPassword(supplierUser.getUserPwd()));
			supplierUser.setType(AccountTypeEnum.supplier.getCode());
			supplierUser.setStatus(CommonStatusEnum.有效.getValue());
			SupplierOrg supplierOrg = supplierOrgService.findByPK(supplierUser.getSupplierOrg().getId());
			supplierUser.setSupplierOrg(supplierOrg);
			resultSupplierUser = supplierUserService.save(supplierUser);
		} else { // 修改
			SupplierUser supplierUserFromDB = supplierUserService.findByPK(supplierUser.getId());
			if (null == supplierUserFromDB) {
				return resultStr;
			}
			supplierUserFromDB.setUserName(supplierUser.getContactNumber());
			supplierUserFromDB.setContactNumber(supplierUser.getContactNumber());
			supplierUserFromDB.setDepartment(supplierUser.getDepartment());
			supplierUserFromDB.setEmail(supplierUser.getEmail());
			supplierUserFromDB.setIdcard(supplierUser.getIdcard());
			supplierUserFromDB.setPosition(supplierUser.getPosition());
			supplierUserFromDB.setSupplierUserName(supplierUser.getSupplierUserName());
			resultSupplierUser = supplierUserService.save(supplierUserFromDB);
		}
		return resultSupplierUser.getId();
	}

	/**
	 * 手机验证
	 * @param number
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/checkMobile", method = RequestMethod.POST)
	@ResponseBody
	public String checkMobile(@RequestParam(name = "number", required = true) String number, @RequestParam(name = "id", required = false) String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("AND_EQ_contactNumber", number);
		List<SupplierUser> supplierUserList = supplierUserService.findAll(params, null);
		String result = "success";
		for (int i = 0; i < supplierUserList.size(); i++) {
			if (!id.equals(supplierUserList.get(i).getId())) {
				result = "fail";
			}
		}
		return result;
	}

	/**
	 * 修改密码
	 * @param id
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "modifyPass", method = RequestMethod.POST)
	@ResponseBody
	public Boolean modifyPass(String id, String password) {
		if (StringUtils.isBlank(id) || StringUtils.isBlank(password)) {
			return Boolean.FALSE;
		}
		SupplierUser supplierUser = supplierUserService.findByPK(id);
		if (null == supplierUser) {
			return Boolean.FALSE;
		}
		supplierUser.setUserPwd(PasswordUtil.encryptPassword(password));
		supplierUserService.save(supplierUser);
		return Boolean.TRUE;
	}
}
