package com.zzc.modules.supply.web;

import com.zzc.common.security.util.UserUtil;
import com.zzc.common.security.web.user.SessionUser;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.common.ResultData;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.sysmgr.enums.AuditRecordTypeEnum;
import com.zzc.modules.sysmgr.enums.SupplyAuditBillStatusEnum;
import com.zzc.modules.supply.entity.AuditBill;
import com.zzc.modules.supply.entity.AuditRecord;
import com.zzc.modules.supply.service.AuditBillService;
import com.zzc.modules.supply.service.AuditRecordService;
import com.zzc.modules.supply.service.SupplyProductSKUService;
import com.zzc.modules.supply.vo.AuditRecordVO;
import com.zzc.modules.supply.vo.SupplyProductSKUVO;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.employee.service.EmployeeService;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierUser;
import com.zzc.modules.sysmgr.user.supplier.service.SupplierUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * //TODO 添加类/供应商提交审批
 *
 * @author ping
 * @date 2016年2月17日
 */
@Controller
@RequestMapping("/supply/approvals")
public class ApprovalBySupplyController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(ApprovalBySupplyController.class);

	@Autowired
	private AuditBillService auditBillService;

	@Autowired
	private AuditRecordService auditRecordService;

	@Autowired
	private SupplyProductSKUService supplyProductSKUService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private SupplierUserService supplierUserService;

	/**
	 * //TODO 供应商已提交商品列表页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/submitted", method = RequestMethod.GET)
	public String submittedView() {
		return "supply/approval/submitted";
	}

	@RequestMapping(value = "/getSubmittedList")
	@ResponseBody
	public String getSubmittedList(@RequestParam("curPage") Integer curPage, @RequestParam("pageSize") Integer pageSize) {
		SessionUser sessionUser = UserUtil.getUserFromSession();
		if (sessionUser == null) {
			return "";
		}
		log.debug(sessionUser.getCurrentOrgName());
		log.debug(sessionUser.getCurrentOrgId());
		return JsonUtils.toJson(auditBillService.findSubmittedforSupply(sessionUser.getCurrentOrgId(), pageSize, curPage));
	}

	@RequestMapping(value = "/{auditBillId}/submittedInfo", method = RequestMethod.GET)
	public ModelAndView detailView(@PathVariable("auditBillId") String auditBillId, ModelMap modelMap) {
		modelMap.put("auditBillId", auditBillId);
		return new ModelAndView("supply/approval/submittedInfo", modelMap);
	}

	@RequestMapping(value = "/{auditBillId}/submittedRecord", method = RequestMethod.POST)
	@ResponseBody
	public String getAuditRecordList(@PathVariable("auditBillId") String auditBillId) {
		return JsonUtils.toJson(auditRecordService.findAuditRecordListForSupply(auditBillId));
	}

	@RequestMapping(value = "/{auditBillId}/submittedProductInfo", method = RequestMethod.POST)
	@ResponseBody
	public String getSubProductDetail(@PathVariable("auditBillId") String auditBillId) {
		return JsonUtils.toJson(auditBillService.findSubProductDetailForSupply(auditBillId));
	}


	@RequestMapping(value = "denied", method = RequestMethod.GET)
	public String deniedView() {
		return "supply/approval/denied";
	}


	@RequestMapping(value = "denied", method = RequestMethod.POST)
	@ResponseBody
	public String deniedList(@RequestParam("curPage") Integer curPage, @RequestParam("pageSize") Integer pageSize) {
		SessionUser sessionUser = UserUtil.getUserFromSession();
		if (sessionUser == null) {
			return "";
		}
		return JsonUtils.toJson(auditBillService.findNeedAdjustforSupply(sessionUser.getCurrentUserId(), pageSize, curPage));
	}

	@RequestMapping(value = "/{approvalId}", method = RequestMethod.GET)
	public String doNeedAdjustView(@PathVariable("approvalId") String approvalId, ModelMap model) {
		Map<String, Object> auditBillParams = new HashMap<>();
		auditBillParams.put("AND_EQ_id", approvalId);
		auditBillParams.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		auditBillParams.put("AND_EQ_auditStatus", SupplyAuditBillStatusEnum.TO_ADJUSTED.getCode());
		List<AuditBill> auditBills = auditBillService.findAll(auditBillParams, AuditBill.class);
		AuditBill auditBill = auditBills.get(0);
		Map<String, Object> auditRecordParams = new HashMap<>();
		auditRecordParams.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		auditRecordParams.put("AND_EQ_auditBill.id", approvalId);
		List<AuditRecord> auditRecords = auditRecordService.findAll(auditRecordParams, AuditRecord.class, "DESC", "createTime");
		List<AuditRecordVO> vos = new ArrayList<>(auditRecords.size());
		for (AuditRecord auditRecord : auditRecords) {
			if (auditRecord.getType() == AuditRecordTypeEnum.CATEGORY_ADMINISTRATOR_toSUPPLY_USER.getCode()) {
				AuditRecordVO vo = new AuditRecordVO();
				Employee categoryAdministrator = employeeService.findByPK(auditRecord.getCreateId());
				vo.setApproverName(categoryAdministrator.getEmployeeName());
				vo.setCreateTime(auditRecord.getCreateTime());
				vo.setApproverNumber(categoryAdministrator.getMobile());
				vo.setComment(auditRecord.getComment());
				vo.setType(auditRecord.getType());
				vos.add(vo);
			} else if (auditRecord.getType() == AuditRecordTypeEnum.SUPPLY_USER_toADMINISTRATOR.getCode()) {
				AuditRecordVO vo = new AuditRecordVO();
				SupplierUser supplierUser = supplierUserService.findByPK(auditRecord.getCreateId());
				vo.setApproverName(supplierUser.getSupplierUserName());
				vo.setCreateTime(auditRecord.getCreateTime());
				vo.setApproverNumber(supplierUser.getContactNumber());
				vo.setType(auditRecord.getType());
				vo.setComment(auditRecord.getComment());
				vos.add(vo);
			}
		}
		model.put("auditRecords", vos);
		model.put("id", auditBill.getSysProductSKU().getId());
		SupplyProductSKUVO vo = JsonUtils.toObject(auditBill.getSysProductSKU().getProductInfos(), SupplyProductSKUVO.class);
		model.put("brandId", vo.getBrandId());
		model.put("supplierOrgId", vo.getSupplierOrgId());
		model.put("secondLevelCateId", vo.getSecondLevelCateId());
		model.put("cateId", vo.getThirdLevelCateId());
		return "supply/approval/adjust";
	}

	@RequestMapping(value = "adjust", method = RequestMethod.POST)
	@ResponseBody
	public ResultData doNeedAdjust(@RequestParam(value = "productDetailAndComment", required = true) String productDetailAndComment) {
		supplyProductSKUService.updateProductAndCreateAuditRecord(productDetailAndComment);
		return new ResultData(true);
	}

	@ResponseBody
	@RequestMapping(value = "{approvalId}", method = RequestMethod.DELETE)
	public ResultData deleteAuditBillAndRecordAndProduct(@PathVariable("approvalId") String approvalId) {
		supplyProductSKUService.deleteProductAndAuditBillAndRecord(approvalId);
		return new ResultData(true);
	}


}
