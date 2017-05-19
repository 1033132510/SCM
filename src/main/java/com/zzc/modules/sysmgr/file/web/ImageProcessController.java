package com.zzc.modules.sysmgr.file.web;

import com.zzc.common.util.ImagePathUtil;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.common.ResultData;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.sysmgr.enums.ImageTypeEnum;
import com.zzc.modules.sysmgr.file.entity.Image;
import com.zzc.modules.sysmgr.file.service.ImageService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * 图片上传加载
 */
@Controller
@RequestMapping("image")
public class ImageProcessController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(ImageProcessController.class);

	@Autowired
	private ImageService imageService;

	private static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

	private static final String LOCAL_DEV_ENV = "dev";

	/**
	 * 图片上传
	 * @param imgFile
	 * @param relationType
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String uploadFile(@RequestParam("file") MultipartFile imgFile,
			Integer relationType, HttpServletRequest request) throws Exception {
		validateImage(imgFile);
		boolean isLocalDev = isLocalDev(request);
		Image image = imageService.processImage(imgFile,
				generateImageNewName(imgFile), relationType,
				isLocalDev ? getServletContext(request).getRealPath("/")
						: getSpringprofileActive(request), isLocalDev);
		return JsonUtils.toJson(new ResultData(true, JsonUtils.toJson(image)));
	}

	/**
	 * 图片验证
	 * @param imgFile
	 * @return
	 */
	private boolean validateImage(MultipartFile imgFile) {
		return true;
	}

	private ServletContext getServletContext(HttpServletRequest request) {
		return request.getSession().getServletContext();
	}

	private boolean isLocalDev(HttpServletRequest request) {
		boolean isLocalDev = false;
		if (LOCAL_DEV_ENV.equals(getSpringprofileActive(request))) {
			isLocalDev = true;
		}
		return isLocalDev;
	}

	private String getSpringprofileActive(HttpServletRequest request) {
		return getServletContext(request).getInitParameter(
				SPRING_PROFILES_ACTIVE);
	}

	/**
	 * 生产图片名称
	 * @param imgFile
	 * @return
	 */
	private String generateImageNewName(MultipartFile imgFile) {
		String imageName = UUID.randomUUID().toString();
		String ext = imgFile.getOriginalFilename().substring(
				imgFile.getOriginalFilename().lastIndexOf("."));
		String reName = imageName + ext;
		return reName;
	}

	/**
	 * 根据唯一关键字或获得图片
	 * @param relationId
	 * @param relationType
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResultData getImagesByRelationId(
			@RequestParam(value = "relationId", required = false) String relationId,
			@RequestParam(value = "relationType", required = false) Integer relationType) {
		long startTime = System.currentTimeMillis();
		if (StringUtils.isEmpty(relationId) || relationType == null) {
			return new ResultData(Boolean.FALSE);
		}
		List<Image> images = imageService.findByRelationIdAndRelationType(relationId,
				relationType, CommonStatusEnum.有效.getValue());
		if (CollectionUtils.isEmpty(images)) {
			return new ResultData(Boolean.FALSE);
		}
		ImagePathUtil.setImagePath(
				ImageTypeEnum.getImageTypeEnumByImageTypeValue(relationType),
				images);
		long endTime = System.currentTimeMillis();
		logger.info("【ImageProcessController#getImagesByRelationId请求时间】"
				+ (endTime - startTime) + "毫秒");
		return new ResultData(Boolean.TRUE, images);
	}

}
