var app = new Vue({
    el: "#menu-list",
    data: {
    },
    mounted: function () {
        this.initTable();
    },
    update: function () {

    },
    method: {
        /**
         * 初始化表格
         */
        initTable: function () {
            layui.use('table', function () {
                var table = layui.table;
                table.render({
                    elem: '#menu-table',
                    url: '',
                    page: true,
                    cols: [[
                    ]],
                });
            })
        },
    },
});