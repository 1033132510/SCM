var supplierOrgGrid, INVALID_STATUS = 0;

$(function() {
	var gridObj = utils.initGrid('supplierOrgGrid',
			'/sysmgr/supplierOrg/getSupplierOrgList')
	$('#search').click(function(e) {
		e.preventDefault();
		var searchParams = { // 搜索条件
			orgCodeOrorgName : $('#orgCodeOrorgName').val()
		}
		gridObj.search(searchParams);
	});
});

function baseOperation(record, rowIndex, colIndex, options) {
	var modifySupplier = '<a onclick="modifySupplierOrg(\'' + record.id
			+ '\');" href="javascript:void(0);" class="red">基本操作</a>';
	return modifySupplier;
}

function modifySupplierOrg(supplierOrgId) {
	window.location.href = ctx + '/sysmgr/supplierOrg/getSupplierOrgInfo/'
			+ supplierOrgId + '?t=' + new Date().valueOf();
}

function toGroundingBrand(supplierOrgId) {
	location.href = ctx + '/sysmgr/brand/invalidBrandList?supplierOrgId=' + supplierOrgId + "&t=" + new Date().valueOf();
}

function modifySupplierUser(supplierOrgId) {
	location.href = ctx + '/sysmgr/supplierUser/view/' + supplierOrgId
			+ "?t=" + new Date().valueOf();
}

function brandOperation(record, rowIndex, colIndex, options) {
	var modifyBrand = '<a onclick="toGroundingBrand(\'' + record.id
			+ '\');" href="javascript:void(0);" class="red">失效品牌管理</a>';
	return modifyBrand;
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