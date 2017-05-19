package com.zzc.modules.sysmgr.file.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zzc.core.service.BaseCrudService;
import com.zzc.modules.sysmgr.file.entity.Image;

@Service("imageService")
public interface ImageService extends BaseCrudService<Image> {

	void deleteByRelationId(String relationId);

	Image processImage(MultipartFile imgFile, String reName, Integer relationType, String env, boolean isDev) throws Exception;

	List<Image> findByRelationIdAndRelationType(String relationId, Integer relationType, Integer status);

	List<Image> findByRelationId(String relationId, Integer status);

	void destoryRelationBetweenImageAndEntity(String[] imageIds);

	void destoryRelationBetweenImageAndEntity(List<String> imageIds);

	void buildRelationBetweenImageAndEntity(String[] imageIds, String relationId);

	void buildRelationBetweenImageAndEntity(List<String> imageIds, String relationId);

	List<Image> findByRelationIdAndRelationTypeAndStatusOrderByCreateTimeAsc(String relationId, Integer relationType, Integer status);

}
