<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
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
</head>
<body>
	<div class="pageHeader" style="border: 1px #B8D0D6 solid">
		<form id="pagerForm" method="post"
			action="LogAction_toLogManagePage"
			onsubmit="return navTabSearch(this);">
			<input type="hidden" name="pageNum" value="1" />
			 <input type="hidden" name="numPerPage" value="${pageResult.page.numPerPage}" />
			 <input type="hidden" name="orderField" value="${param.orderField}" />
			  <input type="hidden" name="orderDirection" value="${param.orderDirection}" />
		</form>
	</div>
	<div class="pageContent"
		style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid;">
		<s:if test="pageResult.list.isEmpty()">
		没有用户
		</s:if>
		<s:else>
			<div id="user_list_print">
				<table class="list" width="100%" targetType="navTab" layoutH="40">
					<thead>
						<tr>
							<th width="22"><input type="checkbox" group="ids"
								class="checkboxCtrl"></th>
							<th width="100" orderField="operator"
								<c:if test='${param.orderField=="operator" }'>class="${param.orderDirection}"</c:if>>管理员：</th>
							<th width="100">操作类容</th>
							<th width="100">操作结果</th>
							<th width="100"  orderField="operTime"
								<c:if test='${param.orderField=="operTime" }'>class="${param.orderDirection}"</c:if>>添加时间</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator var="log" value="pageResult.list" status="sta">
							<tr target="userId" rel="${user.id }">
								<td><input name="ids" type="checkbox" value='${user.id }'></td>
								<td>${log.operator}</td>
								<td><div class="cut">${log.content}</div></td>
								<td>${log.operResult=="success"?"成功":"失败"}</td>
								<td><fmt:formatDate value="${log.operTime}"
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
	</div>
</body>
</html>