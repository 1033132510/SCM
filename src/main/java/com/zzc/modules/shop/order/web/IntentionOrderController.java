package com.zzc.modules.shop.order.web;

import com.zzc.common.security.util.UserUtil;
import com.zzc.common.security.web.user.SessionUser;
import com.zzc.core.exceptions.BizException;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.common.ResultData;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.order.entity.IntentionOrder;
import com.zzc.modules.order.entity.IntentionOrderItem;
import com.zzc.modules.order.service.IntentionOrderItemService;
import com.zzc.modules.order.service.IntentionOrderService;
import com.zzc.modules.sysmgr.product.service.ProductSKUService;
import com.zzc.modules.sysmgr.product.service.vo.ProductSKUBussinessVO;
import com.zzc.modules.sysmgr.user.purchaser.service.PurchaserUserService;
import org.apache.commons.collections.CollectionUtils;
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
 * 订单管理
 */
@Controller("shopIntentionOrder")
@RequestMapping("/shop/intentionOrder")
public class IntentionOrderController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(IntentionOrderController.class);

	@Autowired
	private IntentionOrderService intentionOrderService;

	@Autowired
	private IntentionOrderItemService intentionOrderItemService;

	@Autowired
	private PurchaserUserService purchaserUserService;

	@Autowired
	private ProductSKUService productSKUService;

	private static final Integer HAS_TAX_AND_TRANSPORTATION = 1;

	/**
	 * 订单视图
	 * @return
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public String getIntentionOrderView() {
		SessionUser sessionUser = UserUtil.getUserFromSession();
		if (sessionUser == null) {
			return "redirect:/shop/login";
		}
		return "shop/intentionOrder/intentionOrder";
	}

	/**
	 * 分页
	 * @param pageNumber
	 * @param pageSize
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String getIntentionOrderView(
			@RequestParam(value = "p", required = false) Integer pageNumber,
			@RequestParam(value = "ps", required = false) Integer pageSize,
			@RequestParam(value = "status", required = false) Integer status)
			throws Exception {
		List<Map<String, Object>> intentionOrderList = intentionOrderService
				.getIntentionOrderList(pageNumber, pageSize, status, UserUtil
						.getUserFromSession().getCurrentUserId());
		if (CollectionUtils.isEmpty(intentionOrderList)) {
			return JsonUtils.toJson(new ResultData(Boolean.FALSE));
		}
		return JsonUtils
				.toJson(new ResultData(Boolean.TRUE, intentionOrderList));
	}

	/**
	 * 添加订单
	 * @param intentionOrderJson
	 * @param intentionOrderItemsJson
	 * @param cartIds
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResultData saveIntentionOrder(String intentionOrderJson,
			String intentionOrderItemsJson, String cartIds) throws Exception {
		SessionUser sessionUser = UserUtil.getUserFromSession();
		if (sessionUser == null) {
			return new ResultData(Boolean.FALSE);
		}
		logger.info("【提交订单，采购商】" + sessionUser.getCurrentUserId());
		Boolean result = intentionOrderService.createIntentionOrder(
				intentionOrderJson, intentionOrderItemsJson, cartIds,
				sessionUser.getCurrentUserId());
		return new ResultData(result);
	}

	/**
	 * 订单详情
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detailView/{id}", method = RequestMethod.GET)
	public String getOrderDetailView(@PathVariable("id") String id,
			ModelMap model) {
		model.addAttribute("id", id);
		return "shop/intentionOrder/intentionOrderDetail";
	}

	/**
	 * 查询订单详情
	 * @param id
	 * @param pageNumber
	 * @param pageSize
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	@ResponseBody
	public String orderDetail(
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(name = "p", required = false) Integer pageNumber,
			@RequestParam(name = "ps", required = false) Integer pageSize,
			ModelMap model) throws Exception {
		IntentionOrder intentionOrder = intentionOrderService.findByPK(id);
		if (intentionOrder == null) {
			throw new BizException("参数不正确，查询的订单Id【" + id + "】不存在");
		}
		ResultData resultData = null;
		if (!UserUtil.getUserFromSession().getCurrentUserId()
				.equals(intentionOrder.getPurchaserUser().getId())) {
			resultData = new ResultData(Boolean.FALSE, "您无权限访问此订单", "401");
		} else {
			resultData = new ResultData(Boolean.TRUE,
					intentionOrderService.findIntentionOrderDetail(
							intentionOrder, pageNumber, pageSize));
		}
		return JsonUtils.toJson(resultData);
	}

	/**
	 * 商品展示页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
	public String itemSnap(@PathVariable("id") String id, ModelMap model) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		model.addAttribute("id", id);
		return "shop/intentionOrder/intentionOrderItemSnap";
	}

	@RequestMapping(value = "/productSnap/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String productSnap(@PathVariable("id") String id) {
		IntentionOrderItem intentionOrderItem = intentionOrderItemService
				.findByPK(id);
		if (intentionOrderItem == null) {
			return null;
		}
		Map<String, Object> response = new HashMap<>();
		response.put("quantity", intentionOrderItem.getQuantity());
		response.put("price", intentionOrderItem.getPrice());
		ProductSKUBussinessVO vo = JsonUtils.toObject(intentionOrderItem.getItemSnapShot(), ProductSKUBussinessVO.class);
		if (vo.getHasTax() == null) {
			vo.setHasTax(HAS_TAX_AND_TRANSPORTATION);
		}
		if (vo.getHasTransportation() == null) {
			vo.setHasTransportation(HAS_TAX_AND_TRANSPORTATION);
		}
		response.put("snap", JsonUtils.toJson(vo));
		return JsonUtils.toJson(response);
	}

}
