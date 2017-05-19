/**
 * Created by chenjiahai on 16/3/3.
 */

function previewProduct() {
    var priceAndUnit = $.trim($('#priceAndUnit').text());
    var hasTax = $.trim($('#hasTax').val());
    var hasTransportation = $.trim($('#hasTransportation').val());
    var price = priceAndUnit.substring(1);
    var priceKindId = $('#priceKindId').val();
    price = price.split('/')[0]
    ProductDetail.searchPreviewProduct($.trim(price), priceKindId, hasTax, hasTransportation);
}