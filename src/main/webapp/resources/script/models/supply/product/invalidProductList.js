$(function() {
	gridObj = utils.initGrid('productTable', 'supply/product/searchForProductManager', productParam);
});

var ProductMaintenanceObject = {
		existAuditBill : function(id, url) {
			$.ajax({
        		url : ctx + '/supply/auditBill/existAuditBillByProductSKUId',
        		data : {productSKUId : id},
        		success : function(data) {
        			if(!data.resultData) {
        				zcal({
        					type: 'warning',
        					title: '系统提示',
        					text: data.description
        				});
        			} else {
        				location.href = url;
        			}
        		}
        	});
		},
		getPrice : function(record) {
			if(record.price) {
				return '¥' + new Number(record.price).toFixed(2);
			} else {
				return '暂无';
			}
		},
		toGroundingProduct : function(id, cateId, parentCateId) {
			ProductMaintenanceObject.existAuditBill(id, ctx + '/supply/product/toGroundingProduct?productSKUId=' + id + '&cateId=' + cateId + '&parentCateId=' + parentCateId);
		},
		toViewProduct : function(id, cateId) {
			location.href = ctx + '/supply/product/viewProductDetail?productSKUId=' + id + '&cateId=' + cateId;
		},
		operation : function(record, rowIndex, colIndex, options) {
			var productId = record.productId, cateId = record.cateId,
			parentCateId = record.parentCateId;
			return '<a onclick="ProductMaintenanceObject.toGroundingProduct(\'' + productId + '\', \'' + cateId + '\', \'' + parentCateId + '\')" class="red margin-right-10">商品上架</a>'
				+ '<a onclick="ProductMaintenanceObject.toViewProduct(\'' + productId + '\', \'' + cateId + '\')" class="red margin-right-10">查看商品信息</a>';
		}
},
INVALID_STATUS = 0,
productParam = {status : INVALID_STATUS},
//回车键
ENTER_CODE = 13,
gridObj;