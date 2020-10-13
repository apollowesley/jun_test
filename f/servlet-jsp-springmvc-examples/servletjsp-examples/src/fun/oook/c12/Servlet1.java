package fun.oook.c12;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * Servlet1
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/11 21:14
 */
@WebServlet(urlPatterns = {"/c12/servlet1"})
public class Servlet1 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("here is " + this.getClass().getName());
        final Principal userPrincipal = req.getUserPrincipal();
        System.out.println("This is " + userPrincipal.toString());

        final RequestDispatcher dispatcher = req.getRequestDispatcher("/c12/1.jsp");
        dispatcher.forward(req, resp);
    }
}
