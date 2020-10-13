layui.config({
    base: "/assets/iartisan/plugins/lib/"
}).use(['jquery', 'zTree', 'router', 'layer'], function () {
    let router = layui.router,
        urls = {
            addData: "/hrDept/addData"
        },
        layer = layui.layer;
    //防重提
    let sumbit = false;
    form.on("submit(addData)", function (data) {
        sumbit = !sumbit;
        if (sumbit) {
            router.post({
                url: urls.addData,
                data: data.field,
                success: function () {
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                }
            });
        }
        return false;
    });
});