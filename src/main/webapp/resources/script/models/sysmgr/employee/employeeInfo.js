$(function () {
    jQuery.validator.addMethod("mobile", function (value, element) {
        var tel = /^1[3|4|5|7|8]\d{9}$/;
        return this.optional(element) || (tel.test(value));
    }, "请正确填写您的手机号码");
    jQuery.validator.addMethod("identityCard", function (value, element) {
        var tel = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;
        return this.optional(element) || (tel.test(value));
    }, "请正确填写您的身份证号码");
    jQuery.validator.addMethod("qqValidate", function (value, element) {
        var tel = /^(\d{5,15})$/;
        return this.optional(element) || (tel.test(value));
    }, "请正确填写您的QQ号码");
    jQuery.validator.addMethod("hasExist", function (value, element) {
        return this.optional(element) || !checkAccountIsExist();
    }, "该手机号码已经注册过");

    // 时间选择器
    // data-date="" data-date-format="yyyy年 MM dd日" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd"
    $('#entryDate').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        formatDate: '"yyyy-mm-dd',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0
    });

    var validator = $("#employeeForm").validate();
    $('#submitBtn').click(function () {
        if (validator.form()) {
            var submitOption = {
                url: ctx + '/sysmgr/employee/info',
                type: 'POST',
                contentType: 'application/json;charset=UTF-8',
                dataType: 'json',
                data: JSON.stringify($("#employeeForm").serializeObject()),
                //beforeSubmit: function validateForm() {
                //    $("#positionForm").validate();
                //},
                success: function showResponse(result) {
                    if (result.success) {
                        handleFormSubmitResultForContinue(result, '继续添加员工信息', continueFuncCallBack);

                        $('#oldMobile').val($('#mobile').val());
                        $('#userPwd').val(result.resultData.userPwd);
                        $('#status').val(result.resultData.status);
                        $('#type').val(result.resultData.type);
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

    var mobile = $('#mobile').val();
    if (mobile == 'system_admin' || mobile == 'super_admin') {
        $('#mobile').attr('readonly', 'readonly');
        $('#mobile').removeClass('mobile');
        $('#mobileLabel').html('账号：');
    }
});

/**
 * 复制账户
 */
var copyAccount = function () {
    //如果是新增用户，copy手机信息到账户
    //var id = $("#id").val();
    //if (undefined === id || id.length == 0) {
    var currentUserName = $("#userName").val();
    if (currentUserName != 'system_admin' || currentUserName != 'super_admin') {
        $("#userName").val($("#mobile").val());
    }
    //}
};

/**
 * 保存成功后的继续操作的回调函数
 */
var continueFuncCallBack = function (result) {
    window.location.href = ctx + '/sysmgr/employee/info';
};


/**
 * 检查账户是否已经存在
 * @param account
 */
var checkAccountIsExist = function () {
    var account = $('#mobile').val();
    var oldMobile = $('#oldMobile').val();
    if (oldMobile.length > 0) {
        if (account == oldMobile) {
            return false;
        }
    }

    var accountType = $('#type').val();
    var checkResult = true;
    $.ajax({
        url: ctx + '/account/checkAccountIsExist/' + account + '/' + accountType,
        type: 'get',
        async: false,
        dataType: 'json',
        async: false,
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



