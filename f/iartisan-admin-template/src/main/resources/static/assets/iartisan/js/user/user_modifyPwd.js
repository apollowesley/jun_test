layui.config({
    base: "/assets/iartisan/plugins/lib/"
}).use(['form', 'md5', 'router'], function () {
    var $ = layui.jquery, form = layui.form, router = layui.router,
        layer = layui.layer;

    var urls = {modifyPwd: "/userSupport/modifyPwd"};


    form.verify({
        confirmPwd: function (value, item) {
            var pwd = $("#pwd").val();
            if (pwd != '') {
                if (value != pwd) {
                    return "两次密码要一致";
                }
            }
        }
    });

    form.on("submit(restPwd)", function (data) {
        data.field["pwd"] = $.md5(data.field["pwd"]);
        router.post({
            url: urls.modifyPwd,
            data: data.field,
            success: function () {
                layer.msg("密码修改成功");
                $("#reset").click();
            }
        });
        return false;
    })
});