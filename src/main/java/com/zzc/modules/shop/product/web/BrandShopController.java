package com.zzc.modules.shop.product.web;

import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.sysmgr.product.entity.ProductSearchViewProxy;
import com.zzc.modules.sysmgr.product.service.CategoryService;
import com.zzc.modules.sysmgr.product.service.ProductSearchViewProxyService;
import com.zzc.modules.sysmgr.product.web.vo.BrandShopView;
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
 * 查询
 * @author apple
 *
 */
@Controller
@RequestMapping("/shop/brand/")
public class BrandShopController extends BaseController {

	@Autowired
	private ProductSearchViewProxyService productSearchViewService;
	
	@Autowired
	private CategoryService categoryService;

	/**
	 * 根据类别和产品名称得到品牌信息
	 * 
	 * @param productName
	 * @param cateId
	 * @return
	 */
	@RequestMapping(value = "searchBrandInfoByCateAndProductName", method = RequestMethod.GET)
	@ResponseBody
	public String searchBrandInfoByCateAndproductName(@RequestParam(value = "productName", required = false) String productName, @RequestParam(value = "cateId", required = false) String cateId) {
		Integer level = null;
		if(StringUtils.isNotEmpty(cateId)){
			level = categoryService.findByPK(cateId).getLevel();
		}
		List<ProductSearchViewProxy> proxyes = productSearchViewService.searchBrandInfoByCateAndProductName(productName, cateId,level);
		List<BrandShopView> brands = getBrandSimpleView(proxyes);
		//避免卡死
		if (brands == null || brands.size() <= 0) {
			return "";
		}
		return JsonUtils.toJson(brands);
	}

	/**
	 * 將service得到的全部數據進行轉換簡單輸出
	 * 
	 * @param proxy
	 * @return
	 */
	private BrandShopView getBrandSimpleView(ProductSearchViewProxy proxy) {
		BrandShopView view = new BrandShopView();
		view.setBrandId(proxy.getBrandId());
		view.setBrandENName(proxy.getBrandENName());
		view.setBrandZHName(proxy.getBrandZHName());
		return view;
	}

	private List<BrandShopView> getBrandSimpleView(List<ProductSearchViewProxy> proxyes) {
		List<BrandShopView> brandSimpleViewes = new ArrayList<BrandShopView>();
		for (ProductSearchViewProxy proxy : proxyes) {
			BrandShopView view = getBrandSimpleView(proxy);
			brandSimpleViewes.add(view);
		}
		return brandSimpleViewes;
	}
	
	/**
	 * 根据品牌名称得到品牌信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "searchBrandInfoByBrandName", method = RequestMethod.GET)
	@ResponseBody
	public String searchBrandInfoByBrandName(@RequestParam(value = "brandName", required = false) String brandName) {
		List<ProductSearchViewProxy> proxyes = productSearchViewService.searchBrandInfoByBrandName(brandName);
		List<BrandShopView> brands = getBrandSimpleView(proxyes);
		if (brands == null || brands.size() <= 0) {
			return "";
		}
		return JsonUtils.toJson(brands);
	}
}
