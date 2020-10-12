<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page
	import="com.jake.bean.*,com.jake.dao.*,com.jake.util.*,com.jake.admin.*"%>

<%
	DBC dbc = new DBC();
	Admin admin = dbc.getAdminById(1);
	String id = request.getParameter("id");
	Message mess = null;
	if (id != null) {
		mess = dbc.getMessById(Integer.parseInt(id));
	} else {
		session.setAttribute("error", "页面请求错误，请返回");
		response.sendRedirect("/guestbook01/admin/loginError.jsp");
	}
%>
<html>
  <head>
    <title>管理员回复<%=admin.getWebtitle() %></title>
  </head>
  <body>
    <div id="head" align="center"><img src="../../images/head.jpg"></div>
    <div id="main" align="center">
      <h3>回复留言</h3><h4 align="right">留言时间：<%=mess.getPubtime() %></h4>
      <form name="form1" method="post" action="/guestbook01/admin/admin?q=rep">
        <table width="671" height="52" border="0">
          <tr>
            <td width="58"><em><strong>标题：</strong></em></td>
            <td width="597"><%=mess.getTitle() %></td>
          </tr>
          <tr>
            <td><em><strong>内容：</strong></em></td>
            <td><%=mess.getContent() %></td>
          </tr>
        </table>
        <input type="hidden" name="id" id="id" value="<%=id%>">
        <table width="669" height="32" border="0">
          <tr>
            <td width="119"><strong><em>管理员回复：</em></strong></td>
            <td width="417">
            <textarea name="repcontent" id="repcontent" cols="45" rows="5"></textarea>
            <input type="submit" name="submit" id="submit" value="  回  复  "></td>
          </tr>
        </table>
      </form>
    </div>
    
    <%@include file="../../foot.jsp" %>
  </body>
</html>
