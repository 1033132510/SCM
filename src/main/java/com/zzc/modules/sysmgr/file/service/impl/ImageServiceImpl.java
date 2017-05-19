package com.zzc.modules.sysmgr.file.service.impl;

import com.zzc.common.util.ImagePathUtil;
import com.zzc.core.dao.BaseDao;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.modules.sysmgr.enums.ImageTypeEnum;
import com.zzc.modules.sysmgr.file.dao.ImageDao;
import com.zzc.modules.sysmgr.file.entity.Image;
import com.zzc.modules.sysmgr.file.service.ImageService;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service("imageService")
public class ImageServiceImpl extends BaseCrudServiceImpl<Image> implements
		ImageService {

	private static Logger logger = LoggerFactory
			.getLogger(ImageServiceImpl.class);

	private ImageDao imageDao;

	private static final String FILE_PATH = "/apps/filebase/";

	private static final String ORIGINAL_FILE_PATH = "/images/";

	@Autowired
	public ImageServiceImpl(BaseDao<Image> baseDao) {
		super(baseDao);
		this.imageDao = (ImageDao) baseDao;
	}

	/**
	 * 根据relationid 删除图片
	 * @param relationId
	 */
	@Override
	public void deleteByRelationId(String relationId) {
		imageDao.deleteByRelationId(relationId);
	}

	/**
	 * 图片处理
	 * @param imgFile
	 * @param reName
	 * @param relationType
	 * @param env
	 * @param isDev
	 * @return
	 * @throws Exception
	 */
	@Override
	public Image processImage(MultipartFile imgFile, String reName,
	                          Integer relationType, String env, boolean isDev) throws Exception {
		Image image = saveImageToDb(imgFile, reName, relationType,
				ImageTypeEnum.getImageTypeEnumByImageTypeValue(relationType)
						.getText(), env, isDev);
		saveImageToDisk(imgFile, reName, ImageTypeEnum
						.getImageTypeEnumByImageTypeValue(relationType).getText(), env,
				isDev);
		return image;
	}

	/**
	 * 将图片保存到硬盘
	 * @param imgFile
	 * @param reName
	 * @param imageType
	 * @param env
	 * @param isDev
	 * @throws Exception
	 */
	private void saveImageToDisk(MultipartFile imgFile, String reName,
	                             String imageType, String env, boolean isDev) throws Exception {
		long startTime = System.currentTimeMillis();
		String savePath = "";
		if (isDev) {
			savePath = env + File.separator + "upload" + File.separator
					+ imageType + File.separator;
		} else {
			savePath = FILE_PATH + env + ORIGINAL_FILE_PATH + imageType
					+ File.separator;
		}
		File destDirectory = new File(savePath);
		if (!destDirectory.exists()) {
			destDirectory.mkdirs();
		}
		imgFile.transferTo(new File(savePath + reName));
		long endTime = System.currentTimeMillis();
		logger.info("【图片保存到硬盘执行时间为】" + (endTime - startTime) + "毫秒");
	}

	/**
	 * 将图片保存到数据库
	 * @param imgFile
	 * @param reName
	 * @param relationType
	 * @param imageType
	 * @param env
	 * @param isDev
	 * @return
	 */
	private Image saveImageToDb(MultipartFile imgFile, String reName,
	                            Integer relationType, String imageType, String env, boolean isDev) {
		long startTime = System.currentTimeMillis();
		Image image = new Image();
		image.setOriginalName(imgFile.getOriginalFilename());
		image.setAliasName(reName);
		image.setStatus(CommonStatusEnum.有效.getValue());
		image.setRelationType(relationType);
		image.setPath(ImagePathUtil.getImagePath(ImageTypeEnum
						.getImageTypeEnumByImageTypeValue(relationType),
				image));
		create(image);
		long endTime = System.currentTimeMillis();
		logger.info("【图片保存到数据库执行时间为】" + (endTime - startTime) + "毫秒");
		return image;
	}

	/**
	 * 根据relationid relationType查询图片信息
	 * @param relationId
	 * @param relationType
	 * @param status
	 * @return
	 */
	@Override
	public List<Image> findByRelationIdAndRelationType(String relationId,
	                                                   Integer relationType, Integer status) {
		return imageDao.findByRelationIdAndRelationType(relationId,
				relationType, status);
	}

	/**
	 * 根据relationid 查询图片信息
	 * @param relationId
	 * @param status
	 * @return
	 */
	@Override
	public List<Image> findByRelationId(String relationId, Integer status) {
		return imageDao.findByRelationId(relationId, status);
	}

	/**
	 * 解除关联关系
	 * @param imageIds
	 */
	@Override
	public void destoryRelationBetweenImageAndEntity(String[] imageIds) {
		if (ArrayUtils.isNotEmpty(imageIds)) {
			for (String imageId : imageIds) {
				Image image = findByPK(imageId);
				image.setStatus(CommonStatusEnum.无效.getValue());
				update(image);
			}
		}
	}

	@Override
	public void destoryRelationBetweenImageAndEntity(List<String> imageIds) {
		destoryRelationBetweenImageAndEntity(imageIds.toArray(new String[imageIds.size()]));
	}

	/**
	 * 根据relationid relationType查询图片信息并排序
	 * @param relationId
	 * @param relationType
	 * @param status
	 * @return
	 */
	@Override
	public List<Image> findByRelationIdAndRelationTypeAndStatusOrderByCreateTimeAsc(
			String relationId, Integer relationType, Integer status) {
		return imageDao
				.findByRelationIdAndRelationTypeAndStatusOrderByCreateTimeDesc(
						relationId, relationType, status);
	}

	/**
	 * 建立关联关系
	 * @param imageIds
	 * @param relationId
	 */
	@Override
	public void buildRelationBetweenImageAndEntity(String[] imageIds,
	                                               String relationId) {
		if (ArrayUtils.isNotEmpty(imageIds)) {
			for (String imageId : imageIds) {
				Image image = findByPK(imageId);
				image.setStatus(CommonStatusEnum.有效.getValue());
				image.setRelationId(relationId);
				update(image);
			}
		}
	}

	@Override
	public void buildRelationBetweenImageAndEntity(List<String> imageIds, String relationId) {
		buildRelationBetweenImageAndEntity(imageIds.toArray(new String[imageIds.size()]), relationId);
	}

}
