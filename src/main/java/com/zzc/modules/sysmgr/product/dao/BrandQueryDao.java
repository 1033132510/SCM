package com.zzc.modules.sysmgr.product.dao;

/**
 * 
 * 
 * @author apple
 *
 */
public interface BrandQueryDao {

	/**
	 * 根據品牌的中文或者英文名稱查找重複的品牌
	 * @param orgId
	 * @param brandZHName
	 * @param brandENName
	 * @param brandId
	 * @return
	 */
	Long findRepeatBrandByBrandName(String orgId, String brandZHName, String brandENName, String brandId);

}
