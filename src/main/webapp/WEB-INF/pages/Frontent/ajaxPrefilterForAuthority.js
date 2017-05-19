$(function() {
	$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
		jqXHR.setRequestHeader("ajax", "true");
	});

	$(document).ajaxComplete(function(evt, request, settings) {
		console.log(request);
		var result = request.responseJSON.success;
		var exceptionCode = request.responseJSON.exceptionCode;
		if (!(result === undefined) && !result) {
			if (!(exceptionCode === undefined) && exceptionCode == ERROR_CODE.sessionInvalid) {
				zcal({
					type: 'continue',
					title: '重新登录',
					continueBtnSure: '重新登录'
				});

				$('.continue-sure').on('click', function(e) {
					e.preventDefault();
					window.location.href = ctx + '/sysmgr/login';
				});
			}
		}
	});

})
