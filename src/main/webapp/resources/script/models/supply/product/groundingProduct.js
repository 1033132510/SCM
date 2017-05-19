$(function () {
    ProductObject.init();
});
var baiduTemplate = baidu.template,
    ProductObject = {
        bindContinueAddProductProperty: function () {
            $('#continueAdd').bind('click', function () {
                var $productPropertiesContainer = $('#productPropertiesContainer'), number = ProductObject.generateNumber(),
                    html = '<tr class="' + CUSTOM_TR + '">'
                        + '<td><div class="form-group form-inline text-center"><input type="text" name="propertyName" id="property_' + number + '" class="propertyName form-control"/></div></td>'
                        + '<td class="propertyTd"><div class="form-group pull-left"><input type="text" id="customInput_' + number + '" name="customInputRequire" class="customInput form-control"/></div></td>'
                        + '<td><div class="form-group"><input type="text" class="orderInput form-control sm-form-control text-center" value="' + ProductObject.generateOrder() + '" name="orderInput" id="order_' + number + '"/></div></td>'
                        + '<td><a class="red cancel">删除</a></td>'
                        + '</tr>';
                $productPropertiesContainer.append(html);
                ProductObject.bindClickForRemoveTr();
            });
        },
        generateOrder: function () {
            var $orderInputs = $('input[name="orderInput"]'), length = $orderInputs.length,
                i = 0, tempMaxOrder = 0;
            for (; i < length; i++) {
                var $orderInput = $($orderInputs[i]), order = $.trim($orderInput.val());
                if (!isNaN(order) && order) {
                    order = parseInt(order);
                    tempMaxOrder = Math.max(order, tempMaxOrder);
                }
            }
            return ++tempMaxOrder;
        },
        generateProductPropertyEditHtml: function (productCategoryItemKeys) {
            var obj = {
                list: productCategoryItemKeys,
                generateNumber: ProductObject.generateNumber,
                splitCharacter: ProductObject.splitCharacter
            };
            $('#productPropertiesContainer').html($(baiduTemplate('propertiesTemplate', obj)));
            ProductObject.bindClickForRemoveTr();
        },
        bindClickForRemoveTr: function () {
            $('#productPropertiesContainer .cancel').unbind().bind('click', function () {
                $(this).closest('tr').remove();
            });
        },
        bindCostPriceBlur : function() {
        	$('#costPrice,#recommendedPrice').bind('blur', function() {
        		var $costPrice = $(this), value = $.trim($costPrice.val());
        		if(value) {
        			$costPrice.val(new Number(value).toFixed(2));
        		}
        	});
        },
        // 生成唯一值
        generateNumber: function () {
            if (ProductObject.number) {
                return ++ProductObject.number;
            } else {
                return (ProductObject.number = 1);
            }
        },
        // 单位失去焦点事件,同步两个单位内容
        unitFocusout: function () {
            $('input[name="unit"]').focusout(function () {
                $('input[name="unit"]').eq(1).val($(this).val());
            });
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
                $('#supplierOrgId').val(supplierOrg.id);
                $('#brandId').val(brand.id);
                $('#id').val(productId);
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
                // 回显图片
                $.fn.image.init('productImage', 14, {
                    uploadCount: MAX_PRODUCT_IMAGE,
                    multiple: true
                }, {relationId: productId, relationType: 14});
                $.fn.image.init('productImageDetail', 15, {
                    uploadCount: MAX_PRODUCT_DETAIL_IMAGE,
                    multiple: true
                }, {relationId: productId, relationType: 15});

                var priceLength = productPrices.length, i = 0;
                for(; i < priceLength; i++) {
                	var productPrice = productPrices[i], priceKindModel = productPrice.priceKindModel;
                	if(priceKindModel && priceKindModel.id == SELF_PRICE_ID) {
                		$('#costPrice').val(new Number(productPrice.actuallyPrice).toFixed(2));
                		break;
                	}
                }
                
                // 显示商品属性#productCategoryItemValues
                ProductObject.generateProductPropertyEditHtml(data.productCategoryItemValues);
                $('#feeRemark').val(data.feeRemark);
                $('#feeLogistics').val(data.feeLogistics);
                $('input[name="unit"]').each(function () {
                    $(this).val(data.unit);
                });
                data.hasTax == 1 ? $('#hasTax').closest('label').addClass('checked') : $('#hasTax').closest('label').removeClass('checked');
                data.hasTransportation == 1 ? $('#hasTransportation').closest('label').addClass('checked') : $('#hasTransportation').closest('label').removeClass('checked')
                }
            });
            ProductObject.bindContinueAddProductProperty();
            ProductObject.validateProductForm();
            ProductObject.unitFocusout();
            ProductObject.bindCostPriceBlur();
            $('#submitForm').bind('click', function() {
            	comment = $.trim($('#comment').val());
            	if(comment) {
            		$('#closeCommentModal').click();
            		ProductObject.updateProduct();
            	} else {
            		zcal({
                        type: 'warning',
                        title: '错误提示',
                        text: '请填写备注信息'
                    });
            	}
            });
        },
        // 校验商品属性table
        validatePropertyTable: function () {
            var $trs = $('#productPropertiesContainer').find('tr'), length, i = 0, flag = true;
            if ((length = $trs.length)) {
                for (; i < length; i++) {
                    var $tr = $($trs[i]), $td = $tr.find('.notNullTd');
                    // 必须是.notNullTd
                    if ($td.length) {
                        var $inputs = $td.find('.checked .system'), $customInput = $td.find('.customInput'),
                            $div = $td.find('.form-group'), tdId = $td.attr('id'),
                        // systemLength .checked .system长度
                            $errorLabel = $div.find('label.error'), systemLength = $inputs.length,
                        // customValue 自定义属性值 customLength 自定义属性框长度
                            customValue = $.trim($customInput.val()), customLength = $td.find('.checked .custom').length;
                        if (systemLength || (customValue && customLength) || (!$td.find('.system').length && customValue)) {
                            $td.removeClass('error');
                            $errorLabel.remove();
                        } else {
                            flag = false;
                            // 如果上一次验证已经添加了label.error 就不用添加了
                            if (!$errorLabel.length) {
                                $td.addClass('error');
                                $td.append('<label class="error" for="' + tdId + '">商品属性不能为空</label>');
                            }
                        }
                    }
                }
            }
            // 如果用户没有设置系统属性，不能添加商品
            if (!length) {
                zcal({
                    type: 'warning',
                    title: '错误提示',
                    text: '请设置商品属性'
                });
                flag = false;
            }
            if (!flag) {
                // 如果#productPropertiesContainer校验不通过，需要设置location.hash
                // 通过切换location.hash来定位页面，否则页面只在第一次定位
                var hash = location.hash;
                if (!hash || hash == '#hashProductProperty1') {
                    location.hash = '#hashProductProperty2';
                } else {
                    location.hash = '#hashProductProperty1';
                }
            }

            // 校验图片
            // 商品图片，如果个数为0或>MAX_PRODUCT_IMAGE，提示错误
            var productImageLength = $('#productImage').find('img[id^="img_14_"]').length;
            if (!productImageLength || productImageLength > MAX_PRODUCT_IMAGE) {
                flag = false;
                var hash = location.hash;
                if (!hash || hash == '#hashProductImage1') {
                    location.hash = '#hashProductImage2';
                } else {
                    location.hash = '#hashProductImage1';
                }
                zcal({
                    type: 'warning',
                    title: '错误提示',
                    text: productImageLength ? '最多上传' + MAX_PRODUCT_IMAGE + '张商品图片' : '请至少上传一张商品图片'
                });
            }

            // 商品详情图片，如果个数为0或>productDetailImageLength，提示错误
            var productDetailImageLength = $('#productImageDetail').find('img[id^="img_15_"]').length;
            if (!productDetailImageLength || productDetailImageLength > MAX_PRODUCT_DETAIL_IMAGE) {
                flag = false;
                var hash = location.hash;
                if (!hash || hash == '#hashProductImageDetail1') {
                    location.hash = '#hashProductImageDetail2';
                } else {
                    location.hash = '#hashProductImageDetail1';
                }
                zcal({
                    type: 'warning',
                    title: '错误提示',
                    text: productDetailImageLength ? '最多上传' + MAX_PRODUCT_DETAIL_IMAGE + '张商品详情图片' : '请至少上传一张商品详情图片'
                });
            }
            return flag;
        },
        // 校验表单
        validateProductForm: function () {
            // 如果是编辑商品，不需要校验#productCategoryContainer select
            var opt = {
                submitHandler: function (form) {
                    if (ProductObject.validatePropertyTable()) {
                    	$('#commentButton').click();
                    	$('#comment').val('');
                    }
                    return false;
                }
            }, rules = {
                productName: {
                    required: true,
                    maxlength: 60
                },
                number: {
                    required: true,
                    min: 1,
                    number: true
                },
                minOrderCount : {
                	required : true,
                	min : 1,
                	number : true
                },
                description: {
                    maxlength: 2000
                },
                propertyName: {
                    required: true
                },
                customInputRequire: {
                    required: true
                },
                orderInput: {
                    digits: true
                },
                costPrice: {
                    required: true,
                    min: 0,
                    number: true
                },
                recommendedPrice: {
                    required: true,
                    min: 0,
                    number: true
                },
                unit: {
                    required: true
                },
                feeRemark: {
                    maxlength: 255
                },
                feeLogistics: {
                    maxlength: 255
                }
            }, messages = {
                productName: {
                    required: '请输入商品名称',
                    maxlength: '商品名称最多输入{0}个字符'
                },
                number: {
                    required: '库存数量必填',
                    min: '库存数量至少为{0}',
                    number: '库存数量只能是数字'
                },
                minOrderCount : {
                	required : '最小起订量必填',
                	min : '最小起订量至少为{0}',
                	number : '最小起订量只能是数字'
                },
                description: {
                    maxlength: '请输入商品简介描述信息，用于商品和公司页面展示，字数不超过{0}'
                },
                propertyName: {
                    required: '属性名称必填'
                },
                customInputRequire: {
                    required: '自定义属性值必填'
                },
                orderInput: {
                    digits: '排序字段只能是整数'
                },
                costPrice: {
                    required: '成本价必填',
                    min: '成本价至少为0',
                    number: '成本价只能是数字'
                },
                recommendedPrice: {
                    required: '建议售价必填',
                    min: '建议售价至少为0',
                    number: '建议售价只能是数字'
                },
                unit: {
                    required: '单位必填'
                },
                feeRemark: {
                    maxlength: '最多输入255个字符'
                },
                feeLogistics: {
                    maxlength: '最多输入255个字符'
                }
            };
            opt.rules = rules;
            opt.messages = messages;
            $('#productForm').validate(opt);
        },
        updateProduct: function () {
            ProductDetail.removeForm();
            var productDetail = ProductDetail.getProductDetailAndCommentForUpdate();
            productDetail.type = SHELVE_TYPE;
            productDetail.productSKUId = $('#productSKUId').val();
            productDetail.secondLevelCateId = $('#parentCateId').val();
            productDetail.comment = $.trim($('#comment').val());
            productDetail.standard = standardPrice;
            $.ajax({
                url: ctx + '/supply/product/updateGroundingProduct',
                data: {groundingProduct: JSON.stringify(productDetail)},
                type: 'post',
                success: function (data) {
                	if (data.success) {
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
                        $('#productForm').removeClass('disabled');
                    }
                },
                error: function () {
                    $('#productForm').removeClass('disabled');
                }
            });
        },
        // 属性itemSources的分隔符
        splitCharacter: ','
    },
// 自定义属性tr
    CUSTOM_TR = 'customTr',
// 0:商品(属性)
    PRODUCT_TYPE = 0,
// 1:商品类别(属性)
    CATEGORY_TYPE = 1,
// 有效
    VALID_STATUS = 1,
// TIME后跳转
    TIME = 3000,
// 最大商品图片个数
    MAX_PRODUCT_IMAGE = 15,
// 最大商品详细图片个数
    MAX_PRODUCT_DETAIL_IMAGE = 15,
// 上架操作
    SHELVE_TYPE = 3,
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