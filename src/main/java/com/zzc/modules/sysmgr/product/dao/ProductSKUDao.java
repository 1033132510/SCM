package com.zzc.modules.sysmgr.product.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zzc.core.dao.BaseDao;
import com.zzc.modules.sysmgr.product.entity.ProductSKU;
/**
 * 
 * @author famous
 *
 */
public interface ProductSKUDao extends BaseDao<ProductSKU> {

	@Query("select count(*) from ProductSKU p, ProductCategory pc, CategoryCharge cc where p.id = pc.productSKU.id"
			+ " and pc.category.id = cc.category.id and cc.employee.id = :employeeId and p.id = :productSKUId"
			+ " and pc.level = 2")
	public long countByProductSKUId(@Param("productSKUId")String productSKUId, @Param("employeeId")String employeeId);
	
	@Modifying
	@Query("update ProductSKU p set p.status = :status where p.brand.id = :brandId")
	public void updateProductByBrandId(@Param("brandId")String brandId, @Param("status")Integer status);
	
}