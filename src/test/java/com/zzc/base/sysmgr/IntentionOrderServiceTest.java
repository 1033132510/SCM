package com.zzc.base.sysmgr;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.zzc.core.BaseServiceTest;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.modules.order.entity.IntentionOrder;
import com.zzc.modules.order.entity.IntentionOrderItem;
import com.zzc.modules.order.service.IntentionOrderService;
import com.zzc.modules.sysmgr.user.purchaser.entity.PurchaserUser;

public class IntentionOrderServiceTest extends BaseServiceTest {

	
	@Autowired
	private IntentionOrderService intentionOrderService;
	
	
	@Test
	public void testCreate() {
		for(int i = 0; i < 10; i++) {
			IntentionOrder intentionOrder = new IntentionOrder();
			intentionOrder.setDemandDate(new Date(System.currentTimeMillis()));
			intentionOrder.setProjectDescription("项目描述");
			intentionOrder.setProjectName("项目名称");
			intentionOrder.setOrderCode("1000009090" + i);
			intentionOrder.setOrderStatus(0);
			intentionOrder.setStatus(CommonStatusEnum.有效.getValue());
			PurchaserUser purchaserUser = new PurchaserUser();
			purchaserUser.setId("40288135513c9b1501513c9c36e20000");
			intentionOrder.setPurchaserUser(purchaserUser);
			intentionOrderService.create(intentionOrder);
		}
	}
	
	@Test
	public void testUpdate() {
		IntentionOrder intentionOrder = intentionOrderService.findByPK("8a80808b50f564f00150f56506b70000");
		intentionOrderService.update(intentionOrder);
		System.err.println(intentionOrder);
	}
	
	@Test
	public void testQueryByPK() {
		IntentionOrder intentionOrder = intentionOrderService.findByPK("8a80808b50f564f00150f56506b70000");
		for(IntentionOrderItem orderItem : intentionOrder.getIntentionOrderItems()) {
			System.err.println(orderItem.getQuantity());
		}
	}
	
	@Test
	public void testDelete() {
		IntentionOrder intentionOrder = intentionOrderService.findByPK("8a80808b50f564f00150f56508a10009");
		intentionOrderService.delete(intentionOrder);
	}
	
	@Test
	public void testQueryByPagerInfo() {
		Map<String, Object> params = new HashMap<>();
		Page<IntentionOrder> intentionOrderPager = intentionOrderService.findByParams(IntentionOrder.class, params, 1, 3, "DESC", "orderStatus");
		System.err.println(intentionOrderPager.getTotalPages());
	}
	
}
