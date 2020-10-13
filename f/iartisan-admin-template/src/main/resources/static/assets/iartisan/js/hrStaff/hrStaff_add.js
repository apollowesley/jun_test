layui.config({
    base: "/assets/iartisan/plugins/lib/"
}).use(['jquery', 'zTree', 'router', 'layer'], function () {
    let router = layui.router,
        urls = {
            addData: "/hrStaff/addData"
        },
        layer = layui.layer;
    form.on("submit(addData)", function (data) {
        router.post({
            url: urls.addData,
            data: data.field,
            success: function () {
                layer.closeAll("iframe");
                //刷新父页面
               location.reload();
            }
        });
        return false;
    });
});