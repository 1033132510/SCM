/**
 * 品牌管理
 */
/**
	 * 表单校验
	 */
var brandValidate={};
brandValidate.saveBrandValidate = function(){
	var validate = $("#add_brand_form").validate({
		errorElement : 'label', // default
		errorClass : 'error', // default
		focusInvalid : false, 
		onkeyup: function(element) { 
			$(element).valid();
		},
		rules : {
			brandview_supplierOrg_orgName : {
				"required" : true
			},
			brandview_brandZHName:{
				"required" : true
			},
			brandview_brandENName:{
				"required" : true
			}
		},
		messages : {
			brandview_supplierOrg_orgName : {
				required : "供应商名称不能为空"
			},
			brandview_brandZHName:{
				"required" : "品牌中文名称不能为空"
			},
			brandview_brandENName:{
				"required" : "品牌英文名称不能为空"
			}
		}
	});
	return validate;
}
$(function() {
	$.fn.image.init('brandview_brandLogo_Div', 7, {
		multiple : false,
		size:80
	}, {
		relationId : $('#brandview_id').val(),
		relationType : 7
	});
	$.fn.image.init('brandview_brandrecord_Div', 8, {
		multiple : true,
		uploadCount : 10,
		size:600
	}, {
		relationId : $('#brandview_id').val(),
		relationType : 8
	});
	// 初始化
	catePage.init();
});
// 新增类别方法
var catePage = {
	init : function() {
		// 绑定事件与声明变量
		this.$saveData = $('#brandView_addBrand');
		this.brand = null;
		this.bindEvent();
	},
	bindEvent : function() {
		this.$saveData.on("click", $.proxy(this.saveData, this));
	},
	getData : function(cb) {
		this.brand = {
			id : $('#brandview_id').val() || '',
			brandZHName : $.trim($('#brandview_brandZHName').val()),
			brandENName : $.trim($('#brandview_brandENName').val()),
			brandDesc : um.getContent(),
			status : VALID_STATUS,
			supplierOrg : {
				id : $('#brandview_supplierOrg_orgId').val()
			}
		};
	},
	saveData : function(evt) {
		evt.preventDefault();
		this.getData();
		
		if(!brandValidate.saveBrandValidate().form()){
			var validate = brandValidate.saveBrandValidate();
			scrollTop({
				distance: '#' + utils.getFirstErrorLabelId(validate),
	            customTop: 110,
	            time: 200,
				   windowScroll: false
			});
			return;
		}
		if (um.getContent() == '') {
			zcal({
				type : 'error',
				title : '校验失败,请输入品牌简介',
				time : 2000,
			});
			return;
		}
		if ($('#brandview_brandLogo_Div img').length <= 0) {
			zcal({
				type : 'error',
				title : '请上传品牌LOGO图片',
				time : 2000
			});
			return;
		}
//		if ($('#brandview_brandrecord_Div img').length <= 0) {
//			zcal({
//				type : 'error',
//				title : '请上传品牌描述图片',
//				time : 2000
//			});
//			return;
//		}
		// 获取数据成功后可ajax提交
		$.ajax({
			type : "POST",
			url : ctx + '/sysmgr/brand/saveOrUpdate',// 提交的URL
			data : {
				brandJson : JSON.stringify(this.brand),
				'imageIds[]' : $.fn.image.getUploadImageIds(),
				'deleteImageIds[]' : $.fn.image.getDeleteImageIds(),
				t : new Date().valueOf()
			},
			async : true,
			success : function(data) {
				var idFlag = data.id;
				if (idFlag != undefined) {
					var supplierOrgId = $(
							'#brandview_supplierOrg_orgId').val();
					window.location.href = ctx
							+ '/sysmgr/brand/listByOrg?supplierOrgId='
							+ supplierOrgId + "&t="
							+ new Date().valueOf();
					zcal({
						type : 'success',
						title : '保存成功',
						time : 2000,
					});
				} else {
					var dataObj = JSON.parse(data);
					zcal({
						type : 'error',
						title : dataObj.description,
						time : 2000,
					});
				}
			},
			error : function(request) {
				zcal({
					type : 'error',
					title : '保存失败',
					time : 2000,
				});
			}
		});
	}
}, VALID_STATUS = 1;