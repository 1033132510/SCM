/**
 * 处理错误
 * @param result
 */
var handleError = function (result, handlerErrorFuncCallBack) {
    var errorCode = result.exceptionCode;
    if (errorCode == ERROR_CODE.systemError) {
        zcal({
            type: 'error',
            title: '服务器异常'
        });
    } else if (errorCode == ERROR_CODE.businessError) {
        console.log('business error');
        zcal({
            type: 'error',
            title: result.description
        });
    } else if (errorCode == ERROR_CODE.validateError) {
        console.log('validate error');
        var errorMsgs = eval("(" + result.description + ")");
        for (var key in errorMsgs) {
            $('#' + key).addClass('error');
            $('#' + key).after('<label class="error" for="' + key + '">' + errorMsgs[key] + '</label>')
        }
    } else {
        console.log('其它');
    }

    // 错误处理的回调
    if (handlerErrorFuncCallBack && typeof(handlerErrorFuncCallBack) === 'function') {
        handlerErrorFuncCallBack();
    }
};

/**
 * form提交的后续处理
 * @param result  提交结果集
 * @param continueBtmMsg 继续处理按钮的显示内容
 * @param continueUrl 继续处理的url
 */
var handleFormSubmitResultForContinue = function (result, continueBtmMsg, continueFuncCallBack) {
    var entityId = $('#entityId').val();
    var titleMsg = '';
    if (entityId === undefined || entityId.length == 0) {
        titleMsg = '添加成功';
    } else {
        titleMsg = '修改成功';
    }
    zcal({
        type: 'continue',
        title: titleMsg,
        continueBtnSure: continueBtmMsg
    });
    $('.continue-sure').on('click', function (e) {
        e.preventDefault();
        if (continueFuncCallBack && typeof(continueFuncCallBack) === 'function') {
            continueFuncCallBack(result);
        }
    });
    // 设置实体Id值
    var entityId = $('#entityId');
    if (!(entityId === undefined) && !(entityId.val() === undefined) && entityId.val().length == 0) {
        entityId.val(result.resultData.id);
    }
    //$('#entityId').val(result.resultData.id);
};


/**
 * 生成java对象{}
 * @returns {{}}
 */
$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};


$.fn.escapeHTML = function () {
    return this.replace(/&/g, '&').replace(/>/g, '>').replace(/</g, '<').replace(/"/g, '"');
}