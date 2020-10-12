/** 重新编辑记账 */
function mod_form(obj, bid) {
	document.getElementById('modify_form').className = "modify_form_block";
	document.getElementById('modify_mask').className = "modify_mask_block";
	var objTemp = getFirstChild(obj);
	document.getElementById('datetime_m').value = objTemp.innerHTML;
	objTemp = getNextChild(objTemp);
	document.getElementById('btype_m').value = objTemp.innerHTML;
	objTemp = getNextChild(objTemp);
	document.getElementById('bio_m').selectedIndex = sel_index(objTemp.innerHTML);
	objTemp = getNextChild(objTemp);
	document.getElementById('bamount_m').value = rep_rmb(objTemp.innerHTML);
	if(document.getElementById('bio_m').selectedIndex==5)
	{
		document.getElementById('bamount_m').value=0;
		document.getElementById('bamount_m').setAttribute("type","hidden");
	}
	else
	{
		document.getElementById('bamount_m').setAttribute("type","text");
	}
	objTemp = getNextChild(objTemp);
	document.getElementById('bcaption_m').value = objTemp.innerHTML;
	document.getElementById('b_modify').value = bid;
}
function getFirstChild(obj) {
	var result = obj.firstChild;
	while (!result.tagName) {
		result = result.nextSibling;
	}
	return result;
}

function getNextChild(obj) {
	var result = obj.nextSibling;
	while (!result.tagName) {
		result = result.nextSibling;
	}
	return result;
}
function sel_index(str) {
	var arr = [ "支出", "收入", "债务", "债权","无金钱" ];
	for ( var i = 0; i < arr.length; i++) {
		if (arr[i] == str)
		{
			return i + 1;
		}
	}
}
function rep_rmb(rmb) {
	/*var reg1 = new RegExp("\\-", "g");// 两次转义
	var reg2 = new RegExp("\\+", "g");*/
	var reg3 = new RegExp("[^0-9\.]", "g");
	/*rmb = rmb.replace(reg1, "");
	rmb = rmb.replace(reg2, "");*/
	rmb = rmb.replace(reg3, "");
	return rmb;
}
/** 检测编辑表单是否为空 */
function checknull_m() {
	var btype = trim(document.getElementById("btype_m").value);
	var bio = document.getElementById("bio_m").value;
	var bamount = trim(document.getElementById("bamount_m").value);
	var regx = /^[\d\.]+$/;
	if (btype == "") {
		document.getElementById("checknullprompt_m").innerHTML = "账单类型不能为空！";
		document.getElementById("btype_m").focus();
		return false;
	}
	if (bio == "") {
		document.getElementById("checknullprompt_m").innerHTML = "账单出入不能为空";
		document.getElementById("bio_m").focus();
		return false;
	}
	if (bamount == "" || !regx.exec(bamount)) {
		document.getElementById("checknullprompt_m").innerHTML = "账单金额不能为空且必须为数字！";
		document.getElementById("bamount_m").focus();
		return false;
	}
	return true;
}
/** 去除字符串中的空格 */
function trim(str) {
	str = str.replace(/^(\s|\u00A0)+/, '');
	for ( var i = str.length - 1; i >= 0; i--) {
		if (/\S/.test(str.charAt(i))) {
			str = str.substring(0, i + 1);
			break;
		}
	}
	return str;
}
/**页面加载完成执行，添加监听函数，对有提示内容的输入框，
 * 获得焦点时不显示框内文字，失去焦点后显示框内文字。
 * （如果框内文字不是默认提示语句，则不变）*/
function modify_onload() {
	var arr=["btype_m","bamount_m","bcaption_m"];
for(var i=0;i<arr.length;i++){
	var x1="var obj"+i+" = document.getElementById('"+arr[i]+"')";
	var x2="var str"+i+" = document.getElementById('"+arr[i]+"').value";
	var x3="var fc"+i+" = function(e) {focusclear.call(this, str"+i+");};";
	var x4="var ba"+i+" = function(e) {bluradd.call(this, str"+i+");};";
	var x5="addlistenner(obj"+i+", fc"+i+", ba"+i+");";
	eval(x1);eval(x2);eval(x3);eval(x4);eval(x5);
	}
};
/**页面加载完成执行，添加监听函数*/
addLoadEvent(modify_onload);
/** 继承监听函数,并传入参数以初始化; */
function addlistenner(obj, fc, ba) {
	if (document.addEventListener) {// 如果是Firefox
		obj.addEventListener("focus", fc, true);
		obj.addEventListener("blur", ba, true);
	} else {
		obj.attachEvent("onfocusin", fc);
		obj.attachEvent("onfocusout", ba);
	}
}
function focusclear(str) {
	if (this.value == str)
		this.value = "";
}
function bluradd(str) {
	if (this.value == "")
		this.value = str;
} 
function addLoadEvent(func) {
	  var oldonload = window.onload;
	  if (typeof window.onload != 'function') {
	    window.onload = func;
	  } else {  
	    window.onload = function() {
	      oldonload();
	      func();
	    };
	  }
	}