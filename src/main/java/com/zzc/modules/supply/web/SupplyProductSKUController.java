/**
 *
 */
package com.zzc.modules.supply.web;

import com.zzc.common.page.PageForShow;
import com.zzc.common.security.util.UserUtil;
import com.zzc.common.security.web.user.SessionUser;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.common.ResultData;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.supply.entity.SysProductSKU;
import com.zzc.modules.supply.service.AuditBillService;
import com.zzc.modules.supply.service.SupplyProductSKUService;
import com.zzc.modules.supply.vo.SupplyProductSKUVO;
import com.zzc.modules.sysmgr.file.service.ImageService;
import com.zzc.modules.sysmgr.product.entity.ProductSKU;
import com.zzc.modules.sysmgr.product.service.*;
import com.zzc.modules.sysmgr.product.service.vo.ProductSKUBussinessVO;
import com.zzc.modules.sysmgr.user.supplier.service.SupplierOrgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author zhangyong
 *         供应商--商品模块
 */
@Controller
@RequestMapping("/supply/product")
public class SupplyProductSKUController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(SupplyProductSKUController.class);

	@Resource(name = "productCategoryMainViewProxyService")
	private ProductCategoryMainViewProxyService productCategoryMainViewProxyService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private SupplyProductSKUService supplyProductSKUService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductSKUService productSKUService;

	@Autowired
	private SupplierOrgService supplierOrgService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private CategoryItemService categoryItemService;

	@Autowired
	private PriceKindService priceKindService;

	@Autowired
	private AuditBillService auditBillService;

	@Autowired
	private ProductPropertiesService productPropertiesService;

	@Autowired
	private ProductPriceService productPriceService;

	/**
	 * 跳转到有效商品列表页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/validProductManager", method = RequestMethod.GET)
	public String toProductManager(ModelMap map) {
		map.addAttribute("categoryList", productCategoryMainViewProxyService.getCategoryList(CommonStatusEnum.有效.getValue()));
		map.addAttribute("brandList", productCategoryMainViewProxyService.getBrandList(CommonStatusEnum.有效.getValue()));
		return "supply/product/validProductList";
	}

	/**
	 * 跳转到无效商品列表页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/invalidProductManager", method = RequestMethod.GET)
	public String invalidProductManager() {
		return "supply/product/invalidProductList";
	}

	/**
	 * 搜索商品列表
	 *
	 * @param cateId
	 * @param productName
	 * @param brandName
	 * @param status
	 * @param pageSize
	 * @param curPage
	 * @return
	 */
	@RequestMapping(value = "/searchForProductManager", method = RequestMethod.POST)
	@ResponseBody
	public String searchForProductManager(@RequestParam(value = "cateId", required = false) String cateId, @RequestParam(value = "productName", required = false) String productName, @RequestParam(value = "brandName", required = false) String brandName,
	                                      @RequestParam(value = "status", required = false) Integer status, @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "curPage", required = false) Integer curPage) {
		String employeeId = UserUtil.getUserFromSession().getCurrentUserId();
		PageForShow<Map<String, Object>> vo =
				productCategoryMainViewProxyService.searchForSupplyProductManager(employeeId, productName, brandName, cateId, status, pageSize, curPage);
		return JsonUtils.toJson(vo);
	}

	@RequestMapping(value = "addView", method = RequestMethod.GET)
	public String addProductView(ModelMap model) {
		initData(model);
		return "supply/product/addProduct";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public ResultData save(
			@RequestParam(value = "productDetail", required = true) String productDetail) {
		logger.info("[供应商添加商品请求数据]" + productDetail);
		Map<String, Object> result = supplyProductSKUService.createOrUpdateProductSKUModel(productDetail);
		return new ResultData((boolean) result.remove("success"), result);
	}

	@RequestMapping(value = "disableProduct", method = RequestMethod.POST)
	@ResponseBody
	public ResultData disableProduct(@RequestParam(value = "productSKUId") String productSKUId,
	                                 @RequestParam(value = "hasTax") Integer hasTax,
	                                 @RequestParam(value = "hasTransportation") Integer hasTransportation,
	                                 @RequestParam(value = "comment") String comment,
	                                 @RequestParam(value = "cateId") String cateId,
	                                 @RequestParam(value = "costPrice") BigDecimal costPrice,
	                                 @RequestParam(value = "standardPrice") BigDecimal standardPrice,
	                                 @RequestParam(value = "recommendedPrice") BigDecimal recommendedPrice) {
		ProductSKU productSKU = productSKUService.findByPK(productSKUId);
		Map<String, Object> result = supplyProductSKUService.disableProduct(productSKU, comment, costPrice, recommendedPrice, standardPrice, cateId, hasTax, hasTransportation);
		return new ResultData((boolean) result.remove("success"), result);
	}

	/**
	 * 跳转到商品修改页面
	 *
	 * @param id     商品id
	 * @param cateId 三级品类id
	 * @return
	 */
	@RequestMapping(value = "toUpdate", method = RequestMethod.GET)
	public String toUpdate(ModelMap model, @RequestParam("id") String id, @RequestParam("cateId") String cateId) {
		model.put("id", id);
		model.put("cateId", cateId);
		return "supply/product/updateProduct";
	}

	/**
	 * 跳转到上架商品修改页面
	 *
	 * @return
	 */
	@RequestMapping(value = "toUpdateGroundingProduct", method = RequestMethod.GET)
	public String toUpdateGroundingProduct(ModelMap model, @RequestParam("productSKUId") String productSKUId, @RequestParam("cateId") String cateId,
	                                       @RequestParam("parentCateId") String parentCateId) {
		model.put("productSKUId", productSKUId);
		model.put("cateId", cateId);
		model.put("parentCateId", parentCateId);
		return "supply/product/updateGroundingProduct";
	}

	/**
	 * 跳转到下架商品修改页面
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toGroundingProduct", method = RequestMethod.GET)
	public String toGroundingProduct(ModelMap model, @RequestParam("productSKUId") String productSKUId, @RequestParam("cateId") String cateId,
	                                 @RequestParam("parentCateId") String parentCateId) {
		model.put("productSKUId", productSKUId);
		model.put("cateId", cateId);
		model.put("parentCateId", parentCateId);
		return "supply/product/groundingProduct";
	}

	/**
	 * 上架商品或修改上架商品信息
	 *
	 * @param groundingProduct
	 * @return
	 */
	@RequestMapping(value = "updateGroundingProduct", method = RequestMethod.POST)
	@ResponseBody
	public ResultData updateGroundingProduct(
			@RequestParam(value = "groundingProduct", required = true) String groundingProduct) {
		Map<String, Object> result = supplyProductSKUService.updateProductSKUModelForSupplier(groundingProduct);
		return new ResultData((boolean) result.remove("success"), result);
	}

	/**
	 * 跳转到上架商品修改页面
	 *
	 * @return
	 */
	@RequestMapping(value = "toDisableProduct", method = RequestMethod.GET)
	public String toDisableProduct(ModelMap model, @RequestParam("productSKUId") String productSKUId,
	                               @RequestParam("cateId") String cateId) {
		model.put("productSKUId", productSKUId);
		model.put("cateId", cateId);
		return "supply/product/disableProduct";
	}

	@RequestMapping(value = "viewProductDetail/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ProductSKUBussinessVO viewProductDeatil(@PathVariable("id") String id) {
		return supplyProductSKUService.getProductDetail(id);
	}

	@RequestMapping(value = "viewProductDetailByProductId/{productSKUId}", method = RequestMethod.GET)
	@ResponseBody
	public ProductSKUBussinessVO viewProductDetailByProductId(@PathVariable("productSKUId") String productSKUId) {
		return supplyProductSKUService.getProductDetailByProductId(productSKUId);
	}




	@RequestMapping(value = "getPriceByProductSKUId", method = RequestMethod.GET)
	@ResponseBody
	public ResultData getPriceByProductSKUId(@RequestParam(value = "productSKUId", required = false) String productSKUId) {
		SysProductSKU sysProductSKU = supplyProductSKUService.getLatestSysProductByProductSKUId(productSKUId);
		BigDecimal price = null;
		if (sysProductSKU != null) {
			SupplyProductSKUVO vo = JsonUtils.toObject(sysProductSKU.getProductInfos(), SupplyProductSKUVO.class);
			price = vo.getRecommend();
		}
		return new ResultData(true, price);
	}

	@RequestMapping(value = "viewProductDetail", method = RequestMethod.GET)
	public String viewProductDetail(ModelMap model, @RequestParam("productSKUId") String productSKUId, @RequestParam("cateId") String cateId) {
		model.put("productSKUId", productSKUId);
		model.put("cateId", cateId);
		return "supply/product/viewProductDetail";
	}



	private void initData(ModelMap model) {
		SessionUser sessionUser = UserUtil.getUserFromSession();
		model.addAttribute("orgId", sessionUser.getCurrentOrgId());
		model.addAttribute("orgName", sessionUser.getCurrentOrgName());
		model.addAttribute("brands", brandService.findBrandsBySupplierOrgId(sessionUser.getCurrentOrgId(), CommonStatusEnum.有效.getValue(), Integer.MAX_VALUE, 1).getContent());
	}

}