<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>岗位信息列表</title>
</head>
<body>
<form id="deleteForm" action="" method="POST">
    <input type="hidden" name="_method" value="DELETE" />
</form>

    <h2 class="title"><a class="first">用户管理</a><a>>岗位信息管理</a></h2>
    <div class="container-fluid padding-left-20 padding-right-20 margin-top-20">
        <div class="container-fluid main-info ">
            <div class="search padding-top-10 padding-bottom-10 padding-left-10 clearfix">
                <div class="form-group clearfix">
                    <input id="searchParam" type="text" placeholder="输入岗位名称进行检索" class="form-control pull-left margin-right-10"/>
                    <a id="search" href="javascript:void(0)" class="btn btn-info btn-xs pull-left">搜索</a>
                </div>
                <a href="info" class="btn btn-warning btn-xs pull-right margin-right-10">添加岗位</a>
            </div>
            <div class="scroll scroll-h">
                <table class="table" id="positions">
                    <tr>
                        <th w_num="total_line" width="5%;">序号</th>
                        <th w_index="positionCode" width="20%;">岗位编码</th>
                        <th w_index="positionName" width="20%;">岗位名称</th>
                        <th w_index="description" width="40%;">岗位描述</th>
                        <th w_render="operation" width="15%;">操作</th>
                    </tr>
                </table>
            </div>
        </div>
    </div>


<script src="${ctx}/resources/script/plugins/bsGrid/bsgrid.all.min.js"></script>
<script src="${ctx}/resources/script/plugins/bsGrid/grid.zh-CN.js"></script>
<script src="${ctx}/resources/script/models/utils.js"></script>
<script src="${ctx}/resources/script/models/sysmgr/position/positionList.js"></script>

</body>
</html>