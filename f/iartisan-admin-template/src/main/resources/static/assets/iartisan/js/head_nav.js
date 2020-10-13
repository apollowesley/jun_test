layui.config({
    base: "/assets/iartisan/plugins/lib/"
}).use(['form', 'element', 'layer', 'jquery', 'bodyTab', 'router'], function () {
     let $ = layui.$, form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        router=layui.router;

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
    $("#btnNotify").on('click', function (event) {
        router.ajaxGet(urls.toSysMsg, {}, {async: false}, function (res) {
            layer.open({
                type: 1,
                title: "消息提醒", //不显示标题栏,
                closeBtn: 0,
                area: '60%',
                btn: ['关闭'],
                btnAlign: 'c',
                moveType: 1, //拖拽模式，0或者1
                content: res,
                offset: '22%',
                shade:0.5,
                skin: 'layui-layer-black'
            });
        });

    });
    //查询未读消息
    router.ajaxGet(urls.getUnreadCount, {}, {async: false}, function (res) {
        if (res.data > 0) {
            $(".notify").show();
            $(".notify").html(res.data);
            $(".layui-icon-notice").css("color", "#1E9FFF");
        }
    });
});

//打开新窗口
function addTab(_this) {
    tab.tabAdd(_this);
}