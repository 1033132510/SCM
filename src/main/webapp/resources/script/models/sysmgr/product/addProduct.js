$(function() {
	ProductObject.init();
});

var baiduTemplate = baidu.template,
ProductObject = {
	bindContinueAddProductProperty : function() {
		$('#continueAdd').bind('click',	function() {
			if ($(this).hasClass('no-drop')) {
				return false;
			}
			var $productPropertiesContainer = $('#productPropertiesContainer'), number = ProductObject.generateNumber();
			if (!$productPropertiesContainer.hasClass('hide')) {
				var html = '<tr class="' + CUSTOM_TR + '">'
							+ '<td><div class="form-group form-inline text-center"><input type="text" name="propertyName" id="property_' + number + '" class="propertyName form-control"/></div></td>'
							+ '<td class="propertyTd"><div class="form-group pull-left"><input type="text" id="customInput_' + number + '" name="customInputRequire" class="customInput form-control"/></div></td>'
							+ '<td><div class="form-group"><input type="text" class="orderInput form-control sm-form-control text-center" value="' + ProductObject.generateOrder() + '" name="orderInput" id="order_'	+ number + '"/></div></td>'
							+ '<td><a class="red cancel">删除</a></td>'
						+ '</tr>';
				$productPropertiesContainer.append(html);
				ProductObject.bindClickForRemoveTr();
			}
		});
	},
	// 绑定#productCategoryContainer select,#brandSelectList的change事件
	bindSelectsChange : function() {
		$('#productCategoryContainer select,#brandSelectList').bind('change', function() {
			var $this = $(this), id = $this.attr('id');
			if($this.val()) {
				$('#' + id + '-error').remove();
			}
		});
	},
	bindUnitBlur : function() {
		$('#unit').bind('blur', function() {
			var $unitInput = $('input[name="unit"]'), unit = $.trim($(this).val());
			$unitInput.val(unit);
		});
	},
	// 绑定#supplierName上的keyup、blur、focus事件
	bindSupplierKeywordsEvents : function() {
		$('#supplierName').bind('keyup', function() {
			// 绑定#supplierName的keyup事件
			var supplierName = $.trim($(this).val());
			if (supplierName) {
				$.ajax({
					url : ctx + '/sysmgr/supplierOrg/getSupplierOrgList',
					data : {
						orgCodeOrorgName : supplierName,
						status : VALID_STATUS,
						curPage : 1,
						pageSize : 5
					},
					type : 'post',
					success : function(data) {
						var list, length, i = 0, html = '', $supplierNameUl = $('#supplierNameUl'),
						$inputBox = $supplierNameUl.closest('.input-box');
						// 先清空数据
						$supplierNameUl.empty();
						if (data && (list = data.data) && (length = list.length)) {
							$inputBox.show();
							for (; i < length; i++) {
								var supplier = list[i];
								html += '<li data-id="'	+ supplier.id + '" data-code="'	+ supplier.orgCode + '">' + supplier.orgName + '</li>';
							}
							$supplierNameUl.html(html);
							// 绑定#supplierNameUl li的点击事件
							$supplierNameUl.find('li').unbind().bind('click', ProductObject.getBrandListBySupplierId);
						} else {
							$inputBox.hide();
							$('#brandSelectList').html('<option value="" selected>请选择</option>');
						}
					}
				});
			}
		}).bind('focus', function() {
			// 获取焦点时 如果#supplierNameUl.innerHTML不为空，显示
			if ($('#supplierNameUl').html()) {
				$('#supplierNameUl').closest('.input-box').show();
			}
		}).bind('blur', function() {
			// 添加setTimeout是为了防止$('#supplierNameUl').closest('.input-box').hide()而阻止ProductObject.getBrandListBySupplierId执行
			setTimeout(function() {
				$('#supplierNameUl').closest('.input-box').hide();
			}, 400);
		});
	},
	// 绑定#sureChooseProductCategory点击事件，显示三级品类信息
	bindSureChooseProductCategory : function() {
		$('#sureChooseProductCategory').bind('click', function() {
			var id = $('#productCategoryContainer').find('select:eq(2)').val();
			// 三级品类必须选中才能显示三级品类信息
			if (id) {
				ProductObject.getProductCategoryDetailById(id);
				// 可以继续添加商品属性
				$('#continueAdd').removeClass('gray-bg gray-bd no-drop');
			} else {
				zcal({
					type : 'warning',
					title : '系统提示',
					text : '请选择三级品类信息'
				});
				// 不能继续添加商品属性
				$('#continueAdd').addClass('gray-bg gray-bd no-drop');
			}
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
	// 通过供货商id获取品牌
	getBrandListBySupplierId : function() {
		function decodeHtml(html) {
			if(!html) {
				return '';
			}
			return html.replace('&lt;', '<').replace('&gt;', '>').replace('&amp;', '&').replace('&quot;', '"');
		}
		$('#supplierName').val(decodeHtml($(this).html()));
		var $this = $(this), supplierId = $this.attr('data-id'),
		orgCode = $this.attr('data-code'), $supplierIdInput = $('#supplierId'),
		$supplierCodeInput = $('#supplierCode'), preSupplierId = $supplierIdInput.val();
		$supplierIdInput.val(supplierId);
		$supplierCodeInput.val(orgCode);
		$.ajax({
			url : ctx + '/sysmgr/brand/getBrandManagerList',
			type : 'post',
			data : {
				supplierOrgId : supplierId,
				status : VALID_STATUS,
				curPage : 1,
				pageSize : 500
			},
			success : function(data) {
				var list, length, i = 0,
				$brandSelectList = $('#brandSelectList'), html = '<option value="">请选择</option>';
				if (data && (list = data.data) && (length = list.length)) {
					for (; i < length; i++) {
						var brand = list[i], id = brand.id;
						html += '<option value="' + brand.id + '">'	+ brand.brandZHName + '</option>';
					}
				}
				$brandSelectList.html(html);
				// 模拟change事件
				if(supplierId != preSupplierId) {
					$brandSelectList.select2({
						placeholder: '--请选择--',
						minimumResultsForSearch: -1
					});
				}
			}
		});
	},
	// 获取折扣信息
	getDiscount : function() {
		$.ajax({
			url : ctx + '/sysmgr/categoryCustomerDiscount/getDiscountByCateIdAndCyustomer',
			data : {
				cateId : $('#productCategoryContainer').find('select:eq(2)').val()
			},
			async : false,
			success : function(data) {
				var i = 0, length, productPriceDiscountObj = {};
				if (data && (length = data.length)) {
					for (; i < length; i++) {
						var discountObj = data[i];
						// key:id value:discount
						productPriceDiscountObj[discountObj.customerLevel] = discountObj.discount;
					}
				}
				ProductObject.productPriceDiscountObj = productPriceDiscountObj;
				ProductObject.setLevelPrice();
			}
		});
	},
	getPriceKind : function() {
		$.ajax({
			url : ctx + '/sysmgr/productKindModel/list',
			success : function(data) {
				ProductObject.generatePriceHtml(data);
				ProductObject.bindUnitBlur();
			}
		});
	},
	// 生成商品价格代码片段
	generatePriceHtml : function(list) {
		var i = 0, length,
		priceAndCostHtml = '', firstLevelAndSecondLevelHtml = '',
		thirdLevelAndDescriptionHtml = '';
		if (list && (length = list.length)) {
			for (; i < length; i++) {
				var data = list[i], priceKindType = data.priceKindType,
				priceKindId = data.id;
				// 如果是更新，价格都应该是readonly
				switch (priceKindType) {
				case PRICE:
					priceAndCostHtml += '<div class="row clearfix">'
											+ '<div class="form-group short margin-top-20 margin-right-5 pull-left">'
												+ '<label><i class="imp">*</i>标价:</label>'
												+ '<input type="text" class="form-control padding-left-font2" id="actuallyPrice" name="actuallyPrice" data-priceKindId="' + priceKindId + '"/>'
											+ '</div>'
											+ '<div class="form-group checkbox pull-left margin-right-10 margin-top-20">'
			                                	+ '<label><span>元/</span></label>'
			                                + '</div>'
											+ '<div class="form-group min margin-top-20 margin-right-30 pull-left">'
			                                	+ '<input type="text" class="form-control" name="unit" id="unit" placeholder="单位"/>'
			                                + '</div>'
											+ '<div class="form-group margin-top-20 margin-left-20 checkbox pull-left">'
												+ '<label class="checked">'
												+ '<input type="checkbox" id="hasTax"/>'
												+ '<i></i><span>含税</span></label>'
												+ '<label class="checked margin-left-20">'
												+ '<input type="checkbox" id="hasTransportation">'
												+ '<i></i><span>含运费</span></label>'
											+ '</div>'
									+ '</div>';
					break;
				case COST:
					priceAndCostHtml += '<div class="row clearfix">'
											+ '<div class="form-group short margin-top-20 margin-right-5 pull-left">'
												+ '<label><i class="imp">*</i>成本:</label>'
												+ '<input type="text" class="form-control padding-left-font2" id="costPrice" name="costPrice" data-priceKindId="' + priceKindId + '"/>'
											+ '</div>'
											+ '<div class="form-group checkbox pull-left margin-right-10 margin-top-20">'
												+ '<label><span>元/</span></label>'
											+ '</div>'
											+ '<div class="form-group min margin-top-20 margin-right-30 pull-left">'
												+ '<input type="text" class="form-control" name="unit" readonly placeholder="单位"/>'
											+ '</div>'
										+ '</div>';
					break;
				case FIRST_LEVEL:
					firstLevelAndSecondLevelHtml += '<div class="row clearfix">'
														+ '<div class="form-group short margin-top-20 margin-right-5 pull-left">'
															+ '<label><i class="imp">*</i>一级客户报价:</label>'
															+ '<input type="text" class="form-control padding-left-font6" id="firstLevelPrice" name="firstLevelPrice" readonly data-priceKindId="' + priceKindId + '"/>'
														+ '</div>'
														+ '<div class="form-group checkbox pull-left margin-right-10 margin-top-20">'
															+ '<label><span>元/</span></label>'
														+ '</div>'
														+ '<div class="form-group min margin-top-20 margin-right-30 pull-left">'
															+ '<input type="text" class="form-control" name="unit" readonly placeholder="单位"/>'
														+ '</div>'
													+ '</div>';
					break;
				case SECOND_LEVEL:
					firstLevelAndSecondLevelHtml += '<div class="row clearfix">'
														+ '<div class="form-group short margin-top-20 margin-right-5 pull-left">'
															+ '<label><i class="imp">*</i>二级客户报价:</label>'
															+ '<input type="text" class="form-control padding-left-font6" id="secondLevelPrice" name="secondLevelPrice" readonly data-priceKindId="' + priceKindId	+ '"/>'
														+ '</div>'
														+ '<div class="form-group checkbox pull-left margin-right-10 margin-top-20">'
				                                			+ '<label><span>元/</span></label>'
				                                		+ '</div>'
				                                		+ '<div class="form-group min margin-top-20 margin-right-30 pull-left">'
				                                			+ '<input type="text" class="form-control" name="unit" readonly placeholder="单位"/>'
				                                		+ '</div>'
				                                	+ '</div>';
					break;
				case THIRD_LEVEL:
					thirdLevelAndDescriptionHtml += '<div class="row clearfix">'
														+ '<div class="form-group short margin-top-20 margin-right-5 pull-left">'
															+ '<label><i class="imp">*</i>三级客户报价:</label>'
															+ '<input type="text" class="form-control padding-left-font6" id="thirdLevelPrice" name="thirdLevelPrice" readonly data-priceKindId="' + priceKindId + '"/>'						
														+ '</div>'
														+ '<div class="form-group checkbox pull-left margin-right-10 margin-top-20">'
				                                			+ '<label><span>元/</span></label>'
				                                		+ '</div>'
				                                		+ '<div class="form-group min margin-top-20 margin-right-30 pull-left">'
				                                			+ '<input type="text" class="form-control" name="unit" readonly placeholder="单位"/>'
				                                		+ '</div>'
													+ '</div>'
													+ '<div class="row clearfix">'
														+ '<div class="form-group margin-top-5">'
															+ '<span class="point">客户价格，系统根据规则自动生成，只读不可修改</span>'
														+ '</div>'
													+ '</div>';
					break;
				}
			}
			$('#priceKindContainer').html(priceAndCostHtml + firstLevelAndSecondLevelHtml + thirdLevelAndDescriptionHtml);
			$('#costPrice').unbind().bind('blur', ProductObject.setCostPrice);
			$('#actuallyPrice').unbind().bind('blur', ProductObject.setLevelPrice);
		}
	},
	// 展示数据productCategoryItemKeys 系统属性
	generateProductPropertySaveHtml : function(productCategoryItemKeys) {
		if (productCategoryItemKeys	&& productCategoryItemKeys.length) {
			ProductObject.getDiscount();
		} else {
			ProductObject.productPriceDiscountObj = {};
		}
		// 显示属性框
		$('#productCategoryAttr').removeClass('hide');
		var obj = {
			list : productCategoryItemKeys,
			generateNumber : ProductObject.generateNumber,
			splitCharacter : ProductObject.splitCharacter
		};
		$('#productPropertiesContainer').html($(baiduTemplate('propertiesTemplate', obj)));
	},
	bindClickForRemoveTr : function() {
		$('#productPropertiesContainer .cancel').unbind().bind('click',	function() {
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
		ProductObject.initProductCategorySelect();
		ProductObject.bindSureChooseProductCategory();
		ProductObject.getPriceKind();
		$.fn.image.init('productImage', 14, {uploadCount : MAX_PRODUCT_IMAGE, multiple : true});
		$.fn.image.init('productImageDetail', 15, {uploadCount : MAX_PRODUCT_DETAIL_IMAGE, multiple : true});
		ProductObject.bindSupplierKeywordsEvents();
		ProductObject.bindContinueAddProductProperty();
		ProductObject.validateProductForm();
		ProductObject.bindSelectsChange();
	},
	// 通过三级商品品类id获取详细信息，并且将信息显示到
	getProductCategoryDetailById : function(id) {
		$.ajax({
			url : ctx + '/sysmgr/category/getCategoryInfoSimple',
			data : {
				cateId : id
			},
			success : function(data) {
				ProductObject.generateProductPropertySaveHtml(data.categoryItems);
			}
		});
	},
	// 初始化商品类别三级联动下拉框
	initProductCategorySelect : function() {
		selectProductCategory.init({
			callback : function() {
				$('#productCategoryContainer select').select2({
					placeholder : '--请选择--',
					minimumResultsForSearch : -1
				});
				// 如果#productCategoryContainer下任意一个select有change事件都会清空#productPropertiesContainer
				$('#productCategoryContainer').find('select').bind('change', function() {
					$('#productPropertiesContainer').empty();
					$('#productCategoryAttr').addClass('hide');
					// 没有点击确定不能继续添加属性
					var $continueAddDiv = $('#continueAdd');
					$continueAddDiv.addClass('gray-bg gray-bd no-drop');
				});
			},
			container : $('#productCategoryContainer'),
			labelClasses : [ 'pull-left' ],
			selectClasses : [ 'select', 'form-control' ],
			doms : [ {
				label : '一级品类',
				key : 'parentCategoryId',
				param : {
					level : 1,
					status : VALID_STATUS,
					parentCategoryId : PARENT_CATEGORY_ID
				}
			}, {
				label : '二级品类',
				key : 'parentCategoryId',
				param : {
					level : 2,
					status : VALID_STATUS
				}
			}, {
				label : '三级品类',
				key : 'parentCategoryId',
				param : {
					level : 3,
					status : VALID_STATUS
				}
			} ],
			Key : {
				nameKey : 'cateName',
				valueKey : 'id'
			},
			url : ctx + '/sysmgr/category/cateShow',
			type : 'post'
		});
	},
	// 校验商品属性table
	validatePropertyTable : function() {
		var $trs = $('#productPropertiesContainer').find('tr'), length,
		i = 0, flag = true;
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
		
		// 单位必填
		var unit = $('#unit').val();
		if(!unit) {
			zcal({
				type : 'warning',
				title : '错误提示',
				text : '请填写商品单位'
			});
			flag = false;
		}
		
		return flag;
	},
	// 校验表单
	validateProductForm : function() {
		var $supplierNameInput = $('#supplierName'), supplierName = 'supplierName' + new Date().getTime();
		$supplierNameInput.attr('name', supplierName);
		// 如果是编辑商品，不需要校验#productCategoryContainer select
		var opt = {
			submitHandler : function(form) {
				if (ProductObject.validatePropertyTable()) {
					ProductObject.saveProduct();
				}
				return false;
			}
		}, rules = {
			productName : {
				required : true,
				maxlength : 60
			},
			brandSelectList : {
				required : true
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
			},
			actuallyPrice : {
				required : true,
				min : 0,
				number : true
			},
			costPrice : {
				required : true,
				min : 0,
				number : true
			},
			firstLevelPrice : {
				required : true,
				min : 0,
				number : true
			},
			secondLevelPrice : {
				required : true,
				min : 0,
				number : true
			},
			thirdLevelPrice : {
				required : true,
				min : 0,
				number : true
			},
			feeRemark : {
				maxlength : 255
			},
			feeLogistics : {
				maxlength : 255
			}
		}, messages = {
			productName : {
				required : '请输入商品名称',
				maxlength : '商品名称最多输入{0}个字符'
			},
			brandSelectList : {
				required : '品牌必选'
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
			},
			actuallyPrice : {
				required : '标价必填',
				min : '标价至少为0',
				number : '标价只能是数字'
			},
			costPrice : {
				required : '成本价必填',
				min : '成本价至少为0',
				number : '成本价只能是数字'
			},
			firstLevelPrice : {
				required : '一级客户价必填',
				min : '一级客户价至少为0',
				number : '一级客户价只能是数字'
			},
			secondLevelPrice : {
				required : '二级客户价必填',
				min : '二级客户价至少为0',
				number : '二级客户价只能是数字'
			},
			thirdLevelPrice : {
				required : '三级客户价必填',
				min : '三级客户价至少为0',
				number : '三级客户价只能是数字'
			},
			feeRemark : {
				maxlength : '最多输入255个字符'
			},
			feeLogistics : {
				maxlength : '最多输入255个字符'
			}
		};
		
		// 提高用户体验
		rules[supplierName] = {
				required : true
		};
		messages[supplierName] = {
				required : '请选择供应商名称'
		};

		// 添加三个下拉框的校验
		if (!$('#id').val()) {
			rules.productCategoryContainer_0 = {
				required : true
			};
			rules.productCategoryContainer_1 = {
				required : true
			};
			rules.productCategoryContainer_2 = {
				required : true
			};
			messages.productCategoryContainer_0 = {
				required : '一级商品类别必选'
			};
			messages.productCategoryContainer_1 = {
				required : '二级商品类别必选'
			};
			messages.productCategoryContainer_2 = {
				required : '三级商品类别必选'
			};
		}

		opt.rules = rules;
		opt.messages = messages;
		$('#productForm').validate(opt);
	},
	saveProduct : function() {
		var $productForm = $('#productForm'), product, 
		cateId = $('#productCategoryContainer').find('select:eq(2)').val(), i = 0,
		productCategoryItemValues = [], $trs = $('#productPropertiesContainer').find('tr'),
		length = $trs.length, $actuallyPrice = $('#actuallyPrice'),
		$costPrice = $('#costPrice'), $firstLevelPrice = $('#firstLevelPrice'),
		$secondLevelPrice = $('#secondLevelPrice'), $thirdLevelPrice = $('#thirdLevelPrice');

		// 防止重复提交
		if ($productForm.hasClass('disabled')) {
			return false;
		}
		$productForm.addClass('disabled');

		for (; i < length; i++) {
			var $tr = $($trs[i]), $td = $tr.find('.propertyTd'),
			labelType = $td.attr('data-type'), itemKeyId = $tr.attr('itemKeyId'),
			// 属性名可能是<input type="text" class="propertyName"/> 也可能是<tr
			// class="propertyName">
			propertyName = $tr.find('input[name="propertyName"]').val()	|| $tr.find('td.propertyName').html(),
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
				status : VALID_STATUS,
				productCategoryItemKeyId : itemKeyId
			};

			// 如果不是系统属性，需要添加自定义属性名称
			if (!itemKeyId) {
				productCategoryItemValueObj.productPropertiesName = propertyName;
			}
			productCategoryItemValues.push(productCategoryItemValueObj);
		}

		product = {
			status : VALID_STATUS,
			productName : $.trim($('#productName').val().replace(/\s+/g, ' ')),
			brandId : $('#brandSelectList').val(),
			cateId : cateId,
			feeRemark : $('#feeRemark').val(),
			unit : $('#unit').val(),
			feeLogistics : $('#feeLogistics').val(),
			supplierOrgId : $('#supplierId').val(),
			orgCode : $('#supplierCode').val(),
			productDesc : $('#description').val(),
			hasTax : $('#hasTax').closest('label').hasClass('checked') ? 1 : 0,
			hasTransportation : $('#hasTransportation').closest('label').hasClass('checked') ? 1 : 0,
			productNumber : $('#number').val(),
			minOrderCount : $('#minOrderCount').val(),
			productImageIds : $.fn.image.getUploadImageIds(),
			delProductImageIds : $.fn.image.getDeleteImageIds(),
			productPropertiesModels : productCategoryItemValues
		};
		
		product.productPriceModels = [{
			id : $actuallyPrice.attr('data-productPriceId'),
			priceKindId : $actuallyPrice.attr('data-priceKindId'),
			actuallyPrice : new Number($actuallyPrice.val()).toFixed(2),
			status : VALID_STATUS
		}, {
			id : $costPrice.attr('data-productPriceId'),
			priceKindId : $costPrice.attr('data-priceKindId'),
			actuallyPrice : new Number($costPrice.val()).toFixed(2),
			status : VALID_STATUS
		}, {
			id : $firstLevelPrice.attr('data-productPriceId'),
			priceKindId : $firstLevelPrice.attr('data-priceKindId'),
			actuallyPrice : $firstLevelPrice.val(),
			status : VALID_STATUS
		}, {
			id : $secondLevelPrice.attr('data-productPriceId'),
			priceKindId : $secondLevelPrice.attr('data-priceKindId'),
			actuallyPrice : $secondLevelPrice.val(),
			status : VALID_STATUS
		}, {
			id : $thirdLevelPrice.attr('data-productPriceId'),
			priceKindId : $thirdLevelPrice.attr('data-priceKindId'),
			actuallyPrice : $thirdLevelPrice.val(),
			status : VALID_STATUS
		}];
		
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
						text : '添加成功'
					});
					setTimeout(function() {
						location.href = ctx	+ '/sysmgr/product/validProductManager';
					}, TIME);
				} else {
					zcal({
						type : 'error',
						title : '系统提示',
						text : data.description
					});
					$productForm.removeClass('disabled');
				}
			},
			error : function() {
				$productForm.removeClass('disabled');
			}
		});
	},
	setCostPrice : function() {
		var $costPrice = $('#costPrice'), costPrice = $costPrice.val();
		if(costPrice && !isNaN(costPrice)) {
			$costPrice.val(new Number(costPrice).toFixed(2));
		}
	},
	setLevelPrice : function() {
		var $actuallyPrice = $('#actuallyPrice'), actuallyPrice = $actuallyPrice.val(),
		productPriceDiscountObj = ProductObject.productPriceDiscountObj;
		// 必须设置了priceKindObj才能设置“*级客户价格”
		if (productPriceDiscountObj && actuallyPrice && !isNaN(actuallyPrice)) {
			// toFixed(2)会四舍五入，不精确
			actuallyPrice = new Number(new Number(actuallyPrice).toFixed(2));
			$actuallyPrice.val(actuallyPrice.toFixed(2));
			if (actuallyPrice >= 0) {
				var $firstLevelPrice = $('#firstLevelPrice'), $secondLevelPrice = $('#secondLevelPrice'),
				$thirdLevelPrice = $('#thirdLevelPrice'),
				// 如果没有获取到折扣，就默认0
				firstLevelPrice = actuallyPrice	* (1 - (productPriceDiscountObj['1'] || 0)),
				secondLevelPrice = actuallyPrice * (1 - (productPriceDiscountObj['2'] || 0)),
				thirdLevelPrice = actuallyPrice	* (1 - (productPriceDiscountObj['3'] || 0));
				$firstLevelPrice.val(new Number(firstLevelPrice).toFixed(2));
				$secondLevelPrice.val(new Number(secondLevelPrice).toFixed(2));
				$thirdLevelPrice.val(new Number(thirdLevelPrice).toFixed(2));
			}
		}
	},
	// 属性itemSources的分隔符
	splitCharacter : ','
}, selectProductCategory = new Components.Select(),
// 自定义属性tr
CUSTOM_TR = 'customTr',
// 0:商品(属性)
PRODUCT_TYPE = 0,
// 1:商品类别(属性)
CATEGORY_TYPE = 1,
// 有效
VALID_STATUS = 1,
// 标价
PRICE = -2,
// 成本价
COST = -1,
// 一级客户
FIRST_LEVEL = 1,
// 二级客户
SECOND_LEVEL = 2,
// 三级客户
THIRD_LEVEL = 3,
// TIME后跳转
TIME = 3000,
// 一级商品类别的父id
PARENT_CATEGORY_ID = 1;
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