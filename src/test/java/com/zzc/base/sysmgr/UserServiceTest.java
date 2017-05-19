package com.zzc.base.sysmgr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzc.common.page.PageForShow;
import com.zzc.core.BaseServiceTest;
import com.zzc.core.utils.JsonUtils;
import com.zzc.modules.sysmgr.enums.AccountTypeEnum;
import com.zzc.modules.sysmgr.user.base.entity.Role;
import com.zzc.modules.sysmgr.user.base.service.BaseUserService;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.base.entity.BaseUser;
import com.zzc.modules.sysmgr.user.employee.service.EmployeeService;
import com.zzc.modules.sysmgr.user.base.service.RoleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wufan on 2015/11/1.
 */
public class UserServiceTest extends BaseServiceTest {

    @Autowired
    private BaseUserService baseUserService;
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RoleService roleService;

//    @Transactional
//    @Rollback(true)
    @Test
    public void testCreateSysUser() {
//        SysUser sysUser = new SysUser();
//        sysUser.setUserName("wufan3");
//        sysUser.setUserPwd("111111");
//        sysUser.setDescription("test");

//        sysUserService.create(sysUser);

//        for (int i = 10; i < 2; i++) {
//            BaseUser sysUser = new BaseUser();
//            sysUser.setUserName("wufan" + i);
//            sysUser.setUserPwd("111111");
//            sysUser.setDescription("test" + i);
//
//            baseUserService.create(sysUser);

            Employee employee = new Employee();
            employee.setEmployeeName("超级管理员");
            employee.setDescription("超级管理员");
            employee.setEmail("admin@zhongzhicai.com.cn");
            employee.setMobile("s");
            employee.setSex("1");
            employee.setType("1");

//        System.out.println(JSON.toJSONString(employee));
//            employee.setCreateTime(new Timestamp(new Date().getTime()));
//            employee.setModifiedTime(new Timestamp(new Date().getTime()));
//            employee.setStatus(1);
            employeeService.create(employee);
//        }


    }

    @Transactional
    @Rollback(true)
    @Test
    public void testUpdateSysUser() {
        BaseUser sysUser = baseUserService.findByUserNameAndAccountType("18501278063", AccountTypeEnum.employee.getCode());

        if (sysUser != null) {
            sysUser.setDescription("update test");
            sysUser.setModifiedTime(new Timestamp(new Date().getTime()));
            baseUserService.update(sysUser);
        }
    }

    @Transactional
    @Rollback(true)
    @Test
    public void testAddRole() {
        BaseUser sysUser = baseUserService.findByUserNameAndAccountType("18501278063", AccountTypeEnum.employee.getCode());
        Role role = roleService.findByRoleCode("admin");

        if (sysUser != null && role != null) {
            sysUser.getRoleList().add(role);
        }

        baseUserService.update(sysUser);
    }


    @Test
    public void testFindByUserName() {
        BaseUser user = baseUserService.findByUserNameAndAccountType("18501278063", AccountTypeEnum.employee.getCode());

        if (AccountTypeEnum.employee.getCode().equals(user.getType())) {
            Employee employee = (Employee)user;
            System.out.println("employee name:" + employee.getEmployeeName());
        }
    }


    @Test
    public void testFindValidData() throws Exception{
        Map<String, Object> params = new HashMap<>();
        params.put("AND_NEQ_status", 0);

        List<Employee> employees = employeeService.findAll(params, Employee.class, "desc", "createTime");
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("==================" + mapper.writeValueAsString(employees));
    }

    @Test
    public void testFindByNameForQuery() {
        Employee employee = employeeService.findByName("吴凡");
        System.out.println("==================" + JsonUtils.toJson(employee));
    }

    @Test
    public void testFindAll() {
        Map<String, Object> params = new HashMap<>();
        params.put("AND_LIKE_name", "2");
        List<Employee> employees = employeeService.findAll(params, Employee.class);

        System.out.println("==============================" + JsonUtils.toJson(employees));
    }

    @Test
    public void testBaseUserPageQuery() {
        Map<String, Object> params = new HashMap<>();
        params.put("AND_LIKE_userName", "WU");
        params.put("OR_EQ_status", 0);
//        params.put("EQ_description", "test");
        int pageNum = 1;
        int pageSize = 4;

        Long time1 = System.currentTimeMillis();
        Page<BaseUser> results = baseUserService.findByParams(BaseUser.class, params, pageNum, pageSize, "desc", "createTime");
        Long time2 = System.currentTimeMillis();
        System.out.println(time2 - time1);

        System.out.println("==============================" + JsonUtils.toJson(results));

    }

    @Test
    public void testEmployeePageQuery() {
        Map<String, Object> params = new HashMap<>();

        params.put("OR_EQ_description", "超级管理员");
        params.put("AND_LIKE_employeeName", "WUFAN");
        params.put("AND_LLIKE_employeeNO", "234");
        int pageNum = 1;
        int pageSize = 4;

        Long time1 = System.currentTimeMillis();
        Page<Employee> results = employeeService.findByParams(Employee.class, params, pageNum, pageSize, "desc", "createTime");
        Long time2 = System.currentTimeMillis();
        System.out.println(time2 - time1);

        System.out.println("==============================" + JsonUtils.toJson(results));

    }

    @Test
    public void testFindByRole() {
        List<BaseUser> result = baseUserService.findByRole("admin");
        System.out.println("==================" + JsonUtils.toJson(result));
    }


    @Test
    public void testfindByOrgId () {
        List<Employee> employees = employeeService.findByOrgId("4028b8815132f1720151330e36950000");
        System.out.println("employees:" + JsonUtils.toJson(employees));
    }

    @Test
    public void testFindByRoleId () {
        List<Employee> employees = employeeService.findByRoleId("4028b88151705f990151705fdd8e0000");
        System.out.println("employees:" + JsonUtils.toJson(employees));
    }

    @Test
    public void testFindOrderTaker() {
        List<Employee> employees = employeeService.findOrderTaker("1");
        System.out.println(JsonUtils.toJson("order takers:" + JsonUtils.toJson(employees)));
    }


    @Test
    public void testFindByEmployeeQuery() {
        List<Employee> employees = employeeService.findByEmployeeName("吴凡");
        System.out.println("with params：" + JsonUtils.toJson(employees));

        List<Employee> employees1 = employeeService.findByEmployeeName("");
        System.out.println("with no params：" + JsonUtils.toJson(employees1));

    }

    @Test
    public void testFindByEmployeePageQuery() {
        PageForShow<Employee> employees = employeeService.findByPage("吴凡", 1, 1);
        System.out.println("with params：" + JsonUtils.toJson(employees));

        PageForShow<Employee> employees1 = employeeService.findByPage("", 2, 1);
        System.out.println("with no params：" + JsonUtils.toJson(employees1));

    }

    @Test
    public void testFindByUserNameAndType() {
        BaseUser baseUser = baseUserService.findByUserNameAndAccountType("18501278063", AccountTypeEnum.employee.getCode());
        System.out.println("testFindByUserNameAndType:" + JsonUtils.toJson(baseUser));
    }
}
