package com.zzc.base.sysmgr;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zzc.core.BaseServiceTest;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.employee.service.EmployeeService;

/**
 * Created by wufan on 2015/11/1.
 */
public class EmployeeServiceTest extends BaseServiceTest {

	@Autowired
	private EmployeeService employeeService;

	@Test
	public void testSaveEmployee() {
		Employee employee = employeeService.findByPK("4028b88151674f2a0151674fb5670000");
		employee.setEmployeeName("日志测试1");
		employeeService.saveTest(employee);
	}
}
