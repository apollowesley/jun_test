layui.config({
    base: "/assets/iartisan/plugins/lib/"
}).use(['form', 'layer', 'jquery', 'router', 'md5','iartisntips'], function () {
    var form = layui.form,
        router = layui.router,
        md = layui.md5,
        $ = layui.jquery,
        tips = layui.iartisntips;;
    //登录按钮
    form.on("submit(login)", function (data) {
        var _that = $(this);
        _that.text("登录中...").attr("disabled", "disabled").addClass("layui-disabled");
        //密码md5
        var pwd = $.md5(data.field["password"]);
        router.post({
                url: "/authenticate",
                data: {userName: data.field["userName"], userPwd: pwd,vercode:data.field["vercode"]},
                success: function (res) {
                    location.href = res.message;
                },
                error: function () {
                    _that.text("登录").removeAttr("disabled").removeClass("layui-disabled");
                }
            }
        );
        return false;
    });
    tips.show();
});
