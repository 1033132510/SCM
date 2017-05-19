package com.zzc.modules.sysmgr.product.dao;

import com.zzc.core.dao.BaseDao;
import com.zzc.modules.sysmgr.product.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 
 * @author famous
 *11
 */
public interface CategoryDao extends BaseDao<Category> {
	/**
	 * 获得商品种类
	 * @param employeeId
	 * @param parentCateId
	 * @param status
	 * @param level
	 * @return
	 */
	@Query("select category from Category category, CategoryCharge charge where category.id = charge.category.id and category.status = :status"
			+ " and charge.employee.id = :employeeId and category.parentCategory.id = :parentCateId"
			+ " and category.level = :level order by category.createTime desc")
	public List<Category> getCategoryListByParentCateIdAndEmployeeId(@Param("employeeId")String employeeId,
			@Param("parentCateId")String parentCateId, @Param("status")Integer status, @Param("level")Integer level);
	
}