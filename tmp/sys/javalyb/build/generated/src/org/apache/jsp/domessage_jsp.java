package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.*;

public final class domessage_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");
      out.write("\n");

    request.setCharacterEncoding("utf-8");
    String act = request.getParameter("act");
    Class.forName("org.mariadb.jdbc.Driver");
    Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/javalyb", "javalyb", "111111");
//    

    if (act == "del") {
        int id = Integer.parseInt(request.getParameter("id"));
        String sql = "delete from javalyb where id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
//    stmt.executeUpdate();
        try {
            stmt.executeUpdate(sql);
            out.print("<script>alert('删除成功，点击返回');window.location='index.jsp';</script>");
        } catch (Exception e) {
            out.print("<script>alert('删除失败，点击返回');window.location='index.jsp';</script>");
        }
    } else if (act == "ins") {
        String message = request.getParameter("message");
        String name = request.getParameter("name");
        String ip = request.getRemoteAddr();     //获取IP
        int time = (int) (System.currentTimeMillis() / 1000);   // 获取时间，精确到秒

        String sql = "insert into javalyb(message,name,time,ip) values('" + message + "','" + name + "','" + time + "','" + ip + "')";
        Statement stmt = conn.createStatement();
        try {
            stmt.executeUpdate(sql);
            out.print("<script>alert('发表成功，点击返回');window.location='index.jsp';</script>");
        } catch (Exception e) {
            out.print("<script>alert('发表失败，点击返回');window.location='index.jsp';</script>");
        }
    }



      out.write('\n');
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
