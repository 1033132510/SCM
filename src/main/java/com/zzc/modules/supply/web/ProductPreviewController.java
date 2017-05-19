package com.zzc.modules.supply.web;

import com.zzc.common.util.ImagePathUtil;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.supply.entity.AuditBill;
import com.zzc.modules.supply.entity.SysProductSKU;
import com.zzc.modules.supply.service.AuditBillService;
import com.zzc.modules.supply.vo.SupplyProductSKUVO;
import com.zzc.modules.sysmgr.enums.ImageTypeEnum;
import com.zzc.modules.sysmgr.file.entity.Image;
import com.zzc.modules.sysmgr.file.service.ImageService;
import com.zzc.modules.sysmgr.product.entity.PriceKind;
import com.zzc.modules.sysmgr.product.entity.ProductPrice;
import com.zzc.modules.sysmgr.product.entity.ProductProperties;
import com.zzc.modules.sysmgr.product.service.*;
import com.zzc.modules.sysmgr.product.service.vo.ProductSKUBussinessVO;
import com.zzc.modules.sysmgr.product.web.vo.ProductPropertiesModel;
import com.zzc.modules.sysmgr.user.supplier.service.SupplierOrgService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * （供应商 商品库管理）产品预览
 * Created by chenjiahai on 16/3/24.
 */
@RequestMapping("product/preview")
@Controller
public class ProductPreviewController extends BaseController {

	@Autowired
	private BrandService brandService;

	@Autowired
	private CategoryService categoryService;

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

