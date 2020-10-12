<%-- 
    Document   : domessage
    Created on : 2017-6-12, 14:36:13
    Author     : Lx
--%>

<%@page import="java.sql.*"%>
<%--<%@page import="java.sql.Connection"%>--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String username = (String) session.getAttribute("username");
    if (username == null || username.equals("")) {
        out.print("<script>alert('请登录');window.location='zcdl.html'</script>");
        out.flush();
        return;
    }
    request.setCharacterEncoding("utf-8");
    String act = request.getParameter("act");
    Class.forName("org.mariadb.jdbc.Driver");
    Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/javalyb", "javalyb", "111111");
    if (act.equals("del")) {
        String id = request.getParameter("id");
        String sql = "delete from javalyb where id=? and name=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, id);
        stmt.setString(2, username);
        int rs = stmt.executeUpdate();
        if(rs == 1){
            out.print("<script>alert('删除成功，点击返回');window.location='index.jsp';</script>");
            stmt.close();
            conn.close();
        } else {
            out.print("<script>alert('删除失败，点击返回');window.location='index.jsp';</script>");
        }
    } else if (act.equals("ins")) {
        String message = request.getParameter("message");
        String name = (String) session.getAttribute("username");
        String ip = request.getRemoteAddr();     //获取IP
        int time = (int) (System.currentTimeMillis() / 1000);   // 获取时间，精确到秒
        String sql = "insert into javalyb(message,name,time,ip) values('" + message + "','" + name + "','" + time + "','" + ip + "')";
        Statement stmt = conn.createStatement();
        try {
            stmt.executeUpdate(sql);
            out.print("<script>alert('发表成功，点击返回');window.location='index.jsp';</script>");
            stmt.close();
            conn.close();
        } catch (Exception e) {
            out.print("<script>alert('发表失败，点击返回');window.location='index.jsp';</script>");
        }
    } else if (act.equals("upd")) {
        String message = request.getParameter("message");
//        String name = (String) session.getAttribute("username");
        String ip = request.getRemoteAddr();     //获取IP
        int time = (int) (System.currentTimeMillis() / 1000);
        String id = request.getParameter("id");
        String sql = "update javalyb set message=?,name=?,ip=? ,time=? where id=? and name=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, message);
        stmt.setString(2, username);
        stmt.setString(3, ip);
        stmt.setInt(4, time);
        stmt.setString(5, id);
        stmt.setString(6, username);
        try {
            int rs = stmt.executeUpdate();
            out.print("<script>alert('更新成功，点击返回');window.location='index.jsp';</script>");
            stmt.close();
            conn.close();
        } catch (Exception e) {
            out.print("<script>alert('更新失败，点击返回');window.location='index.jsp';</script>");
        }
    }


%>
<%--<%=sql%>--%>