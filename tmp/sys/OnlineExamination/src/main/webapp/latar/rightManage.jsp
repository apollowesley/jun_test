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
	function selectAll() {
		if ($("#selectAll").is(':checked')) {
			$("input[name^='all']").each(function(i) {
				$(this).attr("checked", true);
			});

		} else if (!$("#selectAll").is(':checked')) {
			$("input[name^='all']").each(function(i) {
				$(this).attr("checked", false);
			});
		}
	}
</script>
</head>
<body>
	<div class="pageHeader" style="border: 1px #B8D0D6 solid">
		<form id="pagerForm" method="post"
			action="RightAction_toRightManagePage"
			onsubmit="return navTabSearch(this);">
			<input type="hidden" name="pageNum" value="1" /> <input
				type="hidden" name="numPerPage"
				value="${pageResult.page.numPerPage}" /> <input type="hidden"
				name="orderField" value="${param.orderField}" /> <input
				type="hidden" name="orderDirection" value="${param.orderDirection}" />
			<table class="searchContent">
				<tr>
					<td>权限名：<input type="text" name="rightName" id="roleName"
						value="${model.rightName }" />&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td>权限地址：<input type="text" name="rightUrl" id="roleName"
						value="${model.rightUrl }" />&nbsp;&nbsp;&nbsp;&nbsp;
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
					<li><s:a cssClass="add" action="RightAction_toAddRightPage"
						target="dialog" width="600" height="500"><span class="add">新增</span></s:a></li>
				</ul>
			</div>
			<div id="user_list_print">
				<table class="list" width="100%" targetType="navTab" layoutH="90">
					<thead>
						<tr>
							<th width="60" orderField="rightName"
								<c:if test='${param.orderField=="rightName" }'>class="${param.orderDirection}"</c:if>>权限名：</th>
							<th width="30">公共资源</th>
							<th width="50">权限地址</th>
							<th width="50">权限位</th>
							<th width="50">权限码</th>
							<th width="60">操作</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator var="right" value="pageResult.list" status="sta">
							<tr target="rightId" rel="${right.id }">
								<td>${right.rightName }</td>
								<td>${right.common?"是":"否" }</td>
								<td><s:property value="rightUrl" /></td>
								<td><s:property value="rightPos" /></td>
								<td><s:property value="rightCode" /></td>
								<td class="operate"><s:a cssClass="buttons small orange"
									style="text-decoration: none"
									action="RightAction_toUpdateRightPage?id=%{#right.id }"
									target="dialog" width="600" height="500"><span>修改</span></s:a> <s:a
									cssClass="buttons small orange" style="text-decoration: none"
									action="RightAction_deleteRight?id=%{#right.id }" title="确定要删除改权限吗?" target="ajaxTodo"><span>删除</span></s:a></td>
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