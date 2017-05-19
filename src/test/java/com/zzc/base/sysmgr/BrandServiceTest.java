package com.zzc.base.sysmgr;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;

import com.alibaba.fastjson.JSON;
import com.zzc.core.BaseServiceTest;
import com.zzc.modules.sysmgr.product.entity.Brand;
import com.zzc.modules.sysmgr.product.service.BrandService;
import com.zzc.modules.sysmgr.user.base.entity.BaseUser;
import com.zzc.modules.sysmgr.user.base.entity.Role;
import com.zzc.modules.sysmgr.user.base.service.BaseUserService;
import com.zzc.modules.sysmgr.user.base.service.RoleService;

/**
 */
public class BrandServiceTest extends BaseServiceTest {

	@Autowired
	private BrandService brandService;

	@Rollback(value = false)
	@Test
	public void createBrand() {

		Brand brand = new Brand();
		brandService.findAll();
	}

}
