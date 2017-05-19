package com.zzc.modules.sysmgr.common.util;

import java.io.File;

import com.zzc.core.web.listener.PropertiesMapUtilListener;
import com.zzc.modules.sysmgr.enums.ImageTypeEnum;
import com.zzc.modules.sysmgr.file.entity.Image;

public class ImagePathUtil {

	private final static String IMAGE_BASE_PATH = "/images/";

	private final static String RESOURCE_BASE_PATH = "/resources/";

	private final static String FILESERVER_HOST_KEY = "fileServerHost";

	public static String getOrginResourcesBasePath(ImageTypeEnum imageTypeEnum) {
		String path = PropertiesMapUtilListener.p.getProperty(FILESERVER_HOST_KEY) + File.separator + RESOURCE_BASE_PATH + imageTypeEnum.getText() + File.separator;
		return path;
	}

	public static String getImageBasePath(ImageTypeEnum imageTypeEnum) {
		String path = PropertiesMapUtilListener.p.getProperty(FILESERVER_HOST_KEY) + File.separator + IMAGE_BASE_PATH + imageTypeEnum.getText() + File.separator;
		return path;
	}

	public static String getImageBasePath(ImageTypeEnum imageTypeEnum, Image image) {
		String path = PropertiesMapUtilListener.p.getProperty(FILESERVER_HOST_KEY) + File.separator + IMAGE_BASE_PATH + imageTypeEnum.getText() + File.separator + image.getAliasName();
		return path;
	}

}
