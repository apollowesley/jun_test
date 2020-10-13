var ztree = "";
layui.config({
    base: "/assets/iartisan/plugins/lib/"
}).use(['router', 'zTree'], function () {
    var $ = layui.jquery, form = layui.form, router = layui.router;

    var urls = {
        getResourceListByRoleId: "/roleSupport/getResourceListByRoleId"
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
        data: {"roleId": $("#roleId").val(), "chkDisabled": true},
        success: function (res) {
            $.fn.zTree.init($("#permissions"), setting, res.data);
            $.fn.zTree.getZTreeObj("permissions").expandAll(true);
        }
    });


});