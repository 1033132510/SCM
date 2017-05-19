var XMLHttpReq;
var sysmgr = 'sysmgr';
var shop = 'shop';
function createXMLHttpRequest() {
	try {
		XMLHttpReq = new ActiveXObject("Msxml2.XMLHTTP");// IE高版本创建XMLHTTP
	} catch (E) {
		try {
			XMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");// IE低版本创建XMLHTTP
		} catch (E) {
			XMLHttpReq = new XMLHttpRequest();// 兼容非IE浏览器，直接创建XMLHTTP对象
		}
	}
}

function checkSessionIfTimeout(urls) {
	createXMLHttpRequest(); // 创建XMLHttpRequest对象
	var response = '';
	XMLHttpReq.open("post", urls[1], false);
	XMLHttpReq.onreadystatechange = function() {
		response = processResponse(urls[0]);
	}; // 指定响应函数
	XMLHttpReq.send(null);
	return response;
}
// 回调函数
function processResponse(env) {
	if (XMLHttpReq.readyState == 4) {
		if (XMLHttpReq.status == 200) {
			return XMLHttpReq.responseText;
		}
	}

}

function generateCheckSessionUrl() {
	var currentRequestUrl = window.location.href;
	var checkSessionUrl = '';
	var urls = [];
	if(currentRequestUrl.indexOf(sysmgr) >= 0) {
		checkSessionUrl = ctx + '/check/' + sysmgr;
		urls.push(sysmgr);
	}else if(currentRequestUrl.indexOf(shop) >= 0) {
		checkSessionUrl = ctx + '/check/' + shop;
		urls.push(shop);
	}
	urls.push(checkSessionUrl);
	return urls;
}

$(function() {
	$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
		var urls = generateCheckSessionUrl();
		var response = checkSessionIfTimeout(urls);
		if('true' === response) {
			jqXHR.abort();
			window.location.href = ctx + '/' + urls[0] + '/login';
		}
	});
//	$(document).ajaxError(function(event,request, settings){
//		console.info(event);
//		console.info(request);
//		console.info(settings);
//	});
//	$(document).ajaxSuccess(function(event, xhr, settings) {
//		console.info(event);
//		console.info(xhr);
//		console.info(settings);
//		  
//	});
	
})