package com.zzc.modules.sysmgr.user.supplier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzc.common.security.util.UserUtil;
import com.zzc.common.security.web.user.SessionUser;
import com.zzc.core.dao.BaseDao;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.core.utils.JsonUtils;
import com.zzc.modules.sysmgr.user.supplier.dao.SupplierUserDao;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierUser;
import com.zzc.modules.sysmgr.user.supplier.service.SupplierUserService;

@Service("supplierUserService")
public class SupplierUserServiceImpl extends BaseCrudServiceImpl<SupplierUser> implements SupplierUserService {

	private SupplierUserDao supplierUserDao;

	@Autowired
	public SupplierUserServiceImpl(BaseDao<SupplierUser> baseDao) {
		super(baseDao);
		// TODO Auto-generated constructor stub
		this.supplierUserDao = (SupplierUserDao) baseDao;
	}
	
	@Override
	public SupplierUser save(SupplierUser supplierUser) {
		// TODO Auto-generated method stub
		return supplierUserDao.save(supplierUser);
	}
	
	@Override
	public String logInformation(SupplierUser supplierUser) {
		// TODO Auto-generated method stub
		return JsonUtils.toJson(supplierUser);
	}

	@Override
	public String userLog() {
		// TODO Auto-generated method stub
		SessionUser sessionUser = UserUtil.getUserFromSession(); 
		return JsonUtils.toJson(sessionUser);
	}

}
