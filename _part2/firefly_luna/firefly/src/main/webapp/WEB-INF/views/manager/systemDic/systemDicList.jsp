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
<title>系统字典表维护</title>
<script type="text/javascript">
	//弹出修改系统字典表维护页面
	function detailInfo(id) {
		if ($("#systemDicUpdate").length > 0) {
			var url = "systemDic/edit/" + id;
			var dlgId = "systemDicDetail";
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
	<form id="pagerForm" action="systemDic/systemIdDicList"
		onsubmit="return navTabSearch(this);" method="post">
		<!--【必须】value=1可以写死-->
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<!--【可选】每页显示多少条-->
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<div class="pageHeader">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<shiro:hasPermission name="systemDic_systemIdDicList">
							<td style="padding: 5px 5px 0 0">类型:</td>
							<td><input name="search_LIKE_type" type="text"
								value="${param.search_LIKE_type}" /></td>
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
		<!-- <input type="hidden" name="isAdvencedSearch" value="0" /> -->
		<div class="panelBar">
			<ul class="toolBar">
				<shiro:hasPermission name="systemDic_add">
					<li><a class="add" href="systemDic/add" target="dialog"
						mask="true" rel="dialog1"><span>添加</span></a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="systemDic_delete">
					<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids"
						href="systemDic/delete" class="delete"><span>删除</span></a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="systemDic_update">
					<li><a class="edit" href="systemDic/edit/{id}" rel="ids"
						id="systemDicUpdate" warn="请选中其中一条记录" target="dialog" mask="true"><span>修改</span></a></li>
				</shiro:hasPermission>
			</ul>
		</div>
		<table class="table" width="99%" layoutH="116">
			<thead>
				<tr>
					<th width="40"><input type="checkbox" group="ids"
						class="checkboxCtrl"></th>
					<th width="250">类型</th>
					<th>值</th>
					<th>名称</th>
					<th>描述</th>
					<th>排序号</th>
					<!-- <th>创建时间</th>
					<th>更新时间</th> -->
				</tr>
			</thead>
			<tbody>
				<c:forEach var="sysDic" items="${sysDicList.content}">
					<tr target="id" rel="${sysDic.id}"
						ondblclick="detailInfo(${sysDic.id})">
						<td><input name="ids" value="${sysDic.id}" type="checkbox"></td>
						<td>${sysDic.type}</td>
						<td>${sysDic.value}</td>
						<td>${sysDic.name}</td>
						<td>${sysDic.desc }</td>
						<td>${sysDic.sortNum }</td>
						<%-- <td><fmt:formatDate value="${sysDic.createTime }"
							pattern='yyyy-MM-dd HH:mm:ss' /></td>
						<td><fmt:formatDate value="${sysDic.updateTime }"
							pattern='yyyy-MM-dd HH:mm:ss' /></td> --%>
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