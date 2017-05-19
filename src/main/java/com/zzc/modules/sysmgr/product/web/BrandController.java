package com.zzc.modules.sysmgr.product.web;

import com.alibaba.fastjson.JSON;
import com.zzc.common.page.PageForShow;
import com.zzc.core.exceptions.BizException;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.common.ResultData;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.sysmgr.product.entity.Brand;
import com.zzc.modules.sysmgr.product.service.BrandService;
import com.zzc.modules.sysmgr.user.supplier.service.SupplierOrgService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 品牌管理
 * @author apple
 *
 */
@Controller
@RequestMapping("/sysmgr/brand/")
public class BrandController extends BaseController {

	@Autowired
	private BrandService brandService;

	@Autowired
	private SupplierOrgService supplierOrgService;

	private final static String ERROR_MSG_NAME_NOT_NULL = "品牌的中文名称和英文名称必须填写一个";

	/**
	 *  跳转到组织下属品牌列表页面
	 * 
	 * @param supplierOrgId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listByOrg", method = RequestMethod.GET)
	public ModelAndView getBrandManagerView(@RequestParam(value = "supplierOrgId", required = true) String supplierOrgId, ModelMap model) {
		model.put("supplierOrgId", supplierOrgId);
		return new ModelAndView("sysmgr/product/brandList", model);
	}
	
	/**
	 *  跳转到组织下属品牌列表页面--品牌无效列表
	 * 
	 * @param supplierOrgId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "invalidBrandList", method = RequestMethod.GET)
	public ModelAndView invalidBrandList(@RequestParam(value = "supplierOrgId", required = true) String supplierOrgId, ModelMap model) {
		model.put("supplierOrgId", supplierOrgId);
		return new ModelAndView("sysmgr/product/invalidBrandList", model);
	}

	/**
	 * 品牌添加
	 * 
	 * @param brandId
	 * @param supplierOrgId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam(value = "brandId", required = false) String brandId, @RequestParam(value = "supplierOrgId", required = false) String supplierOrgId, ModelMap model) {
		Brand brand = new Brand();
		if (brandId != null) {
			brand = brandService.findByPK(brandId);
		} else {
			brand.setSupplierOrg(supplierOrgService.findByPK(supplierOrgId));
		}
		model.put("brand", brand);
		return new ModelAndView("sysmgr/product/brandView", model);
	}
	
	/**
	 * 失效的品牌信息修改
	 * 
	 * @param brandId
	 * @param supplierOrgId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toUpdateDisabledBrand", method = RequestMethod.GET)
	public ModelAndView toUpdateDisabledBrand(@RequestParam(value = "brandId", required = false) String brandId, @RequestParam(value = "supplierOrgId", required = false) String supplierOrgId, ModelMap model) {
		Brand brand = brandService.findByPK(brandId);
		model.put("brand", brand);
		return new ModelAndView("sysmgr/product/toUpdateDisabledBrand", model);
	}
	
	/**
	 * 品牌失效
	 * 
	 * @param brandId
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "toDisableBrand", method = RequestMethod.GET)
	public String toDisableBrand(ModelMap map, String brandId) {
		map.put("brand", brandService.findByPK(brandId));
		return "sysmgr/product/toDisableBrand";
	}
	
	/**
	 * 品牌有效
	 * 
	 * @param brandId
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "toGroundingBrand", method = RequestMethod.GET)
	public String toGroundingBrand(ModelMap map, String brandId) {
		map.put("brand", brandService.findByPK(brandId));
		return "sysmgr/product/toGroundingBrand";
	}

	/**
	 *
	 * @param supplierOrgId
	 * @param status
	 * @param pageSize
	 * @param curPage
	 * @return
	 */
	@RequestMapping(value = "getBrandManagerList", method = RequestMethod.POST)
	@ResponseBody
	public String getBrandManagerList(@RequestParam(value = "supplierOrgId", required = true) String supplierOrgId,
			Integer status,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "curPage", required = false) Integer curPage) {
		
		Page<Brand> page = brandService.findBrandsBySupplierOrgId(supplierOrgId, status, pageSize, curPage);
		return JsonUtils.toJson(PageForShow.newInstanceFromSpringPage(page, curPage));
	}

	/**
	 * 保存或者更新
	 *
	 * @param brandJson
	 * @param imageIds
	 * @param deleteImageIds
	 * @return
	 */
	@RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Brand createOrUpdateBrand(@RequestParam(value = "brandJson", required = true) String brandJson, @RequestParam(value = "imageIds[]", required = false) String[] imageIds,
			@RequestParam(value = "deleteImageIds[]", required = false) String[] deleteImageIds) {
		Brand brand = JSON.parseObject(brandJson, Brand.class);
		
		if (StringUtils.isBlank(brand.getBrandENName()) && StringUtils.isBlank(brand.getBrandZHName())) {
			throw new BizException(ERROR_MSG_NAME_NOT_NULL);
		}
		
		brand = brandService.createOrUpdateBrand(brand, imageIds, deleteImageIds);
		return brand;
	}
	@RequestMapping(value = "disableBrand", method = RequestMethod.POST)
	@ResponseBody
	public ResultData createOrUpdateBrand(String brandId, Integer status) {
		return new ResultData(brandService.updateBrand(brandId, status));
	}

}