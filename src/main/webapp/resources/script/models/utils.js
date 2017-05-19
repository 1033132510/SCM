var utils = function(){}

/***
 * 初始化Grid
 * @param tableId <table>的Id,eg:<table id = "aaa">
 * @param uri	  请求路径,eg:sysmgr/purchase
 */
utils.initGrid = function(tableId, uri, initParams, customProperties) {
	$.bsgridLanguage.noDataToDisplay = '没有数据可以用于显示。';
	var gridObj = $.fn.bsgrid.init(tableId, {
		url : ctx + '/' + uri,
		autoLoad: true,
		pageSizeSelect : false,
		pageSize : 10,
		otherParames : initParams,
		displayBlankRows: false, // 不显示空白行
		displayPagingToolbarOnlyMultiPages: true,
		colsProperties: {
			maxLength: customProperties,
		}
	});
	return gridObj;
}

utils.getFirstErrorLabelId = function(validateObj) {
	if(validateObj.errorList.length > 0) {
		var elementNameAttr = $(validateObj.errorList[0].element).attr('name');
		if(elementNameAttr != null && elementNameAttr != undefined && elementNameAttr != '') {
			return elementNameAttr + '-error';
		}
	}
}

utils.toFixed = function(number, digit) {
	if(!$.isNumeric(number)) {
		console.log('参数不是数字无法转换');
		return;
	}
	if(digit < 0 || digit == undefined || digit == null || !$.isNumeric(digit)) {
		digit = DEFAULT_DIGIT;
	}
	return parseFloat(number).toFixed(digit);
}
//HTML转义
utils.HTMLEncode = function (html) {
	var temp = document.createElement('div');
	(temp.textContent != null) ? (temp.textContent = html) : (temp.innerText = html);
	var output = temp.innerHTML;
	temp = null;
	return output;
}

//HTML反转义
utils.HTMLDecode = function (text) {
	var temp = document.createElement('div');
	temp.innerHTML = text;
	var output = temp.innerText || temp.textContent;
	temp = null;
	return output;
}

var DEFAULT_DIGIT = 2;