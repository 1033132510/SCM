$(function () {
    //初始化节点
    zzcTree.initTree('companyOrgTree', '/sysmgr/companyOrg/initTree', false, false, true, true);
    //获得root节点的孩子节点
    zzcTree.getChildNode(zTree.getNodes()[0]);

});


// ***************** 角色分配 *****************
var yxList = {}, dxList = {};
var yxusers = $("#yxUser").find("input[type='checkbox']");
yxusers.each(function (i, val) {
    yxList[yxusers[i].value] = yxusers[i].name;
});

/**
 * 树节点点击回调
 * @param data
 */
var showNodeInfo = function (data) {
    getEmployeesByOrgId(data.id);
};


/**
 * 根据组织Id查找组织下面的员工
 */
var getEmployeesByOrgId = function (orgId) {
    $.ajax({
        url: ctx + '/sysmgr/employee/org/' + orgId,
        type: 'get',
        async: false,
        dataType: 'json',
        async: false,
        success: function (data) {
            //createEmployeesHtml('orgEmployees', 'orgEmployeeFormDiv', data);
            $('#dxUser').empty();
            for (var i = 0; i < data.length; i++) {
                dxList[data[i].id] = data[i].employeeName;

                // 是否已选
                var isyx = "", isyxText = "";
                if (yxList[data[i].id] != undefined) {
                    isyx = "un-click";
                    isyxText = "<b > 【已 选】 </b>";
                }

                var str = '<label class="' + isyx + ' "><input data-mode="add" class="' + isyx + '" type="checkbox" id="" value="' + data[i].id + '" name="'
                    + data[i].employeeName + '"><i></i><span>'
                    + data[i].employeeName + '</span>' + isyxText + '</label>';

                $("#dxUser").append(str);
            }
            if (data.length > 0) {
                $('#addRelationBtn').show();
            } else {
                $('#dxUser').append('<h2>该部门下没有员工</h2>');
                $('#addRelationBtn').hide();
            }
        },
        error: function () {
            alert('服务器系统异常');
        }
    });
};
/**
 * 添加人员
 */
$('#addRelationBtn').click(function () {
    var isyx = "un-click", isyxText = "<b > 【已 选】 </b>";
    var ck = $("#dxUser").find("input[type='checkbox']:checked").not("[class='un-click']");

    ck.each(function (i, val) {
        ck.eq(i).attr('class', isyx);
        ck.eq(i).prop('checked', false);
        ck.eq(i).parent().attr('class', isyx);
        ck.eq(i).parent().append(isyxText);
        yxList[val.value] = val.name;

        var str = '<label onclick="removeBtnCtrl();">' +
            '<input type="checkbox" value="' + val.value + '" name="' + val.name + '"><i></i><span>' + val.name + '</span>' +
            '</label>';

        $("#yxUser").append(str);
    });
});

/**
 * 删除人员
 */
$('#removeRelationBtn').click(function () {
    var ck = $("#yxUser").find("input[type='checkbox']:checked");
    ck.closest('label').remove();
    ck.each(function (i, val) {
        delete yxList[val.value];
        var dx = $("#dxUser").find("input[value='" + val.value + "']");
        dx.parent().removeAttr("class");
        dx.removeAttr("class");
        dx.siblings("b").remove();
        //dx.removeAttr("class").prop("checked",false).siblings(".badge").remove();
    });
    removeBtnCtrl();
});


$(document).on('click', '.userRoleAuthorize label', function () {
    var mode = $(this).data("mode");
    if (mode == "add") {
        var ck = $("#dxUser").find("input[type='checkbox']").is(":checked");
        if (ck) {
            $("#addRelationBtn").show();
        } else {
            $("#addRelationBtn").hide();
        }
    } else if (mode == undefined) {
        var ck = $("#yxUser").find("input[type='checkbox']").is(":checked");
        if (ck) {
            $("#removeRelationBtn").show();
        } else {
            $("#removeRelationBtn").hide();
        }
    }
});

/**
 * 控制删除用户按钮的显示效果
 */
var removeBtnCtrl = function () {
    setTimeout(function () {
        var ck = $("#yxUser").find("input[type='checkbox']").is(":checked");
        if (ck) {
            $("#removeRelationBtn").show();
        } else {
            $("#removeRelationBtn").hide();
        }
    }, 100);
};

/**
 * 提交分配
 */
var submitAuthorize = function () {
    var userIds = [];
    var selectedUsers = $("#yxUser").find("input[type=checkbox]");

    selectedUsers.each(function (i, val) {
        userIds.push(val.value);
    });

    var role = {};
    role.id = $('#roleId').val();
    role.userIds = userIds;

    $.ajax({
        url : ctx + '/sysmgr/role/authorizeUsers',
        contentType : 'application/json;charset=UTF-8',
        type : 'POST',
        data : JSON.stringify(role),
        dataType : 'json',
        success : function(result) {
            if (result) {
                zcal({
                    type: 'success',
                    title: '系统提示',
                    text: '保存成功'
                });
            } else {
                zcal({
                    type: 'error',
                    title: '失败信息',
                    text: '用户还有未完成的审批单，授权失败'
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
 * 创建员工数据列表html
 * @param data
 */
/*var createEmployeesHtml = function (templateId, htmlDiv, data) {
 var bt = baidu.template;
 var employees = {};
 var orgEmployees = [];

 for (var i = 0; i < data.length; i++) {
 var orgEmployee = {
 employee: null,
 isChecked: 0
 };

 orgEmployee.employee = data[i];
 orgEmployee.isChecked = 1;
 orgEmployees.push(orgEmployee);
 }

 employees.list = orgEmployees;
 var html = bt(templateId, employees);

 $('#' + htmlDiv).html(html);
 };*/



