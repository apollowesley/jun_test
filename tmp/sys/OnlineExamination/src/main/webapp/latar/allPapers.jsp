<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="myTags" uri="../myTags.tld"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
			<table class="searchContent">
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
					<td>&nbsp;&nbsp;&nbsp;&nbsp; <select class="combox"
						name="paperType">
							<option value="">所有类型试题</option>
							<s:iterator var="t" value="#application['paperTypes']">
								<option value="${t.getValue() }"
									<c:if test="${paperType==t.getValue()}">selected = "true"</c:if>>${t.getName() }</option>
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
				<li><s:a cssClass="add" action="PaperAction_newPaper"
						target="dialog" rel="add_dig" width="500" height="400">
						<span>手动添加</span>
					</s:a></li>
				<li><s:a cssClass="add"
						action="IntelligentPaperAction_toIntelligentAddPage"
						target="dialog" rel="dlg_page2" width="500" height="600">
						<span>智能添加</span>
					</s:a></li>
				<li><s:a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids"
						action="PaperAction_bacthDeletePaper" postType="string"
						cssClass="delete">
						<span>批量删除</span>
					</s:a></li>
				<li><s:a cssClass="edit"
						action="PaperAction_toAlerPaperPage?pid={paperId}" target="dialog"
						rel="dlg_page2" width="550" height="400" warn="请选择一个用户">
						<span>修改</span>
					</s:a></li>
				<li class="line">line</li>
				<li><a class="icon" href="javascript:$.printBox('paperTable')"><span>打印</span></a></li>
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
							<th width="22"><input type="checkbox" group="ids"
								class="checkboxCtrl"></th>
							<th width="55" orderField="id"
								<c:if test='${param.orderField=="id" }'>class="${param.orderDirection}"</c:if>>试卷id</th>
							<th width="90" orderField="title"
								<c:if test='${param.orderField=="title" }'>class="${param.orderDirection}"</c:if>>试卷名称</th>
							<th width="90">试卷类型</th>
							<th width="90">考试时间</th>
							<th width="50" orderField="allScore"
								<c:if test='${param.orderField=="allScore" }'>class="${param.orderDirection}"</c:if>>试卷总分</th>
							<th width="90">添加时间</th>	
							<th width="30">审核</th>
							<th width="70">操作</th>
						</tr>
					</thead>
					<tbody id="paersData">
						<s:iterator var="paper" value="#paperList" status="sta">
							<tr target="paperId" rel="${paper.id }">
								<td><input name="ids" value=${paper.id } type="checkbox"></td>
								<td><a title="预览试卷" target="navTab"
									href="PaperAction_findQuestionsByPaperId?pid=${paper.id}&paperPage=previewPage"><s:property
											value="#paper.id" /> </a></td>
								<td><s:property value="#paper.title" /></td>
								<td><myTags:paperType name="paper"
										value="${paper.paperType }" /></td>
								<td>${paper.exanTime }分钟</td>		
								<td><s:property value="#paper.allScore" /></td>
								<td><fmt:formatDate value="${paper.addTime }" pattern="yyyy-MM-dd hh:mm:ss" /></td>		
								<td><s:a title="确定%{#paper.cloesd?'关闭':'开启'}试卷吗?"
										target="ajaxTodo"
										action="PaperAction_toggleStatus?pid=%{#paper.id }">${paper.cloesd?'关闭':'开启'}</s:a>
								</td>
								<td><s:a title="确定删除吗" target="ajaxTodo"
										action="PaperAction_deletePaPer?pid=%{#paper.id}"
										cssClass="btnDel">删除</s:a> <s:a title="设计 " target="navTab"
										action="PaperAction_toDesignPaperPage?pid=%{#paper.id}"
										cssClass="btnEdit">设计</s:a></td>
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
