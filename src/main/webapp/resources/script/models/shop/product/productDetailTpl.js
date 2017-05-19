var cateId = $('#cateId').val();
var productId = $('#productId').val();
$.ajax({
    type: 'GET',
    url: ctx + '/shop/product/pDetailShop?cateId=' + cateId + '&productId=' + productId,
    cache : false,
    success: function (data) {
        console.info(data)
    		commonRender.rend(data, true);
    },
    error: function () {
        zcal({
            type: "error",
            title: "加载数据失败"
        });
    }
});