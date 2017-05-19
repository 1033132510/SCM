package com.zzc.base.sysmgr;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zzc.core.BaseServiceTest;
import com.zzc.modules.order.entity.IntentionOrder;
import com.zzc.modules.order.entity.IntentionOrderItem;
import com.zzc.modules.order.service.IntentionOrderItemService;
import com.zzc.modules.order.service.IntentionOrderService;

public class IntentionOrderItemServiceTest extends BaseServiceTest {

	@Autowired
	private IntentionOrderItemService intentionOrderItemService;

	@Autowired
	private IntentionOrderService intentionOrderService;

	@Test
	public void testCreate() {
		IntentionOrder order = intentionOrderService.findByPK("8a80808b50f564f00150f56506b70000");
		for (int i = 1; i <= 5; i++) {
			IntentionOrderItem item = new IntentionOrderItem();
			item.setIntentionOrder(order);
			item.setQuantity(10);
			intentionOrderItemService.create(item);
		}
	}

}
