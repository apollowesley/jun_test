<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
.cut { /*----  题目超出部分自动省略  -----*/
	width: 270px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
	-o-text-overflow: ellipsis;
	-icab-text-overflow: ellipsis;
	-khtml-text-overflow: ellipsis;
	-moz-text-overflow: ellipsis;
	-webkit-text-overflow: ellipsis !important;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
	var pid=$("#pid").val();
	$("#addPaperQuestion").attr('href','PaperAction_addPaperQuestion?pid='+pid); 
});
</script>
</head>
<form id="pagerForm" method="post" action="QuestionAction_doTopic?questionPage=questionsTablePage"
	onsubmit="return divSearch(this, 'questionTable');">
		<input type="hidden" name="questionsType" value="${model.questionsType }"> 
		<input type="hidden" name="title" value="${model.title }"> 
		<input type="hidden" name="tid" value="${tid }">
		<input type="hidden" name="pageNum" value="1" /> 
		<input type="hidden" name="numPerPage" value="${pageResult.page.numPerPage}" />
		<input type="hidden" name="orderField" value="${param.orderField}" /> 
		<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>
<body>
	<div class="pageHeader" style="border: 1px #B8D0D6 solid">
		<form target="navTab" rel="pageForm" action="QuestionAction_doTopic?questionPage=questionsTablePage"
			method="post" onsubmit="return divSearch(this, 'questionTable');">
			<input type="hidden" name="tid" value="${tid }">
			<table class="searchContent">
				<tr>
					<td>问题名称：<input type="text" name="title" id="paperName"
						value="${model.title }" />&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td><select class="combox" name="questionsType">
							<option value="0"
								<c:if test="${questionsType==0}">selected = "true"</c:if>>单选题</option>
							<option value="1"
								<c:if test="${questionsType==1}">selected = "true"</c:if>>多选题</option>
							<option value="2"
								<c:if test="${questionsType==2}">selected = "true"</c:if>>判断题</option>
					</select></td>
					<td><button type="submit">检索</button></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="pageContent"
		style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="addPaperQuestion" title="确实要添加这些试题到试卷吗?" target="selectedTodo" targetType="dialog" rel="ids"
					href="PaperAction_addPaperQuestion?pid=${pid}"
					postType="string" class="add"><span>添加这些到试卷</span></a></li>
			</ul>
		</div>
		<s:if test="pageResult==null or pageResult.list.isEmpty()">
			<p>没有问题</p>
		</s:if>
		<s:else>
			<div>
				<div id="questions_list_print">
					<table class="list" width="90%"  targetType="dialog" layoutH="91" rel="questionTable">
						<thead>
							<tr>
								<th width="22"><input type="checkbox" group="ids"
									class="checkboxCtrl"></th>
								<th width="200" orderField="title"
									<c:if test='${param.orderField=="title" }'>class="${param.orderDirection}"</c:if>>问题名称</th>
								<th width="100">答案</th>
								<th width="100">模板名称</th>
								<th width="100">添加时间</th>
							</tr>
						</thead>
						<tbody>
							<s:set var="type" value="questionsType" />

							<s:iterator var="question" value="pageResult.list" status="sta">
								<tr target="questionId" rel="${question.id }">
									<td><input name="ids" type="checkbox"
										value='${question.id }'></td>
									<td><div class="cut"><s:a style="color: #000;"
										action="QuestionAction_toEditQuestionPage?qid=%{#question.id }&questionPage=showQuestionPage"
										target="dialog" mask="true" title="试题显示">${question.title}</s:a></div></td>
									<td><s:if test="%{#type==2}">
											<s:property value="#question.answer=='right'?'正确':'错误'" />
										</s:if> <s:else>${question.answer }</s:else></td>
										<td>${question.questionTemplate.name }</td>
									<td><fmt:formatDate value="${question.insertTime}"
											pattern="yyyy-MM-dd hh:mm:ss" /></td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>
				<div class="panelBar">
					<div class="pages">
						<div class="pages">
							<span>显示</span> <select name="numPerPage"
								onchange="dialogPageBreak({numPerPage:this.value}, 'questionTable')">
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
					<div class="pagination" rel="questionTable" targetType="dialog"
						totalCount="${pageResult.page.totalCount }"
						numPerPage="${pageResult.page.numPerPage }"
						currentPage="${param.pageNum }"></div>
				</div>
			</div>
		</s:else>
	</div>

</body>
</html>