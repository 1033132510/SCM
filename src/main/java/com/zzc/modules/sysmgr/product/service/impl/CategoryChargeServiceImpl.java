/**
 * 
 */
package com.zzc.modules.sysmgr.product.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zzc.core.dao.BaseDao;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.core.utils.JsonUtils;
import com.zzc.modules.sysmgr.constant.SystemConstants;
import com.zzc.modules.sysmgr.enums.SupplyAuditBillStatusEnum;
import com.zzc.modules.sysmgr.product.dao.CategoryChargeDao;
import com.zzc.modules.sysmgr.product.entity.CategoryCharge;
import com.zzc.modules.sysmgr.product.service.CategoryChargeService;
import com.zzc.modules.sysmgr.product.service.CategoryService;
import com.zzc.modules.sysmgr.product.web.vo.CategoryChargeVO;
import com.zzc.modules.sysmgr.user.base.entity.BaseUser;
import com.zzc.modules.sysmgr.user.base.entity.Role;
import com.zzc.modules.sysmgr.user.base.service.RoleService;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.employee.service.EmployeeService;
import com.zzc.modules.sysmgr.util.AuditBillStatusUtil;

/**
 * @author zhangyong
 *
 */
@Service(value = "categoryChargeService")
public class CategoryChargeServiceImpl extends BaseCrudServiceImpl<CategoryCharge> implements
		CategoryChargeService {

	private CategoryChargeDao categoryChargeDao;
	
	@Resource(name = "employeeService")
	private EmployeeService employeeService;
	
	@Resource(name = "categoryService")
	private CategoryService categoryService;
	
	@Resource(name = "roleService")
	private RoleService roleService;
	
	public final static Integer CATEGORY_LEVEL_3 = 3;
	
	
	@Autowired
	public CategoryChargeServiceImpl(BaseDao<CategoryCharge> categoryChargeDao) {
		super(categoryChargeDao);
		this.categoryChargeDao = (CategoryChargeDao) categoryChargeDao;
	}
	
	@Transactional
	@Override
	public boolean saveOrUpdateCategoryChargeList(String categoryChargeList, String employeeId) {
		categoryChargeDao.deleteCategoryChargeByEmployeeId(employeeId);
		if(StringUtils.isNoneBlank(categoryChargeList)) {
			List<CategoryChargeVO> voLst = JsonUtils.toList(categoryChargeList, ArrayList.class, CategoryChargeVO.class);
			if(voLst.size() > 0) {
				Employee employee = employeeService.findByPK(employeeId);
				List<Role> roles = employee.getRoleList();
				String category_administrator = SystemConstants.CATEGORY_ADMINISTRATOR,
						category_majordomo = SystemConstants.CATEGORY_MAJORDOMO;
				boolean flag = false;
				for(Role role : roles) {
					String roleCode = role.getRoleCode();
					if(category_administrator.equals(roleCode) || category_majordomo.equals(roleCode)) {
						flag = true;
						break;
					}
				}
				// 如果当前employee既不是品类管理员也不是部类总监，返回false
				if(!flag) {
					return false;
				}
				List<CategoryCharge> list = new ArrayList<CategoryCharge>();
				Date now = new Date();
				for(int i = 0, length = voLst.size(); i < length; i++) {
					CategoryCharge categoryCharge = new CategoryCharge();
					CategoryChargeVO vo = voLst.get(i);
					categoryCharge.setEmployee(employee);
					categoryCharge.setCreateTime(now);
					categoryCharge.setCategory(categoryService.findByPK(vo.getSecondLevelCategoryId()));
					list.add(categoryCharge);
				}
				createBatch(list);
			}
		}
		return true;
	}
	
	@Override
	public List<CategoryChargeVO> getCategoryChargeByEmployeeId(String employeeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("AND_EQ_employee.id", employeeId);
		List<CategoryCharge> list = findAll(map, CategoryCharge.class);
		return fromCategoryChargeToVO(list);
	}

	@Override
	public List<CategoryChargeVO> getCategoryChargeByCateId(String cateId) {
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_category.id", cateId);
		return fromCategoryChargeToVO(findAll(params, CategoryCharge.class));
	}

	@Override
	public long countByEmployeeIdAndSecondLevelCateId(String employeeId,
			String secondLevelCateId) {
		return categoryChargeDao.countByEmployeeIdAndSecondLevelCateId(
				employeeId, secondLevelCateId, CATEGORY_LEVEL_3, CommonStatusEnum.有效.getValue());
	}
	
	private List<CategoryChargeVO> fromCategoryChargeToVO(List<CategoryCharge> list) {
		List<CategoryChargeVO> voList = new ArrayList<CategoryChargeVO>();
		if(list != null && list.size() > 0) {
			for(int i = 0, length = list.size(); i < length; i++) {
				CategoryChargeVO vo = new CategoryChargeVO();
				CategoryCharge categoryCharge = list.get(i);
				vo.setSecondLevelCategoryId(categoryCharge.getCategory().getId());
				vo.setEmployeeId(categoryCharge.getEmployee().getId());
				voList.add(vo);
			}
		}
		return voList;
	}
	
	@Override
	public Employee getCategoryChargeByCategoryIdAndRoleCode(
			String roleCode, String cateId) {
		List<CategoryCharge> list = categoryChargeDao.getCategoryChargeByCategoryIdAndRoleCode(cateId, roleCode);
		if(list == null || list.size() == 0) {
			return null;
		}
		return list.get(0).getEmployee();
	}
	
	@Override
	public Map<String, Object> getCategoryChargeByCateIds(
			List<String> ids, String employeeId, Integer type) {
		String roleCode = null;
    	if(type == 1) {
    		roleCode = SystemConstants.CATEGORY_ADMINISTRATOR;
    	} else {
    		roleCode = SystemConstants.CATEGORY_MAJORDOMO;
    	}
		List<String> ownCateIds = categoryChargeDao.getCateIdsByEmployeeId(employeeId);
		
		// --------------------------------校验即将删除的品类id是否存在商品正在审批流程中------------------------
		// 即将删除的品类id
		List<String> deleteCateIds = new ArrayList<String>();
		for(String id : ownCateIds) {
			boolean flag = true;
			for(String saveCateId : ids) {
				if(id.equals(saveCateId)) {
					flag = false;
					break;
				}
			}
			// 如果该品类id没有在ids中找到，就添加到deleteCateIds
			if(flag) {
				deleteCateIds.add(id);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 不用校验审批流程中的商品 --------------------
		if(deleteCateIds.size() > 0) {
			// 正在审批流程中的品类id
			List<String> inAuditingCateIds = categoryChargeDao.findCateIdsInAuditingByEmployeeIdAndCateIds(deleteCateIds, employeeId, AuditBillStatusUtil.getTerminalStatus());
			if(inAuditingCateIds != null && inAuditingCateIds.size() > 0) {
				map.put("msg", "即将删除的品类中存在正在审批流程中的商品");
				map.put("cateIds", inAuditingCateIds);
				return map;
			}
		}

		
		
		// --------------------------------校验即将删除的品类id是否存在商品正在审批流程中------------------------
		
		// 相同角色下其他员工管理的品类id
		List<String> otherCateIds = categoryChargeDao.getCateIdsByEmployeeIdForOthers(employeeId, roleCode);
		// 即将新增的品类id 注：和ids(即将保存的品类)不同
		List<String> toSaveCateIds = new ArrayList<String>();
		for(String id : ids) {
			boolean flag = true;
			for(String ownCateId : ownCateIds) {
				if(id.equals(ownCateId)) {
					flag = false;
					break;
				}
			}
			// 如果该品类id是新增品类id，添加到toSaveCateIds
			if(flag) {
				toSaveCateIds.add(id);
			}
		}
		List<String> result = new ArrayList<String>();
		// 对比即将新增的品类id和其他相同角色管理员已经分配的品类id，相同的都是已经已经被分配的品类id
		for(String otherCateId : otherCateIds) {
			for(String toSaveCateId : toSaveCateIds) {
				if(toSaveCateId.equals(otherCateId)) {
					result.add(toSaveCateId);
					break;
				}
			}
		}
		map.put("msg", "标红显示的是已分配给其他相同角色管理员的品类");
		map.put("cateIds", result);
		return map;
	}
	
	@Override
	public List<CategoryCharge> findByUserIds(List<String> userIds) {
		return categoryChargeDao.findByUserIdsAndAuditStatus(userIds, AuditBillStatusUtil.getTerminalStatus());
	}
	
	@Transactional
	@Override
	public void deleteByUserIds(List<String> userIds) {
		for(String id : userIds) {
			categoryChargeDao.deleteCategoryChargeByEmployeeId(id);
		}
	}
	
}