package com.zzc.modules.sysmgr.user.employee.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zzc.common.page.PageForShow;
import com.zzc.common.security.util.PasswordUtil;
import com.zzc.core.dao.BaseDao;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.sysmgr.constant.SystemConstants;
import com.zzc.modules.sysmgr.enums.AccountTypeEnum;
import com.zzc.modules.sysmgr.product.dao.CategoryChargeDao;
import com.zzc.modules.sysmgr.product.entity.CategoryCharge;
import com.zzc.modules.sysmgr.user.base.dao.RoleDao;
import com.zzc.modules.sysmgr.user.base.entity.BaseUser;
import com.zzc.modules.sysmgr.user.base.entity.Role;
import com.zzc.modules.sysmgr.user.employee.dao.EmployeeDao;
import com.zzc.modules.sysmgr.user.employee.dao.EmployeeQueryDao;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.employee.service.EmployeeService;
import com.zzc.modules.sysmgr.user.employee.service.vo.EmployeeVO;

/**
 * Created by hadoop on 2015/11/11.
 */
@Service("employeeService")
public class EmployeeServiceImpl extends BaseCrudServiceImpl<Employee> implements EmployeeService {

    private EmployeeDao employeeDao;

    @Autowired
    private EmployeeQueryDao employeeQueryDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private CategoryChargeDao categoryChargeDao;

    @Autowired
    public EmployeeServiceImpl(BaseDao<Employee> employeeDao) {
        super(employeeDao);
        this.employeeDao = (EmployeeDao) employeeDao;
    }


    @Override
    public Employee findByName(String employeeName) {
        return employeeDao.findByName(employeeName);
    }

    @Override
    public Employee create(Employee employee) {
        employee.setType(AccountTypeEnum.employee.getCode());
        employee.setStatus(CommonStatusEnum.有效.getValue());
        employee.setUserName(employee.getMobile());
        employee.setUserPwd(PasswordUtil.initPassword());

        employeeDao.save(employee);
        return employee;
    }

    @Override
    public List<Employee> findByOrgId(String orgId) {
        return employeeDao.findByOrgId(orgId);
    }

    @Override
    public List<Employee> findByRoleId(String roleId) {
        Role role = roleDao.findOne(roleId);

        return findByRole(new HashMap<String, Object>() , role);
    }

    @Override
    public List<Employee> findOrderTaker(String employeeName) {
        Role role = roleDao.findByRoleCode(SystemConstants.ORDER_TAKER);
        Map<String, Object> params = new HashMap<>();
        params.put("AND_LIKE_employeeName", employeeName);

        return findByRole(params, role);
    }

    @Override
    public List<Employee> findByEmployeeName(String employeeName) {
        return employeeQueryDao.findByEmployeeName(employeeName);
    }

    @Override
    public PageForShow<Employee> findByPage(String employeeName, Integer pageNum, Integer pageSize) {
        return employeeQueryDao.findEmployeeByPage(employeeName, pageNum, pageSize);
    }

    /**
     * 角色相关的查询员工信息
     * @return
     */
    private List<Employee> findByRole(Map<String, Object> params, Role role) {
        List<Employee> employees = new ArrayList<>();
        if (role != null) {
            List<BaseUser> userList = role.getUserList();
            List<String> userIds = new ArrayList<>();
            if (userList != null && userList.size() > 0) {
                for (BaseUser user : userList) {
                    if (user.getStatus().intValue() == CommonStatusEnum.有效.getValue().intValue()) {
                        userIds.add(user.getId());
                    }
                }
                if (userIds.size() > 0) {
                    params.put("AND_IN_id", userIds);
                    employees = findAll(params, Employee.class);
                }
            }
        }
        return employees;
    }

    @Override
    public Employee saveTest(Employee employee) {
        return employeeDao.save(employee);
    }
    
