package com.zzc.modules.shop.product.web;

import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.sysmgr.product.entity.Category;
import com.zzc.modules.sysmgr.product.entity.CategoryItem;
import com.zzc.modules.sysmgr.product.entity.ProductSearchViewProxy;
import com.zzc.modules.sysmgr.product.service.CategoryService;
import com.zzc.modules.sysmgr.product.service.ProductSearchViewProxyService;
import com.zzc.modules.sysmgr.product.web.vo.CateSearchVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品类别
 * 
 * @author apple
 *
 */
@Controller
@RequestMapping("/shop/category/")

public class CategoryShopController extends BaseController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductSearchViewProxyService productSearchViewService;

	/**
	 * 商品类别
	 * 
	 * @param parentCategoryId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/cateShow", method = RequestMethod.POST)
	@ResponseBody
	public String getCate(@RequestParam(value = "parentCategoryId", required = false) String parentCategoryId,
						  @RequestParam(name = "status", required = false) Integer status) {
		List<Category> cates = categoryService.getProductCategoryTree(parentCategoryId, null, status);
		return JsonUtils.toJson(cates);
	}

	/**
	 * 得到类别模板
	 * 
	 * @param categoryId
	 * @return
	 */
	@RequestMapping(value = "/cateDecide", method = RequestMethod.GET)
	@ResponseBody
	public String cateDecidePlaneInit(@RequestParam(name = "categoryId", required = true) String categoryId) {
		if(categoryService.cateDecidePlaneInit(categoryId).isEmpty()){
			return "";
		}
		return JsonUtils.toJsonForMap(categoryService.cateDecidePlaneInit(categoryId));
	}

	/**
	 * 将商品品类的信息带出来展示出来
	 * 
	 * @param cateId
	 * @return
	 */
	@RequestMapping(value = "/getCategoryInfoForItemSearch", method = RequestMethod.GET)
	@ResponseBody
	public String getCategoryInfoForItemSearch(@RequestParam(value = "cateId", required = false) String cateId) {
		if (StringUtils.isBlank(cateId)) {
			return "";
		}
		Category category = categoryService.findByPK(cateId);
		List<CategoryItem> items = new ArrayList<CategoryItem>();
		for (CategoryItem item : category.getProductCategoryItemKeys()) {
			if (item.getAllowedCateFilter().intValue() == 1 && item.getStatus() == CommonStatusEnum.有效.getValue()) {
				items.add(item);
			}
		}
		CateSearchVO vo = new CateSearchVO();
		vo.setCateId(category.getId());
		vo.setCateName(category.getCateName());
		vo.setItems(items);
		return JsonUtils.toJson(vo);
	}

	/**
	 * 根据产品名字得到类别 write by pat zheng for ping
	 */
	@RequestMapping(value = "getCateByProductName", method = RequestMethod.GET)
	@ResponseBody
	public String getBrandByProductName(@RequestParam(value = "productName", required = false) String productName, @RequestParam(value = "level", required = false) Integer level) {
		List<ProductSearchViewProxy> views = productSearchViewService.getCateByProductName(productName, level);
		if (views.isEmpty()) {
			return "";
		}
		return JsonUtils.toJson(views);
	}
	
	
	/**
	 * 根据品牌名字得到类别 
	 */
	@RequestMapping(value = "getCateByBrandName", method = RequestMethod.GET)
	@ResponseBody
	public String getCateByBrandName(@RequestParam(value = "brandName", required = false) String brandName, @RequestParam(value = "level", required = false) Integer level) {
		List<ProductSearchViewProxy> views = productSearchViewService.getCateByBrandName(brandName, level);
		if (views.isEmpty()) {
			return "";
		}
		return JsonUtils.toJson(views);
	}

}
