$(function () {
    getRecord();
    getProductDetail();
    getProductPrice();
    $('#submitButton').click(function (e) {
        window.location.href = ctx + '/sysmgr/approvals/admin/processed?t=' + new Date().valueOf();
    });
});

function getRecord() {
    $.ajax({
        url: ctx + '/sysmgr/approvals/admin/' + $('#auditBillId').val() + '/cateAdminRecord',
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
        url: ctx + '/sysmgr/approvals/admin/' + $('#auditBillId').val() + '/cateAdminProductInfo',
        data: {
            t: new Date().valueOf()
        },
        type: 'post',
        async: true,
        dataType: 'text',
        success: function (data) {
            var item = JSON.parse(data);
            var productDetail = {
                cost: item.cost ? utils.toFixed(item.cost) : '',
                productName: item.productName,
                standard: item.standard ? utils.toFixed(item.standard) : '',
                productCode: item.productCode,
                minOrderCount: item.minOrderCount,
                recommendedPrice: (item.recommend != null && item.recommend != undefined) ? utils.toFixed(item.recommend) : '',
                hasTax: item.hasTax,
                hasTransportation: item.hasTransportation,
                productNumber: item.productNumber,
                images: item.images,
                unit: item.unit && item.unit != '' ? '/' + item.unit : ''
            }
            if (item.standard) {
                productDetail.priceKindId = 1; // 标价
            } else {
                productDetail.priceKindId = 6; // 建议售价
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
        url: ctx + '/sysmgr/approvals/admin/' + $('#auditBillId').val() + '/latestRecord',
        data: {
            t: new Date().valueOf()
        },
        type: 'post',
        dataType: 'text',
        success: function (data) {
            var item = JSON.parse(data);
            if (item) {
                if (item.standard) {
                    item.standard = utils.toFixed(item.standard);
                }
                if (item.cost) {
                    item.cost = utils.toFixed(item.cost);
                }
                if (item.recommendedPrice) {
                    item.recommendedPrice = utils.toFixed(item.recommendedPrice);
                }
            }
            showReadOnlyExamineTemplate('approvalExamining', item);
        },
        error: function () {
            zcal({
                type: "error",
                title: "加载数据失败"
            });
        }
    });
}