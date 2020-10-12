var userisexisted=true;//用户已经存在
var emailisexisted=true;//email已经存在
window.onload = function() {
	document.getElementById('username').onfocus = a;
	document.getElementById('username').onblur = a;
	document.getElementById('password').onfocus = b;
	document.getElementById('password').onblur = b;
	document.getElementById('password2').onfocus = c;
	document.getElementById('password2').onblur = c;
	document.getElementById('email').onfocus = d;
	document.getElementById('email').onblur = d;
};
function a() {
	var obj_u = document.getElementById('username');
	var a_u = obj_u.value;
	if (a_u == "") {
		var x = obj_u.parentNode.className;
		x = x.substring(0, 25);
		obj_u.parentNode.className = x;
		document.getElementById('username_msg').innerHTML = "3-25个字符，包含字母数字中文（用户名一旦创建不能修改，建议使用方便记忆的，昵称可以在登录后修改）";
	} else if (!/^[\u4e00-\u9fa5a-z0-9_-]{3,25}$/.exec(a_u)) {
		obj_u.parentNode.className=obj_u.parentNode.className.substring(0, 25)+" msg_err";
		document.getElementById('username_msg').innerHTML = "输入有误：3-25个字符";
	} else {
		obj_u.parentNode.className=obj_u.parentNode.className.substring(0, 25)+" msg_ok";
		document.getElementById('username_msg').innerHTML = "可以使用";
		getResult();
		return true;
	}
	return false;
}
function b() {
	var obj_u = document.getElementById('password');
	var a_u = obj_u.value;
	if (a_u == "") {
		var x = obj_u.parentNode.className;
		x = x.substring(0, 26);
		obj_u.parentNode.className = x;
		document.getElementById('password_msg').innerHTML = "6-16个字符，字母和数字和符号";
	} else if (!/^[a-z0-9_-]{6,16}$/.exec(a_u)) {
		obj_u.parentNode.className=obj_u.parentNode.className.substring(0, 26)+" msg_err";
		document.getElementById('password_msg').innerHTML = "输入有误：6-16个字符";
	} else {
		obj_u.parentNode.className=obj_u.parentNode.className.substring(0, 26)+" msg_ok";
		document.getElementById('password_msg').innerHTML = "可以使用";
		return true;
	}
	return false;
}

function c() {
	var obj_u = document.getElementById('password2');
	var a_u = obj_u.value;
	if (a_u == "") {
		var x = obj_u.parentNode.className;
		x = x.substring(0, 27);
		obj_u.parentNode.className = x;
		document.getElementById('password2_msg').innerHTML = "再输入一次密码";
	} else if (a_u!=document.getElementById('password').value) {
		obj_u.parentNode.className=obj_u.parentNode.className.substring(0, 27)+" msg_err";
		document.getElementById('password2_msg').innerHTML = "两次输入密码不相同";
	} else {
		obj_u.parentNode.className=obj_u.parentNode.className.substring(0, 27)+" msg_ok";
		document.getElementById('password2_msg').innerHTML = "输入正确";
		return true;
	}
	return false;
}

function d() {
	var obj_u = document.getElementById('email');
	var a_u = obj_u.value;
	if (a_u == "") {
		var x = obj_u.parentNode.className;
		x = x.substring(0, 27);
		obj_u.parentNode.className = x;
		document.getElementById('email_msg').innerHTML = "用于找回用户名，可以不填……";
	} else if (!/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/.exec(a_u)) {
		obj_u.parentNode.className=obj_u.parentNode.className.substring(0, 27)+" msg_err";
		document.getElementById('email_msg').innerHTML = "邮箱格式不正确";
	} else {
		obj_u.parentNode.className=obj_u.parentNode.className.substring(0, 27)+" msg_ok";
		document.getElementById('email_msg').innerHTML = "可以使用";
		getResultEmail();
		return true;
	}
	return false;
}
function checkform(){
	if(a()&&b()&&c()&&!userisexisted){
		if(document.getElementById('email').value==""||(document.getElementById('email').value!=""&&!emailisexisted)){
			document.register_form.submit();
			return true;
		}
	}else{return false;}
}