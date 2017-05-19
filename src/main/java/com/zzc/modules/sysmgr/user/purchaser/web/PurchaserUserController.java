package com.zzc.modules.sysmgr.user.purchaser.web;

import com.zzc.common.page.PageForShow;
import com.zzc.common.security.util.PasswordUtil;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.exceptions.ValidateException;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.common.ResultData;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.sysmgr.enums.AccountTypeEnum;
import com.zzc.modules.sysmgr.user.purchaser.dto.SavePurchaserUserRequestDTO;
import com.zzc.modules.sysmgr.user.purchaser.entity.PurchaserUser;
import com.zzc.modules.sysmgr.user.purchaser.service.PurchaserService;
import com.zzc.modules.sysmgr.user.purchaser.service.PurchaserUserService;
import com.zzc.modules.sysmgr.user.purchaser.validator.PurchaserUserSaveGroup;
import com.zzc.modules.sysmgr.user.purchaser.validator.PurchaserUserUpdateGroup;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 采购商管理（添加员工信息）
 */
@Controller
@RequestMapping("sysmgr/purchaserUser")
public class PurchaserUserController extends BaseController {

	@Autowired
	private PurchaserUserService purchaserUserService;

	@Autowired
	private PurchaserService purchaserService;

	private static final Validator validator = Validation
			.buildDefaultValidatorFactory().getValidator();

	/**
	 * 采购商公司员工
	 * 
	 * @param purchaserId
	 *            所属采购商
	 * @param model
	 * @return
	 */
	@RequestMapping("view/purchaser/{purchaserId}")
	public String view(@PathVariable("purchaserId") String purchaserId,
			ModelMap model) {
		model.addAttribute("purchaserId", purchaserId);
		return "sysmgr/purchaser/purchaserUser";
	}

	/**
	 * 添加人员信息
	 * @param id
	 * @param purchaserId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{id}/purchaser/{purchaserId}", method = RequestMethod.GET)
	public String updateView(@PathVariable("id") String id,
			@PathVariable("purchaserId") String purchaserId, ModelMap model) {
		model.addAttribute("purchaserUser", purchaserUserService.findByPK(id));
		initData(purchaserId, model);
		return "sysmgr/purchaser/addOrUpdatePurchaserUser";
	}

	private void initData(String purchaserId, ModelMap model) {
		model.addAttribute("purchaserId", purchaserId);
		model.addAttribute("commonStatus",
				EnumUtils.getEnumList(CommonStatusEnum.class));
	}

	/**
	 * 增加视图
	 * @param purchaserId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "addView/purchaser/{purchaserId}", method = RequestMethod.GET)
	public String addView(@PathVariable("purchaserId") String purchaserId,
			ModelMap model) {
		initData(purchaserId, model);
		return "sysmgr/purchaser/addOrUpdatePurchaserUser";
	}

	/**
	 * 获得用户列表
	 * @param purchaserId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "purchaser/{purchaserId}")
	@ResponseBody
	public String getPurchaserUserList(
			@PathVariable("purchaserId") String purchaserId,
			@RequestParam("curPage") Integer pageNumber,
			@RequestParam("pageSize") Integer pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_purchaser.id", purchaserId);
		Page<PurchaserUser> page = purchaserUserService.findByParams(
				PurchaserUser.class, params, pageNumber, pageSize, "DESC",
				"createTime");
		return JsonUtils.toJson(PageForShow.newInstanceFromSpringPage(page,
				pageNumber));
	}

	/**
	 * 保存或修改
	 * @param requestDTO
	 * @param bindingResults
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public ResultData saveOrUpdatePurchaserUser(
			@Valid @RequestBody SavePurchaserUserRequestDTO requestDTO,
			BindingResult bindingResults) throws Exception {
		Set<ConstraintViolation<SavePurchaserUserRequestDTO>> constraintViolations = null;
		if (StringUtils.isBlank(requestDTO.getId())) {
			constraintViolations = validator.validate(requestDTO,
					PurchaserUserSaveGroup.class);
		} else {
			constraintViolations = validator.validate(requestDTO,
					PurchaserUserUpdateGroup.class);
		}
		StringBuilder validatorMessages = new StringBuilder();
		for (ConstraintViolation<SavePurchaserUserRequestDTO> p : constraintViolations) {
			validatorMessages.append(p.getMessage());
		}

		if (validatorMessages.length() > 0) {
			throw new ValidateException(validatorMessages.toString());
		}
		PurchaserUser purchaserUser = null;
		String originalPass = null;
		if (StringUtils.isBlank(requestDTO.getId())) {
			purchaserUser = new PurchaserUser();
		} else {
			purchaserUser = purchaserUserService.findByPK(requestDTO.getId());
			originalPass = purchaserUser.getUserPwd();
		}
		BeanUtils.copyProperties(purchaserUser, requestDTO);
		if (StringUtils.isNotBlank(originalPass)) {
			purchaserUser.setUserPwd(originalPass);
		}
		if (StringUtils.isNotBlank(requestDTO.getUserPwd())) {
			purchaserUser.setUserPwd(PasswordUtil.encryptPassword(requestDTO
					.getUserPwd()));
		}
		purchaserUser.setUserName(purchaserUser.getMobile());
		purchaserUser.setStatus(CommonStatusEnum.有效.getValue());
		purchaserUser.setType(AccountTypeEnum.purcharser.getCode());
		purchaserUser.setModifiedTime(new Date());
		purchaserUser.setPurchaser(purchaserService.findByPK(requestDTO
				.getPurchaserId()));
		purchaserUserService.save(purchaserUser);
		return new ResultData(true);
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
		PurchaserUser purchaserUser = purchaserUserService.findByPK(id);
		if (purchaserUser == null) {
			return Boolean.FALSE;
		}
		purchaserUser.setUserPwd(PasswordUtil.encryptPassword(password));
		purchaserUser.setModifiedTime(new Date());
		purchaserUserService.save(purchaserUser);
		return Boolean.TRUE;
	}
}
