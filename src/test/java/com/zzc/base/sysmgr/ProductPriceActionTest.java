package com.zzc.base.sysmgr;

import com.zzc.core.BaseServiceTest;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.utils.JsonUtils;
import com.zzc.modules.sysmgr.product.entity.*;
import com.zzc.modules.sysmgr.product.service.ProductCategoryService;
import com.zzc.modules.sysmgr.product.service.ProductPriceActionService;
import com.zzc.modules.sysmgr.product.service.ProductPriceService;
import com.zzc.modules.sysmgr.product.service.ProductSKUService;
import com.zzc.modules.sysmgr.product.service.vo.ProductPriceActionVO;
import com.zzc.modules.sysmgr.user.purchaser.web.PurchaserController;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenjiahai on 16/1/6.
 * <p/>
 * 所有已添加的并且之后没有操作过价格维护的商品价格添加到ES_PRODUCT_PRICE_ACTION表中
 */
public class ProductPriceActionTest extends BaseServiceTest {

	private static Logger logger = LoggerFactory
			.getLogger(ProductPriceActionTest.class);

	@Autowired
	private ProductSKUService productSKUService;

	@Autowired
	private ProductPriceService productPriceService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Autowired
	private ProductPriceActionService productPriceActionService;

	@Test
	public void test() {
		List<ProductSKU> productSKUs = productSKUService.findAll();
		for (ProductSKU productSKU : productSKUs) {
			Category category = findThirdLevelCategoryByProductSKUId(productSKU.getId());
			if (category == null) {
				continue;
			}
			List<ProductPriceAction> productPriceActions = findProductPriceActions(productSKU, category);
			if (CollectionUtils.isNotEmpty(productPriceActions)) {
				modifyProductPriceActions(productPriceActions);
			} else {
				saveProductPriceActionOfFirstTime(productSKU, category);
			}
		}
	}

	/**
	 * 修改已经存在的价格维护记录, 增加含税和含运费
	 *
	 * @param productPriceActions
	 */
	private void modifyProductPriceActions(List<ProductPriceAction> productPriceActions) {
		for (ProductPriceAction productPriceAction : productPriceActions) {
			ProductPriceActionVO productPriceActionVO = JsonUtils.toObject(productPriceAction.getModifyRecord(), ProductPriceActionVO.class);
			productPriceActionVO.setHasTax(1);
			productPriceActionVO.setHasTransportation(1);
			productPriceAction.setModifyRecord(JsonUtils.toJson(productPriceActionVO));
			productPriceActionService.update(productPriceAction);
		}
	}

	/**
	 * 查找3级类别
	 *
	 * @param productSKUId
	 * @return
	 */
	private Category findThirdLevelCategoryByProductSKUId(String productSKUId) {
		Map<String, Object> cateParams = new HashMap<>();
		cateParams.put("AND_EQ_level", 3);
		cateParams.put("AND_EQ_productSKU.id", productSKUId);
		List<ProductCategory> productCategorys = productCategoryService.findAll(cateParams, ProductCategory.class);
		if (CollectionUtils.isEmpty(productCategorys)) {
			return null;
		}
		Category category = productCategorys.get(0).getCategory();
		return category;
	}

	private List<ProductPriceAction> findProductPriceActions(ProductSKU productSKU, Category category) {
		Map<String, Object> productPriceParams = new HashMap<>();
		productPriceParams.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		productPriceParams.put("AND_EQ_productSkuId", productSKU.getId());
		productPriceParams.put("AND_EQ_productCategoryId", category.getId());
		return productPriceActionService.findAll(productPriceParams, ProductPriceAction.class);
	}

	private void saveProductPriceActionOfFirstTime(ProductSKU productSKU, Category category) {
		List<ProductPrice> productPrices = productPriceService.getProductPriceByProductId(productSKU.getId(), CommonStatusEnum.有效.getValue(), null);
		ProductPriceActionVO action = new ProductPriceActionVO();
		for (ProductPrice productPrice : productPrices) {
			PriceKind priceKindModel = productPrice.getPriceKindModel();
			switch (priceKindModel.getId()) {
				case "1":
					action.setStandard(String.valueOf(productPrice.getActuallyPrice()));
					break;
				case "2":
					action.setCost(String.valueOf(productPrice.getActuallyPrice()));
					break;
				case "3":
					action.setLevel1(String.valueOf(productPrice.getActuallyPrice()));
					break;
				case "4":
					action.setLevel2(String.valueOf(productPrice.getActuallyPrice()));
					break;
				case "5":
					action.setLevel3(String.valueOf(productPrice.getActuallyPrice()));
					break;
			}
		}
		action.setOperatorName("韩晶");
		action.setHasTax(productSKU.getHasTax() == null ? 1 : productSKU.getHasTax());
		action.setHasTransportation(productSKU.getHasTransportation() == null ? 1 : productSKU.getHasTransportation());
		productPriceActionService.saveProductPriceAction(productSKU.getId(), category.getId(), action);
	}
}
