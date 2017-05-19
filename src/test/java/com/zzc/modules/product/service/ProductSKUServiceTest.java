package com.zzc.modules.product.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.zzc.common.page.PageForShow;
import com.zzc.core.BaseServiceTest;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.utils.DateUtils;
import com.zzc.modules.sysmgr.enums.ImageTypeEnum;
import com.zzc.modules.sysmgr.file.entity.Image;
import com.zzc.modules.sysmgr.file.service.ImageService;
import com.zzc.modules.sysmgr.product.entity.Category;
import com.zzc.modules.sysmgr.product.entity.CategoryItem;
import com.zzc.modules.sysmgr.product.entity.ProductCategory;
import com.zzc.modules.sysmgr.product.entity.ProductPrice;
import com.zzc.modules.sysmgr.product.entity.ProductProperties;
import com.zzc.modules.sysmgr.product.entity.ProductSKU;
import com.zzc.modules.sysmgr.product.entity.ProductSearchViewProxy;
import com.zzc.modules.sysmgr.product.enums.ItemTypeEnum;
import com.zzc.modules.sysmgr.product.service.BrandService;
import com.zzc.modules.sysmgr.product.service.CategoryService;
import com.zzc.modules.sysmgr.product.service.PriceKindService;
import com.zzc.modules.sysmgr.product.service.ProductPriceService;
import com.zzc.modules.sysmgr.product.service.ProductPropertiesService;
import com.zzc.modules.sysmgr.product.service.ProductSKUService;
import com.zzc.modules.sysmgr.product.service.ProductSearchViewProxyService;
import com.zzc.modules.sysmgr.product.service.vo.PageMiddle;
import com.zzc.modules.sysmgr.product.service.vo.ProductSKUBussinessVO;
import com.zzc.modules.sysmgr.product.web.vo.ProductPriceModel;
import com.zzc.modules.sysmgr.product.web.vo.ProductPropertiesModel;
import com.zzc.modules.sysmgr.product.web.vo.ProductSKUModel;
import com.zzc.modules.sysmgr.product.web.vo.ProductSearchVO;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierOrg;
import com.zzc.modules.sysmgr.user.supplier.service.SupplierOrgService;

/**
 * Created by apple on 2015/11/1
 */
public class ProductSKUServiceTest extends BaseServiceTest {

	@Autowired
	private ProductSKUService productService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private SupplierOrgService supplierOrgService;

	@Autowired
	private CategoryService productCategoryService;

	@Autowired
	private PriceKindService priceKindModelService;

	@Autowired
	private ProductPriceService productPriceService;

	@Autowired
	private ProductPropertiesService productCategoryItemValueService;

	@Autowired
	private ImageService imageService;

	private final static String brandId = "40288137514dcea101514dcebab20000";
	private final static String supporgOrgId = "40288138515268b70151526b07e20013";
	private final static String cateId = "40288137514db2be01514db505ab0006";

	@Rollback(value = false)
	@Test
	public void testSearch() {
		ProductSearchVO vo = new ProductSearchVO();
		vo.setCateId("");
		vo.setPageSize(10);
		vo.setCurPage(1);
		productService.searchEx(vo);
	}

	@Rollback(value = false)
	// @Test
	public void testCreateProductModel() {
		ProductSKUModel model = new ProductSKUModel();
		model.setBrandId(brandId);
		model.setCateId(cateId);
		model.setFeeLogistics("物流费用");
		model.setFeeRemark("费用备注");
		model.setProductName("系统改进之后的测试商品");
		model.setProductDesc("商品描述");
		model.setProductNumber(1000);
		model.setProductPriceModels(getPriceModel());
		model.setProductPropertiesModels(getPropertieModel(cateId));
		model.setStatus(CommonStatusEnum.有效.getValue());
		model.setSupplierOrgId(supporgOrgId);
		model.setUnit("件");
		productService.createOrUpdateProductSKUModel(model);
	}

