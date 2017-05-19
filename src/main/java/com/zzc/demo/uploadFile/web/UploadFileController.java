package com.zzc.demo.uploadFile.web;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.zzc.core.web.controller.BaseController;

@Controller
@RequestMapping("upload")
public class UploadFileController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String view() {
		return "upload";
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String uploadFile(@RequestParam("files") MultipartFile imgFile,
			Integer extraData) throws IllegalStateException, IOException {
		String savePath = "/Users/chenjiahai/develop/temp/";
		String ext = imgFile.getOriginalFilename().substring(
				imgFile.getOriginalFilename().indexOf('.'));
		imgFile.transferTo(new File(savePath + UUID.randomUUID().toString()
				+ ext));
		JSONObject response = new JSONObject();
		response.put("success", true);
		return JSONObject.toJSONString(response);
	}
}
