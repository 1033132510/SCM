package com.zzc.modules.sysmgr.order.web;

import com.zzc.common.page.PageForShow;
import com.zzc.common.security.util.UserUtil;
import com.zzc.common.security.web.user.SessionUser;
import com.zzc.common.sms.*;
import com.zzc.common.util.ConcurrentDateUtil;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.order.entity.IntentionOrder;
import com.zzc.modules.order.service.IntentionOrderItemService;
import com.zzc.modules.order.service.IntentionOrderService;
import com.zzc.modules.sysmgr.common.service.SequenceEntityService;
import com.zzc.modules.sysmgr.constant.SystemConstants;
import com.zzc.modules.sysmgr.enums.IntentionOrderStatusEnum;
import com.zzc.modules.sysmgr.product.service.ProductSKUService;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.employee.service.EmployeeService;
import com.zzc.modules.sysmgr.user.purchaser.service.PurchaserUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 意向单管理
 */
@Controller("sysIntentionOrder")
@RequestMapping("/sysmgr/intentionOrder")
public class IntentionOrderController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(IntentionOrderController.class);

	private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
	@Autowired
	private IntentionOrderService intentionOrderService;

	@Autowired
	private IntentionOrderItemService intentionOrderItemService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private PurchaserUserService purchaserUserService;

	@Autowired
	private SequenceEntityService sequenceEntityService;

	@Autowired
	private ProductSKUService productSKUService;

	@Autowired
	private SmsService smsService;

	@RequestMapping(value = "view", method = RequestMethod.GET)
	public String getIntentionOrderView() {
		return "sysmgr/intentionOrder/intentionOrder";
	}

	@RequestMapping(value = "intentionOrders", method = RequestMethod.POST)
	@ResponseBody
	public String getIntentionOrders(
			@RequestParam(value = "curPage", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "orderStatus", required = false) Integer orderStatus,
			@RequestParam(value = "orderCodeOrOrgName", required = false) String orderCodeOrOrgName)
			throws Exception {
		SessionUser sessionUser = UserUtil.getUserFromSession();
		Page<IntentionOrder> page = intentionOrderService.findByCondition(
				pageNumber, pageSize, orderStatus, orderCodeOrOrgName,
				isBoss(sessionUser), sessionUser.getCurrentUserId());
		return JsonUtils.toJson(PageForShow.newInstanceFromSpringPage(page,
				pageNumber));
	}

	/**
	 * 判断当前用户是否是运营总监
	 * 
	 * @param sessionUser
	 * @return
	 */
	private boolean isBoss(SessionUser sessionUser) {
		boolean isBoss = false;
		if (CollectionUtils.isNotEmpty(sessionUser.getRoleCodes())) {
			for (String roleCode : sessionUser.getRoleCodes()) {
				if (SystemConstants.ORDER_ADMINISTRATOR.equals(roleCode)) {
					isBoss = true;
					break;
				}
			}
		}
		return isBoss;
	}

	/**
	 * 判断当前用户是否为普通接单员
	 * 
	 * @param sessionUser
	 * @return
	 */
	private boolean isOperator(SessionUser sessionUser) {
		boolean isOperator = false;
		if (CollectionUtils.isNotEmpty(sessionUser.getRoleCodes())) {
			for (String roleCode : sessionUser.getRoleCodes()) {
				if (SystemConstants.ORDER_TAKER.equals(roleCode)) {
					isOperator = true;
					break;
				}
			}
		}
		return isOperator;
	}

	/**
	 * 获得意向单详细信息
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getIntentionOrderDetail(@PathVariable("id") String id,
			ModelMap model) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		SessionUser sessionUser = UserUtil.getUserFromSession();
		boolean isBoss = isBoss(sessionUser);
		boolean isOperator = isOperator(sessionUser);
		if (!isBoss && !isOperator) {
			return "common/401";
		}
		IntentionOrder intentionOrder = intentionOrderService.findByPK(id);
		if (intentionOrder == null) {
			return null;
		}
		if (isOperator) { // 如果是运营人员并且当前意向单已经分配给他才有权限查看
			if (intentionOrder.getEmployee() == null) {
				return "common/401";
			}
			if (!sessionUser.getCurrentUserId().equals(
					intentionOrder.getEmployee().getId())) {
				return "common/401";
			}
		}
		model.addAttribute("order", intentionOrder);
		return "sysmgr/intentionOrder/intentionOrderDetail";
	}

	/**
	 * 获得意向单
	 * @param orderId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "detail", method = RequestMethod.POST)
	@ResponseBody
	public String getIntentionOrderItems(
			@RequestParam("orderId") String orderId,
			@RequestParam(value = "curPage", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		return JsonUtils.toJson(intentionOrderService
				.findIntentionOrderDetailForSysmgr(orderId, pageNumber,
						pageSize));
	}

	/**
	 * 任务分配
	 * @param employeeId
	 * @param id
	 * @param employeeName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "allot", method = RequestMethod.POST)
	@ResponseBody
	public String allot(
			@RequestParam(value = "employeeId", required = true) String employeeId,
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "employeeName", required = true) String employeeName)
			throws Exception {
		if (StringUtils.isEmpty(employeeId) || StringUtils.isEmpty(id)) {
			return null;
		}
		long startTime = System.currentTimeMillis();
		IntentionOrder intentionOrder = intentionOrderService.findByPK(id);
		Employee employee = employeeService.findByPK(employeeId);
		intentionOrder.setEmployee(employee);
		intentionOrder.setAllotTime(new Date());
		intentionOrder.setCooName(employeeService.findByPK(
				UserUtil.getUserFromSession().getCurrentUserId())
				.getEmployeeName());
		intentionOrder.setOrderStatus(IntentionOrderStatusEnum.ALREADY_PROCESS
				.getValue());
		intentionOrderService.update(intentionOrder);
		long endTime = System.currentTimeMillis();
		logger.info("【派发订单执行时间为】" + (endTime - startTime) + "【毫秒】");

		// 给运营人员发送短信
		SmsWithoutPlaceholder indexSms = new SmsWithIndexPlaceholder(
				employee.getMobile(), SmsTemplateKeys.TO_OPERATION_EMPLOYEE,
				ArrayUtils.toArray(intentionOrder.getPurchaserUser()
						.getPurchaser().getOrgName(), ConcurrentDateUtil
						.formatDate(intentionOrder.getDemandDate(),
								DATE_FORMAT_PATTERN)));
		smsService.sendSms(indexSms);

		// 给制单人发送短信
		Map<String, Object> pairs = new HashMap<>();
		pairs.put("employeeMobile", employee.getMobile());
		pairs.put("employeeName", employee.getEmployeeName());
		SmsWithoutPlaceholder namingSms = new SmsWithNamingPlaceholder(
				intentionOrder.getCreateOrderMobile(),
				SmsTemplateKeys.TO_PURCHASER_USER, pairs);
		smsService.sendSms(namingSms);
		return JsonUtils.toJson(intentionOrder);
	}
}
