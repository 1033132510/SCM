/**
 * 
 */
package com.zzc.modules.shop.supplier.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zzc.common.page.PageForShow;
import com.zzc.core.dao.BaseDao;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.shop.supplier.dao.ShopSupplierDao;
import com.zzc.modules.shop.supplier.entity.ShopSupplier;
import com.zzc.modules.shop.supplier.service.ShopSupplierService;

/**
 * @author zhangyong
 *
 */
@Service(value = "shopSupplierService")
public class ShopSupplierServiceImpl extends BaseCrudServiceImpl<ShopSupplier> implements ShopSupplierService {


	private ShopSupplierDao shopSupplierDao;
	
	@Autowired
	public ShopSupplierServiceImpl(BaseDao<ShopSupplier> shopSupplierDao) {
		super(shopSupplierDao);
		this.shopSupplierDao = (ShopSupplierDao) shopSupplierDao;
	}
	
	@Transactional
	@Override
	public boolean addSupplier(ShopSupplier supplier) {
		shopSupplierDao.save(supplier);
		return true;
	}
	
	@Override
	public long countByMobile(String mobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("AND_EQ_mobilePhone", mobile);
		return findAllCount(map, ShopSupplier.class);
	}
	
	@Override
	public PageForShow<ShopSupplier> getSupplierPage(Integer curPage,
			Integer pageSize) {
		Page<ShopSupplier> page = findByParams(ShopSupplier.class, null, curPage, pageSize, "desc", "createTime");
		PageForShow<ShopSupplier> p = PageForShow.newInstanceFromSpringPage(page, curPage);
		return p;
	}

}