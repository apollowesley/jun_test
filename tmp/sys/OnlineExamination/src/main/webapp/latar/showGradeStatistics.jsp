<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div class="pageHeader" style="border: 1px #B8D0D6 solid">
		<form id="pagerForm" method="post" action="AnalyzeAction_toGradeStatisticsPage"
			onsubmit="return navTabSearch(this);">
				<input type="hidden" name="id" value="${id }">
				<input type="hidden" name="pageNum" value="1" /> 
				<input type="hidden" name="numPerPage" value="${pageResult.page.numPerPage}" /> 
				<input type="hidden" name="orderField" value="${param.orderField}" /> 
				<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
		</form>
	</div>
		<div style="margin: 15px">
				<table style="width: 90%;">
					<tbody>
						<tr>
							<td>试卷名称：${title }</td>
							<td>考试时间：${exanTime }</td>
							<td>成绩数：${gradeNum }</td>
						</tr>
					</tbody>
				</table>
			</div>
		<div class="panel" defH="80">
		<h1 align="center">总分和平均成绩</h1>
		<div>
			<table class="list" width="85%">
				<thead>
					<tr align="center">
						<th width="100"></th>
						<th width="100">单选题</th>
						<th width="100">多选题</th>
						<th width="100">判断题</th>
						<th width="100">总分</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>总分</td>
						<td>${itemNumberArr[0]*itemScoreArr[0] }</td>
						<td>${itemNumberArr[1]*itemScoreArr[1] }</td>
						<td>${itemNumberArr[2]*itemScoreArr[2] }</td>
						<td>${allScore }</td>
					</tr>
					<tr>
						<td>平均分</td>
						<td>${averageScore[0] }</td>
						<td>${averageScore[1] }</td>
						<td>${averageScore[2] }</td>
						<td>${averageScore[3] }</td>
					</tr>
				</tbody>
			</table>
		</div>
		</div>
		<hr style="height:1px;border:none;border-top:1px solid #555555;" />
		<s:if test="pageResult.list.isEmpty()">
		没用成绩信息
		</s:if>
		<s:else>
	<div class="panel" layoutH="215">
		<h1 align="center">成绩详情</h1>
		<div>
				<table class="list" width="85%" targetType="navTab" layoutH="255">
					<thead>
						<tr align="center">
							<th width="100">名次</th>
							<th width="100">考生姓名</th>
							<th width="100">单选题</th>
							<th width="100">多选题</th>
							<th width="100">判断题</th>
							<th width="100">总分</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator var="userPaper" value="pageResult.list" status="sta">
							<tr align="center">
								<td>${sta.index+1 }</td>
								<td><s:property value="#userPaper.user.userName" /> <!-- %{} --></td>
								<td>${userPaper.scoreArr[0] }</td>
								<td>${userPaper.scoreArr[1] }</td>
								<td>${userPaper.scoreArr[2] }</td>
								<td>${userPaper.allscore }</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
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