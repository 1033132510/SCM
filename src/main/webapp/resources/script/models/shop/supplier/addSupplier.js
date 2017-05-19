$(function() {

	SupplierObject.validateForm();
	
    $('.close').bind('click', function(){
        $(this).siblings('input').val('');
    });
	
});

var validator = jQuery.validator;

validator.addMethod('isMobile', function(value, element) {
	var mobileReg = /^1[0-9]{10}$/;
	return mobileReg.test(value);
}, '请输入正确的手机号');

validator.addMethod('mobileIsUnique', function(value, element) {
	var flag = true;
    $.ajax({
        url: ctx + '/account/checkAccountIsExist/' + value + '/' + SUPPLIER_TYPE,
        type: 'get',
        async: false,
        success: function (data) {
            flag = !data.success;
        }
    });
	
	return flag;
});

var SupplierObject = {
	addSupplier : function() {
		var $addSupplier = $('#addSupplier');
		// 防止重复提交
		if($addSupplier.hasClass('disabled')) {
			return false;
		}
		$('#addSupplier').addClass('disabled');
		$.ajax({
			url : ctx + '/shop/supplier/addSupplier',
			data : $('#supplierForm').serialize(),
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
				$addSupplier.removeClass('disabled');
			}
		});
	},
	validateForm : function() {
		$('#supplierForm').validate({
			rules : {
				mobilePhone : {
					required : true,
					isMobile : true,
					mobileIsUnique : true
				},
				companyName : {
					required : true,
					maxlength : 50
				},
				username : {
					required : true,
					maxlength : 10
				},
				email : {
					required : true,
					email : true
				},
				brandName : {
					required : true,
					maxlength : 30
				}
			},
			messages : {
				mobilePhone : {
					required : '手机号必填',
					mobileIsUnique : '手机号已经存在'
				},
				companyName : {
					required : '请输入公司名称',
					maxlength : '最多{0}个字符'
				},
				username : {
					required : '请输入真实姓名',
					maxlength : '最多{0}个字符'
				},
				email : {
					required : '请输入邮箱',
					email : '请输入正确格式的邮箱'
				},
				brandName : {
					required : '请输入品牌名称',
					maxlength : '请输入公司拥有的品牌名称，字数不超过{0}'
				}
			},
			errorPlacement : function(error, element) {
				error.appendTo(element.closest('.register-input'));
			},
			submitHandler : function(form) {
				SupplierObject.addSupplier();
				return false;
			}
		});
	}
}, TIME = 3000,
SUPPLIER_TYPE = 2;


$.ajaxSetup({
	error : function() {
		zcal({
			type : 'error',
			title : '系统提示',
			text : '服务器异常'
		});
	}
});