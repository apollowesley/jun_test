layui.config({
    base: "/assets/iartisan/plugins/lib/"
}).use(['jquery', 'router','layer', 'iartisntips', 'treeGrid'], function () {
    var router = layui.router, $ = layui.jquery,
        layer = layui.layer, tips = layui.iartisntips,
        treeGrid = layui.treeGrid;

    var urls = {
        queryPageData: "/menuSupport/queryPageData",
        addDataPage: "/menuSupport/addDataPage",
        deleteData: "/menuSupport/deleteData",
        modifyDataPage: "/menuSupport/modifyDataPage",
        addResourceIndex: "/resourceSupport/index"
    };
    queryPageData();

    function queryPageData() {
        treeGrid.render({
            id: 'dataList'
            , elem: '#dataList'
            , url: urls.queryPageData
            , cellMinWidth: 100
            , idField: 'id'
            , treeId: 'id'//树形id字段名称
            , treeUpId: 'parentMenuId'//树形父id字段名称
            , treeShowName: 'title'//以树形式显示的字段
            , cols: [[
                {
                    field: 'title',
                    title: '菜单名称',
                },
                {
                    field: 'href',
                    title: '跳转链接',
                },
                {
                    title: '图标',
                    templet: function (d) {
                        let icon = "layui-icon " + d.icon;
                        return "<i class = '" + icon + "' ></i>";
                    }
                },
                {
                    title: '操作 <i style="color: #FF5722;" class="layui-icon alone-tips" lay-tips="<span style=\'color:red;\'>子权限管理</span></br>主要是针对菜单下按钮级别的操作进行权限控制</br>该功能主要适用于二级菜单</br>"></i>',
                    templet: function (d) {
                        var html = "<a class='layui-btn layui-btn-xs' lay-event='edit'>编辑</a>";
                        /* html += "<a class='layui-btn layui-btn-xs layui-btn-danger' lay-event='del'>删除</a>";*/
                        html += "<a class='layui-btn layui-btn-xs layui-btn-normal' lay-event='addMenuSource'>子权限管理</a>";
                        return html;
                    }
                }
            ]],
            page:false
        });
    }

    $("#btnQuery").on("click", function () {
        queryPageData();
    });
    let area=['45%', '80%']
    //列表操作
    treeGrid.on('tool(dataList)', function (obj) {
        var layEvent = obj.event,
            data = obj.data;
        if (layEvent == 'addMenuSource') {
            layui.layer.open({
                type: 2,
                maxmin: true,
                anim: 1,
                title: '添加子菜单 <span style="margin-left: 5px;font-size: 10px">[' + data.title + "]</span>",
                skin: 'layui-layer-molv',
                area: ['80%', '89%'],
                content: urls.addResourceIndex + "?menuId=" + data.id,
                btn: ['关闭'],
                btnAlign: 'c'
            });
        }
        if (layEvent == 'edit') {
            layui.layer.open({
                type: 2,
                title: '菜单信息修改',
                skin: 'layui-layer-molv',
                area: area,
                content: urls.modifyDataPage + "?menuId=" + data.id,
                btn: ['修改', '关闭'],
                btnAlign: 'c',
                yes: function (index, layero) {
                    layero.find('iframe').contents().find("#formModify").find("#btnModify").click();
                },
                btn2: function (index) {
                    layer.close(index);
                }
            });
        }
    });

    $("#btnAddPage").click(function () {
        var index = layui.layer.open({
            title: "添加菜单",
            type: 2,
            content: urls.addDataPage,
            area: ['50%', '88%'],
            maxmin: true,
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
    });
    tips.show();

});