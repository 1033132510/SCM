package com.zzc.modules.sysmgr.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzc.core.dao.BaseDao;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.sysmgr.product.dao.ProductDiscountDao;
import com.zzc.modules.sysmgr.product.entity.ProductDiscount;
import com.zzc.modules.sysmgr.product.service.ProductDiscountService;

/**
 * 
 * @author apple
 *
 */
@Service("productDiscountService")
public class ProductDiscountServiceImpl extends BaseCrudServiceImpl<ProductDiscount> implements ProductDiscountService {

	@SuppressWarnings("unused")
	private ProductDiscountDao productDiscountDao;

	@Autowired
	public ProductDiscountServiceImpl(BaseDao<ProductDiscount> productDiscountDao) {
		super(productDiscountDao);
		this.productDiscountDao = (ProductDiscountDao) productDiscountDao;
	}

	@Override
	public List<ProductDiscount> getDiscount(String cateId, String customerLevel) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (cateId != null) {
			params.put("AND_EQ_cateId", cateId);
		}
		if (customerLevel != null) {
			params.put("AND_EQ_customerLevel", customerLevel);
		}
		List<ProductDiscount> discounts = findAll(params, ProductDiscount.class);
		if (discounts != null && discounts.size() >= 0) {
			return discounts;
		}
		return null;
	}

}