	public List<ProductPropertiesModel> getPropertieModel(String cateId) {
		Category c = productCategoryService.findByPK(cateId);
		List<CategoryItem> ccc = c.getProductCategoryItemKeys();
		List<ProductPropertiesModel> propertiesModels = new ArrayList<ProductPropertiesModel>();
		for (CategoryItem ci : ccc) {
			ProductPropertiesModel model = new ProductPropertiesModel();
			model.setItemType(ItemTypeEnum.category.getValue());
			model.setProductCategoryId(cateId);
			model.setProductCategoryItemKeyId(ci.getId());
			model.setStatus(CommonStatusEnum.有效.getValue());
			model.setValue("类别属性显示值1");
			propertiesModels.add(model);
		}

		ProductPropertiesModel model = new ProductPropertiesModel();
		model.setItemType(ItemTypeEnum.product.getValue());
		model.setProductCategoryId(cateId);
		model.setStatus(CommonStatusEnum.有效.getValue());
		model.setValue("类别属性显示值1");
		model.setProductPropertiesName("商品属性值名称");
		propertiesModels.add(model);
		return propertiesModels;
	}

	public List<ProductPriceModel> getPriceModel() {
		ProductPriceModel price1 = new ProductPriceModel();
		price1.setPriceKindId("1");
		price1.setActuallyPrice(new BigDecimal(22));
		price1.setStatus(CommonStatusEnum.有效.getValue());

		ProductPriceModel price2 = new ProductPriceModel();
		price2.setPriceKindId("2");
		price2.setActuallyPrice(new BigDecimal(22));
		price2.setStatus(CommonStatusEnum.有效.getValue());

		ProductPriceModel price3 = new ProductPriceModel();
		price3.setPriceKindId("3");
		price3.setActuallyPrice(new BigDecimal(22));
		price3.setStatus(CommonStatusEnum.有效.getValue());

		ProductPriceModel price4 = new ProductPriceModel();
		price4.setPriceKindId("4");
		price4.setActuallyPrice(new BigDecimal(22));
		price4.setStatus(CommonStatusEnum.有效.getValue());

		ProductPriceModel price5 = new ProductPriceModel();
		price5.setPriceKindId("5");
		price5.setActuallyPrice(new BigDecimal(22));
		price5.setStatus(CommonStatusEnum.有效.getValue());

		List<ProductPriceModel> priceModels = new ArrayList<ProductPriceModel>();
		priceModels.add(price1);
		priceModels.add(price2);
		priceModels.add(price3);
		priceModels.add(price4);
		priceModels.add(price5);

		return priceModels;
	}

	@Rollback(value = false)
	// @Test
	public void testGetProductByCategory() {
		// ProductSKUBussinessVO list =
		// productService.getProductDetail("402881375143b1c3015143b5ec460000",
		// "402881375143cb5d015143cb860c0000", CommonStatusEnum.有效.getValue());
		// System.err.println(JsonUtils.toJson(list) + "##");
	}

	@Rollback(value = false)
	// @Test
	public void createProduct() {
		ProductSKU productSKU = new ProductSKU();
		productSKU.setProductName("商品录入--测试商品名称");
		productSKU.setBrand(brandService.findByPK(brandId));
		productSKU.setSupplierOrg(supplierOrgService.findByPK(supporgOrgId));
		productSKU.setFeeLogistics("物流费用描述");
		productSKU.setFeeRemark("费用备注");
		Category productCategory = productCategoryService.findByPK(cateId);
		List<Category> cates = new ArrayList<Category>();
		cates.add(productCategory);

		productSKU.setProductCode(getProductCode(cateId, supporgOrgId));
		productSKU.setProductNumber(100);
		productSKU.setStatus(CommonStatusEnum.有效.getValue());
		productSKU.setUnit("件");
		productSKU.setProductDesc("<p>12334</p>");
		productService.create(productSKU);
		insertProductCategory(productSKU, productCategory);
		insertSKUPrice(productSKU);
		insertProductCategoryItemValues(productSKU, productCategory);
		insertSKUImages(productSKU);
		insertSKUImages1(productSKU);
	}

	public void insertProductCategory(ProductSKU productSKU, Category productCategory) {
		ProductCategory category = new ProductCategory();
		category.setProductSKU(productSKU);
		category.setCategory(productCategory);

	}