    @SuppressWarnings("static-access")
	@Override
    public PageForShow<EmployeeVO> findPageByEmployeeName(Integer curPage, Integer pageSize, String employeeName, Integer type) {
    	String roleCode = null;
    	if(type == 1) {
    		roleCode = SystemConstants.CATEGORY_ADMINISTRATOR;
    	} else {
    		roleCode = SystemConstants.CATEGORY_MAJORDOMO;
    	}
    	if(StringUtils.isBlank(employeeName)) {
    		employeeName = "";
    	}
    	List<Employee> employees = employeeDao.findByRoleCodeAndEmployeeName(roleCode, employeeName, (curPage - 1) * pageSize, pageSize);
    	long count = employeeDao.countByRoleCodeAndEmployeeName(roleCode, employeeName);
    	List<EmployeeVO> employeeVOs = fromEmployeeToVO(employees);
    	return new PageForShow<EmployeeVO>().newInstanceFromSpringPage(employeeVOs, curPage, count);
    }
    
    private List<EmployeeVO> fromEmployeeToVO(List<Employee> employees) {
    	List<EmployeeVO> voList = new ArrayList<EmployeeVO>();
    	if(employees != null && employees.size() > 0) {
    		for(int i = 0, length = employees.size(); i < length; i++) {
    			Employee employee = employees.get(i);
    			EmployeeVO vo = new EmployeeVO();
    			vo.setId(employee.getId());
    			vo.setModifiedTime(employee.getModifiedTime());
    			vo.setMobile(employee.getMobile());
    			vo.setEmployeeName(employee.getEmployeeName());
    			List<CategoryCharge> categoryChargeList = employee.getCategoryCharges();
    			List<String> cateNames = new ArrayList<String>();
    			if(categoryChargeList != null && categoryChargeList.size() > 0) {
    				for(int m = 0, lengthM = categoryChargeList.size(); m < lengthM; m++) {
    					CategoryCharge categoryCharge = categoryChargeList.get(m);
    					if(m == 0) {
    						// 设置品类权限的时间，而不是创建用户的时间
    						vo.setCreateTime(categoryCharge.getCreateTime());
    					}
    					cateNames.add(categoryCharge.getCategory().getCateName());
    				}
    			}
    			vo.setCateNames(cateNames);
    			voList.add(vo);
    		}
    	}
    	return voList;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        if (employee != null && StringUtils.isEmpty(employee.getId())) {
            employee.setType(AccountTypeEnum.employee.getCode());
            employee.setStatus(CommonStatusEnum.有效.getValue());
            employee.setUserName(employee.getMobile());
            employee.setUserPwd(PasswordUtil.initPassword());
        }

        employee = employeeDao.save(employee);
        return employee;
    }
    
    @Override
    public List<Employee> getEmployeeListByKeywords(String employeeName,
    		Integer type, Integer limit) {
    	String category_majordomo = SystemConstants.CATEGORY_MAJORDOMO,
    			category_administrator = SystemConstants.CATEGORY_ADMINISTRATOR;
    	List<Object[]> employees = null;
    	List<Employee> list = new ArrayList<Employee>();
    	if(type == 1) {
    		employees = employeeDao.findByRoleCodeAndEmployeeNameForAdministrator(category_administrator, employeeName, 0, limit);
    	} else {
    		employees = employeeDao.findByRoleCodeAndEmployeeNameForAdministrator(category_majordomo, employeeName, 0, limit);
    	}
    	if(employees != null && employees.size() > 0) {
    		for(Object[] objs : employees) {
    			Employee e = new Employee();
    			e.setId((String) objs[0]);
    			e.setEmployeeName((String) objs[1]);
    			e.setMobile((String) objs[2]);
    			list.add(e);
    		}
    	}
    	return list;
    }
    
    @Transactional
    @Override
    public void updateEmployeeStatus(String employeeId, Integer status) {
    	Employee employee = findByPK(employeeId);
        employee.setStatus(status);
        update(employee);
        // 如果置为无效，需要重置角色为普通用户
    	if(status == CommonStatusEnum.无效.getValue()) {
    		roleDao.deleteByUserId(employeeId);
    		categoryChargeDao.deleteCategoryChargeByEmployeeId(employeeId);
    	}
    }
}