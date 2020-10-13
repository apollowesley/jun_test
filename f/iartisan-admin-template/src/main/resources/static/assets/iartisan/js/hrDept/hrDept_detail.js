layui.config({
    base: "/assets/iartisan/plugins/lib/"
}).use(['jquery', 'router', 'layer'], function () {
    let router = layui.router,
        urls = {
            deleteData: "/hrDept/deleteData"
        },
        layer = parent.layui.layer;
    //删除
    $("#btnDel").on('click', function () {
        let deptId = $(this).attr("attr-data");
        top.layer.confirm('确认删除?', {icon: 3, title: '提示'}, function (index) {
            let load = layer.load(1);
            router.ajaxGet(urls.deleteData, {deptId: deptId}, {}, function (res) {
                layer.close(load);
                if (res.code == '000000') {
                    location.reload();
                } else {
                    top.layer.msg(res.message, {shift: 6});
                }
            });
            top.layer.close(index);
        });
    })
});