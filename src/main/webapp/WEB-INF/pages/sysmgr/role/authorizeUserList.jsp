<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>授权用户列表</title>
</head>
<body>
<form id="deleteForm" action="" method="POST">
    <input type="hidden" name="_method" value="DELETE"/>
</form>
<h2 class="title"><a class="first">用户角色授权维护</a></h2>
<input type="hidden" value="${role.id}" id="roleId">
<div class="container-fluid padding-left-20 padding-right-10 margin-top-20 userRoleAuthorize">
    <div class="container-fluid main-info type-manage clearfix post-content">
        <h3 class="head"><a>角色信息</a></h3>

        <div class="padding-bottom-30 padding-left-10 padding-right-30 clearfix row">

            <div class="scroll scroll-h">
                <table class="table table-bordered margin-right-10 text-left">
                    <tbody>
                    <tr>
                        <td>角色编码：${role.roleCode}</td>
                        <td>角色名称：${role.roleName}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <h3 class="head" id="testInnerHtml"><a>用户授权</a></h3>

        <div class="tree-preview padding-right-20">
            <div class="tree scroll scroll-v margin-top-20" style="background-color: white;">
                <h3 class="header padding-left-10" id="testInnerHtml"><a>用户授权</a></h3>

                <div id="companyOrgTree"></div>
            </div>
            <div class="row">
                <div class="accredit margin-top-20 margin-bottom-20 margin-right-20">
                    <div class="header text-center">待选人员</div>
                    <form>
                        <div class="form-group checkbox" id="dxUser">
                            <label for="male" class="checked">
                                <span>请点选左边组织树确定待选人员</span>
                            </label>
                            <%--<label for="female" class="un-click">
                                <input type="checkbox" id="" name="name"><i></i><span>谢霆锋</span>
                            </label>
                            <label for="female">
                                <input type="checkbox" id="" name="name"><i></i><span>陈冠希</span>
                            </label>
                            <label for="female">
                                <input type="checkbox" id="" name="name"><i></i><span>谢霆锋</span>
                            </label>
                            <label for="female">
                                <input type="checkbox" id="" name="name"><i></i><span>陈冠希</span>
                            </label>
                            <label for="female">
                                <input type="radio" id="" name="name"><i></i><span>谢霆锋</span>
                            </label>--%>
                        </div>
                        <div class="header text-center" id="addRelationBtn" style="display: none;background-color:dodgerblue"><a>添加所选人员</a></div>
                        <%--<div class="text-center margin-bottom-10" id="addRelationBtn" style="display: none">
                            <a id="" class="btn btn-warning margin-top-10">添加所选人员</a>
                        </div>--%>
                    </form>
                </div>
                <div class="accredit margin-top-20 margin-bottom-20">
                    <div class="header text-center">已选人员</div>
                    <form>
                        <div class="form-group checkbox" id="yxUser">
                            <c:if test="${employees.size() > 0}">
                                <c:forEach items="${employees}" var="roleEmployee">
                                    <label onclick="removeBtnCtrl();">
                                        <input type="checkbox" value="${roleEmployee.id}" name="${roleEmployee.employeeName}"><i></i><span>${roleEmployee.employeeName}</span>
                                    </label>
                                </c:forEach>
                            </c:if>
                            <c:if test="${employees.size() == 0}">
                                <label for="male" class="checked">
                                    <span>当前角色暂未授权用户</span>
                                </label>
                            </c:if>
                            <%--<label for="male" class="checked">
                                <input type="checkbox" id="" name="name" checked><i></i><span>陈冠希</span>
                            </label>
                            <label for="female" class="un-click">
                                <input type="checkbox" id="" name="name"><i></i><span>谢霆锋</span>
                            </label>
                            <label for="female">
                                <input type="checkbox" id="" name="name"><i></i><span>陈冠希</span>
                            </label>
                            <label for="female">
                                <input type="checkbox" id="" name="name"><i></i><span>谢霆锋</span>
                            </label>
                            <label for="female">
                                <input type="checkbox" id="" name="name"><i></i><span>陈冠希</span>
                            </label>
                            <label for="female">
                                <input type="radio" id="" name="name"><i></i><span>谢霆锋</span>
                            </label>--%>
                        </div>
                    </form>
                    <div class="header text-center" id="removeRelationBtn" style="display:none;background-color:#ff0000"><a>删除人员</a></div>
                    <%--<div class="text-center margin-bottom-10" id="removeRelationDiv" style="display:none">
                        <a id="removeRelationBtn" class="btn btn-danger margin-top-10">删除人员</a>
                    </div>--%>
                </div>
            </div>
            <div class="row margin-top-30 padding-top-30">
                <div class="accredit-btn">
                    <div class="text-right margin-bottom-30">
                    <a href="javascript:void(0)" onclick="submitAuthorize();" class="btn btn-info">保存</a>
                    </div>
                </div>
                <div class="accredit-btn margin-left-20">
                    <div class="text-left margin-bottom-30">
                    <a href="${ctx}/sysmgr/role/view" class="btn btn-warning">返回</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%--<script id="orgEmployees" type="text/html">
    <div class="form-group checkbox">
        <!if (list.length > 1) { !>

        <!-- 循环语句 for-->
        <!for (var i = 0; i < list.length; i++) {
            var item = list[i];
        !>
        <label
            <!if (item.isChecked == 1) { !>
                class="checked"
            <!} else if (item.isChecked == 2) { !>
                class="checked"
            <!} else {!>
                class = ""
            <!}!>
        >
            <input type="checkbox" name="name" value="<!=item.employee.id!>"><i></i><span><!=item.employee.employeeName!></span>
        </label>
        <!}!>

        <!} else {!>
        <h2>该部门下没有员工</h2>

        <!}!>
    </div>

    <!if (list.length > 1) { !>
        <div class="text-center margin-bottom-10">
            <a onclick="addRelation()" class="btn btn-warning margin-top-10">添加所选人员</a>
        </div>
    <!}!>
</script>
<script id="roleEmployees" type="text/html">

</script>--%>


<script src="${ctx }/resources/script/plugins/ztree/jquery.ztree.all-3.5.min.js"></script>
<script src="${ctx}/resources/script/plugins/ztree/ZTreeUtils.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/plugins/baiduTemplate/baiduTemplate.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/role/roleAuthorizeList.js"></script>

</body>
</html>