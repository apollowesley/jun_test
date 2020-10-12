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
	var arr = [ "支出", "收入", "债务", "债权" ];
	for ( var i = 0; i < arr.length; i++) {
		if (arr[i] == str)
			return i + 1;
	}
}
function rep_rmb(rmb) {
	var reg1 = new RegExp("\\-", "g");// 两次转义
	var reg2 = new RegExp("\\+", "g");
	rmb = rmb.replace(reg1, "");
	rmb = rmb.replace(reg2, "");
	return rmb;
}
/** 检测添加表单是否为空 */
function checknull() {
	var btype = trim(document.getElementById("btype").value);
	var bio = document.getElementById("bio").value;
	var bamount = trim(document.getElementById("bamount").value);
	var regx = /^[\d\.]+$/;
	if (btype == "") {
		document.getElementById("checknullprompt").innerHTML = "账单类型不能为空！";
		document.getElementById("btype").focus();
		return false;
	}
	if (bio == "") {
		document.getElementById("checknullprompt").innerHTML = "账单出入不能为空";
		document.getElementById("bio").focus();
		return false;
	}
	if (bamount == "" || !regx.exec(bamount)) {
		document.getElementById("checknullprompt").innerHTML = "账单金额不能为空且必须为数字！";
		document.getElementById("bamount").focus();
		return false;
	}
	return true;
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
/*window.onload = function() {
	var arrstr = [ [ "btype", "", "", "", "" ], [ "bamount", "", "", "", "" ],
			[ "btype_m", "", "", "", "" ], [ "bamount_m", "", "", "", "" ] ];
	for ( var i = 0; i < arrstr.length; i++) {
		// addlistennerbefore(arrstr[i],i);
		arrstr[i][1] = document.getElementById(arrstr[i][0]);
		arrstr[i][2] = document.getElementById(arrstr[i][0]).value;
		alert(arrstr[i][2]);
		arrstr[i][3] = function(e) {
			focusclear.call(this, arrstr[i][2]);
		};
		arrstr[i][4] = function(e) {
			bluradd.call(this, arrstr[i][2]);
		};
		addlistenner(arrstr[i][1], arrstr[i][3], arrstr[i][4]);
	}
	;
};*/
/**页面加载完成执行，添加监听函数，对有提示内容的输入框，
 * 获得焦点时不显示框内文字，失去焦点后显示框内文字。
 * （如果框内文字不是默认提示语句，则不变）*/
function modify_onload() {
	var arr=["btype","bamount","travelleader","btype_m","bamount_m"];
for(var i=0;i<arr.length;i++){
	var x1="var obj"+i+" = document.getElementById('"+arr[i]+"')";
	var x2="var str"+i+" = document.getElementById('"+arr[i]+"').value";
	var x3="var fc"+i+" = function(e) {focusclear.call(this, str"+i+");};";
	var x4="var ba"+i+" = function(e) {bluradd.call(this, str"+i+");};";
	var x5="addlistenner(obj"+i+", fc"+i+", ba"+i+");";
	eval(x1);eval(x2);eval(x3);eval(x4);eval(x5);
	}
};
addLoadEvent(modify_onload);
/**页面加载完成执行，添加监听函数*/
/*window.onload = function addlistennerbefore(str, i) {
	var obj1 = document.getElementById("btype");
	var str1 = document.getElementById("btype").value;
	var fc1 = function(e) {
		focusclear.call(this, str1);
	};
	var ba1 = function(e) {
		bluradd.call(this, str1);
	};
	addlistenner(obj1, fc1, ba1);
	var obj2 = document.getElementById("bamount");
	var str2 = document.getElementById("bamount").value;
	var fc2 = function(e) {
		focusclear.call(this, str2);
	};
	var ba2 = function(e) {
		bluradd.call(this, str2);
	};
	addlistenner(obj2, fc2, ba2);
	var obj3 = document.getElementById("btype_m");
	var str3 = document.getElementById("btype_m").value;
	var fc3 = function(e) {
		focusclear.call(this, str3);
	};
	var ba3 = function(e) {
		bluradd.call(this, str3);
	};
	addlistenner(obj3, fc3, ba3);
	var obj4 = document.getElementById("bamount_m");
	var str4 = document.getElementById("bamount_m").value;
	var fc4 = function(e) {
		focusclear.call(this, str4);
	};
	var ba4 = function(e) {
		bluradd.call(this, str4);
	};
	addlistenner(obj4, fc4, ba4);
};*/
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