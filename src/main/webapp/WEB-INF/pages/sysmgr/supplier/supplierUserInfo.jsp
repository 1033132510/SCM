<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!-- 主体切换的内容 -->
<h2 class="title"><a class="first">会员管理</a><a>>供应商管理</a><a>>添加</a></h2>
<form name="supplierUser" id="supplierUser" class="container-fluid padding-left-20 padding-right-20 margin-top-20">
    <div class="container-fluid main-info margin-bottom-25 padding-bottom-10">
        <h3 class="head">员工信息</h3>
        <input type="hidden" name="supplierOrgId" id="supplierOrgId" value="${supplierOrgId}"/>
        <input type="hidden" name="id" id="id" value="${supplierUser.id}"/>

        <div class="form-group long margin-left-20 margin-top-20">
            <label><i class="imp">*</i>手机号码:</label>
            <input type="text" class="form-control padding-left-font4" placeholder="请输入手机号，作为平台账号使用" name="contactNumber" id="contactNumber"
                   value="${supplierUser.userName}"/>
            <input type="hidden"  name="oldContactNumber" id="oldContactNumber"
                   value="${supplierUser.userName}"/>
        </div>
        <div class="form-group long margin-left-20 margin-top-20">
            <label><i class="imp">*</i>姓名:</label>
            <input type="text" class="form-control padding-left-font2" placeholder="请输入真实姓名" name="userName" id="userName"
                   value="${supplierUser.supplierUserName}"/>
        </div>

        <div class="form-group long margin-left-20 margin-top-20">
            <label><i class="imp">*</i>个人身份证号:</label>
            <input type="text" class="form-control padding-left-font6" placeholder="请输入个人身份证号" name="idcard" id="idcard"
                   value="${supplierUser.idcard}"/>
        </div>
        <div class="form-group long margin-left-20 margin-top-20">
            <label><i class="imp">*</i>邮箱:</label>
            <input type="email" class="form-control padding-left-font2" placeholder="请输入邮箱" name="email" id="email"
                   value="${supplierUser.email}"/>
        </div>
        <div class="form-group long margin-left-20 margin-top-20">
            <label>所属部门:</label>
            <input type="text" class="form-control padding-left-font4" placeholder="请输入所在部门名称" name="department" id="department"
                   value="${supplierUser.department}"/>
        </div>
        <div class="form-group long margin-left-20 margin-top-20">
            <label>职位:</label>
            <input type="text" class="form-control padding-left-font2" name="position" id="position"  placeholder="请输入职位" value="${supplierUser.position}"/>
        </div>
        <div class="form-group form-select margin-left-20 margin-top-20" style="display:none;">
            <label><i class="imp">*</i>状态:</label>
            <select class="form-control" id="status" name="status">
                <c:forEach var="statusEnum" items="${statusEnums }">
                    <option value="${statusEnum.key}"
                    <c:if test="${supplierUser.status == statusEnum.key }">selected</c:if>
                    >${statusEnum.value}</option>
                </c:forEach>
            </select>
        </div>
        
        <div class="form-group long margin-left-20 margin-top-20"  <c:if test="${not empty supplierUser }"> style="display:none"</c:if> id="userPwdDiv" >
            <label><i class="imp">*</i>密码:</label>
            <input type="password" class="form-control padding-left-font2" placeholder="请输入密码，由字母和数字组成，6-16位" name="userPwd" id="userPwd"
                   value=""/>
        </div>
        <div class="form-group long margin-left-20 margin-top-20" <c:if test="${not empty supplierUser }"> style="display:none" </c:if> id="confirmuserPwdDiv">
            <label><i class="imp">*</i>确认密码:</label>
            <input type="password" class="form-control padding-left-font4" placeholder="请再次输入密码" name="confirmuserPwd" id="confirmuserPwd"
                   value=""/>
        </div>
       
        <div class="text-center margin-bottom-30 margin-top-30">
            <a onclick="saveSupplierUser()" class="btn btn-info">保存</a>
            <a id="updatePasswordBtn" class="btn open-modal"  <c:if test="${empty supplierUser }"> style="display:none"</c:if>>修改密码</a>
        </div>
    </div>
</form>
<div class="modal" id="updatePasswordModal">
   <div class="modal-content">
       <h1>修改密码</h1>
       <p>
       	<form id="updatePasswordForm">
	        <div class="form-group long margin-left-10 margin-top-20">
	            <label class="sr-only"><i class="imp">*</i>密码:</label>
	            <input type="password" class="form-control padding-left-font2" placeholder="请输入密码,由字母和数字组成，6-16位" id="updatePassword" name="updatePassword" value=""/>
	        </div>
	        <div class="form-group long margin-left-10 margin-top-20">
	            <label class="sr-only"><i class="imp">*</i>确认密码:</label>
	            <input type="password" class="form-control padding-left-font4" placeholder="请再次输入密码" id="updateConfirmPassword" name="updateConfirmPassword" value=""/>
	        </div>
        </form>
          <div class="text-center margin-bottom-20 margin-top-20">
              <a id="confirmUpdatePassword" class="btn btn-info close-appoint-box" onclick="updatePassword()">确认</a>
              <a id="closeModal" href="" class="btn btn-danger modal-close">关闭</a>
          </div>
       </p>
   </div>
</div>
<script src="${ctx}/resources/script/models/sysmgr/supplier/supplierUserInfo.js?t=" +new Date().valueOf()></script>
