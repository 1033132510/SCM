$(function() {
	var orderStatus;
	$('a[status]').each(function() {
		if($(this).hasClass('on')) {
			orderStatus = $(this).attr('status');
		}
		$(this).click(function(e) {
			e.preventDefault();
			orderStatus = $(this).attr('status');
			var searchParams = {
				orderStatus : orderStatus,
				orderCodeOrOrgName : $('#orderCodeOrOrgName').val()
			}
			gridObj.search(searchParams);
		});
	});
	orderStatus = (orderStatus == undefined) ? -1 : orderStatus; // undefined 普通运营人员
	var gridObj = utils.initGrid('intentionOrders',
			'sysmgr/intentionOrder/intentionOrders', {orderStatus : orderStatus});
	$('#search').click(function(e) {
		e.preventDefault();
		$('a[status]').each(function() {
			if ($(this).hasClass('on')) {
				orderStatus = $(this).attr('status');
				return false;
			}
		});
		var searchParams = {
			orderCodeOrOrgName : $('#orderCodeOrOrgName').val(),
			orderStatus : orderStatus
		}
		gridObj.search(searchParams);
	});

	// 回车事件
	$("#orderCodeOrOrgName").keydown(function(event) {
		event = document.all ? window.event : event;
		if ((event.keyCode || event.which) == 13) {
			event.stopPropagation();
			$('#search').click();
		}
	});
});

function fmtPurchaserName(record, rowIndex, colIndex, tdObj, trObj, options) {
	return (record.purchaserUser && record.purchaserUser.purchaser) ? record.purchaserUser.purchaser.orgName
			: '';
}

function fmtPurchaserUserName(record, rowIndex, colIndex, tdObj, trObj, options) {
	return record.purchaserUser ? record.purchaserUser.name : '';
}

function fmtPurchaserUserMobile(record, rowIndex, colIndex, tdObj, trObj,
		options) {
	return record.purchaserUser ? record.purchaserUser.mobile : '';
}

function fmtEmployeeName(record, rowIndex, colIndex, tdObj, trObj, options) {
	return record.employee ? record.employee.employeeName : '';
}

function fmtOrderStatus(record, rowIndex, colIndex, tdObj, trObj, options) {
	if (record.orderStatus == 1) {
		return '待办';
	} else if (record.orderStatus == 0) {
		return '已办';
	}
}

function operation(record, rowIndex, colIndex, options) {
	return '<a onclick="detail(event, \'' + record.id
			+ '\');" href="" class="red">查看详情</a>';
}

function detail(e, id) {
	e.preventDefault();
	e.stopPropagation();
	window.location.href = ctx + '/sysmgr/intentionOrder/' + id;
}
