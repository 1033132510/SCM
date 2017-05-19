/**
 * 
 */
package com.zzc.modules.shop.favorite.service;

import com.zzc.common.page.PageForShow;
import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.shop.favorite.entity.Favorite;
import com.zzc.modules.sysmgr.product.service.vo.ProductSKUBussinessVO;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyong
 *
 */
public interface FavoriteService extends BaseCrudService<Favorite> {
	/**
	 * 添加收藏
	 * @param purchaserUserId
	 * @param productId
	 * @param productCategoryId
	 * @return
	 */
	public Map<String, Object> addFavorite(String purchaserUserId, String productId, 
			String productCategoryId);

	/**
	 * 分页显示收藏信息
	 * @param curPage
	 * @param pageSize
	 * @param purchaserUserId
	 * @param secondLevelCategoryId
	 * @return
	 */

	public PageForShow<ProductSKUBussinessVO> getFavoritePageByUserId(Integer curPage, Integer pageSize,
			String purchaserUserId, String secondLevelCategoryId);
	
	/**
	 * 统计用户收藏的二级商品分类的商品个数
	 * @param purchaserUserId
	 * @param status
	 * @return
	 */
	public List<Map<String, Object>> countProductBySecondCategoryLevel(String purchaserUserId, Integer status);
}