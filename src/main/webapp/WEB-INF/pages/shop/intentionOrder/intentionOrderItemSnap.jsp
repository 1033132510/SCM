<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<input type="hidden" id="id" value="${id }">
<div class="main-content product-detail">
    <div class="categorys container">
        <div class="categorys-inner">
            <div class="level-nav">
                <a href="${ctx}/shop/index">首页</a>
            </div>
        </div>
    </div>
    <div class="bread">
        <a id="fCate"></a>
        <a id="sCate"></a>
        <a id="tCate"></a>
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
<%@ include file="/WEB-INF/pages/shop/product/productPreviewTpl.jsp" %>
<%@ include file="/WEB-INF/pages/shop/intentionOrder/intentionOrderItemInnerTpl.jsp" %>
<%@ include file="/WEB-INF/pages/shop/product/tabProductTpl.jsp" %>
<%@ include file="/WEB-INF/pages/shop/product/tabCompanyTpl.jsp" %>
<script src="${ctx}/resources/script/plugins/slick/slick.min.js"></script>
<script src="${ctx}/resources/script/plugins/baiduTemplate/baiduTemplate.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/shop/common/renderProductDetail.js"></script>
<script src="${ctx}/resources/script/models/shop/intentionOrder/intentionOrderItemSnap.js"></script>