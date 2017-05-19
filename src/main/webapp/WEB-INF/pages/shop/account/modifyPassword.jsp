<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<link href="${ctx}/resources/script/plugins/datetime/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<div class="main-content personal-detail cart-detail">
    <form class="register" id="passwordForm">
        <div class="register-input reg-8">
            <input type="password" id="oldPassword" placeholder="请输入旧密码" value="" name="" class="required checkOldPassword">
            <div class="close"></div>
        </div>
        <div class="register-input reg-9">
            <input type="password" id="password" placeholder="请输入新密码" value="" name="username" id="" class="required"/>
            <div class="close"></div>
        </div>
        <div class="register-input reg-10">
            <input type="password" id="passwordRepeat" placeholder="请再次输入新密码" value="" class="required validPasswords">
            <div class="close"></div>
        </div>
        <div class="text-center padding-top-45">
            <a type="submit" class="btn btn-info" onclick="savePassword();">提交</a>
        </div>
    </form>
</div>
<script>
    $(function () {
        $('body').addClass('personal-center');
        $('.personal-remove').remove();
        var spanName = '账号管理';
        $('.logo').append('<span>' + spanName + '</span>');
        $(".close").click(function () {
            $(this).prev().val("");
        });
    });
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
<script src="${ctx}/resources/script/models/account/modifyPassword.js"></script>
<script src="${ctx}/resources/script/plugins/validate/jquery.validate.min.js"></script>
<script src="${ctx}/resources/script/plugins/validate/messages_zh.min.js"></script>
<script src="${ctx}/resources/script/ie.js"></script>
