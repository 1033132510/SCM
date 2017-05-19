package com.zzc.modules.sysmgr.user.employee.service;


import com.zzc.common.page.PageForShow;
import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.employee.service.vo.EmployeeVO;

import java.util.List;

/**
 * Created by wufan on 2015/11/1.
 */
public interface EmployeeService extends BaseCrudService<Employee> {

    /**
     * 测试@Query用法
     * 根据员工姓名查找员工
     * @param employeeName
     * @return
     */
    public Employee findByName(String employeeName);

    /**
     * 根据所在组织查询员工信息
     * @param orgId
     * @return
     */
    public List<Employee> findByOrgId(String orgId);
    /**
     * 根据角色Id查询拥有该角色的所有员工信息
     * @param roleId
     * @return
     */
    public List<Employee> findByRoleId(String roleId);

    /**
     * 根据员工姓名，模糊查询接单员数据
     * @param employeeName
     * @return
     */
    /*@OperateLog(targetId="$args.get(0)",
            targetName="$args.get(0)",
            operateType= OperateType.FIND,
            description="用户查询",
            evalText="操作：查询员工信息\n" +
                    "查询条件：'$args.get(0)';",
            end="")*/
    public List<Employee> findOrderTaker(String employeeName);

    /**
     * 根据员工姓名查找员工
     * @param employeeName
     * @return
     */
    public List<Employee> findByEmployeeName(String employeeName);

    /**
     * 分页查询
     * @param employeeName
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageForShow<Employee> findByPage(String employeeName, Integer pageNum, Integer pageSize);

    /*@OperateLog(begin="$employee=findByPK($args[0].id)",
            targetId="$args.get(0).id",
            targetName="$!employee.employeeName",
            operateType= OperateType.UPDATE,
            description="用户",
            evalText="操作：更新员工信息\n" +
                    "员工姓名:由'$!employee.employeeName'更新为'$args.get(0).employeeName';",
            end="")*/
    public Employee saveTest(Employee employee) ;
    
    /**
     * 通过员工姓名关键字搜索
     * @param employeeName
     * @return
     */
    public PageForShow<EmployeeVO> findPageByEmployeeName(Integer curPage, Integer pageSize, String employeeName, Integer type);


    /**
     * 保存员工信息（新增或修改）
     * @param employee
     * @return
     */
    public Employee saveEmployee(Employee employee);
    
    public List<Employee> getEmployeeListByKeywords(String employeeName, Integer type, Integer limit);
    
    public void updateEmployeeStatus(String employeeId, Integer status);
}