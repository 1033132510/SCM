package com.zzc.supplier;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zzc.core.BaseServiceTest;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierOrg;
import com.zzc.modules.sysmgr.user.supplier.entity.SupplierUser;
import com.zzc.modules.sysmgr.user.supplier.service.SupplierUserService;

public class SupplierUserTest extends BaseServiceTest {

	@Autowired
	private SupplierUserService supplierUserService;

	@Test
	public void createSupplierUserTest() {
		SupplierUser supplierUser = new SupplierUser();

		supplierUser.setEmail("123456@qq.com");
		supplierUser.setIdcard("410622199207105098");
		supplierUser.setUserName("1234567");
		supplierUser.setUserPwd("888888");
		SupplierOrg org = new SupplierOrg();
		org.setId("8a80809750ff11a70150ff11bce40000");
		supplierUser.setSupplierOrg(org);
		supplierUserService.create(supplierUser);

	}
	
	@Test
	public void findAllSupplierUserTest(){
		List<SupplierUser> list = supplierUserService.findAll();
		for(int i=0;i<list.size();i++){
			SupplierUser supplierUser = list.get(i);
			System.err.println(supplierUser.getId()+",name:"+supplierUser.getUserName()+",pwd:"+supplierUser.getUserPwd()
			+",email:"+supplierUser.getEmail()+",身份证号："+supplierUser.getIdcard());
		}
	}

}
