<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="customJs/questionTemples.js"></script>
<link rel="stylesheet" href="css/style.css">
<style type="text/css">
</style>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			showQuestionTemplate();
		});
	</script>
	<%-- 	<div class="pageHeader" style="border: 1px #B8D0D6 solid">
		<form id="pagerForm" method="post" action="GradeAction_toGradePage"
			onsubmit="return navTabSearch(this);">
				<input type="hidden" name="pageNum" value="1" /> 
				<input type="hidden" name="numPerPage" value="${pageResult.page.numPerPage}" /> 
				<input type="hidden" name="orderField" value="${param.orderField}" /> 
				<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
		</form>
	</div> --%>
	<div class="pageContent"
		style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
		<s:if test="#application['paperTypes'].list.isEmpty()">
		没用分类信息
		</s:if>
		<s:else>
			<div class="panelBar">
				<ul class="toolBar">
					<li><s:a cssClass="add" action="QuestionTemplateAction_toAddQuestionTemplatePage"
						target="dialog" rel="dlg_page2" width="550" height="300"><span>添加顶级模板</span></s:a></li>
				</ul>
			</div>
			<div id="classsify">
				<table class="list" width="85%" targetType="navTab" layoutH="110">
					<thead>
						<tr align="center">
							<!-- <th width="80">序号</th> -->
							<th width="100">试题模板</th>
							<th width="100">试卷数量</th>
							<th width="100">添加时间</th>
							<th width="100">操作</th>
						</tr>
					</thead>
					<tbody id="content">
					</tbody>
				</table>
			</div>
		</s:else>
	</div>
</body>
</html>