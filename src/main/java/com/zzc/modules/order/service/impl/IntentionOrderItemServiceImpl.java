package com.zzc.modules.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzc.core.dao.BaseDao;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.order.dao.IntentionOrderItemDao;
import com.zzc.modules.order.entity.IntentionOrderItem;
import com.zzc.modules.order.service.IntentionOrderItemService;

@Service("intentionOrderItemService")
public class IntentionOrderItemServiceImpl extends
		BaseCrudServiceImpl<IntentionOrderItem> implements
		IntentionOrderItemService {

	@SuppressWarnings("unused")
	private IntentionOrderItemDao intentionOrderItemDao;

	@Autowired
	public IntentionOrderItemServiceImpl(BaseDao<IntentionOrderItem> baseDao) {
		super(baseDao);
		this.intentionOrderItemDao = (IntentionOrderItemDao) baseDao;
	}

}
