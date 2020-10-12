<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="myTags" uri="../myTags.tld"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%-- <form id="pagerForm" method="post" action="PaperAction_paperSort">

	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form> --%>
<body>
	<div class="pageHeader">
		<form id="pagerForm" action="ExamAction_toExamManagePage"
			method="post" onsubmit="return navTabSearch(this);">
			<input type="hidden" name="pageNum" value="1" /> <input
				type="hidden" name="numPerPage"
				value="${pageResult.page.numPerPage}" /> <input type="hidden"
				name="orderField" value="${param.orderField}" /> <input
				type="hidden" name="orderDirection" value="${param.orderDirection}" />
			<table class="searchContent">
				<tr>
					<td>考试名称：<input type="text" name="examName" id="examName"
						value='${examName}' />&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp; 
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;
					 <select class="combox" name="examTypeId">
							<option value="">所有类型试题</option>
							<s:iterator var="t" value="examTypes">
								<option value="${t.id }"
									<c:if test="${examTypeId==t.id}">selected = "true"</c:if>>${t.getName() }</option>
							</s:iterator>
					</select>
					</td>
					<td><button type="submit">检索</button></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
			   <li><s:a cssClass="add" action="ExamAction_toAddExamPage" target="dialog" rel="dlg_page2" width="650" height="500"><span>添加新考试</span></s:a ></li>
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
				<table class="table" width="100%" layoutH="110">
					<thead>
						<tr>
							<th width="60">考试名称</th>
							<th width="90">考试类型</th>
							<th width="90">考试试卷</th>
							<th width="90">考试时长</th>
							<th width="90">前台显示</th>
							<th width="90">开放时间</th>
							<th width="90">结束时间</th>
							<th width="90">添加时间</th>
							<th width="50">操作</th>
							
<%-- 							<th width="90" orderField="title"
								<c:if test='${param.orderField=="title" }'>class="${param.orderDirection}"</c:if>>试卷名称</th>
							<th width="90">试卷类型</th>
							<th width="50" orderField="allScore"
								<c:if test='${param.orderField=="allScore" }'>class="${param.orderDirection}"</c:if>>试卷总分</th>
							<th width="30">审核</th>
							<th width="70">操作</th> --%>
						</tr>
					</thead>
					<tbody id="paersData">
						<s:iterator var="exam" value="pageResult.list" status="sta">
							<tr target="examId" rel="${exam.id }">
								<td>${exam.examName }</td>
								<td>${exam.examType.name }</td>
								<td>${exam.paper.title }</td>
								<td>${exam.examTime }分钟</td>
								<td>${exam.frontDeskShow?'是':'否' }</td>
								<td>${exam.startTime}</td>
								<td>${exam.endTime}</td>
								<td>${exam.addTime}</td>
								<td>
									<s:a  title="确定删除吗" target="ajaxTodo" action="ExamAction_deleteExam?id=%{#t.id }" cssClass="btnDel">删除</s:a >
									<s:a  title="修改" action="ExamAction_toUpdateExamPage?id=%{#exam.id}" target="dialog" rel="dlg_page2" width="650" height="500" cssClass="btnEdit">修改</s:a >
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
