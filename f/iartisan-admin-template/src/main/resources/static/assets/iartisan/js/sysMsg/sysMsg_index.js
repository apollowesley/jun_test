layui.config({
    base: "/assets/iartisan/plugins/lib/"
}).use(['router', 'util'], function () {
    var router = layui.router, util = layui.util;

    let urls = {
        queryPageData: "/reminder/getPageData"
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
                    title: '消息内容',
                    templet: function (d) {
                        return "<a class='layui-table-link' lay-event=\"detail\" href='javascript:void(0);'>" + d.msgTitle + "</a>";
                    },
                    align: 'left',
                    width: "70%"
                },
                {
                    title: '创建时间',
                    templet: function (d) {
                        return util.toDateString(d.createTime, 'yyyy-MM-dd HH:mm:ss');
                    },
                    align: 'left'
                }
            ]]
        });
    }
});