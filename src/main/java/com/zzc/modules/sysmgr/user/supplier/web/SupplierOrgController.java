package com.zzc.modules.sysmgr.user.supplier.web;

import com.zzc.common.enums.EnumUtil;
import com.zzc.common.page.PageForShow;
import com.zzc.common.security.util.UserUtil;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.shop.supplier.service.ShopSupplierService;
import com.zzc.modules.sysmgr.common.entity.SequenceEntity;
import com.zzc.modules.sysmgr.common.service.SequenceEntityService;
import com.zzc.modules.sysmgr.common.util.NumberSequenceUtil;
import com.zzc.modules.sysmgr.constant.SystemConstants;
import com.zzc.modules.sysmgr.file.service.ImageService;
import com.zzc.modules.sysmgr.user.base.entity.Role;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.employee.service.EmployeeService;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierOrg;
import com.zzc.modules.sysmgr.user.supplier.service.SupplierOrgService;
import com.zzc.modules.sysmgr.user.supplier.supplierenum.SupplierTypeEnum;
import com.zzc.modules.sysmgr.user.supplier.supplierenum.statusTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 供应商组织Controller
 *
 * @author ping
 */

@Controller
@RequestMapping(value = "/sysmgr/supplierOrg")
public class SupplierOrgController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(SupplierOrgController.class);
	private static final String PURCHASER_CODE_PREFIX = "GYS-";
	private static final String SEQUENCE_NAME = "SUPPLIERORG";

	@Autowired
	private SupplierOrgService supplierOrgService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private SequenceEntityService sequenceEntityService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ShopSupplierService shopSupplierService;

	/**
	 * 供应商有效首页
	 *
	 * @return
	 */
	@RequestMapping(value = "/view")
	public ModelAndView getSupplierIndex(Map<String, Object> modelMap) {
		modelMap.put("status", CommonStatusEnum.有效.getValue());
		return new ModelAndView("sysmgr/supplier/supplierList", modelMap);
	}

	/**
	 * 供应商无效首页
	 *
	 * @return
	 */
	@RequestMapping(value = "/failureView")
	public ModelAndView getSupplierFailure(Map<String, Object> modelMap) {
		modelMap.put("status", CommonStatusEnum.无效.getValue());
		return new ModelAndView("sysmgr/supplier/supplierList", modelMap);
	}

	/**
	 * 供应商首页--上架供应商下的品牌
	 *
	 * @return
	 */
	@RequestMapping(value = "/invalidBrandManager")
	public String invalidBrandManager() {
		log.debug("供应商首页--修改已失效信息");
		return "sysmgr/supplier/supplierListForGrounding";
	}

	@RequestMapping(value = "/getSupplierOrgList", method = RequestMethod.POST)
	@ResponseBody
	public String getSupplierList(@RequestParam("curPage") Integer pageNumber, @RequestParam("pageSize") Integer pageSize,
	                              @RequestParam(name = "orgCodeOrorgName", required = false) String orgCodeOrorgName, @RequestParam(name = "status", required = false) Integer status) {
		Page<SupplierOrg> supplierOrgPage = null;
		if (StringUtils.isNotEmpty(orgCodeOrorgName)) {
			supplierOrgPage = supplierOrgService.findByorgCodeOrorgName(orgCodeOrorgName, pageNumber, pageSize, status);
		} else {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("AND_EQ_status", status);
			supplierOrgPage = supplierOrgService.findByParams(SupplierOrg.class, param, pageNumber, pageSize, "DESC", "createTime");
		}
		log.debug(JsonUtils.toJson(PageForShow.newInstanceFromSpringPage(supplierOrgPage, pageNumber)));
		return JsonUtils.toJson(PageForShow.newInstanceFromSpringPage(supplierOrgPage, pageNumber));
	}

	@RequestMapping(value = "/getSupplierOrgInfo/{supplierOrgId}")
	public ModelAndView getSupplierInfo(@PathVariable("supplierOrgId") String supplierOrgId, @RequestParam(name = "status", required = false) Integer status, Map<String, Object> modelMap) {
		initSupplierOrgInfoMap(supplierOrgId, modelMap);
		modelMap.put("status", status);
		return new ModelAndView("sysmgr/supplier/supplierOrgInfo", modelMap);
	}

	private void initSupplierOrgInfoMap(String supplierOrgId, Map<String, Object> modelMap) {
		SupplierOrg supplierOrg = supplierOrgService.findByPK(supplierOrgId);
		Map<String, String> supplierTypeEnums = EnumUtil.toMap(SupplierTypeEnum.class);
		Map<String, String> statusEnums = EnumUtil.toMap(statusTypeEnum.class);
		modelMap.put("supplierTypeEnums", supplierTypeEnums);
		modelMap.put("supplierOrg", supplierOrg);
		modelMap.put("statusEnums", statusEnums);
	}

	/**
	 * 修改供应商
	 *
	 * @return
	 */
	@RequestMapping(value = "/saveSupplierOrg", method = RequestMethod.POST)
	@ResponseBody
	public String saveSupplierOrg(@RequestParam(value = "supplierOrgParams", required = true) String supplierOrgParams, @RequestParam(value = "imageIds[]", required = false) String[] imageIds,
	                              @RequestParam(value = "deleteImageIds[]", required = false) String[] deleteImageIds) {
		SupplierOrg supplierOrg = JsonUtils.toObject(supplierOrgParams, SupplierOrg.class);
		String resultStr = "fail";

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<SupplierOrg>> constraintViolations = validator.validate(supplierOrg);
		for (ConstraintViolation<SupplierOrg> p : constraintViolations) {
			log.debug(p.getMessage());
		}
		if (!constraintViolations.isEmpty()) {
			return resultStr;
		}

		if (null == supplierOrg.getOrgCode() || "".equals(supplierOrg.getOrgCode())) {
			supplierOrg.setOrgCode(generateOrgCode());
		}
		supplierOrg.setOrgLevel(1);
		SupplierOrg newOrg = supplierOrgService.save(supplierOrg);
		imageService.buildRelationBetweenImageAndEntity(imageIds, newOrg.getId());
		imageService.destoryRelationBetweenImageAndEntity(deleteImageIds);

		if (newOrg.getId().isEmpty()) {
			resultStr = "fail";
		} else {
			resultStr = newOrg.getId();
		}
		return resultStr;
	}

	/**
	 * 修改供应商状态
	 *
	 * @return
	 */
	@RequestMapping(value = "/setSupplierOrgStatus", method = RequestMethod.POST)
	@ResponseBody
	public String setSupplierOrgStatus(@RequestParam(value = "id", required = false) String id,
	                                   @RequestParam(value = "status", required = false) Integer status) {

		SupplierOrg supplierOrg = supplierOrgService.findByPK(id);
		if (supplierOrg != null) {
			supplierOrg.setStatus(status);
		}
		supplierOrg = supplierOrgService.save(supplierOrg);
		return JsonUtils.toJson(supplierOrg);
	}

	@RequestMapping(value = "/addSupplierOrg", method = RequestMethod.GET)
	public ModelAndView addSupplierOrg(ModelMap modelMap) {
		Map<String, String> supplierTypeEnums = EnumUtil.toMap(SupplierTypeEnum.class);
		Map<String, String> statusEnums = EnumUtil.toMap(statusTypeEnum.class);
		modelMap.put("supplierTypeEnums", supplierTypeEnums);
		modelMap.put("statusEnums", statusEnums);
		return new ModelAndView("sysmgr/supplier/supplierOrgInfo", modelMap);
	}

	@RequestMapping(value = "/checkName", method = RequestMethod.POST)
	@ResponseBody
	public String checkName(@RequestParam(name = "name", required = true) String name, @RequestParam(name = "id", required = false) String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("AND_EQ_orgName", name);
		params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		List<SupplierOrg> supplierOrgList = supplierOrgService.findAll(params, null);
		String result = "success";
		for (int i = 0; i < supplierOrgList.size(); i++) {
			if (!id.equals(supplierOrgList.get(i).getId())) {
				result = "fail";
			}
		}
		return result;
	}

	private String generateOrgCode() {
		SequenceEntity sequenceEntity = sequenceEntityService.findSequenceEntityBySequenceName(SEQUENCE_NAME);
		Integer sequenceNumber = 1;
		if (sequenceEntity == null) {
			sequenceEntity = new SequenceEntity();
			sequenceEntity.setSequenceName(SEQUENCE_NAME);
			sequenceEntity.setStatus(CommonStatusEnum.有效.getValue());
			sequenceEntity.setSequenceNumber(sequenceNumber);
		} else {
			sequenceNumber = sequenceEntity.getSequenceNumber() + 1;
			sequenceEntity.setSequenceNumber(sequenceNumber);
		}
		sequenceEntityService.create(sequenceEntity);
		String sequenceNumberStr = NumberSequenceUtil.appendPrefixChar(sequenceNumber, 4, '0');
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return PURCHASER_CODE_PREFIX + format.format(new Date()) + "-" + sequenceNumberStr;
	}

	/**
	 * 查看员工是否有权限查看或操作采购商登记信息，如果没有权限，跳转到对应页面提示
	 */
	private String hasRightToOperatePurchaser() {
		String employeeId = UserUtil.getUserFromSession().getCurrentUserId();
		Employee employee = employeeService.findByPK(employeeId);
		List<Role> roles = employee.getRoleList();
		boolean flag = false;
		String supplierAdministrator = SystemConstants.SUPPLIER_ADMINISTRATOR;
		for (Role role : roles) {
			String code = role.getRoleCode();
			if (supplierAdministrator.equals(code)) {
				flag = true;
				break;
			}
		}
		if (flag) {
			return null;
		} else {
			return "common/401";
		}
	}

	@RequestMapping(value = "/registerSuppliesView")
	public String reigstList() {
		return "sysmgr/supplier/registList";
	}

	@RequestMapping(value = "/registerSupplies")
	@ResponseBody
	public String page(Integer curPage, Integer pageSize) {
		String result = hasRightToOperatePurchaser();
		if (result != null) {
			return "您没有权限查看该资源";
		} else {
			return JsonUtils.toJson(shopSupplierService.getSupplierPage(curPage, pageSize));
		}
	}
}