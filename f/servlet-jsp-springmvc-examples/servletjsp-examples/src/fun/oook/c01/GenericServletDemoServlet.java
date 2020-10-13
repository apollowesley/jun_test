package fun.oook.c01;

import javax.servlet.*;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/10 16:37
 */
@WebServlet(name = "GenericServletDemoServlet",
        urlPatterns = {"/generic"},
        initParams = {
                @WebInitParam(name = "admin", value = "Joey"),
                @WebInitParam(name = "email", value = "joey@qq.com")
        })
public class GenericServletDemoServlet extends GenericServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        ServletConfig config = getServletConfig();
        String admin = config.getInitParameter("admin");
        String email = config.getInitParameter("email");

        res.setContentType("text/html");
        PrintWriter writer = res.getWriter();

        String html = "<html><head></head>" +
                "<body>Admin: %s<br/>Email: %s</body>" +
                "</html>";

        writer.printf(html, admin, email);
    }
}
