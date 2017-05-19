package com.zzc.modules.sysmgr.user.employee.dao;

import com.zzc.core.dao.BaseDao;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by wufan on 2015/11/11.
 */
public interface EmployeeDao extends BaseDao<Employee> {

    @Query("select e from Employee e where e.employeeName = :name")
    public Employee findByName(@Param("name") String name);


//    @Query("select e from Employee e left join UserOrgPositionRelation u on e.id = u.id.employee.id where u.id.companyOrg.id = :orgId")
    @Query("select e from Employee e, UserOrgPositionRelation u where e = u.id.employee and u.id.companyOrg.id = :orgId and e.status <> 0")
    public List<Employee> findByOrgId(@Param("orgId") String orgId);


    @Query(value = "select e.*,a.user_name,a.user_pwd, a.create_time, a.modified_time,a.status,a.description,a.last_login_time,a.last_login_ip,a.type" +
            " from SYS_EMPLOYEE e, SYS_ACCOUNT a, SYS_USER_ROLE ur where e.id = a.id and e.id = ur.user_id and ur.role_id = :roleId and a.status = 1", nativeQuery = true)
    public List<Employee> findByRoleId(@Param("roleId") String roleId);
    
    @Query(value = "select e.*,a.user_name,a.user_pwd, a.create_time, a.modified_time,a.status,a.description,a.last_login_time,a.last_login_ip,a.type" +
            " from SYS_EMPLOYEE e, SYS_ACCOUNT a, SYS_USER_ROLE ur, SYS_ROLE r where e.id = a.id and e.id = ur.user_id and ur.role_id = r.id and r.code = :roleCode and (e.name like %:employeeName% or e.mobile like %:employeeName%) and a.status = 1 limit :start,:limit", nativeQuery = true)
    public List<Employee> findByRoleCodeAndEmployeeName(@Param("roleCode") String roleCode, @Param("employeeName") String employeeName, @Param("start") Integer start, @Param("limit") Integer limit);

    // 查询符合条件
    @Query(value = "select distinct e.id id, e.name name, e.mobile mobile" +
            " from SYS_EMPLOYEE e, SYS_ACCOUNT a, SYS_USER_ROLE ur, SYS_ROLE r where e.id = a.id and e.id = ur.user_id and ur.role_id = r.id and r.code = :roleCode and (e.name like %:employeeName% or e.mobile like %:employeeName%) and a.status = 1 limit :start,:limit", nativeQuery = true)
    public List<Object[]> findByRoleCodeAndEmployeeNameForAdministrator(@Param("roleCode") String roleCode, @Param("employeeName") String employeeName, @Param("start") Integer start, @Param("limit") Integer limit);
    
    @Query(value = "select count(*) from SYS_EMPLOYEE e, SYS_ACCOUNT a, SYS_USER_ROLE ur, SYS_ROLE r where e.id = a.id and e.id = ur.user_id and ur.role_id = r.id and r.code = :roleCode and (e.name like %:employeeName% or e.mobile like %:employeeName%) and a.status = 1", nativeQuery = true)
    public long countByRoleCodeAndEmployeeName(@Param("roleCode") String roleCode, @Param("employeeName") String employeeName);
    
}