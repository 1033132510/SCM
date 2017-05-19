package com.zzc.modules.sysmgr.product.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zzc.core.dao.BaseDao;
import com.zzc.modules.sysmgr.product.entity.ProductProperties;
import org.springframework.transaction.annotation.Transactional;

public interface ProductPropertiesDao extends BaseDao<ProductProperties> {

	/**
	 * 根据productId失效相关产品属性
	 *
	 * @param productId
	 */
	@Transactional
	@Modifying
	@Query(value = "update ProductProperties p set status=0 where p.productSKUId=:productId and p.status=1")
	void disableProductProperties(@Param("productId") String productId);

}