$(function(){
	$('#updatePasswordBtn').click(function(e) {
		e.preventDefault();
		$('#updatePassword').val('');
		$('#updateConfirmPassword').val('');
		modalOpen('#updatePasswordModal');
	});
});

var supplierUserValidate = function(){};
supplierUserValidate.saveSupplierUserValidate = function(){
	$.validator.addMethod("cardNumber", function(value, element) {
		var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
		return this.optional(element) || reg.test(value);
	}, "请填写正确的身份证号");
	
	$.validator.addMethod("mobile", function (value, element) {
        var tel = /^1[3|4|5|7|8]\d{9}$/;
        return this.optional(element) || (tel.test(value));
    }, "请正确填写您的手机号码");
	
	$.validator.addMethod("password", function(value, element) {
        var tel = /^[0-9A-Za-z]{6,16}$/;
        return this.optional(element) || (tel.test(value));
    }, "请输入6-16位密码由字母、数字组成");
	
	$.validator.addMethod("checkSupplierOrgUserMobile", function(value, element) {
		return this.optional(element) || !checkAccountIsExist();
	}, "该手机号码已经注册过");
	
	var validate = $("#supplierUser").validate({
		errorElement : 'label', // default
		errorClass : 'error', // default
		focusInvalid : false, 
		onkeyup: function(element) { 
			$(element).valid();
		},
		onfocusout: function(element) { 
			$(element).valid();
		},
		rules:{
			email:{
				required:true,
				email:true
			},
			userName:{
				required:true,
				minlength:2,
				maxlength:255
			},
			contactNumber:{
				required:true,
				mobile: 'required',
				checkSupplierOrgUserMobile:'required'
			},
			idcard:{
				required:true,
				cardNumber:'required'
			},
			department:{
				minlength:2,
				maxlength:255
			},
			position:{
				minlength:2,
				maxlength:255
			},
			userPwd:{
				required:true,
				password : 'required'
			},
			confirmuserPwd:{
				required:true,
				equalTo: "#userPwd"
			}
		},
		messages:{
			email:{
				required:'[邮箱]必填',
				email: "请输入正确格式的电子邮件"
			},
			userName:{
				required:'[姓名]必填',
				minlength:'[姓名]最小长度为{2}',
				maxlength:'[姓名]最小长度为{255}'
			},
			contactNumber:{
				required:'[手机号码]必填',
				mobile: '手机号码格式不正确'
			},
			idcard:{
				required:'[身份证号]必填',
			},
			department:{
				minlength:'[部门]最小长度为{2}',
				maxlength:'[部门]最大长度为{255}'
			},
			position:{
				minlength:'[职位]最小长度为{2}',
				maxlength:'[职位]最大长度为{255}'
			},
			userPwd:{
				required:'密码不能为空',
				password : '请输入6-16位密码由字母、数字组成'
			},
			confirmuserPwd:{
				required:'确认密码不能为空',
				equalTo: '两次密码输入不一致'
			}
		}
	});
	return validate;
};

supplierUserValidate.updatePass = function(){
	$.validator.addMethod("updatePassword", function(value, element) {
        var tel = /^[0-9A-Za-z]{6,16}$/;
        return this.optional(element) || (tel.test(value));
    }, "请输入6-16位密码由字母、数字组成");
	var validate = $("#updatePasswordForm").validate({
		errorElement : 'label', // default
		errorClass : 'error', // default
		focusInvalid : false, 
		onkeyup: function(element) { 
			$(element).valid();
		},
		onfocusout: function(element) { 
			$(element).valid();
		},
		rules:{
			updatePassword:{
				required:true,
				updatePassword : 'required'
			},
			updateConfirmPassword:{
				required:true,
				equalTo: "#updatePassword"
			}
		},
		messages:{
			updatePassword:{
				required:'密码不能为空',
				password : '请输入6-16位密码由字母、数字组成'
			},
			updateConfirmPassword:{
				required:'确认密码不能为空',
				equalTo: '两次密码输入不一致'
			}
		}
	});
	return validate;
}
function saveSupplierUser(id){
	if(!supplierUserValidate.saveSupplierUserValidate().form()){
		return;
	}
	var supplierUser = getSupplierUser();
	$.ajax({
		url : ctx + '/sysmgr/supplierUser/saveSupplierUser',
		type : 'post',
		data : {
			params:JSON.stringify(supplierUser),
			t:new Date().valueOf()
		},
		async : false,
		dataType: 'text',
		success : function(data) {
			if (data == 'fail') {
				zcal({
					type:"error",
					title:"保存失败"
				});
				return;
			} else {
				$("#id").val(data);
				$("#oldContactNumber").val($("#contactNumber").val());
				
				$("#userPwdDiv,#confirmuserPwdDiv").hide();
				$('#updatePasswordBtn').show();
				zcal({
					type:"success",
					title:"保存成功"
				});
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			zcal({
				type:"error",
				title:"保存失败"
			});
		}
	});
}


function updatePassword() {
	if(!supplierUserValidate.updatePass().form()){
		return;
	}
	$.ajax({
		url : ctx + '/sysmgr/supplierUser/modifyPass',
		data : {id : $('#id').val(), password : $('#updatePassword').val()},
		type : 'POST',
		success : function(result) {
			if(result) {
				zcal({
			        type: 'success',
			        title: '密码修改成功'
			    });
				modalClose('#updatePasswordModal');
			}else {
				zcal({
			        type: 'error',
			        title: '修改失败'
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

function getSupplierUser(){
	var userPwd = '';
	if($('#userPwd').val()){
		userPwd = $('#userPwd').val();
	}
	var supplierUser = {
			id:$('#id').val(),
			userName:$.trim($('#contactNumber').val()),
			email:$.trim($('#email').val()),
			contactNumber:$.trim($('#contactNumber').val()),
			idcard:$.trim($('#idcard').val()),
			department:$('#department').val(),
			position:$('#position').val(),
			status:$('#status option:selected').val(),
			userPwd:userPwd,
			supplierUserName:$('#userName').val()
	}
	supplierUser.supplierOrg={
			id:$('#supplierOrgId').val()
	}
	return supplierUser;
}

/**
 * 检查账户是否已经存在
 * @param account
 */
var checkAccountIsExist = function () {
    var account = $('#contactNumber').val();
    var oldMobile = $('#oldContactNumber').val();
    if (oldMobile.length > 0) {
        if (account == oldMobile) {
            return false;
        }
    }

    var accountType = '2';
    var checkResult = true;
    $.ajax({
        url: ctx + '/account/checkAccountIsExist/' + account + '/' + accountType,
        type: 'get',
        async: false,
        dataType: 'json',
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