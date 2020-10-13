layui.config({
    base: "/assets/iartisan/plugins/lib/"
}).use(['router', 'table', 'util', 'iartisntips'], function () {
    var router = layui.router, table = layui.table,
        util = layui.util, tips = layui.iartisntips;

    var urls = {
        queryPageData: "/userSupport/queryPageData",
        modifyDataPage: "/userSupport/modifyDataPage",
        addDataPage: "/userSupport/addDataPage",
        changeStatus: "/userSupport/changeStatus",
        addData: "/userSupport/addData",
        deleteData: "/userSupport/deleteData"
    };
    queryPageData();

    var tableIns;

    var area = ['50%', '80%'];

    function queryPageData() {
        tableIns = router.table({
            elem: "#dataList",
            url: urls.queryPageData,
            type: 'post',
            where: {"userName": $("#userName").val()},
            even: true,
            cols: [[
                {
                    field: 'userName',
                    title: '用户名',
                },
                {
                    field: 'roles',
                    title: '角色列表',
                },
                {
                    title: '状态',
                    templet: function (d) {
                        var checked = d.userStatus == 'E' ? 'checked' : null;
                        var status = "<input type='checkbox' value=" + d.userId + " lay-skin='switch' lay-text='正常|停用' lay-filter='status' " + checked + "> ";
                        return status;

                    }
                },
                {
                    title: '创建时间',
                    templet: function (d) {
                        return util.toDateString(d.createDate);

                    }
                },
                {
                    title: '操作',
                    fixed: "right",
                    align: "center",
                    templet: function () {
                        var html = "<a class='layui-btn layui-btn-xs' lay-event='edit'>编辑</a>";
                        html += "<a class='layui-btn layui-btn-xs layui-btn-danger' lay-event='del'>删除</a>";
                        return html;

                    }
                }
            ]]
        });
    }

    $("#btnQuery").on("click", function () {
        queryPageData();
    });
    //列表操作
    table.on('tool(_table)', function (obj) {
        var layEvent = obj.event, data = obj.data;
        if (layEvent == 'edit') {
            layui.layer.open({
                type: 2,
                title: '用户信息修改',
                skin: 'layui-layer-molv',
                area: area,
                content: urls.modifyDataPage + "?userId=" + data.userId,
                btn: ['修改', '关闭'],
                yes: function (index, layero) {
                    layero.find('iframe').contents().find("#formModify").find("#btnModify").click();
                },
                btn2: function (index) {
                    layer.close(index);
                }
            });
        } else if (layEvent == 'del') {
            layer.confirm('确定删除该用户吗？', {icon: 3, title: '提示信息'}, function (index) {
                layer.close(index);
                /*$.post(urls.deleteData, {userId: data.userId}, function (res) {
                    if (res.code == '000000') {
                        tableIns.reload();
                    }
                });*/
                router.post({
                    url: urls.deleteData, data: {userId: data.userId}, success: function () {
                        tableIns.reload();
                    }
                })
            });
        }
    });
    //监听性别操作
    form.on('switch(status)', function (obj) {
        var checked = this.checked;
        var index = layui.layer.load(1, {
            shade: [0.1, '#fff'] //0.1透明度的白色背景
        });
        $.post(urls.changeStatus, {userId: obj.value, status: checked ? 'E' : 'D'}, function (res) {
            layui.layer.close(index);
            if (res.code != '000000') {
                layer.msg(res.message);
                queryPageData();
            }
        }).always(function () {
            layui.layer.close(index);
        });
    });

    $("#btnAddPage").click(function () {
        layui.layer.open({
            type: 2,
            title: '添加用户',
            area: area,
            content: urls.addDataPage,
            skin: 'layui-layer-molv',
            btn: ['提交', '关闭'],
            btnAlign: 'c',
            yes: function (index, layero) {
                layero.find('iframe').contents().find("#formAdd").find("#btnAdd").click();
            },
            btn2: function (index) {
                layer.close(index);
            }
        });
    });
    tips.show();
});