	public void insertProductCategoryItemValues(ProductSKU productSKU, Category productCategory) {
		List<CategoryItem> pcs = productCategory.getProductCategoryItemKeys();
		for (CategoryItem itemKey : pcs) {
			ProductProperties itemValue = new ProductProperties();
			itemValue.setProductCategoryId(productCategory.getId());
			itemValue.setProductCategoryItemKey(itemKey);
			itemValue.setProductSKUId(productSKU.getId());
			itemValue.setStatus(CommonStatusEnum.有效.getValue());
			itemValue.setValue("显示类别属性1");
			itemValue.setItemType(ItemTypeEnum.category.getValue());
			productCategoryItemValueService.create(itemValue);
		}
		ProductProperties itemValueForProduct1 = new ProductProperties();
		itemValueForProduct1.setProductCategoryId(productCategory.getId());
		itemValueForProduct1.setProductSKUId(productSKU.getId());
		itemValueForProduct1.setStatus(CommonStatusEnum.有效.getValue());
		itemValueForProduct1.setProductPropertiesName("显示自定义属性名称1");
		itemValueForProduct1.setValue("显示自定义属性值1");
		itemValueForProduct1.setItemType(ItemTypeEnum.product.getValue());
		productCategoryItemValueService.create(itemValueForProduct1);

		ProductProperties itemValueForProduct2 = new ProductProperties();
		itemValueForProduct2.setProductCategoryId(productCategory.getId());
		itemValueForProduct2.setProductSKUId(productSKU.getId());
		itemValueForProduct2.setStatus(CommonStatusEnum.有效.getValue());
		itemValueForProduct2.setProductPropertiesName("显示自定义属性名称2");
		itemValueForProduct2.setValue("显示自定义属性值2");
		itemValueForProduct2.setItemType(ItemTypeEnum.product.getValue());
		productCategoryItemValueService.create(itemValueForProduct2);

		ProductProperties itemValueForProduct3 = new ProductProperties();
		itemValueForProduct3.setProductCategoryId(productCategory.getId());
		itemValueForProduct3.setProductSKUId(productSKU.getId());
		itemValueForProduct3.setStatus(CommonStatusEnum.有效.getValue());
		itemValueForProduct3.setProductPropertiesName("显示自定义属性名称3");
		itemValueForProduct3.setValue("显示自定义属性值3");
		itemValueForProduct3.setItemType(ItemTypeEnum.product.getValue());
		productCategoryItemValueService.create(itemValueForProduct3);

		ProductProperties itemValueForProduct4 = new ProductProperties();
		itemValueForProduct4.setProductCategoryId(productCategory.getId());
		itemValueForProduct4.setProductSKUId(productSKU.getId());
		itemValueForProduct4.setStatus(CommonStatusEnum.有效.getValue());
		itemValueForProduct4.setProductPropertiesName("显示自定义属性名称4");
		itemValueForProduct4.setValue("显示自定义属性值4");
		itemValueForProduct4.setItemType(ItemTypeEnum.product.getValue());
		productCategoryItemValueService.create(itemValueForProduct4);
	}

	public void insertSKUPrice(ProductSKU productSKU) {
		ProductPrice price1 = new ProductPrice();
		price1.setPriceKindModel(priceKindModelService.findByPK("1"));
		price1.setActuallyPrice(new BigDecimal(22));
		price1.setProductSKUId(productSKU.getId());
		price1.setReModified(0);
		price1.setStatus(CommonStatusEnum.有效.getValue());
		ProductPrice price2 = new ProductPrice();
		price2.setPriceKindModel(priceKindModelService.findByPK("2"));
		price2.setActuallyPrice(new BigDecimal(22));
		price2.setProductSKUId(productSKU.getId());
		price2.setReModified(0);
		price2.setStatus(CommonStatusEnum.有效.getValue());
		ProductPrice price3 = new ProductPrice();
		price3.setPriceKindModel(priceKindModelService.findByPK("3"));
		price3.setActuallyPrice(new BigDecimal(22));
		price3.setProductSKUId(productSKU.getId());
		price3.setReModified(0);
		price3.setStatus(CommonStatusEnum.有效.getValue());
		ProductPrice price4 = new ProductPrice();
		price4.setPriceKindModel(priceKindModelService.findByPK("4"));
		price4.setActuallyPrice(new BigDecimal(22));
		price4.setProductSKUId(productSKU.getId());
		price4.setReModified(0);
		price4.setStatus(CommonStatusEnum.有效.getValue());
		ProductPrice price5 = new ProductPrice();
		price5.setPriceKindModel(priceKindModelService.findByPK("5"));
		price5.setActuallyPrice(new BigDecimal(22));
		price5.setReModified(0);
		price5.setProductSKUId(productSKU.getId());
		price5.setStatus(CommonStatusEnum.有效.getValue());
		List<ProductPrice> prices = new ArrayList<ProductPrice>();
		prices.add(price1);
		prices.add(price2);
		prices.add(price3);
		prices.add(price4);
		prices.add(price5);
		productPriceService.createBatch(prices);
	}

