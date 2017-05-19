package com.zzc.modules.sysmgr.user.purchaser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzc.common.security.util.UserUtil;
import com.zzc.common.security.web.user.SessionUser;
import com.zzc.core.dao.BaseDao;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.core.utils.JsonUtils;
import com.zzc.modules.sysmgr.user.purchaser.dao.PurchaserUserDao;
import com.zzc.modules.sysmgr.user.purchaser.entity.PurchaserUser;
import com.zzc.modules.sysmgr.user.purchaser.service.PurchaserUserService;

@Service("purchaserUserService")
public class PurchaserUserServiceImpl extends BaseCrudServiceImpl<PurchaserUser>
		implements PurchaserUserService {

	@Autowired
	private PurchaserUserDao purchaserUserDao;

	@Autowired
	public PurchaserUserServiceImpl(BaseDao<PurchaserUser> baseDao) {
		super(baseDao);
		this.purchaserUserDao = (PurchaserUserDao) baseDao;
	}

	@Override
	public PurchaserUser save(PurchaserUser purchaserUser) {
		// TODO Auto-generated method stub
		return purchaserUserDao.save(purchaserUser);
	}
	
	@Override
	public String logInformation(PurchaserUser purchaserUser) {
		// TODO Auto-generated method stub
		return JsonUtils.toJson(purchaserUser);
	}

	@Override
	public String userLog() {
		// TODO Auto-generated method stub
		SessionUser sessionUser = UserUtil.getUserFromSession(); 
		return JsonUtils.toJson(sessionUser);
	}

}
