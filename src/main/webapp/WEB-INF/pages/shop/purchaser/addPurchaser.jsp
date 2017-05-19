<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta name="renderer" content="webkit"/>
    <meta content="IE=Edge,chrome=1" http-equiv="X-UA-Compatible"/>
    <meta name="description" content=""/>
    <meta name="author" content="zhanjun"/>
    <title>
        采购商登记 － 中直采供应链 － 建筑材料电子商务第一平台
    </title>
    <link rel="stylesheet" href="${ctx}/resources/script/plugins/slick/slick.css"/>
    <link rel="stylesheet" href="${ctx}/resources/content/shop/site.css"/>
</head>
<body>
<div class="shortcut">
    <div class="container row">
        <ul class="pull-left">
            <li><a href="">欢迎您</a></li>
            <li><a href="${ctx}/shop/login">登录</a></li>
        </ul>
        <ul class="pull-right">
            <li><span class="phone">客服热线：400-6470799</span></li>
        </ul>
    </div>
</div>

<div class="header">
    <div class="container row">
        <h1 class="logo pull-left"><a href="" class="pull-left">中直采</a> <span>采购商登记</span></h1>
    </div>
</div>

<div class="nav line">
    <div class="container row"></div>
</div>

<div class="main-content">
    <form class="register" id="purchaserForm">
        <div class="register-input reg-0">
            <input type="text" placeholder="请输入手机号，作为平台账号使用" value="" name="mobilePhone" id="mobilePhone">

            <div class="close"></div>
        </div>
        <div class="register-input reg-1">
            <input type="text" placeholder="请输入真实姓名" value="" name="username" id="username"/>

            <div class="close"></div>
        </div>
        <div class="register-input reg-2">
            <input type="text" placeholder="请填写居民二代身份证号" value="" name="idCard" id="idCard"/>

            <div class="close"></div>
        </div>
        <div class="register-input reg-3">
            <input type="text" placeholder="请输入注册邮箱" value="" name="email" id="email"/>

            <div class="close"></div>
        </div>
        <div class="register-input reg-4">
            <input type="text" placeholder="请填写公司名称" value="" name="companyName" id="companyName"/>

            <div class="close"></div>
        </div>
        <div class="register-input reg-5">
            <input type="text" placeholder="请填写所属部门" value="" name="departmentName" id="departmentName"/>

            <div class="close"></div>
        </div>
        <div class="register-input reg-6">
            <input type="text" placeholder="请填写所在职位" value="" name="position" id="position"/>

            <div class="close"></div>
        </div>
        <div class="text-center padding-top-10">
            <button type="submit" class="btn btn-info" id="addPurchaser">提交</button>
        </div>
    </form>
</div>

<div class="footer">
    <p>工信部ICP备案序号：京ICP备15021171号</p>

    <p>版权所有：北京直采网络科技有限公司 Copyright @ 2015 www.zhongzhicai.com.cn All right Rerserved</p>
</div>
<script src="${ctx}/resources/script/jquery-1.11.0.min.js"></script>
<script src="${ctx}/resources/script/plugins/slick/slick.min.js"></script>
<script src="${ctx}/resources/script/base.js"></script>
<script src="${ctx}/resources/script/front.js"></script>
<script src="${ctx}/resources/script/ie.js"></script>
<script src="${ctx}/resources/script/plugins/validate/jquery.validate.min.js"></script>
<script src="${ctx}/resources/script/plugins/validate/messages_zh.min.js"></script>
<script src="${ctx}/resources/script/models/shop/purchaser/addPurchaser.js"></script>
</body>
</html>