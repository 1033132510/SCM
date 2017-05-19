package com.zzc.common.sms;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import com.alibaba.fastjson.JSONException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.common.ResultData;

public class SmsService {

	private static Logger logger = LoggerFactory.getLogger(SmsService.class);

	// 调用restful的发送key
	private static final String PARAMS_KEY_MOBILE = "mobile";
	private static final String PARAMS_KEY_MESSAGE = "message";
	private static final String PARAMS_KEY_API = "api";

	// restful 返回的结果串的key
	private static final String RESULT_KEY_ERROR_CODE = "error";
	private static final String RESULT_KEY_MSG_CODE = "msg";

	private static final String EMPTY_STRING = "";

	private static Pattern pattern = Pattern.compile(
			"\\{\\s*([a-zA-Z\\.\\_0-9()]+)\\s*\\}", Pattern.MULTILINE);
	private String prefix = EMPTY_STRING;
	private String url;
	private String key;
	private Map<String, Object> templetMap = Collections.emptyMap();

	@Async
	public Future<ResultData> sendSms(SmsWithoutPlaceholder sms) {
		logger.info("【异步发送短信开始，手机号为】" + sms.getMobile() + "【，短信模板key为】"
				+ sms.getKey());
		boolean result = false;
		if (sms instanceof SmsWithNamingPlaceholder) {
			SmsWithNamingPlaceholder namingSms = (SmsWithNamingPlaceholder) sms;
			result = sendSms(namingSms.getMobile(), namingSms.getKey(),
					namingSms.getParams());
		} else if (sms instanceof SmsWithIndexPlaceholder) {
			SmsWithIndexPlaceholder indexSms = (SmsWithIndexPlaceholder) sms;
			result = sendSms(indexSms.getMobile(), indexSms.getKey(),
					indexSms.getValues());
		} else {
			result = sendSms(sms.getMobile(), sms.getKey());
		}
		return new AsyncResult<ResultData>(new ResultData(result, sms));
	}

	/**
	 * 发送单条短信，短信内容定制
	 * 
	 * @param mobile
	 *            手机号
	 * @param key
	 *            配置模板的key值
	 * @return
	 */
	private boolean sendSms(String mobile, String key) {
		checkParams(mobile, key);
		String template = searchTemplateByKey(key);
		String responseText = send(mobile, template);
		return parserResult(responseText);
	}

	/**
	 * 发送单条短信，短信内容定制
	 * 
	 * @param mobile
	 *            手机号
	 * @param key
	 *            配置模板的key值
	 * @param args
	 *            替换值(数组)
	 * @return
	 */
	private boolean sendSms(String mobile, String key, Object[] args) {
		checkParams(mobile, key);
		String template = searchTemplateByKey(key);
		template = replace(template, args);
		String responseText = send(mobile, template);
		return parserResult(responseText);
	}

	/**
	 * 发送单条短信，短信内容定制
	 * 
	 * @param mobile
	 *            手机号
	 * @param key
	 *            配置模板的key值
	 * @param pairs
	 *            替换值(Map)
	 * @return
	 */
	private boolean sendSms(String mobile, String key, Map<String, Object> pairs) {
		checkParams(mobile, key);
		String template = searchTemplateByKey(key);
		template = replace(template, pairs);
		String responseText = send(mobile, template);
		return parserResult(responseText);
	}

	private String send(String mobile, String template) {
		logger.info("【发送短信，手机号为】" + mobile + "【内容为】" + template + prefix);
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		formData.add(PARAMS_KEY_MOBILE, mobile);
		formData.add(PARAMS_KEY_MESSAGE, template + prefix);

		WebResource webResource = getWebResource();
		ClientResponse response = webResource.type(
				MediaType.APPLICATION_FORM_URLENCODED).post(
				ClientResponse.class, formData);
		String responseText = response.getEntity(String.class);
		return responseText;
	}

	private String replace(String template, Object[] args) {
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				template = template.replace("{" + i + "}", args[i]
						+ EMPTY_STRING);
			}
		}
		return template;
	}

	private String searchTemplateByKey(String key) {
		String content = (String) templetMap.get(key);
		if (StringUtils.isBlank(content)) {
			logger.error("【配置文件中没有配置】" + key);
			throw new IllegalArgumentException("配置文件中没有配置" + key);
		}
		return content;
	}

	private String replace(String template, Map<String, Object> pairs) {
		Matcher matcher = pattern.matcher(template);
		String target = null;
		String keyOfPairs = null;
		String valueOfPairs = null;
		while (matcher.find()) {
			target = matcher.group(0);
			keyOfPairs = matcher.group(1);
			valueOfPairs = String.valueOf(pairs.get(keyOfPairs));
			if (StringUtils.isNotBlank(template)
					&& StringUtils.isNotBlank(keyOfPairs)
					&& StringUtils.isNoneBlank(valueOfPairs)) {
				template = template.replace(target, valueOfPairs);
			}
		}
		return template;
	}

	/**
	 * 获取WebResource
	 * 
	 * @return
	 */
	private WebResource getWebResource() {
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter(PARAMS_KEY_API, key));
		WebResource webResource = client.resource(url);
		return webResource;
	}

	/**
	 * 解析结果
	 * 
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	private static boolean parserResult(String response) {
		try {

			Map<String, Object> object = JsonUtils
					.toObject(response, Map.class);

			Integer error_code = (Integer) object.get(RESULT_KEY_ERROR_CODE);
			String error_msg = (String) object.get(RESULT_KEY_MSG_CODE);

			if (error_code == 0) {
				logger.info("Send message success.");
				return true;
			} else {
				logger.error("Send message failed,code is " + error_code
						+ ",msg is " + error_msg);
				return false;
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			return false;
		}
	}

	private void checkParams(String mobile, String key) {
		if (StringUtils.isBlank(mobile) || StringUtils.isBlank(key)) {
			throw new IllegalArgumentException("mobile和key必须不能为空");
		}
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix.trim();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url.trim();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key.trim();
	}

	public Map<String, Object> getTempletMap() {
		return templetMap;
	}

	public void setTempletMap(Map<String, Object> templetMap) {
		this.templetMap = templetMap;
	}

	public SmsService() {
	}

}
