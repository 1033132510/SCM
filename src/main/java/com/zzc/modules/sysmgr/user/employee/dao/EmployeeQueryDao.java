package com.zzc.modules.sysmgr.user.employee.dao;

import com.zzc.common.page.PageForShow;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;

import java.util.List;

/**
 * Created by hadoop on 2015/12/1.
 */
public interface EmployeeQueryDao {
    /**
     * 根据员工姓名查找员工
     * @param employeeName
     * @return
     */
    public List<Employee> findByEmployeeName (String employeeName);

    /**
     * 分页查询
     * @param employeeName
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageForShow<Employee> findEmployeeByPage (String employeeName, Integer pageNum, Integer pageSize);
}
