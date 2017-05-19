package com.zzc.modules.sysmgr.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zzc.core.dao.BaseDao;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.exceptions.BizException;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.sysmgr.file.service.ImageService;
import com.zzc.modules.sysmgr.product.dao.BrandDao;
import com.zzc.modules.sysmgr.product.dao.BrandQueryDao;
import com.zzc.modules.sysmgr.product.dao.ProductSKUDao;
import com.zzc.modules.sysmgr.product.entity.Brand;
import com.zzc.modules.sysmgr.product.service.BrandService;

/**
 * 
 * @author apple
 *
 */
@Service("brandService")
public class BrandServiceImpl extends BaseCrudServiceImpl<Brand> implements BrandService {

	@SuppressWarnings("unused")
	private BrandDao brandDao;

	@Autowired
	@Qualifier("productSearchViewQueryDao")
	private BrandQueryDao brandQueryDao;

	@Autowired
	private ImageService imageService;
	
	@Autowired
	private ProductSKUDao productSKUDao;
	
	private final static String ERROR_MSG_FOR_BRAND_REPEAT = "品牌不能重复";
	

	@Autowired
	public BrandServiceImpl(BaseDao<Brand> brandDao) {
		super(brandDao);
		this.brandDao = (BrandDao) brandDao;
	}

	@Override
	public Page<Brand> findBrandsBySupplierOrgId(String orgId, Integer status, Integer pageSize, Integer currentPage) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (orgId != null) {
			params.put("AND_EQ_supplierOrg.id", orgId);
		}
		if(status != null) {
			params.put("AND_EQ_status", status);
		}
		Page<Brand> page = findByParams(Brand.class, params, currentPage, pageSize, "DESC", "createTime");
		return page;
	}
	
	@Override
	public List<Brand> findBrandsBySupplierOrgId(String orgId, Integer status) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (orgId != null) {
			params.put("AND_EQ_supplierOrg.id", orgId);
		}
		if(status != null) {
			params.put("AND_EQ_status", status);
		}
		return findAll(params, Brand.class);
	}

	@Override
	public Long findRepeatBrandByBrandName(String orgId, String brandZHName, String brandENName, String brandId) {
		return brandQueryDao.findRepeatBrandByBrandName(orgId, brandZHName, brandENName, brandId);
	}

	@Override
	public Brand createOrUpdateBrand(Brand brand, String[] imageIds, String[] deleteImageIds) {
		if (StringUtils.isNoneBlank(brand.getId())) {
			brand = updateBrand(brand, imageIds, deleteImageIds);
		} else {
			brand.setStatus(CommonStatusEnum.有效.getValue());
			brand = createBrand(brand, imageIds, deleteImageIds);
		}
		imageService.buildRelationBetweenImageAndEntity(imageIds, brand.getId());
		imageService.destoryRelationBetweenImageAndEntity(deleteImageIds);
		return brand;
	}
	
	@Transactional
	@Override
	public boolean updateBrand(String brandId, Integer status) {
		Brand brand = findByPK(brandId);
		brand.setStatus(status);
		update(brand);
		productSKUDao.updateProductByBrandId(brandId, status);
		return true;
	}

	/**
	 * 保存
	 * 
	 * @param brand
	 * @param imageIds
	 * @param deleteImageIds
	 * @return
	 */
	private Brand createBrand(Brand brand, String[] imageIds, String[] deleteImageIds) {
		Long countRepeat = findRepeatBrandByBrandName(null, brand.getBrandZHName(), brand.getBrandENName(), null);
		if (countRepeat > 0) {
			throw new BizException(ERROR_MSG_FOR_BRAND_REPEAT);
		}
		brand.setStatus(CommonStatusEnum.有效.getValue());
		Brand brandDB = create(brand);
		return brandDB;
	}

	/**
	 * 更新
	 * 
	 * @param brand
	 * @param imageIds
	 * @param deleteImageIds
	 * @return
	 */
	private Brand updateBrand(Brand brand, String[] imageIds, String[] deleteImageIds) {
		Long countRepeat = findRepeatBrandByBrandName(null, brand.getBrandZHName(), brand.getBrandENName(), brand.getId());
		if (countRepeat > 0) {
			throw new BizException(ERROR_MSG_FOR_BRAND_REPEAT);
		}
		Brand brandDB = update(brand);
		return brandDB;
	}

}