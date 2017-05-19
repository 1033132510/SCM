$(function() {
	PurchaserObject.init();
});

var PurchaserObject = {
			getCompanyName : function(record) {
				return record.companyName;
			},
			getDepartmentName : function(data) {
				var departmentName = data.departmentName;
				return departmentName ? departmentName : '暂无';
			},
			getPosition : function(data) {
				var position = data.position;
				return position ? position : '暂无';
			},
			init : function() {
				gridObj = utils.initGrid('registList', 'sysmgr/purchaser/registerPurchasers', {});
			}
}, gridObj;