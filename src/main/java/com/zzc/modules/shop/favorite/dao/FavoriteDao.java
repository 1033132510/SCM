package com.zzc.modules.shop.favorite.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zzc.core.dao.BaseDao;
import com.zzc.modules.shop.favorite.entity.Favorite;

/**
 * @author zhangyong
 *
 */
public interface FavoriteDao extends BaseDao<Favorite>{
	/**
	 * 根据采购商id和商品id查找
	 * @param purchaserUserId
	 * @param productId
	 * @param productCategoryId
	 * @return
	 */
	@Query("from Favorite f where f.purchaserUser.id = :purchaserUserId and f.productSkuId = :productId "
			+ "and f.productCategoryId = :productCategoryId")
	public List<Favorite> findFavoriteListByParam(@Param("purchaserUserId")String purchaserUserId,
			@Param("productId") String productId,
			@Param("productCategoryId")String productCategoryId);
	
	@Query("select category.id, category.cateName, count(*) from Favorite f,"
			+ " Category category"
			+ " where f.parentProductCategoryId = category.id"
			+ " and f.purchaserUser.id = :purchaserUserId and f.status = :status"
			+ " group by category.id, category.cateName")
	public List<Object[]> countProductBySecondCategoryLevel(@Param("purchaserUserId")String purchaserUserId,
			@Param("status")Integer status);
	
}