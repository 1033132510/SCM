<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/html" id="infoTpl">
    <h2><!:=productName!></h2>
    <div class="summary">
        <div class="price-group">
            <span class="tips"><em>3</em>我的等级</span>
            <div class="item"><span>参考价:</span><i class="price"><em>¥</em><!=productPrice!><!=unit!></i></div>
            <!
            var taxStr = '';
            if(hasTax=='1'){
            taxStr +='含税';
            }else{
            taxStr +='不含税';
            }
            var tranStr = '';
            if(hasTransportation=='1'){
            tranStr +='含运费';
            }else{
            tranStr +='不含运费';
            }
            !>
            <div class="item transtr"><span></span><i><!=taxStr!> <!=tranStr!></i></div>
        </div>
        <div class="item"><span>商品编码:</span><i><!=productCode!></i></div>
        <div class="item"><span></span><i class="caption-14"><!=tip!></i></div>
        <div class="item"><span>最低起订量:</span><i><!=minOrderCount!></i></div>
        <!if(status == 1) { !>
        <div class="item"><span class="choose">数量:</span>
            <div class="choose-amount">
                <button id="amountReduce" disabled="true" class="btn-reduce">-</button>
                <i><!=minOrderCount!></i>
                <button id="amountAdd" disabled="true" class="btn-add">+</button>
            </div>
        </div>
        <div class="item item-btn">
            <a class="btn btn-info" href="javascript:void(0);">添加收藏</a>
            <a class="btn btn-danger" href="javascript:void(0);">加入购物车</a>
        </div>
        <!}else{!>
        <div class="item"><h1 class="margin-top-30 padding-top-20">此商品已下架</h1></div>
        <!}!>

    </div>
</script>