var productDetail = $('#productDetail').val();
var auditBillId = $('#auditBillId').val();
var previewType = $('#previewType').val();
var price = $('#price').val();
var priceKindId = $('#priceKindId').val();
var hasTax = $('#hasTax').val();
var hasTransportation = $('#hasTransportation').val();
$.ajax({
    type: 'POST',
    url: ctx + '/product/preview/detail',
    data: {
        productDetail: productDetail,
        auditBillId: auditBillId,
        previewType: previewType,
        price: price,
        priceKindId: priceKindId,
        hasTax: hasTax,
        hasTransportation: hasTransportation
    },
    cache: false,
    success: function (data) {
        commonRender.rend(data, true);
    },
    error: function () {
        zcal({
            type: "error",
            title: "加载数据失败"
        });
    }
});