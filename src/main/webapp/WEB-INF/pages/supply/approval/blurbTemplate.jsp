<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<link rel="stylesheet" href="${ctx}/resources/script/plugins/slick/slick.css"/>
<script id="blurbTemplate" type="text/html">


    <div class="type-manage">
        <h3 class="head">
            <a>商品信息</a>
            <a><span class="imp blurb-txt pull-right" onclick="previewProduct();">点击查看商品详情</span></a>
        </h3>
        <div class="row product-intro">
            <!-- 图片预览 -->

            <div class="preview pull-left">
                <div class="slider-for">
                    <!for(var i=0;i
                    <images.length
                            ;i++){
                            var image=images[i];
                            if(image.relationType !=14){
                            continue;
                            }
                            !>
                        <div>
                            <img data-lazy="<!=image.path!>" selfId="<!=image.id!>"/>
                        </div>
                        <!}!>
                </div>
                <div class="slider-nav">
                    <!for(var i=0;i
                    <images.length ;i++){
                                   var image=images[i];
                                   if(image.relationType !=14){
                                   continue;
                                   }
                                   !>
                        <div>
                            <img data-lazy="<!=image.path!>" selfId="<!=image.id!>"/>
                        </div>
                        <!}!>
                </div>
            </div>
            <!-- 信息预览 -->
            <div class="inner pull-left" id="productInner">
                <h2><!=productName!></h2>
                <div class="summary">
                    <div class="price-group">
                        <span class="tips"><em>3</em>我的等级</span>
                        <div class="item">
                            <span>参考价:</span><i class="price" id="priceAndUnit">
                            <em>¥</em>
                            <!if(standard){!>
                            	<!=standard!>
                            <!}else{!>
                            	<!=recommendedPrice!>
                            <!}!>
                            <!=unit!>
                        </i>
                        </div>
                        <div class="item transtr"><span></span>
                            <i>
                                <!if(hasTax == 1){!>
                                含税
                                <!}else{!>
                                不含税
                                <!}!>
                                <!if(hasTransportation == 1){!>
                                含运费
                                <!}else{!>
                                不含运费
                                <!}!>
                            </i></div>
                    </div>
                    <input type="hidden" value="<!=hasTax!>" id="hasTax"/>
                    <input type="hidden" value="<!=hasTransportation!>" id="hasTransportation"/>
                    <input type="hidden" value="<!=priceKindId!>" id="priceKindId"/>
                    <div class="item">
                        <span>商品编码:</span>
                        <i><!=productCode!></i>
                    </div>
                    <div class="item">
                        <span>最低起定量:</span><i><!=minOrderCount!></i>
                    </div>
                    <div class="item">
                        <span class="choose">数量:</span>
                        <div class="choose-amount">
                            <button id="amountReduce" class="btn-reduce">-</button>
                            <i id="quantity"><!=minOrderCount!></i>
                            <button id="amountAdd" class="btn-add">+</button>
                        </div>
                    </div>
                    <div class="item item-btn">
                        <a class="btn btn-info" onclick="">添加收藏</a>
                        <a id="addCart" class="btn btn-danger">加入购物车</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</script>
<script src="${ctx}/resources/script/plugins/baiduTemplate/baiduTemplate.js"></script>
<script src="${ctx}/resources/script/plugins/slick/slick.min.js"></script>
<script src="${ctx}/resources/script/models/supply/product/getProductDetailData.js?<%=new Date().getTime()%>"></script>
<script src="${ctx}/resources/script/models/supply/approval/blurbTemplate.js"></script>
<script>
    var blurTemplate = baidu.template;
    blurTemplate.ESCAPE = false;
    function showBlurbTemplate(id, data) {
        var bdhtml = blurTemplate('blurbTemplate', data);
        $("#" + id).append(bdhtml);
        $('.slider-for').slick({
            lazyLoad: 'ondemand',
            slidesToShow: 1,
            slidesToScroll: 1,
            arrows: false,
            fade: true,
            asNavFor: '.slider-nav'
        });
        var slideCount = $('.slider-nav div').length;
        var slidesToShow = 5;
        var Boole = false;
        if (slideCount < slidesToShow || slideCount == slidesToShow) {
            Boole = false;
        } else {
            Boole = true;
        }
        $('.slider-nav').slick({
            slidesToShow: slidesToShow,
            lazyLoad: 'ondemand',
            slidesToScroll: 1,
            asNavFor: '.slider-for',
            dots: false,
            centerMode: Boole,
            centerPadding: 0,
            focusOnSelect: true
        });
    }
</script>