var carts = [];
var bt = baidu.template;
bt.ESCAPE = false;
var templateId = 'cartTpl';
var renderId = 'carts';
var paginationId = 'pager';
var pageNumber = 1;
var pageSize = 10;
var RMB = '￥';
var url = ctx + '/shop/cart';
$(function () {
    cart.init();
});
var cart = {
    init: function () {
        var pagination = initData(pageNumber);
        if (pagination.totalPages > 0) {
            initPagination(pagination);
            this.initEvent();
        }
    },
    initEvent: function () {
        this.initEverySelectSingleEvent();
        this.initEverySelectAllEvent();
        this.initDatetimePicker();
        echoCheckbox();
    },
    initEverySelectSingleEvent: function () {
        if ($('input[id^="chb_"]').length >= 1) {
            $('input[id^="chb_"]').each(function (index, item) {
                $(this).closest('div').off().on('click', function (e) {
                    e.preventDefault ? e.preventDefault() : (e.returnValue = false);
                    var cartTemp = getRowRecord(getIndex($(this)));
                    var operation = $(this).hasClass('checked');
                    if (operation) { // 取消选中
                        carts.remove(cartTemp);
                        $(this).removeClass('checked');
                    } else { // 选中
                        carts.push(cartTemp);
                        $(this).addClass('checked');
                    }
                    setCartQuantityAndTotalPrice(carts);
                    setCheckAllStatus();
                });
            });
        }
    },
    initEverySelectAllEvent: function () {
        var ifAllIsDisabled = getAllRecordCount() - getAllDisabledProductRecordCount();
        if (ifAllIsDisabled > 0) {
            $('.select-all').off().on('click', function (e) {
                e.preventDefault ? e.preventDefault() : (e.returnValue = false);
                var isChecked = $(this).hasClass('checked');
                if (isChecked) {
                    $(this).removeClass('checked');
                } else {
                    $(this).addClass('checked');
                }
                $('.select-single').each(function () {
                    var cartTemp = getRowRecord($(this).attr('id').substring($(this).attr('id').lastIndexOf('_') + 1));
                    if (isChecked) {
                        carts.remove(cartTemp);
                        $(this).removeClass('checked');
                    } else {
                        if (carts.indexOf(cartTemp) == -1) {
                            carts.push(cartTemp);
                        }
                        $(this).addClass('checked');
                    }
                    setCartQuantityAndTotalPrice(carts);
                    setCheckAllStatus();
                });
            });
        } else if (ifAllIsDisabled == 0) {
            $('.select-all').off();
        }
    },
    initDatetimePicker: function () {
        $('#demandDate').datetimepicker({
            language: 'zh-CN',
            format: 'yyyy-mm-dd',
            formatDate: '"yyyy-mm-dd',
            startDate: new Date(),
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        });
    },
    reduceQuantity: function (e, obj) {
        e.preventDefault ? e.preventDefault() : (e.returnValue = false);
        var index = getIndex($(obj));
        var quantity = getQuantity(index) == 1 ? 1 : getQuantity(index) - 1;
        setRecordPrice(quantity, index);
        setCartQuantityAndTotalPrice(index, quantity, $('#chb_div_' + index).hasClass('checked'));
    },
    addQuantity: function (e, obj) {
        e.preventDefault ? e.preventDefault() : (e.returnValue = false);
        var index = getIndex($(obj));
        var quantity = getQuantity(index) + 1;
        setRecordPrice(quantity, index);
        setCartQuantityAndTotalPrice(index, quantity, $('#chb_div_' + index).hasClass('checked'));
    },
    inputChange: function (obj) {
        var quantity = this.returnLegalQuantity($(obj).val());
        var index = getIndex($(obj));
        $(obj).val(quantity)
        setRecordPrice(quantity, index);
        setCartQuantityAndTotalPrice(index, quantity, $('#chb_div_' + index).hasClass('checked'));
    },
    returnLegalQuantity: function (quantity) {
        if (isNaN(quantity) || !quantity) {
            quantity = 1;
        }
        return Math.max(1, parseInt(quantity));
    },
    openModal: function (e) {
        e.preventDefault ? e.preventDefault() : (e.returnValue = false);
        if (carts.length == 0) {
            zcal({
                type: 'error',
                title: '请选择产品',
            });
            return;
        }
        $('#projectName').val('');
        $('#demandDate').val('');
        $('#projectDescription').val('');
        modalOpen('#createOrderModal');
    },
    createOrder: function (e) {
        if (!validateFormEle().form()) {
            return false;
        }
        e.preventDefault ? e.preventDefault() : (e.returnValue = false);
        var intentionOrder = {
            projectName: $('#projectName').val(),
            projectDescription: $('#projectDescription').val(),
            demandDate: $('#demandDate').val()
        }
        var cartIds = this.getToDeleteCartIds(carts);
        var intentionOrderItems = {
            intentionOrderItems: this.removeIdAttr(carts)
        }
        $.ajax({
            url: ctx + '/shop/intentionOrder',
            data: {
                cartIds: JSON.stringify(cartIds),
                intentionOrderJson: JSON.stringify(intentionOrder),
                intentionOrderItemsJson: JSON.stringify(carts)
            },
            dataType: 'json',
            type: 'POST',
            success: function (result) {
                if (result && result.success) {
                    window.location.href = ctx + '/shop/intentionOrder/view';
                } else {
                    zcal({
                        type: 'error',
                        title: result.description
                    });
                }
            },
            error: function () {
                zcal({
                    type: 'error',
                    title: '服务器异常'
                });
            }
        });
    },
    getToDeleteCartIds: function (carts) {
        var cartIds = [];
        for (var i = 0; i < carts.length; i++) {
            cartIds.push(carts[i].id);
            delete carts[i].id;
        }
        return cartIds;
    },
    removeIdAttr: function (carts) {
        var that = carts;
        for (var i = 0; i < that.length; i++) {
            delete that[i].id;
        }
        return that;
    },
    closeModal: function (e) {
        e.preventDefault ? e.preventDefault() : (e.returnValue = false);
        $('#projectName').val('');
        $('#demandDate').val('');
        $('#projectDescription').val('');
        modalClose('#createOrderModal');
    },
    delCart: function (e, obj, id) {
        e.preventDefault ? e.preventDefault() : (e.returnValue = false);
        zcal({
            type: 'confirm',
            title: '您确认删除商品吗？'
        }, function () {
            $.ajax({
                url: ctx + '/shop/cart/' + id,
                type: 'GET',
                success: function (result) {
                    if (result) {
                        var index = getIndex($(obj));
                        var delRecord = getRowRecord(index);
                        if ($('#chb_div_' + index).hasClass('checked') && carts.indexOf(delRecord) > -1) {
                            carts.remove(delRecord);
                            setCartQuantityAndTotalPrice(-1, -1, false);
                        }
                        $('#tr_' + index).remove();
                        setCheckAllStatus();
                        if (getAllRecordCount() == 0) {
                            $('#' + renderId).html('<tr><td colspan="5">购物车空空的哦~，去看看心仪的商品吧~</td></tr>');
                            $('#' + paginationId).remove();
                        }
                    }
                }
            });
        });
    },
    showProductDetail: function (e, productSkuId, productCategoryId) {
        e.preventDefault ? e.preventDefault() : (e.returnValue = false);
        window.open(ctx + '/shop/product/gotoView?productId=' + productSkuId + '&cateId=' + productCategoryId + '&t=' + new Date().valueOf());
    }
}

