function check() {
    var user_name=regist.username.value;//获取表单的用户名的值
    var user_pwd=regist.password.value;//获取表单密码值
    if((user_name=="")||(user_name==null)){//判断用户名是否为空，为空就弹出提示框"请输入用户名"，否则正常执行下面的代码。
        alert("请输入用户名！");
        regist.user.focus();//获取焦点，即：鼠标自动定位到用户名输入框，等待用户输入用户名。
        return;
    }
    else if((user_pwd=="")||(user_pwd==null)){//判断密码是否为空，为空就弹出提示框"请输入密码"，否则正常执行下面的代码。
        alert("请输入密码！");
        regist.pwd.focus();//获取焦点。
        return;}
    else {//如果用户名、密码都正常输入，则提交表单，浏览器经打开新的（主页）窗口。
        regist.submit();
        // window.location.href="http://baidu.com/";
  /*      window.open("http://www.cnblogs.com/qikeyishu/");*/
    }
}