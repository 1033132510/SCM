package com.zzc.modules.sysmgr.product.web.vo;

import java.util.List;

public class ProductSearchVO {

	// 类别
	private String cateId;

	// level
	private Integer level;

	// 商品名称
	private String productName;

	// 品牌ID
	private String brandId;

	// 品牌名称
	private String brandName;

	private Integer pageSize;

	private Integer curPage;

	List<ItemSearchVO> itemSearchVOs;

	public String getCateId() {
		return cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public List<ItemSearchVO> getItemSearchVOs() {
		return itemSearchVOs;
	}

	public void setItemSearchVOs(List<ItemSearchVO> itemSearchVOs) {
		this.itemSearchVOs = itemSearchVOs;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

}
