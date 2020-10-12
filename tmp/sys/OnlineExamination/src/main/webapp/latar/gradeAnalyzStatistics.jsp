<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<body>
	<div class="pageHeader">
		<form id="pagerForm" action="AnalyzeAction_toAnsWerAnalyzePage"
			method="post" onsubmit="return navTabSearch(this);">
			<input type="hidden" name="pageNum" value="1" /> <input
				type="hidden" name="numPerPage"
				value="${pageResult.page.numPerPage}" /> <input type="hidden"
				name="orderField" value="${param.orderField}" /> <input
				type="hidden" name="orderDirection" value="${param.orderDirection}" />
			<%-- 			<table class="searchContent">
				<tr>
					<td>试卷名称：<input type="text" name="title" id="paperName"
						value='${title}' />&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp; <select class="combox"
						name="iscloesd">
							<option value=""
								<c:if test="${iscloesd==null}">selected = "true"</c:if>>所有试题</option>
							<option value="true"
								<c:if test="${iscloesd=='true'}">selected = "true"</c:if>>开放的</option>
							<option value="false"
								<c:if test="${iscloesd=='false'}">selected = "true"</c:if>>审核中</option>
					</select>
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp; <select class="combox" name="paperType">
							<option value="">所有类型试题</option>
							<s:iterator var="t" value="#application['paperTypes']">
								<option value="${t.getValue() }" <c:if test="${paperType==t.getValue()}">selected = "true"</c:if>>${t.getName() }</option>
							</s:iterator>
					</select>
					</td>
					<td><button type="submit">检索</button></td>
				</tr>
			</table> --%>
		</form>
	</div>
	<div class="pageContent">
		<div class="panelBar"></div>
		<s:set var="paperList" value="pageResult.list" />
		<s:if test="%{#paperList.isEmpty()}">
			<tr>
				<p>没有数据</p>
			</tr>
		</s:if>
		<s:else>
			<div>
				<table class="table" width="100%" layoutH="88">
					<thead>
						<tr>
							<th width="90" orderField="title"
								<c:if test='${param.orderField=="title" }'>class="${param.orderDirection}"</c:if>>试卷名称</th>
							<th width="90">考试时长</th>
							<th width="50">成绩数量</th>
							<th width="30">最高分</th>
							<th width="100">开始时间</th>
							<th width="100">结束时间</th>
							<th width="100">添加时间</th>
							<th width="70">作答分析</th>
							<th width="70">成绩统计</th>
						</tr>
					</thead>
					<tbody id="paersData">
						<s:iterator var="paper" value="#paperList" status="sta">
							<tr align="center">
								<td>${paper.title}</td>
								<td>${paper.exanTime }分钟</td>
								<td>${paper.gradeNum }</td>
								<td>${paper.maxScore }</td>
								<td><fmt:formatDate value="${paper.startTime}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
								<td><fmt:formatDate value="${paper.endTime}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
								<td><fmt:formatDate value="${paper.addTime}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
								<td class="operate">
									<s:a cssClass="buttons orange small"
										style="text-decoration: none"
										action="AnalyzeAction_toAnswerCasePage?id=%{id}"
										target="navTab" rel="answerCase">
										<span>作答情况</span>
									</s:a>
								</td>
								<td class="operate">
									<s:a cssClass="buttons orange small"
										style="text-decoration: none"
										action="AnalyzeAction_toGradeStatisticsPage?id=%{id}"
										target="navTab" rel="gradeStatistics">
										<span>成绩统计</span>
									</s:a>
								</td>
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