$(function() {
	ProductMaintenanceObject.bindKeywordsKeyup();
	ProductMaintenanceObject.bindSearch();
	ProductMaintenanceObject.bindToAddProduct();
	ProductMaintenanceObject.bindCateIdLiClick();
	ProductMaintenanceObject.bindBrandNameLiClick();
	gridObj = utils.initGrid('productTable', 'supply/product/searchForProductManager', productParam);
});

var ProductMaintenanceObject = {
		bindKeywordsKeyup : function(e) {
			$('#keywords').bind('keyup', function(e) {
				if(e.keyCode == ENTER_CODE) {
					$('#search').click();
				}
			});
		},
		bindCateIdLiClick : function() {
			$('#cateId').find('li').bind('click', function() {
				var $li = $(this), $ul = $li.closest('ul'),
				cateId = $li.attr('data-value') || '', $redLi = $ul.find('.imp'),
				keywords = $.trim($('#keywords').val()) || '';
				if($redLi.length) {
					$redLi.removeClass('imp');
				}
				$li.addClass('imp');
				if(cateId) {
					productParam.cateId = cateId;
				} else {
					 delete productParam.cateId;
				}
				if(keywords) {
					productParam.productName = keywords;
				} else {
					delete productParam.productName;
				}
				gridObj.search(productParam);
			});
		},
		bindBrandNameLiClick : function() {
			$('#brandName').find('li').bind('click', function() {
				var $li = $(this), $ul = $li.closest('ul'),
				brandName = $li.attr('data-value') || '', $redLi = $ul.find('.imp'),
				keywords = $.trim($('#keywords').val()) || '';
				if($redLi.length) {
					$redLi.removeClass('imp');
				}
				$li.addClass('imp');
				if(brandName) {
					productParam.brandName = brandName;
				} else {
					delete productParam.brandName;
				}
				if(keywords) {
					productParam.productName = keywords;
				} else {
					delete productParam.productName;
				}
				gridObj.search(productParam);
			});
		},
		bindSearch : function() {
			$('#search').bind('click', function() {
				var keywords = $.trim($('#keywords').val()) || '';
				if(keywords) {
					productParam.productName = keywords;
				} else {
					delete productParam.productName;
				}
				gridObj.search(productParam);
			});
		},
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
		bindToAddProduct : function() {
			$('#addProduct').bind('click', function() {
				location.href = ctx + '/sysmgr/supply/product/addView';
			});
		},
		getPrice : function(record) {
			if(record.price) {
				return '¥' + new Number(record.price).toFixed(2);
			} else {
				return '暂无';
			}
		},
		toEditProduct : function(id, cateId, parentCateId) {
			ProductMaintenanceObject.existAuditBill(id, ctx + '/supply/product/toUpdateGroundingProduct?productSKUId=' + id + '&cateId=' + cateId + '&parentCateId=' + parentCateId)
		},
		toDisableProduct : function(id, cateId, parentCateId) {
			ProductMaintenanceObject.existAuditBill(id, ctx + '/supply/product/toDisableProduct?productSKUId=' + id + '&cateId=' + cateId + '&parentCateId=' + parentCateId);
		},
		toViewProduct : function(id, cateId) {
			location.href = ctx + '/supply/product/viewProductDetail?productSKUId=' + id + '&cateId=' + cateId;
		},
		operation : function(record, rowIndex, colIndex, options) {
			var productId = record.productId, cateId = record.cateId,
			parentCateId = record.parentCateId;
			return '<a onclick="ProductMaintenanceObject.toEditProduct(\'' + productId + '\', \'' + cateId + '\', \'' + parentCateId + '\')" class="red margin-right-10">调整商品信息</a>'
				+ '<a onclick="ProductMaintenanceObject.toDisableProduct(\'' + productId + '\', \'' + cateId + '\', \'' + parentCateId + '\')" class="red margin-right-10">商品下架</a>'
				+ '<a onclick="ProductMaintenanceObject.toViewProduct(\'' + productId + '\', \'' + cateId + '\')" class="red margin-right-10">查看商品信息</a>';
		}
},
VALID_STATUS = 1,
productParam = {status : VALID_STATUS},
//回车键
ENTER_CODE = 13,
gridObj;