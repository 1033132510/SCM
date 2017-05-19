/**
 * 
 */
package com.zzc.modules.shop.purchaser.service;

import com.zzc.common.page.PageForShow;
import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.shop.purchaser.entity.ShopPurchaser;

/**
 * @author zhangyong
 *
 */
public interface ShopPurchaserService extends BaseCrudService<ShopPurchaser>{

	public boolean addPurchaser(ShopPurchaser purchaser);
	
	public long countByMobile(String mobile);
	
	public PageForShow<ShopPurchaser> getPurchaserPage(Integer curPage, Integer pageSize);
	
}