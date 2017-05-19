package com.zzc.modules.sysmgr.file.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.web.controller.BaseController;
import com.zzc.modules.sysmgr.common.util.ImagePathUtil;
import com.zzc.modules.sysmgr.enums.ImageTypeEnum;
import com.zzc.modules.sysmgr.file.entity.Image;
import com.zzc.modules.sysmgr.file.service.ImageService;

/**
 * 图片显示工具
 * 
 * @author ping
 *
 */
@Controller
@RequestMapping(value = "/imageshow")
public class ImageShowController extends BaseController {

	@Autowired
	private ImageService imageService;

	@RequestMapping(value = "/getImages", method = RequestMethod.GET)
	@ResponseBody
	public List<Image> getImageList(@RequestParam(value = "relationId", required = true) String relationId, @RequestParam(value = "relationType", required = true) Integer relationType) {
		List<Image> imageList = new ArrayList<Image>();
		imageList = imageService.findByRelationIdAndRelationType(relationId, relationType, CommonStatusEnum.有效.getValue());
		String path = ImagePathUtil.getImageBasePath(ImageTypeEnum.getImageTypeEnumByImageTypeValue(relationType));
		for (Image image : imageList) {
			image.setPath(path + image.getAliasName());
		}
		return imageList;
	}
}
