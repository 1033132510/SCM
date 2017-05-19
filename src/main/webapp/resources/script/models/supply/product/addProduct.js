$(function () {
    ProductObject.init();
    $("#saveProduct").click(function () {
        ProductObject.saveProduct();
    });
});

var baiduTemplate = baidu.template,
    ProductObject = {
        bindContinueAddProductProperty: function () {
            $('#continueAdd').bind('click', function () {
                if ($(this).hasClass('no-drop')) {
                    return false;
                }
                var $productPropertiesContainer = $('#productPropertiesContainer'), number = ProductObject.generateNumber();
                if (!$productPropertiesContainer.hasClass('hide')) {
                    var html = '<tr class="' + CUSTOM_TR + '">'
                        + '<td><div class="form-group form-inline text-center"><input type="text" name="propertyName" id="property_' + number + '" class="propertyName form-control"/></div></td>'
                        + '<td class="propertyTd"><div class="form-group pull-left"><input type="text" id="customInput_' + number + '" name="customInputRequire" class="customInput form-control"/></div></td>'
                        + '<td><div class="form-group"><input type="text" class="orderInput form-control sm-form-control text-center" value="' + ProductObject.generateOrder() + '" name="orderInput" id="order_' + number + '"/></div></td>'
                        + '<td><a class="red cancel">删除</a></td>'
                        + '</tr>';
                    $productPropertiesContainer.append(html);
                    ProductObject.bindClickForRemoveTr();
                }
            });
        },
        // 绑定#productCategoryContainer select,#brandSelectList的change事件
        bindSelectsChange: function () {
            $('#productCategoryContainer select,#brandSelectList').bind('change', function () {
                var $this = $(this), id = $this.attr('id');
                if ($this.val()) {
                    $('#' + id + '-error').remove();
                }
            });
        },
        // 绑定#sureChooseProductCategory点击事件，显示三级品类信息
        bindSureChooseProductCategory: function () {
            $('#sureChooseProductCategory').bind('click', function () {
                var id = $('#productCategoryContainer').find('select:eq(2)').val();
                // 三级品类必须选中才能显示三级品类信息
                if (id) {
                    ProductObject.getProductCategoryDetailById(id);
                    // 可以继续添加商品属性
                    $('#continueAdd').removeClass('gray-bg gray-bd no-drop');
                } else {
                    zcal({
                        type: 'warning',
                        title: '系统提示',
                        text: '请选择三级品类信息'
                    });
                    // 不能继续添加商品属性
                    $('#continueAdd').addClass('gray-bg gray-bd no-drop');
                }
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

        // 展示数据productCategoryItemKeys 系统属性
        generateProductPropertySaveHtml: function (productCategoryItemKeys) {
            if (productCategoryItemKeys && productCategoryItemKeys.length) {
                //ProductObject.getDiscount();
            } else {
                ProductObject.productPriceDiscountObj = {};
            }
            // 显示属性框
            $('#productCategoryAttr').removeClass('hide');
            var obj = {
                list: productCategoryItemKeys,
                generateNumber: ProductObject.generateNumber,
                splitCharacter: ProductObject.splitCharacter
            };
            $('#productPropertiesContainer').html($(baiduTemplate('propertiesTemplate', obj)));
        },
        bindClickForRemoveTr: function () {
            $('#productPropertiesContainer .cancel').unbind().bind('click', function () {
                $(this).closest('tr').remove();
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
        // 初始化页面
        init: function () {
            ProductObject.initProductCategorySelect();
            ProductObject.bindSureChooseProductCategory();
            $.fn.image.init('productImage', 14, {uploadCount: MAX_PRODUCT_IMAGE, multiple: true});
            $.fn.image.init('productImageDetail', 15, {uploadCount: MAX_PRODUCT_DETAIL_IMAGE, multiple: true});
            ProductObject.bindContinueAddProductProperty();
            ProductObject.validateProductForm();
            ProductObject.bindSelectsChange();
            ProductObject.unitFocusout();
            ProductObject.bindEvent();
            ProductObject.priceFocusout();
            //ProductDetail.validateAllNumber();
        },
        // 单位失去焦点事件,同步两个单位内容
        unitFocusout: function () {
            $('input[name="unit"]').focusout(function () {
                $('input[name="unit"]').eq(1).val($(this).val());
            });
        },
        priceFocusout: function () {
            $('#costPrice').focusout(function () {
                $(this).val(utils.toFixed($(this).val()));
            });
            $('#recommendedPrice').focusout(function () {
                $(this).val(utils.toFixed($(this).val()));
            });
        },
        // 通过三级商品品类id获取详细信息，并且将信息显示到
        getProductCategoryDetailById: function (id) {
            $.ajax({
                url: ctx + '/sysmgr/category/getCategoryInfoSimple/',
                data: {
                    cateId: id
                },
                success: function (data) {
                    ProductObject.generateProductPropertySaveHtml(data.categoryItems);
                }
            });
        },
        // 初始化商品类别三级联动下拉框
        initProductCategorySelect: function () {
            selectProductCategory.init({
                callback: function () {
                    $('#productCategoryContainer select').select2({
                        placeholder: '--请选择--',
                        minimumResultsForSearch: -1
                    });
                    // 如果#productCategoryContainer下任意一个select有change事件都会清空#productPropertiesContainer
                    $('#productCategoryContainer').find('select').bind('change', function () {
                        $('#productPropertiesContainer').empty();
                        $('#productCategoryAttr').addClass('hide');
                        // 没有点击确定不能继续添加属性
                        var $continueAddDiv = $('#continueAdd');
                        $continueAddDiv.addClass('gray-bg gray-bd no-drop');
                    });
                },
                container: $('#productCategoryContainer'),
                labelClasses: ['pull-left'],
                selectClasses: ['select', 'form-control'],
                doms: [{
                    label: '一级品类',
                    key: 'parentCategoryId',
                    param: {
                        level: 1,
                        status: VALID_STATUS,
                        parentCategoryId: PARENT_CATEGORY_ID
                    }
                }, {
                    label: '二级品类',
                    key: 'parentCategoryId',
                    param: {
                        level: 2,
                        status: VALID_STATUS
                    }
                }, {
                    label: '三级品类',
                    key: 'parentCategoryId',
                    param: {
                        level: 3,
                        status: VALID_STATUS
                    }
                }],
                Key: {
                    nameKey: 'cateName',
                    valueKey: 'id'
                },
                url: ctx + '/sysmgr/category/cateShowWithoutPermission/',
                type: 'post'
            });
        },
        // 校验商品属性table
        validatePropertyTable: function () {
            var $trs = $('#productPropertiesContainer').find('tr'), length,
                i = 0, flag = true;
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
            var rules = {
                productName: {
                    required: true,
                    maxlength: 60
                },
                brandSelectList: {
                    required: true
                },
                number: {
                    required: true,
                    min: 1,
                    digits: true
                },
                minOrderCount: {
                    required: true,
                    min: 1,
                    number: true
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
                brandSelectList: {
                    required: '品牌必选'
                },
                number: {
                    required: '库存数量必填',
                    min: '库存数量至少为{0}',
                    digits: '库存数量只能是整数'
                },
                minOrderCount: {
                    required: '最小起订量必填',
                    min: '最小起订量至少为{0}',
                    number: '最小起订量只能是数字'
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

            // 添加三个下拉框的校验
            if (!$('#id').val()) {
                rules.productCategoryContainer_0 = {
                    required: true
                };
                rules.productCategoryContainer_1 = {
                    required: true
                };
                rules.productCategoryContainer_2 = {
                    required: true
                };
                messages.productCategoryContainer_0 = {
                    required: '一级商品类别必选'
                };
                messages.productCategoryContainer_1 = {
                    required: '二级商品类别必选'
                };
                messages.productCategoryContainer_2 = {
                    required: '三级商品类别必选'
                };
            }
            var opt = {};
            opt.rules = rules;
            opt.messages = messages;
            return $('#productForm').validate(opt);
        },
        bindEvent: function () {
            $('#saveOrUpdateProduct').click(function () {
                var validate = ProductObject.validateProductForm();
                if (!validate.form()) {
                    scrollTop({
                        distance: '#' + utils.getFirstErrorLabelId(validate),
                        customTop: 110,
                        time: 200,
                        windowScroll: false
                    });
                    return false;
                }
                if (!ProductObject.validatePropertyTable()) {
                    return false;
                }
                modalOpen('#confirmAddProductModal');
            });
        },
        saveProduct: function () {
            ProductDetail.removeForm();
            var productDetail = ProductDetail.getProductDetailData();
            $.ajax({
                url: ctx + '/supply/product/save',
                data: {productDetail: JSON.stringify(productDetail)},
                type: 'post',
                success: function (data) {
                	console.table(data);
                    if (data.success) {
                        modalClose('#confirmAddProductModal');
                        zcal({
                            type: 'success',
                            title: '提示',
                            text: '商品提交成功！！此商品审批记录请在［审批管理－已提交商品］中查看，时刻关注商品动态。'
                        });
                        setTimeout(function () {
                            menu.delCookie('menu');
                            menu.setCookie('menu', 'supplySubmittedView');
                            location.href = ctx + '/supply/approvals/submitted';
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
    }, selectProductCategory = new Components.Select(),
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
// 一级商品类别的父id
    PARENT_CATEGORY_ID = 1;
// 最大商品图片个数
MAX_PRODUCT_IMAGE = 15,
// 最大商品详细图片个数
    MAX_PRODUCT_DETAIL_IMAGE = 15;