<%-- 
    Document   : zcdl
    Created on : 2017-6-16, 12:04:29
    Author     : Lx
--%>
<%@page import="java.math.BigInteger"%>
<%@page import="java.security.MessageDigest"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.regex.Pattern"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("utf-8");
    String act = request.getParameter("act");
    Class.forName("org.mariadb.jdbc.Driver");
    Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/javalyb", "javalyb", "111111");
    if (act.equals("zc")) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        int time = (int) (System.currentTimeMillis() / 1000);
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        try {
            if (!Pattern.matches("^\\w{0,7}$", username)) {     //正则匹配
                throw new Exception("用户名格式错误");
            }
            if (!Pattern.matches("^\\w{5,15}$", password)) {
                throw new Exception("密码格式错误");
            }
            if (!Pattern.matches("^(\\w-*\\.*)+@(\\w-?)+(\\.\\w{2,})+$", email)) {
                throw new Exception("邮箱格式错误");
            }
            if (!Pattern.matches("^1[3|4|5|7|8][0-9]\\d{8}$", phone)) {
                throw new Exception("手机号格式错误");
            }
        } catch (Exception e) {
            out.print("<script>alert('" + e.getMessage() + "');window.location='index.jsp'</script>");
            out.flush();   //强制输出
            return;
        }
        String sql = "insert into user(name,password,time,email,phone)values(?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        MessageDigest md = MessageDigest.getInstance("MD5");  //jsp md5算法
        md.update(password.getBytes());
        String md5 = new BigInteger(1, md.digest()).toString(16);
        stmt.setString(2, md5);
        stmt.setInt(3, time);
        stmt.setString(4, email);
        stmt.setString(5, phone);
        try {
            int rs = stmt.executeUpdate();
            out.print("<script>alert('注册成功，点击返回');window.location='index.jsp';</script>");
            stmt.close();
            conn.close();
        } catch (Exception e) {
            out.print("<script>alert('注册失败，点击返回');window.location='index.jsp';</script>");
        }
    }
    if (act.equals("dl")) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String sql = "select * from user where name=? and password=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        MessageDigest md = MessageDigest.getInstance("MD5");  //jsp md5加密算法
        md.update(password.getBytes());
        String md5 = new BigInteger(1, md.digest()).toString(16);
        stmt.setString(2, md5);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            out.print("<script>alert('登录失败，请核对您的账号和密码');window.location='zcdl.html';</script>");
            out.flush();
            return;
        }
        out.print("<script>alert('登录成功，点击返回');window.location='index.jsp';</script>");
        session.setAttribute("username", username);
//        session.setMaxInactiveInterval(5);
//        Cookie cookie=new Cookie("username", username);//获取
//        cookie.setPath("/");
//        cookie.setMaxAge(3600);  //设置过期时间
//        response.addCookie(cookie);//在浏览器中添加cookie
        stmt.close();
        conn.close();
    }
    if (act.equals("zx")) {
//      session.setAttribute("username", null);  //注销方法一：设置用户名为空
        session.invalidate();                     //注销方法二：清空所有session信息
////         Cookie[] cookies = request.getCookies();  
////   
////    for (Cookie c : cookies) {       // cookie注销方式
////        if (c.getName().equals("username")) {  
////        c.setPath("/");            //设置cookie根目录
////        c.setMaxAge(0);        //设置过期时间
////        response.addCookie(c);   //
//        }    
//    }
        out.print("<script>alert('注销成功，点击返回');window.location='zcdl.html';</script>");
    }

%>