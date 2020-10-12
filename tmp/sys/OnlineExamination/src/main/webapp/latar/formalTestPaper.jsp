<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="myTags" uri="../myTags.tld"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="themes/css/button.css" rel="stylesheet" type="text/css">
<style type="text/css">
.operate a span {
	color: #fef4e9;
	outline: none;
	text-decoration: none;
}
</style>
</head>
<%-- <form id="pagerForm" method="post" action="PaperAction_paperSort">

	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form> --%>
<body>
	<div class="pageHeader">
		<form id="pagerForm" action="PaperAction_findConditionPaper"
			method="post" onsubmit="return navTabSearch(this);">
			<input type="hidden" name="pageNum" value="1" /> <input
				type="hidden" name="numPerPage"
				value="${pageResult.page.numPerPage}" /> <input type="hidden"
				name="orderField" value="${param.orderField}" /> <input
				type="hidden" name="orderDirection" value="${param.orderDirection}" />
		</form>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
			</ul>
		</div>
		<s:set var="paperList" value="pageResult.list" />
		<s:if test="%{#paperList.isEmpty()}">
			<tr>
				<p>没有数据</p>
			</tr>
		</s:if>
		<s:else>
			<div id="paperTable">
				<table class="list" width="100%" layoutH="65">
					<thead>
						<tr>
							<th width="90">试卷名称</th>
							<th width="90">考试时间</th>
							<th width="50">试卷总分</th>
							<th width="90">考试开始时间</th>
							<th width="90">考试结束时间</th>
							<th width="90">添加时间</th>
							<th width="70">操作</th>
						</tr>
					</thead>
					<tbody id="paersData">
						<s:iterator var="paper" value="#paperList" status="sta">
							<tr>
								<td ><a title="预览试卷" target="navTab" style="color: black;"
									href="PaperAction_findQuestionsByPaperId?pid=${paper.id}&paperPage=previewPage"><span><s:property
											value="#paper.title" /></span></a></td>
								<td>${paper.exanTime }分钟</td>
								<td><s:property value="#paper.allScore" /></td>
								<td><fmt:formatDate value="${paper.startTime }"
										pattern="yyyy-MM-dd hh:mm:ss" /></td>
								<td><fmt:formatDate value="${paper.endTime }"
										pattern="yyyy-MM-dd hh:mm:ss" /></td>
								<td><fmt:formatDate value="${paper.addTime }"
										pattern="yyyy-MM-dd hh:mm:ss" /></td>
								<td class="operate"><s:a cssClass="buttons small orange"
										style="text-decoration: none"
										action="FormalTestAction_toUpdateFormalTestPage?id=%{#paper.id}"
										target="dialog" rel="dlg_page2" width="550" height="400">
										<span>修改</span>
									</s:a> <s:a title="设计 " target="navTab"
										action="PaperAction_toDesignPaperPage?pid=%{#paper.id}"
										cssClass="buttons small orange" style="text-decoration: none"><span>设计</span></s:a></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
			<div class="panelBar">
				<div class="pages">
					<div class="pages">
						<span>显示</span> <select name="numPerPage"
							onchange="navTabPageBreak({numPerPage:this.value})">
							<option value="10"
								<c:if test="${param.numPerPage==10}">selected = "true"</c:if>>10</option>
							<option value="20"
								<c:if test="${param.numPerPage==20}">selected = "true"</c:if>>20</option>
							<option value="50"
								<c:if test="${param.numPerPage==50}">selected = "true"</c:if>>50</option>
							<option value="100"
								<c:if test="${param.numPerPage==100}">selected = "true"</c:if>>100</option>
							<option value="200"
								<c:if test="${param.numPerPage==200}">selected = "true"</c:if>>200</option>
						</select> <span>条，共${pageResult.page.totalCount }条</span>
					</div>
				</div>
				<div class="pagination" targetType="navTab"
					totalCount="${pageResult.page.totalCount }"
					numPerPage="${pageResult.page.numPerPage }"
					currentPage="${param.pageNum }"></div>
			</div>
		</s:else>
	</div>
</body>
</html>
