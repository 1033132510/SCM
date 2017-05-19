/**
 * 
 */
package com.zzc.modules.sysmgr.product.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.zzc.core.exceptions.BizException;
import com.zzc.modules.sysmgr.product.dao.ProductCategoryMainViewProxyDao;
import com.zzc.modules.sysmgr.product.entity.Category;
import com.zzc.modules.sysmgr.product.entity.ProductCategoryMainViewProxy;
import com.zzc.modules.sysmgr.product.web.vo.CategoryChargeVO;

/**
 * @author zhangyong
 *
 */
@Repository("productCategoryMainViewProxyDao")
public class ProductCategoryMainViewProxyDaoImpl implements	ProductCategoryMainViewProxyDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private final static String ERROR_MSG_LEVEL = "类别对应的级别有误";

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductCategoryMainViewProxy> findListByParamsForManager(
			Map<String, Object> map, Category category, Integer status, List<CategoryChargeVO> categoryChargeVOList,
			Integer curPage, Integer pageSize) {
		
		StringBuilder hql = new StringBuilder("select p from ProductCategoryMainViewProxy p where 1 = 1");
		
		boolean statusIsExist = status != null;
		if(statusIsExist) {
			hql.append(" and p.status = :status");
		}
		
		String cateId = null;
		boolean categoryIsExist = category != null;
		if(categoryIsExist) {
			Integer level = category.getLevel();
			cateId = category.getId();
			if (level == 1) {
				hql.append(" and p.productCategoryMainView.cateIdGrand = :cateId");
			} else if (level == 2) {
				hql.append(" and p.productCategoryMainView.cateIdParent = :cateId");
			} else if (level == 3) {
				hql.append(" and p.productCategoryMainView.cateId = :cateId");
			} else {
				throw new BizException(ERROR_MSG_LEVEL);
			}
		}
		
		// ---------------------------------品类权限 begin-------------------------------------
		List<String> categoryIds = getCategoryIds(categoryChargeVOList);
		hql.append(" and p.productCategoryMainView.cateIdParent in :categoryIds");
		// ---------------------------------品类权限 end-------------------------------------
		
		Object brandNameObj = map.get("brandName");
		boolean brandNameIsExist = brandNameObj != null;
		if (brandNameIsExist) {
			hql.append(" and (p.productCategoryMainView.brandZHName like :brandName or p.productCategoryMainView.brandENName like :brandName)");
		}
		
		Object productNameObj = map.get("productName");
		boolean productNameIsExist = productNameObj != null;
		if (productNameIsExist) {
			hql.append(" and (p.productCategoryMainView.productName like :productName or p.productCategoryMainView.productCode = :productCode)");
		}
		
		hql.append(" order by modified_time desc");
		
		Query query = entityManager.createQuery(hql.toString());
		if(statusIsExist) {
			query.setParameter("status", status);
		}
		if(categoryIsExist) {
			query.setParameter("cateId", cateId);
		}
		query.setParameter("categoryIds", categoryIds);
		if(brandNameIsExist) {
			query.setParameter("brandName", "%" + brandNameObj + "%");
		}
		if(productNameIsExist) {
			query.setParameter("productName", "%" + productNameObj + "%").setParameter("productCode", productNameObj);
		}
		
		query.setMaxResults(pageSize);
		query.setFirstResult((curPage - 1) * pageSize);
		
		return query.getResultList();
	}
	
	@Override
	public long countByParamsForManager(Map<String, Object> map, Category category,
			Integer status, List<CategoryChargeVO> categoryChargeVOList) {
		
		StringBuilder hql = new StringBuilder("select count(*) from ProductCategoryMainViewProxy p where 1 = 1");
		
		boolean statusIsExist = status != null;
		if(statusIsExist) {
			hql.append(" and p.status = :status");
		}
		
		String cateId = null;
		boolean categoryIsExist = category != null;
		if(categoryIsExist) {
			Integer level = category.getLevel();
			cateId = category.getId();
			if (level == 1) {
				hql.append(" and p.productCategoryMainView.cateIdGrand = :cateId");
			} else if (level == 2) {
				hql.append(" and p.productCategoryMainView.cateIdParent = :cateId");
			} else if (level == 3) {
				hql.append(" and p.productCategoryMainView.cateId = :cateId");
			} else {
				throw new BizException(ERROR_MSG_LEVEL);
			}
		}
		
		// ---------------------------------品类权限 begin-------------------------------------
		List<String> categoryIds = getCategoryIds(categoryChargeVOList);
		hql.append(" and p.productCategoryMainView.cateIdParent in :categoryIds");
		// ---------------------------------品类权限 end-------------------------------------
		
		Object brandNameObj = map.get("brandName");
		boolean brandNameIsExist = brandNameObj != null;
		if (brandNameIsExist) {
			hql.append(" and (p.productCategoryMainView.brandZHName like :brandName or p.productCategoryMainView.brandENName like :brandName)");
		}
		
		Object productNameObj = map.get("productName");
		boolean productNameIsExist = productNameObj != null;
		if (productNameIsExist) {
			hql.append(" and (p.productCategoryMainView.productName like :productName or p.productCategoryMainView.productCode = :productCode)");
		}
		
		Query query = entityManager.createQuery(hql.toString());
		if(statusIsExist) {
			query.setParameter("status", status);
		}
		if(categoryIsExist) {
			query.setParameter("cateId", cateId);
		}
		query.setParameter("categoryIds", categoryIds);
		if(brandNameIsExist) {
			query.setParameter("brandName", "%" + brandNameObj + "%");
		}
		if(productNameIsExist) {
			query.setParameter("productName", "%" + productNameObj + "%").setParameter("productCode", productNameObj);
		}
		
		return (long) query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> findListByParamsForSupplier(Map<String, Object> map,
			Integer status, Integer curPage, Integer pageSize) {
		
		StringBuilder hql = new StringBuilder("select p.product_id productId, p.product_code productCode, p.product_name productName,");
		hql.append(" pp.actuall_price actuallPrice,");
		hql.append(" p.brand_zh_name brandZHName, p.modified_time modifiedTime, p.cate_id cateId, p.cate_id_parent parentId");
		hql.append(" from ES_VIEW_PRODUCT_CATE_ORG p");
		hql.append(" left outer join ES_PRODUCT_PRICE pp on p.product_id = pp.product_sku_id and pp.es_price_kind_id = :priceKindId and pp.status = 1");
		hql.append(" where 1 = 1");
		
		boolean statusIsExist = status != null;
		if(statusIsExist) {
			hql.append(" and p.status = :status");
		}
		
		Object cateId = map.get("cateId");
		boolean cateIdExist = cateId != null;
		if(cateIdExist) {
			hql.append(" and p.cate_id_parent = :cateId");
		}
		
		List<String> brandNames = (List<String>) map.get("brandNames");
		List<String> cateIds = (List<String>) map.get("cateIds");
		
		// ---------------------------------品牌、品类权限 begin-------------------------------------
		hql.append(" and p.brand_zh_name in :brandNames");
		hql.append(" and p.cate_id_parent in :cateIds");
		// ---------------------------------品牌、品类权限 end-------------------------------------
		
		Object brandNameObj = map.get("brandName");
		boolean brandNameIsExist = brandNameObj != null;
		if (brandNameIsExist) {
			hql.append(" and (p.brand_zh_name = :brandName or p.brand_en_name = :brandName)");
		}
		
		Object productNameObj = map.get("productName");
		boolean productNameIsExist = productNameObj != null;
		if (productNameIsExist) {
			hql.append(" and (p.product_name like :productName or p.product_code = :productCode)");
		}
		
		hql.append(" order by p.modified_time desc");
		
		Query query = entityManager.createNativeQuery(hql.toString());
		// 推荐价格
		query.setParameter("priceKindId", 6);
		query.setParameter("brandNames", brandNames);
		query.setParameter("cateIds", cateIds);
		if(statusIsExist) {
			query.setParameter("status", status);
		}
		if(cateIdExist) {
			query.setParameter("cateId", cateId);
		}
		if(brandNameIsExist) {
			query.setParameter("brandName", brandNameObj);
		}
		if(productNameIsExist) {
			query.setParameter("productName", "%" + productNameObj + "%").setParameter("productCode", productNameObj);
		}
		
		query.setMaxResults(pageSize);
		query.setFirstResult((curPage - 1) * pageSize);
		
		List<Object[]> list = query.getResultList();
		for(Object[] objs : list) {
			Object price = objs[3];
			if(price == null) {
				Object productId = objs[0];
				String sql = "select pp.actuall_price from ES_PRODUCT_PRICE pp where pp.es_price_kind_id = 1 and pp.product_sku_id = :productId";
				Query sqlQuery = entityManager.createNativeQuery(sql);
				sqlQuery.setParameter("productId", productId);
				List<String> priceList = sqlQuery.getResultList();
				if(priceList.size() > 0) {
					objs[3] = priceList.get(0);
				}
			}
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public long countByParamsForSupplier(Map<String, Object> map, Integer status) {
		StringBuilder hql = new StringBuilder("select count(*)");
		hql.append(" from ES_VIEW_PRODUCT_CATE_ORG p");
		hql.append(" left outer join ES_PRODUCT_PRICE pp on p.product_id = pp.product_sku_id and pp.es_price_kind_id = :priceKindId and pp.status = 1");
		hql.append(" where 1 = 1");
		
		boolean statusIsExist = status != null;
		if(statusIsExist) {
			hql.append(" and p.status = :status");
		}
		
		Object cateId = map.get("cateId");
		boolean cateIdExist = cateId != null;
		if(cateIdExist) {
			hql.append(" and p.cate_id_parent = :cateId");
		}
		
		List<String> brandNames = (List<String>) map.get("brandNames");
		List<String> cateIds = (List<String>) map.get("cateIds");
		
		// ---------------------------------品牌、品类权限 begin-------------------------------------
		hql.append(" and p.brand_zh_name in :brandNames");
		hql.append(" and p.cate_id_parent in :cateIds");
		// ---------------------------------品牌、品类权限 end-------------------------------------
		
		Object brandNameObj = map.get("brandName");
		boolean brandNameIsExist = brandNameObj != null;
		if (brandNameIsExist) {
			hql.append(" and (p.brand_zh_name = :brandName or p.brand_en_name = :brandName)");
		}
		
		Object productNameObj = map.get("productName");
		boolean productNameIsExist = productNameObj != null;
		if (productNameIsExist) {
			hql.append(" and (p.product_name like :productName or p.product_code = :productCode)");
		}
		
		hql.append(" order by p.modified_time desc");
		
		Query query = entityManager.createNativeQuery(hql.toString());
		// 推荐价格
		query.setParameter("priceKindId", 6);
		query.setParameter("brandNames", brandNames);
		query.setParameter("cateIds", cateIds);
		if(statusIsExist) {
			query.setParameter("status", status);
		}
		if(cateIdExist) {
			query.setParameter("cateId", cateId);
		}
		if(brandNameIsExist) {
			query.setParameter("brandName", brandNameObj);
		}
		if(productNameIsExist) {
			query.setParameter("productName", "%" + productNameObj + "%").setParameter("productCode", productNameObj);
		}
		
		return ((BigInteger) query.getSingleResult()).longValue();
	}
	
	private List<String> getCategoryIds(List<CategoryChargeVO> categoryChargeVOList) {
		List<String> categoryIds = new ArrayList<String>();
		for(CategoryChargeVO categoryChargeVO : categoryChargeVOList) {
			categoryIds.add(categoryChargeVO.getSecondLevelCategoryId());
		}
		return categoryIds;
	}
	
}