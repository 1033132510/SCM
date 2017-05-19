<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>角色信息列表</title>
</head>
<body>
<form id="deleteForm" action="" method="POST">
    <input type="hidden" name="_method" value="DELETE" />
</form>

    <h2 class="title"><a class="first">用户管理</a><a>>角色信息管理</a></h2>
    <div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
        <div class="container-fluid main-info ">
            <div class="search padding-top-10 padding-bottom-10 padding-left-10 clearfix">
                <div class="form-group pull-left margin-right-25">
                    <input id="searchParam" type="text" placeholder="输入角色名称进行检索" class="form-control pull-left margin-right-10"/>
                    <!-- 角色类型 -->
                </div>
                <div class="form-group form-select select-font-4 pull-left padding-left-70 margin-bottom-20 margin-right-25">
                    <label class="pull-left">角色类型：</label>
                    <select class="select" name="" id="roleType">
                        <option value="">所有类型</option>
                        <c:forEach items="${roleTypes}" var="roleType">
                            <option value="${roleType.key}">${roleType.value}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group pull-left">
                    <a id="search" href="javascript:void(0)" class="btn btn-info btn-xs pull-left">搜索</a>
                </div>
                <a href="info" class="btn btn-warning btn-xs pull-right margin-right-10">添加角色</a>
            </div>
            <div class="scroll scroll-h">
                <table class="table" id="roles">
                    <tr>
                        <th w_num="total_line" width="5%;">序号</th>
                        <th w_index="roleCode" width="25%;">角色编码</th>
                        <th w_index="roleName" width="20%;">角色名称</th>
                        <th w_index="roleType"w_render="formatterRoleType" width="10%;">角色类型</th>
                        <th w_index="description" width="20%;">角色描述</th>
                        <th w_render="operation" width="20%;">操作</th>
                    </tr>
                </table>
            </div>
        </div>
    </div>
<script src="${ctx}/resources/script/plugins/bsGrid/bsgrid.all.min.js"></script>
<script src="${ctx}/resources/script/plugins/bsGrid/grid.zh-CN.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/role/roleList.js"></script>

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
</body>
</html>