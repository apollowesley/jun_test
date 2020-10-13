var cacheStr = window.sessionStorage.getItem("cache"),
    oneLoginStr = window.sessionStorage.getItem("oneLogin");
layui.use(['form', 'jquery', "layer"], function () {
    var $ = layui.jquery,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        form = layui.form;

    var content = '<div style="padding: 20px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">' +
        'iartisan-admin-template基于spring-boot、layui、layuiCMS等众多优秀开源框架！<br><br>' +
        '<span style="color: #FF5722">重要提醒：作者不懂前端</span><br><br>' +
        '专注于后台管理系统的实现，提供基础的权限模块，并不实现具体业务代码！<br><br>' +
        '您有任何问题或者建议，都可以和作者联系' +
        '</div>';

    //公告层
    function showNotice() {
        layer.open({
            type: 1,
            title: "系统公告",
            area: '300px',
            shade: 0.8,
            id: 'LAY_layuipro',
            btn: ['关闭'],
            moveType: 1,
            content: content,
            success: function (layero) {
                var btn = layero.find('.layui-layer-btn');
                btn.css('text-align', 'center');
                btn.on("click", function () {
                    tipsShow();
                });
            },
            cancel: function (index, layero) {
                tipsShow();
            }
        });
    }

    function tipsShow() {
        window.sessionStorage.setItem("showNotice", "true");
        if ($(window).width() > 432) {  //如果页面宽度不足以显示顶部“系统公告”按钮，则不提示
            layer.tips('系统公告躲在了这里', '#userInfo', {
                tips: 3,
                time: 1000
            });
        }
    }

    $(".showNotice").on("click", function () {
        showNotice();
    });
    if (window.sessionStorage.getItem("showNotice") != "true") {
        showNotice();
    }

    //功能设定
    $(".functionSetting").click(function () {
        layer.open({
            title: "功能设定",
            area: "20%",
            type: "1",
            content: '<div class="functionSrtting_box">' +
                '<form class="layui-form">' +
                '<div class="layui-form-item">' +
                '<label class="layui-form-label">Tab切换刷新</label>' +
                '<div class="layui-input-block">' +
                '<input type="checkbox" name="changeRefresh" lay-skin="switch" lay-text="开|关">' +
                '<div class="layui-word-aux">开启后切换窗口刷新当前页面</div>' +
                '</div>' +
                '</div>' +
                '<div class="layui-form-item skinBtn">' +
                '<a href="javascript:;" class="layui-btn layui-btn-sm layui-btn-normal" lay-submit="" lay-filter="settingSuccess">确定</a>' +
                '<a href="javascript:;" class="layui-btn layui-btn-sm layui-btn-primary" lay-submit="" lay-filter="noSetting">取消</a>' +
                '</div>' +
                '</form>' +
                '</div>',
            success: function (index, layero) {
                //如果之前设置过，则设置它的值
                $(".functionSrtting_box input[name=changeRefresh]").prop("checked", changeRefreshStr == "true" ? true : false);
                //设定
                form.on("submit(settingSuccess)", function (data) {
                    window.sessionStorage.setItem("changeRefresh", data.field.changeRefresh == "on" ? "true" : "false");
                    location.reload();
                    return false;
                });
                //取消设定
                form.on("submit(noSetting)", function () {
                    layer.closeAll("page");
                });
                form.render();  //表单渲染
            }
        })
    })
   //切换主题
    $(".changeTheme").on('click', function () {
        window.location = "changeTheme";
        window.reload();
    });
});