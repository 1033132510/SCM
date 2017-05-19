$(function () {
    ProductObject.init();
});

var baiduTemplate = baidu.template,
    ProductObject = {
		bindSubmitForm : function() {
			$('#submitForm').bind('click', function(e) {
				comment = $.trim($('#comment').val());
            	if(!comment) {
            		zcal({
                        type: 'warning',
                        title: '错误提示',
                        text: '请填写备注信息'
                    });
            		return false;
            	} else {
            		$('#closeCommentModal').click();
            	}
				$.ajax({
					url : ctx + '/supply/product/disableProduct',
					data : {
						productSKUId : $('#productSKUId').val(),
						comment : $.trim($('#comment').val()),
						costPrice : $('#costPrice').val(),
						recommendedPrice : $('#recommendedPrice').val(),
						standardPrice : standardPrice,
						cateId : $('#cateId').val(),
						hasTax : $('#hasTax').closest('label').hasClass('checked') ? 1 : 0,
						hasTransportation : $('#hasTransportation').closest('label').hasClass('checked') ? 1 : 0
					},
					type : 'post',
					success : function(data) {
						if(data.success) {
							zcal({
								type: 'success',
								title: '系统提示',
								text: '商品调整后，提交审批成功，等待品类管理员审核！'
							});
							setTimeout(function() {
								location.href = ctx + '/supply/product/validProductManager';
							}, TIME);
						} else {
							zcal({
	                            type: 'error',
	                            title: '系统提示',
	                            text: data.resultData.msg
	                        });
						}
					}
				});
			});
		},
		generateProductPropertyHtml: function (productCategoryItemKeys) {
            var obj = {
                list: productCategoryItemKeys,
                generateNumber : ProductObject.generateNumber,
                splitCharacter: ProductObject.splitCharacter
            };
            $('#productPropertiesContainer').html($(baiduTemplate('propertiesTemplate', obj)));
        },
        generateNumber : function() {
			if (ProductObject.number) {
				return ++ProductObject.number;
			} else {
				return (ProductObject.number = 1);
			}
		},
        // 初始化页面
        init: function () {
            $.ajax({
            	url: ctx + '/sysmgr/product/viewProductDetailForSupplier',
                data : {productId : $('#productSKUId').val(), cateId : $('#cateId').val()},
                success: function (data) {
                	var supplierOrg = data.supplierOrg, brand = data.brand,
                    thirdProductCategory = data.category, secondProductCategory = thirdProductCategory.parentCategory,
                    firstProductCategory = secondProductCategory.parentCategory,
                    supplierName = supplierOrg.orgName, productId = data.productId,
                    productPrices = data.productPrices;
	                $.ajax({
	                	url : ctx + '/supply/product/getPriceByProductSKUId',
	                	data : {productSKUId : $('#productSKUId').val()},
	                	success : function(data) {
	                		var price = data.resultData;
                            // ES_PRODUCT_SKU的标价
                            var length = productPrices.length, i = 0;
                            for (; i < length; i++) {
                                var productPrice = productPrices[i], priceKindModel = productPrice.priceKindModel;
                                if (priceKindModel && priceKindModel.id == PRICE_ID) {
                                    $('#recommendedPrice').val(new Number(productPrice.actuallyPrice).toFixed(2));
                                    standardPrice = new Number(productPrice.actuallyPrice).toFixed(2);
                                }
                            }
                            // 如果有已经通过的审批单，就取审批单里面对应商品的建议售价
                            if (price) {
                                $('#recommendedPrice').val(new Number(price).toFixed(2));
                            }
	                	}
	                });
	                $('#productSKUId').val(productId);
	                $('#productCode').val(data.productCode);
	                $('#productName').val(utils.HTMLDecode(data.productName));
	                $('#minOrderCount').val(data.minOrderCount);
	                $('#supplierName').val(utils.HTMLDecode(supplierName));
	                $('#brandName').val(brand.brandZHName);
	                // 设置*级品类
	                var $firstProductCategory = $('#firstLevelCategory'), $secondProductCategory = $('#secondLevelCategory'),
	                    $thirdProductCategory = $('#thirdLevelCategory');
	                $firstProductCategory.html(firstProductCategory.cateName);
	                $secondProductCategory.html(secondProductCategory.cateName);
	                $thirdProductCategory.html(thirdProductCategory.cateName);
	                $('#number').val(data.productNumber);
	                $('#description').val(data.productDesc);

                    var priceLength = productPrices.length, i = 0;
                    for(; i < priceLength; i++) {
                    	var productPrice = productPrices[i], priceKindModel = productPrice.priceKindModel;
                    	if(priceKindModel && priceKindModel.id == SELF_PRICE_ID) {
                    		$('#costPrice').val(new Number(productPrice.actuallyPrice).toFixed(2));
                    		break;
                    	}
                    }
                    
	                // 回显图片
	                $.fn.image.init('productImage', 14, {readonly : true}, {relationId: productId, relationType: 14});
	                $.fn.image.init('productImageDetail', 15, {readonly : true}, {relationId: productId, relationType: 15});
	                // 显示商品属性#productCategoryItemValues
	                ProductObject.generateProductPropertyHtml(data.productCategoryItemValues);
	                $('#feeRemark').val(data.feeRemark);
	                $('#feeLogistics').val(data.feeLogistics);
	                $('input[name="unit"]').each(function () {
	                    $(this).val(data.unit);
	                });
	                data.hasTax == 1 ? $('#hasTax').closest('label').addClass('checked') : $('#hasTax').closest('label').removeClass('checked');
	                data.hasTransportation == 1 ? $('#hasTransportation').closest('label').addClass('checked') : $('#hasTransportation').closest('label').removeClass('checked')
                    $('#saveOrUpdateProduct').bind('click', function() {
                    	$('#commentButton').click();
                    	$('#comment').val('');
                    });
                    ProductObject.bindSubmitForm();
                }
            });
        },
        splitCharacter : ','
    },
    TIME = 3000,
    // 标价
    PRICE_ID = 1,
 // 成本价
    SELF_PRICE_ID = 2,
    standardPrice = null,
    comment = null;

$.ajaxSetup({
    error: function () {
        zcal({
            type: 'warning',
            title: '错误提示',
            text: '服务器异常'
        });
    },
    dataType: 'json'
});