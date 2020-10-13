<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="shiro" uri="http://www.springside.org.cn/tags/shiro"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>图片管理</title>
<script type="text/javascript">
	
</script>
</head>
<body>
	<form id="pagerForm" action="pic/picList" method="post">
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
			action="pic/picList">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td style="padding: 5px 5px 0 0">名称：<input type="text"
							name="search_LIKE_name" value="${param.search_LIKE_name}" />
						</td>
						<td><div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">检索</button>
								</div>
							</div></td>
					</tr>
				</table>
			</div>
	</div>
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<shiro:hasPermission name="button_add">
					<li><a class="add" href="pic/add" target="dialog" mask="true"
						rel="dialog1" width="600" height="400"><span>添加</span></a></li>
					<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids"
						href="pic/delete" class="delete"><span>删除</span></a></li>
				</shiro:hasPermission>
			</ul>
		</div>
		<table class="table" width="99%" layoutH="116">
			<thead>
				<tr>
					<th width="20"><input type="checkbox" group="ids"
						class="checkboxCtrl"></th>
					<th width="80">图片名称</th>
					<th width="200">图片存储地址</th>
					<th width="100">缩略图地址</th>
					<th width="50">图片类型</th>
					<th width="100">图片源地址</th>
					<th width="125">创建时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="pic" items="${picList}">
					<tr target="id" rel="${pic.id}">
						<td><input name="ids" value="${pic.id}" type="checkbox"></td>
						<td>${pic.name}</td>
						<td>${pic.value }</td>
						<td>${pic.thumbnail }</td>
						<td>${pic.type }</td>
						<td>${pic.source }</td>
						<td>${pic.createTime}</td>
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