package fun.oook.c14;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * FirstServlet
 * 动态加载示例
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/12 11:45
 */
public class FirstServlet extends HttpServlet {
private String name;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        final PrintWriter writer = resp.getWriter();

        writer.println("<html><head><title>First servlet" +
                "</title></head><body>" + name);
        writer.println("</body></head>");
    }

    public void setName(String name) {
        this.name = name;
    }
}
