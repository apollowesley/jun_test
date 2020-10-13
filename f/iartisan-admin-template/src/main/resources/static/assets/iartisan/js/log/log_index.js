layui.config({
    base: "/assets/iartisan/plugins/lib/"
}).use(['router', "util"], function () {
    var router = layui.router, util = layui.util;

    var urls = {
        queryPageData: "/log/queryPageData"
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
                    title: '用户',
                    field: 'userName',
                    align: 'center'
                },
                {
                    title: '调用方法',
                    field: 'method',
                    align: 'center'
                },
                {
                    title: '方法描述',
                    field: 'methodDesc',
                    align: 'center'
                },
                {
                    title: '操作IP',
                    field: 'ip',
                    align: 'center'
                },
                {
                    title: '操作时间',
                    templet: function (d) {
                        return util.toDateString(d.startTime);
                    },
                    align: 'center'
                }
            ]]
        });
    }
});