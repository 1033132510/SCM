/**
 * 品牌管理
 */
$(function() {
	/**
	 * 表单校验
	 */
	$.fn.image.init('brandview_brandLogo_Div', 7, {readonly : true}, { relationId : $('#brandview_id').val(), relationType : 7});
	$.fn.image.init('brandview_brandrecord_Div', 8, {readonly : true}, {relationId : $('#brandview_id').val(), relationType : 8});
	// 初始化
	catePage.init();
});
// 新增类别方法
var catePage = {
	init : function() {
		// 绑定事件与声明变量
		this.$disableData = $('#disableBrand');
		this.brand = null;
		this.bindEvent();
		// 设置编辑器只读
		um.ready(function() {
			um.setDisabled(true);
		});
	},
	bindEvent : function() {
		this.$disableData.on("click", $.proxy(this.disableData, this));
	},
	disableData : function(evt) {
		evt.preventDefault();
		console.log($.fn.image.getDeleteImageIds());
		// 获取数据成功后可ajax提交
		$.ajax({
			type : "POST",
			url : ctx + '/sysmgr/brand/disableBrand',// 提交的URL
			data : {
				brandId : $('#brandview_id').val(),
				status : INVALID_STATUS
			},
			success : function(data) {
				if(data.success) {
					zcal({
						type : 'success',
						title : '设置成功',
						time : 2000,
					});
					setTimeout(function() {
						location.href = ctx + '/sysmgr/brand/listByOrg?supplierOrgId=' + $('#brandview_supplierOrg_orgId').val();
					}, 2000);
				} else {
					zcal({
						type : 'success',
						title : '设置失败',
						time : 2000,
					});
				}
			},
			error : function(request) {
				zcal({
					type : 'error',
					title : '服务器异常',
					time : 2000,
				});
			}
		});
	}
}, INVALID_STATUS = 0;