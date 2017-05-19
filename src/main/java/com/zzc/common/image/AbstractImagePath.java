package com.zzc.common.image;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.zzc.modules.sysmgr.enums.ImageTypeEnum;
import com.zzc.modules.sysmgr.file.entity.Image;

/**
 * 抽象图片路径类,定义获取图片路径接口。 类似模板模式实现设置路径方法
 * 
 * @author chenjiahai
 *
 */
public abstract class AbstractImagePath {

	public abstract String getImagePath(ImageTypeEnum imageTypeEnum, Image image);

	public Image setImagePath(ImageTypeEnum imageTypeEnum, Image image) {
		image.setPath(getImagePath(imageTypeEnum, image));
		return image;
	}

	public List<Image> setImagePath(ImageTypeEnum imageTypeEnum,
			List<Image> images) {
		if (CollectionUtils.isNotEmpty(images)) {
			for (Image image : images) {
				setImagePath(imageTypeEnum, image);
			}
		}
		return images;
	}

}
