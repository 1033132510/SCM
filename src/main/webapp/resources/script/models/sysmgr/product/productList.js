$(function() {
	
	ProductMaintenanceObject.bindKeywordsKeyup();
	
	ProductMaintenanceObject.bindSearch();
	
	ProductMaintenanceObject.initProductCategorySelect();
	
	ProductMaintenanceObject.bindToAddProduct();
	
});

var ProductMaintenanceObject = {
		bindKeywordsKeyup : function(e) {
			$('#product_name_keywords').bind('keyup', function(e) {
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
			return record.brand.brandZHName;
		},
		getProductCategory : function(record, rowIndex, colIndex, options) {
			return record.category.cateName;
		},
		bindToAddProduct : function() {
			$('#addProduct').bind('click', function() {
				location.href = ctx + '/sysmgr/product/toAddOrUpdateProduct';
			});
		},
		toEditProduct : function(id, cateId) {
			location.href = ctx + '/sysmgr/product/toAddOrUpdateProduct?id=' + id + '&cateId=' + cateId;
		},
		operation : function(record, rowIndex, colIndex, options) {
			return '<a onclick="ProductMaintenanceObject.toEditProduct(\'' + record.productId + '\', \'' + record.category.id + '\')" class="red">编辑</a>';
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
				url : ctx + '/shop/category/cateShow',
				type : 'post'
			});
		},
		getProductPageByKeywords : function(curPage, pageSize) {
			productParam.productName = $.trim($('#product_name_keywords').val());
			var $productCategoryContainer = $('#productCategoryContainer'), $productCategorySelects = $productCategoryContainer.find('select'),
			cateId = $($productCategorySelects[2]).val() || $($productCategorySelects[1]).val() || $($productCategorySelects[0]).val();
			if(cateId) {
				productParam.cateId = cateId;
			}
			gridObj.search(productParam);
		}
}, productParam = {status : 1},
selectProductCategory = new Components.Select(),
VALID_STATUS = 1,
//回车键
ENTER_CODE = 13,
gridObj = utils.initGrid('productTable', 'sysmgr/product/searchByCateOrName', productParam);