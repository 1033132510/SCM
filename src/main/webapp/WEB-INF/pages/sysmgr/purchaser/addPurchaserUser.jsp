<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<h2 class="title"><a class="first">会员管理</a><a>>采购商管理</a><a>>添加人员信息</a></h2>
<div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
	<div class="container-fluid main-info margin-bottom-25">
	    <h3 class="head">员工账号信息</h3>
        <div class="form-group form-block long margin-left-10 margin-top-20 form-success">
            <label class="sr-only" for=""><i class="imp">*</i>输入邮箱:</label>
            <input type="text" class="form-control" id="email" name="email" value="${purchaserUser.email }"/>
        </div>
        <div class="form-group form-block long margin-left-10 margin-top-20">
            <label class="sr-only" for=""><i class="imp">*</i>输入密码:</label>
            <input type="password" class="form-control" id="password"/>
        </div>
        <div class="form-group form-block long margin-left-10 margin-top-20 form-error">
            <label class="sr-only" for=""><i class="imp">*</i>确定密码:</label>
            <input type="password" class="form-control" id="confirmPassword"/>
           <!--  <span class="error">两次密码不一致</span> -->
        </div>
        <div class="form-group form-block long margin-left-10 margin-top-20">
            <label class="sr-only" for=""><i class="imp">*</i>姓名:</label>
            <input type="text" class="form-control" id="name" value="${purchaserUser.name }"/>
        </div>
        <div class="form-group form-block long margin-left-10 margin-top-20">
            <label class="sr-only" for=""><i class="imp">*</i>联系电话:</label>
            <input type="text" class="form-control" id="mobile" value="${purchaserUser.mobile }"/>
        </div>
        <div class="form-group form-block long margin-left-10 margin-top-20">
            <label class="sr-only" for=""><i class="imp">*</i>个人身份证号:</label>
            <input type="text" class="form-control" id="identityNumber" value="${purchaserUser.identityNumber }"/>
        </div>
        <div class="form-group form-block long margin-left-10 margin-top-20">
            <label class="sr-only" for="">所属部门:</label>
            <input type="text" class="form-control" id="department" value="${purchaserUser.department }"/>
        </div>
        <div class="form-group form-block long margin-left-10 margin-top-20 margin-bottom-30">
            <label class="sr-only" for="">职位:</label>
            <input type="text" class="form-control" id="position" value="${purchaserUser.position }"/>
        </div>
        <div class="form-group form-select pull-left margin-left-25 ">
			<label class="sr-only" for=""><i class="imp">*</i>状态:</label>
			<select id="valid" class="form-control">
				<c:set value="${purchaserUser.status.value }" var="s"></c:set>
				<c:forEach items="${validStatus }" var="valid">
					<option value="${valid.key }" <c:if test="${s == valid.key }">selected</c:if>>${valid.value }</option>
				</c:forEach>
			</select>
		</div>
        <div class="text-center margin-bottom-30 margin-top-30">
            <a id="savePurchaserUser" href="" class="btn btn-info">保存</a>
        </div>
        <input type="hidden" id="id" value="${purchaserUser.id }" />
        <input type="hidden" id="purchaserUserCode" value="${purchaserUser.purchaserUserCode }" />
        <input type="hidden" id="purchaserId" value="${purchaserId }" />
	</div>
</div>
<script src="${ctx}/resources/script/models/purchaser/addOrUpdatePurchaserUser.js"></script>