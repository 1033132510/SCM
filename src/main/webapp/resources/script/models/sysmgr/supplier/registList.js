$(function() {
	SupplierObject.init();
});

var SupplierObject = {
			getCompanyName : function(record) {
				return record.companyName;
			},
			init : function() {
				gridObj = utils.initGrid('registList', 'sysmgr/supplierOrg/registerSupplies', {});
			}
}, gridObj;