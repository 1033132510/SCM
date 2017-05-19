<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收藏列表</title>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
	<meta name="format-detection" content="telephone=no" />
	<meta name="renderer" content="webkit" />
	<meta content="IE=Edge,chrome=1" http-equiv="X-UA-Compatible" />
	<meta name="description" content="" />
	<meta name="author" content="zhanjun" />
	<link rel="stylesheet" href="${ctx}/resources/script/plugins/slick/slick.css"/>
    <link rel="stylesheet" href="${ctx}/resources/content/shop/site.css"/>
</head>
<body>
	<%@ include file="/WEB-INF/pages/shop/product/productShowTemplate.jsp" %>
	<div class="main-content personal-detail favorite-detail">
	    <%@ include file="/WEB-INF/pages/shop/personalCenter.jsp" %>
	    <div class="personal-wrapper">
			<div class="tab-container personal-tab">
				<div class="tab-nav">
					<a class="on"></a>
				</div>
			</div>
	        <div class="category">
	            <ul id="secondLevelCategoryUl"></ul>
	        </div>
	        <div class="hide" id="noFavoriteDiv">
				暂无收藏
			</div>
	        <div id="favoriteContainer"></div>
	        <div class="pagination text-center" id="pager"></div>
	    </div>
	</div>
	<script src="${ctx}/resources/script/plugins/bsGrid/bsgrid.all.min.js"></script>
	<script src="${ctx}/resources/script/plugins/bsGrid/grid.zh-CN.js"></script>
	<script src="${ctx}/resources/script/models/utils.js"></script>
	<script src="${ctx}/resources/script/plugins/baiduTemplate/baiduTemplate.js"></script>
	<script src="${ctx}/resources/script/models/shop/product/productShowTemplate.js"></script>
	<script src="${ctx}/resources/script/plugins/jqueryPager/jquery.pager.js"></script>
	<script src="${ctx}/resources/script/models/shop/favorite/favorite.js"></script>
</body>
</html>