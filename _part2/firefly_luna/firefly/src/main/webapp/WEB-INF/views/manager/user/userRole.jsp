<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css">
.yes {
	height: 32px;
	padding-left: 15px;
	color: green;
}

.no {
	height: 32px;
	padding-left: 15px;
	color: red;
}
</style>
</head>
<body>
	<div class="pageContent">
		<div layoutH="10">
		<input type="hidden" id="userId" value="${userIdSelected}"/>
			<table class="table" class="table" width="499px"  >
				<thead>
					<tr>
						<th>角色名称</th>
						<th>角色描述</th>
						<th>当前状态(可点击改变)</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach  var="childs1" items="${checkedRolesList}"  >
						<tr >
							<td>${childs1.name}</td>
							<td width="260">${childs1.description}</td>
							<td><a onclick='clicks(1,"${childs1.id}")' href="#"
								class="yes changeRolePopedom">已选中（点击删除）</a></td>
					</tr>
					</c:forEach>
					<c:forEach var="childs2" items="${notCheckRolesList}"  >
						<tr>
							<td>${childs2.name}</td>
							<td width="260">${childs2.description}</td>
							<td><a onclick='clicks(2,"${childs2.id}")' href="#"
								class="no changeRolePopedom">未选中（点击添加）</a></td>
					    </tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<script type="text/javascript">
		   var uid = $("#userId").val();
           var url = "admin/user/setUserRole?id="+uid;
           var data = null;
           var dlid = "dialoguser";
		   function clicks(type,pid) {
		        if (type==1) {
		            $.post("admin/user/delUserRoles", {userId: uid,roleId: pid},
		            function(data) {
		                if (data.statusCode == '200') {
		                    $.pdialog.reload(url,data,dlid);
		                    alertMsg.correct('删除成功！');
		                } else {
		                    alertMsg.error('操作出错！');
		                }
		            }); 
		        }else if(type==2) {
		            $.post("admin/user/addUserRoles", { userId: uid, roleId: pid },
		            function(data) {
		                if (data.statusCode == '200') {
		                	$.pdialog.reload(url,data,dlid);
		                    alertMsg.correct('添加成功！');
		                } else {
		                    alertMsg.error('操作出错！');
		                }
		            });
		        }
			}
	</script>
</body>

</html>