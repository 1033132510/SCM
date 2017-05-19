package com.zzc.common.image;

import java.io.File;

import com.zzc.common.util.ImagePathUtil;
import com.zzc.core.web.listener.PropertiesMapUtilListener;
import com.zzc.modules.sysmgr.enums.ImageTypeEnum;
import com.zzc.modules.sysmgr.file.entity.Image;

/**
 * 本地开发环境图片路径，实现获取路径方法
 * 
 * @author chenjiahai
 *
 */
public class LocalDevEnvImagePath extends AbstractImagePath {

	private static final String LOCAL_DEV_IMAGE_BASE_PATH = "/upload/";
	private static final String FILESERVER_HOST_KEY = "fileServerHost";

	@Override
	public String getImagePath(ImageTypeEnum imageTypeEnum, Image image) {
		return getFileServerHost()
				+ LOCAL_DEV_IMAGE_BASE_PATH + imageTypeEnum.getText()
				+ File.separator + image.getAliasName();
	}
	private static String getFileServerHost() {
		return PropertiesMapUtilListener.p.getProperty(FILESERVER_HOST_KEY);
	}

}
