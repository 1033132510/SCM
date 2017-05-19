package com.zzc.modules.sysmgr.user.supplier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.zzc.common.security.util.UserUtil;
import com.zzc.common.security.web.user.SessionUser;
import com.zzc.core.dao.BaseDao;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.core.utils.JsonUtils;
import com.zzc.modules.sysmgr.user.supplier.dao.SupplierOrgDao;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierOrg;
import com.zzc.modules.sysmgr.user.supplier.service.SupplierOrgService;

@Service("supplierOrgService")
public class SupplierOrgServiceImpl extends BaseCrudServiceImpl<SupplierOrg> implements SupplierOrgService {

	private SupplierOrgDao supplierOrgDao;

	@Autowired
	public SupplierOrgServiceImpl(BaseDao<SupplierOrg> baseDao) {
		super(baseDao);
		// TODO Auto-generated constructor stub
		this.supplierOrgDao = (SupplierOrgDao) baseDao;
	}

	@Override
	public Page<SupplierOrg> findByorgCodeOrorgName(String orgCodeOrorgName, Integer pageNumber, Integer pageSize, Integer status) {
		// TODO Auto-generated method stub
		return supplierOrgDao.findByorgCodeOrorgName(status, orgCodeOrorgName, new PageRequest(pageNumber - 1, pageSize));
	}
	
	@Override
	public SupplierOrg save(SupplierOrg supplierOrg){
		return supplierOrgDao.save(supplierOrg);
	}

	@Override
	public String logInformation(SupplierOrg supplierOrg) {
		// TODO Auto-generated method stub
		return JsonUtils.toJson(supplierOrg);
	}

	@Override
	public String userLog() {
		// TODO Auto-generated method stub
		SessionUser sessionUser = UserUtil.getUserFromSession();
		return JsonUtils.toJson(sessionUser);
	}

}
