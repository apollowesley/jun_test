package fun.oook.c01;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Joey
 * @version 1.0
 * @since 2020/4/10 9:27
 */
public class SimpleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        final PrintWriter writer = response.getWriter();
        writer.println("<html><head></head>" +
                "<body>Simple Servlet</body></html>");
    }
}
