layui.define(['layer', 'form', 'element', 'upload', 'util', 'table'], function (exports) {

    var $ = layui.jquery, layer = parent.layer === undefined ? layui.layer : top.layer, table = layui.table;

    var success_code = "000000", err_msg = "请求异常，请重试";

    var router = {

        ajaxGet: function (url, data, options, callback) {
            return $.ajax({
                type: 'get',
                data: data,
                url: url,
                async: options.async || false,
                success: callback
            });
        },
        ajaxPost: function (url, data, callback) {
            $.post(url, data, function (res) {
                if (res.code === success_code) {
                    callback || layer.msg(res.message)
                }
            })
        },
        get: function (options) {
            options = options || {};
            /*var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});*/
            return $.ajax({
                type: options.type || 'get',
                dataType: options.dataType || 'json',
                data: options.data || {},
                url: options.url,
                success: function (res) {
                    /*top.layer.close(index);*/
                    if (res.code === success_code) {
                        options.success && options.success(res);
                    } else {
                        layer.msg(res.message || res.code, {shift: 6});
                        options.error && options.error();
                    }
                },
                error: function (e) {
                    /*top.layer.close(index);*/
                    layer.msg(err_msg, {shift: 6});
                    options.error && options.error(e);
                }
            });
        },
        post: function (options) {
            options = options || {};
            /*var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});*/
            return $.ajax({
                type: options.type || 'post',
                dataType: options.dataType || 'json',
                data: options.data || {},
                url: options.url,
                success: function (res) {
                    /* top.layer.close(index);*/
                    if (res.code === success_code) {
                        options.success && options.success(res);
                    } else {
                        layer.msg(res.message || res.code, {shift: 6});
                        options.error && options.error();
                    }
                },
                error: function (e) {
                    /*top.layer.close(index);*/
                    layer.msg(err_msg, {shift: 6});
                    options.error && options.error(e);
                }
            });
        },
        table: function (options) {
            var tableIns = table.render({
                elem: options.elem,
                url: options.url,
                method: options.type || 'get',
                cols: options.cols,
                page: true,
                skin: options.skin || ['line','row'],
                limits: options.limits || [10],
                where: options.where || {},
                height: options.height,
                even: options.even || false,
                response: {
                    statusCode: "000000",
                    dataName: "data",
                    countName: "totalRecords"
                },
                request: {
                    pageName: 'pageIndex', //页码的参数名称，默认：page,
                    limitName: 'pageSize' //每页数据量的参数名，默认：limit
                }
            });
            return tableIns;
        },
        location: function (href) {
            location.href = href;
        }
    };

    exports('router', router);
});