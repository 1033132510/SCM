package com.zzc.modules.sysmgr.product.service;

import java.util.List;
import java.util.Map;

import com.zzc.common.zTree.ZTreeNode;
import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.sysmgr.product.entity.Category;
import com.zzc.modules.sysmgr.product.web.vo.CategoryViewVO;

/**
 * 
 * @author apple
 *
 */
public interface CategoryService extends BaseCrudService<Category> {

	/**
	 * Cate面板
	 * 
	 * @param categoryId
	 * @return
	 */
	public Map<Category, List<Category>> cateDecidePlaneInit(String categoryId);

	/**
	 * 
	 * @param parentCategoryId
	 * @param level
	 * @param status
	 * @return
	 */
	public List<Category> getProductCategoryTree(String parentCategoryId, Integer level, Integer status);

	/**
	 * 通过上一级品类id获取该员工负责的品类
	 * 
	 * @param employeeId
	 * @param parentCategoryId
	 * @param level
	 * @param status
	 * @return
	 */
	public List<Category> getProductCategoryListByParentIdAndEmployeeId(String employeeId, String parentCategoryId, Integer level, Integer status);

	/**
	 * 
	 * @param cateName
	 * @param status
	 * @param cateId
	 * @return
	 */
	public Long validateRepeatCateName(String cateName, Integer status, String cateId);

	/**
	 * 需要优化的地方
	 * 
	 * @param cateId
	 * @return
	 */
	// need to complate
	public List<String> getThirdLevelCate(String cateId);

	/**
	 * 更新保存
	 * 
	 * @param category
	 * @return
	 */
	public ZTreeNode createOrUpdate(Category category);

	/**
	 * 
	 * @param category
	 * @return
	 */
	public List<Category> getAllChildCate(Category category);

	/**
	 * 
	 * @param cateId
	 * @return
	 */
	public boolean exitsProductByCateId(String cateId);

	public boolean exitsProductByCateId(String cateId, Integer level);

	/**
	 * 
	 * @param cateId
	 * @return
	 */
	public CategoryViewVO getCategoryInfoSimple(String cateId);

	/**
	 * 
	 * @param cateId
	 * @return
	 */
	public ZTreeNode getCategoryInfo(String cateId);

	/**
	 * @param parentCategoryId
	 * @param level
	 * @param status
	 * @return
	 */
	public List<ZTreeNode> getProductCategoryTreeJson(String employeeId, String parentCategoryId, Integer level, Integer status);
	

	/**
	 * 获取类别及其属性
	 * 
	 * @param cateId
	 * @return
	 */
	public Category getCategoryInfoWithCategoryItems(String cateId);

	/**
	 * 获取某个级别下的所有类别
	 * 
	 * @param level
	 * @param status
	 * @return
	 */

	public List<Category> getCategoryListByLevel(int level, Integer status);

	public String getShopCategoryInfo(String cateId);

}