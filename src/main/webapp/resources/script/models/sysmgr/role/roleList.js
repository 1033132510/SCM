$(function () {
    var gridObj = utils.initGrid('roles', 'sysmgr/role/page');


    $('#search').click(function (e) {
        e.preventDefault();
        var searchParams = {
            searchParam: $('#searchParam').val(),
            roleType: $('#roleType').val()
        }
        gridObj.search(searchParams);
    });


    // 删除单条记录事件
    $(".delete").click(function () {
        var href = $(this).attr("href");
        $("#deleteForm").attr("action", href).submit();
        return false;
    });

    /**
     * 回车搜索事件
     */
    $('#searchParam').bind('keypress',function(event){
        if(event.keyCode == "13")
        {
            $('#search').trigger("click");
        }
    });
});

/**
 * 解析角色的类型
 * @param record
 * @returns {string}
 */
var formatterRoleType = function (record) {
    if (record.roleType == '0') {
        return '系统角色';
    } else {
        return '流程角色';
    }
}
/**
 * 删除操作
 * @param roleId
 */
var deleteRole = function (roleId) {
    zcal({
        type:'confirm',
        title:'确定删除该角色吗？'
    }, function(){
        $.ajax({
            url: ctx + '/sysmgr/role/remove/' + roleId,
            type: 'put',
            async: false,
            dataType: 'json',
            async: false,
            success: function (data) {
                if (data) {
                    zcal({
                        type: 'success',
                        title: '删除成功'
                    });
                    setTimeout(function(){
                        location.reload();
                    }, 1000);
                } else {
                    zcal({
                        type : 'error',
                        text : '有用户关联了该角色，无法删除，请先解除关系再删除'
                    });
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                zcal({
                    type : 'error',
                    text : '服务器系统异常',
                    time : 3000
                })
            }
        });
    })
};

/**
 * 定义列表操作
 * @param record
 * @returns {string}
 * record, rowIndex, colIndex, options
 */
var operation = function (record, rowIndex) {
    var modify = '<a class="red" href="info/' + record.id + '">修改</a>';
    var remove = '<a class="delete red" onclick="deleteRole(\'' + record.id + '\')"' + 'href="javascript:void(0);">删除</a>';
    //var authorizeUser  = '<a class="delete red" onclick="authorizeUser(\'' + record.id + '\')"' + 'href="javascript:void(0);">用户授权</a>';
    var authorizeUser = '<a class="red" href="authorizeUsers/' + record.id + '">用户授权</a>';

    return modify + '&nbsp;&nbsp;&nbsp;' + remove + '&nbsp;&nbsp;&nbsp;' + authorizeUser;
};



