package com.zzc.modules.product.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.zzc.core.BaseServiceTest;
import com.zzc.modules.sysmgr.product.service.ProductSearchViewProxyService;

/**
 * Created by apple on 2015/11/1.
 */
public class DepartServiceImplTest extends BaseServiceTest {

	@Autowired
	private ProductSearchViewProxyService productSearchViewService;

	@Rollback(value = true)
	@Test
	public void createBrand() {

	}

}
