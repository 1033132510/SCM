package com.zzc.base.sysmgr;

import com.zzc.core.BaseServiceTest;
import com.zzc.core.utils.JsonUtils;
import com.zzc.modules.sysmgr.enums.AccountTypeEnum;
import com.zzc.modules.sysmgr.enums.RoleTypeEnum;
import com.zzc.modules.sysmgr.user.base.entity.BaseUser;
import com.zzc.modules.sysmgr.user.base.entity.Role;
import com.zzc.modules.sysmgr.user.base.service.BaseUserService;
import com.zzc.modules.sysmgr.user.base.service.RoleService;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.employee.service.EmployeeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wufan on 2015/11/1.
 */
public class RoleServiceTest extends BaseServiceTest {

	@Autowired
	private RoleService roleService;
	@Autowired
	private BaseUserService baseUserService;

	@Autowired
	private EmployeeService employeeService;

	@Test
	public void testCreateRole() {
		Role role = new Role();
		role.setRoleCode("admin");
		role.setRoleName("管理员");
		role.setRoleType(RoleTypeEnum.system.getCode());
		roleService.create(role);
	}

	@Test
	public void testAuthorize () {
//		List<String> oldIds = new ArrayList<>();
//		oldIds.add("4028b8815132881c015132b86d5c0000");

		/*String roleId = "4028817b5119a8f0015119ab7c3e07ea";
		String[] userIds = {"4028b8815132881c015132b86d5c0000", "4028b8815137e0b301513805d0a10000"};

		List<String> userList = (ArrayList)(Arrays.asList(userIds));

		userList.removeAll(oldIds);

		for (String userId : userList) {
			System.out.println(userId);
		}

		for (String userId : userList) {
			Employee employee = employeeService.findByPK(userId);
		}*/


		String roleId = "4028817b5119a8f0015119ab7c3e07ea";
		Role role = roleService.findByPK(roleId);
		String[] userIds = {"4028b8815137e0b301513805d0a10000"};

		List<BaseUser> baseUsers = new ArrayList<>();

		for (String userId : userIds) {
			BaseUser baseuser = baseUserService.findByPK(userId);
			baseUsers.add(baseuser);
		}

		role.setDescription("222");

		role.setUserList(baseUsers);

		roleService.update(role);
	}

	public void testFindByRoleCode () {
		String roleCode = "order_taker";

		Role role = roleService.findByRoleCode(roleCode);

		System.out.println(JsonUtils.toJson(role));
		System.out.println(JsonUtils.toJson(role));
	}

	@Test
	public void testFindByBaseUser() {
		BaseUser baseUser = baseUserService.findByUserNameAndAccountType("system_admin", AccountTypeEnum.employee.getCode());
		List<Role> codes = roleService.findRolesByUser(baseUser);
		System.out.println("role codes:" + JsonUtils.toJson(codes));
	}

}
