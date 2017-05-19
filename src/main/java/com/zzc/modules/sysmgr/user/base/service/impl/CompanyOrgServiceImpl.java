package com.zzc.modules.sysmgr.user.base.service.impl;

import com.zzc.core.dao.BaseDao;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.sysmgr.user.base.dao.CompanyOrgDao;
import com.zzc.modules.sysmgr.user.base.entity.CompanyOrg;
import com.zzc.modules.sysmgr.user.base.service.CompanyOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wufan on 2015/11/17.
 */
@Service("companyOrgService")
public class CompanyOrgServiceImpl extends BaseCrudServiceImpl<CompanyOrg> implements CompanyOrgService {

    private CompanyOrgDao companyOrgDao;

    @Autowired
    public CompanyOrgServiceImpl(BaseDao<CompanyOrg> companyOrgDao) {
        super(companyOrgDao);
        this.companyOrgDao = (CompanyOrgDao) companyOrgDao;
    }

    @Override
    public CompanyOrg findByOrgCode(String orgCode) {
        return companyOrgDao.findByOrgCode(orgCode);
    }

    @Override
    public boolean isOrgExsit(String parentOrgId, String orgName) {
        CompanyOrg companyOrg = companyOrgDao.findByNameAndParentOrgId(orgName, parentOrgId);
        if (companyOrg != null) {
            return true;
        } else {
            return false;
        }
    }
}

