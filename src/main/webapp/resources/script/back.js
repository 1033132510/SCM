$(function () {
// 菜单展开收起控制
    $('nav .slide-nav > li').click(function () {
        if ($(this).hasClass('active')) {
            $(this).removeClass('active').find('ul').slideUp();
        }
        else {
            $(this).find('ul').slideDown();
            $(this).addClass('active').siblings('li').removeClass('active').find('ul').slideUp();
            $(".scroll-v").mCustomScrollbar("update");
        }
    });
    $('.pack-up').click(function () {
        $('body').toggleClass('nav-close');
        $(".scroll-h").mCustomScrollbar("update");
        if ($('body').hasClass('nav-close')) {
            $('.pack-up i').text('展开');
        }
        else {
            $('.pack-up i').text('收起');
        }
    });

// scroll
    if ($(window).width() < 1367) {
        $('.scroll-h').mCustomScrollbar({
            scrollInertia: 400,
            horizontalScroll: true,
            advanced: {
                updateOnContentResize:true
            }
        });
    }
    $('.scroll-v').mCustomScrollbar({
        scrollInertia: 400,
        horizontalScroll: false,
        update: true,
        advanced: {
            autoExpandHorizontalScroll: true,
            updateOnContentResize:!0
        }
    });

// slect
    $('.form-select .form-control').select2({
        placeholder: "--请选择--",
        minimumResultsForSearch: -1
    });
});


var menu = function () {
}
$(function () {
    var menuId = menu.getCookie('menu');
    if (menuId) {
        var $menu = $('#' + menuId);
        var menuValue = $menu.attr('value');
        if (menuValue != undefined) {
            $menu.closest('li').addClass('active').siblings('li').removeClass('active');
            $menu.closest('li').parents('ul').closest('li').addClass('active').siblings('active').removeClass('active');
            $menu.closest('li').parents('ul').css('display', 'block');
        }
        else {
            $menu.addClass('active').children('ul').css('display', 'block')
        }
    }
})

//写cookies
menu.setCookie = function (name, value) {
    document.cookie = name + '=' + value + ';path=/';
}

//读取cookies
menu.getCookie = function (name) {
    var cookies = document.cookie;
    var cookiesArr = cookies.split(';');
    for (var i = 0; i < cookiesArr.length; i++) {
        var nameAndValue = cookiesArr[i].split('=');
        if (name == $.trim(nameAndValue[0]))
            return nameAndValue[1];
    }
    return null;
}

//删除cookies
menu.delCookie = function (name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1000);
    var cval = menu.getCookie(name);
    if (cval != null)
        document.cookie = name + '=' + cval + ';expires=' + exp.toGMTString() + ';path=/';
}

menu.rememberMenu = function (event, menuDom) {
    if (event.stopPropagation) {
        event.stopPropagation();
    }
    else if (window.event) {
        window.event.cancelBubble = true;
    }
    var $menu = $(menuDom);
    var menuId = $menu.attr('id');

    menu.delCookie('menu');
    menu.setCookie('menu', menuId);
}
