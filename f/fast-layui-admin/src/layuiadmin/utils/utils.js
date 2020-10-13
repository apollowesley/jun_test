//判断是否为数组且至少有一个元素;
function isArray(arr) {
    if (!$.isArray(arr)) {
        return false;
    } else if (arr.length < 1) {
        return false;
    } else {
        return true;
    }
}
//判断数值是否为空
function isUndefined(value) {
    return value == undefined || $.trim(value).length == 0;
}
//当value为空,长度为零或者仅由空白字符()组成时,返回True;否则返回False
function isBlank(value) {
    return value == '' || $.trim(value).length == 0;
}


//获取url地址参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

//获取url地址参数(可传中文)
function GetQueryStringCanChinese(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}


/**
 * 检验ip地址
 * @param {地址} ip 
 */
function isValidIP(ip) {
    var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
    return reg.test(ip);
}
/**
 * 检验密码强度正则，最少8位 ,字母+数字，字母+特殊字符，数字+特殊字符
 * @param {密码} password 
 */
function isVlidPassword(password) {
    if (password.length < 8) {
        return false;
    }
    var reg = /^(?![a-zA-z]+$)(?!\d+$)(?![!@#$%^&*]+$)[a-zA-Z\d!@#$%^&*]+$/;
    return reg.test(password);
}

/**
 * 检验非负数
 * @param {值} value 
 */
function isNonnegative(value) {
    var reg = /^\d+(\.{0,1}\d+){0,1}$/;
    return reg.test(value);
}

/**
 * 根据Token请求下载
 * @param {请求地址} url 
 * @param {文件名带格式} fileName 
 */
function exportByToken(url, fileName) {
    var url = url;
    var xhr = new XMLHttpRequest();
    xhr.open('GET', url, true); //get请求，请求地址，是否异步
    xhr.setRequestHeader("Token", $.cookie("token"));
    xhr.responseType = "blob"; // 返回类型blob
    xhr.onload = function () { // 请求完成处理函数
        if (this.status === 200) {
            var blob = this.response; // 获取返回值
            var a = document.createElement('a');
            a.download = fileName;
            a.href = window.URL.createObjectURL(blob);
            a.click();
        }
    };
    // 发送ajax请求
    xhr.send();
}

/**
 * 检验价格, 1-9开头，后跟是0-9，可以跟小数点，但小数点后要带上1-2位小数
 * @param {价格} price 
 */
function vlidPrice(price) {
    var reg = /^[1-9]\d*(.\d{1,2})?$/;
    return reg.test(price);
}


