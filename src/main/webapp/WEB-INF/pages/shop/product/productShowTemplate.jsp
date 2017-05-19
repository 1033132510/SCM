<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script id="tempHtmlProductULInit" type="text/html">
	<ul class="classify-group" id="<!=currentId!>">
	</ul>
</script>
<script id="tempHtmlProductLIInit" type="text/html">
	<li id="<!=currentId!>">
    </li>
</script>
<script id="tempHtmlProductFirstImageInit" type="text/html">
	<a class="big-img" id="<!=productId!>_bigImage" categoryId = "<!=category.id!>" selfId="<!=productId!>" target="_blank">
   		<span><img class="lazy" src="${ctx}/resources/content/images/shop/lazy.png" data-original="<!=image.path!>"  id="<!=image.id!>" relationType="<!=image.relationType!>" width="200" height="200"/></span>
	</a>
</script>
<script id="tempHtmlProductImagesInit" type="text/html">
<div class="sm-img" id="<!=currentId!>">
	<div>
	<!for(var i=0;i<images.length;i++){
		var image = images[i];
	!>
        <span><img class="lazy" src="" data-original="<!=image.path!>" alert="" relationType="<!=image.relationType!>" width="30" height="30"/></span>
    <!}!>
    </div>
</div>
</script>

<script id="tempHtmlProductNameTxtInit" type="text/html">
	<a class="txt" categoryId = "<!=category.id!>" selfId="<!=productId!>" id="<!=productId!>_product" target="_blank"><!=productName!></a>
</script>

<script id="tempHtmlProductPriceTxtInit" type="text/html">
	<a class="price" categoryId = "<!=category.id!>" selfId="<!=productId!>" id="<!=productId!>_price" target="_blank">
		¥ <!=price!>
	</a>
</script>
<script id="tempHtmlProductTxtInit" type="text/html">
	<!
		if(customData && customData.favoriteId) {
			var favoriteId = customData.favoriteId;
	!>
			<div class="cancelFavorite cancel">
				<a class="cancelFavorite" data-favorite-id="<!=favoriteId!>">取消收藏</a>
				<a class="showProductDetail" data-product-id="<!=productId!>" data-category-id="<!=category.id!>">查看详情</a>
			</div>
	<!
		}
	!>
</script> 
<script id="tempHtmlDisabledProductTxtInit" type="text/html">
	<span style="color:red">商品下架</span>
</script> 