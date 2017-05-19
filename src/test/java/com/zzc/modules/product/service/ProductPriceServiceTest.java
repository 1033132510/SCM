package com.zzc.modules.product.service;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.zzc.core.BaseServiceTest;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.modules.sysmgr.product.entity.PriceKind;
import com.zzc.modules.sysmgr.product.entity.ProductPrice;
import com.zzc.modules.sysmgr.product.entity.ProductSKU;
import com.zzc.modules.sysmgr.product.service.PriceKindService;
import com.zzc.modules.sysmgr.product.service.ProductPriceService;
import com.zzc.modules.sysmgr.product.service.ProductSKUService;

/**
 * Created by apple on 2015/11/1.
 */
public class ProductPriceServiceTest extends BaseServiceTest {

	@Autowired
	private ProductPriceService productPriceService;

	@Autowired
	private PriceKindService priceKindModelService;

	@Autowired
	private ProductSKUService productService;

	@Rollback(value = false)
	@Test
	public void createProductPrice() {
		ProductPrice price = new ProductPrice();
		PriceKind model = priceKindModelService.findByPK("402881375139cb93015139cbac5e0000");
		ProductSKU sku = productService.findByPK("1");
		System.err.println(sku + "@@@@####");
		price.setPriceKindModel(model);
		price.setActuallyPrice(new BigDecimal(22.2));
		price.setStatus(CommonStatusEnum.有效.getValue());
		price.setReModified(1);
		productPriceService.create(price);
	}
}
