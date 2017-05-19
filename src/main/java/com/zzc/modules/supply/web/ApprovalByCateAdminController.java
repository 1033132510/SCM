package com.zzc.modules.supply.web;

import com.zzc.common.security.util.UserUtil;
import com.zzc.common.security.web.user.SessionUser;
import com.zzc.core.exceptions.AuthException;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.supply.service.AuditBillCateAdminService;
import com.zzc.modules.supply.service.AuditRecordService;
import com.zzc.modules.supply.vo.SupplyProductSKUVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

/**
 * //TODO 添加类/管理员审批
 *
 * @author ping
 * @date 2016年2月17日
 */
@Controller
@RequestMapping("/sysmgr/approvals/admin/")
public class ApprovalByCateAdminController extends BaseController {
	/**
	 * 指定类初始化日志对象
	 */
	private static Logger log = LoggerFactory.getLogger(ApprovalByCateAdminController.class);
	/**
	 * 品类总监审批单服务层实例化对象
	 */
	@Autowired
	private AuditBillCateAdminService auditBillCateAdminService;
	/**
	 * 审核记录服务层实例化对象
	 */
	@Autowired
	private AuditRecordService auditRecordService;

	/**
	 * TODO 品类管理员审批列表页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/pending", method = RequestMethod.GET)
	public String pending() {
		return "sysmgr/approval/cateAdmin/pending";
	}

	/**
	 * 分页
	 * @param curPage
	 * @param pageSize
	 * @param nameOrCode
	 * @param orgName
	 * @param brandName
	 * @return
	 */
	@RequestMapping(value = "/getPendingList", method = RequestMethod.POST)
	@ResponseBody
	public String getPendingList(@RequestParam("curPage") Integer curPage, @RequestParam("pageSize") Integer pageSize,
	                             @RequestParam(value = "nameOrCode", required = false) String nameOrCode,
	                             @RequestParam(value = "orgName", required = false) String orgName,
	                             @RequestParam(value = "brandName", required = false) String brandName) {
		SessionUser sessionUser = UserUtil.getUserFromSession();
		if (sessionUser == null) {
			throw new AuthException("会话失效,请重新登录");
		}
		return JsonUtils.toJson(
				auditBillCateAdminService.findPendingforCateAdmin(sessionUser.getCurrentUserId(), nameOrCode, orgName, brandName, pageSize, curPage));
	}

	/**
	 * 品类管理员已处理
	 * @return
	 */
	@RequestMapping(value = "/processed", method = RequestMethod.GET)
	public String processed() {
		return "sysmgr/approval/cateAdmin/processed";
	}

	@RequestMapping(value = "/getProcessedList", method = RequestMethod.POST)
	@ResponseBody
	public String getProcessedList(@RequestParam("curPage") Integer curPage, @RequestParam("pageSize") Integer pageSize,
	                               @RequestParam(value = "nameOrCode", required = false) String nameOrCode,
	                               @RequestParam(value = "orgName", required = false) String orgName,
	                               @RequestParam(value = "brandName", required = false) String brandName) {
		SessionUser sessionUser = UserUtil.getUserFromSession();
		if (sessionUser == null) {
			return "";
		}
		return JsonUtils.toJson(auditBillCateAdminService.findProcessedforCateAdmin(sessionUser.getCurrentUserId(), nameOrCode, orgName, brandName, pageSize, curPage));
	}

	/**
	 * 管理员待处理详情
	 *
	 * @param auditBillId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/{auditBillId}/pendingInfo", method = RequestMethod.GET)
	public ModelAndView getPendingInfo(@PathVariable("auditBillId") String auditBillId, ModelMap modelMap) {
		modelMap.put("auditBillId", auditBillId);
		return new ModelAndView("sysmgr/approval/cateAdmin/pendingInfo", modelMap);
	}

	/**
	 * 管理员已处理信息
	 * @param auditBillId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/{auditBillId}/processedInfo", method = RequestMethod.GET)
	public ModelAndView getProcessedInfo(@PathVariable("auditBillId") String auditBillId, ModelMap modelMap) {
		modelMap.put("auditBillId", auditBillId);
		return new ModelAndView("sysmgr/approval/cateAdmin/processedInfo", modelMap);
	}

	/**
	 * 获得品类管理员审核记录
	 * @param auditBillId
	 * @return
	 */
	@RequestMapping(value = "/{auditBillId}/cateAdminRecord", method = RequestMethod.POST)
	@ResponseBody
	public String getCateAdminRecord(@PathVariable("auditBillId") String auditBillId) {
		return JsonUtils.toJson(auditRecordService.findAuditRecordListForCateAdmin(auditBillId));
	}

	/**
	 * 获得下架商品详情
	 * @param auditBillId
	 * @return
	 */
	@RequestMapping(value = "/{auditBillId}/cateAdminProductInfo", method = RequestMethod.POST)
	@ResponseBody
	public SupplyProductSKUVO getCateAdminProductInfo(@PathVariable("auditBillId") String auditBillId) {
		return auditBillCateAdminService.findSubProductDetailForCateAdmin(auditBillId);
	}

	/**
	 * 获得待处理商品价格
	 * @param auditBillId
	 * @return
	 */
	@RequestMapping(value = "/{auditBillId}/pendingProductPrice", method = RequestMethod.POST)
	@ResponseBody
	public String getPendingProductPrice(@PathVariable("auditBillId") String auditBillId) {
		return JsonUtils.toJson(auditRecordService.findProductPrice(auditBillId));
	}

	/**
	 * 审核不通过
	 * @param auditBillId
	 * @param comment
	 * @param hasTax
	 * @param hasTransportation
	 * @param standard
	 * @return
	 */
	@RequestMapping(value = "/{auditBillId}/noPass", method = RequestMethod.POST)
	@ResponseBody
	public String setNoPass(@PathVariable("auditBillId") String auditBillId,
	                        @RequestParam(name = "comment", required = true) String comment, @RequestParam(name = "hasTax", required = false) Integer hasTax,
	                        @RequestParam(name = "hasTransportation", required = false) Integer hasTransportation,
	                        @RequestParam(name = "standard", required = false) BigDecimal standard) {
		auditBillCateAdminService.setNoPass(auditBillId, comment, hasTax, hasTransportation, standard);
		return "success";
	}

	/**
	 * 审核通过
	 * @param auditBillId
	 * @param comment
	 * @param hasTax
	 * @param hasTransportation
	 * @param standard
	 * @return
	 */
	@RequestMapping(value = "/{auditBillId}/pass", method = RequestMethod.POST)
	@ResponseBody
	public String setPass(@PathVariable("auditBillId") String auditBillId,
	                      @RequestParam(name = "comment", required = true) String comment, @RequestParam(name = "hasTax", required = false) Integer hasTax,
	                      @RequestParam(name = "hasTransportation", required = false) Integer hasTransportation,
	                      @RequestParam(name = "standard", required = false) BigDecimal standard) {
		auditBillCateAdminService.setPass(auditBillId, comment, hasTax, hasTransportation, standard);
		return "success";
	}

	/**
	 * 最新审核记录
	 * @param auditBillId
	 * @return
	 */
	@RequestMapping(value = "/{auditBillId}/latestRecord", method = RequestMethod.POST)
	@ResponseBody
	public String getLatestRecord(@PathVariable("auditBillId") String auditBillId) {
		SessionUser sessionUser = UserUtil.getUserFromSession();
		if (sessionUser == null) {
			return "";
		}
		return JsonUtils.toJson(auditRecordService.findRecordLatestByCreateId(auditBillId, sessionUser.getCurrentUserId()));
	}
}
