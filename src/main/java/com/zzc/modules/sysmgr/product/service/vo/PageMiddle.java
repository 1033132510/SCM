package com.zzc.modules.sysmgr.product.service.vo;

import org.springframework.data.domain.Page;

/**
 * 
 * @author apple
 *
 * @param <T>
 */
public class PageMiddle<T> {

	private Page<T> page;

	private Long totalCount;

	public Page<T> getPage() {
		return page;
	}

	public void setPage(Page<T> page) {
		this.page = page;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

}
