<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<h2 class="title"><a class="first">会员管理</a><a>>采购商管理</a><a>>添加人员信息</a></h2>
<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
	<div class="container-fluid main-info margin-bottom-25">
		<form id="purchaserUserForm">
	    <h3 class="head">员工账号信息</h3>
        <div class="form-group long margin-left-10 margin-top-20">
            <label class="sr-only" for=""><i class="imp">*</i>手机号:</label>
            <input type="text" class="form-control padding-left-font4" id="mobile" placeholder="请输入手机号，作为平台账号使用" name="mobile" value="${purchaserUser.mobile }"/>
        </div>
        <div class="form-group long margin-left-10 margin-top-20">
            <label class="sr-only" for=""><i class="imp">*</i>姓名:</label>
            <input type="text" class="form-control padding-left-font2" placeholder="请输入真实姓名" id="name" name="name" value="${purchaserUser.name }"/>
        </div>
        <c:if test="${empty purchaserUser }">
	        <div class="form-group long margin-left-10 margin-top-20">
	            <label class="sr-only" for=""><i class="imp">*</i>密码:</label>
	            <input type="password" class="form-control padding-left-font2" placeholder="请输入密码，由字母和数字组成，6-16位" id="password" name="password" value=""/>
	        </div>
	        <div class="form-group long margin-left-10 margin-top-20">
	            <label class="sr-only" for=""><i class="imp">*</i>确认密码:</label>
	            <input type="password" class="form-control padding-left-font4" placeholder="请再次输入密码" id="confirmPassword" name="confirmPassword" value=""/>
	        </div>
        </c:if>
        <div class="form-group long margin-left-10 margin-top-20 form-success">
            <label class="sr-only" for=""><i class="imp">*</i>邮箱:</label>
            <input type="text" class="form-control padding-left-font2" placeholder="请输入邮箱" id="email" name="email" value="${purchaserUser.email }"/>
        </div>
        <div class="form-group long margin-left-10 margin-top-20">
            <label class="sr-only" for=""><i class="imp">*</i>个人身份证号:</label>
            <input type="text" class="form-control padding-left-font6" id="identityNumber" placeholder="请输入个人身份证号" name="identityNumber" value="${purchaserUser.identityNumber }"/>
        </div>
        <div class="form-group long margin-left-10 margin-top-20">
            <label class="sr-only" for="">所属部门:</label>
            <input type="text" class="form-control padding-left-font4" id="department" placeholder="请输入所在部门名称" value="${purchaserUser.department }"/>
        </div>
        <div class="form-group long margin-left-10 margin-top-20 margin-bottom-30">
            <label class="sr-only" for="">职位:</label>
            <input type="text" class="form-control padding-left-font2" id="position" placeholder="请输入职位" value="${purchaserUser.position }"/>
        </div>
        <div class="clear"></div>
        <div class="text-center margin-bottom-30 margin-top-30">
            <a id="savePurchaserUser" href="" class="btn btn-info">保存</a>
            <c:if test="${not empty purchaserUser }">
            		<a id="updatePasswordBtn" href="" class="btn open-modal">修改密码</a>
            </c:if>
        </div>
        <input type="hidden" id="id" value="${purchaserUser.id }" />
        <input type="hidden" id="purchaserId" value="${purchaserId }" />
        <input type="hidden" id="originalMobile" value="${ purchaserUser.mobile}" />
        </form>
	</div>
</div>
<div class="modal" id="updatePasswordModal">
   <div class="modal-content">
       <h1>修改密码</h1>
       <p>
       	<form id="updatePasswordForm">
	        <div class="form-group long margin-left-10 margin-top-20">
	            <label class="sr-only" for=""><i class="imp">*</i>密码:</label>
	            <input type="password" class="form-control padding-left-font2" placeholder="请输入密码，由字母和数字组成，6-16位" id="updatePassword" name="updatePassword" value=""/>
	        </div>
	        <div class="form-group long margin-left-10 margin-top-20">
	            <label class="sr-only" for=""><i class="imp">*</i>确认密码:</label>
	            <input type="password" class="form-control padding-left-font4" placeholder="请输入密码，由字母和数字组成，6-16位" id="updateConfirmPassword" name="updateConfirmPassword" value=""/>
	        </div>
        </form>
          <div class="text-center margin-bottom-20 margin-top-20">
              <a id="confirmUpdatePassword" class="btn btn-info close-appoint-box">确认</a>
              <a id="closeModal" href="" class="btn btn-danger modal-close">关闭</a>
          </div>
       </p>
   </div>
</div>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/purchaser/addOrUpdatePurchaserUser.js"></script>