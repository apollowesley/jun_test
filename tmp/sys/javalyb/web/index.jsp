<%-- 
    Document   : index
    Created on : 2017-6-12, 10:46:53
    Author     : Lx
--%>
<%@page import="java.text.*"%>
<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%

    String username = (String) session.getAttribute("username");
////   session.setMaxInactiveInterval(5);//5秒内session过期
//    Cookie[] cookies = request.getCookies();  //获取cookie 并存入数组Cookie中
//    String username = "";          //设置字符串username
//    for (Cookie c : cookies) {      //      遍历cookie信息
//        if (c.getName().equals("username")) {    //查找cookie中的username
//            username = c.getValue();         //cookie中"username"信息赋值给字符串username
//        }    //若遍历结束后字符串username值仍为空，则进行登录处理
//    }
    if (username == null || username.equals("")) {
        out.print("<script>alert('请登录');window.location='zcdl.html'</script>");
        out.flush();
        return;
    }
    Class.forName("org.mariadb.jdbc.Driver");
    Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/javalyb", "javalyb", "111111");
    Statement stmt = conn.createStatement();
    String sql = "select * from javalyb";
    ResultSet rs = stmt.executeQuery(sql);
//    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf = new SimpleDateFormat();
    sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
//    sdf.format(date);
//    session.setAttribute(name, value);//设置
//    session.getAttribute(name);//获取
//    session.invalidate();//清空
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>留言板主页</title>
    </head>
    <body>
        <a href="zcdl.html">注册登录</a>
        <a href="zcdl.jsp?act=zx">注销</a>
        <h1>留言板</h1>
        <h2>欢迎<%=username%></h2>
        <form id="ins"  method="post" action="domessage.jsp?act=ins" >
            <input type="text" name="message" placeholder="请输入内容"/>
            <!--            <input type="text" name="name" placeholder="请输入姓名"/>-->
            <input type="submit" value="发表">
        </form><br>
        <form id="upd" style="display: none;" method="post" action="domessage.jsp?act=upd" >
            <input id="id" type="hidden" name="id">
            <input id="mes" type="text" name="message"/>
            <input type="submit" value="修改"/>
            <input type="reset" value="清空"/>
            <input class="deupd" type="button" value="取消修改"/>
        </form>
        <h2>历史留言</h2>
        <ul>
            <%
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String message = rs.getString("message");
                    String name = rs.getString("name");
                    int time = rs.getInt("time");
                    String ip = rs.getString("ip");
            %>
            <li><span style="display: inline-block;display:none;"class="id"><%=id%></span>来自
                <%=ip%>
                的
                <span class="nam" style="color:red"><%=name%></span>
                在<%=sdf.format(time * 1000L)%>说：
                <br>
                <span class="mes"><%=message%></span>
                <br>
                <%
                    if (name.equals(username)) {
                %>
                <a class="del" href="domessage.jsp?act=del&id=<%=id%>" onsubmit="return check()">删除</a><a class="upd" >修改</a>
                <% }%>
            </li>
            <% }%>
        </ul>
        <script src="jquery.min.js" type="text/javascript"></script>
        <script src="index.js" type="text/javascript"></script>
    </body>
</html>
