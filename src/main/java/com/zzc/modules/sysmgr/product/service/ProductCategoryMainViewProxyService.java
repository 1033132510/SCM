package com.zzc.modules.sysmgr.product.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.zzc.common.page.PageForShow;
import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.sysmgr.product.entity.ProductCategoryMainViewProxy;
import com.zzc.modules.sysmgr.product.web.vo.CategoryVO;
import com.zzc.modules.supply.vo.BrandVO;

public interface ProductCategoryMainViewProxyService extends BaseCrudService<ProductCategoryMainViewProxy> {

	/**
	 * 根据产品名得到类别信息
	 * 
	 * @param productName
	 * @param level
	 * @return
	 */
	Page<ProductCategoryMainViewProxy> findByProductNameAndCateId(String productName, String cateId, Integer status, Integer pageSize, Integer curPage);

	/**
	 * 获取组织下的商品信息
	 * 
	 * @param supplierOrgId
	 * @param status
	 * @param pageSize
	 * @param curPage
	 * @return
	 */
	Page<ProductCategoryMainViewProxy> findBySupplierOrgId(String supplierOrgId, Integer status, Integer pageSize, Integer curPage);
	

	/**
	 * 参考findByProductNameAndCateId
	 * @param employeeId
	 * @param productName
	 * @param cateId
	 * @param status
	 * @param pageSize
	 * @param curPage
	 * @return
	 */
	PageForShow<ProductCategoryMainViewProxy> searchForProductManager(String employeeId, 
			String productName, String brandName, String cateId, Integer status, Integer pageSize, Integer curPage);
	
	/**
	 * 自己所在公司的品牌下查找商品
	 * @param employeeId
	 * @param productName
	 * @param cateId
	 * @param status
	 * @param pageSize
	 * @param curPage
	 * @return
	 */
	PageForShow<Map<String, Object>> searchForSupplyProductManager(String employeeId, 
			String productName, String brandName, String cateId, Integer status, Integer pageSize, Integer curPage);
	
	public List<CategoryVO> getCategoryList(Integer status);
	
	public List<BrandVO> getBrandList(Integer status);

}