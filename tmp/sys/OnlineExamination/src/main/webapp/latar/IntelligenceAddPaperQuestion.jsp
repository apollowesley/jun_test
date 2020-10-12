<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//labelD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.labeld">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>智能添加试卷</title>
<script type="text/javascript">
	
</script>
</head>

<body>
	<!-- 	<h2 class="contentTitle">suggest+lookup</h2> -->
	<s:set var="haveRight" value="@com.evil.util.ValidateUtil@hasRight('/IntelligentPaperAction_doIntelligenceAddPaperQuestion')" />
	<form
		action="${haveRight?'IntelligentPaperAction_doIntelligenceAddPaperQuestion':'#' }"
		method="post" class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<input type="hidden" name="id" value="${id }">
		<div class="pageContent">
			<div class="pageFormContent" layoutH="97">
				<div class="tabs">
					<div class="tabsContent" style="height: 300px;">
						<table class="list nowrap itemDetail" addButton="添加1条模板信息"
							width="100%">
							<thead>
								<tr>
									<th type="lookup" name="templateForms[#index#].name"
										lookupGroup="templateForms[#index#]"
										lookupUrl='latar/treeLookup.jsp' postField="keywords"
										callback="templateForms_callback" size="12"
										fieldClass="required">模板名称</th>
									<th type="enum" name="templateForms[#index#].singleNum"
										size="12">单选题数量</th>
									<th type="enum" name="templateForms[#index#].multipleNum"
										size="12">多选题数量</th>
									<th type="enum" name="templateForms[#index#].judgeNum"
										size="12">判断题数量</th>
									<th type="del" width="60">操作</th>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
				</div>
				<div class="formBar">
					<ul>
						<li><s:if test="#haveRight">
								<div class="buttonActive">
									<div class="buttonContent">
										<button type="submit">添加</button>
									</div>
								</div>
							</s:if> <s:else>
								<div class="buttonDisabled">
									<div class="buttonContent">
										<button type="button">添加</button>
									</div>
								</div>
							</s:else></li>
						<li><div class="button">
								<div class="buttonContent">
									<button class="close" type="button">关闭</button>
								</div>
							</div></li>
					</ul>
				</div>
			</div>
		</div>
	</form>
	<script language="javascript" src="customJs/lookupCallback.js">
		
	</script>
</body>
</html>