	@RequestMapping(value = "{auditBillId}", method = RequestMethod.GET)
	public String previewProductForUpdate(@PathVariable(value = "auditBillId") String auditBillId,
	                                      @RequestParam(value = "price", required = false) String price,
	                                      @RequestParam(value = "priceKindId", required = false) String priceKindId,
	                                      @RequestParam(value = "hasTax", required = false) Integer hasTax,
	                                      @RequestParam(value = "hasTransportation", required = false) Integer hasTransportation, ModelMap model) {
		model.addAttribute("price", price);
		model.addAttribute("priceKindId", priceKindId);
		model.addAttribute("auditBillId", auditBillId);
		model.addAttribute("hasTax", hasTax);
		model.addAttribute("hasTransportation", hasTransportation);
		model.addAttribute("previewType", 2);
		return "supply/product/productDetail";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String previewProduct(@RequestParam(value = "productDetail", required = false) String productDetail, ModelMap model) {
		model.addAttribute("productDetail", productDetail);
		model.addAttribute("previewType", 1);
		return "supply/product/productDetail";
	}

	@RequestMapping(value = "detail", method = RequestMethod.POST)
	@ResponseBody
	public ProductSKUBussinessVO previewProductDetail(@RequestParam(value = "productDetail", required = false) String productDetail,
	                                                  @RequestParam(value = "auditBillId", required = false) String auditBillId,
	                                                  @RequestParam(value = "previewType", required = false) String previewType,
	                                                  @RequestParam(value = "price", required = false) String price,
	                                                  @RequestParam(value = "priceKindId", required = false) String priceKindId,
	                                                  @RequestParam(value = "hasTax", required = false) Integer hasTax,
	                                                  @RequestParam(value = "hasTransportation", required = false) Integer hasTransportation) {
		ProductSKUBussinessVO bussinessVO = null;
		SupplyProductSKUVO supplyProductSKUVO = null;
		if (Integer.valueOf(previewType) == 1) { //TODO 增加枚举类型, 添加或编辑商品时,直接获取正在填写内容
			supplyProductSKUVO = JsonUtils.toObject(productDetail, SupplyProductSKUVO.class);
			bussinessVO = buildProductSKUBussinessVO(supplyProductSKUVO);
			setProductCategoryItemValues(bussinessVO, supplyProductSKUVO.getProductPropertiesModels());
			setProductPrices(bussinessVO, supplyProductSKUVO);
		} else if (Integer.parseInt(previewType) == 2) {
			AuditBill auditBill = auditBillService.findByPK(auditBillId);
			SysProductSKU sysProductSKU = auditBill.getSysProductSKU();
			supplyProductSKUVO = JsonUtils.toObject(sysProductSKU.getProductInfos(), SupplyProductSKUVO.class);
			bussinessVO = buildProductSKUBussinessVO(supplyProductSKUVO);
			bussinessVO.setHasTax(hasTax);
			bussinessVO.setHasTransportation(hasTransportation);
			bussinessVO.setProductCategoryItemValues(productPropertiesService
					.getProductCategoryItemValueByProductAndCategory(sysProductSKU.getId(),
							supplyProductSKUVO.getThirdLevelCateId(), CommonStatusEnum.有效.getValue()));
			setProductPrices(bussinessVO, price, priceKindId);
		}
		setProductImages(bussinessVO, supplyProductSKUVO);
		return bussinessVO;
	}

	private void setProductPrices(ProductSKUBussinessVO bussinessVO, String price, String priceKindId) {
		List<ProductPrice> productPrices = new ArrayList<>();
		PriceKind priceKind = priceKindService.findByPK(priceKindId);
		ProductPrice productPrice = new ProductPrice();
		productPrice.setActuallyPrice(new BigDecimal(price));
		productPrice.setPriceKindModel(priceKind);
		productPrices.add(productPrice);
		bussinessVO.setProductPrices(productPrices);
	}

	private void setProductPrices(ProductSKUBussinessVO bussinessVO, SupplyProductSKUVO supplyProductSKUVO) {
		List<ProductPrice> productPrices = new ArrayList<>();
		PriceKind priceKind = priceKindService.findByPK("6");
		ProductPrice productPrice = new ProductPrice();
		productPrice.setActuallyPrice(supplyProductSKUVO.getRecommend());
		productPrice.setPriceKindModel(priceKind);
		productPrices.add(productPrice);
		bussinessVO.setProductPrices(productPrices);
	}

	private void setProductCategoryItemValues(ProductSKUBussinessVO bussinessVO, List<ProductPropertiesModel> productPropertiesModels) {
		List<ProductProperties> productPropertiesList = new ArrayList<>();
		for (ProductPropertiesModel productPropertiesModel : productPropertiesModels) {
			ProductProperties productProperties = new ProductProperties();
			productProperties.setValue(productPropertiesModel.getValue());
			productProperties.setProductPropertiesName(productPropertiesModel.getProductPropertiesName());
			if (StringUtils.isNotBlank(productPropertiesModel.getProductCategoryItemKeyId())) {
				productProperties.setProductCategoryItemKey(categoryItemService.findByPK(productPropertiesModel.getProductCategoryItemKeyId()));
			}
			productProperties.setProductCategoryId(productPropertiesModel.getProductCategoryId());
			productProperties.setItemType(productPropertiesModel.getItemType());
			productProperties.setOrderNumber(productPropertiesModel.getOrder());
			productPropertiesList.add(productProperties);
		}
		Collections.sort(productPropertiesList, new Comparator<ProductProperties>() {
			@Override
			public int compare(ProductProperties o1, ProductProperties o2) {
				return o1.getOrderNumber().compareTo(o2.getOrderNumber());
			}
		});
		bussinessVO.setProductCategoryItemValues(productPropertiesList);
	}

	private void setProductImages(ProductSKUBussinessVO bussinessVO, SupplyProductSKUVO vo) {
		List<Image> productImages = new ArrayList<>();
		List<Image> productDescImages = new ArrayList<>();
		for (String imageId : vo.getImageIds()) {
			Image image = imageService.findByPK(imageId);
			if (ImageTypeEnum.PRODUCT_IMAGE.getValue() == image.getRelationType()) {
				productImages.add(image);
			} else if (ImageTypeEnum.PRODUCT_DESC.getValue() == image.getRelationType()) {
				productDescImages.add(image);
			}
		}
		// TODO 根据页面内容预览商品信息, 相关排序方法Extract
//		Collections.sort(productImages, new Comparator<Image>() {
//			@Override
//			public int compare(Image o1, Image o2) {
//				return o1.getCreateTime().before(o2.getCreateTime()) ? -1 : 1;
//			}
//		});
//		Collections.sort(productDescImages, new Comparator<Image>() {
//			@Override
//			public int compare(Image o1, Image o2) {
//				return o1.getCreateTime().before(o2.getCreateTime()) ? -1 : 1;
//			}
//		});
		ImagePathUtil.setImagePath(ImageTypeEnum.PRODUCT_IMAGE,
				productImages);
		ImagePathUtil.setImagePath(ImageTypeEnum.PRODUCT_DESC,
				productDescImages);
		bussinessVO.setProductImages(productImages);
		bussinessVO.setProductDescImages(productDescImages);
		List<Image> brandLogoImages = imageService
				.findByRelationIdAndRelationType(vo.getBrandId(),
						ImageTypeEnum.BRAND_LOGO.getValue(),
						CommonStatusEnum.有效.getValue());
		ImagePathUtil.setImagePath(ImageTypeEnum.BRAND_LOGO,
				brandLogoImages);
		bussinessVO.setBrandLogoImages(brandLogoImages);
		List<Image> brandDescImages = imageService
				.findByRelationIdAndRelationType(vo.getBrandId(),
						ImageTypeEnum.BRAND_DESC.getValue(),
						CommonStatusEnum.有效.getValue());
		ImagePathUtil.setImagePath(ImageTypeEnum.BRAND_DESC,
				brandDescImages);
		bussinessVO.setBrandDescImages(brandDescImages);
		List<Image> honorImages = imageService
				.findByRelationIdAndRelationType(vo.getSupplierOrgId(),
						ImageTypeEnum.SUPPLIER_HONOR.getValue(),
						CommonStatusEnum.有效.getValue());
		ImagePathUtil.setImagePath(ImageTypeEnum.SUPPLIER_HONOR,
				honorImages);
		bussinessVO.setHonorImages(honorImages);
		List<Image> supplierLogoImages = imageService
				.findByRelationIdAndRelationType(vo.getSupplierOrgId(),
						ImageTypeEnum.SUPPLIER_LOGO.getValue(),
						CommonStatusEnum.有效.getValue());
		ImagePathUtil.setImagePath(ImageTypeEnum.SUPPLIER_LOGO,
				supplierLogoImages);
		bussinessVO.setSupplierLogoImages(supplierLogoImages);
	}

	private ProductSKUBussinessVO buildProductSKUBussinessVO(SupplyProductSKUVO vo) {
		ProductSKUBussinessVO bussinessVO = new ProductSKUBussinessVO();
		bussinessVO.setBrand(brandService.findByPK(vo.getBrandId()));
		bussinessVO.setProductDesc(vo.getProductDesc());
		bussinessVO.setStatus(vo.getStatus());
		bussinessVO.setFeeLogistics(vo.getFeeLogistics());
		bussinessVO.setFeeRemark(vo.getFeeRemark());
		bussinessVO.setProductCode(vo.getProductCode());
//		bussinessVO.setProductId(productSKUModel.getProductId());
		bussinessVO.setProductName(vo.getProductName());
		bussinessVO.setProductNumber(vo.getProductNumber());
		bussinessVO.setSupplierOrg(supplierOrgService.findByPK(vo.getSupplierOrgId()));
		bussinessVO.setUnit(vo.getUnit());
		bussinessVO.setHasTax(vo.getHasTax());
		bussinessVO.setHasTransportation(vo.getHasTransportation());
		bussinessVO.setCategory(categoryService.findByPK(vo.getThirdLevelCateId()));
		bussinessVO.setMinOrderCount(vo.getMinOrderCount());
		return bussinessVO;
	}
}
