/**
 * 
 */
package com.zzc.modules.shop.favorite.service.impl;

import com.zzc.common.page.PageForShow;
import com.zzc.core.dao.BaseDao;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.shop.favorite.dao.FavoriteDao;
import com.zzc.modules.shop.favorite.entity.Favorite;
import com.zzc.modules.shop.favorite.service.FavoriteService;
import com.zzc.modules.sysmgr.product.dao.CategoryDao;
import com.zzc.modules.sysmgr.product.entity.Category;
import com.zzc.modules.sysmgr.product.service.CategoryService;
import com.zzc.modules.sysmgr.product.service.ProductSKUService;
import com.zzc.modules.sysmgr.product.service.vo.ProductSKUBussinessVO;
import com.zzc.modules.sysmgr.user.purchaser.dao.PurchaserUserDao;
import com.zzc.modules.sysmgr.user.purchaser.entity.PurchaserUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zhangyong
 *
 */
@Service(value = "favoriteService")
public class FavoriteServiceImpl extends BaseCrudServiceImpl<Favorite> implements FavoriteService {

	private FavoriteDao favoriteDao;

	@Resource(name = "purchaserUserDao")
	private PurchaserUserDao purchaserUserDao;

	@Resource(name = "categoryService")
	private CategoryService categoryService;

	@Resource(name = "categoryDao")
	private CategoryDao categoryDao;

	@Resource(name = "productSKUService")
	private ProductSKUService productSkuService;

	/**
	 * @param baseDao
	 */
	@Autowired
	public FavoriteServiceImpl(BaseDao<Favorite> baseDao) {
		super(baseDao);
		this.favoriteDao = (FavoriteDao) baseDao;
	}

	/**
	 * 添加收藏
	 * @param purchaserUserId
	 * @param productId
	 * @param productCategoryId
	 * @return
	 */
	@Transactional
	@Override
	public Map<String, Object> addFavorite(String purchaserUserId, String productId, String productCategoryId) {

		/**
		 * 如果能在Favorite中通过purchaserUserId、productId、productCategoryId找到一条有效的数据，
		 * 就提示已经收藏过该商品
		 */

		Map<String, Object> map = new HashMap<String, Object>();
		List<Favorite> list = favoriteDao.findFavoriteListByParam(purchaserUserId, productId, productCategoryId);
		Integer status = CommonStatusEnum.有效.getValue();
		if (list != null && list.size() > 0) {
			map.put("success", false);
			map.put("msg", "该商品已经被收藏过了");
			return map;
		}

		PurchaserUser purchaserUser = purchaserUserDao.getOne(purchaserUserId);
		Favorite favorite = new Favorite();
		Date now = new Date();
		favorite.setCreateTime(now);
		favorite.setModifiedTime(now);
		Category category = categoryDao.getOne(productCategoryId), parentCategory = null;
		if (category != null) {
			parentCategory = category.getParentCategory();
			favorite.setParentProductCategoryId(parentCategory.getId());
		}
		favorite.setProductCategoryId(productCategoryId);
		favorite.setProductSkuId(productId);
		favorite.setPurchaserUser(purchaserUser);
		favorite.setStatus(status);
		favoriteDao.save(favorite);
		map.put("success", true);
		return map;
	}

	/**
	 * 分页
	 * @param curPage
	 * @param pageSize
	 * @param purchaserUserId
	 * @param secondLevelCategoryId
	 * @return
	 */
	@Override
	public PageForShow<ProductSKUBussinessVO> getFavoritePageByUserId(Integer curPage, Integer pageSize, String purchaserUserId, String secondLevelCategoryId) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (purchaserUserId != null) {
			params.put("AND_EQ_purchaserUser.id", purchaserUserId);
		}
		if (secondLevelCategoryId != null) {
			List<String> cateIds = categoryService.getThirdLevelCate(secondLevelCategoryId);
			params.put("AND_IN_productCategoryId", cateIds);
		}
		params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());

		Page<Favorite> page = findByParams(Favorite.class, params, curPage, pageSize, "DESC", "modifiedTime");

		PageForShow<ProductSKUBussinessVO> p = PageForShow.newInstanceFromSpringPage(genreateList(page), curPage, page.getTotalElements());
		return p;
	}

	// 获取商品图片
	private List<ProductSKUBussinessVO> genreateList(Page<Favorite> page) {
		List<Favorite> list = page.getContent();
		List<ProductSKUBussinessVO> productList = new ArrayList<ProductSKUBussinessVO>();
		int length, i = 0;
		if (list != null && (length = list.size()) > 0) {
			for (; i < length; i++) {
				Favorite favorite = list.get(i);
				String productId = favorite.getProductSkuId(), productCategoryId = favorite.getProductCategoryId();
				ProductSKUBussinessVO productVo = productSkuService.getProductDetailShop(productCategoryId, productId);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("favoriteId", favorite.getId());
				productVo.setCustomData(map);
				productList.add(productVo);
			}
		}
		return productList;
	}

	/**
	 * 获取二级商品品类数量
	 * @param purchaserUserId
	 * @param status
	 * @return
	 */
	@Override
	public List<Map<String, Object>> countProductBySecondCategoryLevel(String purchaserUserId, Integer status) {
		List<Object[]> list = favoriteDao.countProductBySecondCategoryLevel(purchaserUserId, status);
		List<Map<String, Object>> categoryList = new ArrayList<Map<String, Object>>();
		int length, i = 0;
		if (list != null && (length = list.size()) > 0) {
			for (; i < length; i++) {
				Object[] objs = list.get(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("categoryId", objs[0]);
				map.put("categoryName", objs[1]);
				map.put("productCount", objs[2]);
				categoryList.add(map);
			}
		}
		return categoryList;
	}

}