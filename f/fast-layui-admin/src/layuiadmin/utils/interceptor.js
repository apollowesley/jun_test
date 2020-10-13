var url = ["login.html"];

function _interceptor() {
    var href = window.location.href;
    //判断当前url是否登录页面
    if (href.indexOf(url[0]) != -1) {
        //是
    } else {
        var token = $.cookie("token");
        if (token == undefined || token == null || token == "null" || token == "") {
            window.location.href = "../login.html";
        }
    }
}

_interceptor();