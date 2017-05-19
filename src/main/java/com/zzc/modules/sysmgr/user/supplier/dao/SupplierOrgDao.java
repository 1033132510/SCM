package com.zzc.modules.sysmgr.user.supplier.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zzc.core.dao.BaseDao;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierOrg;

public interface SupplierOrgDao extends BaseDao<SupplierOrg> {

	@Query("select s from SupplierOrg s where s.status=:status and (s.orgCode like %:orgCodeOrorgName% or s.orgName like %:orgCodeOrorgName%)")
	Page<SupplierOrg> findByorgCodeOrorgName(@Param("status") Integer status,@Param("orgCodeOrorgName") String orgCodeOrorgName,
			Pageable pageRequest);
}