function initPagination(pagination) {
    PageClick = function (pageclickednumber) {
        pagination = initData(pageclickednumber);
        cart.initEvent();
        $('#' + paginationId).pager({
            pagenumber: pageclickednumber,
            pagecount: pagination.totalPages,
            buttonClickCallback: PageClick
        });
    };
    $('#' + paginationId).pager({
        pagenumber: pagination.pageNumber,
        pagecount: pagination.totalPages,
        buttonClickCallback: PageClick
    });
}

function initData(pageNumber) {
    var pagination = {
        pageNumber: pageNumber,
        totalPages: 0
    }
    $.ajax({
        url: url,
        type: 'GET',
        data: {p: pageNumber, ps: pageSize},
        async: false,
        success: function (page) {
            if (page.totalCount > 0) {
                var htmlCart = '';
                for (var i = 0; i < page.result.length; i++) {
                    page.result[i].index = i;
                    page.result[i].price = utils.toFixed(page.result[i].price);
                    page.result[i].totalPrice = utils.toFixed(page.result[i].totalPrice);
                    htmlCart += bt(templateId, page.result[i]);
                }
                pagination.totalPages = page.totalPages;
                $('#' + renderId).html(htmlCart);
            } else {
                pagination.totalPages = 0;
                $('#' + renderId).html('<tr><td colspan="5">购物车空空的哦~，去看看心仪的商品吧~</td></tr>');
            }
        },
        error: function () {
            zcal({
                type: "error",
                title: "加载数据失败"
            });
        }
    });
    return pagination;
}

Array.prototype.indexOf = function (cart) {
    for (var i = 0; i < this.length; i++) {
        if (cart.productSKUId == this[i].productSKUId && cart.productCategoryId == this[i].productCategoryId) {
            return i;
        }
    }
    return -1;
};

