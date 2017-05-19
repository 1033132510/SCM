$(function() {
	ProductObject.init();
});

var ProductObject = {
	productPropertiesIdsForDelete : [],
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
							+ '<td><div class="form-group"><input type="text" class="orderInput form-control sm-form-control text-center" value="' + DEFAULT_SHOW_NUMBER + '" name="orderInput" id="order_'	+ number + '"/></div></td>'
							+ '<td><a class="red cancel">删除</a></td>'
						+ '</tr>';
				$productPropertiesContainer.append(html);
				ProductObject.bindClickForRemoveTr();
			}
		});
	},
	// 绑定#supplierName上的keyup、blur、focus事件
	bindSupplierKeywordsEvents : function() {
		$('#supplierName').bind('keyup', function() {
			// 绑定#supplierName的keyup事件
			var supplierName = $.trim($(this).val());
			if (supplierName) {
				$.ajax({
					url : ctx
							+ '/sysmgr/supplierOrg/getSupplierOrgList',
					data : {
						orgCodeOrorgName : supplierName,
						curPage : 1,
						pageSize : 5
					},
					type : 'post',
					success : function(data) {
						var list, length, i = 0, html = '', $supplierNameUl = $('#supplierNameUl'), $inputBox = $supplierNameUl
								.closest('.input-box');
						// 先清空数据
						$supplierNameUl.empty();
						if (data
								&& (list = data.data)
								&& (length = list.length)) {
							$inputBox.show();
							for (; i < length; i++) {
								var supplier = list[i];
								html += '<li data-id="'
										+ supplier.id
										+ '" data-code="'
										+ supplier.orgCode
										+ '">'
										+ supplier.orgName
										+ '</li>'
							}
							$supplierNameUl.html(html);
							// 绑定#supplierNameUl li
							// 的点击事件
							$supplierNameUl
									.find('li')
									.unbind()
									.bind(
											'click',
											ProductObject.getBrandListBySupplierId);
						} else {
							$inputBox.hide();
							$('#brandSelectList')
									.html(
											'<option value="" selected>请选择</option>');
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
	// 通过供货商id获取品牌
	getBrandListBySupplierId : function(brandZHName, brandId, supplierName,
			supplierId, supplierCode) {
		$('#supplierName').val(supplierName || $(this).html());
		$('#supplierNameUl').closest('.input-box').hide();
		var $this = $(this), id = supplierId || $this.attr('data-id'), orgCode = $this
				.attr('data-code');
		$('#supplierId').val(id);
		$('#supplierCode').val(supplierCode || orgCode);
		$.ajax({
			url : ctx + '/sysmgr/brand/getBrandManagerList',
			type : 'post',
			data : {
				supplierOrgId : id,
				curPage : 1,
				pageSize : 500
			},
			success : function(data) {
				var list, length, i = 0, $brandSelectList = $('#brandSelectList'), html = '<option value="">请选择</option>';
				if (data && (list = data.data)
						&& (length = list.length)) {
					for (; i < length; i++) {
						var brand = list[i], id = brand.id;
						if (brandId == id) {
							html += '<option value="' + brand.id
									+ '" selected>' + brand.brandZHName
									+ '</option>'
						} else {
							html += '<option value="' + brand.id + '">'
									+ brand.brandZHName + '</option>'
						}
					}
				}
				$brandSelectList.html(html);
				// 编辑的时候需要默认选中该项,顺便获取折扣信息
				if (brandId) {
					$brandSelectList.val(brandId);
					$brandSelectList.select2({
						placeholder : brandZHName,
						minimumResultsForSearch : -1
					});
					// 获取折扣
					ProductObject.getDiscount();
				}
				// 隐藏
				$('#supplierNameUl').closest('.input-box').hide();
			}
		});
	},
	// 获取折扣信息
	getDiscount : function() {
		$.ajax({
			url : ctx
					+ '/sysmgr/categoryCustomerDiscount/getDiscountByCateIdAndCyustomer',
			data : {
				cateId : $('#productCategoryContainer').find(
						'select:eq(2)').val()
						|| $('#thirdProductCategoryName').attr(
								'data-id')
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
	getPriceKind : function(productPrices) {
		if (productPrices) {
			ProductObject.generatePriceHtml(0, productPrices);
		} else {
			$.ajax({
				url : ctx + '/sysmgr/productKindModel/list',
				success : function(data) {
					ProductObject.generatePriceHtml(1, data);
				}
			});
		}
	},
	// 生成商品价格代码片段
	generatePriceHtml : function(type, list) {
		var i = 0, length, priceAndCostHtml = '', firstLevelAndSecondLevelHtml = '', thirdLevelAndDescriptionHtml = '';
		if (list && (length = list.length)) {
			for (; i < length; i++) {
				var data = list[i], priceKindType, priceId = '', actuallyPrice = '', priceKindId;
				if (type == TYPE_AJAX) {
					// 没有productPrice对象 --------------data是priceKindModel
					priceKindType = data.priceKindType;
					priceKindId = data.id;
				} else {
					// 有productPrice对象 ---------------data是productPrice
					var priceKindModel = data.priceKindModel;
					priceKindId = priceKindModel.id;
					priceKindType = priceKindModel.priceKindType;
					priceId = data.id;
					actuallyPrice = data.actuallyPrice;
				}
				// 如果是更新，价格都应该是readonly
				switch (priceKindType) {
				case PRICE:
					priceAndCostHtml += '<div class="form-group margin-top-20">'
										+ '<label><i class="imp">*</i>标价:</label>'
										+ '<input type="text" class="form-control padding-left-font2 cost" id="actuallyPrice" name="actuallyPrice" value="' + actuallyPrice + '" data-priceKindId="' + priceKindId + '" data-productPriceId="' + priceId + '"' + (isUpdate ? ' readonly' : '') + '/>'
										+ '<label class="pull-right">元/单位</label>'
									+ '</div>';
					break;
				case COST:
					priceAndCostHtml += '<div class="form-group margin-top-20">'
										+ '<label><i class="imp">*</i>成本:</label>'
										+ '<input type="text" class="form-control padding-left-font2 cost" id="costPrice" name="costPrice" value="' + actuallyPrice	+ '" data-priceKindId="' + priceKindId + '" data-productPriceId="' + priceId + '"' + (isUpdate ? ' readonly' : '') + '/>'
										+ '<label class="pull-right">元/单位</label>'
									+ '</div>';
					break;
				case FIRST_LEVEL:
					firstLevelAndSecondLevelHtml += '<div class="form-group margin-top-20">'
													+ '<label><i class="imp">*</i>一级客户报价:</label>'
													+ '<input type="text" class="form-control padding-left-font6 cost" id="firstLevelPrice" value="' + actuallyPrice	+ '" name="firstLevelPrice" readonly data-priceKindId="' + priceKindId + '" data-productPriceId="' + priceId + '"/>'
													+ '<label class="pull-right">元/单位</label>'
												+ '</div>';
					break;
				case SECOND_LEVEL:
					firstLevelAndSecondLevelHtml += '<div class="form-group margin-top-20">'
													+ '<label><i class="imp">*</i>二级客户报价:</label>'
													+ '<input type="text" class="form-control padding-left-font6 cost" id="secondLevelPrice" value="' + actuallyPrice	+ '" name="secondLevelPrice" readonly data-priceKindId="' + priceKindId	+ '" data-productPriceId="'	+ priceId + '"/>'
													+ '<label class="pull-right">元/单位</label>'
												+ '</div>';
					break;
				case THIRD_LEVEL:
					thirdLevelAndDescriptionHtml += '<div class="form-group margin-top-20">'
													+ '<label><i class="imp">*</i>三级客户报价:</label>'
													+ '<input type="text" class="form-control padding-left-font6 cost" id="thirdLevelPrice" value="' + actuallyPrice	+ '" name="thirdLevelPrice" readonly data-priceKindId="' + priceKindId + '" data-productPriceId="' + priceId + '"/>'						
													+ '<label class=" pull-right">元/单位</label>'
												+ '</div>'
												+ '<div class="form-group margin-top-5">'
													+ '<span class="point">客户价格，系统根据规则自动生成，只读不可修改</span>'
												+ '</div>';
					break;
				}
			}
			$('#priceKindContainer').html(
					priceAndCostHtml + firstLevelAndSecondLevelHtml
							+ thirdLevelAndDescriptionHtml);
			$('#actuallyPrice').unbind().bind('blur',
					ProductObject.setLevelPrice);
		}
	},
	// 展示数据productCategoryItemKeys 系统属性
	generateProductPropertySaveHtml : function(productCategoryItemKeys) {
		var html = '', i = 0, length = 0, splitCharacter = ProductObject.splitCharacter, generateNumber = ProductObject.generateNumber;
		if (productCategoryItemKeys
				&& (length = productCategoryItemKeys.length)) {
			for (; i < length; i++) {
				var itemKeyObj = productCategoryItemKeys[i], itemKeyId = itemKeyObj.id,
				// 1:不为空
				allowedNotNull = itemKeyObj.allowedNotNull || 0, allowedCustom = itemKeyObj.allowedCustom, itemsSources = itemKeyObj.itemsSources
						|| '', allowedMultiSelect = itemKeyObj.allowedMultiSelect, itemCode = itemKeyObj.itemCode, itemName = itemKeyObj.itemName, m = 0, itemsSourcesLength = 0, labelType = allowedMultiSelect ? 'checkbox'
						: 'radio', number = generateNumber(),
				// hasItemsSources 如果$.trim(itemsSources)长度不为0，可以拆分为数组
				itemsSourcesArray = [], hasItemsSources = $.trim(itemsSources).length > 0, defaultShowNumber = itemKeyObj.defaultShowNumber
						|| DEFAULT_SHOW_NUMBER;

				if (hasItemsSources) {
					itemsSourcesArray = itemsSources.split(splitCharacter);
				}

				html += '<tr itemKeyId="' + itemKeyId
						+ '" itemValueId="" class="property_' + number + '">'
						+ '<td class="propertyName">'
						+ (allowedNotNull ? '<i class="imp">*</i>' : '')
						+ itemName + '</td>'
						+ '<td class="propertyTd form-group'
						+ (allowedNotNull ? ' notNullTd' : '')
						+ '" data-type="'
						+ (allowedMultiSelect ? 'checkbox' : 'radio')
						+ '" id="notNull_' + number + '">'
						+ '<div class="form-group'
						+ (hasItemsSources ? ' ' + labelType : '') + '">';
				// 组装itemSources
				if (itemsSourcesArray
						&& (itemsSourcesLength = itemsSourcesArray.length)) {
					for (; m < itemsSourcesLength; m++) {
						var itemsSource = itemsSourcesArray[m];
						// 默认选中第一个
						if (!m) {
							html += '<label class="pull-left margin-right-15 checked" for="property_'
									+ number
									+ '" checked>'
									+ '<input type="'
									+ labelType
									+ '" name="property_'
									+ number
									+ '" class="propertyValue system" value="'
									+ itemsSource
									+ '"/><i></i><span>'
									+ itemsSource + '</span>' + '</label>';
						} else {
							html += '<label class="pull-left margin-right-15" for="property_'
									+ number
									+ '">'
									+ '<input type="'
									+ labelType
									+ '" name="property_'
									+ number
									+ '" class="propertyValue system" value="'
									+ itemsSource
									+ '"/><i></i><span>'
									+ itemsSource + '</span>' + '</label>';
						}
					}
				}

				if (allowedCustom && itemsSourcesLength) {
					html += '<label class="pull-left" for="property_'
							+ number
							+ '"><input data-type="custom" type="'
							+ labelType
							+ '" name="property_'
							+ number
							+ '" class="propertyValue custom"/><i class="customI"></i></label>';
				}

				html += '</div>';

				if (allowedCustom) {
					html += '<div class="form-group pull-left">'
							+ '<input type="text" class="customInput form-control"/>'
							+ '</div>';
				}

				html += '</td>'
						+ '<td><div class="form-group"><input type="text" value="'
						+ defaultShowNumber
						+ '" class="orderInput form-control sm-form-control text-center" name="orderInput" id="order_'
						+ number + '"/></div></td>' + '<td></td>' + '</tr>';
			}
			ProductObject.getDiscount();
		} else {
			ProductObject.productPriceDiscountObj = {};
		}
		// 显示属性框
		$('#productCategoryAttr').removeClass('hide');
		$('#productPropertiesContainer').html(html);
	},
	// 展示数据productCategoryItemKeys 系统属性和商品属性
	generateProductPropertyEditHtml : function(productCategoryItemKeys) {
		var html = '', i = 0, length = 0, splitCharacter = ProductObject.splitCharacter, generateNumber = ProductObject.generateNumber;
		if (productCategoryItemKeys
				&& (length = productCategoryItemKeys.length)) {
			for (; i < length; i++) {
				var itemValueObj = productCategoryItemKeys[i], itemValueId = itemValueObj.id;
						// 属性值 属性名
						itemValue = itemValueObj.value,
						productPropertiesName = itemValueObj.productPropertiesName,
						itemType = itemValueObj.itemType,
						itemKeyObj = itemValueObj.productCategoryItemKey,
						itemKeyId = itemKeyObj && itemKeyObj.id || '',
						orderNumber = itemValueObj.orderNumber,
						// 自定义属性的itemKeyObj为空，那么allowedNotNull默认为0
						number = generateNumber(), allowedNotNull = itemKeyObj
								&& itemKeyObj.allowedNotNull || 0,
						itemValueArray = itemValue.split(splitCharacter);
				if (itemKeyObj) {
					productPropertiesName = itemKeyObj.itemName;
				}
				html += '<tr itemKeyId="' + itemKeyId + '" itemValueId="'
						+ itemValueId + '" class="property_' + number + '">'
				if (itemType) {
					// 系统属性
					var allowedMultiSelect = itemKeyObj.allowedMultiSelect, labelType = allowedMultiSelect ? 'checkbox'
							: 'radio', itemsSources = itemKeyObj.itemsSources
							|| '', itemsSourcesLength, m = 0,
					// flag: 是否在checkbox或者radio中匹配到
					allowedCustom = itemKeyObj.allowedCustom,
					// hasItemsSources 如果$.trim(itemsSources)长度不为0，可以拆分为数组
					itemsSourcesArray = [], hasItemsSources = $
							.trim(itemsSources).length > 0;

					if (hasItemsSources) {
						itemsSourcesArray = itemsSources.split(splitCharacter);
					}

					html += '<td class="propertyName">'
							+ (allowedNotNull ? ' <i class="imp">*</i>' : '')
							+ productPropertiesName + '</td>'
							+ '<td class="propertyTd form-group'
							+ (allowedNotNull ? ' notNullTd' : '')
							+ '" data-type="' + labelType + '">'
							+ '<div class="form-group'
							+ (hasItemsSources ? ' ' + labelType : '') + '">';
					if (itemsSourcesArray
							&& (itemsSourcesLength = itemsSourcesArray.length)) {
						var itemValues = itemValue.split(splitCharacter);
						for (; m < itemsSourcesLength; m++) {
							var itemsSource = itemsSourcesArray[m], index = itemValueArray
									.indexOf(itemsSource);
							if (index >= 0) {
								itemValueArray.splice(index, 1);
							}
							html += '<label class="margin-right-15 pull-left'
									+ (index >= 0 ? ' checked' : '') + '">'
									+ '<input type="' + labelType
									+ '" name="property_' + number
									+ '" class="propertyValue system" value="'
									+ itemsSource + '"/>' + '<i></i><span>'
									+ itemsSource + '</span>' + '</label>';
						}
					}

					if (allowedCustom && itemsSourcesLength) {
						html += '<label class="pull-left'
								+ (itemValueArray.length ? ' checked' : '')
								+ '" for="property_'
								+ number
								+ '"><input type="'
								+ labelType
								+ '" name="property_'
								+ number
								+ '" class="custom propertyValue"/><i class="customI"></i></label>';
					}

					html += '</div>';

					if (allowedCustom) {
						html += '<div class="form-group pull-left">'
								+ '<input type="text" class="customInput form-control" value="'
								+ (itemValueArray.length ? itemValueArray
										.join(',') : '') + '"/>' + '</div>';
					}

					html += '</td>';
				} else {
					// 自定义属性 这个td不需要加data-type属性
					html += '<td class="propertyName">'
							+ '<div class="form-group">'
							+ '<input type="text" name="propertyName" id="property_'
							+ number
							+ '" value="'
							+ productPropertiesName
							+ '" class="form-control"/>'
							+ '</div>'
							+ '</td>'
							+ '<td class="propertyTd">'
							+ '<div class="form-group">'
							+ '<input type="text" id="customInput_'
							+ number
							+ '" name="customInputRequire" class="customInput form-control" value="'
							+ itemValue + '"/>' + '</div>'

							+ '</td>';
				}
				html += '<td>'
						+ '<div class="form-group">'
						+ '<input type="text" value="'
						+ orderNumber
						+ '" class="orderInput form-control sm-form-control text-center" name="orderInput" id="order_'
						+ number + '"/>' + '</div>' + '</td>';
				if (itemType) {
					html += '<td></td>';
				} else {
					html += '<td><a class="red cancel">删除</a></td>';
				}
				html += '</tr>';
			}
		}
		// 显示属性框
		$('#productCategoryAttr').removeClass('hide');
		$('#productPropertiesContainer').html(html);
		ProductObject.bindClickForRemoveTr();
	},
	bindClickForRemoveTr : function() {
		$('#productPropertiesContainer .cancel').unbind().bind(
				'click',
				function() {
					var $tr = $(this).closest('tr'), itemValueId = $tr
							.attr('itemValueId');
					if (itemValueId) {
						ProductObject.productPropertiesIdsForDelete
								.push(itemValueId);
					}
					$tr.remove();
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
		// 如果id存在就获取详情
		if (id && cateId) {
			$.ajax({
				url : ctx + '/sysmgr/product/viewProductDetail',
				data : {
					productId : id,
					cateId : cateId
				},
				success : function(data) {
					var supplierOrg = data.supplierOrg, brand = data.brand, thirdProductCategory = data.category, secondProductCategory = thirdProductCategory.parentCategory, firstProductCategory = secondProductCategory.parentCategory, supplierName = supplierOrg.orgName, supplierId = supplierOrg.id, supplierCode = supplierOrg.orgCode, productImages = data.productImages, productDescImages = data.productDescImages;
					$('#productName').val(data.productName);
					$('#minOrderCount').val(data.minOrderCount);
					$('#supplierId').val(supplierId);
					$('#supplierCode').val(supplierCode);
					$('#supplierName').val(supplierName);
					// 如果是编辑商品，需要显示#productCategoryDefaultContainer,#productPropertyForm
					$('#productCategoryDefaultContainer,#productPropertyForm').removeClass('hide');
					// 设置*级品类
					var $firstProductCategoryInput = $('#firstProductCategoryName'), $secondProductCategoryInput = $('#secondProductCategoryName'),
					$thirdProductCategoryInput = $('#thirdProductCategoryName');
					$firstProductCategoryInput.val(firstProductCategory.cateName);
					$firstProductCategoryInput.attr('data-id', firstProductCategory.id);
					$secondProductCategoryInput.val(secondProductCategory.cateName);
					$secondProductCategoryInput.attr('data-id', secondProductCategory.id);
					$thirdProductCategoryInput.val(thirdProductCategory.cateName);
					$thirdProductCategoryInput.attr('data-id', thirdProductCategory.id);
					// 移除#continueAdd的默认样式
					$('#continueAdd').removeClass('gray-bg gray-bd no-drop');
					$('#sureChooseProductCategory').addClass('hide');
					$('#number').val(data.productNumber);
					$('#feeRemark').val(data.feeRemark);
					$('#feeLogistics').val(data.feeLogistics);
					$('#description').val(data.productDesc);
					// 显示品牌
					ProductObject.getBrandListBySupplierId(brand.brandZHName, brand.id, supplierName, supplierId, supplierCode);
					// 回显图片
					$.fn.image.init('productImage', 14, {uploadCount : MAX_PRODUCT_IMAGE, multiple : true}, {relationId : data.productId, relationType : 14});
					$.fn.image.init('productImageDetail', 15, {uploadCount : MAX_PRODUCT_DETAIL_IMAGE, multiple : true}, {relationId : data.productId, relationType : 15});
					// 显示商品属性#productCategoryItemValues
					ProductObject.generateProductPropertyEditHtml(data.productCategoryItemValues);
					// 显示price设置默认值
					ProductObject.getPriceKind(data.productPrices);
				}
			});
		} else {
			// 添加商品的时候显示#productCategoryContainer,#sureChooseProductCategory
			$('#productCategoryContainer').removeClass('hide');
			ProductObject.initProductCategorySelect();
			ProductObject.bindSureChooseProductCategory();
			ProductObject.getPriceKind();
			$.fn.image.init('productImage', 14, {uploadCount : MAX_PRODUCT_IMAGE, multiple : true});
			$.fn.image.init('productImageDetail', 15, {uploadCount : MAX_PRODUCT_DETAIL_IMAGE, multiple : true});
		}
		$('.tab-container .tab').css('display', 'block');
		ProductObject.bindSupplierKeywordsEvents();
		ProductObject.bindContinueAddProductProperty();
		ProductObject.validateProductForm();
	},
	// 通过三级商品品类id获取详细信息，并且将信息显示到
	getProductCategoryDetailById : function(id) {
		$.ajax({
			url : ctx + '/sysmgr/category/getCategoryInfoSimple',
			data : {
				cateId : id
			},
			success : function(data) {
				ProductObject
						.generateProductPropertySaveHtml(data.categoryItems);
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
				$('#productCategoryContainer')
						.find('select')
						.bind(
								'change',
								function() {
									$('#productPropertiesContainer')
											.empty();
									$('#productCategoryAttr').addClass(
											'hide');
									// 没有点击确定不能继续添加属性
									var $continueAddDiv = $('#continueAdd');
									$continueAddDiv
											.addClass('gray-bg gray-bd no-drop');
								});
			},
			container : $('#productCategoryContainer'),
			labelClasses : [ 'pull-left' ],
			selectClasses : [ 'select', 'form-control' ],
			doms : [ {
				label : '一级品类',
				key : 'parentCategoryId',
				param : {
					status : VALID_STATUS,
					parentCategoryId : PARENT_CATEGORY_ID
				}
			}, {
				label : '二级品类',
				key : 'parentCategoryId',
				param : {
					status : VALID_STATUS
				}
			}, {
				label : '三级品类',
				key : 'parentCategoryId',
				param : {
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
					if (systemLength || (customValue && customLength)
							|| (!$td.find('.system').length && customValue)) {
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
				text : productImageLength ? '最多上传' + MAX_PRODUCT_IMAGE
						+ '张商品图片' : '请至少上传一张商品图片'
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
					ProductObject.saveOrUpdateProduct();
				}
				return false;
			}
		}, rules = {
			productName : {
				required : true,
				maxlength : 30
			},
			supplierId : {
				required : true
			},
			supplierName : {
				required : true
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
			supplierId : {
				required : '请选择供应商名称'
			},
			supplierName : {
				required : '请选择供应商名称'
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
	saveOrUpdateProduct : function() {
		var $productForm = $('#productForm'), product, 
		cateId = $('#productCategoryContainer').find('select:eq(2)').val(), i = 0,
		productCategoryItemValues = [], $trs = $('#productPropertiesContainer').find('tr'),
		length = $trs.length, $actuallyPrice = $('#actuallyPrice'),
		$costPrice = $('#costPrice'), $firstLevelPrice = $('#firstLevelPrice'),
		$secondLevelPrice = $('#secondLevelPrice'), $thirdLevelPrice = $('#thirdLevelPrice'),
		id = $('#id').val()	|| '';

		// 防止重复提交
		if ($productForm.hasClass('disabled')) {
			return false;
		}
		$productForm.addClass('disabled');

		for (; i < length; i++) {
			var $tr = $($trs[i]), $td = $tr.find('.propertyTd'), labelType = $td
					.attr('data-type'), itemKeyId = $tr.attr('itemKeyId'), itemValueId = $tr
					.attr('itemValueId'),
			// 属性名可能是<input type="text" class="propertyName"/> 也可能是<tr
			// class="propertyName">
			propertyName = $tr.find('input[name="propertyName"]').val()
					|| $tr.find('td.propertyName').html(),
			// 这里没有去掉.propertyTd是为了防止以后添加新的label出现问题
			$inputs = $tr.find('.propertyTd .checked .system'), inputLength = $inputs.length,
			// 如果排序没有值，就取默认值
			order = $tr.find('.orderInput').val() || DEFAULT_SHOW_NUMBER, m = 0, propertyValues = [], productCategoryItemValueObj, productPriceModels = [], $customInput = $tr
					.find('.propertyTd .customInput');

			for (; m < inputLength; m++) {
				propertyValues.push($($inputs[m]).val());
			}
			// 如果.propertyTd .checked .system不存在或者$tr.find('.propertyTd .checked
			// .custom').closest('label').hasClass('checked'),取自定义属性值
			if (!inputLength
					|| $tr.find('.propertyTd .checked .custom')
							.closest('label').hasClass('checked')) {
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
			productName : $('#productName').val(),
			brandId : $('#brandSelectList').val(),
			cateId : $('#cateId').val() || cateId,
			feeRemark : $('#feeRemark').val(),
			feeLogistics : $('#feeLogistics').val(),
			supplierOrgId : $('#supplierId').val(),
			orgCode : $('#supplierCode').val(),
			productDesc : $('#description').val(),
			productNumber : $('#number').val(),
			minOrderCount : $('#minOrderCount').val(),
			productImageIds : $.fn.image.getUploadImageIds(),
			delProductImageIds : $.fn.image.getDeleteImageIds(),
			productPropertiesModels : productCategoryItemValues,
			delProductPropertiesIds : ProductObject.productPropertiesIdsForDelete
		};
		
		// 如果不是更新需要添加价格属性
		if(!isUpdate) {
			product.productPriceModels = [{
				id : $actuallyPrice.attr('data-productPriceId'),
				priceKindId : $actuallyPrice.attr('data-priceKindId'),
				actuallyPrice : $actuallyPrice.val(),
				status : VALID_STATUS
			}, {
				id : $costPrice.attr('data-productPriceId'),
				priceKindId : $costPrice.attr('data-priceKindId'),
				actuallyPrice : $costPrice.val(),
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
			}]
		}
		
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
						location.href = ctx
								+ '/sysmgr/product/toProductManager';
					}, TIME);
				}
			},
			error : function() {
				$productForm.removeClass('disabled');
			}
		});
	},
	setLevelPrice : function() {
		var actuallyPrice = $('#actuallyPrice').val(), productPriceDiscountObj = ProductObject.productPriceDiscountObj;
		// 必须设置了priceKindObj才能设置“*级客户价格”
		if (productPriceDiscountObj && actuallyPrice) {
			actuallyPrice = new Number(actuallyPrice);
			if (actuallyPrice >= 0) {
				var $firstLevelPrice = $('#firstLevelPrice'), $secondLevelPrice = $('#secondLevelPrice'), $thirdLevelPrice = $('#thirdLevelPrice'),
				// 如果没有获取到折扣，就默认0
				firstLevelPrice = actuallyPrice
						* (1 - (productPriceDiscountObj['1'] || 0)), secondLevelPrice = actuallyPrice
						* (1 - (productPriceDiscountObj['2'] || 0)), thirdLevelPrice = actuallyPrice
						* (1 - (productPriceDiscountObj['3'] || 0));
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
// 有actuallyPrice
TYPE_ = 0,
// ajax获取的值，没有actuallyPrice
TYPE_AJAX = 1,
// TIME后跳转
TIME = 3000,
// 一级商品类别的父id
PARENT_CATEGORY_ID = 1;
// 默认显示的order值
DEFAULT_SHOW_NUMBER = 10,
// 最大商品图片个数
MAX_PRODUCT_IMAGE = 15,
// 最大商品详细图片个数
MAX_PRODUCT_DETAIL_IMAGE = 15,
// 是否是修改
isUpdate = !!$('#id').val();

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