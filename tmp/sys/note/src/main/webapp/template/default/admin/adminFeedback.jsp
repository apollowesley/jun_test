<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>后台管理用户反馈界面  - ${websiteName}</title>

<link href="${templatePath}/css/home.css" rel="stylesheet" type="text/css"/>
<style>
table th{border-bottom:0.5px dotted;padding:3px;margin:3px;line-height:23px;height:23px;font-weight:700;}
table td{border-bottom:0.5px dotted;padding:3px;margin:3px;line-height:23px;height:23px;}
tbody tr.split{background-color:#fbfbfb;}
table a{color:#33f;padding:3px;margin:3px;}
.opr{width:80px;}
.fid{width:40px;}
.username{width:40px;}
.fdate{width:80px;}
.contents .rcontents{min-width:80px;}
.rcontents{color:#e33;}
</style>
</head>
<body>
<table>
<tr class="split"><th></th><th>FID</th><th>用户名</th><th>反馈时间</th><th>反馈内容</th><th>回复内容</th><th></th></tr>
<c:forEach items="${list}" var="list" varStatus="status"><tr <c:if test="${status.index%2!=0}">class="split"</c:if>>
          <td></td>
          <td class="fid">${list.fid}</td>
          <td class="username">${list.username}</td>
          <td class="fdate">${list.fdate}</td>
          <td class="contents">${list.contents}</td>
          <td class="rcontents">${list.rcontents}</td>
          <td class="opr"><a href="Admin.yy?a=feedback&operate=detail&fid=${list.fid}">详情</a>
          <a href="Admin.yy?a=feedback&operate=delete&fid=${list.fid}" onclick="if(confirm('删除后不可恢复！！！')){return true;}return false;">删除</a>
          </td>
      </tr></c:forEach>
</table>
</body>
</html>