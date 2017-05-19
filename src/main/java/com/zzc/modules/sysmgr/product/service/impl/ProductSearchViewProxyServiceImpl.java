package com.zzc.modules.sysmgr.product.service.impl;

import com.zzc.core.dao.BaseDao;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.sysmgr.product.dao.ProductSearchViewProxyDao;
import com.zzc.modules.sysmgr.product.dao.ProductSearchViewQueryDao;
import com.zzc.modules.sysmgr.product.entity.ProductSearchViewProxy;
import com.zzc.modules.sysmgr.product.service.ProductSearchViewProxyService;
import com.zzc.modules.sysmgr.product.web.vo.ProductSearchVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author apple
 *
 */
@Service("productSearchViewProxyService")
public class ProductSearchViewProxyServiceImpl extends BaseCrudServiceImpl<ProductSearchViewProxy> implements ProductSearchViewProxyService {

	@SuppressWarnings("unused")
	private ProductSearchViewProxyDao productSearchViewDaoProxy;

	@Autowired
	private ProductSearchViewQueryDao productSearchViewDao;

	@Autowired
	public ProductSearchViewProxyServiceImpl(BaseDao<ProductSearchViewProxy> productSearchViewDaoProxy) {
		super(productSearchViewDaoProxy);
		this.productSearchViewDaoProxy = (ProductSearchViewProxyDao) productSearchViewDaoProxy;
	}

	/**
	 * 产品名称得到级别
	 */
	@Override
	public List<ProductSearchViewProxy> getCateByProductName(String productName, Integer level) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (level != null) {
			params.put("AND_EQ_level", level);
		}
		if (StringUtils.isNoneBlank(productName)) {
			params.put("AND_LIKE_productName", productName);
		}
		if (StringUtils.isNoneBlank(productName)) {
            params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
        }

		params.put("AND_GROUP_brandCategoryView.cateId", "cateId");
		return findAll(params, ProductSearchViewProxy.class);
	}

	/**
	 * searchBrandInfoByCateAndProductName
	 * 
	 * 通过类别和产品名称 查询 品牌
	 */
	@Override
	public List<ProductSearchViewProxy> searchBrandInfoByCateAndProductName(String productName, String cateId,Integer level) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("AND_EQ_status",CommonStatusEnum.有效.getValue());
//		if (productName != null) {
//			params.put("AND_LIKE_productName", productName);
//		}
//		if (cateId != null) {
//			params.put("AND_EQ_brandCategoryView.cateId", cateId);
//		}
//		params.put("AND_GROUP_brandZHName", "brandZHName");
//		System.err.println(JsonUtils.toJson(params));
//		return findAll(params, ProductSearchViewProxy.class);
		return productSearchViewDao.searchBrandInfoByCateAndProductName(productName,cateId,level);
	}

	@Override
	public List<ProductSearchViewProxy> searchEx(final ProductSearchVO v) {
		return productSearchViewDao.superSearchView(v);
	}

	@Override
	public Long searchExByCount(ProductSearchVO v) {
		return productSearchViewDao.superSearchViewCount(v);
	}
	//判断某个类别下是否有商品
	@Override
	public Long getViewCountByCateId(String cateId) {
		return productSearchViewDao.getViewCountByCateId(cateId);
	}
	
	//判断某个类别下是否有商品
		@Override
		public Long getViewCountByCateId(String cateId,Integer level) {
			return productSearchViewDao.getViewCountByCateId(cateId,level);
		}

	/**
	 * 根据品牌名称查品牌信息
	 * @param brandName
	 * @return
	 */
	@Override
	public List<ProductSearchViewProxy> searchBrandInfoByBrandName(String brandName) {
		return productSearchViewDao.searchBrandInfoByBrandName(brandName);
	}

	@Override
	public List<ProductSearchViewProxy> getCateByBrandName(String brandName, Integer level) {
		// TODO Auto-generated method stub
		return productSearchViewDao.getCateByBrandName(brandName,level);
	}

}
