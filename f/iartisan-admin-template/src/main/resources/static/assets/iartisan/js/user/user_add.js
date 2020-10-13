layui.config({
    base: "/assets/iartisan/plugins/lib/"
}).use(['router'], function () {

    var router = layui.router;

    var urls = {
        addData: "/userSupport/addData"
    };

    form.on("submit(addData)", function (data) {
        var arr = new Array();
        $("input:checkbox[name=roles]:checked").each(function () {
            arr.push($(this).val());
        });
        data.field.roles = arr.join(",");
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
    });

});