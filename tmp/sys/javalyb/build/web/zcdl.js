function check() {
    var username = document.getElementsByName("username")[0].value;
    var password = document.getElementsByName("password")[0].value;
    var repassword = document.getElementsByName("repassword")[0].value;
    var email = document.getElementsByName("email")[0].value;
    var phone = document.getElementsByName("phone")[0].value;
    if (username == "") {
        alert("用户名不能为空");
        return false;
    } else if (!username.match(/^\w{0,7}$/)) {
        alert("用户名格式不正确");
        return false;
    } else {
    }
    if (!password.match(/^\w{5,15}$/)) {
        alert("密码格式不正确");
        return false;
    } else if (password !== repassword) {
        alert("两次输入的密码不一致");
        return false;
    } else {
    }
    if (!email.match(/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/)) {
        alert("邮箱格式不正确");
        return false;
    } else {
    }
    if (!phone.match(/^1[3|4|5|7|8][0-9]\d{8}$/)) {
        alert("手机号格式不正确");
        return false;
    } else {
    }
}



