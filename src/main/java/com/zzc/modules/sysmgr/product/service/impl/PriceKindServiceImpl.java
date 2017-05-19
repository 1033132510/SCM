package com.zzc.modules.sysmgr.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzc.core.dao.BaseDao;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.sysmgr.product.dao.PriceKindDao;
import com.zzc.modules.sysmgr.product.entity.PriceKind;
import com.zzc.modules.sysmgr.product.service.PriceKindService;

/**
 * 
 * @author apple
 *
 */
@Service("priceKindService")
public class PriceKindServiceImpl extends BaseCrudServiceImpl<PriceKind> implements PriceKindService {

	@SuppressWarnings("unused")
	private PriceKindDao priceKindModelDao;

	@Autowired
	public PriceKindServiceImpl(BaseDao<PriceKind> priceKindModelDao) {
		super(priceKindModelDao);
		this.priceKindModelDao = (PriceKindDao) priceKindModelDao;
	}

}
