package com.zzc.common.page;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 前台Grid组件需要的数据结构
 * 
 * @author chenjiahai
 *
 */
public class PageForShow<T> implements Serializable {

	private static final long serialVersionUID = -2520222858731713630L;

	public boolean success = true;

	public Long totalRows = 0L;

	public Integer curPage = 0;

	public List<T> data = Collections.emptyList();

	public static <T> PageForShow<T> newInstanceFromSpringPage(Page<T> page, Integer currentPage) {
		PageForShow<T> pageForShow = new PageForShow<T>();
		if (page == null || currentPage == null)
			return pageForShow;
		if (CollectionUtils.isEmpty(page.getContent()))
			return pageForShow;
		pageForShow.setCurPage(currentPage);
		pageForShow.setTotalRows(page.getTotalElements());
		pageForShow.setData(page.getContent());
		return pageForShow;
	}

	public static <T> PageForShow<T> newInstanceFromSpringPage(List<T> list, Integer currentPage, Long totalElements) {
		PageForShow<T> pageForShow = new PageForShow<T>();
		if (list == null || currentPage == null)
			return pageForShow;
		if (CollectionUtils.isEmpty(list))
			return pageForShow;
		pageForShow.setCurPage(currentPage);
		pageForShow.setTotalRows(totalElements);
		pageForShow.setData(list);
		return pageForShow;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Long getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Long totalRows) {
		this.totalRows = totalRows;
	}

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
