layui.config({
    base: "/assets/plugins/lib/",
}).use(['form', 'element', 'layer', 'jquery'], function () {
    var $ = layui.$,
        layer = layui.layer;
    //切换导航栏按钮点击事件
    $("#switchNav").click(function () {
        switchNav();
    });

    //折叠显示导航栏
    function switchNav() {
        var sideNavExpand = $('body').hasClass('nav-mini');
        if (!sideNavExpand) {
            $('body').addClass('nav-mini');
        } else {
            $('body').removeClass('nav-mini');
        }
        $('.nav-mini .layui-side .layui-nav .layui-nav-item').hover(function () {
            var that = $(this),
                text = that.children("a:eq(0)").find("cite").text();
            if ($('body').hasClass('nav-mini') && document.body.clientWidth > 750) {
                layer.tips(text, this);
            }
        }, function () {
            layer.closeAll('tips');
        });
    }

    $(".navBar").on("click", function () {
        if ($('body').hasClass('nav-mini')) {
            $('body').removeClass('nav-mini');
        }
    });

    $('*[lay-tips]').on('mouseenter', function () {
        var content = $(this).attr('lay-tips');
        this.index = layer.tips(content, this, {
            tips: [1, '#3A3D49']
        });
    }).on('mouseleave', function () {
        layer.close(this.index);
    });

    $("#clearCache").on('mouseenter', function () {
        var content = $(this).attr('lay-tips');
        this.index = layer.tips(content, this, {
            time: -1, tips: [1, '#3A3D49']
        });
    }).on('mouseleave', function () {
        layer.close(this.index);
    }).on("click", function () {
        window.sessionStorage.removeItem("showNotice");
        layer.msg("缓存清除成功！");
    });


    function hanlder() {
        var requestPath = window.location.pathname;
        var item = $("[href='" + requestPath + "']");
        if (item != null && item.length < 1) {
            requestPath = requestPath.substr(0, requestPath.lastIndexOf("\/")) + "/index";
            item = $("[href='" + requestPath + "']");
        }
        item.parent().parent().find("dd").removeClass("layui-this");
        item.parent().addClass("layui-this");
    }

    hanlder();

    $("*[data-url]").on("click", function () {
        var index = layui.layer.load(2);
        var url = $(this).attr("data-url");
        $("#_content").attr("src", url);
        layui.layer.close(index);
    });

    $("li[name='_layui-nav-item']").on("click", function () {
        $("li[name='_layui-nav-item']").removeClass("layui-nav-itemed");
        $(this).addClass("layui-nav-itemed");
    });

    $("#btnFull").on('click', function (event) {
        event.preventDefault();
        let _that=$(this).find("i");
        console.log(_that);
        if (document.fullscreenElement) {
            document.exitFullscreen()
                .then(_that.removeClass("layui-icon-screen-restore").addClass("layui-icon-screen-full"));
        } else {
            document.documentElement.requestFullscreen()
                .then(_that.removeClass("layui-icon-screen-full").addClass("layui-icon-screen-restore"));
        }
    });
});