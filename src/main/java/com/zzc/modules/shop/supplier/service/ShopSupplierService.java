/**
 * 
 */
package com.zzc.modules.shop.supplier.service;

import com.zzc.common.page.PageForShow;
import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.shop.supplier.entity.ShopSupplier;

/**
 * @author zhangyong
 *
 */
public interface ShopSupplierService extends BaseCrudService<ShopSupplier>{

	public boolean addSupplier(ShopSupplier supplier);
	
	public long countByMobile(String mobile);
	
	public PageForShow<ShopSupplier> getSupplierPage(Integer curPage, Integer pageSize);
	
}