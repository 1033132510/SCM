package com.zzc.demo.component.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(value = "/demo/component")
public class ComponentController {

	@RequestMapping(value = "/getProvinces")
	@ResponseBody
	public String getProvinces() {
		JSONArray arr = new JSONArray();
		JSONObject obj1 = new JSONObject();
		obj1.put("name", "四川省");
		obj1.put("value", 1);

		JSONObject obj2 = new JSONObject();
		obj2.put("name", "河南省");
		obj2.put("value", 2);
		arr.add(obj1);
		arr.add(obj2);
		return JSONObject.toJSONString(arr);
	}

	@RequestMapping(value = "/getCitiesByProvinceId")
	@ResponseBody
	public String getCitiesByProvinceId(Integer id) {
		JSONArray arr = new JSONArray();
		if (id == 1) {
			JSONObject obj1 = new JSONObject();
			obj1.put("name", "成都市");
			obj1.put("value", 3);

			JSONObject obj2 = new JSONObject();
			obj2.put("name", "眉山市");
			obj2.put("value", 4);
			arr.add(obj1);
			arr.add(obj2);
		} else {
			JSONObject obj1 = new JSONObject();
			obj1.put("name", "驻马店市");
			obj1.put("value", 5);

			JSONObject obj2 = new JSONObject();
			obj2.put("name", "焦作市");
			obj2.put("value", 6);
			arr.add(obj1);
			arr.add(obj2);
		}
		return JSONObject.toJSONString(arr);
	}

	@RequestMapping(value = "/getAreasByCityId")
	@ResponseBody
	public String getAreasByCityId(Integer id) {
		JSONArray arr = new JSONArray();
		if (id == 3) {
			JSONObject obj1 = new JSONObject();
			obj1.put("name", "郫县");
			obj1.put("value", 7);

			JSONObject obj2 = new JSONObject();
			obj2.put("name", "温江县");
			obj2.put("value", 8);
			arr.add(obj1);
			arr.add(obj2);
		} else if (id == 4) {
			JSONObject obj1 = new JSONObject();
			obj1.put("name", "彭山县");
			obj1.put("value", 9);

			JSONObject obj2 = new JSONObject();
			obj2.put("name", "青龙县");
			obj2.put("value", 10);
			arr.add(obj1);
			arr.add(obj2);
		} else if (id == 5) {
			JSONObject obj1 = new JSONObject();
			obj1.put("name", "黄淮学院");
			obj1.put("value", 11);

			JSONObject obj2 = new JSONObject();
			obj2.put("name", "碴牙山");
			obj2.put("value", 12);
			arr.add(obj1);
			arr.add(obj2);
		} else {
			JSONObject obj1 = new JSONObject();
			obj1.put("name", "温县");
			obj1.put("value", 13);

			JSONObject obj2 = new JSONObject();
			obj2.put("name", "白石山");
			obj2.put("value", 14);
			arr.add(obj1);
			arr.add(obj2);
		}
		return JSONObject.toJSONString(arr);
	}

	@RequestMapping(value = "/select")
	public String select() {
		return "demo/component/select";
	}

}