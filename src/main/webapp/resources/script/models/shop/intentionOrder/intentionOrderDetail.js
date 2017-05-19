var bt = baidu.template;
var	orderInfoTemplateId = 'orderInfoTpl';
var orderItemsTemplateId = 'orderItemsTpl';
var orderInfoRenderId = 'orderInfo';
var orderItemsRenderId = 'orderItems';
var paginationId = 'pager';
var pageNumber = 1;
var pageSize = 10;
var url = ctx + '/shop/intentionOrder/detail';

$(document).on('click', '#searchBtn', function (e) {
	searchEvent.init(true);
});

$(function() {
	intentionOrderDetail.init();
	$("#searchName").keydown(function (event) {
		event = document.all ? window.event : event;
		if ((event.keyCode || event.which) == 13) {
			event.stopPropagation();
			searchEvent.init(true);
		}
	});
});


var intentionOrderDetail = {
	init : function() {
		var pagination = initData(pageNumber);
		initPagination(pagination);
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
	$.ajax({
        url: url,
        type:'GET',
        data : {p : pageNumber, ps : pageSize, id : $('#id').val()},
        async : false,
        success: function(result){
        		if(result.success) {
        			var resultHtml = '';
        			for(var i = 0; i < result.resultData.data.length; i++) {
        				result.resultData.data[i].itemTotalPrice = utils.toFixed(result.resultData.data[i].itemTotalPrice);
        				result.resultData.data[i].price = utils.toFixed(result.resultData.data[i].price);
        				resultHtml += bt(orderItemsTemplateId, result.resultData.data[i]);
            			}
        	    		pagination.totalPages = result.resultData.totalPages;
        	    		$('#' + orderInfoRenderId).html(bt(orderInfoTemplateId, result.resultData));	
        	    		$('#' + orderItemsRenderId).html(resultHtml);	
        		}else {
        			$('#' + orderInfoRenderId).html('<tr><td>您无权限访问此订单暂时无数据</td></tr>');	
    	    			$('#' + orderItemsRenderId).html('<tr><td colspan="4">您无权限访问此订单</td></tr>');	
        			zcal({
						type:'error',
						title: '系统提示',
						text : result.description
					});
        		}
			
        },
        error: function(){
            zcal({
                type:"error",
                title:"加载数据失败"
            });
        }
    });
	return pagination;
}

function searchDetail(id) {
	window.open(ctx + '/shop/intentionOrder/item/' + id);
}
