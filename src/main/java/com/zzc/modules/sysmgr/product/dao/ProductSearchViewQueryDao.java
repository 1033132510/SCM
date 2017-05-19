package com.zzc.modules.sysmgr.product.dao;

import java.util.List;

import com.zzc.modules.sysmgr.product.entity.ProductSearchViewProxy;
import com.zzc.modules.sysmgr.product.web.vo.ProductSearchVO;

/**
 * 11
 * @author apple
 *
 */
public interface ProductSearchViewQueryDao {

	
	public Long getViewCountByCateId(String cateId);
	
	public Long getViewCountByCateId(String cateId,Integer level);

	public List<ProductSearchViewProxy> searchBrandInfoByBrandName(String brandName);

	public List<ProductSearchViewProxy> getCateByBrandName(String brandName, Integer level);

	public List<ProductSearchViewProxy> superSearchView(ProductSearchVO v);

	public Long superSearchViewCount(ProductSearchVO v);
	
	public List<ProductSearchViewProxy> searchBrandInfoByCateAndProductName(String productName, String cateId,Integer level);

}
