$(function() {
	
	$('#add').bind('click', function() {
		CategoryChargeObject.toEdit();
	});
	CategoryChargeObject.bindSearchButtonClick();
	CategoryChargeObject.bindInputEnter();

});

var CategoryChargeObject = {
		bindSearchButtonClick : function() {
			$('#search').bind('click', function() {
				var keywords = $('#keywords').val();
				categoryChargeParam.keywords = keywords;
				gridObj.search(categoryChargeParam);
			});
		},
		bindInputEnter : function() {
			$('#keywords').bind('keyup', function(event) {
				var keyCode = event.keyCode;
				if(keyCode == ENTER_CODE) {
					$('#search').click();
				}
			});
		},
		option : function(record) {
			var  employeeId = record.id, employeeName = record.employeeName;
			return '<a onclick="CategoryChargeObject.toEdit(\'' + employeeId + '\', \'' + employeeName + '\')" class="red">修改</a>';
		},
		showCategoryName : function(record, rowIndex, colIndex, options) {
			var i = 0, list = record.cateNames,
			length, categoryNames = [];
			if(list && (length = list.length)) {
				for(; i < length; i++) {
					// 只显示前三个品类名称
					if(i == 3) {
						return categoryNames.join(',') + '...';
						break;
					}
					categoryNames.push(list[i]);
				}
				return categoryNames.join(',');
			} else {
				return '暂无';
			}
		},
		toEdit : function(employeeId, employeeName) {
			if(employeeId && employeeName) {
				location.href = ctx + '/sysmgr/category/charge/toAddOrUpdateCategoryMajordomo?employeeId=' + employeeId + '&employeeName=' + employeeName;
			} else {
				location.href = ctx + '/sysmgr/category/charge/toAddOrUpdateCategoryMajordomo';
			}
		}
}, VALID_STATUS = 1,
//回车键
ENTER_CODE = 13,
curPage = 1,
PAGE_SIZE = 10,
CATEGORY_MAJORDOMO = 2,
categoryChargeParam = {type : CATEGORY_MAJORDOMO},
gridObj = utils.initGrid('categoryChargeTable', 'sysmgr/category/charge/getCategoryChargePageByKeywords', categoryChargeParam)