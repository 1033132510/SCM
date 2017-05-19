function saveSupplierOrg(id) {
	var tt = um.getPlainTxt();
	console.log(tt);
	var supplierOrg = getSupplierOrg();
	$.ajax({
		url : ctx + '/sysmgr/supplier/saveSupplierOrg',
		type : 'post',
		data : {
			params : JSON.stringify(supplierOrg),
			t : new Date().valueOf()
		},
		// dataType : 'text',
		async : false,
		success : function(data) {
			if (data == 'fail') {
				alert('保存数据失败!');
				return;
			} else {
				$("#id").val(data);
				alert('保存成功!');
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert('服务器系统异常');
		}
	});
}

function getSupplierOrg() {
	alert(um.get);
	alert(um.getAllHtml());
	var supplierOrg = {
		id : $('#id').val(),
		orgName : $('#orgName').val(),
		legalPerson : $('#legalPerson').val(),
		contactNumber : $('#contactNumber').val(),
		supplierType : $('sypplierType :selected').val(),
		telNumber : $('#telNumber').val(),
		supplierIntroduction : um.getAllHtml()
	}
	return supplierOrg;
}

var um;

$(function() {

	var serverPath = '/server/umeditor/', um = UM
			.getEditor(
					'supplierIntroduction',
					{
						// 自定义图片路径
						imageUrl : serverPath + "imageUp.jsp", // 图片上传提交地址
						imagePath : serverPath, // 图片修正地址

						toolbar : [
								'source | undo redo | bold italic underline strikethrough | forecolor backcolor removeformat | ',
								'paragraph fontfamily fontsize |',
								'justifyleft justifycenter justifyright |',
								'link unlink | image imagefloat video |',
								'preview ', 'fullscreen' ],
						autoClearinitialContent : true,
						initialFrameHeight : 300,
					});

	/*
	 * $('#businessLicence').fileupload({ url : ctx + '/image', dataType :
	 * 'json', add : function(e, data) { showPreview(data.files[0],
	 * 'businessLicenceLink'); data.formData = { orgCode:$('#orgCode').val(),
	 * relationType : 2 }; data.submit(); }, done : function(e, data) { if(data &&
	 * data.result.success) { if($('#orgCode').val() == '')
	 * $('#orgCode').val(data.result.orgCode); } }, fail : function(e, data) {
	 * alert('上传出错'); } });
	 */
});

function showPreview(file, aId) {
	if (window.FileReader) { // 浏览器支持FileReader
		var fileReader = new FileReader();
		$('#' + aId).before('');
		fileReader.onloadend = function(e) {
			var preview = '<span class="preview">' + '<img src="'
					+ e.target.result + '" alt=""/>' + '</span>';
			$('#' + aId).before(preview);
		};
		fileReader.readAsDataURL(file);
	}
}