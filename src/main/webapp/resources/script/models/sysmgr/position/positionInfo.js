$(function () {
    jQuery.validator.addMethod("positionNameHasExist", function(value, element) {
        return this.optional(element) || !checkPositionNameIsExist();
    }, "该岗位名称系统已经存在");


    var validator = $("#positionForm").validate();

    $('#submitBtn').click(function () {
        if (validator.form()) {
            var submitOption = {
                url: ctx + '/sysmgr/position/info',
                type: 'POST',
                contentType: 'application/json;charset=UTF-8',
                dataType: 'json',
                data: JSON.stringify($("#positionForm").serializeObject()),
                //beforeSubmit: function validateForm() {
                //    $("#positionForm").validate();
                //},
                success: function showResponse(result) {
                    if (result.success) {
                        handleFormSubmitResultForContinue(result, '继续添加岗位信息', continueFuncCallBack);
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
var continueFuncCallBack = function () {
    window.location.href = ctx + '/sysmgr/position/info';
};

/**
 * 按照角色名称检查角色是否已经存在
 * @param account
 */
var checkPositionNameIsExist = function () {
    var oldPositionName = $('#oldPositionName').val();
    var positionName = $('#positionName').val();
    if (positionName == oldPositionName) {
        return false;
    }
    var checkResult = true;
    $.ajax({
        url : ctx + '/sysmgr/position/checkPositionNameIsExist/' + positionName,
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
