/*
 *
 *
 */
/** *���μ����¼�** */
function keyEvent() {
	if ((window.event.altKey) && ((window.event.keyCode == 37) || // ���� Alt+
																	// ����� ��
	(window.event.keyCode == 39))) { // ���� Alt+ ����� ��
		alert("��׼��ʹ��ALT+�����ǰ���������ҳ��");
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
		event.returnValue = false; // ���� Ctrl+n
	}
}
function reply(cidd) {
	$("html,body").stop(false);
	$("html,body").animate({
		scrollTop : $(cidd).offset().top - 70
	}, 800);
	return false;
}
/* ��ѡ��ѡ����ʾ */
function testSelectDial(id, value) {
	// alert(value);
	var dom1 = jQuery('#answer-' + id);
	if (value != null || value != '') {
		dom1.addClass('indexFinished');
	}
}

/* ��ѡ��ѡ����ʾ */

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
/* �ж���ѡ����ʾ */

function testJudgeDial(id, value) {
	// alert(value);
	var dom1 = jQuery('#answer-' + id);
	if (value != null || value != '') {
		dom1.addClass('indexFinished');
	}
}

/* �����ѡ����ʾ */

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
	if (num1 != num2) { // δ��д����
		// alert("if ");
		dom2.removeClass('indexFinished');
	} else { // ��д������
		// alert("else ");
		dom2.addClass('indexFinished');

	}
}

/* ��Ǹ��� */

function markQuestion(id, value) {
	// alert(value);
	var dom2 = jQuery('#answer-' + id);
	if (value) {
		dom2.addClass('indexMark');
	} else {
		dom2.removeClass('indexMark');
	}
}
/*-------------------ʱ��------------------------------*/
var ts = document.getElementById("timestart");
var tp = document.getElementById("timespan");
var te = document.getElementById("timeend");
var nowstart = new Date(); // ��ʼʱ��
var hour = nowstart.getHours();
var minute = nowstart.getMinutes();
var second = nowstart.getSeconds();

regltime(hour, minute, second, ts);
var num =$("#timeNum").text();
/** ** num Ϊ����ʱ�� *** */
var num2 = num * 60; // num���� Ϊ�����
var nowend = new Date(); // ����һ����ʱ�� Ϊ ����ʱ��
nowend.setMinutes(nowend.getMinutes() + parseFloat(num)); // ���ÿ���ʱ��Ϊnum����
var hour2 = nowend.getHours();
var minute2 = nowend.getMinutes();
var second2 = nowend.getSeconds();
regltime(hour2, minute2, second2, te);

function regltime(hour, minute, second, id) {
	/** �淶ʱ���ʽ���� * */
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
	/** ** num Ϊ����ʱ�� *** */
	var num2 = num * 60; // num���� Ϊ�����
	var now = new Date();
	var hour = now.getHours();
	var minute = now.getMinutes();
	var second = now.getSeconds();
	regltime(hour, minute, second, tp);

	if (num2 <= 5 * 60) {
		/** ��ʣ5��������ʱ�� ��Ϊ��ɫ * */
		document.getElementById("timespan").style.color = "#F00";
	}
	if (num2 == 0) {
		alert("����ʱ���ѵ�����[ȷ��]���Ծ��Զ��ύ��");
		document.getElementById("btnSubmit").click();
	} else if (num2 != 0) {
		var mm = setTimeout("time()", "1000");
	}
	num2--;
};
