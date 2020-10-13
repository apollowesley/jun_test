<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../taglib.jsp" %>
<html>
<head>
<meta charset="utf-8">
<title>学生管理</title>
<body>

<form id="frm" action="${ctx}jsp/studentManager.do" method="post">
	姓名(模糊查询):<input name="schName" type="text" value="${searchData.schName}">
	学号(全查询):<input name="schStuNo" type="text" value="${searchData.schStuNo}">
	<input id="pageIndex" name="pageIndex" type="hidden" value="${resultHolder.currentPageIndex}">
	<button class="btn btn-primary" onclick="searchStu()">查询</button>
</form>
<hr>
<table>
	<thead>
		<tr>
			<th></th>
			<th>学号</th>
			<th>姓名</th>
			<th>性别</th>
			<th>名族</th>
			<th>家庭地址</th>
			<th>手机号</th>
			<th>出生日期</th>
			<th>入学时间</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
	<!-- 
	查看durcframework.test.student.controller.StudentJspController类
	 -->
		<c:forEach items="${resultHolder.rows}" var="stu" varStatus="stat">
			<tr
			<c:if test="${stat.index%2==0}">
				class="odd"
			</c:if>
			>
				<td>${stat.index+1}</td>
				<td>${stu.stuNo}</td>
				<td>${stu.name}</td>
				<td>${stu.genderStr}</td>
				<td>${stu.nationality}</td>
				<td>${stu.address}</td>
				<td>${stu.mobile}</td>
				<td><fmt:formatDate value="${stu.birthday}" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${stu.registDate}" pattern="yyyy-MM-dd"/></td>
				<td>
					<a href="${ctx}jsp/editStudent.do?id=${stu.id}">编辑</a>
					|
					<a href="#" onclick="del(${stu.id},'${stu.name}');return false;">删除</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="10">
				<a href="javascript:void(0)" onclick="goPage(${resultHolder.firstPageIndex})">首页</a>
				<a href="javascript:void(0)" onclick="goPage(${resultHolder.prePageIndex})">上一页</a>
				<a href="javascript:void(0)" onclick="goPage(${resultHolder.nextPageIndex})">下一页</a>
				<a href="javascript:void(0)" onclick="goPage(${resultHolder.lastPageIndex})">尾页</a>
				|
				第 <c:out value="${resultHolder.currentPageIndex}"/>/<c:out value="${resultHolder.pageCount}"/> 页 |
				共<c:out value="${resultHolder.total}"/>条记录 
			</td>
		</tr>
	</tfoot>
	</table>
						
<script type="text/javascript">
	function goPage(index){
		document.getElementById('pageIndex').value = index;
		document.getElementById('frm').submit();
	}
	
	function searchStu(){
		goPage(1);
	}
	
	function del(id,name){
		var isTrue = confirm('确定要删除('+name+')吗?');
		if(isTrue){
			location.href = 'delStu.do?id='+id;
		}
	}
</script>
								
</body>
</html>