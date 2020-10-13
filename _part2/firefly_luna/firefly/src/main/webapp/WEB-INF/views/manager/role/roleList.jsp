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
<title>角色管理</title>
<script type="text/javascript">
	//弹出修改角色管理页面
	function detailInfo(id) {
		if ($("a[name='roleUpdate']").length > 0) {
			var url = "roleManage/edit?id=" + id;
			var dlgId = "roleManageDetail";
			var title = "编辑";
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
	<form id="pagerForm" action="roleManage/roleList" method="post">
		<!--【必须】value=1可以写死-->
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<!--【可选】每页显示多少条-->
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<!--【查询条件】角色名字-->
		<input type="hidden" name="search_LIKE_name"
			value="${param.search_LIKE_name}" />
	</form>

	<div class="pageHeader">
		<form onsubmit="return navTabSearch(this);"
			action="roleManage/roleList">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<shiro:hasPermission name="roleManage_roleList">
							<td style="padding: 5px 5px 0 0">角色名称：<input type="text"
								name="search_LIKE_name" value="${param.search_LIKE_name}" />
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
		</form>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<shiro:hasPermission name="role_add">
					<li><a class="add" href="roleManage/add" target="dialog"
						mask="true" rel="dialog1"><span>添加</span></a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="role_update">
					<li><a class="edit" href="roleManage/edit?id={id}"
						rel="dialog1" id="systemConfigUpdate" warn="请选中其中一条记录"
						target="dialog" mask="true"><span>编辑</span></a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="role_delete">
					<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids"
						href="roleManage/delete" class="delete"><span>删除</span></a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="role_assign_menu">
					<li><a class="edit" title="配置系统菜单" target="dialog" mask="true"
						rel="dialog2" height="560" href="roleManage/getMenu?id={id}"><span>配置菜单权限</span></a>
					</li>
				</shiro:hasPermission>
				</td>
			</ul>
		</div>
		<table class="table" width="99%" layoutH="116">
			<thead>
				<tr>
					<th width="40"><input type="checkbox" group="ids"
						class="checkboxCtrl"></th>
					<th>角色名称</th>
					<th>角色描述</th>
					<th>创建时间</th>
					<th>创建人</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="role" items="${role.content}">
					<tr target="id" rel="${role.id}"
						ondblclick="detailInfo(${role.id})">
						<td><input name="ids" value="${role.id}" type="checkbox"></td>
						<td>${role.name}</td>
						<td>${role.description}</td>
						<td><fmt:formatDate
							value="${role.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${role.createUser}</td>
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