$(function() {
	ProductObject.init();
});

var baiduTemplate = baidu.template;
baiduTemplate.ESCAPE = false;
ProductObject = {
		bindDisableButtonClick : function() {
			$('#disableProduct').bind('click', function() {
				var $this = $(this);
				if($this.hasClass('disabled')) {
					return false;
				}
				$this.addClass('disabled');
				$.ajax({
					url : ctx + '/sysmgr/product/updateProductStatus',
					data : {productId : $('#id').val(), status : INVALID_STATUS},
					type : 'post',
					success : function(data) {
						if(data.success) {
							zcal({
								type : 'success',
								title : '系统提示',
								text : '下架成功'
							});
						} else {
							zcal({
								type : 'error',
								title : '系统提示',
								text : data.description
							});
						}
						setTimeout(function() {
							location.href = ctx	+ '/sysmgr/product/validProductManager';
						}, TIME);
					},
					error : function() {
						$this.removeClass('disabled');
					}
				});
			});
		},
		generateNumber : function() {
			if (ProductObject.number) {
				return ++ProductObject.number;
			} else {
				return (ProductObject.number = 1);
			}
		},
		generateProductPropertyHtml : function(productCategoryItemKeys) {
			var obj = {
				list : productCategoryItemKeys,
				splitCharacter : ProductObject.splitCharacter,
				generateNumber : ProductObject.generateNumber
			};
			$('#productPropertiesContainer').html($(baiduTemplate('propertiesTemplate', obj)));
		},
		init : function() {
			var id = $('#id').val(), cateId = $('#cateId').val();
			$.ajax({
				url : ctx + '/sysmgr/product/viewProductDetail',
				data : {
					productId : id,
					cateId : cateId
				},
				success : function(data) {
					var supplierOrg = data.supplierOrg, brand = data.brand,
					thirdProductCategory = data.category, secondProductCategory = thirdProductCategory.parentCategory,
					firstProductCategory = secondProductCategory.parentCategory,
					supplierName = supplierOrg.orgName, productImages = data.productImages,
					productDescImages = data.productDescImages;
					$('#productName').val(data.productName);
					$('#minOrderCount').val(data.minOrderCount);
					$('#supplierName').val(supplierName);
					$('#brandName').val(brand.brandZHName);
					// 设置*级品类
					var $firstProductCategoryInput = $('#firstProductCategoryName'), $secondProductCategoryInput = $('#secondProductCategoryName'),
					$thirdProductCategoryInput = $('#thirdProductCategoryName');
					$firstProductCategoryInput.val(firstProductCategory.cateName);
					$secondProductCategoryInput.val(secondProductCategory.cateName);
					$thirdProductCategoryInput.val(thirdProductCategory.cateName);
					$('#number').val(data.productNumber);
					$('#description').val(data.productDesc);
					// 回显图片
					$.fn.image.init('productImage', 14, {readonly : true}, {relationId : data.productId, relationType : 14});
					$.fn.image.init('productImageDetail', 15, {readonly : true}, {relationId : data.productId, relationType : 15});
					// 显示商品属性#productCategoryItemValues
					ProductObject.generateProductPropertyHtml(data.productCategoryItemValues);
					ProductObject.bindDisableButtonClick();
				}
			});
		},
		splitCharacter : ','
}, TIME = 3000,
INVALID_STATUS = 0;