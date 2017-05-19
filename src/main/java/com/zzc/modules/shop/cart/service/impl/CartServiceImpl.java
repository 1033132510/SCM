package com.zzc.modules.shop.cart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzc.core.dao.BaseDao;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.shop.cart.dao.CartDao;
import com.zzc.modules.shop.cart.entity.Cart;
import com.zzc.modules.shop.cart.service.CartService;

@Service("cartService")
public class CartServiceImpl extends BaseCrudServiceImpl<Cart> implements
		CartService {

	@SuppressWarnings("unused")
	private CartDao cartDao;

	@Autowired
	public CartServiceImpl(BaseDao<Cart> baseDao) {
		super(baseDao);
		this.cartDao = (CartDao) baseDao;
	}

}
