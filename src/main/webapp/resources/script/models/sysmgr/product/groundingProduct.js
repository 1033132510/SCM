$(function() {
	ProductObject.init();
});

var baiduTemplate = baidu.template,
ProductObject = {
	bindContinueAddProductProperty : function() {
		$('#continueAdd').bind('click',	function() {
			var $productPropertiesContainer = $('#productPropertiesContainer'), number = ProductObject.generateNumber(),
			html = '<tr class="' + CUSTOM_TR + '">'
						+ '<td><div class="form-group form-inline text-center"><input type="text" name="propertyName" id="property_' + number + '" class="propertyName form-control"/></div></td>'
						+ '<td class="propertyTd"><div class="form-group pull-left"><input type="text" id="customInput_' + number + '" name="customInputRequire" class="customInput form-control"/></div></td>'
						+ '<td><div class="form-group"><input type="text" class="orderInput form-control sm-form-control text-center" value="' + ProductObject.generateOrder() + '" name="orderInput" id="order_'	+ number + '"/></div></td>'
						+ '<td><a class="red cancel">删除</a></td>'
				+ '</tr>';
				$productPropertiesContainer.append(html);
				ProductObject.bindClickForRemoveTr();
		});
	},
	generateOrder : function() {
		var $orderInputs = $('input[name="orderInput"]'), length = $orderInputs.length,
		i = 0, tempMaxOrder = 0;
		for(; i < length; i++) {
			var $orderInput = $($orderInputs[i]), order = $.trim($orderInput.val());
			if(!isNaN(order) && order) {
				order = parseInt(order);
				console.log(order);
				tempMaxOrder = Math.max(order, tempMaxOrder);
			}
		}
		return ++tempMaxOrder;
	},
	generateProductPropertyEditHtml : function(productCategoryItemKeys) {
		var obj = {
			list : productCategoryItemKeys,
			generateNumber : ProductObject.generateNumber,
			splitCharacter : ProductObject.splitCharacter
		};
		$('#productPropertiesContainer').html($(baiduTemplate('propertiesTemplate', obj)));
		ProductObject.bindClickForRemoveTr();
	},
	bindClickForRemoveTr : function() {
		$('#productPropertiesContainer .cancel').unbind().bind('click', function() {
			$(this).closest('tr').remove();
		});
	},
	// 生成唯一值
	generateNumber : function() {
		if (ProductObject.number) {
			return ++ProductObject.number;
		} else {
			return (ProductObject.number = 1);
		}
	},
	// 初始化页面
	init : function() {
		var id = $('#id').val(), cateId = $('#cateId').val();
		$.ajax({
			url : ctx + '/sysmgr/product/viewProductDetail',
			data : {
				productId : id,
				cateId : cateId
			},
			success : function(data) {
				var supplierOrg = data.supplierOrg, brand = data.brand,
				thirdProductCategory = data.category, secondProductCategory = thirdProductCategory.parentCategory,
				firstProductCategory = secondProductCategory.parentCategory,
				supplierName = supplierOrg.orgName, productImages = data.productImages,
				productDescImages = data.productDescImages;
				$('#productName').val(data.productName);
				$('#minOrderCount').val(data.minOrderCount);
				$('#supplierName').val(supplierName);
				$('#brandName').val(brand.brandZHName);
				// 设置*级品类
				var $firstProductCategoryInput = $('#firstProductCategoryName'), $secondProductCategoryInput = $('#secondProductCategoryName'),
				$thirdProductCategoryInput = $('#thirdProductCategoryName');
				$firstProductCategoryInput.val(firstProductCategory.cateName);
				$secondProductCategoryInput.val(secondProductCategory.cateName);
				$thirdProductCategoryInput.val(thirdProductCategory.cateName);
				$('#number').val(data.productNumber);
				$('#description').val(data.productDesc);
				// 回显图片
				$.fn.image.init('productImage', 14, {uploadCount : MAX_PRODUCT_IMAGE, multiple : true}, {relationId : data.productId, relationType : 14});
				$.fn.image.init('productImageDetail', 15, {uploadCount : MAX_PRODUCT_DETAIL_IMAGE, multiple : true}, {relationId : data.productId, relationType : 15});
				// 显示商品属性#productCategoryItemValues
				ProductObject.generateProductPropertyEditHtml(data.productCategoryItemValues);
			}
		});
		ProductObject.bindContinueAddProductProperty();
		ProductObject.validateProductForm();
	},
	// 校验商品属性table
	validatePropertyTable : function() {
		var $trs = $('#productPropertiesContainer').find('tr'), length, i = 0, flag = true;
		if ((length = $trs.length)) {
			for (; i < length; i++) {
				var $tr = $($trs[i]), $td = $tr.find('.notNullTd');
				// 必须是.notNullTd
				if ($td.length) {
					var $inputs = $td.find('.checked .system'), $customInput = $td.find('.customInput'),
					$div = $td.find('.form-group'), tdId = $td.attr('id'),
					// systemLength .checked .system长度
					$errorLabel = $div.find('label.error'), systemLength = $inputs.length,
					// customValue 自定义属性值 customLength 自定义属性框长度
					customValue = $.trim($customInput.val()), customLength = $td.find('.checked .custom').length;
					if (systemLength || (customValue && customLength) || (!$td.find('.system').length && customValue)) {
						$td.removeClass('error');
						$errorLabel.remove();
					} else {
						flag = false;
						// 如果上一次验证已经添加了label.error 就不用添加了
						if (!$errorLabel.length) {
							$td.addClass('error');
							$td.append('<label class="error" for="' + tdId + '">商品属性不能为空</label>');
						}
					}
				}
			}
		}
		// 如果用户没有设置系统属性，不能添加商品
		if (!length) {
			zcal({
				type : 'warning',
				title : '错误提示',
				text : '请设置商品属性'
			});
			flag = false;
		}
		if (!flag) {
			// 如果#productPropertiesContainer校验不通过，需要设置location.hash
			// 通过切换location.hash来定位页面，否则页面只在第一次定位
			var hash = location.hash;
			if (!hash || hash == '#hashProductProperty1') {
				location.hash = '#hashProductProperty2';
			} else {
				location.hash = '#hashProductProperty1';
			}
		}

		// 校验图片
		// 商品图片，如果个数为0或>MAX_PRODUCT_IMAGE，提示错误
		var productImageLength = $('#productImage').find('img[id^="img_14_"]').length;
		if (!productImageLength || productImageLength > MAX_PRODUCT_IMAGE) {
			flag = false;
			var hash = location.hash;
			if (!hash || hash == '#hashProductImage1') {
				location.hash = '#hashProductImage2';
			} else {
				location.hash = '#hashProductImage1';
			}
			zcal({
				type : 'warning',
				title : '错误提示',
				text : productImageLength ? '最多上传' + MAX_PRODUCT_IMAGE	+ '张商品图片' : '请至少上传一张商品图片'
			});
		}

		// 商品详情图片，如果个数为0或>productDetailImageLength，提示错误
		var productDetailImageLength = $('#productImageDetail').find('img[id^="img_15_"]').length;
		if (!productDetailImageLength || productDetailImageLength > MAX_PRODUCT_DETAIL_IMAGE) {
			flag = false;
			var hash = location.hash;
			if (!hash || hash == '#hashProductImageDetail1') {
				location.hash = '#hashProductImageDetail2';
			} else {
				location.hash = '#hashProductImageDetail1';
			}
			zcal({
				type : 'warning',
				title : '错误提示',
				text : productDetailImageLength ? '最多上传' + MAX_PRODUCT_DETAIL_IMAGE + '张商品详情图片' : '请至少上传一张商品详情图片'
			});
		}
		return flag;
	},
	// 校验表单
	validateProductForm : function() {
		// 如果是编辑商品，不需要校验#productCategoryContainer select
		var opt = {
			submitHandler : function(form) {
				if (ProductObject.validatePropertyTable()) {
					ProductObject.updateProduct();
				}
				return false;
			}
		}, rules = {
			productName : {
				required : true,
				maxlength : 60
			},
			number : {
				required : true,
				min : 0,
				number : true
			},
			minOrderCount : {
				required : true,
				min : 1,
				number : true
			},
			description : {
				maxlength : 2000
			},
			propertyName : {
				required : true
			},
			customInputRequire : {
				required : true
			},
			orderInput : {
				digits : true
			}
		}, messages = {
			productName : {
				required : '请输入商品名称',
				maxlength : '商品名称最多输入{0}个字符'
			},
			number : {
				required : '请输入商品库存数量'
			},
			minOrderCount : {
				required : '最小起订量必填'
			},
			description : {
				maxlength : '请输入商品简介描述信息，用于商品和公司页面展示，字数不超过{0}'
			},
			propertyName : {
				required : '属性名称必填'
			},
			customInputRequire : {
				required : '自定义属性值必填'
			},
			orderInput : {
				digits : '排序字段只能是整数'
			}
		};

		opt.rules = rules;
		opt.messages = messages;
		$('#productForm').validate(opt);
	},
	updateProduct : function() {
		var $productForm = $('#productForm'), product, 
		i = 0, productCategoryItemValues = [],
		$trs = $('#productPropertiesContainer').find('tr'), length = $trs.length,
		id = $('#id').val(), cateId = $('#cateId').val();

		// 防止重复提交
		if ($productForm.hasClass('disabled')) {
			return false;
		}
		$productForm.addClass('disabled');

		for (; i < length; i++) {
			var $tr = $($trs[i]), $td = $tr.find('.propertyTd'),
			labelType = $td.attr('data-type'), itemKeyId = $tr.attr('itemKeyId'),
			itemValueId = $tr.attr('itemValueId'),
			// 属性名可能是<input type="text" class="propertyName"/> 也可能是<tr
			// class="propertyName">
			propertyName = $tr.find('input[name="propertyName"]').val() || $tr.find('td.propertyName').html(),
			// 这里没有去掉.propertyTd是为了防止以后添加新的label出现问题
			$inputs = $tr.find('.propertyTd .checked .system'), inputLength = $inputs.length,
			// 如果排序没有值，就取默认值
			order = $tr.find('.orderInput').val(), m = 0,
			propertyValues = [], productCategoryItemValueObj,
			productPriceModels = [], $customInput = $tr.find('.propertyTd .customInput');

			for (; m < inputLength; m++) {
				propertyValues.push($($inputs[m]).val());
			}
			// 如果.propertyTd .checked .system不存在或者$tr.find('.propertyTd .checked
			// .custom').closest('label').hasClass('checked'),取自定义属性值
			if (!inputLength || $tr.find('.propertyTd .checked .custom').closest('label').hasClass('checked')) {
				propertyValues.push($.trim($customInput.val()));
			}

			productCategoryItemValueObj = {
				order : order,
				value : propertyValues.join(','),
				itemType : itemKeyId ? CATEGORY_TYPE : PRODUCT_TYPE,
				status : VALID_STATUS
			};

			if (itemValueId) {
				// 自定义属性
				productCategoryItemValueObj.id = itemValueId;
			} else {
				// 系统属性
				productCategoryItemValueObj.productCategoryItemKeyId = itemKeyId;
				productCategoryItemValueObj.productCategoryId = cateId;
			}

			// 如果不是系统属性，需要添加自定义属性名称
			if (!itemKeyId) {
				productCategoryItemValueObj.productPropertiesName = propertyName;
			}
			productCategoryItemValues.push(productCategoryItemValueObj);
		}

		product = {
			productId : id,
			status : VALID_STATUS,
			productName : $.trim($('#productName').val().replace(/\s+/g, ' ')),
			cateId : cateId,
			productDesc : $('#description').val(),
			productNumber : $('#number').val(),
			minOrderCount : $('#minOrderCount').val(),
			productImageIds : $.fn.image.getUploadImageIds(),
			delProductImageIds : $.fn.image.getDeleteImageIds(),
			productPropertiesModels : productCategoryItemValues
		};
		
		$.ajax({
			url : ctx + '/sysmgr/product/save',
			data : {
				productJSON : JSON.stringify(product)
			},
			type : 'post',
			success : function(data) {
				if (data.success) {
					zcal({
						type : 'success',
						title : '系统提示',
						text : '上架成功'
					});
					setTimeout(function() {
						location.href = ctx	+ '/sysmgr/product/invalidProductManager';
					}, TIME);
				} else {
					zcal({
						type : 'error',
						title : '系统提示',
						text : data.description
					});
				}
			},
			error : function() {
				$productForm.removeClass('disabled');
			}
		});
	},
	// 属性itemSources的分隔符
	splitCharacter : ','
},
// 自定义属性tr
CUSTOM_TR = 'customTr',
// 0:商品(属性)
PRODUCT_TYPE = 0,
// 1:商品类别(属性)
CATEGORY_TYPE = 1,
// 有效
VALID_STATUS = 1,
// TIME后跳转
TIME = 3000,
// 最大商品图片个数
MAX_PRODUCT_IMAGE = 15,
// 最大商品详细图片个数
MAX_PRODUCT_DETAIL_IMAGE = 15;

$.ajaxSetup({
	error : function() {
		zcal({
			type : 'warning',
			title : '错误提示',
			text : '服务器异常'
		});
	},
	dataType : 'json'
});