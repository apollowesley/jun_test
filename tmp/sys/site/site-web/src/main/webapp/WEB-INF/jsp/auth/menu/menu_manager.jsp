<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
<title>菜单管理</title>
<script type="text/javascript">
  
</script>
</head>
<body>
	<div class="container">
		<div class="well">
			<form class="form-inline">
				<div class="form-group">
					<label for="exampleInputName2">Name</label> <input type="text" class="form-control" id="exampleInputName2" placeholder="Jane Doe">
				</div>
				<div class="form-group">
					<label for="exampleInputEmail2">Email</label> <input type="email" class="form-control" id="exampleInputEmail2" placeholder="jane.doe@example.com">
				</div>
				<button type="submit" class="btn btn-default">Send invitation</button>
			</form>
		</div>
		<div class="row">
			<table class="table table-condensed">
				<thead>
					<tr>
						<th>菜单名称</th>
						<th>编码</th>
						<th>URL</th>
						<th>等级</th>
						<th>显示顺序</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="userBody">
					<c:forEach var="m" items="${pers }">
						<tr>
							<td>${m.menuName }</td>
							<td>${m.menuCode }</td>
							<td>${m.url }</td>
							<td>${m.lev }</td>
							<td>${m.sort }</td>
							<td><fmt:formatDate value="${m.createTime }" pattern="yyyy-MM-dd HH:mm" /></td>
							<td><a href="${ctx }/user/edit?userId=${u.id}">修改</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
