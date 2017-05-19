$(function(){

    jQuery.validator.addMethod("validPasswords", function(value, element) {
        return this.optional(element) || validPasswords();
    }, "两次密码不一致");

    jQuery.validator.addMethod("checkOldPassword", function (value, element) {
        return this.optional(element) || checkOldPassword();
    }, "原密码不正确");
});

/**
 * 校验密码一致性
 * @returns {boolean}
 */
var validPasswords = function () {
    var password = $("#password").val();
    var passwordRepeat = $("#passwordRepeat").val();

    if (password == passwordRepeat) {
        return true;
    } else {
        return false;
    }
};

/**
 * 校验旧密码的正确性
 */
var checkOldPassword = function () {
    var oldPassword = $('#oldPassword').val();
    var checkResult = true;
    $.ajax({
        url : ctx + '/account/sysmgr/checkPassword',
        contentType : 'application/json;charset=UTF-8',
        type : 'POST',
        data : oldPassword,
        dataType : 'json',
        async: false,
        success : function(result) {
            checkResult = result;
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


/**
 * 保存密码数据
 */
var savePassword = function () {
    var validator = $("#passwordForm").validate();
    if (validator.form()){
        // 组装数据
        var baseUser = {};
        baseUser.userPwd = $('#password').val();
        // 提交数据
        $.ajax({
            url : ctx + '/account/sysmgr/password',
            contentType : 'application/json;charset=UTF-8',
            type : 'POST',
            data : JSON.stringify(baseUser),
            dataType : 'json',
            success : function(result) {
                if (result) {
                    zcal({
                        type: 'success',
                        title: '系统提示',
                        text: '修改成功'
                    });
                } else {
                    zcal({
                        type: 'error',
                        title: '系统提示',
                        text: '修改失败'
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
    }
};
