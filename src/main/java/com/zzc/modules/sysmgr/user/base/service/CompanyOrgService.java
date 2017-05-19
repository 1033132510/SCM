package com.zzc.modules.sysmgr.user.base.service;


import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.sysmgr.user.base.entity.CompanyOrg;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wufan on 2015/11/17.
 */
public interface CompanyOrgService extends BaseCrudService<CompanyOrg> {
    /**
     * 根据组织编码查找组织
     *
     * @param orgCode
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public CompanyOrg findByOrgCode(String orgCode);


    /**
     * 根据父组织和组织名称，判断已经存在相同的组织
     * @param parentOrgId
     * @param orgName
     * @return
     */
    public boolean isOrgExsit(String parentOrgId, String orgName);
}

