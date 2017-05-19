$(function () {
    getRecord();
    getProductDetail();
    getProductPrice();
    priceFocusout();
    $('#submitNoPass').click(function (e) {
        e.preventDefault();
        operaterNoPass();
    });
    $('#submitPass').click(function (e) {
        e.preventDefault();
        operaterPass();
    });
});
function operaterNoPass() {
    if (!recordValidate.saveRecordValidate().form()) {
        var validate = recordValidate.saveRecordValidate();
        scrollTop({
            distance: '#' + utils.getFirstErrorLabelId(validate),
            customTop: 110,
            time: 200,
            windowScroll: false
        });
        return;
    }
    var comment = $.trim($('#comment').val());
    var standard = utils.toFixed($("#standard").val());
    var hasTax = 0;
    if ($("#hasTaxClass").hasClass('checked')) {
        hasTax = 1;
    }
    var hasTransportation = 0;
    if ($("#hasTransportationClass").hasClass('checked')) {
        hasTransportation = 1;
    }
    $.ajax({
        url: ctx + '/sysmgr/approvals/director/' + $('#auditBillId').val() + '/waitingApprovalNoPass',
        data: {
            auditBillId: $('#auditBillId').val(),
            comment: comment,
            standard: standard,
            hasTax: hasTax,
            hasTransportation: hasTransportation,
            t: new Date().valueOf()
        },
        type: 'post',
        async: true,
        dataType: 'text',
        success: function (data) {
            zcal({
                type: "success",
                title: "审批已退回品类管理员，等待调整"
            });
            setTimeout(function () {
                window.location.href = ctx + '/sysmgr/approvals/director/waitingApproval?t=' + new Date().valueOf();
            }, 2000);
        },
        error: function () {
            zcal({
                type: "error",
                title: "加载数据失败"
            });
        }
    });
}
function operaterPass() {
    if (!recordValidate.saveRecordValidate().form()) {
        var validate = recordValidate.saveRecordValidate();
        scrollTop({
            distance: '#' + utils.getFirstErrorLabelId(validate),
            customTop: 110,
            time: 200,
            windowScroll: false
        });
        return;
    }
    var comment = $.trim($('#comment').val());
    var standard = utils.toFixed($("#standard").val());
    var hasTax = 0;
    if ($("#hasTaxClass").hasClass('checked')) {
        hasTax = 1;
    }
    var hasTransportation = 0;
    if ($("#hasTransportationClass").hasClass('checked')) {
        hasTransportation = 1;
    }
    $.ajax({
        url: ctx + '/sysmgr/approvals/director/' + $('#auditBillId').val() + '/waitingApprovalpass',
        data: {
            comment: comment,
            standard: standard,
            hasTax: hasTax,
            hasTransportation: hasTransportation,
            t: new Date().valueOf()
        },
        type: 'post',
        async: true,
        dataType: 'text',
        success: function (data) {
            if ($('#operationType').val() == $("#OFF_SHELVE").val()) {
                zcal({
                    type: "success",
                    title: "商品下架成功！"
                });
            } else {
                zcal({
                    type: "success",
                    title: "商品上架成功！"
                });
            }

            setTimeout(function () {
                window.location.href = ctx + '/sysmgr/approvals/director/waitingApproval?t=' + new Date().valueOf();
            }, 2000);
        },
        error: function () {
            zcal({
                type: "error",
                title: "加载数据失败"
            });
        }
    });
}
function getRecord() {
    $.ajax({
        url: ctx + '/sysmgr/approvals/director/' + $('#auditBillId').val() + '/cateDirectorRecord',
        data: {
            t: new Date().valueOf()
        },
        type: 'post',
        async: true,
        dataType: 'text',
        success: function (data) {
            var item = [];
            item.recordList = JSON.parse(data);
            showRecordTemplate("approvalRecord", item);
        },
        error: function () {
            zcal({
                type: "error",
                title: "加载数据失败"
            });
        }
    });
}
function getProductDetail() {
    $.ajax({
        url: ctx + '/sysmgr/approvals/director/' + $('#auditBillId').val() + '/cateDirectorProductInfo',
        data: {
            t: new Date().valueOf()
        },
        type: 'post',
        success: function (result) {
            var productDetail = {
                cost: '',
                productName: result.productName,
                standard: result.standard ? utils.toFixed(result.standard) : '',
                productCode: result.productCode,
                minOrderCount: result.minOrderCount,
                recommendedPrice: (result.recommend != null && result.recommend != undefined) ? utils.toFixed(result.recommend) : '',
                hasTax: result.hasTax,
                hasTransportation: result.hasTransportation,
                productNumber: result.productNumber,
                images: result.images,
                unit: result.unit && result.unit != '' ? '/' + result.unit : ''
            }
            if (result.standard) {
                productDetail.priceKindId = 1;
            } else if (result.recommendedPrice) {
                productDetail.priceKindId = 6;
            }
            showBlurbTemplate("approvalBlurb", productDetail);
        },
        error: function () {
            zcal({
                type: "error",
                title: "加载数据失败"
            });
        }
    });
}

function getProductPrice() {
    $.ajax({
        url: ctx + '/sysmgr/approvals/director/' + $('#auditBillId').val() + '/waitingApprovalProductPrice',
        data: {
            t: new Date().valueOf()
        },
        type: 'post',
        async: true,
        dataType: 'text',
        success: function (data) {
            var item = JSON.parse(data);
            showExamineTemplate('approvalExamining', item);
            priceFocusout();
        },
        error: function () {
            zcal({
                type: "error",
                title: "加载数据失败"
            });
        }
    });
}

var recordValidate = function () {
};
recordValidate.saveRecordValidate = function () {
    var validate = $("#recordForm").validate({
        errorElement: 'label', // default
        errorClass: 'error', // default
        focusInvalid: false,
        onkeyup: function (element) {
            $(element).valid();
        },
        onfocusout: function (element) {
            $(element).valid();
        },
        rules: {
            standard: {
                required: true,
                number: true,
                min: Number($('#cost').text())
            },
            comment: {
                required: true,
                minlength: 2,
                maxlength: 255
            }
        },
        messages: {
            standard: {
                required: '[标价]必填',
                number: '[标价]为数字',
                min: '[标价]不能小于成本价'
            },
            comment: {
                required: '[审批备注]必填',
                minlength: '[审批备注]最小长度为{2}',
                maxlength: '[审批备注]最小长度为{255}'
            }
        }
    });
    return validate;
};

function priceFocusout() {
    $('#standard').focusout(function () {
        $(this).val(utils.toFixed($(this).val()));
    });
}