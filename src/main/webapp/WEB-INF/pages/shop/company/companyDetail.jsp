<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/pages/shop/product/productShowTemplate.jsp" %>
<script src="${ctx}/resources/script/plugins/baiduTemplate/baiduTemplate.js"></script>
<script src="${ctx}/resources/script/models/shop/product/productShowTemplate.js"></script>
<div class="main-content company-detail">
    <div class="categorys">
        <div class="level-nav">
            <a class="btn-level1 on" href="${ctx}/shop/index">首页</a>
        </div>
    </div>
    <div class="bread">
        <a href="${ctx}/shop/index">首页</a>
        <a>&gt;${company.orgName}</a>
        <input type="hidden" value="${company.id}" id="orgId" name="orgId"/>
    </div>
    <div class="company-logo" >
    	<div id="LOGOShow">
    		<img src="${logoImage.path}" width="272px" height="">
    	</div>
        <span>${company.orgName}</span>
    </div>
    <div class="tab-container">
        <div class="row tab-nav">
            <a class="on">公司介绍</a>
            <a onclick="getHonor()">荣誉证书</a>
            <a onclick="getbrandList()" id="brandList">品牌列表</a>
            <a onclick="getProductListByOrgId()" id="productList">商品列表</a>
        </div>

        <div class="tab-content">
            <div class="tab on">
                <p>${company.supplierOrgIntroductionHtml}</p>
            </div>
            <div class="tab">
                <div class="model certificate" id="honorShow">
                </div>
            </div>
            <div class="tab">
                <div class="model" id="brandShow">
                </div>
            </div>
            <div class="tab" id="productShow">
            </div>
        </div>
    </div>
</div>

<script>
$(function(){
});

var companyBt = baidu.template;
function getProductListByOrgId(){
	if(!$.trim($('#orgId').val()) || $.trim($("#productShow").html()).length>0){
		return;
	}
	productTemplate.initByOrgId('productShow',$.trim($('#orgId').val()),true);
}
function getHonor(){
	if(!$.trim($('#orgId').val()) || $.trim($("#honorShow").html()).length>0){
		return;
	}
	$.ajax({
		url : ctx + '/shop/company/getHonorList',
		type : 'get',
		data : {
			orgId:$('#orgId').val(),
			t : new Date().valueOf()
		},
		async : false,
		success : function(data) {
			if (data == 'fail') {
				zcal({
					type : "error",
					title : "加载数据失败"
				});
				return;
			} else {
				var item ={
					images:data
				}
				var html = companyBt('tempHtmlHonorInit',item);
				$('#honorShow').html(html);
				$("img.lazy").lazyload({
					effect: "fadeIn",
					threshold: 100,
				});
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			zcal({
				type : "error",
				title : "加载数据失败"
			});
		}
	});
}

function getbrandList(){
	if(!$.trim($('#orgId').val()) || $.trim($("#brandShow").html()).length>0 ){
		return;
	}
	$.ajax({
		url : ctx + '/shop/company/getBrandList',
		type : 'post',
		data : {
			orgId:$('#orgId').val(),
			t : new Date().valueOf()
		},
		async : false,
		success : function(data) {
			if (data == 'fail') {
				zcal({
					type : "error",
					title : "加载数据失败"
				});
				return;
			} else {
				var html ='';
				for(var i in data){
					var item =data[i];
					html += companyBt('tempHtmlBrandInit',item);
				}
				$('#brandShow').html(html);
				$("img.lazy").lazyload({
					effect: "fadeIn",
					threshold: 200,
				});

			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			zcal({
				type : "error",
				title : "加载数据失败"
			});
		}
	});
}
$(document).on('click', '#searchBtn', function (e) {
	searchEvent.init(true);
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

<script id="tempHtmlHonorInit" type="text/html">
	<!for(var i=0 ;i<images.length;i++){
		var image = images[i];
	!>
	<i><span><img class="lazy" src="${ctx}/resources/content/images/shop/lazy.png" data-original="<!=image.path!>" id="<!=image.id!>" height="<!=height!>" ></span></i>
	<!}!>
</script>


<script id="tempHtmlBrandInit" type="text/html">
	<div class="row">
		<span class="pp-logo pull-left">
			<!if(imageListLogo.length>0){
					var image = imageListLogo[0];
			!>
				<i><img class="lazy" src="${ctx}/resources/content/images/shop/lazy.png" data-original="<!=image.path!>" /></i>
			<!}!>
		</span>
		<strong class="pp-caption"><!=brand.brandZHName!></strong>
	</div>
	<div class="margin-bottom-20">
		<p>
			<!:=brand.brandDesc!>
		</p>
		<!for(var i=0;i<imageListDesc.length;i++){
			var image = imageListDesc[i];
		!>
			<div class="row"><img class="lazy" src="${ctx}/resources/content/images/shop/lazy.png" data-original="<!=image.path!>" /></div>

		<!}!>
	</div>
</script>