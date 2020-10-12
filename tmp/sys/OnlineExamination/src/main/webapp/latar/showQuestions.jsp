<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>试卷显示</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.modal-body {
	position: relative;
	max-height: 400px;
	padding: 15px;
	overflow-y: auto;
}

.choiceTitle {
	font-size: 14px;
	font-weight: normal;
	line-height: 21px;
}
</style>
</head>
<body>
	<div class="modal-body">
		<span class="choiceTitle">${model.title }</span>
		<s:if test="questionsType<2">
			<div style="margin-left: 30px;" id="type">
				<span class="choiceTitle"> A.&nbsp; <span>${model.optionA}</span></span><br>
				<span class="choiceTitle"> B.&nbsp; <span>${model.optionB}</span></span><br>
				<span class="choiceTitle"> C.&nbsp; <span>${model.optionC}</span></span><br>
				<span class="choiceTitle"> D.&nbsp; <span>${model.optionD}</span></span>
				<br>
			</div>
		</s:if>
		<hr />
		<div style="margin-left: 0px;">
			<span class="answer">正确答案：</span> <span
				style="color: #356AA0; font-weight: bold;">${model.answer } </span>&nbsp;
			<hr />
			<span class="answer">试题解析：</span> <span style="color: #356AA0;">${model.content }</span>
			<br>
		</div>
	</div>

</body>
</html>