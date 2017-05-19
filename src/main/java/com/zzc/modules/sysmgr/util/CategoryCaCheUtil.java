/**
 * 
 */
package com.zzc.modules.sysmgr.util;

import java.util.HashMap;
import java.util.Map;

import com.zzc.common.zTree.ZTreeNode;

/**
 * @author zhangyong
 * 缓存品类信息
 *
 */
public class CategoryCaCheUtil {

	public static Map<String, ZTreeNodeCacheVO> zTreeNodeCache = new HashMap<String, ZTreeNodeCacheVO>();
	
	public final static long MAX_INTERVAL = 30 * 60 * 1000;
	
	public static boolean exist(String id) {
		return zTreeNodeCache.containsKey(id);
	}
	
	public static ZTreeNode get(String id) {
		if(exist(id)) {
			ZTreeNodeCacheVO vo = zTreeNodeCache.get(id);
			// 调用次数加1
			Integer count = vo.getQueryCount();
			vo.setQueryCount(++count);
			vo.setLastQueryTime(System.currentTimeMillis());
			zTreeNodeCache.put(id, vo);
			return vo.getZTreeNode();
		} else {
			return null;
		}
	}
	
	public static void put(ZTreeNode zTreeNode) {
//		remove();
		ZTreeNodeCacheVO vo = new ZTreeNodeCacheVO(zTreeNode);
		zTreeNodeCache.put(zTreeNode.getId(), vo);
	}
	
	public static void setDirdy(String id) {
		ZTreeNodeCacheVO vo = zTreeNodeCache.get(id);
		if(vo != null) {
			vo.setDirty(1);
			zTreeNodeCache.put(id, vo);
		}
	}
	
	public static boolean isOriginal(String id) {
		ZTreeNodeCacheVO vo = zTreeNodeCache.get(id);
		return vo.getDirty() == 0;
	}
	
	/**
	 * 移除距离上次查询时间间隔>=MAX_INTERVAL的所有数据
	 */
	private static void remove() {
		long now = System.currentTimeMillis();
		for(Map.Entry<String, ZTreeNodeCacheVO> entry : zTreeNodeCache.entrySet()) {
			ZTreeNodeCacheVO vo = entry.getValue();
			long lastQueryTime = vo.getLastQueryTime();
			if(now - lastQueryTime > MAX_INTERVAL) {
				zTreeNodeCache.remove(vo.getZTreeNode().getId());
			}
		}
	}
	
	public static class ZTreeNodeCacheVO {
		
		public ZTreeNode zTreeNode;
		
		public long lastQueryTime;
		
		public Integer queryCount;
		
		// 是否是脏数据
		public int dirty;
		
		public ZTreeNode getZTreeNode() {
			return zTreeNode;
		}

		public void setZTreeNode(ZTreeNode zTreeNode) {
			this.zTreeNode = zTreeNode;
		}

		public long getLastQueryTime() {
			return lastQueryTime;
		}

		public void setLastQueryTime(long lastQueryTime) {
			this.lastQueryTime = lastQueryTime;
		}

		public Integer getQueryCount() {
			return queryCount;
		}

		public void setQueryCount(Integer queryCount) {
			this.queryCount = queryCount;
		}
		
		public int getDirty() {
			return dirty;
		}

		public void setDirty(int dirty) {
			this.dirty = dirty;
		}

		public ZTreeNodeCacheVO() {
			
		}

		public ZTreeNodeCacheVO(ZTreeNode zTreeNode) {
			this.zTreeNode = zTreeNode;
			this.queryCount = 0;
			this.lastQueryTime = System.currentTimeMillis();
			this.dirty = 0;
		}
		
	}
	
}