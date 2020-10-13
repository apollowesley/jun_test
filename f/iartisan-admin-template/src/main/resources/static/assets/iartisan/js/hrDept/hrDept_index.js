layui.config({
    base: "/assets/iartisan/plugins/lib/"
}).use(['jquery', 'zTree', 'router', 'layer'], function () {
    let $ = layui.jquery, tree = layui.tree, router = layui.router,
        urls = {
            getDataList: "/hrDept/queryListData",
            addDataPage: "/hrDept/addDataPage",
            queryDetailPage: "/hrDept/queryDetailPage",
            modifyDataPage: "/hrDept/modifyDataPage",
        },
        layer = layui.layer;
    layer.config({
        skin: 'layui-layer-molv'
    })

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
                router.ajaxGet(urls.queryDetailPage,
                    {deptId: currentDeptId},
                    {},
                    function (res) {
                        layer.close(load);
                        $("#rightPage").html(res);
                    });
            }
        }
    };

    function initTree() {
        let loading = layer.load(1);
        router.get({
            url: urls.getDataList,
            success: function (res) {
                layer.close(loading);
                $.fn.zTree.init($("#deptTree"), setting, res.data);
                $.fn.zTree.getZTreeObj("deptTree").expandAll(true);
            }
        });
    }

    //添加按钮
    $("#btnAddPage").on('click', function () {
        if (typeof currentDeptId != 'undefined') {
            let index = layui.layer.open({
                title: "添加下级部门",
                type: 2,
                content: urls.addDataPage + "?parentDeptId=" + currentDeptId,
                area: ['40%','80%'],
                btn: ['添加', '关闭'],
                btnAlign: 'c',
                skin: 'layui-layer-molv',
                yes: function (index, layero) {
                    layero.find('iframe').contents().find("#formAdd").find("#btnAdd").click();
                },
                btn2: function (index) {
                    layui.layer.close(index);
                }
            });
        }
    });
    //刷新
    $("#btnRefresh").on('click', function () {
        initTree();
    });
    //刷新
    $("#btnModify").on('click', function () {
        if (typeof currentDeptId != 'undefined') {
            let index = layui.layer.open({
                title: "修改部门信息",
                type: 2,
                content: urls.modifyDataPage + "?deptId=" + currentDeptId,
                area: ['40%','80%'],
                btn: ['修改', '关闭'],
                btnAlign: 'c',
                skin: 'layui-layer-molv',
                yes: function (index, layero) {
                    layero.find('iframe').contents().find("#formAdd").find("#btnAdd").click();
                },
                btn2: function (index) {
                    layui.layer.close(index);
                }
            });
        }
    });
    initTree();
});