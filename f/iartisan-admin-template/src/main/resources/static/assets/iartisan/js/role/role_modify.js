var ztree = "";
layui.config({
    base: "/assets/iartisan/plugins/lib/"
}).use(['router', 'zTree'], function () {
    var $ = layui.jquery, form = layui.form, router = layui.router;

    var urls = {
        getResourceListByRoleId: "/roleSupport/getResourceListByRoleId",
        modifyData: "/roleSupport/modifyData"

    };

    var setting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };

    router.post({
        url: urls.getResourceListByRoleId,
        data: {"roleId": $("#roleId").val(), "chkDisabled": false},
        success: function (res) {
            $.fn.zTree.init($("#permissions"), setting, res.data);
            $.fn.zTree.getZTreeObj("permissions").expandAll(true);
        }
    });

    form.on("submit(addData)", function (data) {
        var zTree = $.fn.zTree.getZTreeObj("permissions")
        var nodes = zTree.getCheckedNodes(true);
        var auths = new Array();
        for (var i = 0; i < nodes.length; i++) {
            auths.push(nodes[i].id)
        }
        router.post({
            url: urls.modifyData,
            data: {"roleId": $("#roleId").val(), "permissions": auths.join(",")},
            success: function () {
                layer.closeAll("iframe");
                //刷新父页面
                parent.location.reload();
            }
        });
        return false;
    })
});