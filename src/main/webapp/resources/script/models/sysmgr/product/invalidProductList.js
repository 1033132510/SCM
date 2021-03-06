$(function() {
	ProductMaintenanceObject.bindKeywordsKeyup();
	ProductMaintenanceObject.bindSearch();
	ProductMaintenanceObject.initProductCategorySelect();
	ProductMaintenanceObject.setStatusEnum();
	gridObj = utils.initGrid('productTable', 'sysmgr/product/searchForProductManager', productParam);
});

var ProductMaintenanceObject = {
		bindKeywordsKeyup : function(e) {
			$('#product_name_keywords,#brand_name_keywords').bind('keyup', function(e) {
				if(e.keyCode == ENTER_CODE) {
					$('#search').click();
				}
			});
		},
		bindSearch : function() {
			var $selects = $('#productCategoryContainer').find('select'),
			// 取有效的productCategoryId
			productCategoryId = $($selects[2]).val() || $($selects[1]).val() || $($selects[0]).val();
			if(productCategoryId) {
				productParam.cateId = productCategoryId;
			} else {
				delete productParam.cateId;
			}
			$('#search').bind('click', ProductMaintenanceObject.getProductPageByKeywords);
		},
		getBrandName : function(record, rowIndex, colIndex, options) {
			return record.productCategoryMainView.brandZHName;
		},
		getProductCategory : function(record, rowIndex, colIndex, options) {
			return record.productCategoryMainView.cateName;
		},
		getProductCode : function(record) {
			return record.productCategoryMainView.productCode;
		},
		getProductName : function(record) {
			return record.productCategoryMainView.productName;
		},
		getStatusEnumMap : function() {
			$.ajax({
				url : ctx + '/sysmgr/product/getStatusEnumMap',
				async : false,
				success : function(data) {
					ProductMaintenanceObject.statusMap = data;
				}
			});
		},
		toGroundingProduct : function(id, cateId) {
			location.href = ctx + '/sysmgr/product/toGroundingProduct?id=' + id + '&cateId=' + cateId;
		},
		operation : function(record, rowIndex, colIndex, options) {
			var productId = record.productCategoryMainView.productId, cateId = record.productCategoryMainView.cateId;
			return '<a onclick="ProductMaintenanceObject.toGroundingProduct(\'' + productId + '\', \'' + cateId + '\')" class="red margin-right-10">上架</a>';
		},
		initProductCategorySelect : function() {
			selectProductCategory.init({
				callback : function() {
					$('#productCategoryContainer select').select2({
						placeholder: "--请选择--",
						minimumResultsForSearch: -1
					});
				},
				container : $('#productCategoryContainer'),
				labelClasses : [''],
				selectClasses : ['form-control','select'],
				doms : [{
					label : '一级品类',
					key : 'parentCategoryId',
					param : {
						status : VALID_STATUS,
						level : 1,
						parentCategoryId : 1
					}
				}, {
					label : '二级品类',
					key : 'parentCategoryId',
					param : {
						status : VALID_STATUS,
						level : 2
					}
				}, {
					label : '三级品类',
					key : 'parentCategoryId',
					param : {
						status : VALID_STATUS,
						level : 3
					}
				}],
				Key : {
					nameKey : 'cateName',
					valueKey : 'id'
				},
				url : ctx + '/sysmgr/category/cateShow',
				type : 'post'
			});
		},
		getProductPageByKeywords : function(curPage, pageSize) {
			productParam.productName = $.trim($('#product_name_keywords').val());
			productParam.brandName = $.trim($('#brand_name_keywords').val());
			var $productCategoryContainer = $('#productCategoryContainer'), $productCategorySelects = $productCategoryContainer.find('select'),
			cateId = $($productCategorySelects[2]).val() || $($productCategorySelects[1]).val() || $($productCategorySelects[0]).val();
			if(cateId) {
				productParam.cateId = cateId;
			}
			gridObj.search(productParam);
		},
		setStatusEnum : function() {
			var $statusInputs = $('.status'), length = $statusInputs.length, i = 0;
			ProductMaintenanceObject.statusMap = {};
			for(; i < length; i++) {
				var $statusInput = $($statusInputs[i]);
				ProductMaintenanceObject.statusMap[$statusInput.attr('data-key')] = $statusInput.attr('data-value');
			}
		}
},
selectProductCategory = new Components.Select(),
VALID_STATUS = 1,
INVALID_STATUS = 0,
productParam = {status : INVALID_STATUS},
//回车键
ENTER_CODE = 13,
gridObj;