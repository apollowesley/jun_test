<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//labelD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.labeld">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改试卷</title>
</head>

<body>

	<div class="pageContent">
		<s:set var="haveRight"
			value="@com.evil.util.ValidateUtil@hasRight('/PaperAction_doEditPaperHeading')" />
		<form method="post"
			action="${haveRight?'PaperAction_doEditPaperHeading':'#' }"
			class="pageForm"
			onsubmit="return validateCallback(this,dialogAjaxDone)">
			<input type="hidden" name="pid" value="${model.id }"> <input
				type="hidden" name="questionsType" value="${questionsType}">
			<div class="pageFormContent nowrap" layoutH="56">
				<!-- 		NAME,parentId,url,ORDER,content -->
				<p>
					<label>标题名称：</label>
					<s:if test="questionsType==0">单选题</s:if>
					<s:elseif test="questionsType==1">多选题</s:elseif>
					<s:elseif test="questionsType==2">判断题</s:elseif>
				</p>
				<p>
					<label>问题数：</label> ${model.itemNumberArr[questionsType] }
				</p>
				<p>
					<label>每小题分值:</label><input name="score" type="text"
						value="${model.itemScoreArr[questionsType] }">
				</p>
			</div>
			<div class="formBar">
				<ul>
					<li><s:if test="#haveRight">
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">提交</button>
								</div>
							</div>
						</s:if> <s:else>
							<div class="buttonDisabled">
								<div class="buttonContent">
									<button type="button">提交</button>
								</div>
							</div>
						</s:else></li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</body>
</html>