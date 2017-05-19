(function($) {
	var deleteImageIds = [];
	var uploadImageObj = {};
	var relationTypeToSettings = {};
	$.fn.image = {
			'init' : function(divId, relationType, options, data) {
				try {
					if(options) {
						isValid(options);
					}
				}catch(e) {
					console.log(e)
					return false;
				}
				
				var empty = {}; 
				var defaults = { 
					url : ctx + '/image',
					type : 'POST',
					dataType : 'json',
					singleFileUploads : true,
					readonly : false,
					multipart : true,
					autoUpload : true,
					uploadCount : 1, // 允许上传图片数量
					multiple : false, // 是否传多张图片
				}; 
				var settings = jQuery.extend(empty, defaults, options);
				if(!$.isNumeric(settings.uploadCount) || !settings.multipart || settings.uploadCount <= 0) {
					settings.uploadCount = 1;
				}
				relationTypeToSettings[relationType] = settings;
				relationTypeToSettings[relationType].divId = divId;
				render(data, $('#' + divId), relationType, settings);
				uploadFile(settings, $('#file_' + relationType), relationType);
			},
			'getUploadImageIds' : function() {
				var uploadImageIds = [];
				for(var key in uploadImageObj) {
					uploadImageIds.push(uploadImageObj[key]);
				}
				return uploadImageIds;
			},
			'getDeleteImageIds' : function() {
				return deleteImageIds;
			},
			'clearData' : function() {
				deleteImageIds = [];
				uploadImageObj = {};
			},
			'reloadImage' : function(imgSrc, relationType, id, readonly) {
				$('#' + relationTypeToSettings[relationType].divId).each(function() {
					$(this).empty();
					createDomAndBindEvent(imgSrc, relationType, id, $(this), readonly);
				});
			},
			'reInit' : function(relationTypes) {
				for(var i = 0; i < relationTypes.length; i++) {
					$('#' + relationTypeToSettings[relationTypes[i]].divId).empty();
					createUploadInput($('#' + relationTypeToSettings[relationTypes[i]].divId), relationTypes[i], relationTypeToSettings[relationTypes[i]].multiple);
				}
			}
	};
	function uploadFile(settings, input, relationType) {
		input.fileupload({
			url : settings.url,
			dataType : settings.dataType,
			add : function(e, data) {
				var alearyUploadCount = 0;
				if($('#' + relationTypeToSettings[relationType].divId).find('span[class="preview"]').length > 0) {
					alearyUploadCount += $('#' + relationTypeToSettings[relationType].divId).find('span[class="preview"]').length;
				}
				if(relationTypeToSettings[relationType].multiple && (alearyUploadCount + data.originalFiles.length) > relationTypeToSettings[relationType].uploadCount) {
					zcal({
				        type: 'error',
				        title: '超过最大上传数量'
				    });
					return false;
				}
				var isPass = true;
				for(var i = 0; i < data.originalFiles.length; i++) {
					var errorMessages = validateImage(data.originalFiles[i], settings);
					if(errorMessages != '') {
						zcal({
					        type: 'error',
					        title: errorMessages
					    });
						isPass = false;
						break;
					}
				}
				if(!isPass) {
					return false;
				}
				data.formData = {
						relationType : relationType
				}
				data.submit();
			},
			done : function(e, data) {
				if(data && data.result.success) {
					var imageObj = $.parseJSON(data.result.resultData);
					showPreview(data.files[0], relationType, imageObj.id);
					uploadImageObj[imageObj.id] = imageObj.id;
				}
			},
			fail : function(e, data) {
				zcal({
			        type: 'error',
			        title: '上传错误'
			    });
			}
		});
	}
	
	function render(params, div, relationType, settings) {
		div.empty();
		$.ajax({
			url : ctx + '/image/',
			type : 'GET',
			data : params,
			dataType : 'json',
			async : false,
			success : function(result) {
				if (result.success) {
					if(settings.multiple) {
						createUploadInput(div, relationType, settings.multiple);
					}
					for (var i = 0; i < result.resultData.length; i++) {
						createDomAndBindEvent(result.resultData[i].path, relationType, result.resultData[i].id, div, settings.readonly);
					}
				}else {
					createUploadInput(div, relationType, settings.multiple);
				}
			}
		});
	}
	
	function disableUploadInput(multiple, relationType) {
		if(!multiple) {
			$('#a_' + relationType).remove();
		}
	}
	
	function createUploadInput(div, relationType, multiple) {
		div.append('<a id="a_'+ relationType +'" class="upload-btn"><input accept="image/*" name="file" type="file" id="file_'+ relationType +'" /></a>')
		var $domEle = $('#file_' + relationType);
		if(multiple == true) {
			$domEle.attr('multiple', 'multiple');
		}
		uploadFile(relationTypeToSettings[relationType], $('#file_' + relationType), relationType);
	}
	function isValid(options) {
		if(options.multiple && options.uploadCount <= 1) {
			throw new Error('参数不正确，如果设置了允许上传多张图片,uploadCount必须大于1');
		}
		if(!options.multiple && options.uploadCount > 1) {
			throw new Error('参数不正确，如果设置了不允许上传多张图片,uploadCount必须等于1');
		}
		return true;
	}
	
	function checkUploadCountLimit(alreayUploadCount, relationType) {
		var limit = alreayUploadCount + 1;
		if(limit > relationTypeToSettings[relationType].uploadCount) {
			return '最多上传' + relationTypeToSettings[relationType].uploadCount +'张图片';
		}
		return '';
	}
	
	function showPreview(file, relationType, id) {
		if (window.FileReader) { // 浏览器支持FileReader
			var fileReader = new FileReader();
			fileReader.onloadend = function(e) {
				var imgDom = '<img id="img_'+ relationType + '_' + id +'" src="'+ e.target.result +'" alt=""/>';
				createUpdatablePreview(imgDom, relationType, id, $('#' + relationTypeToSettings[relationType].divId));
				// 上传成功后，如果设置为只能上传单张图片，删除上传图片按钮
				disableUploadInput(relationTypeToSettings[relationType].multiple, relationType);
			};
			fileReader.readAsDataURL(file);
		}
	}
	
	function createDomAndBindEvent(imgSrc, relationType, id, div, readonly) {
		var imgDom = '<img id="img_'+ relationType + '_' + id +'" src="'+ imgSrc +'" alt=""/>';
		if(readonly) {
			createReadOnlyPreview(imgDom, div);
		}else {
			createUpdatablePreview(imgDom, relationType, id, div);
		}
	}
	
	
	function createUpdatablePreview(imgDom, relationType, id, div) {
		var preview = '<span class="preview">'
			+ imgDom
			+ '<b onclick="close(this, \'c_'+ id +'\')" id="id_'+ id +'">' + '删除图片' + '</b>' + '</span>';
		if(relationTypeToSettings[relationType].multiple && $('#a_' + relationType).length > 0) {
			$('#a_' + relationType).before(preview);
		} else {
			div.append(preview);
		}
		$('#id_' + id).on('click', function() {
			close($(this)[0], id, relationType);
		});
	}
	
	function createReadOnlyPreview(imgDom, div) {
		var preview = '<span class="preview">'
			+ imgDom
			+ '</span>';
		div.append(preview);
	}
	
	function close(obj, id, relationType) {
		$(obj).parent('span').remove();
		if(!relationTypeToSettings[relationType].multiple) {
			createUploadInput($('#' + relationTypeToSettings[relationType].divId), relationType, relationTypeToSettings[relationType].multiple);
		}
		if(uploadImageObj[id]) {
			deleteImageIds.push(uploadImageObj[id]);
			delete uploadImageObj[id];
		} else {
			deleteImageIds.push(id);
		}
	};
	
	
	function validateImage(data, settings) {
		var acceptFileSize = 614400;
		var acceptFileTypes = /(\.|\/)(gif|jpe?g|png|svg|bmp)$/i;
		if(settings.size && settings.size > 0) {
			acceptFileSize = settings.size * 1024;
		}
	    if(data['type'] && !acceptFileTypes.test(data['type'])) {
	    		return '文件类型不支持';
	    }
	    if(data['size'] && data['size'] > acceptFileSize) {
	    		return '超过最大文件限制';
	    }
	    if(!data['size'] || data['size'] < 1000) {
	    		return '上传的文件不能小于1KB';
	    }
	    return '';
	}
})(jQuery);
