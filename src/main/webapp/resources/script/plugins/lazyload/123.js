(function () {
    var b = 0;
    var c = ["webkit", "moz"];
    for (var a = 0; a < c.length && !window.requestAnimationFrame; ++a) {
        window.requestAnimationFrame = window[c[a] + "RequestAnimationFrame"];
        window.cancelAnimationFrame = window[c[a] + "CancelAnimationFrame"] || window[c[a] + "CancelRequestAnimationFrame"]
    }
    if (!window.requestAnimationFrame) {
        window.requestAnimationFrame = function (h, e) {
            var d = new Date().getTime();
            var f = Math.max(0, 16 - (d - b));
            var g = window.setTimeout(function () {
                h(d + f)
            }, f);
            b = d + f;
            return g
        }
    }
    if (!window.cancelAnimationFrame) {
        window.cancelAnimationFrame = function (d) {
            clearTimeout(d)
        }
    }
}());
var soundManager;
window.onload = function () {
    var s = document.querySelector("#gameBody") || document.body;
    var x = [], G = document.querySelector("#game-layer-bg"), F = [], o;
    var V = 0, ao = 0;
    var az, aO;
    var ag = [], aM = 0, ah = false, L = false, aL = true, k = false, aI, I, ab;
    var E = "touchstart" in document.documentElement ? "touchstart" : "click";
    var aF = document.querySelector("#btn-back");
    var J = document.querySelector("#btn-replay");
    var C = document.querySelectorAll(".btn-close-ad");
    var aa = document.querySelector("#btn-prize");
    var v = document.querySelector("#btn-share");
    var aq = document.querySelectorAll(".btn-know");
    var af = document.getElementById("masklayer");
    var ac = document.querySelectorAll(".ads");
    var ap = document.querySelector("#game-start-tap");
    var Q = document.querySelector("#game-end-tap");
    var B = document.querySelector("#game-icon-prize");
    var aQ = document.querySelectorAll(".company-rights");
    var p = document.querySelector("#game-layer-score");
    var m = document.getElementById("game-layer-score-best");
    var aA = document.getElementById("game-layer-score-btn");
    var z = document.querySelector(".register-panel-layer");
    var r = document.querySelector("#panel-got-prize");
    var b = document.querySelector("#panel-without-times");
    var t = document.querySelector("#guide-panel");
    var H = document.querySelector("#loading-panel");
    var d = document.querySelector("#main-box");
    var Y = document.querySelector("#game-panel-ad");
    var l = H.querySelector(".loading-panel-progress");
    var aB = H.querySelector(".loading-panel-progress-current");
    var D = window.config_custom.IMG.CLICK;
    var ae = false;
    var O = false;
    var A = false;
    var w = false;
    var an = 0;
    var aE = 0;
    var aR = window.config_custom.DEBUG || false;
    var ad = window.config_custom.RESTTIMES;
    var at = window.config_custom.TIMETOTAL * 1000;
    var q = new LocalStorageManager();
    var au = new loadingBox(document.body);
    var aH = q.getGuide();
    var aK = [];
    var ax = {
        versions: function () {
            var i = navigator.userAgent;
            return {
                mobile: !!i.match(/AppleWebKit.*Mobile.*/),
                ios: !!i.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),
                android: i.indexOf("Android") > -1 || i.indexOf("Linux") > -1,
            }
        }(),
    };
    soundManager = createjs.Sound;
    if (ax.versions.android) {
        soundManager = SoundManager
    }
    if (window.config_custom.NEEDGUIDE) {
        if (aH == "false") {
            t.classList.remove("show")
        } else {
            t.classList.add("show");
            q.setGuide(false)
        }
    }
    J.addEventListener(E, aD, false);
    if (C.length > 0) {
        for (var T = 0; C[T]; T++) {
            C[T].addEventListener(E, function () {
                for (var aS = 0; ac[aS]; aS++) {
                    ac[aS].classList.remove("show")
                }
                for (var aT = 0; aQ[aT]; aT++) {
                    aQ[aT].classList.add("low");
                    e()
                }
                d.style.webkitTransform = "none";
                p.style.height = window.innerHeight + "px";
                p.classList.remove("modify");
                aP()
            }, false)
        }
        d.style.webkitTransform = "translate3d(0," + (-Y.offsetHeight) + "px,0)"
    } else {
        for (var T = 0; aQ[T]; T++) {
            aQ[T].classList.add("low")
        }
    }
    [af, G, Q, r, b, z, t, H].forEach(function (i) {
        i.ontouchmove = function (j) {
            j.preventDefault()
        }
    });
    t.onclick = function () {
        t.classList.remove("show")
    };
    t.onclick = function () {
        t.classList.remove("show")
    };
    v.onclick = function () {
        var i = af.className;
        if (i == "masklayer") {
            af.className = "masklayer on"
        } else {
            af.className = "masklayer"
        }
    };
    af.onclick = function () {
        af.className = "masklayer"
    };
    for (var T = 0; aq[T]; T++) {
        aq[T].onclick = function () {
            if (k) {
                k = false;
                f();
                return
            }
            r.classList.remove("show");
            b.classList.remove("show");
            z.classList.remove("show")
        }
    }
    var y = [];
    for (var T in window.config_custom.IMG) {
        if (window.config_custom.IMG[T]) {
            if (typeof window.config_custom.IMG[T] === "string") {
                y.push(window.config_custom.IMG[T])
            } else {
                for (var S = 0; window.config_custom.IMG[T][S]; S++) {
                    y.push(window.config_custom.IMG[T][S])
                }
            }
        }
    }
    ar(y, function (i) {
        l.style.width = i + "%";
        aB.textContent = i + "%"
    }, function () {
        H.classList.remove("show");
        var aS = new CSSCreate();
        for (var j = 0; D[j]; j++) {
            aS.add(".t" + (j + 1), "background-image:url(" + D[j] + ");")
        }
        aS.add("#game-layer-bg", "background-image: url(" + window.config_custom.IMG.BG + ");");
        aS.add("#game-layer-score", "background-image: url(" + window.config_custom.IMG.RESULTBG + ");");
        if (window.config_custom.IMG.GUIDEBG) {
            aS.add("#guide-panel", "background-image: url(" + window.config_custom.IMG.GUIDEBG + ");")
        }
        aS.produce();
        aG();
        P(window.config_custom.URL.RESTART, function (i) {
            am()
        })
    });
    function ar(aV, aZ, aS) {
        var aT = aV.length;
        var aU = 0;
        var aX = typeof aZ === "function" ? aZ : null;
        var j = typeof aS === "function" ? aS : null;
        for (var aY = 0; aV[aY]; aY++) {
            var aW = new Image();
            aW.src = aV[aY];
            aW.onload = function () {
                aU++;
                aX && aX(Math.round(aU * 100 / aT));
                if (aU === aT) {
                    setTimeout(function () {
                        j && j()
                    }, 1500)
                }
            }
        }
    }

    function am() {
        az = "webkitTransform";
        aO = "webkitTransitionDuration";
        o = document.querySelector("#game-layer-time");
        x.push(document.querySelector("#game-layer1"));
        x[0].children = x[0].querySelectorAll("div");
        x.push(document.querySelector("#game-layer2"));
        x[1].children = x[1].querySelectorAll("div");
        if (G.ontouchstart === null) {
            G.ontouchstart = av
        } else {
            G.onmousedown = av
        }
        X()
    }

    function U() {
        V = s.offsetWidth / 4;
        bodyHeight = document.documentElement.offsetHeight;
        ao = Math.round(188 * V / 160);
        aE = window.innerHeight % ao;
        ap.style.height = ao + "px";
        ap.style.display = "block";
        aP()
    }

    function X() {
        soundManager.registerSound({src: window.config_custom.PATH.MUSIC + "err.mp3", id: "err"});
        soundManager.registerSound({src: window.config_custom.PATH.MUSIC + "end.mp3", id: "end"});
        soundManager.registerSound({src: window.config_custom.PATH.MUSIC + "tap.mp3", id: "tap"});
        soundManager.registerSound({src: window.config_custom.PATH.MUSIC + "win.mp3", id: "win"});
        Z();
        u()
    }

    function Z() {
        ad--;
        ag = [];
        aM = 0;
        ab = 0;
        ah = false;
        L = false;
        I = 0;
        aL = true;
        A = false;
        ae = false;
        w = false;
        aP();
        Q.classList.remove("show");
        Q.topData = Q.topDataInit;
        Q.style[az] = "translate3d(0," + Q.topData + "px,0)";
        an = Math.floor(Math.random() * D.length);
        o.innerHTML = n(I);
        U();
        aj(x[0]);
        aj(x[1], 1)
    }

    function aP() {
        var i = (Y && Y.classList.contains("show")) ? Y.scrollHeight : 0;
        F[0] = window.innerHeight - ao * 1 - i;
        F[1] = window.innerHeight - ao * 2.5 - i
    }

    var aC = 0;
    var ak = 0;

    function h() {
        aC = Date.now();
        ak = aC;
        L = true;
        M()
    }

    function f() {
        ah = true;
        cancelAnimationFrame(aI);
        ay(window.config_custom.URL.SUBMIT, function (i) {
            G.className = "";
            c(i)
        })
    }

    function g(j) {
        var i = ["p", "a", "s", "n", "b", "x", "z", "q", "j", "m"];
        var aS = "";
        j += "";
        j = j.split("");
        j.forEach(function (aT) {
            aS += i[aT - 0]
        });
        return aS
    }

    function M() {
        if (ah) {
            return
        }
        var i = Date.now();
        I += (i - aC);
        aC = i;
        if (I >= at) {
            o.innerHTML = "时间到！";
            ab = 0;
            f();
            soundManager.play("end");
            return
        } else {
            o.innerHTML = n(I)
        }
        aI = requestAnimationFrame(M)
    }

    function P(j, aT) {
        var aS = {};
        if (aR) {
            var i = {prize: Math.random() > 0.5 ? true : false, status: 1};
            O = i.prize;
            typeof aT === "function" && aT(i)
        } else {
            $.ajax({
                url: j, type: "post", data: aS, success: function (aU) {
                    var aV = JSON.parse(aU);
                    O = aV.prize;
                    typeof aT === "function" && aT(aV)
                }, error: function () {
                    au.hide();
                    N()
                }
            })
        }
    }

    var ai = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    function N() {
        k = true;
        b.querySelector(".plus").innerHTML = "网络繁忙,请重新尝试!";
        b.classList.add("show");
        z.classList.add("show")
    }

    function W(aX) {
        aX += "";
        var aT, aV, j;
        var aW, aU, aS;
        j = aX.length;
        aV = 0;
        aT = "";
        while (aV < j) {
            aW = aX.charCodeAt(aV++) & 255;
            if (aV == j) {
                aT += ai.charAt(aW >> 2);
                aT += ai.charAt((aW & 3) << 4);
                aT += "==";
                break
            }
            aU = aX.charCodeAt(aV++);
            if (aV == j) {
                aT += ai.charAt(aW >> 2);
                aT += ai.charAt(((aW & 3) << 4) | ((aU & 240) >> 4));
                aT += ai.charAt((aU & 15) << 2);
                aT += "=";
                break
            }
            aS = aX.charCodeAt(aV++);
            aT += ai.charAt(aW >> 2);
            aT += ai.charAt(((aW & 3) << 4) | ((aU & 240) >> 4));
            aT += ai.charAt(((aU & 15) << 2) | ((aS & 192) >> 6));
            aT += ai.charAt(aS & 63)
        }
        return aT
    }

    function al(j) {
        var i = Date.now();
        return md5(i) + g(j) + md5(i + Math.random())
    }

    function ay(j, aU) {
        var aT = ("0" + I).substr(-5, 4);
        var aS = {score: al(ab > 0 ? aT : 0), steps_time: aK};
        if (aR) {
            var i = {status: 1, rank: 1, prize: true};
            typeof aU === "function" && aU(i)
        } else {
            au.show();
            $.ajax({
                url: j, type: "post", data: aS, success: function (aV) {
                    au.hide();
                    typeof aU === "function" && aU(JSON.parse(aV))
                }, error: function () {
                    au.hide();
                    N()
                }
            })
        }
    }

    function n(aS, i) {
        var j = (1000000 + aS + "").substr(-5, 4);
        j = j.substr(0, 2) + "." + j.substr(2) + (i ? "" : '"');
        return j
    }

    function aj(aY, aZ, aU) {
        var aW = Math.floor(Math.random() * 1000) % 4 + (aZ ? 0 : 4);
        for (var aV = 0; aV < aY.children.length; aV++) {
            var aS = aY.children[aV], aT = aS.style, a0 = Math.floor(aV / 4);
            aT.left = (aV % 4) * V + "px";
            aT.bottom = a0 * ao + "px";
            aT.width = V + "px";
            aT.height = ao + "px";
            aS.className = "";
            if (aW == aV) {
                if (D.length === 0) {
                    var aX = a0
                } else {
                    var aX = a0 % D.length === 0 ? D.length : a0 % D.length
                }
                if (aX === 1 && !L && !w) {
                    w = true;
                    aS.className = "tile t1 start"
                } else {
                    if (O && !ae && !A && a0 === an) {
                        A = true;
                        aS.className = "tile prize t" + aX
                    } else {
                        aS.className = "tile t" + aX
                    }
                }
                ag.push({cell: aW % 4, id: aS.id});
                aS.notEmpty = true;
                aW = (a0 + 1) * 4 + Math.floor(Math.random() * 1000) % 4
            } else {
                aS.notEmpty = false
            }
        }
        if (aZ) {
            aY.style.webkitTransitionDuration = "0ms";
            aY.style.display = "none";
            aY.y = -ao * (Math.floor(aY.children.length / 4) + (aU || 0)) * aZ;
            setTimeout(function () {
                aY.style[az] = "translate3D(0," + aY.y + "px,0)";
                setTimeout(function () {
                    aY.style.display = "block"
                }, 100)
            }, 200)
        } else {
            aY.y = 0;
            aY.style[az] = "translate3D(0," + aY.y + "px,0)"
        }
        aY.style[aO] = "150ms"
    }

    function aN() {
        var j = Math.floor(window.innerHeight / ao);
        if (ab >= window.config_custom.NUM - j) {
            Q.topData += ao;
            Q.classList.add("show");
            Q.style[az] = "translate3D(0," + Q.topData + "px,0)"
        }
        for (var aS = 0; aS < x.length; aS++) {
            var aT = x[aS];
            aT.y += ao;
            if (aT.y > ao * (Math.floor(aT.children.length / 4))) {
                aj(aT, 1, -1)
            } else {
                aT.style[az] = "translate3D(0," + aT.y + "px,0)"
            }
        }
    }

    function av(aT) {
        if (ah) {
            return false
        }
        if (ab >= window.config_custom.NUM) {
            f();
            return
        }
        var j = aT.target;
        var aV = aT.clientY || aT.targetTouches[0].clientY, i = (aT.clientX || aT.targetTouches[0].clientX) - s.offsetLeft, aS = ag[aM];
        if (aV > F[0] || aV < F[1]) {
            return false
        }
        var aU = document.querySelector("#" + aS.id);
        if ((aS.id == j.id && aU.notEmpty) || (aS.cell == 0 && i < V) || (aS.cell == 1 && i > V && i < 2 * V) || (aS.cell == 2 && i > 2 * V && i < 3 * V) || (aS.cell == 3 && i > 3 * V)) {
            var aU = document.querySelector("#" + aS.id);
            aU.classList.add("clicked");
            if (!L) {
                ap.style.display = "none";
                h();
                aK = []
            }
            if (Date.now() - ak - 1000 > I) {
                M()
            }
            aK.push(Date.now());
            soundManager.play("tap");
            if (aU.classList.contains("prize")) {
                R(j)
            }
            aM++;
            ab++;
            if (ab >= window.config_custom.NUM) {
                f()
            } else {
                aN()
            }
        } else {
            if (L && !j.notEmpty) {
                soundManager.play("err");
                ab = 0;
                f();
                j.className += " bad"
            }
        }
        return false
    }

    function R(i) {
        ae = true;
        aa.classList.add("show");
        B.style.webkitTransform = "translate3d(" + (i.offsetLeft + i.offsetWidth / 2) + "px," + (window.innerHeight - i.offsetHeight) + "px,0)";
        B.classList.add("anim");
        B.addEventListener("webkitTransitionEnd", function () {
            B.classList.remove("anim");
            aa.classList.add("anim");
            setTimeout(function () {
                if (!ah) {
                    aa.classList.remove("show");
                    aa.classList.remove("anim")
                }
            }, 2000)
        }, false);
        setTimeout(function () {
            B.style.webkitTransform = "translate3d(" + aa.offsetLeft + "px," + aa.offsetTop + "px,0) scale(0.5)"
        }, 20)
    }

    function aG() {
        for (var aV = 1; aV <= 2; aV++) {
            var aU = document.querySelector("#game-layer" + aV);
            for (var aT = 0; aT < 10; aT++) {
                for (var aS = 0; aS < 4; aS++) {
                    var aW = document.createElement("div");
                    aW.id = "game-layer" + aV + "-" + (aS + aT * 4);
                    aW.setAttribute("num", (aS + aT * 4));
                    aU.appendChild(aW)
                }
            }
        }
    }

    function u() {
        var aT = Math.floor(window.innerHeight / ao);
        var aU = Math.ceil(window.innerHeight / ao);
        for (var aS = 0; aS < aT; aS++) {
            for (var i = 0; i < 4; i++) {
                var aV = document.createElement("span");
                aV.style.height = ao + "px";
                Q.appendChild(aV)
            }
        }
        Q.topData = -aU * ao;
        Q.topDataInit = -aU * ao;
        Q.style["top"] = -(ao - aE) + "px";
        Q.style[az] = "translate3D(0," + Q.topData + "px,0)";
        Q.style[aO] = "150ms"
    }

    function c(j) {
        window.config_custom.RESULT.SCORE = ab;
        window.config_custom.RESULT.TIME = n(I, true);
        if (ae && ab > 0) {
            r.classList.add("show");
            z.classList.add("show");
            soundManager.play("win")
        }
        d.style.webkitTransform = "none";
        aa.classList.add("show");
        aa.classList.add("anim");
        G.style.display = "none";
        document.getElementById("game-layer-score-text").className = ab > 0 ? "success" : "fail";
        document.getElementById("game-layer-score-score").innerHTML = ab > 0 ? n(I) : "失败了!";
        document.getElementById("game-layer-score-rank").innerHTML = (j && j.rank) + "&nbsp;&nbsp;";
        if (window.config_custom.NUMLIMIT) {
            document.getElementById("game-layer-score-times").innerHTML = "剩余次数:<b>" + ad + "</b>"
        } else {
            document.getElementById("game-layer-score-times").innerHTML = ""
        }
        var i = j.score + "0" - 0;
        if ((!i || i > I) && ab > 0) {
            m.innerHTML = '<span class="icon-star"></span>新纪录'
        } else {
            m.innerHTML = '<span class="icon-star"></span>最佳 ' + n(i)
        }
        p.style.display = "block";
        e()
    }

    function aJ() {
        if (Y) {
            Y.style.top = "auto"
        }
        aa.classList.remove("show");
        aa.classList.remove("anim");
        p.style.display = "none";
        p.classList.remove("success");
        p.classList.remove("fail");
        G.style.display = "block";
        if (ac.length > 0) {
            d.style.webkitTransform = "translate3d(0," + (-Y.offsetHeight) + "px,0)"
        }
    }

    function aD() {
        if (!aL) {
            aL = false;
            return
        }
        P(window.config_custom.URL.RESTART, function (i) {
            if (aw()) {
                return
            }
            Z();
            aJ()
        })
    }

    function aw() {
        if (ad <= 0 || window.config_custom.TOTALRESTTIMES <= 0) {
            if (window.config_custom.TOTALRESTTIMES <= 0) {
                b.querySelector(".plus").innerHTML = "你的游戏机会已经用完<br/>期待下次活动开启!"
            } else {
                b.querySelector(".plus").innerHTML = "你的游戏机会已经用完<br/>明天再来吧!"
            }
            b.classList.add("show");
            z.classList.add("show");
            return true
        }
        return false
    }

    function K() {
        P(window.config_custom.URL.RESTART, function (i) {
            if (aw()) {
                return
            }
            Z();
            aJ()
        })
    }

    function a(i) {
        if (typeof i == "object") {
            return JSON.stringify(i)
        } else {
            return i
        }
        return ""
    }

    function e() {
        var j = 440;
        var aS = document.body.clientHeight;
        if (Y && Y.classList.contains("show")) {
            var i = window.innerHeight - Y.offsetHeight;
            if (i > 440) {
                j = i
            } else {
                if (window.innerHeight < j) {
                    j = window.innerHeight
                }
            }
            p.classList.add("modify");
            p.style.height = j + "px";
            Y.style.top = j + "px"
        } else {
            p.style.height = window.innerHeight + "px"
        }
    }
};
window.onunload = function () {
    soundManager && soundManager.removeAllSounds()
};