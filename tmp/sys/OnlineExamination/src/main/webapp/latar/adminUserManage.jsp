<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="themes/css/button.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
		<form id="pagerForm" method="post"
			action="AdminUserAction_toAdminUserManagePage"
			onsubmit="return navTabSearch(this);">
			<input type="hidden" name="pageNum" value="1" /> <input
				type="hidden" name="numPerPage"
				value="${pageResult.page.numPerPage}" /> <input type="hidden"
				name="orderField" value="${param.orderField}" /> <input
				type="hidden" name="orderDirection" value="${param.orderDirection}" />
			<table class="searchContent">
				<tr>
					<td>用户名：<input type="text" name="name" id="paperName"
						value="${model.name }" />&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td><button type="submit">检索</button></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="pageContent"
		style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid;">
		<s:if test="pageResult.list.isEmpty()">
		没有用户
		</s:if>
		<s:else>
			<div class="panelBar">
				<ul class="toolBar">
					<li><s:a cssClass="add" action="AdminUserAction_toAddAdminUserPage"
						target="dialog" width="500" height="300"><span>新增</span></s:a></li>
					<li><s:a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids"
						action="AdminUserAction_bacthDeleteAdminUsers" postType="string"
						cssClass="delete"><span>删除</span></s:a></li>
					<li class="line">line</li>
				</ul>
			</div>
			<div id="user_list_print">
				<table class="list" width="100%" targetType="navTab" layoutH="90">
					<thead>
						<tr>
							<th width="22"><input type="checkbox" group="ids"
								class="checkboxCtrl"></th>
							<th width="100" orderField="name"
								<c:if test='${param.orderField=="name" }'>class="${param.orderDirection}"</c:if>>用户名：</th>
							<th width="100">是否超级管理员</th>
							<th width="100">添加时间</th>
							<th width="100">操作</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator var="user" value="pageResult.list" status="sta">
							<tr target="userId" rel="${user.id }">
								<td><input name="ids" type="checkbox" value='${user.id }'></td>
								<td>${user.name}</td>
								<td>${user.superAdmin?"是":"否"}</td>
								<td><fmt:formatDate value="${user.regdate}"
										pattern="yyyy-MM-dd hh:mm:ss" /></td>
								<td class="operate">
									<s:a cssClass="buttons small orange" style="text-decoration: none" action="AdminUserAction_toAssignRolesPage?id=%{id }" target="dialog" width="500" height="320"><span>分配角色</span></s:a>
									<s:a cssClass="buttons small orange" style="text-decoration: none" action="AdminUserAction_toAlterAdminUserPage?id=%{id }" target="dialog" width="500" height="300"><span>修改</span></s:a>
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