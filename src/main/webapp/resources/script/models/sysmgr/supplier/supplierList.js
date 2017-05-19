var supplierOrgGrid;

$(function() {
	var gridObj = utils.initGrid('supplierOrgGrid',
			'/sysmgr/supplierOrg/getSupplierOrgList?status='+$('#status').val());
	$('#search').click(function(e) {
		e.preventDefault();
//		if(!$.trim($('#orgCodeOrorgName').val())){
//			return;
//		}
		var searchParams = { // 搜索条件
			orgCodeOrorgName : $.trim($('#orgCodeOrorgName').val()),
			status:$('#status').val()
		}
		gridObj.search(searchParams);
	});
	
	// 回车事件
    $("#orgCodeOrorgName").keydown(function(event){
        event = document.all ? window.event : event;
        if((event.keyCode || event.which) == 13) {
        		event.stopPropagation();
        		$('#search').click();
        }
     }); 
});

function baseOperation(record, rowIndex, colIndex, options) {
	var modifySupplier = '<a onclick="modifySupplierOrg(\'' + record.id
			+ '\');" href="javascript:void(0);" class="red margin-right-10">编辑公司信息</a>';
	var modifyBrand = '<a onclick="modifyBrand(\'' + record.id
	+ '\');" href="javascript:void(0);" class="red margin-right-10">品牌管理</a>';
	var modifyUser = '<a onclick="modifySupplierUser(\'' + record.id
	+ '\');" href="javascript:void(0);" class="red margin-right-10">人员管理</a>';
	return modifySupplier+modifyBrand+modifyUser;
}

function modifySupplierOrg(supplierOrgId) {
	window.location.href = ctx + '/sysmgr/supplierOrg/getSupplierOrgInfo/'
			+ supplierOrgId + '?t=' + new Date().valueOf();
}

function modifyBrand(supplierOrgId) {
	window.location.href = ctx + '/sysmgr/brand/listByOrg?supplierOrgId='
			+ supplierOrgId + "&t=" + new Date().valueOf();
}

function modifySupplierUser(supplierOrgId) {
	window.location.href = ctx + '/sysmgr/supplierUser/view/' + supplierOrgId
			+ "?t=" + new Date().valueOf();
}


function addSupplierOrg() {
	window.location.href = ctx + '/sysmgr/supplierOrg/addSupplierOrg?t='
			+ new Date().valueOf();
}

function formatterStatus(record, rowIndex, colIndex, tdObj, trObj, options) {
	var status = '';
	if (record.status == '1') {
		status = '有效';
	} else {
		status = '无效';
	}
	return status;
}

function formatterSupplierType(record, rowIndex, colIndex, tdObj, trObj,
		options) {
	var supplierType = '';
	if (record.supplierType == '1') {
		supplierType = '代理商';
	} else {
		supplierType = '厂商';
	}
	return supplierType;
}


function statusOperation(record, rowIndex, colIndex, options){
	var modifyStatus = '<a onclick="modifySupplierStatus(\'' + record.id
	+ '\',0);" href="javascript:void(0);" class="red">置为无效</a>';
return modifyStatus;
}

function statusFailureOperation(record, rowIndex, colIndex, options){
	var modifyStatus = '<a onclick="modifySupplierStatus(\'' + record.id
	+ '\',1);" href="javascript:void(0);" class="red">置为有效</a>';
return modifyStatus;
}

function modifySupplierStatus(supplierOrgId,status) {
	window.location.href = ctx + '/sysmgr/supplierOrg/getSupplierOrgInfo/' + supplierOrgId
			+ '?status='+status+"&t=" + new Date().valueOf();
}