	public void insertSKUImages(ProductSKU productSKU) {
		Image image = new Image();
		image.setAliasName(UUID.randomUUID().toString());
		image.setOriginalName("a.jpg");
		image.setPath("");
		image.setRelationId(productSKU.getId());
		image.setRelationType(ImageTypeEnum.PRODUCT_IMAGE.getValue());
		image.setSortOrder(2);
		image.setStatus(CommonStatusEnum.有效.getValue());
		imageService.create(image);

		Image image1 = new Image();
		image1.setAliasName(UUID.randomUUID().toString());
		image1.setOriginalName("a.jpg");
		image1.setPath("");
		image1.setRelationId(productSKU.getId());
		image1.setRelationType(ImageTypeEnum.PRODUCT_IMAGE.getValue());
		image1.setSortOrder(2);
		image1.setStatus(CommonStatusEnum.有效.getValue());
		imageService.create(image1);

		Image image2 = new Image();
		image2.setAliasName(UUID.randomUUID().toString());
		image2.setOriginalName("a.jpg");
		image2.setPath("");
		image2.setRelationId(productSKU.getId());
		image2.setRelationType(ImageTypeEnum.PRODUCT_IMAGE.getValue());
		image2.setSortOrder(2);
		image2.setStatus(CommonStatusEnum.有效.getValue());
		imageService.create(image2);

	}

	public void insertSKUImages1(ProductSKU productSKU) {
		Image image = new Image();
		image.setAliasName(UUID.randomUUID().toString());
		image.setOriginalName("a.jpg");
		image.setPath("");
		image.setRelationId(productSKU.getId());
		image.setRelationType(ImageTypeEnum.PRODUCT_DESC.getValue());
		image.setSortOrder(2);
		image.setStatus(CommonStatusEnum.有效.getValue());
		imageService.create(image);

		Image image1 = new Image();
		image1.setAliasName(UUID.randomUUID().toString());
		image1.setOriginalName("a.jpg");
		image1.setPath("");
		image1.setRelationId(productSKU.getId());
		image1.setRelationType(ImageTypeEnum.PRODUCT_DESC.getValue());
		image1.setSortOrder(2);
		image1.setStatus(CommonStatusEnum.有效.getValue());
		imageService.create(image1);

		Image image2 = new Image();
		image2.setAliasName(UUID.randomUUID().toString());
		image2.setOriginalName("a.jpg");
		image2.setPath("");
		image2.setRelationId(productSKU.getId());
		image2.setRelationType(ImageTypeEnum.PRODUCT_DESC.getValue());
		image2.setSortOrder(2);
		image2.setStatus(CommonStatusEnum.有效.getValue());
		imageService.create(image2);

	}

	private String getProductCode(String productCategoryId, String supplierOrgId) {
		Category pc = productCategoryService.findByPK(productCategoryId);
		SupplierOrg supplierOrg = supplierOrgService.findByPK(supplierOrgId);
		Date date = new Date();
		String dateFormate = DateUtils.formatDate(date, "yyyy-MM-dd");
		StringBuilder productCode = new StringBuilder("");
		String supplierOrgCode = supplierOrg.getOrgCode();
		supplierOrgCode = supplierOrgCode.replace("GYS-", "").replace("-", "");
		String productCodeLine = productCode.append(pc.getCategoryCode()).append(supplierOrgCode).append(dateFormate).toString();
		productCodeLine = productCodeLine.replace("GYS-", "").replace("-", "");
		System.err.println("productCodeLine" + productCodeLine);
		return productCodeLine;
	}
}
