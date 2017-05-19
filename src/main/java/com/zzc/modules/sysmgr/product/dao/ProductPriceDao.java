package com.zzc.modules.sysmgr.product.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zzc.core.dao.BaseDao;
import com.zzc.modules.sysmgr.product.entity.ProductPrice;

/**
 * 11
 * @author apple
 *
 */
public interface ProductPriceDao extends BaseDao<ProductPrice> {

	@Modifying
	@Query(value = "update ProductPrice p set status=0 where p.productSKUId=:productId and p.status=1")
	void disableProductPrice(@Param("productId") String productId);

}
