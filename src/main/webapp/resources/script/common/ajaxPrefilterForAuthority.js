$.ajaxSetup({
	cache : false
});
$(function() {
	$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
		jqXHR.setRequestHeader("ajax", "true");
	});

	$(document).ajaxComplete(function(evt, request, settings) {
		try {
			var responseText = eval("(" + request.responseText + ")");
			var result = responseText.success;
			var exceptionCode = responseText.exceptionCode;
			if (!(result === undefined) && !result) {
				if (!(exceptionCode === undefined) && exceptionCode == ERROR_CODE.sessionInvalid) {
					zcal({
						type: 'continue',
						title: '由于您长时间未操作，请重新登录',
						continueBtnSure: '确定',
						showCloseButton: false
					});

					$('.continue-sure').on('click', function (e) {
						e.preventDefault();
						var currentUrl = window.location.href;
						if (currentUrl.indexOf('/shop') > -1) {
							window.location.href = ctx + '/shop/login';
						} else if (currentUrl.indexOf('/supply') > -1) {
							window.location.href = ctx + '/supply/login';
						} else {
							window.location.href = ctx + '/sysmgr/login';
						}
					});
				}
			}
		} catch (e) {

		}
	});
})
