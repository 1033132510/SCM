<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
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
    <title>直采科技有限公司</title>
    <link rel="stylesheet" href="${ctx}/resources/script/plugins/slick/slick.css"/>
    <link rel="stylesheet" href="${ctx}/resources/content/shop/site.css"/>
</head>
<body>

<div class="header">
    <div class="container row">
    <h1 class="logo pull-left"><a href="javascript:history.go(-1)">中直采</a></h1>
    </div>
</div>

<div class="nav">
</div>

<div class="main-content">
    <div class="register password">
        <div class="password-top">
            <div class="password-first-step on">验证身份</div>
            <div class="first-line"></div>
            <div class="password-sec-step">重置密码</div>
            <div class="sec-line"></div>
            <div class="password-thi-step">重置成功</div>
        </div>

        <form class="password-in first-step" id="firstStepForm">
            <input type="hidden" id="accountType" value="${accountType}"/>
            <div class="register-input reg-0">
                <input type="text" placeholder="请输入手机号" id="mobile" name="mobile" value="" class="mobile required"
                       onchange="cleanMobileErrorMsg();">
                <div class="close"></div>
                <label id="mobileErrorMsg" class="error" style="display: none">不输入点击提交提示“请输入手机号”“请输入验证码”</label>
            </div>
            <div class="register-input reg-7">
                <input type="text" class="code required" id="code" name="code" placeholder="请输入验证码" value=""
                       onchange="cleanCodeErrorMsg();">

                <div class="close"></div>
                <a class="code-btn" id="sendSmsBtn" onclick="sendSms();">获取验证码</a>
                <label class="error" id="codeErrorMsg" style="display: none"></label>
            </div>
            <div class="text-center padding-top-10">
                <a name="button" class="btn btn-info" onclick="goToStepTwo();">下一步</a>
            </div>
        </form>

        <form class="password-in sec-step hide" id="secondStepForm">
            <div class="register-input reg-8">
                <input class="required passwordRule" type="password" id="password" name="password" placeholder="请输入由字母和数字组成6-16位密码"
                       value="">
                <label class="error" style="display: none;">请输入正确的密码</label>
                <div class="close"></div>
            </div>
            <div class="register-input reg-9">
                <input type="password" id="passwordRepeat" name="passwordRepeat" placeholder="请再次输入密码" value=""
                       class="validPasswords required passwordRule">
                <label class="error" style="display: none" id="passwordErrorMsg">两次密码不一样</label>
                <div class="close"></div>
            </div>
            <div class="text-center padding-top-10">
                <a name="button" class="btn btn-info" onclick="goToStepThree();">下一步</a>
            </div>
        </form>

        <div class="password-in thi-step hide">
            <div class="password-success">您的账号已经重置成功，点击完成进行重新登录</div>
            <div class="text-center padding-top-10">
                <button name="button" onclick="goToLoginPage();" class="btn btn-info" id="finishedBtn">完成</button>
            </div>
        </div>
    </div>
</div>


<div class="footer">
    <p>工信部ICP备案序号：京ICP备15021171号-2</p>

    <p>版权所有：中直采企业管理有限公司 Copyright @ 2015 www.zzcqy.com All right Rerserved</p>
</div>
<script src="${ctx}/resources/script/jquery-1.11.0.min.js"></script>
<script src="${ctx}/resources/script/base.js"></script>
<script src="${ctx}/resources/script/front.js"></script>
<script src="${ctx}/resources/script/ie.js"></script>
<script src="${ctx}/resources/script/common/dataSubmitUtil.js"></script>
<script src="${ctx}/resources/script/models/account/resetPassword.js"></script>
<script src="${ctx}/resources/script/plugins/validate/jquery.validate.min.js"></script>
<script src="${ctx}/resources/script/plugins/validate/messages_zh.min.js"></script>
<script>
    $(function () {
        $(".close").click(function () {
            $(this).prev().val("");
        });
        $('body').addClass('personal-center');
    });
</script>
</body>
</html>