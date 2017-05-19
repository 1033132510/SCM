<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<%@ page import="com.zzc.modules.sysmgr.enums.AccountTypeEnum" %>
<%
    String accountType = AccountTypeEnum.employee.getCode();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta name="renderer" content="webkit"/>
    <meta content="IE=Edge,chrome=1" http-equiv="X-UA-Compatible"/>
    <meta name="description" content=""/>
    <meta name="author" content="zhanjun"/>
    <title>中直采跨境供应链电子商务后台</title>
    <script src="${ctx}/resources/script/jquery-1.11.0.min.js"></script>

    <link rel="stylesheet" href="${ctx}/resources/script/plugins/mCustomScrollbar/jquery.mCustomScrollbar.css"/>
    <link rel="stylesheet" href="${ctx}/resources/content/sysmgr/site.css"/>
</head>
<script type="text/javascript">
    function refreshCaptcha(){
        document.getElementById("imgCaptcha").src="${ctx}/images/kaptcha.jpg?t=" + Math.random();
    }
</script>
<body onload="initFormValue()">
<div class="login">
    <div class="login-wrapper">
        <div class="backend-logo"></div>
        <div class="login-shadow">
            <form commandName="user" method="post">
                <h2>供应链用户登录</h2>
                <div class="form-group form-error">
                    <label class="user" for></label>
                    <input class="form-control" id="userName" placeholder="请输入用户名"/>
                </div>
                <div class="form-group form-error">
                    <label class="password" for></label>
                    <input type="password" id="userPwd" class="form-control" placeholder="请输入密码"/>
                    <span class="error"></span>
                </div>
                <div class="form-group form-code clearfix form-error">
                    <label class="code" for></label>
                    <input class="form-control pull-left" id="captcha" placeholder="请输入验证码"/>
                    <img id="imgCaptcha" src="${ctx}/images/kaptcha.jpg" title="" class="pull-left" onclick="javascript:refreshCaptcha();"/>
                    <a  class="btn btn-info pull-right change" onclick="javascript:refreshCaptcha();">换一个</a>
                    <span class="error" id="errorMsg"></span>
                    <span class="forget-password text-right margin-bottom-20"><a
                            href="${ctx}/account/password/reset?accountType=1" class="pull-right">忘记密码？ </a></span>
                </div>
                <div style="text-align: center">
                    <a onclick="login()" href="javascript:void(0);" value="登录" id="loginBtn"
                       class="btn btn-info lg-btn">登录</a>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    var login = function () {
        var user = {};
        user.userName = $('#userName').val();
        user.userPwd = $('#userPwd').val();
        user.captcha = $('#captcha').val();
        user.accountType = '<%=accountType %>';
        $.ajax({
            url : ctx + '/sysmgr/login',
            contentType : 'application/json;charset=UTF-8',
            type : 'POST',
            data : JSON.stringify(user),
            dataType : 'json',
            success : function(result) {
                if (result.success) {
                    window.location.href = ctx + '/sysmgr';
                } else {
                    $('#errorMsg').html(result.description);
                }
            },
            error : function() {
                console.log('tttt');
                $('#errorMsg').html('系统异常');
            }
        });
    };
    /** 重置登入值 */
    var initFormValue = function () {
        $('#userName').val('');
        $('#captcha').val('');
    }
</script>
</body>
</html>