package com.zzc.modules.shop.product.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zzc.common.page.PageForShow;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.sysmgr.product.service.ProductSKUService;
import com.zzc.modules.sysmgr.product.service.vo.ProductSKUBussinessVO;
import com.zzc.modules.sysmgr.product.web.vo.ProductSearchVO;

/**
 * 产品web
 * 
 * @author apple
 *
 */
@Controller
@RequestMapping("/shop/product")
public class ProductSKUShopController extends BaseController {

	@Autowired
	private ProductSKUService productService;

	/**
	 * 跳转到商品详情页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "gotoView", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam(value = "productId", required = true) String productId, @RequestParam(value = "cateId", required = true) String cateId, ModelMap model) {
		model.put("productId", productId);
		model.put("cateId", cateId);
		return new ModelAndView("shop/product/productDetail", model);
	}

	/**
	 * 超級搜索
	 * 
	 * @return
	 */
	@RequestMapping(value = "Search", method = RequestMethod.POST)
	@ResponseBody
	public String searchSuperView(@RequestParam(value = "searchVOMsg", required = true) String searchVOMsg) {
		ProductSearchVO vo = JsonUtils.toObject(searchVOMsg, ProductSearchVO.class);
		PageForShow<ProductSKUBussinessVO> page = productService.searchEx(vo);
		if(page.data.isEmpty()){
			return "";
		}
		return JsonUtils.toJson(page);
	}

	/**
	 * 電商展示信息 包含價格（標準價格和會員價格）
	 * 
	 * @param cateId
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "pDetailShop", method = RequestMethod.GET)
	@ResponseBody
	public ProductSKUBussinessVO viewProductDeatilForShop(@RequestParam(value = "cateId", required = true) String cateId, @RequestParam(value = "productId", required = true) String productId) {
		ProductSKUBussinessVO vo = productService.getProductDetailShop(cateId, productId);
		return vo;
	}

	/**
	 * 通過組織獲取公司
	 * 
	 * @param supplierOrgId
	 * @param status
	 * @param pageSize
	 * @param curPage
	 * @return
	 */
	@RequestMapping(value = "searchBySupplierOrg", method = RequestMethod.GET)
	@ResponseBody
	public String searchBySupplierOrg(@RequestParam(value = "supplierOrgId", required = false) String supplierOrgId,
									  @RequestParam(value = "status", required = false) Integer status,
									  @RequestParam(value = "pageSize", required = false) Integer pageSize,
									  @RequestParam(value = "curPage", required = false) Integer curPage) {
		PageForShow<ProductSKUBussinessVO> vo = productService.searchBySupplierOrg(supplierOrgId, status,
				pageSize, curPage);
		return JsonUtils.toJson(vo);
	}

	/**
	 * 根据类别 获取商品信息
	 * 
	 * @param cateId
	 * @param productName
	 * @param status
	 * @param pageSize
	 * @param curPage
	 * @return
	 */

	@RequestMapping(value = "searchByCateOrName", method = RequestMethod.POST)
	@ResponseBody
	public String searchByCateOrName(@RequestParam(value = "cateId", required = false) String cateId,
									 @RequestParam(value = "productName", required = false) String productName,
									 @RequestParam(value = "status", required = false) Integer status,
									 @RequestParam(value = "pageSize", required = false) Integer pageSize,
									 @RequestParam(value = "curPage", required = false) Integer curPage) {
		PageForShow<ProductSKUBussinessVO> vo = productService.searchByCateOrName(cateId,
				productName, status, pageSize, curPage);
		return JsonUtils.toJson(vo);
	}

}
