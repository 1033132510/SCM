$(function () {
    getRecord();
    getProductDetail();
    getProductPrice();
    $('#submitButton').click(function (e) {
        window.location.href = ctx + '/sysmgr/approvals/director/approved?t=' + new Date().valueOf();
    });
});

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
            } else {
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
        url: ctx + '/sysmgr/approvals/director/' + $('#auditBillId').val() + '/cateDirectorLatestRecord',
        data: {
            t: new Date().valueOf()
        },
        type: 'post',
        dataType: 'text',
        success: function (data) {
            var item = JSON.parse(data);
            if (item) {
                item.standard = utils.toFixed(item.standard);
                item.cost = utils.toFixed(item.cost);
                item.recommendedPrice = utils.toFixed(item.recommendedPrice);
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