<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="themes/css/button.css" rel="stylesheet" type="text/css">
</head>
<style type="text/css" media="screen">
.my-uploadify-button {
	background: none;
	border: none;
	text-shadow: none;
	border-radius: 0;
}

.uploadify:hover .my-uploadify-button {
	background: none;
	border: none;
}

.fileQueue {
	width: 400px;
	height: 150px;
	overflow: auto;
	border: 1px solid #E5E5E5;
	margin-bottom: 10px;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		//初始化uploadify 用于word上传
		$("#testFileInput2").uploadify({
			'swf' : 'uploadify/scripts/uploadify.swf',
			'uploader' : 'QestionsExcelAction_doQuestionsImport',
			formData : {
				PHPSESSID : 'xxx',
				ajax : 1
			},
			'method' : "post",//请求方式
			queueID : 'fileQueue',
			buttonText : '选择题库文件(.xls)',
			'fileTypeDesc' : '支持格式:*.xls',//提示上传格式
			'fileTypeExts' : '*.xls',//限制上传文件格式
			'fileObjName' : 'questionsExcel',
			fileSizeLimit : '50MB',
			successTimeout : 99999,
			progressData : "percentage",//显示上传的百分比
			width : 102,
			auto : false,
			multi : false,
			onUploadSuccess : function(file, data, response) {
				alert('导入题库' + file.name + '成功');
			},
			//选择文件时出错
			onSelectError : function(file, errorCode, errorMsg) {
				alert(errorMsg);
			},
			onError : function(errorObj) {
				alert(errorObj.info + "               " + errorObj.type);
			}
		});
	});
</script>
<s:set var="haveRight"
	value="@com.evil.util.ValidateUtil@hasRight('/QestionsExcelAction_doQuestionsImport')" />
<div class="pageContent" style="margin: 10px;" layoutH="50">
	<s:if test="#haveRight">
		<input id="testFileInput2" type="file" name="questionsExcel">
		<form action="QestionsExcelAction_doQuestionsImport" method="post"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, navTabAjaxDone);"></form>
	</s:if>
	<s:else>
		<div id="testFileInput2-button" class="uploadify-button "
			style="height: 30px; line-height: 30px; width: 102px; background: white;">
			<span class="uploadify-button-text" style="color: black;">选择题库文件(.xls)</span>
		</div>
	</s:else>
	<div id="fileQueue" class="fileQueue" style="height: 50px;"></div>

	<input type="image" src="uploadify/img/upload.jpg"
		onclick="$('#testFileInput2').uploadify('upload', '*');"
		${haveRight?'':'disabled="disabled"'  } /> <input type="image"
		src="uploadify/img/cancel.jpg"
		onclick="$('#testFileInput2').uploadify('cancel', '*');"
		${haveRight?'':'disabled="disabled"'  } />

	<div class="divider"></div>
	<s:set var="exportRight"
		value="@com.evil.util.ValidateUtil@hasRight('/QestionsExcelAction_exportQuestions')" />
	<div style="margin: 25px; height: 30px">
		导出题库：
		<s:if test="#exportRight">
			<a class="buttons orange" href="QestionsExcelAction_exportQuestions"
				target="dwzExport" targetType="navTab" title="确定要导出全部题库吗?"><span>导出题库</span></a>
		</s:if>
		<s:else>
			<span class="buttons orange" style="background: white;">导出题库</span>
		</s:else>

	</div>
	<div class="divider"></div>
	<s:set var="clearRight"
		value="@com.evil.util.ValidateUtil@hasRight('/QuestionAction_clearQuestionBnakAndTemplate')" />
	<div style="margin: 25px; height: 30px">
		清空题库：
		<s:if test="#exportRight">
		<a class="buttons orange"
			href="QuestionAction_clearQuestionBnakAndTemplate" target="ajaxTodo"
			title="确定要清空所有的试题和试题模板吗?"><span>清空题库</span></a>
		</s:if>
		<s:else>
			<span class="buttons orange" style="background: white;">清空题库</span>
		</s:else>
	</div>
	<div class="divider"></div>
	<div style="margin: 25px; height: 30px">
		题库模板:&nbsp;&nbsp;&nbsp;<a href="temp/questions.xls">questions.xls</a>
	</div>
	<div class="divider"></div>

</div>

</html>