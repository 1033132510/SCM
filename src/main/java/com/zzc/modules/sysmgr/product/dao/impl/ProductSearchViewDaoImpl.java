package com.zzc.modules.sysmgr.product.dao.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.exceptions.BizException;
import com.zzc.modules.sysmgr.product.dao.CategoryDao;
import com.zzc.modules.sysmgr.product.dao.ProductSearchViewQueryDao;
import com.zzc.modules.sysmgr.product.entity.Brand;
import com.zzc.modules.sysmgr.product.entity.Category;
import com.zzc.modules.sysmgr.product.entity.ProductSearchViewProxy;
import com.zzc.modules.sysmgr.product.web.vo.ItemSearchVO;
import com.zzc.modules.sysmgr.product.web.vo.ProductSearchVO;

/**
 * 11 商品检索自定义ＤＡＯ 特殊
 */
@Repository("productSearchViewDao")
public class ProductSearchViewDaoImpl implements ProductSearchViewQueryDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private CategoryDao categoryDao;
//1
	// 判断某个类别下是否有商品
	@Override
	public Long getViewCountByCateId(String cateId) {
		StringBuilder hql = new StringBuilder("select count(1)  from ProductSearchViewProxy e where 1=1");
		if (StringUtils.isNotBlank(cateId)) {
			Category category = categoryDao.findOne(cateId);
			Integer level = category.getLevel();
			if (level == 1) {
				hql.append(" and e.brandCategoryView.cateIdGrand = :cateId");
			} else if (level == 2) {
				hql.append(" and e.brandCategoryView.cateIdParent = :cateId");
			} else if (level == 3) {
				hql.append(" and e.brandCategoryView.cateId = :cateId");
			}
		}
		Query query = entityManager.createQuery(hql.toString());
		if (StringUtils.isNotBlank(cateId)) {
			query.setParameter("cateId", cateId);
		}
		return (Long) query.getSingleResult();
	}

	@Override
	public Long getViewCountByCateId(String cateId, Integer level) {
		StringBuilder hql = new StringBuilder("select count(1)  from ProductSearchViewProxy e where 1=1");
		if (StringUtils.isNotBlank(cateId)) {
			if (level == 1) {
				hql.append(" and e.brandCategoryView.cateIdGrand = :cateId");
			} else if (level == 2) {
				hql.append(" and e.brandCategoryView.cateIdParent = :cateId");
			} else if (level == 3) {
				hql.append(" and e.brandCategoryView.cateId = :cateId");
			}
		}
		Query query = entityManager.createQuery(hql.toString());
		if (StringUtils.isNotBlank(cateId)) {
			query.setParameter("cateId", cateId);
		}
		return (Long) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductSearchViewProxy> superSearchView(ProductSearchVO v) {
		StringBuilder sql = new StringBuilder("select * from ES_VIEW_PRODUCT_SEARCH e where e.level=3");
		buildWhereSql(v, sql);
		sql.append(" group by e.product_id");
		Query query = entityManager.createNativeQuery(sql.toString(), ProductSearchViewProxy.class);
		setParameterValues(v, query);
		Integer pageNum = v.getCurPage() - 1;
		Integer pageSize = v.getPageSize();
		query.setFirstResult(pageNum * pageSize);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	@Override
	public Long superSearchViewCount(ProductSearchVO v) {
		StringBuilder sql = new StringBuilder("select count(DISTINCT e.product_id)  from ES_VIEW_PRODUCT_SEARCH e where e.level=3");
		buildWhereSql(v, sql);
		Query query = entityManager.createNativeQuery(sql.toString());
		setParameterValues(v, query);
		return ((BigInteger) query.getSingleResult()).longValue();
	}

	private void buildWhereSql(ProductSearchVO v, StringBuilder hql) {
		hql.append(" and e.status=(:status)");
		if (StringUtils.isNotEmpty(v.getProductName())) {
			hql.append(" and e.product_name like ").append("(:productName)");
		}
		if (StringUtils.isNotEmpty(v.getBrandId())) {
			hql.append(" and e.brand_id = (:brandId)");
		}
		if (StringUtils.isNotEmpty(v.getBrandName())) {
			hql.append(" and ( e.brand_zh_name like ").append("(:brandZHName)");
			hql.append(" or e.brand_en_name like ").append("(:brandENName))");
		}
		if (StringUtils.isNotEmpty(v.getCateId()) && v.getLevel() != null) {
			Integer level = v.getLevel();
			if (level == 1) {
				hql.append(" and e.cate_id_grand = (:cateId)");
			} else if (level == 2) {
				hql.append(" and e.cate_id_parent = (:cateId)");
			} else if (level == 3) {
				hql.append(" and e.cate_id = (:cateId)");
			}

		}

		if (v.getItemSearchVOs() != null && v.getItemSearchVOs().size() > 0) {
			List<ItemSearchVO> list = v.getItemSearchVOs();
			String viewAlias = null;
			for (int i = 0, len = list.size(); i < len; i++) {
				if (i == 0) {
//					hql.append(" and e.item_code =(:itemCode").append(i).append(") and e.value=(:value").append(i).append(") ");
					hql.append(" and e.item_code =(:itemCode").append(i).append(") and FIND_IN_SET((:value").append(i).append("), (select GROUP_CONCAT(z.value) from ES_VIEW_PRODUCT_SEARCH z where z.item_code  = (:itemCode").append(i).append(") and z.product_id = e.product_id))");
				} else {
					viewAlias = "e" + i;
					hql.append(" and EXISTS (select *").append(" from ES_VIEW_PRODUCT_SEARCH ").append(viewAlias).append(" where ").append(viewAlias).append(".level=3 and ").append(viewAlias).append(".status=(:status").append(i).append(") and ").append(viewAlias).append(".item_code=(:itemCode")
							.append(i).append(") and FIND_IN_SET((:value").append(i).append("), (select GROUP_CONCAT(").append(viewAlias).append("_").append(".value) from ES_VIEW_PRODUCT_SEARCH ").append(viewAlias).append("_").append(" where ").append(viewAlias).append("_").append(".item_code  = (:itemCode").append(i).append(") and ").append(viewAlias).append("_").append(".product_id = e.product_id)) and ").append(viewAlias).append(".product_id = e.product_id) ");
				}
			}
		}
	}

	private void setParameterValues(ProductSearchVO v, Query query) {
		query.setParameter("status", CommonStatusEnum.有效.getValue());
		if (StringUtils.isNotEmpty(v.getCateId()) && v.getLevel() != null) {
			query.setParameter("cateId", v.getCateId());
		}
		if (StringUtils.isNotEmpty(v.getProductName())) {
			query.setParameter("productName", "%" + v.getProductName() + "%");
		}
		if (StringUtils.isNotEmpty(v.getBrandId())) {
			query.setParameter("brandId", v.getBrandId());
		}

		if (StringUtils.isNotEmpty(v.getBrandName())) {
			query.setParameter("brandZHName", "%" + v.getBrandName() + "%");

			query.setParameter("brandENName", "%" + v.getBrandName().toUpperCase() + "%");
		}
		if (v.getItemSearchVOs() != null && v.getItemSearchVOs().size() > 0) {
			List<ItemSearchVO> list = v.getItemSearchVOs();
			for (int i = 0, len = list.size(); i < len; i++) {
				if (i != 0) {
					query.setParameter("status" + i, CommonStatusEnum.有效.getValue());
				}
				query.setParameter("itemCode" + i, list.get(i).getItemCode());
				query.setParameter("value" + i, list.get(i).getValue());
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductSearchViewProxy> searchBrandInfoByBrandName(String brandName) {
		StringBuilder hql = new StringBuilder("from ProductSearchViewProxy b where 1=1");
		if (StringUtils.isNotEmpty(brandName)) {
			hql.append(" and ( brandZHName like ").append(":brandZHName");
			hql.append(" or brandENName like ").append(":brandENName )");
		}
		hql.append(" and status = ").append(CommonStatusEnum.有效.getValue());
		hql.append(" group  by brandId");
		Query query = entityManager.createQuery(hql.toString());
		if (StringUtils.isNoneBlank(brandName)) {
			query.setParameter("brandZHName", "%" + brandName + "%");
			query.setParameter("brandENName", "%" + brandName.toUpperCase() + "%");
		}
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductSearchViewProxy> getCateByBrandName(String brandName, Integer level) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder("from ProductSearchViewProxy b where 1=1");
		hql.append(" and b.status=:status");
		if (null != level) {
			hql.append(" and level =:level");
		}
		if (StringUtils.isNotEmpty(brandName)) {
			hql.append(" and ( brandZHName like ").append(":brandZHName");
			hql.append(" or brandENName like ").append(":brandENName )");
		}
		hql.append(" group  by b.brandCategoryView.cateId");
		Query query = entityManager.createQuery(hql.toString());
		query.setParameter("status", CommonStatusEnum.有效.getValue());
		if (StringUtils.isNoneBlank(brandName)) {
			query.setParameter("brandZHName", "%" + brandName + "%");

			query.setParameter("brandENName", "%" + brandName.toUpperCase() + "%");
		}
		if (null != level) {
			query.setParameter("level", level);
		}
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductSearchViewProxy> searchBrandInfoByCateAndProductName(String productName, String cateId, Integer level) {
		StringBuilder hql = new StringBuilder("from ProductSearchViewProxy e where level=3");
		hql.append(" and e.status =:status");
		if (StringUtils.isNotEmpty(productName)) {
			hql.append(" and productName like ").append(":productName");
		}
		if (StringUtils.isNotBlank(cateId) && level != null) {
			if (level == 1) {
				hql.append(" and e.brandCategoryView.cateIdGrand = :cateId");
			} else if (level == 2) {
				hql.append(" and e.brandCategoryView.cateIdParent = :cateId");
			} else if (level == 3) {
				hql.append(" and e.brandCategoryView.cateId = :cateId");
			}

		}
		hql.append(" group by e.brandId");
		Query query = entityManager.createQuery(hql.toString());
		query.setParameter("status", CommonStatusEnum.有效.getValue());
		if (StringUtils.isNotBlank(cateId) && level != null) {
			query.setParameter("cateId", cateId);
		}
		if (StringUtils.isNotEmpty(productName)) {
			query.setParameter("productName", "%" + productName + "%");
		}
		return query.getResultList();
	}
}
