/**
 * 品牌管理
 */
$(function() {
	/**
	 * 表单校验
	 */
	$("#update_brand_form").validate({
		rules : {
			brandview_supplierOrg_orgName : {
				"required" : true
			}
		},
		messages : {
			brandview_supplierOrg_orgName : {
				required : "名称不能为空"
			}
		}
	});
	$.fn.image.init('brandview_brandLogo_Div', 7, {
		multiple : false
	}, {
		relationId : $('#brandview_id').val(),
		relationType : 7
	});
	$.fn.image.init('brandview_brandrecord_Div', 8, {
		multiple : true,
		uploadCount : 10
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
		this.$updateData = $('#updateDisabledBrand');
		this.brand = null;
		this.bindEvent();
	},
	bindEvent : function() {
		this.$updateData.on("click", $.proxy(this.updateData, this));
	},
	getData : function(cb) {
		this.brand = {
			id : $('#brandview_id').val() || '',
			brandZHName : $('#brandview_brandZHName').val(),
			brandENName : $('#brandview_brandENName').val(),
			brandDesc : um.getContent(),
			status : INVALID_STATUS,
			supplierOrg : {
				id : $('#brandview_supplierOrg_orgId').val()
			}
		};
	},
	updateData : function(evt) {
		evt.preventDefault();
		this.getData();
		if (um.getContent() == '') {
			zcal({
				type : 'error',
				title : '校验失败,请输入品牌简介',
				time : 2000,
			});
			return;
		}
		if (!$("#update_brand_form").valid()) {
			zcal({
				type : 'error',
				title : '校验失败',
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
		if ($('#brandview_brandrecord_Div img').length <= 0) {
			zcal({
				type : 'error',
				title : '请上传品牌描述图片',
				time : 2000
			});
			return;
		}
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
					var supplierOrgId = $('#brandview_supplierOrg_orgId').val();
					setTimeout(function() {
						location.href = ctx + '/sysmgr/brand/invalidBrandList?supplierOrgId=' + supplierOrgId + "&t=" + new Date().valueOf();
					}, TIME);
					zcal({
						type : 'success',
						title : '修改成功',
						time : TIME
					});
				} else {
					var dataObj = JSON.parse(data);
					zcal({
						type : 'error',
						title : dataObj.description,
						time : TIME
					});
				}
			},
			error : function(request) {
				zcal({
					type : 'error',
					title : '服务器异常',
					time : TIME
				});
			}
		});
	}
}, INVALID_STATUS = 0,
TIME = 3000;