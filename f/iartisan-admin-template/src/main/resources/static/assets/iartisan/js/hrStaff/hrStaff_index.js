layui.config({
    base: "/assets/iartisan/plugins/lib/"
}).use(['jquery', 'zTree', 'router', 'layer'], function () {
    let $ = layui.jquery, tree = layui.tree, router = layui.router,
        urls = {
            getDeptDataList: "/hrDept/queryListData",
            staffManagerPage: "/hrStaff/staffManagerPage"
        },
        layer = layui.layer;
    let currentDeptId;

    let setting = {
        check: {
            enable: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: function (event, srcEvent, tree) {
                currentDeptId = tree.id;
                let load = layer.load(1);
                router.ajaxGet(urls.staffManagerPage,
                    {deptId: currentDeptId},
                    {},
                    function (res) {
                        layer.close(load);
                        $("#rightPage").css("display","block");
                        $("#rightPage").html(res);
                    });
            }
        }
    };

    function initTree() {
        let loading = layer.load(1);
        router.get({
            url: urls.getDeptDataList,
            success: function (res) {
                layer.close(loading);
                $.fn.zTree.init($("#deptTree"), setting, res.data);
                $.fn.zTree.getZTreeObj("deptTree").expandAll(true);
            }
        });
    }
    initTree();
});