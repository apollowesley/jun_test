<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">   
<title>模板管理  - ${websiteName}</title>
<link href="${templatePath}/css/home.css" rel="stylesheet" type="text/css"/>
<style>
table th{border-bottom:0.5px dotted;padding:3px;margin:3px;line-height:23px;height:23px;font-weight:700;}
table td{border-bottom:0.5px dotted;padding:3px;margin:3px;line-height:23px;height:23px;}
tbody tr.split{background-color:#fbfbfb;}
table a{color:#33f;padding:3px;margin:3px;}
.fid{width:40px;}
.tname{width:90px;}
.tdirectory{width:50px;}

</style>
<script>
function startEdit(obj){
	alert(obj.parentNode.nodeName);
	
}
function editSubmit(){
	
}
</script>
</head>
<body>
<table>
<tr class="split"><th></th><th>TID</th><th>模板名称</th><th>模板路径</th><th>模板版权</th><th></th></tr>
<c:forEach items="${list}" var="list" varStatus="status">

	<tr <c:if test="${status.index%2!=0}">class="split"</c:if>>
          <td><form action="<%=basePath%>Admin.yy"></form></td>
          <td class="tid">${list.tid}<input type="hidden" name="tid" value="${list.tid}" /></td>
          <td class="tname"><input type="text" name="tname" value="${list.tname}" disabled/></td>
          <td class="tdirectory"><input type="text" name="tname" value="${list.tdirectory}" disabled/></td>
          <td class="tcopyright">${list.tcopyright}</td>
          <td><a  href="Admin.yy?a=template&operate=setdefault&tid=${list.tid}&tdirectory=${list.tdirectory}">默认</a>
          <a href="Admin.yy?a=template&operate=delete&tid=${list.tid}" onclick="if(confirm('删除后不可恢复！！！')){return true;}return false;">删除</a>
         </form> </td>
      </tr></c:forEach>
      <form method="post" action="<%=basePath%>Admin.yy"><tr><td></td><td></td><td><input type="text" name="tname"/></td><td><input type="text" name="tdirectory"/></td><td><input type="text" name="tcopyright"/></td><td><input type="submit" value="添加"/><input type="hidden" name="a" value="template"/><input type="hidden" name="operate" value="add"/></td></tr></form>
</table>
</body>
</html>
