/*
 *
 *
 */
/** *屏蔽键盘事件** */
function keyEvent() {
	if ((window.event.altKey) && ((window.event.keyCode == 37) || // 屏蔽 Alt+
																	// 方向键 ←
	(window.event.keyCode == 39))) { // 屏蔽 Alt+ 方向键 →
		alert("不准你使用ALT+方向键前进或后退网页！");
		event.returnValue = false;
	}

	if ((event.keyCode == 116) || (event.keyCode == 8) ||

	(event.ctrlKey && event.keyCode == 82)) {
		event.keyCode = 32;
		event.cancelBubble = false;
		event.returnValue = false;
		return false;
	}
	if (event.ctrlKey && event.keyCode == 78) {
		event.returnValue = false; // 屏蔽 Ctrl+n
	}
}
function reply(cidd) {
	$("html,body").stop(false);
	$("html,body").animate({
		scrollTop : $(cidd).offset().top - 70
	}, 800);
	return false;
}
/* 单选题选择提示 */
function testSelectDial(id, value) {
	// alert(value);
	var dom1 = jQuery('#answer-' + id);
	if (value != null || value != '') {
		dom1.addClass('indexFinished');
	}
}

/* 多选题选择提示 */

function testMultiSelectDial(id) {
	// alert('#multi-select-' +id);
	var flag = false;
	var dom1 = jQuery('#multi-select-' + id + ' input:checked');
	var value = dom1.length;
	var dom2 = jQuery('#answer-' + id);
	// alert(value);
	if (value == 0) {
		dom2.removeClass('indexFinished');
	} else {
		dom2.addClass('indexFinished');
	}
}
/* 判断题选择提示 */

function testJudgeDial(id, value) {
	// alert(value);
	var dom1 = jQuery('#answer-' + id);
	if (value != null || value != '') {
		dom1.addClass('indexFinished');
	}
}

/* 填空题选择提示 */

function testFillDial(id) {
	// alert('#fill-' + id);
	var flag = false;
	var dom1 = jQuery('#fill-' + id + ' > input');
	var num1 = dom1.length;
	// alert(num1);
	var num2 = 0;
	var dom2 = jQuery('#dial-' + id);
	// alert(num1);
	dom1.each(function() {
		var tm = jQuery(this).val();
		// alert(tm);
		if (tm.trim() != '') {
			num2++;
		}
	});
	if (num1 != num2) { // 未填写内容
		// alert("if ");
		dom2.removeClass('indexFinished');
	} else { // 填写了内容
		// alert("else ");
		dom2.addClass('indexFinished');

	}
}

/* 标记该题 */

function markQuestion(id, value) {
	// alert(value);
	var dom2 = jQuery('#answer-' + id);
	if (value) {
		dom2.addClass('indexMark');
	} else {
		dom2.removeClass('indexMark');
	}
}
/*-------------------时钟------------------------------*/
var ts = document.getElementById("timestart");
var tp = document.getElementById("timespan");
var te = document.getElementById("timeend");
var nowstart = new Date(); // 开始时间
var hour = nowstart.getHours();
var minute = nowstart.getMinutes();
var second = nowstart.getSeconds();

regltime(hour, minute, second, ts);
var num =$("#timeNum").text();
/** ** num 为考试时间 *** */
var num2 = num * 60; // num换算 为秒计算
var nowend = new Date(); // 定义一个新时间 为 结束时间
nowend.setMinutes(nowend.getMinutes() + parseFloat(num)); // 设置考试时间为num分钟
var hour2 = nowend.getHours();
var minute2 = nowend.getMinutes();
var second2 = nowend.getSeconds();
regltime(hour2, minute2, second2, te);

function regltime(hour, minute, second, id) {
	/** 规范时间格式函数 * */
	if (hour < 10) {
		hour = "0" + hour;
	}
	if (minute < 10) {
		minute = "0" + minute;
	}
	if (second < 10) {
		second = "0" + second;
	}
	id.innerHTML = hour + ":" + minute + ":" + second;
}
function time() {
	var num =$("#timeNum").text();
	/** ** num 为考试时间 *** */
	var num2 = num * 60; // num换算 为秒计算
	var now = new Date();
	var hour = now.getHours();
	var minute = now.getMinutes();
	var second = now.getSeconds();
	regltime(hour, minute, second, tp);

	if (num2 <= 5 * 60) {
		/** 还剩5分钟现在时间 变为红色 * */
		document.getElementById("timespan").style.color = "#F00";
	}
	if (num2 == 0) {
		alert("交卷时间已到，点[确定]后试卷将自动提交！");
		document.getElementById("btnSubmit").click();
	} else if (num2 != 0) {
		var mm = setTimeout("time()", "1000");
	}
	num2--;
};
