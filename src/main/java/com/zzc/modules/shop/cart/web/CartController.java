package com.zzc.modules.shop.cart.web;

import com.zzc.common.security.util.UserUtil;
import com.zzc.common.security.web.user.SessionUser;
import com.zzc.common.util.ImagePathUtil;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.web.common.ResultData;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.shop.cart.dto.CartDTO;
import com.zzc.modules.shop.cart.entity.Cart;
import com.zzc.modules.shop.cart.service.CartService;
import com.zzc.modules.sysmgr.enums.ImageTypeEnum;
import com.zzc.modules.sysmgr.product.entity.ProductPrice;
import com.zzc.modules.sysmgr.product.service.ProductSKUService;
import com.zzc.modules.sysmgr.product.service.vo.ProductSKUBussinessVO;
import com.zzc.modules.sysmgr.user.purchaser.entity.PurchaserUser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * 购物车页面
 */
@Controller
@RequestMapping(value = "/shop/cart")
public class CartController extends BaseController {

	@Autowired
	private CartService cartService;

	@Autowired
	private ProductSKUService productSKUService;

	/**
	 * 跳转到购物车
	 * @return
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public String getCartView() {
		SessionUser sessionUser = UserUtil.getUserFromSession();
		if (sessionUser == null) {
			return "redirect:/shop/login";
		}
		return "shop/cart/cart";
	}

	/**
	 * 获得购物车中商品数量
	 * @return
	 */
	@RequestMapping(value = "count", method = RequestMethod.GET)
	@ResponseBody
	public Long getCartCount() {
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_purchaserUser.id", UserUtil.getUserFromSession()
				.getCurrentUserId());
		params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		return cartService.findAllCount(params, Cart.class);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public com.zzc.modules.shop.util.Page<CartDTO> getCart(
			@RequestParam(value = "p", required = false) Integer pageNumber,
			@RequestParam(value = "ps", required = false) Integer pageSize,
			ModelMap model) {
		SessionUser sessionUser = UserUtil.getUserFromSession();
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_purchaserUser.id", sessionUser.getCurrentUserId());
		params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		pageNumber = (pageNumber == null) ? 1 : pageNumber;
		pageSize = (pageSize == null) ? 2 : pageSize;
		Page<Cart> page = cartService.findByParams(Cart.class, params,
				pageNumber, pageSize, "DESC", "modifiedTime");
		ProductSKUBussinessVO productSKUBussinessVO = null;
		CartDTO cartDTO = null;
		List<CartDTO> cartDTOs = new ArrayList<>();
		for (Cart cart : page.getContent()) {
			productSKUBussinessVO = productSKUService.getProductDetailShop(
					cart.getProductCategoryId(), cart.getProductSkuId());
			if (productSKUBussinessVO == null) {
				continue;
			}
			cartDTO = new CartDTO();
			cartDTO.setProductStatus(productSKUBussinessVO.getStatus());
			cartDTO.setId(cart.getId());
			if (CollectionUtils.isNotEmpty(productSKUBussinessVO
					.getProductImages())) {
				cartDTO.setImagePath(ImagePathUtil.getImagePath(
						ImageTypeEnum.PRODUCT_IMAGE, productSKUBussinessVO
								.getProductImages().get(0)));
			}
			if (CollectionUtils.isNotEmpty(productSKUBussinessVO
					.getProductPrices())) {
				int purchaserLevel = (Integer) UserUtil.getUserFromSession()
						.getUserDefinedParams().get("purchaserLevel");
				for (ProductPrice productPrice : productSKUBussinessVO
						.getProductPrices()) {
					int priceKindType = productPrice.getPriceKindModel()
							.getPriceKindType().intValue();
//					if (priceKindType > 0 && purchaserLevel == priceKindType) {
//						cartDTO.setPrice(productPrice.getActuallyPrice());
//					}
					// 显示标价
					if (purchaserLevel != priceKindType) {
						cartDTO.setPrice(productPrice.getActuallyPrice());
					}
				}
			}
			cartDTO.setProductCategoryId(cart.getProductCategoryId());
			cartDTO.setProductName(productSKUBussinessVO.getProductName());
			cartDTO.setProductSKUId(cart.getProductSkuId());
			cartDTO.setQuantity(cart.getQuantity());
			cartDTO.setTotalPrice(cartDTO.getPrice().multiply(
					new BigDecimal(cart.getQuantity())));
			cartDTO.setHasTax(productSKUBussinessVO.getHasTax());
			cartDTO.setHasTransportation(productSKUBussinessVO.getHasTransportation());
			cartDTOs.add(cartDTO);
		}
		return com.zzc.modules.shop.util.Page.newInstanceFromSpringPage(
				pageNumber, pageSize, page.getTotalElements(), cartDTOs);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Boolean deleteCart(@PathVariable("id") String id) {
		if (StringUtils.isBlank(id)) {
			return Boolean.FALSE;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("AND_EQ_purchaserUser.id", UserUtil.getUserFromSession()
				.getCurrentUserId());
		params.put("AND_EQ_id", id);
		List<Cart> carts = cartService.findAll(params, Cart.class);
		if (CollectionUtils.isEmpty(carts)) {
			return Boolean.FALSE;
		}
		Cart cart = carts.get(0);
		cart.setStatus(CommonStatusEnum.无效.getValue());
		cart.setModifiedTime(new Date());
		cartService.save(cart);
		return Boolean.TRUE;
	}

	@RequestMapping(value = "updateQuantity", method = RequestMethod.POST)
	@ResponseBody
	public ResultData updateQuantity(String id, Integer quantity) {
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_id", id);
		params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		List<Cart> carts = cartService.findAll(params, Cart.class);
		if (CollectionUtils.isEmpty(carts)) {
			return new ResultData(false);
		}
		Cart cart = carts.get(0);
		cart.setQuantity(quantity);
		cartService.update(cart);
		return new ResultData(true);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResultData addCart(@RequestBody Cart cart) {
		Long cartCount = getCartCount();
		if (StringUtils.isBlank(cart.getProductSkuId())
				|| StringUtils.isBlank(cart.getProductCategoryId())
				|| cart.getQuantity() == null) {
			return new ResultData(Boolean.FALSE, cartCount);
		}
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_productSkuId", cart.getProductSkuId());
		params.put("AND_EQ_productCategoryId", cart.getProductCategoryId());
		params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		params.put("AND_EQ_purchaserUser.id", UserUtil.getUserFromSession()
				.getCurrentUserId());
		List<Cart> carts = cartService.findAll(params, Cart.class);
		if (CollectionUtils.isEmpty(carts)) {
			PurchaserUser purchaser = new PurchaserUser();
			purchaser.setId(UserUtil.getUserFromSession().getCurrentUserId());
			cart.setPurchaserUser(purchaser);
			cart.setStatus(CommonStatusEnum.有效.getValue());
			cartService.save(cart);
			cartCount += 1;
		} else {
			Cart existCart = carts.get(0);
			existCart.setModifiedTime(new Date());
			existCart.setQuantity(existCart.getQuantity() + cart.getQuantity());
			cartService.save(existCart);
		}
		return new ResultData(Boolean.TRUE, cartCount);
	}

}
