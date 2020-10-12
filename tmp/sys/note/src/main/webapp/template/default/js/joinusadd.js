var inputs;
var spans;
window.onload = function() {
	inputs=$$("titles").getElementsByTagName("input");
	spans=$$("titles").getElementsByTagName("span");
	inputs[0].onfocus = a;
	inputs[0].onblur = a;
	inputs[1].onfocus = b;
	inputs[1].onblur = b;
	inputs[2].onfocus = c;
	inputs[2].onblur = c;
	inputs[3].onfocus = d;
	inputs[3].onblur = d;
};
function a() {
	var a_u = inputs[0].value;
	if (a_u == "") {
		spans[0].innerHTML = "3-50个字符，包含字母数字中文";
	} else if (!/^[\u4e00-\u9fa5a-z0-9_-]{3,25}$/.exec(a_u)) {
		spans[0].innerHTML = "输入有误：3-50个字符";
	} else {
		spans[0].innerHTML = "可以使用";
		return true;
	}
	return false;
}
function b() {
	var a_u = inputs[1].value;
	if (a_u == "") {
		spans[1].innerHTML = "输入金额，整数值";
	} else if (!/^[0-9]+$/.exec(a_u)) {
		spans[1].innerHTML = "输入有误：请输入一个整数值";
	} else {
		spans[1].innerHTML = "可以使用";
		return true;
	}
	return false;
}

function c() {
	var a_u = inputs[2].value;
	if (a_u == "") {
		spans[2].innerHTML = "输入预计人数，整数值";
	} else if (!/^[0-9]+$/.exec(a_u)) {
		spans[2].innerHTML = "输入有误：请输入一个整数值";
	} else {
		spans[2].innerHTML = "输入正确";
		return true;
	}
	return false;
}

function d() {
	var a_u = inputs[3].value;
	if (a_u == "") {
		spans[3].innerHTML = "可参与人权限";
	} else if (!/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/.exec(a_u)) {
		spans[3].innerHTML = "邮箱格式不正确";
	} else {
		spans[3].innerHTML = "可以使用";
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