layui.config({
    base: "/assets/iartisan/plugins/lib/"
}).use(['jquery', 'laydate','router'], function () {
    var laydate = layui.laydate,router=layui.router;

    var urls = {addData: "/bpm/demo/newItem/addData"};

    laydate.render({elem: '#beginTime', trigger: 'click'});
    laydate.render({elem: '#endTime', trigger: 'click'});

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
    });

});