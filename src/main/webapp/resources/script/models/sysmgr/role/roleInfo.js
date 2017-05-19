$(function(){
    jQuery.validator.addMethod("roleCodeHasExist", function(value, element) {
        return this.optional(element) || !checkRoleCodeIsExist();
    }, "该角色编码系统已经存在");
    jQuery.validator.addMethod("roleNameHasExist", function(value, element) {
        return this.optional(element) || !checkRoleNameIsExist();
    }, "该角色名称系统已经存在");


    var validator = $("#roleForm").validate();

    $('#submitBtn').click(function () {
        if (validator.form()) {
            var submitOption = {
                url: ctx + '/sysmgr/role/info',
                type: 'POST',
                contentType: 'application/json;charset=UTF-8',
                dataType: 'json',
                data: JSON.stringify($("#roleForm").serializeObject()),
                success: function showResponse(result) {
                    if (result.success) {
                        handleFormSubmitResultForContinue(result, '继续添加角色信息', continueFuncCallBack);

                        $('#oldRoleCode').val(result.resultData.roleCode);
                        $('#oldRoleName').val(result.resultData.roleName);
                    } else {
                        // 处理错误信息
                        handleError(result);
                    }
                },
                error : function () {
                    zcal({
                        type: 'error',
                        title: '服务器异常'
                    });
                }
            };
            $.ajax(submitOption);
        }
    });
});

/**
 * 保存成功后的继续操作的回调函数
 */
var continueFuncCallBack = function (result) {
    window.location.href = ctx + '/sysmgr/role/info';
};

/**
 * 按照角色编码检查角色是否已经存在
 * @param account
 */
var checkRoleCodeIsExist = function () {
    var oldRoleCode = $('#oldRoleCode').val();
    var roleCode = $('#roleCode').val();
    if (roleCode == oldRoleCode) {
        return false;
    }
    var checkResult = true;
    $.ajax({
        url : ctx + '/sysmgr/role/checkRoleCodeIsExist/' + roleCode,
        type : 'get',
        async : false,
        dataType: 'json',
        async:false,
        success : function(data) {
            checkResult = data;
        },
        error : function() {
            alert('服务器系统异常');
        }
    });
    return checkResult;
};
/**
 * 按照角色名称检查角色是否已经存在
 * @param account
 */
var checkRoleNameIsExist = function () {
    var oldRoleName = $('#oldRoleName').val();
    var roleName = $('#roleName').val();
    if (roleName == oldRoleName) {
        return false;
    }
    var checkResult = true;
    $.ajax({
        url : ctx + '/sysmgr/role/checkRoleNameIsExist/' + roleName,
        type : 'get',
        async : false,
        dataType: 'json',
        async:false,
        success : function(data) {
            checkResult = data;
        },
        error : function() {
            zcal({
                type: 'error',
                title: '服务器异常'
            });
        }
    });
    return checkResult;
};



