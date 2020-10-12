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
		<form id="pagerForm" method="post" action="UserAction_toUsersPage"
			onsubmit="return navTabSearch(this);">
			<input type="hidden" name="pageNum" value="1" /> <input
				type="hidden" name="numPerPage"
				value="${pageResult.page.numPerPage}" /> <input type="hidden"
				name="orderField" value="${param.orderField}" /> <input
				type="hidden" name="orderDirection" value="${param.orderDirection}" />
			<table class="searchContent">
				<tr>
					<td>用户名称：<input type="text" name="userName" id="paperName"
						value="${model.userName }" />&nbsp;&nbsp;&nbsp;&nbsp;
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
					<li><s:a cssClass="add" action="UserAction_toAddUserPage"
						target="dialog" width="500" height="300"><span class="add">添加</span></s:a></li>
					<li><s:a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids"
						action="UserAction_bacthDeleteUsers" postType="string"
						cssClass="delete"><span>删除</span></s:a></li>
					<li><s:a cssClass="edit"
						action="UserAction_toEditUserPage?uid={userId}" target="dialog"
						width="500" height="300" warn="请选择一条信息"><span>修改</span></s:a></li>
					<li class="line">line</li>
					<li><s:a cssClass="icon"
						action="javascript:$.printBox('user_list_print')"><span>打印</span></s:a></li>
				</ul>
			</div>
			<div id="user_list_print">
				<table class="list" width="100%" targetType="navTab" layoutH="130">
					<thead>
						<tr>
							<th width="22"><input type="checkbox" group="ids"
								class="checkboxCtrl"></th>
							<th width="100" orderField="userName"
								<c:if test='${param.orderField=="userName" }'>class="${param.orderDirection}"</c:if>>用户姓名：</th>
							<th width="100" orderField="emailAddress"
								<c:if test='${param.orderField=="emailAddress" }'>class="${param.orderDirection}"</c:if>>邮箱：</th>
							<th width="100">年龄</th>
							<th width="100">单位名称</th>
							<th width="50">启用/禁用</th>
							<th width="100">添加时间</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator var="user" value="pageResult.list" status="sta">
							<tr target="userId" rel="${user.id }">
								<td><input name="ids" type="checkbox" value='${user.id }'></td>
								<td>${user.userName}</td>
								<td>${user.emailAddress }</td>
								<td>${user.userAge}</td>
								<td>${user.userUnit}</td>
								<td align="center">(已${user.isEnabled?'启用':'禁用' })<br>
									<s:a
										title="确定要%{#user.isEnabled?'禁用改用户吗？禁用后该用户将无法登陆。':'启用该用户吗？' }"
										target="ajaxTodo"
										action="UserAction_toggleUserStatus?id=%{#user.id}">${user.isEnabled?'禁用':'启用' }</s:a>
									<%-- 									<a
									title="确定要${user.isEnabled?'禁用改用户吗？禁用后该用户将无法登陆。':'启用该用户吗？' }"
									target="ajaxTodo" href="UserAction_toggleUserStatus?id=${id }">${user.isEnabled?'禁用':'启用' }</a> --%></td>
								<td><fmt:formatDate value="${user.regdate}"
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
		<div style="padding: 8px; color: green;">*注：前台用户和文件、日志等关联，为了系统安全与完整，请慎重考虑删除用户，用户有恶意行为时，可以禁用！用户启用后才能登录系统。</div>
	</div>
</body>
</html>