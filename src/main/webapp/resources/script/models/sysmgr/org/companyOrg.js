$(function(){
    jQuery.validator.addMethod("hasExist", function (value, element) {
        return this.optional(element) || !checkOrgIsExist();
    }, "该组织已经存在");

    // 初始化节点
    zzcTree.initTree('companyTree', '/sysmgr/companyOrg/initTree', true, true, true, true);
    // 获得root节点的孩子节点
    zzcTree.getChildNode(zTree.getNodes()[0]);

    $('#companyOrgForm').validate({
        onsubmit:true,
        // 通过回调
        submitHandler: function(form) {
            saveOrg();
        },
        // 不通过回调
        invalidHandler: function(form, validator) {
            return false;
        }
    });
});

/**
 * 树节点点击回调
 * @param data
 */
var showNodeInfo = function (data) {
    $('#orgInfoDiv').show();
    $('#orgName').val(data.orgName);
    $('#oldOrgName').val(data.orgName);
    $('#orgClass option[value="' + data.orgClass + '"]').attr("selected", true);
    $('#description').val(data.description);
    $('#phone').val(data.phone);
    $('#id').val(data.id);
    $('#oldOrgId').val(data.id);
    $('#orgLevel').val(data.orgLevel);

    var parentOrgName = "";
    if (!(data.parentOrg === undefined)) {
        $('#parentOrgId').val(data.parentOrg.id);
        parentOrgName = data.parentOrg.orgName;
    } else {
        parentOrgName = "无上级组织";
    }

    $('#parentOrgName').val(parentOrgName);
};


/**
 * 添加组织
 * @param treeNode
 */
var addInfo = function (treeNode) {
    $('#reset').click();
    $('#orgLevel').val(treeNode.level + 1);
    $('#parentOrgId').val(treeNode.id);
    $('#parentOrgName').val(treeNode.name);
    $('#id').val("");
    $('#orgClass option[value=""]').attr("selected", true);
    // 设置当前节点
    $('#orgInfoDiv').show();
};

/**
 * 保存组织数据
 */
var saveOrg = function () {
    // 组装数据
    var companyOrg = {};
    companyOrg.orgName = $('#orgName').val();
    companyOrg.orgClass = $('#orgClass').val();
    companyOrg.phone = $('#phone').val();
    companyOrg.description = $('#description').val();
    companyOrg.id = $('#id').val();
    companyOrg.orgLevel = $('#orgLevel').val();

    var parentOrg = {};
    parentOrg.id = $('#parentOrgId').val();
    companyOrg.parentOrg = parentOrg;
    // 提交数据
    $.ajax({
        url : ctx + '/sysmgr/companyOrg/saveOrUpdate',
        contentType : 'application/json;charset=UTF-8',
        type : 'POST',
        data : JSON.stringify(companyOrg),
        dataType : 'json',
        success : function(result) {
            //handleFormSubmitResultForContinue(result, '继续添加组织信息', continueFuncCallBack);
            if(result.success){
            	zcal({
                    type: 'success',
                    title: '系统提示',
                    text: '保存成功'
                });
            	zzcTree.refreshAndSelected(result.treeNode);
            	$('#id').val(result.orgId);
            	// 更新
                $('#oldOrgName').val($('#orgName').val());
            }
        },
        error : function() {
            zcal({
                type: 'error',
                title: '服务器异常'
            });
        }
    });
};
/**
 * 删除树节点
 * @param treeNode
 */
var removeNode = function (treeNode) {
    //console.log(treeNode.id);
    //console.log(treeNode.name);

    $.ajax({
        url : ctx + '/sysmgr/companyOrg/deleteOrg/' + treeNode.id,
        contentType : 'application/json;charset=UTF-8',
        type : 'POST',
        dataType : 'json',
        success : function(result) {
            if (result.success) {
                zcal({
                    type: 'success',
                    title: '系统提示',
                    text: '删除成功'
                });
                zzcTree.removeZtreeNode(treeNode);
            } else {
                zcal({
                    type: 'error',
                    title: '系统提示',
                    text: '删除失败，' + result.description
                });
            }
        },
        error : function() {
            zcal({
                type: 'error',
                title: '服务器异常'
            });
        }
    });
};

/**
 * 回调：继续添加组织
 */
var continueFuncCallBack = function () {
    addInfo(currentTreeNode);
};



/**
 * 检查组织是否已经存在
 * @param account
 */
var checkOrgIsExist = function () {
    var currentOrgId = $('#id').val();
    // 修改操作
    if (currentOrgId.length > 0) {
        var oldOrgName = $('#oldOrgName').val();
        var currentOrgName = $('#orgName').val();
        if (oldOrgName == currentOrgName) {
            return false;
        }
    }
    // 组装数据
    var companyOrg = {};
    companyOrg.orgName = $('#orgName').val();
    var parentOrg = {};
    parentOrg.id = $('#parentOrgId').val();
    companyOrg.parentOrg = parentOrg;

    var checkResult = true;
    $.ajax({
        url: ctx + '/sysmgr/companyOrg/checkOrgNameIsExist',
        contentType : 'application/json;charset=UTF-8',
        type: 'post',
        async: false,
        dataType: 'json',
        async: false,
        data : JSON.stringify(companyOrg),
        success: function (data) {
            checkResult = data.success;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            zcal({
                type: 'error',
                title: '服务器异常'
            });
        }
    });

    return checkResult;
};

