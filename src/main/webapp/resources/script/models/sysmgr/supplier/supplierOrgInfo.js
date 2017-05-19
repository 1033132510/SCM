var supplierOrgValidate = function(){};
supplierOrgValidate.saveSupplierOrgValidate = function(){
	$.validator.addMethod("checkSupplierOrgName", function(value, element) {
		var response = 0;
		$.ajax({
			url : ctx + '/sysmgr/supplierOrg/checkName',
			type : 'POST',
			data : {
				name:$.trim($('#orgName').val()),
				id:$('#id').val(),
				t:new Date().valueOf()
			},
			dataType : 'text',
			async : false,
			success : function(data) {
				 if("fail" == data){
					 response = 0;
				 }else{
					 response = 1;
				 }
			}
		});
		if (response == 1) {
			return true;
		} else {
			return false;
		}
	}, "名字已被使用");
	
	var validate = $("#supplierOrgForm").validate({
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
			orgName:{
				required:true,
				minlength:2,
				maxlength:255,
				checkSupplierOrgName:'required'
			},
			legalPerson:{
				required:true,
				minlength:2,
				maxlength:255
			},
			telNumber:{
				required:true,
				digits:true
			}
		},
		messages:{
			orgName:{
				required:'[公司名称]必填',
				minlength:'[公司名称]最小长度为{2}',
				maxlength:'[公司名称]最小长度为{255}'
			},
			legalPerson:{
				required:'[法人]必填',
				minlength:'[法人]最小长度为{2}',
				maxlength:'[法人]最小长度为{255}'
			},
			telNumber:{
				required:'[公司座机]必填',
				digits:'[公司座机]必须为数字'
			}
		}
	});
	return validate;
};

var validateImages = ['businessLicenceDiv','orgCodeDiv','LOGODiv','taxRegistrationCertificateDiv'];
//,'soleDistributorPaDiv' 总经销商或总代理授权书   ,'powerOfAttorneyDiv' 法人委托书  ,'trademarkRegistrationCertificateDiv'  商标注册证
$(function(){
	var imageOption ={multiple : false};
	var imageOptionMore ={multiple : true,uploadCount:10};
	if($.trim($('#statusFlag').val())){
		$(':input').attr('disabled','disabled');
		imageOption={readonly:true};
		imageOptionMore ={readonly:true};
		
		ue.ready(function() {
			ue.setDisabled(true);
		});
	}
	// 上传营业执照图片
	$.fn.image.init('businessLicenceDiv', 3, imageOption, {relationId : $('#id').val(), relationType : 3});
	$.fn.image.init('orgCodeDiv', 4, imageOption, {relationId : $('#id').val(), relationType : 4});
	$.fn.image.init('LOGODiv', 5, imageOption, {relationId : $('#id').val(), relationType : 5});
	$.fn.image.init('honorDiv', 6,imageOptionMore, {relationId : $('#id').val(), relationType : 6});
	$.fn.image.init('taxRegistrationCertificateDiv', 10, imageOption, {relationId : $('#id').val(), relationType : 10});
	$.fn.image.init('trademarkRegistrationCertificateDiv', 11, imageOptionMore, {relationId : $('#id').val(), relationType : 11});
	$.fn.image.init('powerOfAttorneyDiv', 12, imageOption, {relationId : $('#id').val(), relationType : 12});
//	$.fn.image.init('soleDistributorPaDiv', 13, imageOption, {relationId : $('#id').val(), relationType : 13});
});
function saveSupplierOrg(){
	if(!supplierOrgValidate.saveSupplierOrgValidate().form()){
		var validate = supplierOrgValidate.saveSupplierOrgValidate();
		scrollTop({
			distance: '#' + utils.getFirstErrorLabelId(validate),
            customTop: 110,
            time: 200,
			   windowScroll: false
		});
		return;
	}
	if(!validateImage(validateImages)){
		return;
	}
	var supplierOrg = getSupplierOrg();
	$.ajax({
		url : ctx + '/sysmgr/supplierOrg/saveSupplierOrg',
		type : 'post',
		data : {
			supplierOrgParams:JSON.stringify(supplierOrg),
			'imageIds[]':$.fn.image.getUploadImageIds(),
			'deleteImageIds[]' : $.fn.image.getDeleteImageIds(),
			t:new Date().valueOf()
		},
		dataType : 'text',
		async : false,
		success : function(data) {
			if (data == 'fail') {
				zcal({
					type:"error",
					title:"保存失败"
				});
				return;
			} else {
				$.fn.image.clearData();
				var newId = $('#id').val();
//				if (!newId || newId == '') {
					zcal({
						type: 'continue',
						title: '保存成功',
						continueBtnSure: '继续添加公司人员信息'
					});
					resetForm();
					$('.continue-cancel').off().on('click', function (e) {
						e.preventDefault();
						window.location.href = ctx + '/sysmgr/supplierOrg/view';
					});
					$('.continue-sure').on('click', function (e) {
						e.preventDefault();
						window.location.href = ctx + '/sysmgr/supplierUser/view/' + data + "?t=" + new Date().valueOf();
					});
//				} else {
//					zcal({
//						type: 'success',
//						title: '系统提示',
//						text: '保存成功'
//					});
//				}
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
function resetForm() {
	$('input').val('');
	$('#level').val('1');
	$('#status').val('0');
	$('.form-select .form-control').select2();
	$('.preview').each(function() {
		$(this).remove();
	});
	$.fn.image.clearData();
	$.fn.image.reInit([3,4,5,6,10,11,12]);
	$('#id').val('');
	ue.reset();
	ue.setContent('');
}
function validateImage(ids){
	for(var i =0;i<ids.length;i++){
		if($('#'+ids[i]+' img').length<=0){
			zcal({
				type:"error",
				title:"有必填图片未上传"
			});
			return false;
		}
	}
	return true;
}
function getSupplierOrg(){
	var supplierOrg = {
		id:$('#id').val(),
		orgName:$.trim($('#orgName').val()),
		legalPerson:$.trim($('#legalPerson').val()),
		supplierType:$('#supplierType option:selected').val(),
		status:$('#status option:selected').val(),
		telNumber:$.trim($('#telNumber').val()),
		supplierOrgIntroductionHtml:ue.getContent(),
		supplierOrgIntroductionText:ue.getPlainTxt()
	}
	return supplierOrg;
}


function setSupplierOrgStatus(status){
	$.ajax({
		url : ctx + '/sysmgr/supplierOrg/setSupplierOrgStatus',
		type : 'post',
		data : {
			id:$('#id').val(),
			status:status,
			t:new Date().valueOf()
		},
		dataType : 'text',
		async : false,
		success : function(data) {
			if (data == 'fail') {
				zcal({
					type:"error",
					title:"设置失败"
				});
				return;
			} else {
				zcal({
					type:'success',
					title:'设置成功',
					time : 2000,
				});
				setTimeout(function() {
					if(status == 1){
						window.location.href = ctx + '/sysmgr/supplierOrg/failureView/?t='+new Date().valueOf();
					}else{
						window.location.href = ctx + '/sysmgr/supplierOrg/view/?t='+new Date().valueOf();
					}
				}, 2000);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			zcal({
				type:"error",
				title:"设置失败"
			});
		}
	});
}