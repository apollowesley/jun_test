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
	<div class="pageHeader" style="border: 1px #B8D0D6 solid">
		<form id="pagerForm" method="post" action="GradeAction_toGradePage"
			onsubmit="return navTabSearch(this);">
				<input type="hidden" name="pageNum" value="1" /> 
				<input type="hidden" name="numPerPage" value="${pageResult.page.numPerPage}" /> 
				<input type="hidden" name="orderField" value="${param.orderField}" /> 
				<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
				<input type="hidden" value="${formalTest }" name="formalTest" >
					<table class="searchContent">
						<tr>
							<s:if test="!formalTest">
								<td>试题名称：
									<input type="text" name="paperTitle" id="paperName" value="${paperTitle }" />&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</s:if>
							<td>考生姓名：
								<input type="text" name="userName" id ="paperName" value="${userName }" />&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
							<td><label>完成时间：</label> 
								<input type="text" name="date" class="date" value="${date }" readonly="true" /></td>
							<td><button type="submit">检索</button></td>
						</tr>
					</table>
		</form>
	</div>
	<div class="pageContent"
		style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
		<s:if test="pageResult.list.isEmpty()">
		没用成绩信息
		</s:if>
		<s:else>
			<div class="panelBar">
				<s:if test="!formalTest">
					<ul class="toolBar">
						<li><s:a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids"
							action="GradeAction_batchDeleteGrade" postType="string"
							cssClass="delete"><span>删除</span></s:a></li>
						<li><s:a title="确实清空所用记录吗?" action="GradeAction_clearGrade"
							target="ajaxTodo" cssClass="delete"><span>清空所有成绩</span></s:a></li>
						<li class="line">line</li>
						<li><a class="icon"
							href="javascript:$.printBox('grade_list_print')"><span>打印</span></a></li>
					</ul>
				</s:if>
			</div>
			<div id="grade_list_print">
				<table class="list" width="85%" targetType="navTab" layoutH="90">
					<thead>
						<tr align="center">
							<th width="22"><input type="checkbox" group="ids"
								class="checkboxCtrl"></th>
							<th width="80">考试名称</th>
							<th width="100">考生姓名</th>
							<th width="100" orderField="finishTime"
								<c:if test='${param.orderField=="finishTime" }'>class="${param.orderDirection}"</c:if>>提交时间</th>
							<th width="100">成绩/满分</th>
							<th width="100">查看</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator var="userPaper" value="pageResult.list" status="sta">
							<tr target="userPaperId" rel="${userPaper.id }" align="center">
								<td><input name="ids" type="checkbox"
									value='${userPaper.id }'></td>
								<td><s:property value="#userPaper.paper.title " /></td>
								<td><s:property value="#userPaper.user.userName" /> <!-- %{} --></td>
								<td><fmt:formatDate value="${userPaper.finishTime }" pattern="yyyy-MM-dd hh:mm:ss" /></td>
								<td><s:property
										value="#userPaper.scoreArr==null?'0':#userPaper.scoreArr[#userPaper.scoreArr.length-1]" />/<s:property
										value="#userPaper.paper.allScore" /></td>
								<td class="operate">
									<s:a cssClass="buttons small orange" style="text-decoration: none"
										title="得分详情 " target="navTab"
										action="GradeAction_toGradeDetailsPage?gid=%{#userPaper.id }">
										<span>得分详情<span></span>
									</s:a></td>
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