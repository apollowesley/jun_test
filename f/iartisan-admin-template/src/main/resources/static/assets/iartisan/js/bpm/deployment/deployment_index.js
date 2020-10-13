layui.config({
    base: "/assets/iartisan/plugins/lib/"
}).use(['router', "util", 'layer', 'upload', 'table'], function () {
    var router = layui.router, util = layui.util, layer = layui.layer,
        upload = layui.upload, table = layui.table;
    layer.config({
        skin: 'layui-layer-molv'
    });

    var urls = {
        queryPageData: "/bpm/deployment/queryPageData",
        toDesign: "/bpm/deployment/toDesign",
        deploy: "/bpm/deployment/deploy",
        deleteData: "/bpm/deployment/deleteData",
        getDefinitionDetail: "/bpm/deployment/getDefinitionDetail"
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
                    templet: function () {
                        var html = "<a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='del'>删除</a>";
                        html += "<a class='layui-btn layui-btn-normal layui-btn-xs' lay-event='query'>查看流程图</a>";
                        return html;
                    }
                }
            ]]
        });
    }

    table.on('tool(dataList)', function (obj) {
        let layEvent = obj.event, data = obj.data;
        if (layEvent == 'del') {
            layer.confirm("确定删除吗",{async:true}, function (index) {
                layer.close(index);
                router.post({
                    url: urls.deleteData,
                    data: {deploymentId: data.id},
                    success: function () {
                        tableIns.reload();
                    }
                });
            });
        }
        if (layEvent == 'query') {
            let index = layer.open({
                    type: 2,
                area: ['80%', '90%'],
                    anim: 1,
                    title: '流程设计',
                    content: urls.getDefinitionDetail + "?deploymentId=" + data.id
                }
            );
        }
    });

    upload.render({
        elem: '#btnUpload',
        url: urls.deploy,
        accept: "file",
        done: function (res) {
            tableIns.reload();
        }
    });

    $("#btnDesign").on("click", function () {
        var index = layer.open({
                type: 2,
                maxmin: true,
                anim: 1,
                title: '流程设计',
            content: urls.toDesign
            }
        );
        layer.full(index);
    });

    $("#btnQuery").on('click', function () {
        queryPageData();
    });
});