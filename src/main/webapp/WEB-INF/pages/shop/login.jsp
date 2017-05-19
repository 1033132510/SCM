<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ page import="com.zzc.modules.sysmgr.enums.AccountTypeEnum" %>
<%String accountType = AccountTypeEnum.purcharser.getCode();%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
    <meta name="description" content=""/>
    <meta name="author" content="zhanjun"/>
    <title>
        中直采供应链 － 建筑材料电子商务第一平台
    </title>
    <link rel="stylesheet" href="${ctx}/resources/script/plugins/videoJs/video-js.css"/>
    <link rel="stylesheet" href="${ctx}/resources/content/shop/site.css"/>
    <script src="${ctx}/resources/script/jquery-1.11.0.min.js"></script>
    <script src="${ctx}/resources/script/plugins/videoJs/video.js"></script>
    <script src="${ctx}/resources/script/ie.js"></script>
    <script type="text/javascript">
        function refreshCaptcha() {
            document.getElementById("imgCaptcha").src = "${ctx}/images/kaptcha.jpg?t=" + Math.random();
            videojs.options.flash.swf = "${ctx}/resources/script/plugins/videoJs/video-js.swf";
        }
    </script>
</head>
<body onload="initFormValue()">
<div class="login">
    <div class="video">
        <video id="example_video_1" class="video-js vjs-default-skin" controls preload="none" data-setup='{"example_option":true}'
               poster="${ctx}/resources/content/images/shop/login-bg1.jpg">
            <source src="${ctx}/resources/script/plugins/videoJs/video.mp4" type='video/mp4'/>
        </video>
    </div>
    <div class="bg"></div>
    <form commandName="user" method="post">
        <h2>用户登录</h2>

        <div class="login-group">
            <label class="login-username">
                <input type="text" id="userName" placeholder="手机号进行登录"/>
            </label>
            <label class="login-password">
                <input type="password" id="userPwd" placeholder="请输入密码"/>
            </label>
            <label id="captchaContent" class="login-code">
                <input type="text" id="captcha" placeholder="请输入验证码"/>

                <div class="code-img">
                    <img id="imgCaptcha" src="${ctx}/images/kaptcha.jpg" onclick="javascript:refreshCaptcha();"/>
                    <a onclick="javascript:refreshCaptcha();">点击图片刷新验证码</a>
                </div>
            </label>
        </div>
        <div class="error-tips" id="errorMsg">
            <%--${message} <span class="error">${invalidCaptcha}</span>--%>
        </div>
        <a onclick="login()" href="javascript:void 0" value="登录" id="loginBtn" class="btn btn-info lg-btn">登录</a>

        <div class="lg-tips clearfix">
            <a href="${ctx}/shop/purchaser/toAddPurchaser" onclick="javascript:void 0" class="pull-left">还不是中直采会员？立即登记</a>
            <a href="${ctx}/account/password/reset?accountType=3" class="pull-right">忘记密码？ </a>
        </div>
    </form>
</div>
<script>
    function detectOS() {
        var isWin = (navigator.platform == "Win32") || (navigator.platform == "Windows");
        var isMac = (navigator.platform == "Mac68K") || (navigator.platform == "MacPPC") || (navigator.platform == "Macintosh") || (navigator.platform == "MacIntel");
        if (isMac) {
            $('html').addClass('mac');
        } else if (isWin) {
            $('html').addClass('win');
        }
    }
    detectOS();

    // placeholder text color
    $('input[placeholder]').css('color', '#666');
    $('input[placeholder]').focus(function () {
        $(this).css({
            'color': '#2a2e23'
        });
    }).blur(function () {
        if ($(this).val() == '') {
            $(this).css({
                'color': '#666'
            });
        }
    });

    var retryAmount = 0;
    var browser = {
        versions: function () {
            var u = navigator.userAgent,
                    app = navigator.appVersion;
            return {
                trident: u.indexOf('Trident') > -1, // IE内核
                presto: u.indexOf('Presto') > -1, // opera内核
                webKit: u.indexOf('AppleWebKit') > -1, // 苹果、谷歌内核
                gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, // 火狐内核
                mobile: !!u.match(/AppleWebKit.*Mobile.*/), // 是否为移动终端
                ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), // ios终端
                android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, // android终端或者uc浏览器
                iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, // 是否为iPhone或者QQHD浏览器
                iPad: u.indexOf('iPad') > -1, // 是否iPad
                webApp: u.indexOf('Safari') == -1 // 是否web应该程序，没有头部与底部
            };
        }(),
        language: (navigator.browserLanguage || navigator.language).toLowerCase()
    }

    $(function () {
        if (browser.versions.mobile) {
            var myPlayer = videojs('example_video_1');
            var scaleYX = 1920 / 1080;
            var imgHeight = $(window).height();
            var imgWidth = scaleYX * imgHeight;
            $('#example_video_1').css({
                'width': imgWidth,
                'height': imgHeight,
                'left': '50%',
                'margin-left': -imgWidth / 2
            });
        } else {
            var myPlayer = videojs('example_video_1');
            var scaleXY = 1080 / 1920;
            var imgWidth = $(window).width();
            var imgHeight = scaleXY * imgWidth;
            $('#example_video_1').css({
                'width': imgWidth,
                'height': imgHeight
            });
        }
        setTimeout(function () {
            $('.login').addClass('on');
        }, 200);
    });

    var login = function () {
        var user = {};
        user.userName = $('#userName').val();
        user.userPwd = $('#userPwd').val();
        user.captcha = $('#captcha').val();
        user.retryAmount = retryAmount;
        user.accountType = '<%=accountType %>';

        $.ajax({
            url: ctx + '/shop/login',
            contentType: 'application/json;charset=UTF-8',
            type: 'POST',
            data: JSON.stringify(user),
            dataType: 'json',
            success: function (result) {
                if (result.success) {
                    // 登录成功后的处理
                    $('.login').addClass('off');
                    setTimeout(function () {
                        videojs("example_video_1").ready(function () {
                            var myPlayer = this;
                            myPlayer.play();
                        });
                    }, 400);
                    setTimeout(function () {
                        window.location.href = ctx + '/shop';
                    }, 2000);

                } else {
                    $('#errorMsg').html(result.description);
                    retryAmount++;
                    if (retryAmount == 3) {
                        $('#captchaContent').addClass('on');
                    }
                }
            },
            error: function () {
                $('#errorMsg').html('系统异常');
            }
        });
    };

    $('.lg-btn, #userPwd, #captcha').keydown(function (event) {
        event = document.all ? window.event : event;
        if((event.keyCode || event.which) == 13) {
            event.stopPropagation();
            login();
        }
    });

    /** 重置登入值 */
    var initFormValue = function () {
        $('#userName').val('');
        $('#captcha').val('');
    }
</script>
</body>
</html>