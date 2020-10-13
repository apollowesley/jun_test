layui.config({
    base: "/assets/iartisan/plugins/lib/"
}).use(['router', "util", 'layer', 'table'], function () {
    var router = layui.router, util = layui.util, layer = layui.layer, table = layui.table;


    var urls = {
        queryPageData: "/activiti/model/queryPageData",
        toDesign: "/activiti/model/toDesign",
        deleteData: "/activiti/model/deleteData",
        deploy: "/activiti/model/deploy",
        modifyDataPage: "/activiti/model/modifyDataPage"
    };
    queryPageData();

    var tableIns;

    function queryPageData() {
        tableIns = router.table({
            elem: "#dataList",
            url: urls.queryPageData,
            type: 'post',
            cols: [[
                {
                    title: 'id',
                    field: 'id',
                    align: 'center'
                },
                {
                    title: '流程名称',
                    field: 'name',
                    align: 'center'
                },
                {
                    title: 'category',
                    field: 'category',
                    align: 'center'
                },
                {
                    title: 'tenantId',
                    field: 'tenantId',
                    align: 'center'
                },
                {
                    title: '部署时间',
                    templet: function (d) {
                        return util.toDateString(d.startTime);
                    },
                    align: 'center'
                },
                {
                    title: '操作',
                    templet: function (d) {
                        var html = "";
                        if (d.deploymentId == null) {
                            html += "<a class='layui-btn layui-btn-xs' lay-event='deploy'>发布</a>";
                        }
                        html += "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='edit'>编辑</a>";
                        html += "<a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='del'>删除</a>";
                        return html;
                    }
                }
            ]]
        });
    }

    table.on('tool(dataList)', function (obj) {
        var layEvent = obj.event, data = obj.data;
        if (layEvent == 'del') {
            top.layer.confirm("确定删除吗", {async: true}, function (index) {
                top.layer.close(index);
                router.post({
                    url: urls.deleteData,
                    data: {modelId: data.id},
                    success: function () {
                        tableIns.reload();
                    }
                });
            });
        } else if (layEvent == 'deploy') {
            top.layer.confirm("确定发布吗", {async: true}, function (index) {
                top.layer.close(index);
                router.post({
                    url: urls.deploy,
                    data: {modelId: data.id},
                    success: function () {
                        tableIns.reload();
                    }
                });
            });
        } else if (layEvent == 'edit') {
            var index = layer.open({
                    type: 2,
                    maxmin: true,
                    anim: 1,
                    title: '流程设计',
                    content: urls.modifyDataPage + "/" + data.id
                }
            );
            layer.full(index);
        }
    });
    $("#btdDesign").on("click", function () {
        var index = top.layui.layer.open({
                type: 2,
                maxmin: true,
                anim: 1,
                title: '流程设计',
                content: urls.toDesign
            }
        );
        layer.full(index);
    });
});