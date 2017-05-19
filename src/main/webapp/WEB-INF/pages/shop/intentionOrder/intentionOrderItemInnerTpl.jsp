<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/html" id="infoTpl">
    <h2><!=productName!></h2>
    <div class="summary">
        <div class="price-group">
            <span class="tips"><em>3</em>我的等级</span>
            <div class="item"><span>参考价:</span><i class="price"><em>¥</em><!=productPrice!></i></div>
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
        <div class="item"><span class="choose">数量:</span>
             <input type="text" value="<!=quantity!>" readonly="readonly" disabled="disabled" />
        </div>
    </div>
</script>