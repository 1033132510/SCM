<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!-- 主体切换的内容 -->
<h2 class="title">zTree Demo</h2>

<div class="container-fluid tree-preview padding-left-20 padding-right-20 margin-top-20">
	<div id="myztree"  class="tree scroll scroll-v"></div>
</div>
<link rel="stylesheet" href="${ctx}/resources/script/plugins/ztree/zTreeStyle.css"/>
<script type="text/javascript" src="${ctx }/resources/script/plugins/ztree/jquery.ztree.all-3.5.min.js"></script>
<script src="${ctx}/resources/script/plugins/ztree/ztreeUtil.js"></script>
<script>
$(function(){
	//初始化节点
	zzcTree.initTree('myztree','/sysmgr/zTree/initzTree');
	//获得root节点的孩子节点
	zzcTree.getChildNode(zTree.getNodes()[0]);
});

function showNodeInfo(data){
	//显示详细信息页面
	alert('显示详细信息');
}

function addInfo(treeNode){
	//添加一条方法
	alert('添加一条');
}
/*  新增节点 
 * 参数 父节点  新节点对象
 */
 //zzcTree.addNode(parentNode,newNode);

</script>