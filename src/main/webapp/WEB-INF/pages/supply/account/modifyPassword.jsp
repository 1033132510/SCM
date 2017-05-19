<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>修改密码</title>
</head>
<body>

<h2 class="title">
    <a class="first">账号管理</a><a>>修改密码</a>
</h2>
<form id="passwordForm">
        <!-- 详情 -->
        <div class="row">
            <div class="row margin-top-20">
                <div class="form-group margin-auto">
                    <label class="sr-only" for=""><i class="imp">*</i>请输入旧密码:</label>
                    <input type="password" id="oldPassword" class="form-control padding-left-font6 required checkOldPassword"/>
                </div>
            </div>
            <div class="row margin-top-20">
                <div class="form-group margin-auto">
                    <label class="sr-only" for=""><i class="imp">*</i>请输入新密码:</label>
                    <input type="password" id="password" class="form-control padding-left-font6 required"/>
                </div>
            </div>
            <div class="row margin-top-20">
                <div class="form-group margin-auto">
                    <label class="sr-only" for=""><i class="imp">*</i>再次输入新密码:</label>
                    <input type="password" id="passwordRepeat" class="form-control padding-left-font7 required validPasswords">
                </div>
            </div>
            <div class="text-center margin-bottom-30">
                <a type="submit" onclick="savePassword();" class="btn btn-info margin-top-30 margin-right-10 margin-left-10">提交<a>
            </div>
        </div>
</form>

<br/>
    <script src="${ctx}/resources/script/models/account/supply/modifyPassword.js"></script>
</body>
</html>