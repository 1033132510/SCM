$(function () {

    jQuery.validator.addMethod("mobile", function (value, element) {
        var tel = /^1[3|4|5|7|8]\d{9}$/;
        return this.optional(element) || (tel.test(value));
    }, "请正确填写您的手机号码");

    jQuery.validator.addMethod("passwordRule", function (value, element) {
        var tel = /^[0-9A-Za-z]{6,16}$/;
        return this.optional(element) || (tel.test(value));
    }, "请输入6-16位密码由字母、数字组成");

    jQuery.validator.addMethod("validPasswords", function (value, element) {
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
}
/**
 * 获取验证码
 */
var codeInterval = 60;
var countdown = codeInterval; // 控制获取验证码频率的计数器
var sendSms = function () {
    if (countdown > 0 && countdown < codeInterval) {
        return;
    }
    countdown--;
    var mobile = $('#mobile').val();

    var tel = /^1[3|4|5|7|8]\d{9}$/;
    if (!(tel.test(mobile))) {
        $('#mobileErrorMsg').show();
        $('#mobileErrorMsg').html('请正确填写您的手机号码');
        // 初始化计数器
        countdown = codeInterval;
        return;
    } else {
        $('#mobileErrorMsg').hide();
        $('#mobileErrorMsg').html('');
    }

    var accountType = $('#accountType').val();

    // 验证手机号码对应的账号是否存在
    $.ajax({
        url: ctx + '/account/password/reset/checkAccount',
        type: 'POST',
        data: {accountType: accountType, mobile: mobile},
        dataType: 'json',
        async: false,
        success: function (result) {
            if (result.success) {
                $('#mobileErrorMsg').hide();
                $('#mobileErrorMsg').html('');
                // 发送短信验证码
                $.ajax({
                    url: ctx + '/account/password/reset/sendsms',
                    contentType: 'application/json;charset=UTF-8',
                    type: 'POST',
                    data: mobile,
                    dataType: 'json',
                    async: false,
                    success: function (result) {
                        if (result.success) {
                            $('#sendSmsBtn').addClass('disabled');
                            // 倒计时

                            var timer = setInterval(function () {
                                // 通知视图模型的变化
                                $('#sendSmsBtn').html('验证码已发送(' + countdown + ')');
                                countdown--;
                            }, 1000);

                            setTimeout(function () {
                                clearInterval(timer);
                                $('#sendSmsBtn').removeClass("disabled");
                                $('#sendSmsBtn').html('获取验证码');
                                countdown = codeInterval;
                            }, codeInterval * 1000);

                        } else {
                            $('#sendSmsBtn').html('获取验证码失败');
                            // 初始化计数器
                            countdown = codeInterval;
                        }
                    },
                    error: function () {
                        zcal({
                            type: 'error',
                            title: '服务器异常'
                        });
                        // 初始化计数器
                        countdown = codeInterval;
                    }
                });
            } else {
                $('#mobileErrorMsg').show();
                $('#mobileErrorMsg').html(result.description);
                // 初始化计数器
                countdown = codeInterval;
            }
        },
        error: function () {
            zcal({
                type: 'error',
                title: '服务器异常'
            });
            // 初始化计数器
            countdown = codeInterval;
        }
    });
};
/**
 * 跳转到第二步
 */
var goToStepTwo = function () {
    // 验证表单数据:手机号的正确性，以及验证码的正确性
    var validateSettings = {
        rules: {
            mobile: {
                required: true
            },
            code: {
                required: true
            }
        },
        messages: {
            mobile: {
                required: '请输入手机号'
            },
            code: {
                required: '请输入验证码'
            }
        }
    }
    var validator = $("#firstStepForm").validate(validateSettings);
    if (validator.form()) {
        var smsCode = {};
        smsCode.mobile = $('#mobile').val();
        smsCode.code = $('#code').val();
        var submitOption = {
            url: ctx + '/account/password/reset/checkSmsCode',
            type: 'POST',
            contentType: 'application/json;charset=UTF-8',
            dataType: 'json',
            data: JSON.stringify(smsCode),
            success: function showResponse(result) {
                if (result.success) {
                    //$('#firstStepForm').hide();
                    //$('#secondStepForm').show();
                    $(".password-sec-step,.first-line").addClass("on");
                    $(".first-step").addClass("hide");
                    $(".sec-step").removeClass("hide");
                    $(".password-first-step").css({
                        "font-weight": "normal",
                        "transform": 'scale(1)',
                        "webkitTransform": 'scale(1)'
                    });
                } else {
                    // 处理错误信息
                    var errorMsg = result.description;
                    $('#codeErrorMsg').show();
                    $('#codeErrorMsg').html(errorMsg);
                }
            },
            error: function () {
                zcal({
                    type: 'error',
                    title: '服务器异常'
                });
            }
        };
        $.ajax(submitOption);
    }
};
/**
 * 清除校验码的错误信息
 */
var cleanCodeErrorMsg = function () {
    var codeErrorMsg = $('#codeErrorMsg').html();
    if (codeErrorMsg.length > 0) {
        $('#codeErrorMsg').hide();
        $('#codeErrorMsg').html('');
    }
};

/**
 * 清除手机号码的错误信息
 */
var cleanMobileErrorMsg = function () {
    var mobileErrorMsg = $('#mobileErrorMsg').html();
    if (mobileErrorMsg.length > 0) {
        $('#mobileErrorMsg').hide();
        $('#mobileErrorMsg').html('');
    }
};

/**
 * 跳转到第三步，完成密码的重置
 */
var goToStepThree = function () {
    var accountType = $('#accountType').val();
    var baseUser = {};
    baseUser.userName = $('#mobile').val();
    baseUser.userPwd = $('#password').val();
    var validator = $("#secondStepForm").validate();
    if (validator.form()) {
        var submitOption = {
            url: ctx + '/account/password/reset/' + accountType,
            type: 'POST',
            contentType: 'application/json;charset=UTF-8',
            dataType: 'json',
            data: JSON.stringify(baseUser),
            success: function showResponse(result) {
                if (result.success) {

                    $(".password-thi-step,.sec-line").addClass("on");
                    $(".sec-step").addClass("hide");
                    $(".thi-step").removeClass("hide");
                    $(".password-sec-step").css({
                        "font-weight": "normal",
                        "transform": 'scale(1)',
                        "webkitTransform": 'scale(1)'
                    });
                } else {
                    // 处理错误信息
                    var errorMsg = result.description;
                    $('#passwordErrorMsg').show();
                    $('#passwordErrorMsg').html(errorMsg);
                }
            },
            error: function () {
                zcal({
                    type: 'error',
                    title: '服务器异常'
                });
            }
        };
        $.ajax(submitOption);
    }
};
/**
 * 跳转到登录页面
 */
var goToLoginPage = function () {
    var accountType = $('#accountType').val();
    if (accountType == '1') {
        window.location.href = ctx + '/sysmgr/login';
    } else if (accountType == '2') {
        window.location.href = ctx + '/supply/login';
    } else if (accountType == '3') {
        window.location.href = ctx + '/shop/login';
    }
};
