$(function() {
	CategoryChargeObject.init();
});

var baiduTemplate = baidu.template,
CategoryChargeObject = {
		// 添加或修改按钮点击事件
		bindAddOrUpdateClick : function() {
			$('#addOrUpdate').bind('click', function() {
				var $this = $(this);
				if($this.hasClass('disabeld')) {
					return false;
				}
				
				var $checkedSecondDivs = $('.checked-div'), i = 0,
				length = $checkedSecondDivs.length, categoryChargeList = [],
				employeeId = $('#userId').val(), employeeName = $.trim($('#keywords').val()),
				$spans = $('#employeeList .employeeName'), lengthM = $spans.length,
				m = 0, flag = false;
				if(lengthM) {
					// 如果ul中有选项，那么employeeName应该和其中一项匹配
					for(; m < lengthM; m++) {
						var $span = $($spans[m]), name = $span.html(),
						id = $span.closest('li').attr('id');
						if(name == employeeName) {
							flag = true;
							// 提交的是在ul中匹配到的员工id
							employeeId = id;
							break;
						}
					}
					if(!flag) {
						zcal({
							type : 'warning',
							title : '错误提示',
							text : '请从员工姓名的搜索结果中选择一个选项'
						});
						return false;
					}
				} else {
					// 如果ul中没有选项，employeeName应该和#employeeName的值相同，否则阻止提交
					var name = $('#employeeName').val();
					if(name && (name != employeeName)) {
						zcal({
							type : 'warning',
							title : '错误提示',
							text : '员工姓名关键字搜索结果不存在'
						});
						return false;
					}
				}
				
				if(!employeeId) {
					zcal({
						type : 'warning',
						title : '错误提示',
						text : '请选择员工'
					});
					return false;
				}
				
				$this.addClass('disabled');
				var secondLevelCateIds = [];
				for(; i < length; i++) {
					var id = $($checkedSecondDivs[i]).attr('data-id'),
					categoryCharge = {
						secondLevelCategoryId : id,
						employeeId : employeeId
					};
					secondLevelCateIds.push(id);
					categoryChargeList.push(categoryCharge);
				}
				
				// 校验是否存在已经被分配的品类
				$('#checkedContainer div').removeClass('imp');
				var stopSubmit = false;
				$.ajax({
					url : ctx + '/sysmgr/category/charge/existOwnedCategoryForOthers',
					data : {ids : secondLevelCateIds.join(','), employeeId : employeeId, type : CATEGORY_MAJORDOMO},
					async : false,
					success : function(data) {
						var resultData = data.resultData, list = resultData.cateIds,
						i = 0, length = list.length,
						$errorContainer = $('#errorContainer'), $errorContainerSpan = $errorContainer.find('span');
						if(length) {
							for(; i < length; i++) {
								$('div[data-id=' + list[i] + ']').addClass('imp');
							}
							$errorContainerSpan.html(resultData.msg);
							$errorContainer.removeClass('hide');
							$this.removeClass('disabled');
							stopSubmit = true;
						} else {
							$errorContainer.addClass('hide');
						}
					}
				});
				if(stopSubmit) {
					return false;
				}
				
				$.ajax({
					url : ctx + '/sysmgr/category/charge/saveOrUpdate',
					data : {categoryChargeVOList : JSON.stringify(categoryChargeList), employeeId : employeeId},
					type : 'post',
					success : function(data) {
						if(data.success) {
							zcal({
								type : 'success',
								title : '系统提示',
								text : '操作成功'
							});
							setTimeout(function() {
								location.href = ctx + '/sysmgr/category/charge/major/index';
							}, 3000);
						}
					},
					error : function() {
						$this.removeClass('disabled');
					}
				});
			});
		},
		// 确认选中的选项
		bindCheckedCategoryClick : function() {
			$('#checkCategory').bind('click', function() {
				var $firstLabels = $('.first-level.checked'), $firstCateNameSpans = $firstLabels.find('.cateName'),
				i = 0, length = $firstLabels.length,
				// 保存获取的类别信息
				categoryList = [], $errorContainerSpan = $('#errorContainer').find('span');
				$errorContainerSpan.empty();
				for(; i < length; i++) {
					var $firstLabel = $($firstLabels[i]), firstLevelId = $firstLabel.attr('id'),
					// 获取选中的label
					$secondLabels = $('.' + firstLevelId + '.checked'), secondLabelsLength = $secondLabels.length,
					m = 0, category = {cateName : $firstLabel.closest('li').find('.cateName').html(), id : firstLevelId};
					if(secondLabelsLength) {
						var secondCategoryList = [];
						for(; m < secondLabelsLength; m++) {
							var $secondLabel = $($secondLabels[m]), secondLevelId = $secondLabel.attr('id'),
							secondCateName = $secondLabel.find('.second-level .cateName').html(),
							secondCategory = {id : secondLevelId, cateName : secondCateName};
							secondCategoryList.push(secondCategory);
						}
						category.child = secondCategoryList;
					}
					categoryList.push(category);
				}
				if(categoryList.length) {
					var obj = {categoryList : categoryList}
					$('#checkedContainer').html($(baiduTemplate('checkedCategories', obj)));
				} else {
					$('#checkedContainer').html('<span class="text-center">暂时没有分配的品类</span>');
				}
			});
		},
		bindKeywordsInputEvent : function() {
			var $employeeListContainer = $('#employeeListContainer');
			$('#keywords').bind('focus', function() {
				$employeeListContainer.show();
			}).bind('blur', function() {
				setTimeout(function() {
					$employeeListContainer.hide();
				}, 400);
			}).bind('keyup', function() {
				var keywords = $.trim($(this).val());
				$.ajax({
					url : ctx + '/sysmgr/employee/getEmployeeListByKeywords',
					data : {employeeName : keywords, type : CATEGORY_MAJORDOMO, limit : 5},
					type : 'post',
					success : function(data) {
						var i = 0, length,
						html = '', list = data;
						if(list && (length = list.length)) {
							for(; i < length; i++) {
								var obj = list[i];
								html += '<li id="' + obj.id + '"><span class="employeeName">' + obj.employeeName + '</span>+' + obj.mobile + '</li>';
							}
							$('#employeeList').html(html);
							$employeeListContainer.show();
						} else {
							$employeeListContainer.hide();
						}
					}
				});
			});
		},
		bindUlClick : function() {
			var $employeeListContainer = $('#employeeListContainer'), $keywords = $('#keywords');
			$employeeListContainer.bind('click', function(e) {
				var $li = $(e.target), id = $li.attr('id'),
				employeeName = $li.find('.employeeName').html();
				$keywords.val(employeeName);
				$('#userId').val(id);
				setTimeout(function() {
					$employeeListContainer.hide();
					CategoryChargeObject.getEmployeeInofByUserId();
				}, 400);
			});
		},
		// 获取所有的一---三级类别信息
		getCategoryList : function() {
			$.ajax({
				url : ctx + '/sysmgr/category/charge/getCategoryList',
				async : false,
				success : function(data) {
					var $firstLevelContainer = $('#firstLevelContainer'), firstCategoryList = data.firstCategoryList,
					firstItem = {
						list : firstCategoryList
					},
					$secondLevelContainer = $('#secondLevelContainer'),
					secondItem = {
						list : data.secondCategoryList
					};
					if(firstCategoryList && firstCategoryList.length) {
						secondItem.firstLevelId = firstCategoryList[0].id;
					}
					$firstLevelContainer.append($(baiduTemplate('firstLevel', firstItem)));
					$secondLevelContainer.append($(baiduTemplate('secondLevel', secondItem)));
					$('#firstLevelContainer li').bind('click', function(event) {
						var $li = $(this), id = $li.find('label').attr('id'),
						$childLis = $('.' + id).closest('li'), $otherChildLis = $('#secondLevelContainer').find('label[class!="' + id + '"]').closest('li');
						
						$li.addClass('on');
						$li.siblings().removeClass('on');
						$otherChildLis.addClass('hide');
						$childLis.removeClass('hide');
					});
					$('#secondLevelContainer li').bind('click', function(event) {
						var $lable = $(this).find('label'), $parentLi = $('li.on'),
						$parentLabel = $parentLi.find('label'), parentLiId = $parentLabel.attr('id');
						if(!$lable.hasClass('checked')) {
							$parentLi.find('label').addClass('checked');
						} else {
							if($('.' + parentLiId + '.checked').length == 1) {
								$parentLabel.removeClass('checked');
							}
						}
					});
				}
			});
		},
		// 通过userId获取员工负责的品类信息
		getEmployeeInofByUserId : function() {
			var userId = $('#userId').val();
			if(userId) {
				// 清空所有选中的checkbox
				$('label').removeClass('checked');
				$.ajax({
					url : ctx + '/sysmgr/category/charge/getCategoryChargeByUserId',
					data : {userId : userId, status : VALID_STATUS},
					success : function(data) {
						var length, i = 0;
						if(data && (length = data.length)) {
							for(; i < length; i++) {
								$('#' + data[i].secondLevelCategoryId).addClass('checked');
							}
						}
						$('#checkCategory').click();
					}
				});
			}
		},
		init : function() {
			this.bindUlClick();
			this.bindKeywordsInputEvent();
			this.bindCheckedCategoryClick();
			this.bindAddOrUpdateClick();
			this.getCategoryList();
			this.getEmployeeInofByUserId();
			// 一级品类label点击事件的回调函数
			window.labelCallback = function($label, param) {
				// 避免二级品类的点击
				if($label.closest('#secondLevelContainer').length) {
					return false;
				}
				var id = $label.attr('id'), $childLabels = $('.' + id),
				$otherChildLabels = $('#secondLevelContainer').find('label[class!="' + id + '"]');
				
				if(param.checked) {
					$childLabels.addClass('checked');
				} else {
					$childLabels.removeClass('checked');
				}
			};
		}
}, VALID_STATUS = 1,
CATEGORY_MAJORDOMO = 2;