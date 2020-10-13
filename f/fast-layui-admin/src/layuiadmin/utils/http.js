/**
 * api ajax封装
 */

//测试地址

//开发地址
var base_dev = 'http://127.0.0.1:10000/';

var base = base_dev;

var api = {
    //访问多项目配置前缀，方便nginx配置
    admin: base + 'admin/',
};

//封装AJAX请求
function ajax(type, url, data, success) {
    var index = layer.load(0, {
        shade: false
    });
    $.ajax({
        //添加请求头
        headers: {
            'Token': $.cookie("token"),
            "Content-Type": "application/json;charset=UTF-8"
        },
        url: url,
        dataType: "json", //数据格式
        data: JSON.stringify(data),
        type: type, //请求方式
        async: false, //是否异步请求
        timeout: 30000, //超时时间：30秒
        beforeSend: function (XMLHttpRequest) {
            var token = $.cookie("token");
            //发送请求，如果token为空，跳转到登录页面
            if (token == undefined || token == null || token == "null" || token == "") {
                var url = window.document.location.href;
                if (url.indexOf('login.html') > -1) {} else {
                    window.location.href = "../login.html"
                }
            }
        },
        success: function (res) {
            if (res.code == "0000") {
                return success(res);
            } else if (res.code == "9996") {
                layer.alert(res.msg, {
                        icon: 5,
                        title: "提示"
                    },
                    function (index) {
                        layer.close(index);
                        window.location.href = "../login.html";
                    });
            } else {
                layer.alert(res.msg, {
                    icon: 5,
                    title: "提示"
                });
            }
        },
        complete: function (XMLHttpRequest, textStatus) {
            layer.close(index);
        },
        error: function () {
            //请求出错处理
            layer.alert('系统繁忙，请稍后', {
                icon: 5,
                title: "提示"
            });
        }
    });
}
/**
 * 发送post请求
 * @param {发送请求路径} url 
 * @param {JSON数据} data 
 * @param {发送成功回调函数} success 
 */
function apiPost(url, data, success) {
    ajax('post', url, data, function (res) {
        return success(res);
    });
}

/**
 * 发送GET请求
 * @param {发送请求路径} url 
 * @param {JSON数据} data 
 * @param {发送成功回调函数} success 
 */
function apiGet(url, data, success) {
    ajax('get', url, data, function (res) {
        return success(res);
    });
}

/**
 * 发送Delete请求
 * @param {发送请求路径} url 
 * @param {JSON数据} data 
 * @param {发送成功回调函数} success 
 */
function apiDelete(url, data, success) {
    ajax('delete', url, data, function (res) {
        return success(res);
    });
}