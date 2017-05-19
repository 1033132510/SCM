package com.zzc.modules.sysmgr.file.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zzc.core.dao.BaseDao;
import com.zzc.modules.sysmgr.file.entity.Image;

public interface ImageDao extends BaseDao<Image> {

	/**
	 * 根据relationid relationType查询图片信息
	 * @param relationId
	 * @param relationType
	 * @param status
	 * @return
	 */
	@Query("select i from Image i where i.relationId = :relationId and i.relationType = :relationType and i.status = :status order by createTime desc")
	List<Image> findByRelationIdAndRelationType(@Param("relationId") String relationId, @Param("relationType") Integer relationType, @Param("status") Integer status);

	/**
	 * 根据relationid relationType查询图片信息并排序
	 * @param relationId
	 * @param relationType
	 * @param status
	 * @return
	 */
	List<Image> findByRelationIdAndRelationTypeAndStatusOrderByCreateTimeDesc(String relationId, Integer relationType, Integer status);

	/**
	 * 根据relationid 查询图片信息
	 * @param relationId
	 * @param status
	 * @return
	 */
	@Query("select i from Image i where i.relationId = :relationId and i.status = :status order by createTime desc")
	List<Image> findByRelationId(@Param("relationId") String relationId, @Param("status") Integer status);

	/**
	 * 根据relationid 删除图片信息（修改图片状态）
	 * @param relationId
	 */
	@Modifying
	@Query(value = "update Image i set i.status=0  where  i.relationId = :relationId")
	void deleteByRelationId(@Param("relationId") String relationId);
}
