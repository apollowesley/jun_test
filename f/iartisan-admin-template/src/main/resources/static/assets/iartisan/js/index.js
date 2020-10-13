var $, tab, menus, layer, form, element;
layui.config({
    base: "/assets/iartisan/plugins/lib/"
}).use(['form', 'element', 'layer', 'jquery', 'bodyTab', 'router'], function () {
     $ = layui.$, element = layui.element,
        layer = parent.layer === undefined ? layui.layer : top.layer;
    tab = layui.bodyTab({
        openTabNum: "10",  //最大可打开窗口数量
        url: "/getMenus" //获取菜单json地址
    }),
        router = layui.router;
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

    //加载左侧菜单

    function getLeftMenus() {
        $.get(tab.tabConfig.url, function (data) {
            menus = data.data;
            tab.render();
        })
    }

    getLeftMenus();

    // 添加新窗口
    $("body").on("click", ".layui-nav .layui-nav-item a:not('.mobileTopLevelMenus .layui-nav-item a')", function () {
        //如果不存在子级
        if ($(this).siblings().length == 0) {
            addTab($(this));
            $('body').removeClass('site-mobile');  //移动端点击菜单关闭菜单层
        }
        $(this).parent("li").siblings().removeClass("layui-nav-itemed");
    })

    $("#donation").on("click", function () {
        parent.layer.msg("暂不需要捐助", {shade: 0.5});
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
        layer.msg("缓存清除成功！", {shade: 0.5});
    });

    let urls = {
        getUserDetail: "/userSupport/queryDetailPage",
        getUnreadCount: "/reminder/getUnreadCount",
        toSysMsg: "/reminder/index"
    };
    $("#ziliao").click(function () {
        var userId = $(this).attr("attr-data");
        router.ajaxGet(urls.getUserDetail, {"userId": userId}, {async: false}, function (res) {
            top.layer.open({
                type: 1,
                title: false, //不显示标题栏,
                shade: [0.5, '#393D49'],
                area: '40%',
                closeBtn: 2,
                id: '_ziliao', //设定一个id，防止重复弹出
                btn: ['关闭'],
                btnAlign: 'c',
                moveType: 1, //拖拽模式，0或者1
                content:"<div style=\"padding: 10px; line-height: 20px; background-color: #393D49; color: #fff; font-weight: 300;\">" +res+"</div>"
            });
        });


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

//打开新窗口
function addTab(_this) {
    tab.tabAdd(_this);
}