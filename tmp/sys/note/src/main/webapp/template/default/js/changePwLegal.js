window.onload = function() {
	document.getElementById('password_old').onfocus = b;
	document.getElementById('password_old').onblur = b;
	document.getElementById('password_new').onfocus = c;
	document.getElementById('password_new').onblur = c;
	document.getElementById('password_new2').onfocus = d;
	document.getElementById('password_new2').onblur = d;
};
function b() {
	var obj_u = document.getElementById('password_old');
	var a_u = obj_u.value;
	if (a_u == "") {
		var x = obj_u.parentNode.className;
		x = x.substring(0, 26);
		obj_u.parentNode.className = x;
		document.getElementById('password_msg').innerHTML = "输入旧密码";
	} else if (!/^[a-z0-9_-]{4,16}$/.exec(a_u)) {
		obj_u.parentNode.className = obj_u.parentNode.className
				.substring(0, 26)
				+ " msg_err";
		document.getElementById('password_msg').innerHTML = "输入有误：4-16个字符";
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
	var obj_u = document.getElementById('password_new');
	var a_u = obj_u.value;
	if (a_u == "") {
		var x = obj_u.parentNode.className;
		x = x.substring(0, 26);
		obj_u.parentNode.className = x;
		document.getElementById('password2_msg').innerHTML = "6-16个字符，字母和数字和符号";
	} else if (!/^[a-z0-9_-]{6,16}$/.exec(a_u)) {
		obj_u.parentNode.className = obj_u.parentNode.className
				.substring(0, 26)
				+ " msg_err";
		document.getElementById('password2_msg').innerHTML = "输入有误：6-16个字符";
	} else {
		obj_u.parentNode.className = obj_u.parentNode.className
				.substring(0, 26)
				+ " msg_ok";
		document.getElementById('password2_msg').innerHTML = "可以使用";
		return true;
	}
	return false;
}
function d() {
	var obj_u = document.getElementById('password_new2');
	var a_u = obj_u.value;
	if (a_u == "") {
		var x = obj_u.parentNode.className;
		x = x.substring(0, 27);
		obj_u.parentNode.className = x;
		document.getElementById('password3_msg').innerHTML = "再输入一次密码";
	} else if (a_u != document.getElementById('password_new').value) {
		obj_u.parentNode.className = obj_u.parentNode.className
				.substring(0, 27)
				+ " msg_err";
		document.getElementById('password3_msg').innerHTML = "两次输入密码不相同";
	} else {
		obj_u.parentNode.className = obj_u.parentNode.className
				.substring(0, 27)
				+ " msg_ok";
		document.getElementById('password3_msg').innerHTML = "输入正确";
		return true;
	}
	return false;
}

function checkform() {
	if (b() && c() && d()) {
		if (confirm('确认修改密码？？？')) {
			document.changePw.submit();
			return true;
		}
	} else {
		return false;
	}
}