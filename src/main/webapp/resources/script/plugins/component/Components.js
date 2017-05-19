/**
 * author zhanygong
 */

window.Components = {
		/**
		 * 组件默认配置
		 */
		getOption : function() {
			return {
				// dom加载完成后执行回调方法
				callback : $.noop,
				// jquery dom 元素加载的容器
				container : null,
				// 默认显示值	比如<select><option>--请选择--</option></select>
				defaultHtml : '--请选择--',
				/**
				 * 一组dom元素的配置
				 * @param label			String	label
				 * @paran url			String	url
				 * @param key			String	url请求时添加到param中的键值
				 * @param type			String	url请求时默认使用get
				 * @param param			Object	url请求时的参数
				 * @param selectClasses	Array	select的class
				 * @param labelClasses	Array	label的class
				 */
				doms : [],
				/**
				 * <select><option value="data[Key.valueKey]">data[Key.nameKey]</option></select>
				 * 这里特别需要注意的是所有doms中Key完全一致
				 */
				Key : {
					nameKey : 'name',
					valueKey : 'id'
				},
				/**
				 * 如果doms中每个{}中没有配置labelClasses,每个dom元素会默认使用这个labelClasses,反之,使用doms中labelClasses
				 */
				labelClasses : [],
				/**
				 * 如果doms中每个{}中没有配置param,请求url时会默认使用这个param,反之,使用doms中{}的param
				 */ 
				param : {},
				/**
				 * 如果doms中每个{}中没有配置selectClasses,每个dom元素会默认使用这个selectClasses,反之,使用doms中selectClasses
				 */
				selectClasses : [],
				// 默认使用type
				type : 'get'
			};
		},
		Console : {
			// 检测字段是否为空
			warningNull : function(name, value, componentName) {
				if(value == null || value == undefined) {
					console.log('组件[' + componentName + ']必须指定[' + name + ']参数');
					return false;
				} else {
					return true;
				}
			},
			// 检测长度
			warningLength : function(name, array, length, componentName) {
				// 如果length能够转换为数字并且array是数组才做校验
				if($.isNumeric(length)) {
					if($.isArray(array)) {
						if(length === array.length) {
							console.log('组件[' + componentName + ']的[' + name + ']参数长度不能为' + length + '');
							return false;
						} else {
							return true;
						}
					} else {
						return false;
					}
				} else {
					return true;
				}
			},
			// 检测该元素是否是jquery对象
			warningQueryDom : function(name, value, testJQueryDom, componentName) {
				if(testJQueryDom == true) {
					if(value instanceof jQuery) {
						return true;
					} else {
						console.log('组件' + componentName + ']的[' + name + ']不是jQuery元素');
						return false;
					}
				} else {
					return true;
				}
			}
		},
		Validation : {
			getOption : function(opt) {
				var option = {
					testJQueryDom : null,
					length : null,
					name : ''
				};
				return $.extend(option, opt);
			},
			/**
			 * @param option		Object		配置
			 * @param validations	Array[]		需要校验的参数项
			 */
			validate : function(option, validations, component) {
				Console = Components.Console, componentName = component.componentName,
				flag = true;
				for(var i = 0; i < validations.length; i++) {
					var keyObject = $.extend(this.getOption(), validations[i]), name = keyObject.name,
					length = keyObject.length, value = option[name],
					jqueryDom = keyObject.jqueryDom, testJQueryDom = keyObject.testJQueryDom;
					if(!(Console.warningNull(name, value, componentName) && Console.warningQueryDom(name, value, testJQueryDom, componentName) && Console.warningLength(name, value, length, componentName))) {
						flag = false;
					}
				}
				// 返回校验结果
				return flag;
			}
		},
		/**
		 * 下拉联动
		 */
		Select : function() {
			var _this = this,
			select = {
				componentName : 'select',
				init : function(opts) {
					opts = $.extend(true, Components.getOption(), opts || {});
					var validations = [{
						name : 'container',
						testJQueryDom : true
					}, {
						name : 'doms',
						length : 0
					}];
					if(!Components.Validation.validate(opts, validations, _this)) {
						return false;
					}
					_this.generatHtml(opts);
				},
				generatHtml : function(opts) {
					var $container = opts.container, doms = opts.doms,
					length = doms.length, html = '',
					i = 0, Key = opts.Key,
					nameKey = Key.nameKey, valueKey = Key.valueKey,
					globalUrl = opts.url || '',	defaultHtml = opts.defaultHtml,
					globalParam = opts.param || {},	globalSelectClasses = opts.selectClasses || [],
					globalLabelClasses = opts.labelClasses || [], globalType = opts.type || 'get',
					callback = opts.callback, containerId = $container.attr('id');
					// 初始化
					for(; i < length; i++) {
						var opt = doms[i], label = opt.label,
						selectClasses = opt.selectClasses || globalSelectClasses, labelClasses = opt.labelClasses || globalLabelClasses;
						html += '<div class="form-group form-select media pull-left margin-right-25 padding-left-70">'
								+ '<label class="' + labelClasses.join(' ') + '">' + label + '：</label>'
								+ '<select class="' + selectClasses.join(' ') + '" name="' + (containerId + '_' + i) + '" id="' + containerId + '-' + i + '"></select>'
							+ '</div>';
					}
					$container.html(html);
					
					//去掉输入框
			        scrollTop({
			            name: '.back-top',
			            time: 500,
			            windowScroll: true
			        });
			        
					// 执行回调函数
					callback();
					
					// 设置第一个select的option begin -------------------------------------
					var $selects = $container.find('select'), $firstSelect = $selects.eq(0),
					firstOpt = opts.doms[0], url = firstOpt.url || globalUrl,
					param = firstOpt.param || globalParam, type = firstOpt.type || globalType;
					$.ajax({
						url : url,
						type : type,
						data : param,
						success : function(data) {
							var dataLength, html = '<option value="">' + defaultHtml + '</option>';
							if(data && (dataLength = data.length)) {
								for(i = 0; i < dataLength; i++) {
									var obj = data[i], name = obj[nameKey],
									value = obj[valueKey];
									html += '<option value="' + value + '">' + name + '</option>';
								}
							}
							$firstSelect.html(html);
						}
					});
					// 设置第一个select的option end   -------------------------------------
					
					/**
					 * select事件查询时可能带有其他的参数，所以需要传入param
					 */
					// 如果select个数大于1，需要设置前面n-1个select的change事件 begin ------------
					if(length > 1) {
						for(i = 0; i < length - 1; i++) {
							var opt = opts.doms[i + 1];
							(function(opt, index) {
								var $select = $selects.eq(index);
								// 删除unbind()是为了保留callback()中添加select的事件
								$select.bind('change', function() {
									
									var $childSelects = $container.find('select:gt(' + index + ')');
									$childSelects.empty();
									// 外部插件设置空值
									$childSelects.select2({
										placeholder: "--请选择--",
										minimumResultsForSearch: -1
									});
									
									$this = $(this), value = $this.val(),
									param = opt.param || globalParam, key = opt.key;
									param[key] = value;
									$.ajax({
										url : opt.url || globalUrl,
										type : opt.type || globalType,
										data : param,
										success : function(data) {
											var $childSelect = $selects.eq(index + 1),
											dataLength, html = '<option value="">' + defaultHtml + '</option>';
											if(data && (dataLength = data.length)) {
												for(i = 0; i < dataLength; i++) {
													var obj = data[i], name = obj[nameKey],
													value = obj[valueKey];
													html += '<option value="' + value + '">' + name + '</option>';
												}
											}
											$childSelect.html(html);
										}
									});
								});
							})(opt, i);
						}
					}
					// 如果select个数大于1，需要设置前面n-1个select的change事件 end ------------
				}
			};
			$.extend(_this, select);
		},
		Checkbox : function() {
			var _this = this,
			checkbox = {
				init : function(opts) {
					opts = $.extend(true, Components.getOption(), opts || {});
				}
			};
			$.extend(_this, checkbox);
		},
		Radio : function() {
			
		}
};

window.Utils = {
		ID : {
			/**
			 * 生成组件class
			 * @param componentName 组件名称
			 */
			generateClass : function(componentName) {
				return componentName + '_' + Utils.TIME.getTimeStamp() + '_' + (Math.random() * 10000);
			}
		},
		TIME : {
			getTimeStamp : function() {
				return new Date().getTime();
			}
		}
};

//$(function() {
//	var select = new Components.Select();
//	select.init({
//		container : $('#selectContainer'),
//		Key : {
//			valueKey : 'value'
//		},
//		doms : [{
//			label : '省份',
//			url : 'http://localhost:8080/SCM/demo/component/getProvinces',
//			key : 'id'
//		}, {
//			label : '城市',
//			url : 'http://localhost:8080/SCM/demo/component/getCitiesByProvinceId',
//			key : 'id'
//		}, {
//			label : '乡镇',
//			url : 'http://localhost:8080/SCM/demo/component/getAreasByCityId',
//			key : 'id'
//		}]
//	});
//});