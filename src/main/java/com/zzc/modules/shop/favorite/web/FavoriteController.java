/**
 * 
 */
package com.zzc.modules.shop.favorite.web;

import java.util.Map;

import javax.annotation.Resource;

import com.zzc.common.security.web.user.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzc.common.page.PageForShow;
import com.zzc.common.security.util.UserUtil;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.shop.favorite.service.FavoriteService;
import com.zzc.modules.sysmgr.product.service.vo.ProductSKUBussinessVO;

/**
 * @author zhangyong
 * 收藏
 *
 */
@Controller
@RequestMapping(value = "/shop/favorite")
public class FavoriteController extends BaseController {
	
	@Resource(name = "favoriteService")
	private FavoriteService favoriteService;
	
	@RequestMapping(value = "/myFavorite")
	public String index() {
		SessionUser sessionUser = UserUtil.getUserFromSession();
		if (sessionUser == null) {
			return "redirect:/shop/login";
		}
		return "shop/favorite/favoriteList";
	}
	
	@RequestMapping(value = "/getMyFavoritePage")
	@ResponseBody
	public String getFavoriteList(Integer curPage, Integer pageSize, String secondLevelCategoryId) {
		String purchaserUserId = UserUtil.getUserFromSession().getCurrentUserId();
		PageForShow<ProductSKUBussinessVO> page = favoriteService.getFavoritePageByUserId(curPage, pageSize, purchaserUserId, secondLevelCategoryId);
		return JsonUtils.toJson(page);
	}
	
	/**
	 * 添加收藏
	 * @param productId
	 * @param productCategoryId
	 * @return
	 */
	@RequestMapping(value = "/addFavorite")
	@ResponseBody
	public Map<String, Object> addFavorite(@RequestParam(name = "productId", required = true) String productId,
			@RequestParam(name = "productCategoryId", required = true) String productCategoryId) {
		String purchaserUserId = UserUtil.getUserFromSession().getCurrentUserId();
		return favoriteService.addFavorite(purchaserUserId, productId, productCategoryId);
	}
	
	/**
	 * 删除收藏
	 * @param favoriteId
	 * @return
	 */
	@RequestMapping(value = "/deleteFavorite")
	@ResponseBody
	public boolean deleteFavorite(@RequestParam(name = "favoriteId", required = true)String favoriteId) {
		favoriteService.delete(favoriteId);
		return true;
	}
	
}