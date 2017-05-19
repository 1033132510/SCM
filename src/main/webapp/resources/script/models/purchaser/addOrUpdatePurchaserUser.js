var validateSettings = {
    errorElement: 'label',
    errorClass: 'error',
    focusInvalid: false,
    onkeyup: function (element) {
        $(element).valid();
    },
    onfocusout: function (element) {
        $(element).valid();
    },
    rules: {
        email: {
            required: true,
            email: 'required'
        },
        password: {
            required: true,
            password: 'required'
        },
        confirmPassword: {
            required: true,
            equalTo: "#password"
        },
        name: {
            required: true
        },
        mobile: {
            required: true,
            mobile: 'required',
            existMobile: 'required'
        },
        identityNumber: {
            required: true,
            identityCard: 'required'
        }
    },
    messages: {
        email: {
            required: '邮箱不能为空',
            email: '邮箱格式不正确'
        },
        password: {
            required: '密码不能为空',
            password: '请输入6-16位密码由字母、数字组成'
        },
        confirmPassword: {
            required: '确认密码不能为空',
            equalTo: '两次密码输入不一致'
        },
        name: {
            required: '姓名不能为空'
        },
        mobile: {
            required: '手机号不能为空',
            mobile: '手机号格式不正确'
        },
        identityNumber: {
            required: '身份证号不能为空',
            identityCard: '身份证号格式不正确'
        }
    }
};
$(function () {
    jQuery.validator.addMethod("password", function (value, element) {
        var tel = /^[0-9A-Za-z]{6,16}$/;
        return this.optional(element) || (tel.test(value));
    }, "请输入6-16位密码由字母、数字组成");

    $('#savePurchaserUser').click(function (e) {
        var password = $('#password').val();
        var confirmPassword = $('#confirmPassword').val();
        var id = $('#id').val();
        if (id != '' && password == '' && confirmPassword == '') {
            $("#purchaserUserForm").validate(validateSettings);
            $('#password').rules('remove', 'required password');
            $('#confirmPassword').rules('remove', 'required equalTo');
        }
        if (!validateFormEle().form()) {
            return false;
        }
        e.preventDefault();
        var email = $.trim($('#email').val());
        var name = $.trim($('#name').val());
        var mobile = $.trim($('#mobile').val());
        var identityNumber = $.trim($('#identityNumber').val());
        var department = $.trim($('#department').val());
        var position = $.trim($('#position').val());
        var purchaserId = $('#purchaserId').val();
        var params = {
            email: email,
            name: name,
            userPwd: password,
            mobile: mobile,
            identityNumber: identityNumber,
            department: department,
            position: position,
            id: id,
            purchaserId: purchaserId
        };

        $.ajax({
            url: ctx + '/sysmgr/purchaserUser/saveOrUpdate',
            type: 'POST',
            data: JSON.stringify(params),
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            success: function (result) {
                if (result && result.success) {
                    zcal({
                        type: 'success',
                        title: '添加成功'
                    });
                    resetForm();
                    $('.alert-close').on('click', function (e) {
                        e.preventDefault();
                        window.location.href = ctx + '/sysmgr/purchaserUser/view/purchaser/' + purchaserId;
                    });
                } else {
                    var descriptionObj = JSON.parse(result.description);
                    var description = [];
                    for (var key in descriptionObj) {
                        description.push(descriptionObj[key]);
                    }
                    zcal({
                        type: 'error',
                        title: description.join('、')
                    });
                }
            },
            error: function () {
                zcal({
                    type: 'error',
                    title: '服务器异常'
                });
            }

        });
    });
    $('#updatePasswordBtn').click(function (e) {
        e.preventDefault();
        $('#updatePassword').val('');
        $('#updateConfirmPassword').val('');
        modalOpen('#updatePasswordModal');
    });
    $('#confirmUpdatePassword').click(function (e) {
        if (!validateUpdateFormEle().form()) {
            return false;
        }
        e.preventDefault();
        $.ajax({
            url: ctx + '/sysmgr/purchaserUser/modifyPass',
            data: {id: $('#id').val(), password: $('#updatePassword').val()},
            type: 'POST',
            success: function (result) {
                if (result) {
                    zcal({
                        type: 'success',
                        title: '密码修改成功'
                    });
                    modalClose('#updatePasswordModal');
                } else {
                    zcal({
                        type: 'error',
                        title: '修改失败'
                    });
                }
            },
            error: function () {
                zcal({
                    type: 'error',
                    title: '服务器异常'
                });
            }
        });
    });

});

function checkExistMobile() {
    var mobile = $.trim($('#mobile').val());
    var originalMobile = $('#originalMobile').val();
    var isExist = false;
    if (originalMobile != '' && originalMobile == mobile) {
        return isExist;
    }
    $.ajax({
        url: ctx + '/account/checkAccountIsExist/' + mobile + '/3',
        type: 'GET',
        async: false,
        success: function (result) {
            isExist = result.success;
        }
    });
    return isExist;
}

function resetForm() {
    $('#email').val('');
    $('#name').val('');
    $('#password').val('');
    $('#confirmPassword').val('');
    $('#mobile').val('');
    $('#identityNumber').val('');
    $('#department').val('');
    $('#position').val('');
    $('.form-select .form-control').select2();
}

function validateFormEle() {
    jQuery.validator.addMethod("mobile", function (value, element) {
        var tel = /^1[3|4|5|7|8]\d{9}$/;
        return this.optional(element) || (tel.test(value));
    }, "请正确填写您的手机号码");
    jQuery.validator.addMethod("identityCard", function (value, element) {
        var tel = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;
        return this.optional(element) || (tel.test(value));
    }, "请正确填写您的身份证号码");
    $.validator.addMethod("existMobile", function (value, element) {
        return this.optional(element) || !checkExistMobile();
    }, "该手机号码已经注册过");
    return $("#purchaserUserForm").validate(validateSettings);
};

function validateUpdateFormEle() {
    var validateSettings = {
        errorElement: 'label',
        errorClass: 'error',
        focusInvalid: false,
        onkeyup: function (element) {
            $(element).valid();
        },
        onfocusout: function (element) {
            $(element).valid();
        },
        rules: {
            updatePassword: {
                required: true,
                password: 'required'
            },
            updateConfirmPassword: {
                required: true,
                equalTo: "#updatePassword"
            }
        },
        messages: {
            updatePassword: {
                required: '密码不能为空',
                password: '请输入6-16位密码由字母、数字组成'
            },
            updateConfirmPassword: {
                required: '确认密码不能为空',
                equalTo: '两次密码输入不一致'
            }
        }
    };
    return $("#updatePasswordForm").validate(validateSettings);
}