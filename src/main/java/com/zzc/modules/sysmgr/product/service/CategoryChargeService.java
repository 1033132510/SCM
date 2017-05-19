/**
 * 
 */
package com.zzc.modules.sysmgr.product.service;

import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.sysmgr.product.entity.CategoryCharge;
import com.zzc.modules.sysmgr.product.web.vo.CategoryChargeVO;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyong
 *
 */
public interface CategoryChargeService extends BaseCrudService<CategoryCharge>{
	
	/**
	 * 批量保存或更新CategoryCharge
	 * @return
	 */
	public boolean saveOrUpdateCategoryChargeList(String categoryChargeList, String employeeId);
	
	/**
	 * 获取该员工负责的品类信息
	 * @param employeeId
	 * @return
	 */
	public List<CategoryChargeVO> getCategoryChargeByEmployeeId(String employeeId);

	/**
	 * 根据二级品类id查询品类负责人
	 *
	 * @param cateId 品类id
	 * @return
	 */
	public List<CategoryChargeVO> getCategoryChargeByCateId(String cateId);
	
	/**
	 * 校验该员工是否有权限操作该二级品类
	 * @param employeeId
	 * @return
	 */
	public long countByEmployeeIdAndSecondLevelCateId(String employeeId, String secondLevelCateId);
	
	public Employee getCategoryChargeByCategoryIdAndRoleCode(String roleCode, String cateId);
	
	/**
	 * 查询在secondLevelCateIds中没有分配给employeeId的品类id，以及即将删除并且正在审批流程中的商品品类id
	 * @param ids	即将保存的
	 * @param employeeId
	 * @param type
	 * @return
	 */
	public Map<String, Object> getCategoryChargeByCateIds(List<String> ids, String employeeId, Integer type);
	
	public List<CategoryCharge> findByUserIds(List<String> userIds);
	
	public void deleteByUserIds(List<String> userIds);

}