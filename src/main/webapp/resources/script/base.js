// 复选框
$(document).on('click', '.checkbox label', function (e) {
    e.preventDefault ? e.preventDefault() : (e.returnValue = false);

    var inputChecked = $(this).find("input"), param = {event : e};

    if ($(this).hasClass('checked')) {
        $(this).removeClass('checked');
        inputChecked.prop('checked', false);
        param.checked = false;
    } else {
        $(this).addClass('checked');
        inputChecked.prop('checked', true);
        param.checked = true;
    }

    // 自定义方法
    (window.labelCallback || $.noop)($(this), param);

});

// 全选
$(document).on('click', '.checked-all', function (e) {
    e.preventDefault ? e.preventDefault() : (e.returnValue = false);

    var checkedGroups = $(this).closest('.checkbox');

    checkedGroups.find("label").addClass('checked');
    checkedGroups.find("input").prop('checked', true);
});

// 取消
$(document).on('click', '.checked-false', function (e) {
    e.preventDefault ? e.preventDefault() : (e.returnValue = false);
    var checkedGroups = $(this).closest('.checkbox');

    checkedGroups.find("label").removeClass('checked');
    checkedGroups.find("input").prop('checked', false);
});

// 单选框
$(document).on('click', '.radio label', function (e) {
    e.preventDefault ? e.preventDefault() : (e.returnValue = false);

    var inputRadio = $(this).find("input");

    $(this).addClass('checked').siblings('label').removeClass('checked');
    inputRadio.prop('checked', true);
});

// 选项卡
$(document).on('click', '.tab-nav a', function (e) {
    e.preventDefault ? e.preventDefault() : (e.returnValue = false);
    var tabContainer = $(this).closest('.tab-container');
    var index = $(this).index();
    $(this).addClass('on').siblings('a').removeClass('on');
    tabContainer.find('.tab').eq(index).addClass('on').siblings('.tab').removeClass('on');
});

// alert 弹出框
function zcal(settings, callback) {
    var defaultSetting = {
        type: null,
        title: '',
        text: '',
        time: null,
        showCloseButton: true,
        continueBtnCancel: '确定',
        continueBtnSure: '继续添加'
    };
    $.extend(defaultSetting, settings);

    // 向网页中添加节点
    $('body').append(
        '<div class="alert">' +
        '<div class="alert-content">' +
        '<div class="icon al-info"></div> ' +
        '<div class="icon al-success">' +
        '<span class="al-line al-tip"></span>' +
        '<span class="al-line al-long"></span>' +
        '<div class="al-placeholder"></div>' +
        '<div class="al-fix"></div></div>' +
        '<div class="icon al-warning"></div>' +
        '<div class="icon al-error"><span></span></div>' +
        '<h2 class="title"></h2>' +
        '<p class="caption"></p>' +
        '<a class="btn btn-info alert-close">关闭</a>' +
        '<div class="confirm-btn text-center"><a class="btn btn-danger alert-sure">确定</a><a class="btn btn-default alert-cancel">取消</a></div>' +
        '<div class="continue-btn text-center"><a class="btn btn-danger continue-cancel"></a><a class="btn btn-info continue-sure"></a></div>' +
        '</div>' +
        '</div>');

    // 改变文本
    $('.alert .title').text(defaultSetting.title);
    $('.alert .caption').text(defaultSetting.text);

    // 控制出现的弹出框是哪种类型
    if (defaultSetting.type != null) {
        $('.alert').fadeIn().addClass(defaultSetting.type);
    } else {
        $('.alert').fadeIn();
    }

    // 继续操作
    if (defaultSetting.type == 'continue') {
        $('.continue-cancel').text(defaultSetting.continueBtnCancel);
        $('.continue-sure').text(defaultSetting.continueBtnSure);
    }

    // 是否显示关闭按钮
    if (defaultSetting.showCloseButton === false) {
        $('.alert .alert-close, .continue-cancel').css('display', 'none');
    }

    // 控制入场和出场动画
    $('.alert-content').removeClass('hide-alert').addClass('show-alert');

    // 关闭alert 移除节点
    function closeAlert() {
        $('.alert-content').removeClass('show-alert').addClass('hide-alert');
        $('.alert').fadeOut();
        setTimeout(function () {
            $('.alert').remove();
        }, 400);
    }

    // 手动控制关闭alert
    $('.alert-close,.alert-cancel,.continue-cancel').click(function (e) {
        e.preventDefault ? e.preventDefault() : (e.returnValue = false);
        closeAlert();
    });

    // 控制confirm
    $('.alert-sure').click(function (e) {
        e.preventDefault ? e.preventDefault() : (e.returnValue = false);
        closeAlert();
        setTimeout(function () {
            callback();
        }, 400);
    });

    // 是否自动消失
    if (defaultSetting.time != null) {
        setTimeout(function () {
            closeAlert();
        }, defaultSetting.time);
    }
    else {
        return false;
    }
}

// 返回顶部
function scrollTop(settings) {
    var defaultSettings = {
        name: null,
        distance: null,
        time: 0,
        customTop: 0,
        windowScroll: true
    }
    $.extend(defaultSettings, settings);

    // 滚动的距离
    var scrollTop;
    if (defaultSettings.distance != null) {
        scrollTop = $(defaultSettings.distance).offset().top - defaultSettings.customTop;
    }
    else {
        scrollTop = 0;
    }

    // 是否显示返回顶部按钮
    if (defaultSettings.windowScroll === true) {
        $(window).scroll(function () {
            if ($(document).scrollTop() > 100) {
                $(defaultSettings.name).fadeIn();
            } else {
                $(defaultSettings.name).fadeOut();
            }
        });
    }

    // 自动滚动还是点击滚动
    if (defaultSettings.name === null) {
        $("html, body").animate({scrollTop: scrollTop}, defaultSettings.time);
    }
    else {
        $(defaultSettings.name).click(function (e) {
            e.preventDefault ? e.preventDefault() : (e.returnValue = false);
            $("html, body").animate({scrollTop: scrollTop}, defaultSettings.time);
        })
    }
}

// modal
function modalOpen(data) {
    $(data).fadeIn(300).removeClass('modal-close').addClass('modal-open');
}
function modalClose(data) {
    $(data).fadeOut(300).removeClass('modal-open').addClass('modal-close')
}
$('.open-modal').click(function (e) {
    e.preventDefault ? e.preventDefault() : (e.returnValue = false);
    var data = $(this).data('target');
    modalOpen(data);
});
$('.modal-close').click(function (e) {
    e.preventDefault ? e.preventDefault() : (e.returnValue = false);
    var data = $(this).closest('.modal');
    modalClose(data);
});

