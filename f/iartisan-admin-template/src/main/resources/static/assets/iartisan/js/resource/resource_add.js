var ztree = "";
layui.config({
    base: "/assets/iartisan/plugins/lib/"
}).use(['jquery', 'router', 'zTree'], function () {

    var $ = layui.jquery, form = layui.form, router = layui.router,
        layer = parent.layer === undefined ? layui.layer : top.layer;


    var urls = {
        addData: "/resourceSupport/addData"
    };


    form.on("submit(addData)", function (data) {
        router.post({
            url: urls.addData,
            data: data.field,
            success: function () {
                layer.closeAll("iframe");
                //刷新父页面
                parent.location.reload();
            }
        });
        return false;
    })
});