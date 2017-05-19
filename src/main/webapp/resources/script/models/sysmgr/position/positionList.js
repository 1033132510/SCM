$(function () {
    var gridObj = utils.initGrid('positions', 'sysmgr/position/page');


    /**
     * 搜索操作
     */
    $('#search').click(function (e) {
        e.preventDefault();
        var searchParams = {
            searchParam: $('#searchParam').val()
        };
        gridObj.search(searchParams);
    });


    /**
     * 删除单条记录
     */
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
 * 删除操作
 * @param positionId
 */
var deletePosition = function (positionId) {
    zcal({
        type: 'confirm',
        title: '确定删除该岗位吗？'
    }, function () {
        $.ajax({
            url: ctx + '/sysmgr/position/remove/' + positionId,
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
                    location.reload();
                } else {
                    zcal({
                        type : 'error',
                        title : '有用户关联了该岗位，无法删除'
                    });
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert('服务器系统异常');
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
    // <a class="delete red" onclick="deletePosition('em')" href="javascript:void(0);">删除</a>
    var remove = '<a class="delete red" onclick="deletePosition(\'' + record.id + '\')"' + 'href="javascript:void(0);">删除</a>';
    return modify + '&nbsp;&nbsp;&nbsp;' + remove;
};





