package com.zzc.modules.product.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.zzc.core.BaseServiceTest;
import com.zzc.modules.sysmgr.product.entity.Brand;
import com.zzc.modules.sysmgr.product.service.BrandService;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierOrg;
import com.zzc.modules.sysmgr.user.supplier.service.SupplierOrgService;

/**
 * Created by apple on 2015/11/1.
 */
public class BrandServiceTest extends BaseServiceTest {

	@Autowired
	private BrandService brandService;

	@Autowired
	private SupplierOrgService supplierOrgService;

	@Rollback(value = false)
	// @Test
	public void createBrand() {
		Brand brand = new Brand();
		brand.setBrandZHName("三星1");
		brand.setBrandENName("sunkung1");
		brand.setSupplierOrg(supplierOrgService.findByPK("40288137514c040701514c0421080000"));
		brand.setBrandDesc("woailuo");
		brandService.create(brand);
		List<Brand> list = new ArrayList<Brand>();
		brandService.createBatch(list);
	}

	@Test
	public void changeOrgBrand() {
		Brand brand = brandService.findByPK("8a80809550ffe8140150ffe833d70000");
		SupplierOrg baseOrg = new SupplierOrg();
		brand.setSupplierOrg(baseOrg);
		brandService.update(brand);
	}

}
