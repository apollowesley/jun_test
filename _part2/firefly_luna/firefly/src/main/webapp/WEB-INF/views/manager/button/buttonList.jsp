<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="shiro" uri="http://www.springside.org.cn/tags/shiro"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>菜单管理</title>
<script type="text/javascript">
	//弹出修改菜单管理页面
	function detailInfo(id) {
		if ($("#buttonUpate").length > 0) {
			var url = "sysButton/edit/" + id;
			var dlgId = "systemButtonDetail";
			var title = "修改";
			var options = {
				width : 580,
				height : 300,
				mask : true,
				closeOnEscape : true
			};
			$.pdialog.open(url, dlgId, title, options);
		}
	}
</script>
</head>
<body>

	<form id="pagerForm" action="sysButton/buttonList"
		onsubmit="return navTabSearch(this);" method="post">
		<!--【必须】value=1可以写死-->
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<!--【可选】每页显示多少条-->
		<input type="hidden" name="numPerPage" value="${numPerPage}" />

		<div class="pageHeader">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<shiro:hasPermission name="sysButton_buttonList">
							<td style="padding: 5px 5px 0 0">按钮代码：<input type="text"
								name="search_LIKE_buttonCode"
								value="${param.search_LIKE_buttonCode}" />
							</td>
							<td><div class="buttonActive">
									<div class="buttonContent">
										<button type="submit">检索</button>
									</div>
								</div></td>
						</shiro:hasPermission>
					</tr>
				</table>
			</div>
		</div>
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<shiro:hasPermission name="button_add">
					<li><a class="add" href="sysButton/add" target="dialog"
						mask="true" rel="dialog1"><span>添加</span></a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="button_delete">
					<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids"
						href="sysButton/delete" class="delete"><span>删除</span></a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="button_update">
					<li><a class="edit" href="sysButton/edit/{id}" rel="ids"
						id="buttonUpate" warn="请选中其中一条记录" target="dialog" mask="true"><span>修改</span></a></li>
				</shiro:hasPermission>
			</ul>
		</div>
		<table class="table" width="99%" layoutH="116">
			<thead>
				<tr>
					<th width="40"><input type="checkbox" group="ids"
						class="checkboxCtrl"></th>
					<th>按钮代码</th>
					<th>按钮名称</th>
					<th>所属菜单</th>
					<th>创建时间</th>
					<th>描述</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="button" items="${button.content}">
					<tr target="id" rel="${button.buttonCode}"
						ondblclick="detailInfo('${button.buttonCode}')">
						<td><input name="ids" value="${button.buttonCode}"
							type="checkbox"></td>
						<td width="20%">${button.buttonCode}</td>
						<td>${button.buttonName }</td>
						<td>${button.menu.name }</td>
						<td>${button.createTime }</td>
						<td width="40%">${button.buttonDesc }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div class="panelBar">
			<div class="pages">
				<span>显示</span> <select id="numPerPage" name="numPerPage"
					onchange="navTabPageBreak({numPerPage:this.value})">
					<option value="20" <c:if test="${numPerPage==20}">selected</c:if>>20</option>
					<option value="50" <c:if test="${numPerPage==50}">selected</c:if>>50</option>
					<option value="100" <c:if test="${numPerPage==100}">selected</c:if>>100</option>

				</select> <span>条，共${totalCount}条</span>
			</div>
			<div id="pagination" class="pagination" targetType="navTab"
				totalCount="${totalCount}" numPerPage="${numPerPage}"
				pageNumShown="10" currentPage="${pageNum}"></div>
		</div>
	</div>
</body>
</html>