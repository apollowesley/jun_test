package fun.oook.c01;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Joey
 * @version 1.0.0
 * @since 2020/4/10 15:05
 */
@WebServlet(name = "MyServlet", urlPatterns = {"/my"})
public class MyServlet implements Servlet {
    private transient ServletConfig servletConfig;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.servletConfig = servletConfig;
    }

    @Override
    public ServletConfig getServletConfig() {
        return this.servletConfig;
    }

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        String servletName = servletConfig.getServletName();
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        String html = "<html><head></head>" +
                "<body>Hello from %s</body>" +
                "</html>";
        //        String html = """
        //        <html><head></head>
        //        <body>Hello from %s</body>
        //        </html>
        //""";
        writer.printf(html, servletName);
//        writer.println();
//        writer.println("٩( 'ω' )و get！");
    }

    @Override
    public String getServletInfo() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void destroy() {
    }
}
