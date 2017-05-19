<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
    <meta name="description" content=""/>
    <meta name="author" content="zhanjun"/>
    <title>
        中直采供应链 － 建筑材料电子商务第一平台
    </title>
    <link rel="stylesheet" href="${ctx}/resources/script/plugins/slick/slick.css"/>
    <link href="${ctx}/resources/script/plugins/datetime/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/resources/content/shop/site.css"/>
    <script src="${ctx}/resources/script/jquery-1.11.0.min.js"></script>
    <script src="${ctx}/resources/script/base.js"></script>
    <script src="${ctx}/resources/script/front.js"></script>
    <script src="${ctx}/resources/script/plugins/lazyload/jquery.lazyload.js"></script>
    <script src="${ctx}/resources/script/models/shop/searchEvent.js"></script>
</head>
<body>
<div class="shortcut">
    <div class="container row">
        <ul class="pull-left">
            <li><a>欢迎您，供应商用户</a></li>
            <li><a href="javascript:void(0);">退出</a></li>
        </ul>
        <ul class="pull-right">
            <li><a href="javascript:void(0);">首页</a></li>
            <!--<li><a href="${ctx}/shop/purchaser/toAddPurchaser">采购商登记</a></li>-->
            <!--<li><a href="${ctx}/shop/supplier/toAddSupplier">供应商登记</a></li>-->
            <li><a href="javascript:void(0);">我的订单</a></li>
            <li><a href="javascript:void(0);">我的购物车</a></li>
            <li><a href="javascript:void(0);">我的收藏</a></li>
            <li><a href="javascript:void(0);">账号管理</a></li>
            <li><span class="phone">客服热线：400-6470799</span></li>
        </ul>
    </div>
</div>
<div class="header">
    <div class="container row">
        <h1 class="logo pull-left"><a href="javascript:void(0);" class="pull-left">中直采</a></h1>

        <div class="search pull-left">
            <div class="row">
                <input type="text" class="pull-left" id="searchName" value="${productName}" placeholder="商品名称检索" disabled/>
                <button class="pull-left" onclick="javascript:void(0);">检索</button>
                <input type="hidden" id="seaType" value="${seaType}">
                <ul class="search-terms">
                    <c:if test="${seaType != 1}">
                        <li class="tp">
                            <a href="javascript:void(0);">商品</a>
                        </li>
                        <li class="dn">
                            <a href="javascript:void(0);">品牌</a>
                        </li>
                    </c:if>
                    <c:if test="${seaType == 1}">
                        <li class=tp>
                            <a href="javascript:void(0);">品牌</a>
                        </li>
                        <li class="dn">
                            <a href="javascript:void(0);">商品</a>
                        </li>
                    </c:if>
                </ul>
            </div>
            <div class="hotwords personal-remove">
                <a id="tjsecondId1"></a>
                <a id="tjsecondId2"></a>
                <a id="tjsecondId3"></a>
                <a id="tjsecondId4"></a>
                <a id="tjsecondId5"></a>
            </div>
        </div>

        <div class="shop-cart personal-remove pull-left">
            <span class="shop-ico"></span>
            <a href="javascript:void(0);">购物车(1)</a>
        </div>
    </div>
</div>
<div class="nav">
    <div class="container row">
        <ul class="nav-items pull-left">
            <li><a href="javascript:void(0);">国内优选商品</a></li>
            <li><a href="javascript:void(0);">海外进口商品</a></li>
        </ul>
    </div>
</div>

<input type="hidden" id="productDetail" value='${productDetail }'/>
<input type="hidden" id="previewType" value="${previewType}"/>
<input type="hidden" id="auditBillId" value="${auditBillId}"/>
<input type="hidden" id="price" value="${price}"/>
<input type="hidden" id="priceKindId" value="${priceKindId}"/>
<input type="hidden" id="hasTax" value="${hasTax}"/>
<input type="hidden" id="hasTransportation" value="${hasTransportation}"/>
<div class="main-content product-detail">
    <div class="categorys container">
        <div class="categorys-inner">
            <div class="level-nav">
                <a href="javascript:void(0);">首页</a>
            </div>
        </div>
    </div>
    <div class="bread">
        <button id="fCate" disabled></button>
        <button id="sCate" disabled></button>
        <button id="tCate" disabled></button>
    </div>
    <div class="row product-intro">
        <!-- 图片预览 -->
        <div class="preview pull-left">

        </div>
        <!-- 信息预览 -->
        <div class="inner pull-left" id="productInner"></div>
    </div>
    <div class="tab-container">
        <div class="row tab-nav">
            <a href="" class="on">商品详情</a>
            <a href="">公司介绍</a>
        </div>
        <div class="tab-content">
            <!-- 产品详情 -->
            <div class="tab tab-product-detail on">

            </div>
            <!-- 公司详情 -->
            <div class="tab tab-company-detail">

            </div>
        </div>
    </div>
</div>
<!-- 引入模版 -->
<%@ include file="/WEB-INF/pages/supply/product/productPreviewTpl.jsp" %>
<%@ include file="/WEB-INF/pages/supply/product/productInnerTpl.jsp" %>
<%@ include file="/WEB-INF/pages/supply/product/tabProductTpl.jsp" %>
<%@ include file="/WEB-INF/pages/supply/product/tabCompanyTpl.jsp" %>
<script src="${ctx}/resources/script/plugins/slick/slick.min.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/plugins/baiduTemplate/baiduTemplate.js"></script>
<script src="${ctx}/resources/script/plugins/datetime/bootstrap-datetimepicker.min.js"></script>
<script src="${ctx}/resources/script/models/shop/common/renderProductDetail.js"></script>
<script>
    $(function () {
        $('#fCate').bind('click', function (e) {
            return e.preventDefault();
        })
    });

</script>
<script src="${ctx}/resources/script/models/supply/product/productDetailTpl.js"></script>
<div class="footer">
    <p>工信部ICP备案序号：京ICP备15021171号</p>

    <p>版权所有：北京直采网络科技有限公司 Copyright @ 2015 www.zhongzhicai.com.cn All right Rerserved</p>
</div>

<a href="javascript:void(0);" class="back-top">返回顶部</a>
</body>
</html>