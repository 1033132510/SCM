package com.zzc.modules.product.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zzc.core.BaseServiceTest;
import com.zzc.modules.sysmgr.product.entity.ProductProperties;
import com.zzc.modules.sysmgr.product.service.ProductPropertiesService;

public class ProductCategoryItemKValueServiceTest extends BaseServiceTest {

	@Autowired
	private ProductPropertiesService productCategoryItemValueService;

	@Test
	public void createSupplierTest() {
		List<ProductProperties> list = productCategoryItemValueService.getProductCategoryItemValueByProductAndCategory(
				"40288137513e237101513e238ee50000", "40288137513de0e501513de1d3fb0001", 1);
		System.err.println(list.size());
	}

}
