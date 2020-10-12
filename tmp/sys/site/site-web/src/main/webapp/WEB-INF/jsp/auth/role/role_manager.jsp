<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
<title>角色管理</title>
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
						<th>角色名称</th>
						<th>角色编辑</th>
						<th>备注</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="userBody">
					<c:forEach var="r" items="${page.resultList }">
						<tr>
							<td>${r.roleName }</td>
							<td>${r.roleCode }</td>
							<td>${r.remark }</td>
							<td><fmt:formatDate value="${r.createTime }" pattern="yyyy-MM-dd HH:mm" /></td>
							<td><a href="${ctx }/user/edit?userId=${r.id}">修改</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
