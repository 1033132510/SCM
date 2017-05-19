package com.zzc.modules.sysmgr.product.service;

import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.sysmgr.product.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 * @author apple
 *
 */
public interface BrandService extends BaseCrudService<Brand> {

	/**
	 * 根据组织和产品
	 * @param orgId
	 * @param status
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public Page<Brand> findBrandsBySupplierOrgId(String orgId, Integer status, Integer pageSize, Integer currentPage);
	
	/**
	 * @param orgId
	 * @param status
	 *
	 * @return
	 */
	public List<Brand> findBrandsBySupplierOrgId(String orgId, Integer status);

	/**
	 * 更新或者保存品牌
	 * @param brand
	 * @param imageIds
	 * @param deleteImageIds
	 * @return
	 */
	@Transactional
	public Brand createOrUpdateBrand(Brand brand, String[] imageIds, String[] deleteImageIds);
	
	/**
	 * 更新品牌
	 * @param brandId
	 * @param status
	 * @return
	 */
	public boolean updateBrand(String brandId, Integer status);

	/**
	 * 查询重复的中文或者因为品牌 更新的时候可以和自身重复
	 * @param orgId
	 * @param brandZHName
	 * @param brandENName
	 * @param brandId
	 * @return
	 */
	public Long findRepeatBrandByBrandName(String orgId, String brandZHName, String brandENName, String brandId);

}