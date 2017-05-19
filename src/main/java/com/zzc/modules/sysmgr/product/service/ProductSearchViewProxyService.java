package com.zzc.modules.sysmgr.product.service;

import java.util.List;

import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.sysmgr.product.entity.ProductSearchViewProxy;
import com.zzc.modules.sysmgr.product.web.vo.ProductSearchVO;

public interface ProductSearchViewProxyService extends BaseCrudService<ProductSearchViewProxy> {

	
	List<ProductSearchViewProxy> getCateByProductName(String productName, Integer level);

	List<ProductSearchViewProxy> getCateByBrandName(String brandName, Integer level);
	
	List<ProductSearchViewProxy> searchBrandInfoByCateAndProductName(String productName, String cateId,Integer level) ;

	List<ProductSearchViewProxy> searchEx(ProductSearchVO v);

	Long searchExByCount(ProductSearchVO v);

	Long getViewCountByCateId(String cateId);
	
	Long getViewCountByCateId(String cateId,Integer level);

	List<ProductSearchViewProxy> searchBrandInfoByBrandName(String brandName);
	

}
