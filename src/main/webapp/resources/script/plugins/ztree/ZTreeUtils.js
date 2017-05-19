var zTree; // 全局树对象
var zNodes; // 节点

var frontSwitch; // 前台控制按钮作用  如果 true，以下按钮前台起作用
var addBtn; // 添加按钮
var removeBtn; //删除按钮
var expandBtn; // 展开按钮
var currentTreeNode; // 当前页面的全局变量，用于存储当前点击的树节点

//树的配置
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
    }
};

var zzcTree = {
	// 初始化树
	initTree:function (ztreeId, initUrl,addButton,removeButton,expandButton,fronts) {
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
	},
	addEvent:function(treeNode){
		if(!window.addInfo){
			return;
		}	
		addInfo(treeNode);
	},
	removeEvent:function(treeNode){
		if(!window.removeNode){
			return;
		}
		removeNode(treeNode);
	},
	// 获取节点的子节点
	getChildNode : function (treeNode,ck) {
		// 是否点击进入，如果true子节点已加载过，不在加载
		//现保存更新用此方法，后续改进
	    if (!treeNode.childURL || ck) {
	        return;
	    }
	    $.ajax({
	        url: ctx + treeNode.childURL,
	        type: 'get',
	        data: {
	            t: new Date().valueOf()
	        },
	        dataType: 'text',
	        async: true,
	        success: function (data) {
	        	zTree.removeChildNodes(treeNode);
	        	if(data == ''){
	        		
	        	}else{
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
	},
	getNodeInfo : function (treeNode) {
		//详细信息URL为空
	    if (!treeNode.infoURL) {
	        return;
	    }
	    $.ajax({
	        url: ctx + treeNode.infoURL,
	        type: 'get',
	        data: {
	            t: new Date().valueOf()
	        },
	        dataType: 'text',
	        async: false,
	        success: function (data) {
	        	if(window.showNodeInfo){
		            showNodeInfo(JSON.parse(data));
	        	}
	        },
	        error: function () {
	            zcal({
	                type:'error',
	                text:'查询信息失败!'
	            });
	        }
	    });
	},
	selectNodeById : function(nodeId){
		var node = zTree.getNodeByParam("id",nodeId);
		zTree.selectNode(node,false);
		currentTreeNode = node;
	},
	selectNodeByNode :function(treeNode){
		var node = zTree.getNodeByParam("id",treeNode.id);
		zTree.selectNode(node,false);
		currentTreeNode = node;
	},
	getChildNodeById :function(nodeId){
		//根据ID更新节点
		var that = this;
		var node = zTree.getNodeByParam("id",nodeId);
		//保存调用的更新方法
		that.getChildNode(node,false);
	},
	updateNodeById : function(nodeId){
		//更新当前节点 暂时不用，未来将使用更新某个节点的方法
		var node = zTree.getNodeByParam("id",nodeId);
		if(!node.infoURL){
			return;
		}
		$.ajax({
	        url: ctx + node.infoURL,
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
	},
	setExpandBySelfNode :function(node,isShow){
		// 添加或删除子类，更新父类展开按钮
		if(node.id && isShow){
			$("[selftId="+node.id+"_expand]").css("display",'block');
		}else {
			$("[selftId="+node.id+"_expand]").css("display",'none');
		}
	},
	refreshAndSelected:function(node){
		//刷新当前节点 并重新加载该节点的子节点
		var oldNode = zTree.getNodeByParam("id",node.id);
		var that = this;
		if(oldNode){
			oldNode.name = node.name;
			oldNode.infoURL = node.infoURL;
			oldNode.childURL = node.childURL;
			oldNode.pId = node.pId;
			oldNode.param = node.param;
			oldNode.addBtn = node.addBtn;
			oldNode.expandBtn = node.expandBtn;
			oldNode.removeBtn = node.removeBtn;
			oldNode.level = node.level;
			zTree.updateNode(oldNode);
//			 目前都是放回新的节点，直接更新
//			 品类之前带有属性参数，现在不带，因此不用更新子节点
//			that.getChildNode(node,false);
		}else{
			var parentNode = zTree.getNodeByParam("id",node.pId);
			//添加该节点
			zTree.addNodes(parentNode, node);
			//选中该节点
			that.selectNodeById(node.id);
			//父节点展开按钮显示
			that.setExpandBySelfNode(parentNode,true);
			that.setRemoveBtn(parentNode,false);
		}
	},
	removeZtreeNode:function(treeNode){
		//删除节点
		zTree.removeNode(treeNode);
//		隐藏父节点张开按钮
		var that = this;
		var parentNode = zTree.getNodeByParam("id",treeNode.pId);
		if(parentNode.children.length<=0){
			that.setExpandBySelfNode(parentNode,false);
			that.setRemoveBtn(parentNode,true);
		}
		zTreeOnClick([], parentNode.id, parentNode);
		that.selectNodeByNode(parentNode);
	},
	setRemoveBtn:function(treeNode,isShow){
		if(!isShow){
			$('#removeBtn_'+treeNode.tId).remove();
		}else if(isShow && ($('#removeBtn_'+treeNode.tId).length<=0)){
			var removeStr = "<span class='btn-remove' id='removeBtn_" + treeNode.tId
	        + "' title='remove node' onclick=''>--</span>";
			$('#'+treeNode.tId+'_a').append(removeStr);
			var nremovebtn = $("#removeBtn_" + treeNode.tId);
			//绑定删除事件
			nremovebtn.bind("click", function (e) {
		         e.stopPropagation();
		         zzcTree.selectNodeByNode(treeNode,false);
		         currentTreeNode = treeNode;
		         zzcTree.removeEvent(treeNode);
		     });
		} 
	},
	InfoAndSelectNodeById:function(treeId){
		var that = this;
		that.selectNodeById(treeId);
		that.getNodeInfo(currentTreeNode);
	}
};

// 按钮控制  自定义按钮
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
    //绑定添加事件
	naddbtn.bind("click", function (e) {
        e.stopPropagation();
        zzcTree.selectNodeByNode(treeNode,false);
        currentTreeNode = treeNode;
        zzcTree.addEvent(treeNode);
    });
	//绑定删除事件
	nremovebtn.bind("click", function (e) {
         e.stopPropagation();
         zzcTree.selectNodeByNode(treeNode,false);
         currentTreeNode = treeNode;
         zzcTree.removeEvent(treeNode);
     });
};


$(document).on('click', '.tree .switch', function () {
  $(this).siblings('a').click();
});
// 树节点点击事件
function zTreeOnClick(event, treeId, treeNode) {
	currentTreeNode = treeNode;
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
