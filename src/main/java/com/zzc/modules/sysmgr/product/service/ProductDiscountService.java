package com.zzc.modules.sysmgr.product.service;

import java.util.List;

import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.sysmgr.product.entity.ProductDiscount;

/**
 * 
 * @author apple
 *
 */
public interface ProductDiscountService extends BaseCrudService<ProductDiscount> {

	/**
	 * 客户登记得到折扣信息
	 * @param cateId
	 * @param customerLevel
	 * @return
	 */
	List<ProductDiscount> getDiscount(String cateId, String customerLevel);

}
