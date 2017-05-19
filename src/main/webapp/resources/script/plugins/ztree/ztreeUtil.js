var zTree;
var zNodes;
var addBtn;
var removeBtn;
var expandBtn;
var frontSwitch;

var zzcTree = function () {
};
// 树的配置
var setting = {
    view: {
        addDiyDom: addHoverDom,
        dblClickExpand: false,
        selectedMulti: false,
        nameIsHTML: true,
        showIcon: false
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pId"
        }
    },
    async: {},
    callback: {
        onClick: zTreeOnClick,
        //onDblClick:zTreeOnDblClick
        // onExpand : zTreeOnExpand
    }
};

function zTreeOnDblClick(event, treeId, treeNode) {
    dbClick(event, treeId, treeNode);
};
// 初始化树
zzcTree.initTree = function (ztreeId, initUrl,addButton,removeButton,expandButton,fronts) {
    if (!initUrl) {
        return;
    }
    if(fronts){
    	frontSwitch = fronts;
    	addBtn = addButton;
        removeBtn = removeButton;
        expandBtn = expandButton;
    }
    
    $.ajax({
        url: ctx + initUrl,
        contentType:"application/x-www-form-urlencoded;charset=utf-8",
        type: 'get',
        data: 't=' + new Date().valueOf(),
        dataType: 'text',
        async: false,
        success: function (data) {
            if (data == 'fail') {
                zcal({
                    type:'error',
                    text:'加载数据失败!'
                });
                return;
            } else {
                zNodes = JSON.parse(data);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            zcal({
                type:'error',
                text:'服务器系统异常!'
            });
        }
    });
    zTree = $.fn.zTree.init($('#' + ztreeId), setting, zNodes);
}

// 新增节点页面
var newCount = 1;
function addHoverDom(zTree, treeNode, e) {
	$('#'+treeNode.tId+'_switch').attr("selftId",treeNode.id+"_expand");
	var addStr = "";
	var sObj = $("#" + treeNode.tId + "_span");
	if(frontSwitch){
		if(((!expandBtn) && treeNode.level !=0) || treeNode.last){
			$('#'+treeNode.tId+'_switch').css("display",'none');
		}
	    
	    if(addBtn){
			addStr = "<span class='btn-add' id='addBtn_" + treeNode.tId
			+ "' title='add node' onclick=''></span>";
	    }
	    // 0节点不能删除 最后节点才能删除
	    if(removeBtn && treeNode.level !=0 && treeNode.last){
			addStr += "<span class='btn-remove' id='removeBtn_" + treeNode.tId
	        + "' title='remove node' onclick=''>--</span>";
	    }
	}else{
		
		if((!treeNode.expandBtn) && treeNode.level !=0){
			$('#'+treeNode.tId+'_switch').css("display",'none');
		}
	    
	    if(treeNode.addBtn){
			addStr = "<span class='btn-add' id='addBtn_" + treeNode.tId
			+ "' title='add node' onclick=''></span>";
	    }
	    if(treeNode.removeBtn && treeNode.level !=0){
			addStr += "<span class='btn-remove' id='removeBtn_" + treeNode.tId
	        + "' title='remove node' onclick=''>--</span>";
	    }
	   
	}
	sObj.after(addStr);
    var naddbtn = $("#addBtn_" + treeNode.tId);
    var nremovebtn = $("#removeBtn_" + treeNode.tId);
//    if (treeNode.addBtn) {
    	naddbtn.bind("click", function (e) {
            e.stopPropagation();
            addInfoById(treeNode);
        });
//    }
//    if(treeNode.removeBtn){
    	nremovebtn.bind("click", function (e) {
             e.stopPropagation();
             console.log();
             removeInfoById(treeNode);
         });
//    }
};
function removeInfoById(treeNode){
	var node = zTree.getNodeByParam("id",treeNode.id);
    removeNode(node);
}

function addInfoById(treeNode){
	var node = zTree.getNodeByParam("id",treeNode.id);
    addInfo(node);
}
// 展开
function zTreeOnExpand(event, treeId, treeNode) {
    zzcTree.getChildNode(treeNode);
};

$(document).on('click', '.tree .switch', function () {
  $(this).siblings('a').click();
});
// 树节点点击事件
function zTreeOnClick(event, treeId, treeNode) {
	zzcTree.getNodeInfo(treeNode);
	if (treeNode.last) {
        return;
    }else{
    	var childNodes = zTree.getNodesByParam("pId", treeNode.id);
    	if(childNodes.length>0){
    		zzcTree.getChildNode(treeNode,true);
    	}else{
    		zzcTree.getChildNode(treeNode,false);
    	}
    }
};

// 获取节点的子节点
zzcTree.getChildNode = function (treeNode,ck) {
    if (!treeNode.childURL) {
        return;
    }
    if(ck){
    	return;
    }
    $.ajax({
        url: ctx + treeNode.childURL,
        contentType:"application/x-www-form-urlencoded;charset=utf-8",
        type: 'get',
        data: {
            t: new Date().valueOf()
        },
        dataType: 'text',
        async: false,
        success: function (data) {
        	if(data==''){
        		
        	}else{
        		zTree.removeChildNodes(treeNode);
                var nodeData = JSON.parse(data);
                if (nodeData.length > 0) {
                    zTree.addNodes(treeNode, nodeData);
                }
        	}
        },
        error: function () {
            zcal({
                type:'error',
                text:'查询信息失败!'
            });
        }
    });
};

zzcTree.getNodeInfo = function (treeNode) {
    if (!treeNode.infoURL) {
        return;
    }
    $.ajax({
        url: ctx + treeNode.infoURL,
        contentType:"application/x-www-form-urlencoded;charset=utf-8",
        type: 'get',
        data: {
            t: new Date().valueOf()
        },
        dataType: 'text',
        async: false,
        success: function (data) {
            showNodeInfo(JSON.parse(data), treeNode);
        },
        error: function () {
            zcal({
                type:'error',
                text:'查询信息失败!'
            });
        }
    });
};

zzcTree.selectNodeById =function(nodeId){
	var node = zTree.getNodeByParam("id",nodeId);
	zTree.selectNode(node);
}


zzcTree.getChildNodeById = function(nodeId){
	var node = zTree.getNodeByParam("id",nodeId);
	zzcTree.getChildNode(node);
}

zzcTree.updateNodeById = function(nodeId){
	var node = zTree.getNodeByParam("id",nodeId);
	if(!node.infoURL){
		return;
	}
	$.ajax({
        url: ctx + node.infoURL,
        contentType:"application/x-www-form-urlencoded;charset=utf-8",
        type: 'get',
        data: {
            t: new Date().valueOf()
        },
        async: false,
        success: function (data) {
        	node.name = data.name;
        	node.param = data.param;
            zTree.updateNode(node);
        },
        error: function () {
            zcal({
                type:'error',
                text:'查询信息失败!'
            });
        }
    });
}
zzcTree.removeNode=function(nodeId){
	var node = zTree.getNodeByParam("id",nodeId);
	zTree.removeNode(node);
}

zzcTree.addExpandByChildId =function(nodeId){
	var node = zTree.getNodeByParam("id",nodeId);
	$("[selftid="+node.pId+"_expand]").css("display",'block');
}
