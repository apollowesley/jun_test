package fun.oook.c12;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * ProgrammaticServlet
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/12 11:03
 */
@WebServlet(urlPatterns = {"/c12/prog"})
public class ProgrammaticServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getUserPrincipal());
        if (req.authenticate(resp)) {
            resp.setContentType("text/html");
            final PrintWriter writer = resp.getWriter();
            writer.println("Welcome");
        } else {
            System.out.println("User not authenticated");
        }
    }
}
