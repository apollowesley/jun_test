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
					<li><s:a cssClass="add" action="DictionaryAction_toAddClassifyPage" target="dialog" rel="dlg_page2" width="550" height="300"><span>添加类别</span></s:a ></li>
				</ul>
			</div>
			<div id="classsify">
				<table class="list" width="85%" targetType="navTab" layoutH="110">
					<thead>
						<tr align="center">
							<th width="80">序号</th>
							<th width="100">类别名称</th>
							<th width="100">类别编号</th>
							<th width="100">添加时间</th>
							<th width="100">操作</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator var="t" value="#application['paperTypes']" status="sta">
							<tr align="center">
								<td><s:property value="#sta.index+1" /> </td>
								<td><s:property value="#t.name" /> </td>
								<td><s:property value="#t.value" /> </td>
								<td><fmt:formatDate value="${t.addTime}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
								<td>
									<s:if test="#t.value!=-1">
										<s:a  title="确定删除吗" target="ajaxTodo" action="DictionaryAction_deleteClassify?id=%{#t.id }" cssClass="btnDel">删除</s:a >
									</s:if> 
									<s:a  title="修改" action="DictionaryAction_toUpdateClassify?id=%{#t.id}" target="dialog" rel="dlg_page2" width="550" height="300" cssClass="btnEdit">修改</s:a >
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
<%-- 			<div class="panelBar">
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
			</div> --%>
			</s:else>
	</div>
</body>
</html>