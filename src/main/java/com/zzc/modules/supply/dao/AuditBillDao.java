package com.zzc.modules.supply.dao;

import com.zzc.core.dao.BaseDao;
import com.zzc.modules.supply.entity.AuditBill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuditBillDao extends BaseDao<AuditBill> {

	@Query(value = "from AuditBill a where a.auditStatus not in :statusList and a.sysProductSKU.id = :sysProductId and a.status = 1")
	public List<AuditBill> findAuditBillBySysProductId(@Param("statusList") List<Integer> statusList, @Param("sysProductId") String sysProductId);
	
	/**
	 * 删除品类管理员品类id时查出还在处理流程中的审批单
	 * @param cateIds
	 * @param employeeId
	 * @param auditStatus 流程结束的状态		SupplyAuditBillStatusEnum
	 * @return
	 */
	@Query(value = "select ab.* from SYS_AUDIT_BILL ab"
			+ " left join SYS_PRODUCT_SKU p on ab.sys_product_sku_id = p.id"
			+ " where ab.audit_status not in :auditStatus and p.second_level_cate_id in :cateIds and ab.category_admin_id = :employeeId", nativeQuery = true)
	public List<AuditBill> findAuditBillByCateIdsToDeleteForCategoryAdmin(@Param("cateIds") List<String> cateIds, @Param("employeeId") String employeeId, @Param("auditStatus") List<Integer> auditStatus);

	/**
	 * 删除部类总监品类id时查出还在处理流程中的审批单
	 * @param cateIds
	 * @param employeeId
	 * @param auditStatus 流程结束的状态		SupplyAuditBillStatusEnum
	 * @return
	 */
	@Query(value = "select ab.*"
			+ " from SYS_AUDIT_BILL ab"
			+ " left join SYS_PRODUCT_SKU p on ab.sys_product_sku_id = p.id"
			+ " where ab.audit_status not in :auditStatus and p.second_level_cate_id in :cateIds and ab.category_admin_id = :employeeId", nativeQuery = true)
	public List<AuditBill> findAuditBillByCateIdsToDeleteForCategoryMajordomo(@Param("cateIds") List<String> cateIds, @Param("employeeId") String employeeId, @Param("auditStatus") List<Integer> auditStatus);
	
}