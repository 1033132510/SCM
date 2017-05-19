package com.zzc.modules.sysmgr.product.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzc.common.enums.EnumUtil;
import com.zzc.common.page.PageForShow;
import com.zzc.common.security.util.UserUtil;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.common.ResultData;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.supply.entity.AuditRecord;
import com.zzc.modules.supply.entity.SysProductSKU;
import com.zzc.modules.supply.service.AuditRecordService;
import com.zzc.modules.sysmgr.enums.SupplyAuditBillStatusEnum;
import com.zzc.modules.sysmgr.product.entity.ProductCategoryMainViewProxy;
import com.zzc.modules.sysmgr.product.entity.ProductPrice;
import com.zzc.modules.sysmgr.product.entity.ProductPriceAction;
import com.zzc.modules.sysmgr.product.service.*;
import com.zzc.modules.sysmgr.product.service.vo.ProductPriceActionVO;
import com.zzc.modules.sysmgr.product.service.vo.ProductSKUBussinessVO;
import com.zzc.modules.sysmgr.product.web.vo.ProductSKUModel;
import com.zzc.modules.sysmgr.user.supplier.supplierenum.statusTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品web
 * 
 * @author apple
 *
 */
@Controller
@RequestMapping("/sysmgr/product")
public class ProductSKUController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(ProductSKUController.class);

	@Autowired
	private ProductSKUService productService;

	@Autowired
	private ProductPriceService productPriceService;

	@Autowired
	private ProductPriceActionService productPriceActionService;
	
	@Resource(name = "categoryChargeService")
	private CategoryChargeService categoryChargeService;
	
	@Resource(name = "productCategoryMainViewProxyService")
	private ProductCategoryMainViewProxyService productCategoryMainViewProxyService;
	
	@Autowired
	private AuditRecordService auditRecordService;
	
	/**
	 * 查看员工是否有权限查看或操作商品属性，如果没有权限，跳转到对应页面提示
	 * @param productSKUId
	 */
	private String hasRightToOperateProduct(String productSKUId) {
		String employeeId = UserUtil.getUserFromSession().getCurrentUserId();
		if(!productService.checkRightByProductSKUId(employeeId, productSKUId)) {
			return "common/401";
		} else {
			return null;
		}
	}
	
	/**
	 * 查看员工操作商品的2级类别
	 * @param cateId
	 * @return
	 */
	private String hasRightToOperateCategory(String cateId) {
		String employeeId = UserUtil.getUserFromSession().getCurrentUserId();
		long count = categoryChargeService.countByEmployeeIdAndSecondLevelCateId(employeeId, cateId);
		if(count > 0) {
			return null;
		} else {
			return "common/401";
		}
	}

	/**
	 * 保存产品 write by zhenglong for zhangyong
	 * 
	 * @param productJSON
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public ResultData save(
			@RequestParam(value = "productJSON", required = true) String productJSON) {
		ProductSKUModel model = JsonUtils.toObject(productJSON,
				ProductSKUModel.class);
		
		String productSKUId = model.getProductId();
		
		// 如果是修改商品，需要对商品进行校验是否有权限修改
		if(StringUtils.isNoneBlank(productSKUId)) {
			String result = hasRightToOperateProduct(productSKUId);
			if(StringUtils.isNotBlank(result)) {
				return new ResultData(false, "您没有权限对该资源进行操作", "401");
			}
		} else {
			// 如果是保存商品，需要校验是否有权限保存该商品的品类
			String cateId = model.getCateId();
			if(StringUtils.isNotBlank(cateId)) {
				String result = hasRightToOperateCategory(cateId);
				if(StringUtils.isNotBlank(result)) {
					return new ResultData(false, "您没有权限对该资源进行操作", "401");
				}
			}
		}
		
		productService.createOrUpdateProductSKUModel(model);
		return new ResultData(true, null, null);
	}

	/**
	 * 商品详细信息AJAX JSON 信息
	 * 
	 * @param cateId
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "viewProductDetail", method = RequestMethod.GET)
	@ResponseBody
	public ProductSKUBussinessVO viewProductDeatil(
			@RequestParam(value = "cateId", required = true) String cateId,
			@RequestParam(value = "productId", required = true) String productId) {
		ProductSKUBussinessVO vo = productService.getProductDetail(cateId,
				productId);
		return vo;
	}
	
	/**
	 * 商品详细信息AJAX JSON 信息
	 * 
	 * @param cateId
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "viewProductDetailForSupplier", method = RequestMethod.GET)
	@ResponseBody
	public ProductSKUBussinessVO viewProductDetailForSupplier(
			@RequestParam(value = "cateId", required = true) String cateId,
			@RequestParam(value = "productId", required = true) String productId) {
		ProductSKUBussinessVO vo = productService.getProductDetail(cateId,
				productId);
		// 查询最近一次审批通过时的含税、含运费，如果存在就设置，否则使用正是库的含税、含运费
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("AND_EQ_auditBill.auditStatus", SupplyAuditBillStatusEnum.PASS.getCode());
		param.put("AND_EQ_sysProductSKU.productSKUId", productId);
		List<AuditRecord> auditRecordList = auditRecordService.findAll(param, AuditRecord.class, "desc", "createTime");
		if(auditRecordList.size() > 0) {
			AuditRecord auditRecord = auditRecordList.get(0);
			SysProductSKU sysProductSKU = auditRecord.getSysProductSKU();
			JSONObject productInfo = JSON.parseObject(sysProductSKU.getProductInfos());
			vo.setHasTax(Integer.parseInt(productInfo.get("hasTax") + ""));
			vo.setHasTransportation(Integer.parseInt(productInfo.get("hasTransportation") + ""));
		}
		return vo;
	}

	/**
	 * 跳转到商品详情页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "gotoView", method = RequestMethod.GET)
	public ModelAndView view(
			@RequestParam(value = "productId", required = true) String productId,
			@RequestParam(value = "cateId", required = true) String cateId,
			ModelMap model) {
		model.put("productId", productId);
		model.put("cateId", cateId);
		return new ModelAndView("shop/product/productDetail", model);
	}

	/**
	 * 跳转到有效商品列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/validProductManager", method = RequestMethod.GET)
	public String toProductManager() {
		return "sysmgr/product/validProductList";
	}
	
	/**
	 * 跳转到无效商品列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/invalidProductManager", method = RequestMethod.GET)
	public String invalidProductManager() {
		return "sysmgr/product/invalidProductList";
	}

	/**
	 * 跳转到商品添加页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "toAdd", method = RequestMethod.GET)
	public String toAdd() {
		return "sysmgr/product/addProduct";
	}

	/**
	 * 跳转到商品修改页面
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "toUpdate", method = RequestMethod.GET)
	public String toUpdate(ModelMap map, String id, String cateId) {
		String result = hasRightToOperateProduct(id);
		if(StringUtils.isNotBlank(result)) {
			return result;
		}
		map.put("id", id);
		map.put("cateId", cateId);
		map.put("statusTypeMap", EnumUtil.toMap(statusTypeEnum.class));
		return "sysmgr/product/updateProduct";
	}

	/**
	 * 
	 * @param supplierOrgId
	 * @param status
	 * @param pageSize
	 * @param curPage
	 * @return
	 */
	@RequestMapping(value = "searchBySupplierOrg", method = RequestMethod.GET)
	@ResponseBody
	public String searchBySupplierOrg(
			@RequestParam(value = "supplierOrgId", required = false) String supplierOrgId,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "curPage", required = false) Integer curPage) {
		PageForShow<ProductSKUBussinessVO> vo = productService
				.searchBySupplierOrg(supplierOrgId, status, pageSize, curPage);
		return JsonUtils.toJson(vo);
	}
	
	/**
	 * 搜索商品列表
	 * @param cateId
	 * @param productName
	 * @param brandName
	 * @param status
	 * @param pageSize
	 * @param curPage
	 * @return
	 */
	@RequestMapping(value = "searchForProductManager", method = RequestMethod.POST)
	@ResponseBody
	public String searchForProductManager(@RequestParam(value = "cateId", required = false) String cateId, @RequestParam(value = "productName", required = false) String productName,@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "status", required = false) Integer status, @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "curPage", required = false) Integer curPage) {
		String employeeId = UserUtil.getUserFromSession().getCurrentUserId();
		PageForShow<ProductCategoryMainViewProxy> vo = 
				productCategoryMainViewProxyService.searchForProductManager(employeeId, productName, brandName, cateId, status, pageSize, curPage);
		return JsonUtils.toJson(vo);
	}
	
	/**
	 * 价格修改页面
	 * 
	 * @param model
	 * @param id
	 *            产品Id
	 * @param cateId
	 *            产品分类Id
	 * @return
	 */
	@RequestMapping(value = "toUpdatePrice", method = RequestMethod.GET)
	public String toUpdatePrice(ModelMap model, @RequestParam("id") String id,
			@RequestParam("cateId") String cateId) {
		String result = hasRightToOperateProduct(id);
		if(StringUtils.isNotBlank(result)) {
			return result;
		}
		model.addAttribute("id", id);
		model.addAttribute("cateId", cateId);
		return "sysmgr/product/updateProductPrice";
	}

	/**
	 * 修改价格
	 * 
	 * @param productPricesJson
	 *            价格
	 * @param modifyRecordJson
	 *            修改记录
	 * @param productSkuId
	 *            产品Id
	 * @param productCategoryId
	 *            产品分类Id
	 * @return
	 */
	@RequestMapping(value = "updatePrice", method = RequestMethod.POST)
	@ResponseBody
	public ResultData updatePrice(String productPricesJson,
	                              String modifyRecordJson, String productSkuId,
	                              String unit,
	                              String productCategoryId, String feeRemark, String feeLogistics, Integer hasTax, Integer hasTransportation) {
		String result = hasRightToOperateProduct(productSkuId);
		if(StringUtils.isNotBlank(result)) {
			return new ResultData(false, "您没有权限对该资源进行操作", "401");
		}
		
		logger.info("【商品价格维护，价格参数】" + productPricesJson + "【修改记录参数】"
				+ modifyRecordJson + "【productSkuId】" + productSkuId
				+ "【productCategoryId】" + productCategoryId);
		List<ProductPrice> productPrice = JsonUtils.toList(productPricesJson,
				ArrayList.class, ProductPrice.class);
		ProductPriceActionVO productPriceActionVO = JsonUtils.toObject(
				modifyRecordJson, ProductPriceActionVO.class);
		productPriceActionVO.setOperatorName(UserUtil.getUserFromSession()
				.getDisplayUserName());
		productService.updatePriceAndSaveOperation(productSkuId,
				productCategoryId, productPrice, productPriceActionVO,
				feeRemark, feeLogistics, unit, hasTax, hasTransportation);
		return new ResultData(true);
	}

	@RequestMapping(value = "modifyRecord", method = RequestMethod.GET)
	@ResponseBody
	public String getModifyRecord(String productSkuId, String productCategoryId) {
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		params.put("AND_EQ_productSkuId", productSkuId);
		params.put("AND_EQ_productCategoryId", productCategoryId);
		List<ProductPriceAction> productPriceActions = productPriceActionService
				.findAll(params, ProductPriceAction.class, "DESC", "createTime");
		List<ProductPriceActionVO> modifyRecords = new ArrayList<>();
		for (ProductPriceAction productPriceAction : productPriceActions) {
			modifyRecords.add(JsonUtils.toObject(
					productPriceAction.getModifyRecord(),
					ProductPriceActionVO.class));
		}
		return JsonUtils.toJson(modifyRecords);
	}
	
	/**
	 * 在价格维护中，商品详情页面
	 * @param map
	 * @param id
	 * @param cateId
	 * @return
	 */
	@RequestMapping(value = "searchProductDetail", method = RequestMethod.GET)
	public String searchProductDetail(ModelMap map, String id, String cateId) {
		String result = hasRightToOperateProduct(id);
		if(StringUtils.isNotBlank(result)) {
			return result;
		}
		map.put("id", id);
		map.put("cateId", cateId);
		return "sysmgr/product/searchProductDetail";
	}

	/**
	 * 商品下架页面
	 * 
	 * @param map
	 * @param cateId
	 * @return
	 */
	@RequestMapping(value = "/toDisable")
	public String toDisablePrice(ModelMap map, String id, String cateId) {
		String result = hasRightToOperateProduct(id);
		if(StringUtils.isNotBlank(result)) {
			return result;
		}
		map.put("id", id);
		map.put("cateId", cateId);
		return "sysmgr/product/disableProduct";
	}
	
	/**
	 * 商品下架页面
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/toGroundingProduct")
	public String toGroundingProduct(ModelMap map, String id, String cateId) {
		String result = hasRightToOperateProduct(id);
		if(StringUtils.isNotBlank(result)) {
			return result;
		}
		map.put("id", id);
		map.put("cateId", cateId);
		return "sysmgr/product/groundingProduct";
	}

	/**
	 * 商品下架
	 * 
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/updateProductStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResultData updateProductStatus(String productId, Integer status) {
		String result = hasRightToOperateProduct(productId);
		if(StringUtils.isNotBlank(result)) {
			return new ResultData(false, "您没有权限对该资源进行操作", "401");
		}
		
		boolean success = productService.updateProductStatus(productId, status);
		return new ResultData(success, null, null);
	}
	
//	@RequestMapping(value = "/addBatch1000Products", method = RequestMethod.GET)
//	@ResponseBody
//	public ResultData addBatch1000Products() {
//		productService.addBatch1000Products();
//		return new ResultData(true, null, null);
//	}

}