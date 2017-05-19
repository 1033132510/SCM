package com.zzc.modules.product.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.zzc.core.BaseServiceTest;
import com.zzc.modules.sysmgr.product.entity.ProductSearchViewProxy;
import com.zzc.modules.sysmgr.product.service.ProductSearchViewProxyService;
import com.zzc.modules.sysmgr.product.web.vo.ItemSearchVO;
import com.zzc.modules.sysmgr.product.web.vo.ProductSearchVO;

/**
 * Created by apple on 2015/11/1.
 */
public class BrandCategoryServiceTest extends BaseServiceTest {

	@Autowired
	private ProductSearchViewProxyService productSearchViewService;

	@Transactional
	@Rollback(value = true)
	@Test
	public void testSearch() {
		ProductSearchVO v = new ProductSearchVO();
		// v.setCateId("ff808081516abc8201516b3e74630c1b");
		List<ItemSearchVO> itemSearchVOs = new ArrayList<ItemSearchVO>();
		ItemSearchVO io = new ItemSearchVO();
		io.setItemCode("3b369a9b-3ec0-4b61-90e5-9bc1903a3098");
		io.setValue("4324234234");
		ItemSearchVO io1 = new ItemSearchVO();
		io1.setItemCode("469144b5-6779-4736-89e4-b36489252439");
		io1.setValue("53453453454");
		itemSearchVOs.add(io);
		itemSearchVOs.add(io1);
		v.setItemSearchVOs(itemSearchVOs);
		v.setCurPage(1);
		v.setPageSize(10);
	}

	@Rollback(value = false)
	// @Test
	public void findBrandByCate() {
//		List<ProductSearchViewProxy> views = productSearchViewService.searchBrandInfoByCateAndProductName("", "ff808081516abc8201516b0bb3cc0721");
//		for (ProductSearchViewProxy view : views) {
//			System.err.println(view.getBrandZHName() + "@@");
//		}
	}

}
