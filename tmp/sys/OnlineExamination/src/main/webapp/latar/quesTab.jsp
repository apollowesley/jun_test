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
	width: 290px;
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
</head>
<form id="pagerForm" method="post"
	action="PaperAction_doPaperQuestionsByQuesType"
	onsubmit="return divSearch(this, 'questionBox');">
	<input type="hidden" name="pid" value="${model.id }" /> <input
		type="hidden" name="questionsType" value="${questionsType }" /> <input
		type="hidden" name="pageNum" value="1" /> <input type="hidden"
		name="numPerPage" value="${pageResult.page.numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /> <input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>
<body>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><s:a cssClass="add"
					action="PaperAction_toAddQuestionPage?pid=%{model.id }"
					target="dialog" width="1045" rel="addQuestion_dialg" height="600"><span>添加问题</span></s:a></li>
				<li><s:a cssClass="add"
					 action="IntelligentPaperAction_toIntelligentAddPaperQuestionPage?id=%{model.id }"
					 target="dialog" width="450" rel="IntelligentAddQuestion_dialg" height="480"><span>智能添加问题</span></s:a></li>
				<li><s:a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids"
					action="PaperAction_batchDeleteQuestions?pid=%{model.id }"
					postType="string" cssClass="delete"><span>删除</span></s:a></li>
				<li><s:a title="确实清空所用记录吗?"
					action="PaperAction_clearQuestion?questionsType=%{questionsType }&&pid=%{model.id }"
					target="ajaxTodo" cssClass="delete"><span>清空</span></s:a></li>
				<li><s:a cssClass="edit"
					action="PaperAction_toEditPaperHeadingPage?pid=%{model.id}&&questionsType=%{questionsType }"
					target="dialog" width="400" height="200"><span>编辑标题</span></s:a></li>
			</ul>
		</div>
		<s:if test="%{pageResult==null||pageResult.list.isEmpty()}">
		没有问题
		</s:if>
		<s:else>
			<div>
				<table class="list" width="90%" targetType="navTab" layoutH="100"
					rel="questionBox">
					<thead>
						<tr>
							<th width="22"><input type="checkbox" group="ids"
								class="checkboxCtrl"></th>
							<th width="80" id="id">问题id</th>
							<th width="100">问题名称</th>
							<th width="100">答案</th>
							<th width="100">添加时间</th>
							<th width="100">操作</th>
						</tr>
					</thead>
					<tbody id="questionsMess">
						<s:set var="type" value="questionsType" />
						<s:set var="questionList" value="pageResult.list" />
						<s:iterator var="question" value="#questionList" status="sta">
							<tr target="questionId" rel="${question.id }">
								<td><input name="ids" type="checkbox"
									value='${question.id }'></td>
								<td><s:a style="color: #000;"
									action="QuestionAction_toEditQuestionPage?qid=%{question.id }&questionPage=showQuestionPage"
									target="dialog" title="试题显示">${question.id }</s:a></td>
								<td><div class="cut">${question.title}</div></td>
								<td><s:if test="%{#type==2}">
										<s:property value="#question.answer=='right'?'正确':'错误'" />
									</s:if> <s:else>${question.answer }</s:else></td>
								<td><fmt:formatDate value="${question.insertTime}"
										pattern="yyyy-MM-dd hh:mm:ss" /></td>
								<td><s:a title="确定删除吗" target="ajaxTodo"
									action="PaperAction_deleteQuestion?qid=%{#question.id }&&pid=%{model.id }"
									cssClass="btnDel">删除</s:a> 
									<%-- <a title="修改" target="dialog" width="590" height="600" action="QuestionsAction_toEditQuestionPage?qid=${question.id }&&pid=${model.id}&&questionsType=${questionsType }" cssClass="btnEdit">修改</s:a> --%>
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
							onchange="navTabPageBreak({numPerPage:this.value}, 'questionBox')">
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
				<div class="pagination" rel="questionBox" targetType="navTab"
					totalCount="${pageResult.page.totalCount }"
					numPerPage="${pageResult.page.numPerPage }"
					currentPage="${param.pageNum }"></div>
			</div>
		</s:else>
	</div>
</body>
</html>