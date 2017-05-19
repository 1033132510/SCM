var bt = baidu.template;
var orderTemplateId = 'intentionOrderTpl';
var orderItemsTemplateId = 'intentionOrderItemsTpl';
var orderStatusAndOperateTemplateId = 'orderStatusAndOperateTpl';
var renderId = 'intentionOrders'
var paginationId = 'pager';
var params = {};
var pageNumber = 1;
var pageSize = 10;
var url = ctx + '/shop/intentionOrder';
$(function() {
	intentionOrder.init();
});

var intentionOrder = {
	init : function() {
		var pagination = initData(pageNumber);
		initPagination(pagination);
		this.bindEvent();
	},
	bindEvent : function() {
		$('a[status]').each(function() {
			$(this).on('click', function() {
				params = {status : $(this).attr('status')};
				var pagination = initData(pageNumber);
				initPagination(pagination);
			});
		});
	}
}

function initPagination(pagination) {
	PageClick = function(pageclickednumber) { 
		pagination = initData(pageclickednumber);
		$('#' + paginationId).pager({ pagenumber: pageclickednumber, pagecount: pagination.totalPages, buttonClickCallback: PageClick });
	}; 
	$('#' + paginationId).pager({ pagenumber: pagination.pageNumber, pagecount: pagination.totalPages, buttonClickCallback: PageClick });
}

function initData(pageNumber) {
	var pagination = {
			pageNumber : pageNumber,
			totalPages : 0
	}
	var data = {
			p : pageNumber,
			ps : pageSize,
	}
	if(params) {
		for(var key in params) {
			data[key] = params[key];
		}
	}
	$.ajax({
        url: url,
        type:'GET',
        data : data,
        dataType : 'json',
        async : false,
        success: function(result){
        	if(result && result.success) {
        		var resultHtml = '';
        		for(var i = 0; i < result.resultData.length; i++) {
        			resultHtml = renderOrderInfo(resultHtml, result.resultData[i]);
        			for(var j = 0; j < result.resultData[i].items.length; j++) {
        				resultHtml = renderOrderItemsInfo(resultHtml, result.resultData[i].items[j]);
        				if(j == 0) {
        					resultHtml = renderStatusAndOperateInfo(resultHtml, result.resultData[i], result.resultData[i].items.length);
        				}
        			}
        		}
        		pagination.totalPages = result.resultData[0].totalPages;
        		$('#' + renderId).html(resultHtml);
        	}else {
        		$('#' + renderId).html('<tr><td colspan="6">暂时无数据</td></tr>');
        	}
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
            zcal({
                type:"error",
                title:"加载数据失败"
            });
        }
    });
	return pagination;
}

// 渲染订单信息
function renderOrderInfo(resultHtml, intentionOrder) {
	intentionOrder.orderTotalPrice = utils.toFixed(intentionOrder.orderTotalPrice);
	resultHtml += bt(orderTemplateId, intentionOrder);
	return resultHtml;
}

// 渲染订单项信息
function renderOrderItemsInfo(resultHtml, intentionOrderItem) {
	intentionOrderItem.itemTotalPrice = utils.toFixed(intentionOrderItem.itemTotalPrice);
	intentionOrderItem.price = utils.toFixed(intentionOrderItem.price);
	resultHtml += bt(orderItemsTemplateId, intentionOrderItem);
	return resultHtml;
}

// 渲染状态和操作(合并单元格操作)
function renderStatusAndOperateInfo(resultHtml, intentionOrder, itemsLength) {
	var statusAndOperate = {
			itemsLength : itemsLength,
			orderId : intentionOrder.orderId,
			orderStatus : intentionOrder.orderStatus
	}
	if(statusAndOperate.orderStatus != 1) {
		statusAndOperate.employeeName = intentionOrder.employeeName;
		statusAndOperate.employeeMobile = intentionOrder.employeeMobile;
	}
	resultHtml += bt(orderStatusAndOperateTemplateId, statusAndOperate);
	return resultHtml;
}

$(document).on('click', '#searchBtn', function (e) {
	searchEvent.init(true);
});
$(function(){
	 $("#searchName").keydown(function(event){
      event = document.all ? window.event : event;
      if((event.keyCode || event.which) == 13) {
      		event.stopPropagation();
      		searchEvent.init(true);
      }
   }); 
});
