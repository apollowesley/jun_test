<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>移动</title>
<script type="text/javascript" src="customJs/templateTree.js"></script>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			questionTemplateTree('eventTree');
		});
		function setTemplateId(id, name) {
			$("#checkedId").val(id);
			$("#moveMess").text(name);
			/* 			document.moveQuestionForm.submit(); */
		}
	</script>

	<div class="pageContent">
		<div class="panelBar" align="center">
			<ul class="toolBar">
				<li><a><span><b>请选择移动的模板</b></span></a></li>
			</ul>
		</div>
		<div class="pageFormContent" layoutH="85" >
			<ul class="tree treeFolder expand" id="eventTree">
				<li><a href="javascript">题型选择</a>
					<ul>
						<li><a ondblclick="setTemplateId('11')">单选题单选题单选题单选题单选题</a>
							<ul>
								<li><a tname="name2" tvalue="value2">单选题</a></li>
							</ul></li>
						<li><a tname="name2" tvalue="value2">多选题</a></li>
						<li><a tname="name2" tvalue="value2">判断题</a></li>
					</ul></li>
			</ul>
		</div>
		<div class="formBar">
			<form id="moveQuestionForm" name="moveQuestionForm" method="post"
				action="QuestionAction_doMoveQuestions"
				onsubmit="return validateCallback(this,dialogAjaxDone)">
				<input type="hidden" value="${ids }" name="ids"> 
				<input type="hidden" value="" name="id" id="checkedId">
					<table  width="100%" align="center">
						<tr>
							<td><label >移动到-><span id="moveMess" style="color: red;"></span></label></td>
							<td><button type="submit">确定</button></td>
						</tr>
					</table>
			</form>
		</div>
	</div>
</body>
</html>


