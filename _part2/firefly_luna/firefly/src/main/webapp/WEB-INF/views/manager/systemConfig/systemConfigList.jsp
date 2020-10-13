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
<title>系统参数管理</title>
<script type="text/javascript">
	function refreshSystem() {
		$.post("systemConfig/refresh", function(data) {
			if (data.statusCode == "300") {
				alertMsg.error('操作出错！');
			} else {
				alertMsg.info("刷新系统参数成功");
			}
			;
		});
	}
	//弹出修改系统参数页面
	function detailInfo(id) {
		if ($("#systemConfigUpdate").length > 0) {
			var url = "systemConfig/edit/" + id;
			var dlgId = "systemConfigDetail";
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

	<form id="pagerForm" action="systemConfig/systemConfigList"
		onsubmit="return navTabSearch(this);" method="post">
		<!--【必须】value=1可以写死-->
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<!--【可选】每页显示多少条-->
		<input type="hidden" name="numPerPage" value="${numPerPage}" />

		<div class="pageHeader">

			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<shiro:hasPermission name="systemConfig_systemConfigList">
							<td style="padding: 5px 5px 0 0">系统参数名称：<input type="text"
								name="search_LIKE_configName"
								value="${param.search_LIKE_configName}" />
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
				<li><a class="add" href="systemConfig/add" target="dialog"
					mask="true" rel="dialog1"><span>添加</span></a></li>
				<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids"
					href="systemConfig/delete" class="delete"><span>删除</span></a></li>
				<shiro:hasPermission name="systemConfig_update">
					<li><a class="edit" href="systemConfig/edit/{id}" rel="ids"
						id="systemConfigUpdate" warn="请选中其中一条记录" target="dialog"
						mask="true"><span>修改</span></a></li>
				</shiro:hasPermission>
			</ul>
		</div>
		<table class="table" width="99%" layoutH="116">
			<thead>
				<tr>
					<th width="40"><input type="checkbox" group="ids"
						class="checkboxCtrl"></th>
					<th width="80">系统参数编号</th>
					<th>系统参数名称</th>
					<th>系统参数描述</th>
					<th>系统参数别名</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach var="systemConfig" items="${systemConfigList.content}">
					<tr target="id" rel="${systemConfig.configId}"
						ondblclick="detailInfo(${systemConfig.configId})">
						<td><input name="ids" value="${systemConfig.configId}"
							type="checkbox"></td>
						<td>${systemConfig.configId}</td>
						<td>${systemConfig.configName}</td>
						<td>${systemConfig.configDesc}</td>
						<td>${systemConfig.configValue}</td>
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