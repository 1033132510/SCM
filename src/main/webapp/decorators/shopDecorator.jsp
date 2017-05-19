<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
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
<!-- Google Tag Manager -->
<noscript>
    <iframe src="//www.googletagmanager.com/ns.html?id=GTM-KD2RXS" height="0" width="0" style="display:none;visibility:hidden"></iframe>
</noscript>
<script>
    (function (w, d, s, l, i) {
        w[l] = w[l] || [];
        w[l].push({
            'gtm.start': new Date().getTime(), event: 'gtm.js'
        });
        var f = d.getElementsByTagName(s)[0],
                j = d.createElement(s), dl = l != 'dataLayer' ? '&l=' + l : '';
        j.async = true;
        j.src =
                '//www.googletagmanager.com/gtm.js?id=' + i + dl;
        f.parentNode.insertBefore(j, f);
    })(window, document, 'script', 'dataLayer', 'GTM-KD2RXS');
</script>
<!-- End Google Tag Manager -->
<div class="shortcut">
    <div class="container row">
        <ul class="pull-left">
            <!-- ${sessionScope.session_user.userDefinedParams['purchaserLevel']} -->
            <li><a>欢迎您，${sessionScope.session_user.displayUserName}</a></li>
            <li><a href="${ctx}/shop/logout">退出</a></li>
        </ul>
        <ul class="pull-right">
            <li><a href="${ctx}/shop/index">首页</a></li>
            <!--<li><a href="${ctx}/shop/purchaser/toAddPurchaser">采购商登记</a></li>-->
            <!--<li><a href="${ctx}/shop/supplier/toAddSupplier">供应商登记</a></li>-->
            <li><a href="${ctx }/shop/intentionOrder/view">我的订单</a></li>
            <li><a href="${ctx }/shop/cart/view">我的购物车</a></li>
            <li><a href="${ctx}/shop/favorite/myFavorite">我的收藏</a></li>
            <li><a href="${ctx}/account/password/shop">账号管理</a></li>
            <li><span class="phone">客服热线：400-6470799</span></li>
        </ul>
    </div>
</div>
<div class="header">
    <div class="container row">
        <h1 class="logo pull-left"><a href="${ctx}/shop/" class="pull-left">中直采</a></h1>

        <div class="search pull-left">
            <div class="row">
                <input type="text" class="pull-left" id="searchName" value="${productName}" placeholder="商品名称检索"/>
                <button class="pull-left" id="searchBtn">检索</button>
                <input type="hidden" id="seaType" value="${seaType}">
                <ul class="search-terms">
                    <c:if test="${seaType != 1}">
                        <li class="tp">
                            <a href="javascript:void(0)">商品</a>
                        </li>
                        <li class="dn">
                            <a href="javascript:void(0)">品牌</a>
                        </li>
                    </c:if>
                    <c:if test="${seaType == 1}">
                        <li class=tp>
                            <a href="javascript:void(0)">品牌</a>
                        </li>
                        <li class="dn">
                            <a href="javascript:void(0)">商品</a>
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
            <a href="${ctx }/shop/cart/view">购物车<span class="shop-count"></span></a>
        </div>
    </div>
</div>
<div class="nav">
    <div class="container row">
        <ul class="nav-items pull-left">
            <li><a href="${ctx}/shop/national/view">国内优选商品</a></li>
            <li><a href="${ctx}/shop/national/view">海外进口商品</a></li>
        </ul>
    </div>
</div>

<sitemesh:write property='body'/>

<div class="footer">
    <p>工信部ICP备案序号：京ICP备15021171号</p>

    <p>版权所有：北京直采网络科技有限公司 Copyright @ 2015 www.zhongzhicai.com.cn All right Rerserved</p>
</div>

<a href="javascript:void(0)" class="back-top">返回顶部</a>

<script src="${ctx}/resources/script/common/ajaxPrefilterForAuthority.js"></script>
<script src="${ctx}/resources/script/common/closeAjaxCache.js"></script>
</body>
</html>