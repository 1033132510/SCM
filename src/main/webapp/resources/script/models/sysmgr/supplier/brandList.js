var supplierOrgGrid;

$(function() {
	var gridObj = utils.initGrid('supplierOrgBrandGrid',
			'/sysmgr/supplier/getBrandList')
});

function baseOperation(record, rowIndex, colIndex, options) {
	var modifyBrand = '<a onclick="modifyBrand(\'' + record.id
			+ '\');" href="javascript:void(0);" class="red">基本操作</a>';
	return modifyBrand;
}

function modifyBrand(id) {
	window.location.href = ctx + '/sysmgr/brand/listByOrg?supplierOrgId?' + id;
}
