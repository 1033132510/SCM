

$(function() {
	 var gridObj = utils.initGrid('purchaserUser', 'sysmgr/purchaserUser/purchaser/' + $('#purchaserId').val() + '?curPage=1')
	 $('#savePurchaserUserView').click(function(e) {
		 e.preventDefault();
		 window.location.href = ctx + '/sysmgr/purchaserUser/addView/purchaser/' + $('#purchaserId').val();
	 });
	 
	 $('#getPurchaserInfo').click(function(e) {
		 e.preventDefault();
		 window.location.href = ctx + '/sysmgr/purchaser/modifyView/' + $('#purchaserId').val() + '/?readOnly=1';
	 });
});

function formatterStatus(record, rowIndex, colIndex, tdObj, trObj, options) {
    if (record.status == 0) {
    		return '无效';
    } else if (record.status == 1) {
    		return '有效';
    }
}

function formatterCompany(record, rowIndex, colIndex, tdObj, trObj, options) {
	var company = record.purchaser.orgName;
	if(record.department) {
		company = company + '\\' + record.department;
	}
	return company;
}

function operation(record, rowIndex, colIndex, options) {
    return '<a onclick="modifyPurchaserUser(\'' + record.id + '\', '+ '\''+ record.purchaser.id +'\');" href="javascript:void(0);" class="red">编辑</a>';
}

function modifyPurchaserUser(id, purchaserId) {
	window.location.href = ctx + '/sysmgr/purchaserUser/' + id + '/purchaser/' + purchaserId;
}