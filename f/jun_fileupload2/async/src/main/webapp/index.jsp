<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>async</title>
</head>
<script type="text/javascript" src="/async/resources/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
$.fn.setCursorPosition = function(position) {
	if (this.lengh == 0)
		return this;
	return $(this).setSelection(position, position);
}

$.fn.setSelection = function(selectionStart, selectionEnd) {
	if (this.lengh == 0)
		return this;
	input = this[0];

	if (input.createTextRange) {
		var range = input.createTextRange();
		range.collapse(true);
		range.moveEnd('character', selectionEnd);
		range.moveStart('character', selectionStart);
		range.select();
	} else if (input.setSelectionRange) {
		input.focus();
		input.setSelectionRange(selectionStart, selectionEnd);
	}
	return this;
}

$.fn.focusEnd = function() {
	this.setCursorPosition(this.val().length);
}
/**
 * 这里开始才是主要的,上面的可有可无
 */
var xmlhttp = null;
function async() {
	$('#result').css('style')
	var url = '/async/WebLogServlet';
	// 创建XMLHttpRequest
	if (window.XMLHttpRequest) {
		//IE7+, Firefox, Opera支持  
		xmlhttp = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		//IE5,IE6支持  
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	// 准备发送请求(未发出)
	xmlhttp.open("GET", url, true);
	xmlhttp.setRequestHeader("cache-control","no-cache");
	xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	// 发送不带参的请求
	xmlhttp.send(null);
	// 回调函数
	xmlhttp.onreadystatechange = callback;
}
function callback() {
	if (xmlhttp.readyState > 2 && xmlhttp.status == 200) {
		var dom = $('#result');
		dom.val(xmlhttp.responseText.replace(/<br\/>/g, ''));
		// 这不是重点,只是将光标移动到最后而已
		dom.focusEnd();
	}
}
</script>
<style>
.inputStyle {
	position: absolute;
	font-size: 9;
	color: #DDDDDD;
	font-family: Fixedsys;
	width: 100%;
	height: 100%;
	border: 0;
	background-color: #000000;
}
</style>
<body style="margin: 0; overflow: hidden;width: 100%; height: 100%;">
	<h3>IE请在地址栏键入/async/WebLogServlet</h3>
	<input type="button" value="点我查看效果" onclick="async()" />
	<br/>
	<textarea id="result" class="inputStyle" wrap="off" readOnly="true"></textarea>
</body>
</html>