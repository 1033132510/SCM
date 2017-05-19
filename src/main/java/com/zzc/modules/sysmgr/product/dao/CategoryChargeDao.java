/**
 * 
 */
package com.zzc.modules.sysmgr.product.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zzc.core.dao.BaseDao;
import com.zzc.modules.sysmgr.product.entity.CategoryCharge;

/**
 * @author zhangyong
 *
 */
public interface CategoryChargeDao extends BaseDao<CategoryCharge> {
	
	@Modifying
	@Query("delete from CategoryCharge cc where cc.employee.id = :employeeId")
	public int deleteCategoryChargeByEmployeeId(@Param("employeeId")String employeeId);
	
	@Modifying
	@Query("delete from CategoryCharge cc where cc.employee.id = :employeeId and cc.category.id = :cateId")
	public int deleteCategoryChargeByEmployeeIdAndCateId(@Param("employeeId")String employeeId, @Param("cateId")String cateId);
	
	@Modifying
	@Query("delete from CategoryCharge cc where cc.employee.id = :employeeId and cc.category.id in :cateIds")
	public int deleteCategoryChargeByEmployeeIdAndCateIds(@Param("employeeId")String employeeId, @Param("cateIds")List<String> cateIds);
	
	@Query("select count(*) from CategoryCharge cc, Category category where cc.employee.id = :employeeId"
			+ " and cc.category.id = category.parentCategory.id and category.level = :level and category.status = :status"
			+ " and category.id = :secondLevelCateId")
	public long countByEmployeeIdAndSecondLevelCateId(@Param("employeeId")String employeeId,
			@Param("secondLevelCateId")String secondLevelCateId, @Param("level")Integer level, @Param("status")Integer status);
	
	@Query("from CategoryCharge cc where cc.employee.id = :employeeId and cc.category.id = :cateId")
	public List<CategoryCharge> getCategoryChargeByEmployeeIdAndCateId(@Param("employeeId")String employeeId, @Param("cateId")String cateId);
	
	@Query(value = "select cc.*"
			+ " from ES_CATEGORY_CHARGE cc"
			+ " left join SYS_USER_ROLE ur on cc.employee_id = ur.user_id"
			+ " left join SYS_ROLE r on ur.role_id = r.id"
			+ " where r.`code` = :roleCode and cc.category_id = :cateId", nativeQuery = true)
	public List<CategoryCharge> getCategoryChargeByCategoryIdAndRoleCode(@Param("cateId")String cateId, @Param("roleCode")String roleCode);
	
	@Query(value = "select cc.category_id"
			+ " from ES_CATEGORY_CHARGE cc"
			+ " left join ES_CATEGORY c on cc.category_id = c.id and c.level = 2"
			+ " where cc.employee_id = :employeeId", nativeQuery = true)
	public List<String> getCateIdsByEmployeeId(@Param("employeeId") String employeeId);
	
	/**
	 * 获取相同角色下其他员工管理的品类id
	 * @param employeeId
	 * @param roleCode
	 * @return
	 */
	@Query(value = "select DISTINCT cc.category_id id"
			+ " from ES_CATEGORY_CHARGE cc"
			+ " left join ES_CATEGORY c on cc.category_id = c.id"
			+ " left join SYS_USER_ROLE ur on cc.employee_id = ur.user_id"
			+ " left join SYS_ROLE r on ur.role_id = r.id"
			+ " where r.code = :roleCode and cc.employee_id != :employeeId and c.level = 2", nativeQuery = true)
	public List<String> getCateIdsByEmployeeIdForOthers(@Param("employeeId") String employeeId, @Param("roleCode") String roleCode);
	
	@Query(value = "select distinct cc.*"
			+ " from ES_CATEGORY_CHARGE cc"
			+ " left join ES_CATEGORY c on cc.category_id = c.id"
			+ " left join SYS_USER_ROLE ur on cc.employee_id = ur.user_id"
			+ " left join SYS_AUDIT_BILL ab on (cc.employee_id = ab.category_admin_id or cc.employee_id = ab.category_director_id)"
			+ " where cc.employee_id in :userIds and ab.audit_status not in :auditStatus and c.level = 2", nativeQuery = true)
	public List<CategoryCharge> findByUserIdsAndAuditStatus(@Param("userIds") List<String> userIds, @Param("auditStatus") List<Integer> auditStatus);
	
	/**
	 * 查看正在审批流程中的品类 ---------该sql有问题，如果不需要，等测试后删除掉！！！！！！！！
	 * @param cateIds
	 * @param employeeId
	 * @return
	 */
	@Query(value = "select distinct sps.id"
			+ " from SYS_AUDIT_BILL ab"
			+ " left join SYS_PRODUCT_SKU sps on ab.sys_product_sku_id = sps.id"
			+ " left join ES_CATEGORY_CHARGE cc on sps.second_level_cate_id = cc.id"
			+ " where ab.audit_status not in :auditStatus and sps.second_level_cate_id in :cateIds and (ab.category_admin_id = :employeeId or ab.category_director_id = :employeeId)", nativeQuery = true)
	public List<String> findCateIdsInAuditingByEmployeeIdAndCateIds(@Param("cateIds") List<String> cateIds, @Param("employeeId") String employeeId, @Param("auditStatus") List<Integer> auditStatus);
	
}