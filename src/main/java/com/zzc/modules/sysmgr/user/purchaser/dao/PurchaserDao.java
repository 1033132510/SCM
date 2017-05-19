package com.zzc.modules.sysmgr.user.purchaser.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zzc.core.dao.BaseDao;
import com.zzc.modules.sysmgr.user.purchaser.entity.Purchaser;

public interface PurchaserDao extends BaseDao<Purchaser> {

	@Query("select p from Purchaser p where p.orgCode like %:codeOrCompany% or p.orgName like %:codeOrCompany%")
	Page<Purchaser> findByPurchaserCodeOrCompany(
			@Param("codeOrCompany") String codeOrCompany, Pageable pageRequest);

					     
	@Query(nativeQuery = true, value = "select p.id, p.legal_name, p.org_name, p.org_level, p.mobile, p.org_code, p.parent_id, GROUP_CONCAT(i.alias_name), GROUP_CONCAT(i.id), i.path from SYS_PURCHASER p left join SYS_IMAGE i on p.org_code = i.relation_id where p.id = :id and i.relation_type = 1")
	Object[] findPurchaserAndImageById(@Param("id") String id);

}
