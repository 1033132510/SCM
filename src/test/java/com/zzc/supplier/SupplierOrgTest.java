package com.zzc.supplier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.zzc.core.BaseServiceTest;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierOrg;
import com.zzc.modules.sysmgr.user.supplier.service.SupplierOrgService;

public class SupplierOrgTest extends BaseServiceTest {

	@Autowired
	private SupplierOrgService supplierOrgservice;

	@Rollback(value = false)
	@Test
	public void createSupplierOTest() {
		for (int i = 0; i < 1; i++) {
			SupplierOrg supplierOrg = new SupplierOrg();
			supplierOrg.setOrgCode("12245" + i);
			supplierOrg.setOrgName("测试供2应商" + i);
			supplierOrg.setContact("联系人");
//			supplierOrg.setContactNumber("12334");
			supplierOrg.setLegalPerson("法人");
			supplierOrg.setMobileNumber("12312");
			supplierOrg.setTelNumber("123");
			supplierOrg.setOrgLevel(1);
			supplierOrg.setSupplierType(1);
			System.err.println("creat start");
			supplierOrgservice.create(supplierOrg);
			System.err.println("creat end");
		}
	}

	// @Test
	public void findAllSupplierTest() {
		List<SupplierOrg> list = supplierOrgservice.findAll();
		for (int i = 0; i < list.size(); i++) {
			SupplierOrg supplierOrg = list.get(i);
			System.err.println("编号：" + supplierOrg.getOrgCode() + ",名称：" + supplierOrg.getOrgName());
		}
	}

	// @Test
	public void updateSupplier() {
		SupplierOrg supplierOrg = new SupplierOrg();
		supplierOrg.setId("0000000050f5aa760150f5aa88fb0000");
		supplierOrg.setOrgCode("12345");
		supplierOrg.setOrgName("测试供应商");
		supplierOrg.setContact("联系人");
//		supplierOrg.setContactNumber("12334");
		supplierOrg.setLegalPerson("法人");
		supplierOrg.setMobileNumber("12312");
		supplierOrg.setTelNumber("123");
		supplierOrgservice.update(supplierOrg);
	}

	// @Test
	public void findAllByconditions() {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("LIKE_supplierName", "测试供应商");
		paramsMap.put("LIKE_contact", "联系人");

		List<SupplierOrg> list = supplierOrgservice.findAll(paramsMap, null);
		for (int i = 0; i < list.size(); i++) {
			SupplierOrg supplierOrg = list.get(i);
			System.err.println("编号：" + supplierOrg.getOrgCode() + ",名称：" + supplierOrg.getOrgName());
		}
	}
}
