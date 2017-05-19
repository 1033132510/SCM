package com.zzc.common.image;

import com.zzc.core.web.listener.PropertiesMapUtilListener;
import com.zzc.modules.sysmgr.enums.ImageTypeEnum;
import com.zzc.modules.sysmgr.file.entity.Image;

import java.io.File;

/**
 * 测试与生产环境图片路径，实现获取路径方法。
 * 
 * @author chenjiahai
 *
 */
public class TestAndProductEnvImagePath extends AbstractImagePath {

	private static final String IMAGE_BASE_PATH = "/images/";

	private static final String FILESERVER_HOST_KEY = "fileServerHost";

	private static String getFileServerHost() {

		return PropertiesMapUtilListener.p.getProperty(FILESERVER_HOST_KEY);
	}

	@Override
	public String getImagePath(ImageTypeEnum imageTypeEnum, Image image) {
		return getFileServerHost() + File.separator + IMAGE_BASE_PATH
				+ imageTypeEnum.getText() + File.separator
				+ image.getAliasName();
	}

}
