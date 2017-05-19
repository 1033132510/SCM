package com.zzc.modules.sysmgr.user.employee.web;

import com.zzc.common.enums.EnumUtil;
import com.zzc.common.page.PageForShow;
import com.zzc.common.security.util.UserUtil;
import com.zzc.common.security.web.user.SessionUser;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.exceptions.ValidateException;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.common.ResultData;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.sysmgr.enums.AccountTypeEnum;
import com.zzc.modules.sysmgr.enums.EmployeeStatusEnum;
import com.zzc.modules.sysmgr.enums.EmployeeTypeEnum;
import com.zzc.modules.sysmgr.enums.SexEnum;
import com.zzc.modules.sysmgr.user.base.entity.BaseUser;
import com.zzc.modules.sysmgr.user.base.entity.UserOrgPositionRelation;
import com.zzc.modules.sysmgr.user.base.entity.UserOrgPositionRelationPK;
import com.zzc.modules.sysmgr.user.base.service.BaseUserService;
import com.zzc.modules.sysmgr.user.base.service.UserOrgPositionRelationService;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.employee.service.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wufan on 2015/11/11.
 */
@Controller
@RequestMapping("/sysmgr/employee")
class EmployeeController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private BaseUserService baseUserService;
    @Autowired
    private UserOrgPositionRelationService userOrgPositionRelationService;

    // 性别类型，所有controller线程共享
    private Map<String, String> sexTypes;
    // 在职状态
    private Map<String, String> employeeStatus;
    // 员工类型
    private Map<String, String> employeeTypes;


    /**
     * 进入员工信息列表页面
     * @param map
     * @return
     */
    @RequestMapping(value = "view", method = RequestMethod.GET)
    public String list(Map<String, Object> map) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("AND_NEQ_status", 0);
