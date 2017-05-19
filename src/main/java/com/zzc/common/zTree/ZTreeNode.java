package com.zzc.common.zTree;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zzc.core.security.XssIgnoreSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * 树节点结构
 * 
 * @author ping
 *
 */
public class ZTreeNode {
	private String id;

	private String name;

	private String pId;

	/**
	 * 节点级别
	 */
	private int level;

	/**
	 * 请求子节点URL
	 */
	@JsonSerialize(using = XssIgnoreSerializer.class)
	private String childURL;

	/**
	 * 请求详细信息URL
	 */
	@JsonSerialize(using = XssIgnoreSerializer.class)
	private String infoURL;

	private String icon;
	
	private String updateURL;
	
	private boolean addBtn = true;
	
	private boolean removeBtn = false;
	
	private boolean expandBtn = true;

	public boolean isAddBtn() {
		return addBtn;
	}

	public void setAddBtn(boolean addBtn) {
		this.addBtn = addBtn;
	}

	public boolean isRemoveBtn() {
		return removeBtn;
	}

	public void setRemoveBtn(boolean removeBtn) {
		this.removeBtn = removeBtn;
	}

	public boolean isExpandBtn() {
		return expandBtn;
	}

	public void setExpandBtn(boolean expandBtn) {
		this.expandBtn = expandBtn;
	}

	public String getUpdateURL() {
		return updateURL;
	}

	public void setUpdateURL(String updateURL) {
		this.updateURL = updateURL;
	}

	/**
	 * 参数
	 */
	private Map<String, Object> param = new HashMap<String, Object>();

	/**
	 * 是否是子节点 1是，0不是
	 */
	private boolean last;

	/**
	 * 是否打开 1展开 0 折叠
	 */
	private boolean open;

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}


	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getChildURL() {
		return childURL;
	}

	public void setChildURL(String childURL) {
		this.childURL = childURL;
	}

	public String getInfoURL() {
		return infoURL;
	}

	public void setInfoURL(String infoURL) {
		this.infoURL = infoURL;
	}

	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}

}
