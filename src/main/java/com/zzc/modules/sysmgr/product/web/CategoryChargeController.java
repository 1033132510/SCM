/**
 * 
 */
package com.zzc.modules.sysmgr.product.web;

import com.zzc.common.page.PageForShow;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.web.common.ResultData;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.sysmgr.product.entity.Category;
import com.zzc.modules.sysmgr.product.service.CategoryChargeService;
import com.zzc.modules.sysmgr.product.service.CategoryService;
import com.zzc.modules.sysmgr.product.web.vo.CategoryChargeVO;
import com.zzc.modules.sysmgr.product.web.vo.CategoryVO;
import com.zzc.modules.sysmgr.user.employee.service.EmployeeService;
import com.zzc.modules.sysmgr.user.employee.service.vo.EmployeeVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * 组织管理（公司信息管理）
 * @author zhangyong
 *
 */
@Controller
@RequestMapping(value = "/sysmgr/category/charge")
public class CategoryChargeController extends BaseController {

	public static final int LEVEL_ONE = 1;
	public static final int LEVEL_TWO = 2;
	public static final int LEVEL_THREE = 3;

	@Resource(name = "employeeService")
	private EmployeeService employeeService;

	@Resource(name = "categoryService")
	private CategoryService categoryService;
	
	@Resource(name = "categoryChargeService")
	private CategoryChargeService categoryChargeService;
	/**
	 * 跳转到品类负责人管理
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index() {
		return "sysmgr/categoryCharge/categoryChargeList";
	}
	/**
	 *跳转到品类总监管理
	 * @return
	 */
	@RequestMapping(value = "/major/index")
	public String viewCategoryMajor() {
		return "sysmgr/categoryCharge/categoryMajordomoList";
	}
	/**
	 *添加品类管理员
	 * @return
	 */
	@RequestMapping(value = "toAddOrUpdateCategoryCharge")
	public String toAddOrUpdateCategoryCharge(ModelMap map, String employeeId, String employeeName) {
		map.put("employeeId", employeeId);
		map.put("employeeName", employeeName);
		return "sysmgr/categoryCharge/addOrUpdateCategoryCharge";
	}
	/**
	 *添加品类总监
	 * @return
	 */
	@RequestMapping(value = "toAddOrUpdateCategoryMajordomo")
	public String toAddOrUpdateCategoryMajordomo(ModelMap map, String employeeId, String employeeName) {
		map.put("employeeId", employeeId);
		map.put("employeeName", employeeName);
		return "sysmgr/categoryCharge/addOrUpdateCategoryMajordomo";
	}
	/**
	 *获得品类列表
	 * @return
	 */
	@RequestMapping(value = "/getCategoryList")
	@ResponseBody
	public Map<String, Object> getCategoryList() {
		Integer status = CommonStatusEnum.有效.getValue();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("firstCategoryList", categoryService.getCategoryListByLevel(LEVEL_ONE, status));
		List<Category> secondCategoryList = categoryService.getCategoryListByLevel(LEVEL_TWO, status),
				thirdCategoryList = categoryService.getCategoryListByLevel(LEVEL_THREE, status);
		List<CategoryVO> categoryVOList = new ArrayList<CategoryVO>();
		for(int i = 0, length = secondCategoryList.size(); i < length; i++) {
			Category category = secondCategoryList.get(i);
			CategoryVO vo = new CategoryVO();
			String id = category.getId();
			vo.setId(id);
			vo.setCateName(category.getCateName());
			vo.setParentId(category.getParentCategory().getId());
			List<CategoryVO> childCategoryVOList = new ArrayList<CategoryVO>();
			for(int m = 0, lengthM = thirdCategoryList.size(); m < lengthM; m++) {
				Category childCategory = thirdCategoryList.get(m);
				String parentId = childCategory.getParentCategory().getId();
				if(parentId.equals(id)) {
					CategoryVO childVO = new CategoryVO();
					childVO.setId(childCategory.getId());
					childVO.setCateName(childCategory.getCateName());
					childVO.setParentId(parentId);
					childCategoryVOList.add(childVO);
				}
			}
			vo.setChilds(childCategoryVOList);
			categoryVOList.add(vo);
		}
		
		map.put("secondCategoryList", categoryVOList);
		return map;
	}
	
	/**
	 * 通过员工关键字查询分页列表
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param keywords
	 * @return
	 */
	@RequestMapping(value = "/getCategoryChargePageByKeywords", method = RequestMethod.POST)
	@ResponseBody
	public PageForShow<EmployeeVO> getCategoryChargePageByKeywords(Integer curPage, Integer pageSize, String keywords, Integer type) {
		return employeeService.findPageByEmployeeName(curPage, pageSize, keywords, type);
	}

	/**
	 * 获取员工负责的品类信息
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/getCategoryChargeByUserId", method = RequestMethod.GET)
	@ResponseBody
	public List<CategoryChargeVO> getCategoryChargeByUserId(String userId, Integer status) {
		return categoryChargeService.getCategoryChargeByEmployeeId(userId);
	}
	/**
	 *保存或修改品类列表
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate")
	@ResponseBody
	public Map<String, Object> saveOrUpdateCategoryChargeList(String categoryChargeVOList, String employeeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean success = categoryChargeService.saveOrUpdateCategoryChargeList(categoryChargeVOList, employeeId);
		map.put("success", success);
		if(!success) {
			map.put("msg", "Id为[" + employeeId + "]的员工不是品类管理员或部类总监");
		}
		return map;
	}
	
	/**
	 * 查看即将分配的品类是否已经被别人分配，以及即将删除的品类是否正在处理流程中
	 * @param ids
	 * @param employeeId
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/existOwnedCategoryForOthers")
	@ResponseBody
	public ResultData existOwnedCategoryForOthers(@RequestParam("ids") String ids,
			@RequestParam("employeeId") String employeeId,
			@RequestParam("type") Integer type) {
		List<String> idList = Arrays.asList(ids.split(","));
		return new ResultData(true, categoryChargeService.getCategoryChargeByCateIds(idList, employeeId, type));
	}

}