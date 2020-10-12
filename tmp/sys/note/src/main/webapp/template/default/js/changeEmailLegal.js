var userisexisted = true;// 用户已经存在
var emailisexisted = true;// email已经存在
window.onload = function() {
	document.getElementById('username').onfocus = a;
	document.getElementById('username').onblur = a;
	document.getElementById('password_now').onfocus = b;
	document.getElementById('password_now').onblur = b;
	document.getElementById('email').onfocus = d;
	document.getElementById('email').onblur = d;
	document.getElementById('tel').onfocus = c;
	document.getElementById('tel').onblur = c;
};
function a() {
	var obj_u = document.getElementById('username');
	var a_u = obj_u.value;
	if (a_u == "") {
		var x = obj_u.parentNode.className;
		x = x.substring(0, 25);
		obj_u.parentNode.className = x;
		document.getElementById('username_msg').innerHTML = "3-25个字符，包含字母数字中文";
		return true;
	} else if (!/^[\u4e00-\u9fa5a-z0-9_-]{3,25}$/.exec(a_u)) {
		obj_u.parentNode.className = obj_u.parentNode.className
				.substring(0, 25)
				+ " msg_err";
		document.getElementById('username_msg').innerHTML = "输入有误：3-25个字符";
	} else {
		obj_u.parentNode.className = obj_u.parentNode.className
				.substring(0, 25)
				+ " msg_ok";
		document.getElementById('username_msg').innerHTML = "可以使用";
		getResult();
		return true;
	}
	return false;
}
function b() {
	var obj_u = document.getElementById('password_now');
	var a_u = obj_u.value;
	if (a_u == "") {
		var x = obj_u.parentNode.className;
		x = x.substring(0, 26);
		obj_u.parentNode.className = x;
		document.getElementById('password_msg').innerHTML = "6-16个字符，字母和数字和符号";
	} else if (!/^[a-z0-9_-]{6,16}$/.exec(a_u)) {
		obj_u.parentNode.className = obj_u.parentNode.className
				.substring(0, 26)
				+ " msg_err";
		document.getElementById('password_msg').innerHTML = "输入有误：6-16个字符";
	} else {
		obj_u.parentNode.className = obj_u.parentNode.className
				.substring(0, 26)
				+ " msg_ok";
		document.getElementById('password_msg').innerHTML = "可以使用";
		return true;
	}
	return false;
}
function c() {
	var obj_u = document.getElementById('tel');
	var a_u = obj_u.value;
	if (a_u == "") {
		var x = obj_u.parentNode.className;
		x = x.substring(0, 26);
		obj_u.parentNode.className = x;
		document.getElementById('tel_msg').innerHTML = "手机号码，可以为空";
		return true;
	} else if (!/^(1[3|4|5|8]{1}[0-9]{9})$/.exec(a_u)) {
		obj_u.parentNode.className = obj_u.parentNode.className
				.substring(0, 26)
				+ " msg_err";
		document.getElementById('tel_msg').innerHTML = "输入有误：请输入正确的手机号码";
	} else {
		obj_u.parentNode.className = obj_u.parentNode.className
				.substring(0, 26)
				+ " msg_ok";
		document.getElementById('tel_msg').innerHTML = "正确";
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
		obj_u.parentNode.className = obj_u.parentNode.className
				.substring(0, 27)
				+ " msg_err";
		document.getElementById('email_msg').innerHTML = "邮箱格式不正确";
	} else {
		obj_u.parentNode.className = obj_u.parentNode.className
				.substring(0, 27)
				+ " msg_ok";
		document.getElementById('email_msg').innerHTML = "可以使用";
		getResultEmail();
		return true;
	}
	return false;
}
function checkform() {
	if ((( a()&& b()&&c()) || (!emailisexisted && d())&& b()&&c()) ) {
		if (confirm('确认修改个人资料？？？')) {
			document.changeEmail.submit();
			return true;
		}
	} else {
		return false;
	}
}