package com.zzc.modules.sysmgr.user.base.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzc.common.enums.EnumUtil;
import com.zzc.common.page.PageForShow;
import com.zzc.core.exceptions.ValidateException;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.common.ResultData;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.sysmgr.constant.SystemConstants;
import com.zzc.modules.sysmgr.enums.RoleTypeEnum;
import com.zzc.modules.sysmgr.product.entity.CategoryCharge;
import com.zzc.modules.sysmgr.product.service.CategoryChargeService;
import com.zzc.modules.supply.service.AuditBillService;
import com.zzc.modules.sysmgr.user.base.entity.BaseUser;
import com.zzc.modules.sysmgr.user.base.entity.Role;
import com.zzc.modules.sysmgr.user.base.service.BaseUserService;
import com.zzc.modules.sysmgr.user.base.service.RoleService;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.employee.service.EmployeeService;

/**
 * Created by wufan on 2015/11/15.
 */
@Controller
@RequestMapping("/sysmgr/role")
class RoleController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private BaseUserService baseUserService;
	
    @Autowired
	private AuditBillService auditBillService;
	
	@Autowired
	private CategoryChargeService categoryChargeService;
	
	// 角色类型，所有controller线程共享
	private Map<String, String> roleTypes;

	/**
	 * 进入角色管理列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public String list(Map<String, Object> map) {
		prepareInitDatas(map);
		return "sysmgr/role/roleList";
	}

	/**
	 * 分页查询角色信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page")
	public String list(@RequestParam("curPage") Integer pageNumber, @RequestParam("pageSize") Integer pageSize,
			@RequestParam(name = "searchParam", required = false) String searchParam,
			@RequestParam(name = "roleType", required = false) String roleType) throws Exception {

		Map<String, Object> params = new HashMap<>();

		if (StringUtils.isNotEmpty(searchParam)) {
			params.put("AND_LIKE_roleName", searchParam);
		}

		if (StringUtils.isNotEmpty(roleType)) {
			params.put("AND_EQ_roleType", roleType);
		}

		Page<Role> results = roleService.findByParams(Role.class, params, pageNumber, pageSize, "desc", "createTime");

		return JsonUtils.toJson(PageForShow.newInstanceFromSpringPage(results, pageNumber));
	}

	/**
	 * 进入角色维护页面 - 添加
	 *
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String toAddPage(Map<String, Object> map) {
		prepareInitDatas(map);
		map.put("role", new Role());
		return "sysmgr/role/roleInfo";
	}

	/**
	 * 添加或修改角色
	 *
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public ResultData saveRole(@Valid @RequestBody Role role, BindingResult bindingResults) {
		if (bindingResults.hasErrors()) {
			log.error("后台数据校验错误");
			throw new ValidateException(bindingResults);
		}
		role = roleService.save(role);
		return new ResultData(true, role);
	}

	/**
	 * 进入角色维护页面 - 修改
	 *
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
	public String toAddPage(@PathVariable String id, Map<String, Object> map) {
		prepareInitDatas(map);
		map.put("role", roleService.findByPK(id));
		return "sysmgr/role/roleInfo";
	}

	/**
	 * 删除角色信息
	 * 
	 * @param roleCode
	 * @return
	 */
	/*
	 * @RequestMapping(value = "/info/{roleId}", method = RequestMethod.DELETE)
	 * public String deleteRole(@PathVariable String roleId) {
	 * roleService.delete(roleId); return "redirect:/sysmgr/role"; }
	 */

	@ResponseBody
	@RequestMapping(value = "/checkRoleCodeIsExist/{roleCode}", method = RequestMethod.GET)
	public Boolean checkRoleCodeIsExist(@PathVariable String roleCode) {
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_roleCode", roleCode);
		List<Role> roles = roleService.findAll(params, Role.class);
		if (roles != null && roles.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/checkRoleNameIsExist/{roleName}", method = RequestMethod.GET)
	public Boolean checkRoleNameIsExist(@PathVariable String roleName) {
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_roleName", roleName);
		List<Role> roles = roleService.findAll(params, Role.class);
		if (roles != null && roles.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 准备页面的初始化数据
	 * 
	 * @param map
	 */
	private void prepareInitDatas(Map<String, Object> map) {
		roleTypes = EnumUtil.toMap(RoleTypeEnum.class);
		map.put("roleTypes", roleTypes);
	}

	@RequestMapping(value = "/authorizeUsers/{roleId}", method = RequestMethod.GET)
	public String toAuthorizeUsers(@PathVariable String roleId, Map<String, Object> map) {
		Role role = roleService.findByPK(roleId);
		map.put("role", role);

		List<Employee> employees = employeeService.findByRoleId(roleId);
		map.put("employees", employees);
		return "sysmgr/role/authorizeUserList";
	}

	/**
	 * 用户授权
	 * 
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/authorizeUsers", method = RequestMethod.POST)
	public boolean authorizeUsers(@RequestBody Role role) {
		try {
			String[] userIds = role.getUserIds();
			Role persistRole = roleService.findByPK(role.getId());

			// 当前角色分配的新用户
			List<BaseUser> newUsers = new ArrayList<>();
			if (userIds != null && userIds.length > 0) {
				for (String userId : userIds) {
					BaseUser baseUser = baseUserService.findByPK(userId);
					newUsers.add(baseUser);
				}
			}
			persistRole.setUserList(newUsers);

			boolean flag = deleteCategoryCharge(persistRole, userIds);
			if(flag) {
				roleService.update(persistRole);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("给ID为" + role.getId() + "的角色授权失败", e.getMessage());
			return false;
		}
	}

	/**
	 * 删除岗位信息，如果该角色下的员工
	 * 
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/remove/{roleId}", method = RequestMethod.PUT)
	public Boolean deleteRole(@PathVariable String roleId) {

		Role role = roleService.findByPK(roleId);
		if (role != null && role.getUserList() != null && role.getUserList().size() > 0) {
			return false;
		} else {
			roleService.delete(role);
			return true;
		}
	}
	
	private boolean deleteCategoryCharge(Role role, String[] userIds) {
		String category_administrator = SystemConstants.CATEGORY_ADMINISTRATOR,
		category_majordomo = SystemConstants.CATEGORY_MAJORDOMO,
		roleCode = role.getRoleCode();
		List<CategoryCharge> categoryChargeList = new ArrayList<CategoryCharge>();
		if(roleCode.equals(category_administrator) || roleCode.equals(category_majordomo)) {
			List<String> ids = Arrays.asList(userIds);
			List<String> deleteUserIds = new ArrayList<String>();
			List<String> idList = baseUserService.findByRoleId(role.getId());
			if(idList.size() != 0) {
				// 该角色下所有用户都将被删除
				if(ids.size() == 0) {
					for(String id : idList) {
						deleteUserIds.add(id);
					}
				} else {
					for(String id : idList) {
						boolean flag = true;
						for(String saveId : ids) {
							if(id.equals(saveId)) {
								flag = false;
								break;
							}
						}
						// 如果id即将删除，添加到deleteUserIds
						if(flag) {
							deleteUserIds.add(id);
						}
					}
				}
			}
			
			if(deleteUserIds.size() > 0) {
				categoryChargeList = categoryChargeService.findByUserIds(deleteUserIds);
			} else {
				return true;
			}
			// 查看该角色是否是品类管理员或者部类总监，如果是，查询这些员工中是否还存在未处理完的审批单
			if(categoryChargeList != null && categoryChargeList.size() > 0) {
				return false;
			}
			categoryChargeService.deleteByUserIds(deleteUserIds);
			return true;
		} else {
			return true;
		}
	}
	
}