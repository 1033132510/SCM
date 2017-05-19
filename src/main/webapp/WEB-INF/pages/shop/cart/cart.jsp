<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<link href="${ctx}/resources/script/plugins/datetime/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<div class="main-content personal-detail cart-detail">
    <div class="personal-wrapper">
        <div class="tab-container personal-tab">
            <div class="tab-nav">
                <a class="on">我的购物车</a>
            </div>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th class="w1">
                        <div class="form-checkbox select-all">
                            <label>
                                <input type="checkbox"><i><s></s></i><span>全部</span>
                            </label>
                        </div>
                        商品名称
                    </th>
                    <th class="w2">单价</th>
                    <th class="w3">数量</th>
                    <th class="w4">总金额</th>
                    <th class="w5 last">操作</th>
                </tr>
            </thead>
            <tbody id="carts"></tbody>
        </table>
        <div class="pagination text-center" id="pager"></div>
        <div class="price-tips">
            <div class="form-checkbox select-all">
                <label>
                    <input type="checkbox"><i><s></s></i><span>全选</span>
                </label>
            </div>
            <div class="pull-right">
                <span class="pull-left">已选商品：<i><em id="cartQuantity">0</em></i>件</span>
                <span class="pull-left">合计：<i>¥<em id="totalPrice">0.00</em></i></span>
                <a data-target="#createOrderModal" onclick="cart.openModal(event);" href="" class="btn open-modal">提交</a>
            </div>
        </div>
    </div>
</div>
<div class="modal" id="createOrderModal">
    <div class="modal-content cart-modal">
        <form id="projectInfo">
            <h1>项目信息</h1>

            <div class="row margin-bottom-20 margin-top-20">
                <div class="form-group pull-left margin-right-20">
                    <label><i class="imp">*</i>项目名称:</label>
                    <input name="projectName" id="projectName" type="text" class="form-control padding-left-font4"/>
                </div>
                <div class="form-group pull-left date-group date">
                    <label><i class="imp">*</i>预计需求期:</label>
                    <input type="text" name="demandDate" id="demandDate" class="form-control padding-left-font5" name="demandDate" size="16"
                           readonly="readonly"/>
                </div>
            </div>
            <div class="form-group form-textarea">
                <label>项目备注:</label>
                <textarea id="projectDescription" type="text" class="form-control"></textarea>
            </div>
        </form>
        <div class="text-center margin-top-20">
            <a onclick="cart.createOrder(event);" class="btn btn-info close-appoint-box">确定提交</a>
            <a onclick="cart.closeModal(event);" href="" class="btn btn-danger modal-close">关闭</a>
        </div>
    </div>
</div>
<script src="${ctx}/resources/script/plugins/baiduTemplate/baiduTemplate.js"></script>
<script id="cartTpl" type="text/html">
    <tr id="tr_<!=index!>" class="gray_<!=productStatus!>">
        <td class="text-left">
            <div class="cart-name">
			    <!if(productStatus == 1) { !>
                    <div id="chb_div_<!=index!>" class="form-checkbox select-single">
                        <label>
                            <input id="chb_<!=index!>" type="checkbox"><i><s></s></i>
                        </label>
                    </div>
				<!}else{!>
                    <div class="del">
                        <label>
                            下架
                        </label>
                    </div>
    			<!}!>
                <img style="cursor:hand" onclick="cart.showProductDetail(event, '<!=productSKUId!>', '<!=productCategoryId!>');" src="<!=imagePath!>" class="preview-img"/>
                <p>
                    <span><a onclick="cart.showProductDetail(event, '<!=productSKUId!>', '<!=productCategoryId!>');"><!=productName!></a></span>
                </p>
            </div>
        </td>
        <td>￥<span id="price_<!= index!>"><!=price!></span>
           <i class="tax">
            <! if(hasTax == 1) {!>
            含税
            <!}else{!>
            不含税
            <!}!>
            <! if(hasTransportation == 1) {!>
            含运费
            <!}else{!>
            不含运费
            <!}!>
           </i>
        </td>
        <td>
			 <!if(productStatus == 1) { !>
			 	<div class="choose-amount">
                    <input onkeyup="cart.inputChange(this);" id="quantity_<!=index!>" type="text" value="<!=quantity!>"/>
                    <a onclick="cart.reduceQuantity(event, this);" id="reduce_<!=index!>" class="btn-reduce">-</a>
               	 	<a onclick="cart.addQuantity(event, this);" id="add_<!=index!>" class="btn-add">+</a>
                </div>
			<!}else{!>
                <span><!=quantity!></span>
    		<!}!>
        </td>
        <td id="recordPrice_<!=index!>">￥<!=totalPrice!></td>
        <td><a id="del_<!=index!>" onclick="cart.delCart(event, this, '<!=id!>')" class="btn-delete">删除</a></td>
        <input type="hidden" id="productSKUId_<!=index!>" value="<!=productSKUId!>"/>
        <input type="hidden" id="productCateId_<!=index!>" value="<!=productCategoryId!>"/>
        <input type="hidden" id="id_<!=index!>" value="<!=id!>"/>
    </tr>
</script>
<script>
    $(function () {
        $('body').addClass('personal-center');
        $('.personal-remove').remove();
        var spanName = '我的购物车';
        $('.logo').append('<span>' + spanName + '</span>');
        function scroll() {
            var height = $("body").height();//获取内容高
            var scrollTop = $(window).scrollTop();
            var innerHeight = window.innerHeight;
            var scrollBtm = height - scrollTop - innerHeight;
            if (scrollBtm <= 100) {
                $('.price-tips').css('position', 'absolute');
            } else {
                $('.price-tips').css('position', 'fixed');
            }
        }
        scroll();
        $(window).scroll(function () {
            scroll();
        });
        $(document).on('click', '#searchBtn', function (e) {
            searchEvent.init(true);
        });
    });
    $(function(){
		 $("#searchName").keydown(function(event){
	       event = document.all ? window.event : event;
	       if((event.keyCode || event.which) == 13) {
	       		event.stopPropagation();
	       		searchEvent.init(true);
	       }
	    }); 
	});
</script>
<script src="${ctx}/resources/script/plugins/datetime/bootstrap-datetimepicker.min.js"></script>
<script src="${ctx}/resources/script/plugins/datetime/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${ctx}/resources/script/plugins/validate/jquery.validate.min.js"></script>
<script src="${ctx}/resources/script/plugins/validate/messages_zh.min.js"></script>
<script src="${ctx}/resources/script/plugins/jqueryPager/jquery.pager.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/shop/cart/cart.js"></script>
