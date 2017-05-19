/**
 * 
 */
package com.zzc.modules.shop.purchaser.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zzc.common.page.PageForShow;
import com.zzc.core.dao.BaseDao;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.shop.purchaser.dao.ShopPurchaserDao;
import com.zzc.modules.shop.purchaser.entity.ShopPurchaser;
import com.zzc.modules.shop.purchaser.service.ShopPurchaserService;
import com.zzc.modules.sysmgr.user.base.service.RoleService;

/**
 * @author zhangyong
 *
 */
@Service(value = "shopPurchaserService")
public class ShopPurchaserServiceImpl extends BaseCrudServiceImpl<ShopPurchaser> implements
		ShopPurchaserService {

	private ShopPurchaserDao shopPurchaserDao;
	
	@Resource(name = "roleService")
	private RoleService roleService;
	
	@Autowired
	public ShopPurchaserServiceImpl(BaseDao<ShopPurchaser> shopPurchaserDao) {
		super(shopPurchaserDao);
		this.shopPurchaserDao = (ShopPurchaserDao) shopPurchaserDao;
	}
	
	@Transactional
	@Override
	public boolean addPurchaser(ShopPurchaser purchaser) {
		shopPurchaserDao.save(purchaser);
		return true;
	}
	
	@Override
	public long countByMobile(String mobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("AND_EQ_mobilePhone", mobile);
		return findAllCount(map, ShopPurchaser.class);
	}
	
	@Override
	public PageForShow<ShopPurchaser> getPurchaserPage(Integer curPage,
			Integer pageSize) {
		Page<ShopPurchaser> page = findByParams(ShopPurchaser.class, null, curPage, pageSize, "desc", "createTime");
		PageForShow<ShopPurchaser> p = PageForShow.newInstanceFromSpringPage(page, curPage);
		return p;
	}
	
}