package com.zzc.common.util;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;

import com.zzc.modules.sysmgr.enums.ImageTypeEnum;
import com.zzc.modules.sysmgr.file.entity.Image;

public class ImagePathUtil {

	public static ServletContext servletContext = ContextLoader
			.getCurrentWebApplicationContext().getServletContext();

	public static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

	public static final String LOCAL_DEV_ENV = "dev";

	public static boolean isLocalDev() {
		boolean isLocalDev = false;
		if (LOCAL_DEV_ENV.equals(servletContext
				.getInitParameter(SPRING_PROFILES_ACTIVE))) {
			isLocalDev = true;
		}
		return isLocalDev;
	}

	public static Image setImagePath(ImageTypeEnum imageTypeEnum, Image image) {
		return ImagePathFactory.newInstance(isLocalDev()).setImagePath(
				imageTypeEnum, image);
	}

	public static String getImagePath(ImageTypeEnum imageTypeEnum, Image image) {
		return ImagePathFactory.newInstance(isLocalDev()).getImagePath(
				imageTypeEnum, image);
	}

	public static List<Image> setImagePath(ImageTypeEnum imageTypeEnum,
			List<Image> images) {
		return ImagePathFactory.newInstance(isLocalDev()).setImagePath(
				imageTypeEnum, images);
	}

}
