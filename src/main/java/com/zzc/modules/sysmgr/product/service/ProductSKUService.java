package com.zzc.modules.sysmgr.product.service;

import com.zzc.common.page.PageForShow;
import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.sysmgr.product.entity.ProductPrice;
import com.zzc.modules.sysmgr.product.entity.ProductSKU;
import com.zzc.modules.sysmgr.product.service.vo.ProductPriceActionVO;
import com.zzc.modules.sysmgr.product.service.vo.ProductSKUBussinessVO;
import com.zzc.modules.sysmgr.product.web.vo.ProductSKUModel;
import com.zzc.modules.sysmgr.product.web.vo.ProductSearchVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductSKUService extends BaseCrudService<ProductSKU> {

	/**
	 * 获取商品詳細信息  给后台使用
	 * @param cateId
	 * @param productId
	 * @return
	 */
	ProductSKUBussinessVO getProductDetail(String cateId, String productId);
	
	/**
	 * 获取商品詳細信息  給前端使用
	 * @param cateId
	 * @param productId
	 * @return
	 */
	ProductSKUBussinessVO getProductDetailShop(String cateId, String productId);

/**
 * 查詢公司下面的商品信息
 * @param supplierOrgId
 * @param status
 * @param pageSize
 * @param curPage
 * @return
 */
	PageForShow<ProductSKUBussinessVO> searchBySupplierOrg(String supplierOrgId, Integer status, Integer pageSize, Integer curPage);
/**
 * 通過類別或者產品名稱得到商品
 * @param cateId
 * @param productName
 * @param status
 * @param pageSize
 * @param curPage
 * @return
 */
	PageForShow<ProductSKUBussinessVO> searchByCateOrName(String cateId, String productName, Integer status, Integer pageSize, Integer curPage);
	
	


	/**
	 * 前端主页搜索
	 * @param vo
	 * @return
	 */
	PageForShow<ProductSKUBussinessVO> searchEx(ProductSearchVO vo);
	
	/**
	 * 保存或者修改商品
	 * @param model
	 */

	@Transactional
	void createOrUpdateProductSKUModel(ProductSKUModel model);

	/**
	 * 修改商品状态
	 * 
	 * @param productId
	 * @param status
	 * @return
	 */
	public boolean updateProductStatus(String productId, Integer status);

	/*
	 * 修改商品价格，并记录修改记录
	 * 
	 * @param productSkuId
	 *            产品Id
	 * @param productCategoryId
	 *            产品分类Id
	 * @param productPrice
	 *            修改的价格列表
	 * @param productPriceActionVO
	 *            修改记录对象
	 * @param feeRemark
	 *            费用备注
	 * @param feeLogistics
	 *            物流备注
	 */
	void updatePriceAndSaveOperation(String productSkuId,
	                                 String productCategoryId, List<ProductPrice> productPrice,
	                                 ProductPriceActionVO productPriceActionVO, String feeRemark,
	                                 String feeLogistics, String unit, Integer hasTax, Integer hasTransportation);
	
	/**
	 * 校验该员工是否有权限查看该商品
	 * @param employeeId
	 * @param productSKUId
	 * @return
	 */
	public boolean checkRightByProductSKUId(String employeeId, String productSKUId);

	public String getProductCode(String productCategoryId, String supplierOrgId);
	
	public boolean addBatch1000Products();

}