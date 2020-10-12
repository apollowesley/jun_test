package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.text.*;
import java.sql.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");


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

      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>留言板主页</title>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <a href=\"zcdl.html\">注册登录</a>\n");
      out.write("        <a href=\"zcdl.jsp?act=zx\">注销</a>\n");
      out.write("        <h1>留言板</h1>\n");
      out.write("        <h2>欢迎");
      out.print(username);
      out.write("</h2>\n");
      out.write("        <form id=\"ins\"  method=\"post\" action=\"domessage.jsp?act=ins\" >\n");
      out.write("            <input type=\"text\" name=\"message\" placeholder=\"请输入内容\"/>\n");
      out.write("            <!--            <input type=\"text\" name=\"name\" placeholder=\"请输入姓名\"/>-->\n");
      out.write("            <input type=\"submit\" value=\"发表\">\n");
      out.write("        </form><br>\n");
      out.write("        <form id=\"upd\" style=\"display: none;\" method=\"post\" action=\"domessage.jsp?act=upd\" >\n");
      out.write("            <input id=\"id\" type=\"hidden\" name=\"id\">\n");
      out.write("            <input id=\"mes\" type=\"text\" name=\"message\"/>\n");
      out.write("            <input type=\"submit\" value=\"修改\"/>\n");
      out.write("            <input type=\"reset\" value=\"清空\"/>\n");
      out.write("            <input class=\"deupd\" type=\"button\" value=\"取消修改\"/>\n");
      out.write("        </form>\n");
      out.write("        <h2>历史留言</h2>\n");
      out.write("        <ul>\n");
      out.write("            ");

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String message = rs.getString("message");
                    String name = rs.getString("name");
                    int time = rs.getInt("time");
                    String ip = rs.getString("ip");
            
      out.write("\n");
      out.write("            <li><span style=\"display: inline-block;display:none;\"class=\"id\">");
      out.print(id);
      out.write("</span>来自\n");
      out.write("                ");
      out.print(ip);
      out.write("\n");
      out.write("                的\n");
      out.write("                <span class=\"nam\" style=\"color:red\">");
      out.print(name);
      out.write("</span>\n");
      out.write("                在");
      out.print(sdf.format(time * 1000L));
      out.write("说：\n");
      out.write("                <br>\n");
      out.write("                <span class=\"mes\">");
      out.print(message);
      out.write("</span>\n");
      out.write("                <br>\n");
      out.write("                ");

                    if (name.equals(username)) {
                
      out.write("\n");
      out.write("                <a class=\"del\" href=\"domessage.jsp?act=del&id=");
      out.print(id);
      out.write("\" onsubmit=\"return check()\">删除</a><a class=\"upd\" >修改</a>\n");
      out.write("                ");
 }
      out.write("\n");
      out.write("            </li>\n");
      out.write("            ");
 }
      out.write("\n");
      out.write("        </ul>\n");
      out.write("        <script src=\"jquery.min.js\" type=\"text/javascript\"></script>\n");
      out.write("        <script src=\"index.js\" type=\"text/javascript\"></script>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
