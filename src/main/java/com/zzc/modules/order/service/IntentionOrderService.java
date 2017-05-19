package com.zzc.modules.order.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.zzc.common.page.PageForShow;
import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.order.entity.IntentionOrder;
import com.zzc.modules.order.entity.IntentionOrderItem;

public interface IntentionOrderService extends BaseCrudService<IntentionOrder> {

	void create(IntentionOrder intentionOrder,
			List<IntentionOrderItem> intentionOrderItems);

	Page<IntentionOrder> findByCondition(Integer pageNumber, Integer pageSize,
			Integer orderStatus, String orderCodeOrOrgName, boolean isBoss,
			String employeeId);

	Boolean createIntentionOrder(String intentionOrderJson,
			String intentionOrderItemsJson,  String cartIds,String purchserUserId)
			throws Exception;

	Map<String, Object> findIntentionOrderDetail(IntentionOrder intentionOrder,
			Integer pageNumber, Integer pageSize) throws ParseException;

	public PageForShow<Map<String, Object>> findIntentionOrderDetailForSysmgr(
			String orderId, Integer pageNumber, Integer pageSize);

	public List<Map<String, Object>> getIntentionOrderList(Integer pageNumber,
			Integer pageSize, Integer status, String currentUserId)
			throws ParseException;

}
