package com.zzc.modules.supply.web;

import com.zzc.common.page.PageForShow;
import com.zzc.common.security.util.UserUtil;
import com.zzc.common.security.web.user.SessionUser;
import com.zzc.common.util.ImagePathUtil;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.sysmgr.enums.ImageTypeEnum;
import com.zzc.modules.sysmgr.file.entity.Image;
import com.zzc.modules.sysmgr.file.service.ImageService;
import com.zzc.modules.sysmgr.product.entity.Brand;
import com.zzc.modules.sysmgr.product.service.BrandService;
import com.zzc.modules.sysmgr.product.service.ProductSKUService;
import com.zzc.modules.sysmgr.product.service.vo.ProductSKUBussinessVO;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierOrg;
import com.zzc.modules.sysmgr.user.supplier.service.SupplierOrgService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 供应商组织管理（公司信息管理）
 */
@Controller
@RequestMapping(value = "/supply/supplierOrgManage")
public class  SupplierOrgManageController extends BaseController {

	@Autowired
	private SupplierOrgService supplierOrgService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private ProductSKUService productService;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView companyView(ModelMap model) {

		SessionUser sessionUser = UserUtil.getUserFromSession();
		String orgId = "";
		if(StringUtils.isNotEmpty(sessionUser.getCurrentOrgId())){
			orgId =sessionUser.getCurrentOrgId();
		}else{
			return new ModelAndView("supply/supplierOrg/supplierOrg", model);
		}

		SupplierOrg supplierOrg = supplierOrgService.findByPK(orgId);
		if (null == supplierOrg) {

		} else {
			Map<String, Object> imageParams = new HashMap<String, Object>();
			imageParams.put("AND_EQ_relationId", orgId);
			imageParams.put("AND_EQ_relationType",
					ImageTypeEnum.SUPPLIER_LOGO.getValue());
			imageParams.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
			List<Image> imageList = imageService.findAll(imageParams, null);
			ImagePathUtil.setImagePath(ImageTypeEnum.SUPPLIER_LOGO, imageList);
			if (!imageList.isEmpty()) {
				model.put("logoImage", imageList.get(0));
			}
			model.put("LOGOType", ImageTypeEnum.SUPPLIER_LOGO.getValue());
			model.put("honorType", ImageTypeEnum.SUPPLIER_HONOR.getValue());
			model.put("brandLogoType", ImageTypeEnum.BRAND_LOGO.getValue());
			model.put("brandDescType", ImageTypeEnum.BRAND_DESC.getValue());
			model.put("company", supplierOrg);
		}

		return new ModelAndView("supply/supplierOrg/supplierOrg", model);
	}

	@RequestMapping(value = "/getHonorList", method = RequestMethod.GET)
	@ResponseBody
	public String getHonorList(
			@RequestParam(value = "orgId", required = true) String orgId) {
		Map<String, Object> imageParams = new HashMap<String, Object>();
		imageParams.put("AND_EQ_relationId", orgId);
		imageParams.put("AND_EQ_relationType",
				ImageTypeEnum.SUPPLIER_HONOR.getValue());
		imageParams.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		List<Image> imageList = imageService.findAll(imageParams, null);
		ImagePathUtil.setImagePath(ImageTypeEnum.SUPPLIER_HONOR, imageList);

		return JsonUtils.toJson(imageList);
	}

	@RequestMapping(value = "/getBrandList", method = RequestMethod.POST)
	@ResponseBody
	public String getBrandList(
			@RequestParam(value = "orgId", required = true) String orgId) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("AND_EQ_supplierOrg.id", orgId);
		List<Brand> brandList = brandService.findAll(params, null);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (Brand brand : brandList) {
			Map<String, Object> imageParamsLogo = new HashMap<String, Object>();
			imageParamsLogo.put("AND_EQ_relationId", brand.getId());
			imageParamsLogo.put("AND_EQ_relationType",
					ImageTypeEnum.BRAND_LOGO.getValue());
			imageParamsLogo
					.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
			List<Image> imageListLogo = imageService.findAll(imageParamsLogo,
					null);
			ImagePathUtil.setImagePath(ImageTypeEnum.BRAND_LOGO, imageListLogo);

			Map<String, Object> imageParamsDesc = new HashMap<String, Object>();
			imageParamsDesc.put("AND_EQ_relationId", brand.getId());
			imageParamsDesc.put("AND_EQ_relationType",
					ImageTypeEnum.BRAND_DESC.getValue());
			imageParamsDesc
					.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
			List<Image> imageListDesc = imageService.findAll(imageParamsDesc,
					null);
			ImagePathUtil.setImagePath(ImageTypeEnum.BRAND_DESC, imageListDesc);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("brand", brand);
			map.put("imageListLogo", imageListLogo);
			map.put("imageListDesc", imageListDesc);
			list.add(map);
		}
		return JsonUtils.toJson(list);
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
	public String searchBySupplierOrg(@RequestParam(value = "supplierOrgId", required = false) String supplierOrgId, @RequestParam(value = "status", required = false) Integer status,
									  @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "curPage", required = false) Integer curPage) {
		PageForShow<ProductSKUBussinessVO> vo = productService.searchBySupplierOrg(supplierOrgId, status, pageSize, curPage);
		return JsonUtils.toJson(vo);
	}

}
