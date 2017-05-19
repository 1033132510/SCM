$(function() {
	
	$('.close').bind('click', function(){
        $(this).siblings('input').val('');
    });
	
	PurchaserObject.validateForm();
	
});

var validator = jQuery.validator;

validator.addMethod('isIdCard', function(value, element) {
	var idCardReg = /^\d{15}|(\d{17}(\d|x|X))$/;
	return idCardReg.test(value);
}, '请输入正确的身份证号');

validator.addMethod('isMobile', function(value, element) {
	var mobileReg = /^1[0-9]{10}$/;
	return mobileReg.test(value);
}, '请输入正确的手机号');

validator.addMethod('mobileIsUnique', function(value, element) {
	var flag = true;
    $.ajax({
        url: ctx + '/account/checkAccountIsExist/' + value + '/' + PURCHASER_TYPE,
        type: 'get',
        async: false,
        success: function (data) {
            flag = !data.success;
        }
    });
	
	return flag;
});

var PurchaserObject = {
	addPurchaser : function() {
		var $addPurchaser = $('#addPurchaser');
		if($addPurchaser.hasClass('disabled')) {
			return false;
		}
		$addPurchaser.addClass('disabled');
		$.ajax({
			url : ctx + '/shop/purchaser/addPurchaser',
			data : $('#purchaserForm').serialize(),
			type : 'post',
			success : function(data) {
				if(data === true) {
					zcal({
						type : 'success',
						title : '系统提示',
						text : '恭喜您，信息已提交成功，请等待管理员审核，客服电话：400-6470799'
					});
				}
				setTimeout(function() {
					location.href = '/';
				}, TIME);
			},
			error : function() {
				zcal({
					type : 'warning',
					title : '错误提示',
					text : '服务器异常'
				});
				$addPurchaser.removeClass('disabled');
			}
		});
	},
	validateForm : function() {
		$('#purchaserForm').validate({
			rules : {
				mobilePhone : {
					required : true,
					isMobile : true,
					mobileIsUnique : true
				},
				username : {
					required : true,
					maxlength : 10
				},
				idCard : {
					required : true,
					isIdCard : true
				},
				email : {
					required : true,
					email : true
				},
				companyName : {
					required : true,
					maxlength : 50
				},
				departmentName : {
					maxlength : 20
				},
				position : {
					maxlength : 20
				}
			},
			messages : {
				mobilePhone : {
					required : '请输入手机号，作为平台账号使用',
					mobileIsUnique : '手机号已存在'
				},
				username : {
					required : '请输入真实姓名',
					maxlength : '最多{0}个字符'
				},
				idCard : {
					required : '请输入个人身份证号',
					isIdCard : '请输入正确格式的身份证号'
				},
				email : {
					required : '请输入邮箱',
					email : '请输入正确格式的邮箱'
				},
				companyName : {
					required : '请输入公司名称',
					maxlength : '最多{0}个字符'
				},
				departmentName : {
					maxlength : '最多{0}个字符'
				},
				position : {
					maxlength : '最多{0}个字符'
				}
			},
			submitHandler : function(form) {
				PurchaserObject.addPurchaser();
				return false;
			}
		});
	}
}, TIME = 3000,
PURCHASER_TYPE = 3;

$.ajaxSetup({
	error : function() {
		zcal({
			type : 'error',
			title : '系统提示',
			text : '服务器异常'
		});
	}
});