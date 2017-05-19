package com.zzc.modules.sysmgr.product.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.zzc.modules.sysmgr.product.dao.BrandQueryDao;

/**
 */
@Repository("productSearchViewQueryDao")
public class ProductSearchViewQueryDaoImpl implements BrandQueryDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Long findRepeatBrandByBrandName(String orgId, String brandZHName, String brandENName, String brandId) {
		StringBuilder hql = new StringBuilder("select count(1)  from Brand e where 1=1");
		if (StringUtils.isNotEmpty(orgId)) {
			hql.append(" and orgId=:orgId");
		}
		if (StringUtils.isNotBlank(brandId)) {
			hql.append(" and e.id != :id");
		}
		hql.append(" and (");
		boolean ZHNameExits = false;
		if (StringUtils.isNotBlank(brandZHName)) {
			ZHNameExits = true;
			hql.append("  brandZHName=:brandZHName");
		}
		if (StringUtils.isNotBlank(brandENName)) {
			if (ZHNameExits) {
				hql.append(" or ");
			}
			hql.append("brandENName=:brandENName");
		}
		hql.append(")");
		Query query = entityManager.createQuery(hql.toString());
		if (StringUtils.isNotEmpty(orgId)) {
			query.setParameter("orgId", orgId);
		}
		if (StringUtils.isNotBlank(brandId)) {
			query.setParameter("id", brandId);
		}
		if (StringUtils.isNotBlank(brandZHName)) {
			query.setParameter("brandZHName", brandZHName);
		}
		if (StringUtils.isNotBlank(brandENName)) {
			query.setParameter("brandENName", brandENName);
		}
		return (Long) query.getSingleResult();
	}

}
