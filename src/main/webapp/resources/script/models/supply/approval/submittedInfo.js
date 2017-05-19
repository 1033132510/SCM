$(function () {
    getRecord();
    getProductDetail();
});

function getRecord() {
    $.ajax({
        url: ctx + '/supply/approvals/' + $('#auditBillId').val() + '/submittedRecord',
        data: {
            t: new Date().valueOf()
        },
        type: 'post',
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
        url: ctx + '/supply/approvals/' + $('#auditBillId').val() + '/submittedProductInfo',
        data: {
            t: new Date().valueOf()
        },
        type: 'post',
        success: function (result) {
            console.info(result)
            var productDetail = {
                standard: result.standard != null  ? utils.toFixed(result.standard) : '',
                cost: result.cost ? utils.toFixed(result.cost) : '',
                productName: result.productName,
                productCode: result.productCode,
                minOrderCount: result.minOrderCount,
                recommendedPrice: result.recommend != null ? utils.toFixed(result.recommend) : '',
                hasTax: result.hasTax,
                hasTransportation: result.hasTransportation,
                productNumber: result.productNumber,
                images: result.images,
                unit: result.unit && result.unit != '' ? '/' + result.unit : ''
            }
            if (result.recommendedPrice) {
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


