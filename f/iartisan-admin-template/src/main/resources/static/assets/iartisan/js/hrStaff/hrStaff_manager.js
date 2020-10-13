layui.config({
    base: "/assets/iartisan/plugins/lib/"
}).use(['router', 'table', 'util', 'iartisntips'], function () {
    let router = layui.router, table = layui.table,
        util = layui.util, tips = layui.iartisntips;

    let urls = {
        addDataPage: "/hrStaff/addDataPage",
        queryPageData: "/hrStaff/queryPageData",
        updateStaffStatus: "/hrStaff/updateStaffStatus",
        deleteData: "/hrStaff/deleteData",
        assignLoginPermissions: "/hrStaff/assignLoginPermissions"
    };

    let area = ['50%', '80%'];
    var tableIns;
    queryPageData();
    function queryPageData() {
        tableIns = router.table({
            elem: "#dataList",
            url: urls.queryPageData,
            type: 'post',
            where: {"staffDept": $("#deptId").val()},
            even: true,
            cols: [[
                {
                    field: 'staffName',
                    title: '员工姓名',
                    width: "15%",
                },
                {
                    title: '状态',
                    width: "11%",
                    templet: function (d) {
                        var checked = d.staffStatus == 'Y' ? 'checked' : null;
                        var status = "<input type='checkbox' value=" + d.staffId + " lay-skin='switch' lay-text='在职|离职' lay-filter='staffStatus' " + checked + "> ";
                        return status;

                    }
                },
                {
                    title: '创建时间',
                    width: "22%",
                    templet: function (d) {
                        return util.toDateString(d.createDate);

                    }
                },
                {
                    title: '最后更新时间',
                    width: "22%",
                    templet: function (d) {
                        return util.toDateString(d.createDate);

                    }
                },
                {
                    title: '操作',
                    align: "center",
                    width: "30%",
                    templet: function () {
                        var html = "<a class='layui-btn layui-btn-xs' lay-event='edit'>编辑</a>";
                        html += "<a class='layui-btn layui-btn-xs layui-btn-danger' lay-event='del'>删除</a>";
                        html += "<a class='layui-btn layui-btn-xs layui-btn-normal' lay-event='assignLoginPermissions'>分配登录权限</a>";
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
            layer.confirm('确定删除该员工吗？', {icon: 3, title: '提示信息'}, function (index) {
                layer.close(index);
                router.get({
                    url: urls.deleteData, data: {staffId: data.staffId}, success: function () {
                        tableIns.reload();
                    }
                })
            });
        } else if (layEvent == 'assignLoginPermissions') {
            layer.confirm('确定分配登录权限吗？', {icon: 3, title: '提示信息'}, function (index) {
                layer.close(index);
                router.get({
                    url: urls.assignLoginPermissions, data: {staffId: data.staffId}, success: function () {
                        tableIns.reload();
                    }
                })
            });
        }
    });
    //监听在/离职操作
    form.on('switch(staffStatus)', function (obj) {
        let index = layui.layer.load(1, {
            shade: [0.1, '#fff'] //0.1透明度的白色背景
        });
        $.get(urls.updateStaffStatus, {staffId: obj.value}, function (res) {
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
            title: '添加员工',
            area: area,
            content: urls.addDataPage + "?deptId=" + $("#deptId").val(),
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