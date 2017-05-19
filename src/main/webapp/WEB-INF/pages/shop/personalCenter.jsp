<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="personal-bar">
    <div class="personal-bar-title">
        <a href="">个人中心</a>
    </div>
    <ul class="personal-bar-inner">
        <li>
            <h3><a href="${ctx }/shop/intentionOrder/view">我的订单</a></h3>
        </li>
        <li>
            <h3><a href="${ctx }/shop/cart/view">我的购物车</a></h3>
        </li>
        <li id="myFavoriteLi">
            <h3><a href="${ctx}/shop/favorite/myFavorite">我的收藏</a></h3>
        </li>
        <li>
            <h3><a href="${ctx}/account/password/shop">账号管理</a></h3>
        </li>
    </ul>
</div>
<script>
    $(function () {
        $('body').addClass('personal-center');
        $('.personal-remove').remove();
        $('.header .search').removeClass('pull-left').addClass('pull-right margin-top-20');
        var path = window.location.pathname.split("/")[3];
        $('.personal-bar-inner li a').each(function () {
            var href = $(this).attr('href').split("/")[3];
            var spanName = $(this).text();
            if (path == href) {
                $(this).addClass('on').closest('li').siblings('li').find('a').removeClass('on');
                $('.logo').append('<span>' + spanName + '</span>');
            }
        });
    })
</script>