Array.prototype.remove = function (obj) {
    var productSKUId;
    var productCategoryId;
    for (var key in obj) {
        if (key == 'productSKUId') {
            productSKUId = obj[key];
        }
        if (key == 'productCategoryId') {
            productCategoryId = obj[key];
        }
    }
    var index = -1;
    for (var i = 0; i < this.length; i++) {
        if (productSKUId && productCategoryId) {
            if (this[i].productSKUId == productSKUId && this[i].productCategoryId == productCategoryId) {
                index = i;
                break;
            }
        }
    }
    if (index > -1) {
        this.splice(index, 1);
    }
};

function validateFormEle() {
    var validate = $("#projectInfo").validate({
        errorElement: 'label',
        errorClass: 'error',
        focusInvalid: false,
        onkeyup: function (element) {
            $(element).valid();
        },
        onfocusout: function (element) {
            $(element).valid();
        },
        rules: {
            projectName: {
                required: true
            },
            demandDate: {
                required: true
            }
        },
        messages: {
            projectName: {
                required: '项目名称不能为空'
            },
            demandDate: {
                required: '预计需求期不能为空'
            }
        }
    });
    return validate;
};

function setCartQuantityAndTotalPrice(index, quantity, ifChangeQuantity) {
    if (ifChangeQuantity) {
        changeQuantityForCarsArray(index, quantity);
    }
    $('#cartQuantity').html(carts.length);
    var totalPrice = 0;
    for (var i = 0; i < carts.length; i++) {
        totalPrice += (carts[i].price * 10000 * carts[i].quantity / 10000);
    }
    $('#totalPrice').html(utils.toFixed(totalPrice));
}

function changeQuantityForCarsArray(index, quantity) {
    var cartTemp = {
        productSKUId: $('#productSKUId_' + index).val(),
        productCategoryId: $('#productCateId_' + index).val()
    };
    var cartIndexInArray = carts.indexOf(cartTemp);
    if (cartIndexInArray >= 0) {
        var cart = carts[cartIndexInArray];
        cart.quantity = quantity;
    }
}

function getRowRecord(index) {
    var cartTemp = {};
    cartTemp.productSKUId = $('#productSKUId_' + index).val();
    cartTemp.productCategoryId = $('#productCateId_' + index).val();
    cartTemp.quantity = $('#quantity_' + index).val();
    cartTemp.price = $('#price_' + index).text();
    cartTemp.id = $('#id_' + index).val();
    return cartTemp;
}

function getAllRecordCount() {
    return $('#' + renderId).find('tr').length;
}

function getAllDisabledProductRecordCount() {
    return $('#' + renderId).find('tr[class="gray_0"]').length;
}

function getIndex(obj) {
    var operateId = obj.attr('id');
    var index = operateId.substring(operateId.lastIndexOf('_') + 1);
    return index;
}

function getQuantity(index) {
    return parseInt($('#quantity_' + index).val());
}

// 一条购物车记录的总价格
function setRecordPrice(quantity, index) {
    $('#quantity_' + index).val(quantity);
    var price = utils.toFixed($('#price_' + index).html());
    var totalPrice = utils.toFixed((price * 10000 * quantity / 10000));
    $('#recordPrice_' + index).html(RMB + totalPrice);
    setRealQuantity(quantity, index);
}

function setRealQuantity(quantity, index) {
    $.ajax({
        url: ctx + '/shop/cart/updateQuantity',
        type: 'POST',
        data: {id: $('#id_' + index).val(), quantity: quantity},
        dataType: 'json',
        success: function (result) {

        }
    });
}

// 设置全选checkbox状态
function setCheckAllStatus() {
    var recordCount = getAllRecordCount() - getAllDisabledProductRecordCount();
    var checkedChb = 0;
    $('div[id^="chb_div_"]').each(function () {
        if ($(this).hasClass('checked')) {
            checkedChb += 1;
        }
    });
    if (recordCount == 0 || checkedChb == 0 || checkedChb < recordCount) {
        $('.select-all').each(function () {
            $(this).removeClass('checked');
        });
    } else {
        $('.select-all').each(function () {
            $(this).addClass('checked');
        });
    }
}

// 回显checkbox
function echoCheckbox() {
    $('#' + renderId).find('tr').each(function () {
        var inputHidden = $(this).find('input:hidden');
        var productSKUId = inputHidden.eq(1).val();
        var productCateId = inputHidden.eq(2).val();
        var index = getIndex(inputHidden.eq(1));
        var cartTemp = {
            productSKUId: productSKUId,
            productCategoryId: productCateId
        };
        if (carts.indexOf(cartTemp) > -1) {
            $('#chb_div_' + index).addClass('checked');
        }
        setCheckAllStatus();
    });
}

