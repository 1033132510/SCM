package com.zzc.base.sysmgr;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.zzc.core.BaseServiceTest;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.modules.shop.cart.entity.Cart;
import com.zzc.modules.shop.cart.service.CartService;
import com.zzc.modules.sysmgr.user.purchaser.entity.PurchaserUser;

public class CartServiceTest extends BaseServiceTest {

	@Autowired
	private CartService cartService;

	@Test
	public void testCreate() {
//		for(int i = 1; i < 20; i++) {
//			Cart cart = new Cart();
//			cart.setQuantity(i);
//			cart.setStatus(CommonStatusEnum.有效.getValue());
//			PurchaserUser purchaserUser = new PurchaserUser();
//			purchaserUser.setId("00000000516727be015167287eb90000");
//			cart.setPurchaserUser(purchaserUser);
//			cart.setProductSkuId("402881285166df77015166e9de2a0002" + i);
//			cart.setProductCategoryId("402881375165de040151665cb5080009" + i);
//			cartService.create(cart);
//		}
		Cart cart = new Cart();
		cart.setQuantity(10);
		cart.setStatus(CommonStatusEnum.有效.getValue());
		PurchaserUser purchaserUser = new PurchaserUser();
		purchaserUser.setId("00000000516727be015167287eb90000");
		cart.setPurchaserUser(purchaserUser);
		cart.setProductSkuId("402881285166df77015166e9de2a0002");
		cart.setProductCategoryId("402881375165de040151665cb5080009");
		cartService.create(cart);
	}
}
