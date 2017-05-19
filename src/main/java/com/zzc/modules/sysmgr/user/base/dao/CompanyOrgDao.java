package com.zzc.modules.sysmgr.user.base.dao;


import com.zzc.core.dao.BaseDao;
import com.zzc.modules.sysmgr.user.base.entity.CompanyOrg;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by wufan on 2015/11/17.
 */
public interface CompanyOrgDao extends BaseDao<CompanyOrg> {
    /**
     * 根据组织编码查找组织
     *
     * @param orgCode
     * @return
     */
    public CompanyOrg findByOrgCode(String orgCode);

    @Query("select o from CompanyOrg o where o.orgName = :orgName and o.parentOrg.id = :parentId")
    public CompanyOrg findByNameAndParentOrgId(@Param("orgName") String orgName, @Param("parentId") String parentId);
}