//
//        map.put("employees", employeeService.findAll(params, Employee.class));

        return "sysmgr/employee/employeeList";
    }


    /**
     * 分页查询
     * @param pageNumber
     * @param pageSize
     * @param searchParam
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    public String listByPage(
            @RequestParam("curPage") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(name = "searchParam", required = false) String searchParam) throws Exception {
        // 查询参数
        Map<String, Object> params = new HashMap<>();
//        params.put("AND_NEQ_status", 0);
        if (StringUtils.isNotEmpty(searchParam)) {
            params.put("AND_LIKE_employeeName", searchParam);
            params.put("OR_LIKE_mobile", searchParam);
        }

        Page<Employee> results = employeeService.findByParams(Employee.class, params, pageNumber, pageSize, "desc", "createTime");

        return JsonUtils.toJson(PageForShow.newInstanceFromSpringPage(results, pageNumber));
    }
    
    @RequestMapping(value = "/getEmployeeListByKeywords")
    @ResponseBody
    public String getEmployeeListByKeywords(@RequestParam("employeeName") String employeeName,
    		@RequestParam("type") Integer type,
    		@RequestParam("limit") Integer limit) {
    	return JsonUtils.toJson(employeeService.getEmployeeListByKeywords(employeeName, type, limit));
    }

    /**
     * 进入添加员工信息页面
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String toAddPage(Map<String, Object> map) {
        prepareInitDatas(map);
        Employee employee = new Employee();
        employee.setType(AccountTypeEnum.employee.getCode());
        employee.setStatus(CommonStatusEnum.有效.getValue());
        map.put("employee", employee);

        return "sysmgr/employee/employeeInfo";
    }

    /**
     * 修改员工信息页面
     *
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public String toAddPage(@PathVariable String id, Map<String, Object> map) {
        map.put("employee", employeeService.findByPK(id));
        prepareInitDatas(map);
        return "sysmgr/employee/employeeInfo";
    }

    /**
     * 新增或修改员工信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public ResultData saveEmployee(@Valid @RequestBody Employee employee, BindingResult bindingResults) {
        // 如果数据校验错误
        if (bindingResults.hasErrors()) {
            log.error("后台数据校验错误");
            throw new ValidateException(bindingResults);
        }

        employee = employeeService.saveEmployee(employee);
        return new ResultData(true, employee);
    }

    /**
     * 删除用户
     * @param employeeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/remove/{employeeId}", method = RequestMethod.POST)
    public ResultData deleteEmployee(@PathVariable String employeeId) {

    	Integer status = CommonStatusEnum.无效.getValue();
    	employeeService.updateEmployeeStatus(employeeId, status);

        return new ResultData(true);
    }

    /**
     * 更新用户状态
     * @param employeeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateStatus/{employeeId}/{status}", method = RequestMethod.POST)
    public ResultData deleteEmployee(@PathVariable String employeeId, @PathVariable Integer status) {

        Employee employee = employeeService.findByPK(employeeId);

        employee.setStatus(status);
        employeeService.update(employee);

        return new ResultData(true);
    }


    /**
     * 判断账户是否已经存在
     * @param account
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkAccountIsExist/{account}", method = RequestMethod.GET)
    public Boolean checkAccountIsExist (@PathVariable String account) {
        Map<String, Object> params = new HashMap<>();
        params.put("AND_EQ_userName", account);
        params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
        List<BaseUser> accounts = baseUserService.findAll(params, BaseUser.class);
        if (accounts != null && accounts.size() > 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 准备员工维护页面的初始化数据
     * @param map
     */
    private void prepareInitDatas(Map<String, Object> map) {
        sexTypes = EnumUtil.toMap(SexEnum.class);
        map.put("sexs", sexTypes);
        employeeStatus = EnumUtil.toMap(EmployeeStatusEnum.class);
        map.put("employeeStatus", employeeStatus);
        employeeTypes = EnumUtil.toMap(EmployeeTypeEnum.class);
        map.put("employeeTypes", employeeTypes);
    }

    /**
     * 进入用户维护组织岗位信息界面
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/orgPosition/{employeeId}", method = RequestMethod.GET)
        public String toEdit(@PathVariable String employeeId, Map<String, Object> map) {
        Map<String, Object> params = new HashMap<>();
        params.put("AND_EQ_id.employee.id", employeeId);

        List<UserOrgPositionRelation> relationList = userOrgPositionRelationService.findAll(params, UserOrgPositionRelation.class);
        if (relationList != null && relationList.size() > 0) {
            map.put("relation", relationList.get(0));
        } else {
            Employee employee = employeeService.findByPK(employeeId);
            UserOrgPositionRelation relation = new UserOrgPositionRelation();
            UserOrgPositionRelationPK userOrgPositionRelationPK = new UserOrgPositionRelationPK();
            userOrgPositionRelationPK.setEmployee(employee);
            relation.setId(userOrgPositionRelationPK);
            map.put("relation", relation);
        }
        return "sysmgr/employee/employeeOrgPositionList";
    }

    /**
     * 保存用户组织岗位关系
     * @return
     */
    @RequestMapping(value = "/orgPosition", method = RequestMethod.POST)
    public String saveRelation (@Valid UserOrgPositionRelation relation, BindingResult result, Map<String, Object> map) {
        if (result.hasErrors()) {
            map.put("relation", relation);
            return "sysmgr/employee/employeeOrgPositionList";
        }
        Map<String, Object> params = new HashMap<>();
        params.put("AND_EQ_id.employee.id", relation.getId().getEmployee().getId());

        List<UserOrgPositionRelation> relationList = userOrgPositionRelationService.findAll(params, UserOrgPositionRelation.class);
        if (relationList != null && relationList.size() > 0) {
            userOrgPositionRelationService.delete(relationList.get(0));
        }

        userOrgPositionRelationService.create(relation);

        refreshCurrentSessionUser(relation);
        return "redirect:/sysmgr/employee/view";
    }

    /**
     * 根据组织ID获取该组织下的员工信息
     * @param orgId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/org/{orgId}", method = RequestMethod.GET)
    public List<Employee> getEmployeeByOrgId (@PathVariable String orgId) {
        List<Employee> employees = employeeService.findByOrgId(orgId);
        return employees;
    }

    /**
     * 模糊查询获取接单员
     * @param employeeName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/orderTaker", method = RequestMethod.POST)
    public ResultData getOrderTaker(@RequestParam(name = "employeeName", required = false) String employeeName) {
        return new ResultData(true, employeeService.findOrderTaker(employeeName));
    }
    /**
     * 刷新当前sessionUser信息
     */
    private void refreshCurrentSessionUser (UserOrgPositionRelation relation) {
        SessionUser sessionUser = UserUtil.getUserFromSession();
        String relationEmployeeId = relation.getId().getEmployee().getId();
        // 如果所更新的是当前用户的用户组织岗位关系，则刷新session
        if (sessionUser.getCurrentUserId().equals(relationEmployeeId)) {
            sessionUser.setCurrentOrgId(relation.getId().getCompanyOrg().getId());
            sessionUser.setCurrentOrgName(relation.getId().getCompanyOrg().getOrgName());
            sessionUser.setCurrentPositionId(relation.getId().getPosition().getId());
            sessionUser.setCurrentPositionName(relation.getId().getPosition().getPositionName());
            UserUtil.saveUserToSession(sessionUser);
        }
    }
}
