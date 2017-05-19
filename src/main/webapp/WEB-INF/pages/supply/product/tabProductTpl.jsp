<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script id="tabProductDetail" type="text/html">
    <div class="row inner">
        <!if (productCategoryItemValues.length > 2) {!>
        <!for (var i = 0; i < productCategoryItemValues.length; i++) {!>
        <div class="item">
            <!if(productCategoryItemValues[i].itemType==1)
            {!>
            <span><!=productCategoryItemValues[i].productCategoryItemKey.itemName!>：</span><!=productCategoryItemValues[i].value!>
            <!}
            else
            {!>
            <span><!=productCategoryItemValues[i].productPropertiesName!>：</span><!=productCategoryItemValues[i].value!>
            <!}!>
        </div>
        <!}!>
        <!} else {!>
        <div class="item">
            没有数据
        </div>
        <!}!>
    </div>
    <div class="product-preview">
        <p>
            <span>商品描述：</span>
            <!if(productDesc != null && productDesc != ''){!>
            <!=productDesc!>
            <!} else {!>
            暂无商品描述
            <!}!>
        </p>
        <p>
            <span>商品展示：</span>
        </p>
        <!for (var i = 0; i < productDescImages.length; i++) {
        var image = productDescImages[i];
        !>
        <div class="row"><img class="lazy" src="${ctx}/resources/content/images/shop/lazy.png"
                              data-original="<!=image.path!>" alt="商品描述图片" imageType="<!=image.relationType!>"/></div>
        <!}!>
    </div>
</script>
