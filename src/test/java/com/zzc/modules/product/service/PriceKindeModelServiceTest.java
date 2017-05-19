package com.zzc.modules.product.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.zzc.core.BaseServiceTest;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.modules.sysmgr.product.entity.PriceKind;
import com.zzc.modules.sysmgr.product.service.PriceKindService;

/**
 * Created by apple on 2015/11/1
 */
public class PriceKindeModelServiceTest extends BaseServiceTest {

	@Autowired
	private PriceKindService priceKindModelService;

	@Rollback(value = false)
	@Test
	public void createPriceKindeModel() {
		List<PriceKind> list = new ArrayList<PriceKind>();
		PriceKind kindModel = new PriceKind();
		kindModel.setPriceKindName("标价");
		kindModel.setPriceKindDesc("标价");
		kindModel.setStatus(CommonStatusEnum.有效.getValue());
		PriceKind kindModel1 = new PriceKind();
		kindModel1.setPriceKindName("成本价");
		kindModel1.setPriceKindDesc("成本价");
		kindModel1.setStatus(CommonStatusEnum.有效.getValue());
		PriceKind kindModel2 = new PriceKind();
		kindModel2.setPriceKindName("一级客户报价");
		kindModel2.setPriceKindDesc("一级客户报价");
		kindModel2.setStatus(CommonStatusEnum.有效.getValue());
		PriceKind kindModel3 = new PriceKind();
		kindModel3.setPriceKindName("二级客户报价");
		kindModel3.setPriceKindDesc("二级客户报价");
		kindModel3.setStatus(CommonStatusEnum.有效.getValue());
		PriceKind kindModel4 = new PriceKind();
		kindModel4.setPriceKindName("三级客户报价");
		kindModel4.setPriceKindDesc("三级客户报价");
		kindModel4.setStatus(CommonStatusEnum.有效.getValue());
		list.add(kindModel);
		list.add(kindModel1);
		list.add(kindModel2);
		list.add(kindModel3);
		list.add(kindModel4);
		priceKindModelService.createBatch(list);
	}
}
