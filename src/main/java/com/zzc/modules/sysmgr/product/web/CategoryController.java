package com.zzc.modules.sysmgr.product.web;

import com.alibaba.fastjson.JSON;
import com.zzc.common.security.util.UserUtil;
import com.zzc.common.zTree.ZTreeNode;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.sysmgr.product.entity.Category;
import com.zzc.modules.sysmgr.product.service.CategoryService;
import com.zzc.modules.sysmgr.product.web.vo.CategoryViewVO;
import com.zzc.modules.sysmgr.util.CategoryCaCheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品类别
 * 
 * @author apple
 *
 */
@Controller
@RequestMapping("/sysmgr/category")

public class CategoryController extends BaseController {

	@Autowired
	private CategoryService categoryService;

	/**
	 * 将商品品类的信息带出来展示出来
	 * 
	 * @param cateId
	 * @return
	 */
	@RequestMapping(value = "/getCategoryInfo", method = RequestMethod.GET)
	@ResponseBody
	public ZTreeNode getCategoryInfo(@RequestParam(value = "cateId", required = true) String cateId) {
		ZTreeNode ztreeNode = null;
		if(CategoryCaCheUtil.exist(cateId) && CategoryCaCheUtil.isOriginal(cateId)) {
			ztreeNode = CategoryCaCheUtil.get(cateId);
		} else {
			ztreeNode = categoryService.getCategoryInfo(cateId);
			CategoryCaCheUtil.put(ztreeNode);
		}
		return ztreeNode;
	}

	/**
	 * 商品类别
	 * 
	 * @param level
	 * @param parentCategoryId
	 * @param level
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/cateShow", method = RequestMethod.POST)
	@ResponseBody
	public String getCate(@RequestParam(value = "parentCategoryId", required = false) String parentCategoryId, @RequestParam(name = "level", required = false) Integer level,
			@RequestParam(name = "status", required = false) Integer status) {
		String employeeId = UserUtil.getUserFromSession().getCurrentUserId();
		List<Category> cates = categoryService.getProductCategoryListByParentIdAndEmployeeId(employeeId, parentCategoryId, level, status);
		return JsonUtils.toJson(cates);
	}

	/**
	 * 商品类别, 供应商平台可以查看所有类别
	 *
	 * @param parentCategoryId
	 * @param level
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/cateShowWithoutPermission", method = RequestMethod.POST)
	@ResponseBody
	public String getCateWithoutPermission(@RequestParam(value = "parentCategoryId", required = false) String parentCategoryId, @RequestParam(name = "level", required = false) Integer level,
	                                       @RequestParam(name = "status", required = false) Integer status) {
		return JsonUtils.toJson(categoryService.getProductCategoryTree(parentCategoryId, level, status));
	}

	@RequestMapping(value = "/getCategoryInfoSimple", method = RequestMethod.GET)
	@ResponseBody
	public CategoryViewVO getCategoryInfoSimple(@RequestParam(value = "cateId", required = true) String cateId) {
		return categoryService.getCategoryInfoSimple(cateId);
	}

	/**
	 * 直接跳转到类别表格页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String getPurchaserView() {
		return "sysmgr/product/productCategory";
	}

	/**
	 * 商品类目树结构加载
	 * 
	 * @param level
	 * @param parentCategoryId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	@ResponseBody
	public String getProductCategoryTree(@RequestParam(name = "level", required = false) Integer level, @RequestParam(value = "parentCategoryId", required = false) String parentCategoryId,
			@RequestParam(name = "status", required = false) Integer status) {
		String employeeId = UserUtil.getUserFromSession().getCurrentUserId();
		List<ZTreeNode> zList = categoryService.getProductCategoryTreeJson(employeeId, parentCategoryId, level, status);
		if (zList.isEmpty()) {
			return "";
		}
		return JsonUtils.toJson(zList);
	}

	/**
	 * 类别保存和更新
	 * 
	 * @param productCategoryJson
	 * @return
	 */
	@RequestMapping(value = "saveOrUpdate")
	@ResponseBody
	public String saveCategory(String productCategoryJson) {
		Category productCategory = JSON.parseObject(productCategoryJson, Category.class);
		return JsonUtils.toJson(categoryService.createOrUpdate(productCategory));
	}

}
