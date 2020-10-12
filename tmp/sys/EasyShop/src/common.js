/*
* 定义mock数据格式
* */
export function customizedReturn(success, data, msg, code) {
    return {
        success: success,
        data: data,
        msg: msg,
        code: code
    }
}

/*
* 用户是否登陆
* */
export function isAuth() {
    if (localStorage.getItem('token')) {
        return true
    }
    return false
}