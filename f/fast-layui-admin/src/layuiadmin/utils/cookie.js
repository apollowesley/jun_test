var cookie = {};
//设置cookie
cookie.setCookie = function (res) {
    //console.info("setCookie:"+res);
    $.cookie('token', res.data.token, {
        path: '/',
        expires: 1 //有效期一天
    });
    $.cookie('userName', res.data.userName, {
        path: '/',
        expires: 1 //有效期一天
    });
}
//删除cookie
cookie.removeCookie = function (locationUrl) {
    $.cookie('userName', null, {
        path: '/'
    });
    $.cookie('token', null, {
        path: '/'
    });
  
}