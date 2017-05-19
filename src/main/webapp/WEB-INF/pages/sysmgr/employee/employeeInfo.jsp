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
    <title>用户基本信息维护</title>
    <link href="${ctx}/resources/script/plugins/datetime/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${ctx}/resources/script/plugins/datetime/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body>
<form id="employeeForm" action="${ctx}/sysmgr/employee/info" method="POST">
    <input type="hidden" id="entityId" name="id" value="${employee.id}"/>
    <input type="hidden" id="userPwd" name="userPwd" value="${employee.userPwd}"/>
    <input type="hidden" id="type" name="type" value="${employee.type}"/>
    <input type="hidden" id="status" name="status" value="${employee.status}"/>

    <h2 class="title"><a class="first">用户维护</a></h2>

    <div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
        <div class="container-fluid main-info type-manage clearfix user-content">
            <h3 class="head"><a>账户维护</a></h3>

            <div class="row">
                <div class="col-6 padding-top-20">
                    <div class="form-group margin-bottom-20">
                        <label>账号:</label>
                        <input name="userName" id="userName" value="${employee.userName}" placeholder="该字段不可编辑，取自于手机号码"
                               class="form-control padding-left-font2" readonly="true"/>
                    </div>
                    <div class="form-group margin-bottom-20">
                        <label><i class="imp">*</i>姓名:</label>
                        <input name="employeeName" id="employeeName" value="${employee.employeeName}"
                               class="form-control padding-left-font2 required"/>
                    </div>
                </div>
            </div>
            <h3 class="head"><a>基础信息维护</a></h3>

            <div class="row padding-top-20">
                <div class="margin-bottom-20 col-6">
                    <div class="form-group ">
                        <label><i class="imp">*</i><span id="mobileLabel">手机:</span></label>
                        <input type="hidden" id="oldMobile" value="${employee.mobile}"/>
                        <input name="mobile" id="mobile" value="${employee.mobile}"
                               class="form-control padding-left-font2 required mobile hasExist" onblur="copyAccount();"/>
                    </div>
                </div>
                <div class="margin-bottom-20 col-6">
                    <div class="form-group">
                        <label>工号:</label>
                        <input name="employeeNO" value="${employee.employeeNO}" class="form-control padding-left-font2"/>
                    </div>
                </div>
                <div class="margin-bottom-20 col-6">
                    <div class="form-group">
                        <label><i class="imp">*</i>E-mail:</label>
                        <input name="email" id="email" value="${employee.email}" class="form-control padding-left-font3 required email"/>
                    </div>
                </div>
                <div class="margin-bottom-20 col-6">
                    <div class="form-group">
                        <label>微信:</label>
                        <input name="weixin" value="${employee.weixin}" class="form-control padding-left-font2"/>
                    </div>
                </div>
                <div class="margin-bottom-20 col-6">
                    <div class="form-group">
                        <label>固定电话:</label>
                        <input name="telephone" id="telephone" value="${employee.telephone}" class="form-control padding-left-font4"/>
                    </div>
                </div>
                <div class="margin-bottom-20 col-6">
                    <div class="form-group padding-left-80 date-group">
                        <label class="text-left">入职日期:</label>
                        <form action="" class="form-horizontal" role="form">
                            <fieldset>
                                <div id="entryDate" class="input-group date">
                                    <input class="form-control" name="entryDate" size="16" value="${employee.entryDate}"/>
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>
                            </fieldset>
                        </form>
                    </div>
                </div>
                <div class="margin-bottom-20 col-6">
                    <div class="form-group">
                        <label>工作地点:</label>
                        <input name="workplace" id="workplace" value="${employee.workplace}" class="form-control padding-left-font4"/>
                    </div>
                </div>
                <div class="margin-bottom-20 col-6">
                    <div class="form-group">
                        <label>身份证号:</label>
                        <input name="identityCard" value="${employee.identityCard}" class="form-control padding-left-font4 identityCard"/>
                    </div>
                </div>
                <div class="margin-bottom-20 col-6">
                    <div class="form-group">
                        <label>QQ:</label>
                        <input name="qq" id="qq" value="${employee.qq}" class="form-control padding-left-font2 qqValidate"/>
                    </div>
                </div>
                <div class="margin-bottom-20 col-6">
                    <div class="form-group form-select pull-left margin-right-25">
                        <label>性别：</label>
                        <select class="form-control select" name="sex" id="sex">
                            <c:forEach items="${sexs}" var="sex">
                                <option value="${sex.key}"
                                <c:if test="${employee.sex == sex.key }">selected</c:if>
                                >${sex.value}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group form-select pull-left margin-right-25">
                        <label>状态：</label>
                        <select class="form-control select" name="employeeStatus" id="employeeStatus">
                            <c:forEach items="${employeeStatus}" var="status">
                                <option value="${status.key}"
                                <c:if test="${employee.employeeStatus == status.key }">selected</c:if>
                                >${status.value}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group form-select pull-left">
                        <label>类型：</label>
                        <select class="select" name="employeeType" id="employeeType">
                            <c:forEach items="${employeeTypes}" var="employeeType">
                                <option value="${employeeType.key}"
                                <c:if test="${employee.employeeType == employeeType.key }">selected</c:if>
                                >${employeeType.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="clear"></div>

                <div class="col-6 margin-bottom-20">
                    <div class="form-group form-textarea">
                        <label>个人简介:</label>
                        <textarea name="description" value="${employee.description}" rows="5" cols="30"
                                  class="form-control"></textarea>
                    </div>
                </div>

                <div class="clear"></div>
                <div class="text-center margin-bottom-30">
                    <c:if test="${empty employee.id}">
                        <input type="reset" class="btn btn-warning margin-top-10"/>
                    </c:if>
                    <a href="javascript:void 0" id="submitBtn" class="btn btn-info margin-top-10 margin-right-10 margin-left-10">保存</a>
                    <a href="${ctx}/sysmgr/employee/view" class="btn btn-default margin-top-10">返回</a>
                </div>
            </div>
        </div>
    </div>
</form>
<script>

    $(document).ready(function () {
        // carousel
        $(".select").select2({
            minimumResultsForSearch: -1
        });//去掉输入框
        scrollTop({
            name: '.back-top',
            time: 500,
            windowScroll: true
        })
        // select2
    });
</script>
<script src="${ctx}/resources/script/plugins/datetime/bootstrap.min.js"></script>
<script src="${ctx}/resources/script/plugins/datetime/bootstrap-datetimepicker.min.js"></script>
<script src="${ctx}/resources/script/plugins/datetime/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${ctx}/resources/script/common/dataSubmitUtil.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/employee/employeeInfo.js"></script>

</body>
</html>