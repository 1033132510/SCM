package com.zzc.modules.supply.service;

import java.math.BigDecimal;
import java.util.Map;

import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.supply.entity.SysProductSKU;
import com.zzc.modules.supply.vo.GroundingProductVO;
import com.zzc.modules.sysmgr.product.entity.ProductSKU;
import com.zzc.modules.sysmgr.product.service.vo.ProductSKUBussinessVO;

/**
 * Created by chenjiahai on 16/1/19.
 */
public interface SupplyProductSKUService extends BaseCrudService<SysProductSKU> {

	Map<String, Object> createOrUpdateProductSKUModel(String productDetail);

	public Map<String, Object> updateProductSKUModelForSupplier(String groundingProduct);

	ProductSKUBussinessVO getProductDetailByProductId(String id);

	ProductSKUBussinessVO getProductDetail(String id);

	void updateProductAndCreateAuditRecord(String productDetailAndComment);


	public Map<String, Object> disableProduct(ProductSKU productSKUId, String comment, BigDecimal costPrice, BigDecimal recommendedPrice, BigDecimal standardPrice, String cateId, Integer hasTax, Integer hasTransportation);

	public void deleteProductAndAuditBillAndRecord(String auditBillId);

	/**
	 * 通过productSKUId查找到该商品最近一次审批通过的记录信息
	 *
	 * @param productSKUId
	 * @return
	 */
	public SysProductSKU getLatestSysProductByProductSKUId(String productSKUId);

	public GroundingProductVO buildGroundingProductVO(ProductSKU productSKU, String comment, String thirdLevelCateId, String supplyProductSKUId);

}
