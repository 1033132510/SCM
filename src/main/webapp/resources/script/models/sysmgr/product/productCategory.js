/**
 * 
 * 产品类别js
 */

function uuid() {
	var s = [];
	var hexDigits = "0123456789abcdef";
	for (var i = 0; i < 36; i++) {
		s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	}
	s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
	s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the
	// clock_seq_hi_and_reserved
	// to 01
	s[8] = s[13] = s[18] = s[23] = "-";
	var uuid = s.join("");
	return uuid;
}
$(function() {
	// 左侧详情页面初始化的时候需要不抱
	// $('#mainView').hide();
	$("#productCategoryForm").validate({
		rules : {
			categoryInfoForCateName : {
				"required" : true,
				"minlength" : 2,
				"maxlength" : 15
			},
			parentCategoryName : {
				"required" : true
			},
			itemName : {
				"required" : true,
				"minlength" : 2,
				"maxlength" : 15
			},
			itemsSources : "required",
			sortOrder : {
				"required" : true,
				"digits" : true
			},
			defaultShowNumber : {
				"required" : true,
				"digits" : true,
				 "min" : 1
			},
			maxShowNumber : {
				"required" : true,
				"digits" : true
			}
		},
		messages : {
			categoryInfoForCateName : {
				required : "品类名称不能为空",
				minlength : "品类名称长度不能小于2个",
				maxlength : "品类名称长度不能大于15个"
			},
			parentCategoryName : "点击左侧+号确定父级类别",
			itemName : {
				required : "属性名称不能为空",
				minlength : "属性名称长度不能小于2个",
				maxlength : "品类名称长度不能大于15个"
			},
			itemsSources : "属性参数不能为空",
			sortOrder : {
				required : "名称不能为空",
				digits : "必须是整数"
			},
			defaultShowNumber : {
				required : "数量不能为空",
				digits : "必须是整数",
			    min : "最小值为1"
			},
			maxShowNumber : {
				required : "名称不能为空",
				digits : "必须是整数"
			}
		}
	});
	// 初始化节点
	zzcTree.initTree('productCategoryTree',
			'/sysmgr/category/tree?level=0&status=1', true, false, false);
	// 获得root节点的孩子节点
	zzcTree.getChildNode(zTree.getNodes()[0]);
});
function cleanPage() {
	// 将form表单数据清空
	$('#categoryInfoForCateId').val('');
	$('#productCategoryForm')[0].reset();
	// 删除表格数据
	$("#categoryInfoForItemValues tr:gt(0)").remove();
}
function showNodeInfo(data) {
	cleanPage();
	$('#mainView').show();
	$('#saveOrUpdateProductCategory').text('更新');
	var productCategory = data.param.category;
	var productCategoryItemKeys = data.param.productCategoryItemKeys;
	var parentCategory = data.param.parentCategory;
	if (parentCategory != null) {
		$('#parentCategoryName').val(parentCategory.cateName);
		$('#parentCategoryId').val(parentCategory.id);
	}
	$('#categoryInfoForCateId').val(productCategory.id);
	$('#categoryInfoForCateName').val(productCategory.cateName);
	$('#categoryInfoForStatus').val(productCategory.status);
	$('#categoryInfoLevel').val(productCategory.level);
	$('#categoryInfoForCateCode').val(productCategory.categoryCode);
	$('#parentCategoryName-error').remove();
	$('#categoryInfoForCateName-error').remove();
	$('#parentCategoryName').removeClass('error');
	$('#categoryInfoForCateName').removeClass('error');
	var bt = baidu.template;
	var hh = $("#categoryInfoForItemValues tbody"), html = '';
	if (productCategoryItemKeys != null) {
		var length = productCategoryItemKeys.length; index = 0;
		for (;index < length; index++) {
			var itemKey = productCategoryItemKeys[index];
			itemKey.itemCode_id = uuid();
			itemKey.itemName_id = uuid();
			itemKey.itemsSources_id = uuid();
			itemKey.defaultShowNumber_id = uuid();
			html += bt('tempHtmlForInit', itemKey);
		}
		hh.html(html);
	}
	$("#categoryInfoForItemValues tbody tr").each(function() {
		// 遍历td
		var canBeChanged = $(this).find("input[name='canBeChanged']").val();
		if (canBeChanged == 0) {
			$(this).find("input[name='itemName']").attr("readonly", true);
			$(this).find("a[class='red']").remove();
		}
	});
	if (data.param.hasProduct) {
		$('#saveOrUpdateProductCategory,#addProductCategoryItemKey').hide();
		var $trs = $('#categoryInfoForItemValues tbody tr');
		$trs.find('a[class="red"]').remove();
		$trs.find('input').attr('disabled', true);
		$trs.find('input').closest('label').addClass('disabled');
		$trs.find('span,input').addClass('gray');
	} else {
		$('#saveOrUpdateProductCategory,#addProductCategoryItemKey').show();
	}
}
function del(obj) {
	$(obj).closest("tr").remove();
}
function addInfo(parentTreeNode) {
	$('#parentCategoryName-error').remove();
	$('#categoryInfoForCateName-error').remove();
	$('#parentCategoryName').removeClass('error');
	$('#categoryInfoForCateName').removeClass('error');
	$('#saveOrUpdateProductCategory,#addProductCategoryItemKey').show();
	cleanPage();
	$('#mainView').show();
	$('#saveOrUpdateProductCategory').text('保存');
	$('#parentCategoryId').val(parentTreeNode.id);
	$('#parentCategoryName').val(parentTreeNode.name);
	// 给页面隐藏的节点赋值父级名称
	var productCategoryItemKeys = parentTreeNode.param.productCategoryItemKeys;
	var bt = baidu.template, hh = $("#categoryInfoForItemValues tbody"),
	html = '';
	if (productCategoryItemKeys != null) {
		var length = productCategoryItemKeys.length, index = 0;
		for (; index < length; index++) {
			var itemKey = productCategoryItemKeys[index];
			itemKey.id = '';
			itemKey.itemCode_id = uuid();
			itemKey.itemName_id = uuid();
			itemKey.itemsSources_id = uuid();
			itemKey.defaultShowNumber_id = uuid();
			itemKey.canBeChanged = 0;
			html += bt('tempHtmlForInit', itemKey);
		}
		hh.html(html);
		$("input[name='itemName']").attr("readonly", true);
		$("#categoryInfoForItemValues a.red").remove();
	}
}
// 新增类别方法
var catePage = {
	init : function() {
		// 绑定事件与声明变量
		this.$addTr = $('#addProductCategoryItemKey');
		this.$saveData = $('#saveOrUpdateProductCategory');
		this.$tableTboday = $("#categoryInfoForItemValues tbody");
		this.category = null;
		this.bindEvent();
	},
	bindEvent : function() {
		this.$addTr.on("click", $.proxy(this.addTr, this));
		this.$saveData.on("click", $.proxy(this.saveData, this));
	},
	addTr : function(evt) {
		evt.preventDefault();
		var bt = baidu.template;
		var html = bt('tempHtmlForInit', {
			itemName_id : uuid(),
			itemsSources_id : uuid(),
			defaultShowNumber_id : uuid(),
			canBeChanged : 1
		});
		this.$tableTboday.append($(html));
	},
	getData : function(cb) {
		this.category = {
			id : $('#categoryInfoForCateId').val() || '',
			cateName : $('#categoryInfoForCateName').val(),
			status : $('#categoryInfoForStatus').val(),
			parentCategory : {
				id : $('#parentCategoryId').val()
			},
			productCategoryItemKeys : []
		};
		var $trs = $("#categoryInfoForItemValues tbody tr"), trsLength = $trs.length,
		i = 0, arr = [];
		for (; i < trsLength; i++) {
			var obj = {}, $tr = $($trs[i]), itemKeyId = $tr.find('th').text(),
			$inputs = $tr.find("input"), inputsLength = $inputs.length, j = 0;
			for (; j < inputsLength; j++) {
				var $input = $($inputs[j]), name = $input.attr("name");
				if ($input.attr("type") == 'radio') {
					if ($input.closest('label').hasClass('checked')) {
						obj[name] = $input.val();
					}
				} else {
					obj[name] = $input.val();
				}
			}
			obj['id'] = itemKeyId;
			arr.push(obj);
		}
		this.category.productCategoryItemKeys = arr;
	},
	saveData : function(evt) {
		evt.preventDefault();
		this.getData();
		if (!$("#productCategoryForm").valid()) {
			zcal({
				type : 'error',
				title : '校验失败',
				time : 2000,
			});
			return;
		}
		$('#error-categoryInfoForCateName').remove();
		// 获取数据成功后可ajax提交
		$.ajax({
			type : "POST",
			url : ctx + '/sysmgr/category/saveOrUpdate',// 提交的URL
			data : {
				productCategoryJson : JSON.stringify(this.category)
			},
			async : true,
			success : function(data) {
				var idFlag = data.id;
				if (idFlag != undefined) {
					zcal({
						type : 'success',
						title : '保存成功',
						time : 2000,
					});
					zzcTree.refreshAndSelected(data);
//					选中新增节点
					showNodeInfo(data);
				} else {
					var dataObj = JSON.parse(data);
					if(dataObj.description == 'the same cateName') {
						var $categoryInfoForCateName = $('#categoryInfoForCateName');
						$categoryInfoForCateName.closest('div').append('<label class="error" for="error-categoryInfoForCateName">类别名称一律不能重复</label>');
						$categoryInfoForCateName.focus();
					} else {
						zcal({
							type : 'error',
							title : dataObj.description,
							time : 2000,
						});
					}
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
};
// 初始化
catePage.init();
