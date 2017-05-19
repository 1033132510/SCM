/**
 * Created by chenjiahai on 16/1/12.
 */


$.ajaxSetup({
    cache: false,
    error: function () {
        zcal({
            type: 'warning',
            title: '错误提示',
            text: '服务器异常'
        });
    }
})