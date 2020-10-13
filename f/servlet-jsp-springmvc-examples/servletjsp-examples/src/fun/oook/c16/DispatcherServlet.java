package fun.oook.c16;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * DispatcherServlet
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/12 18:26
 */
@WebServlet(name = "ControllerServlet", urlPatterns = {"/c16/product_input.action", "/c16/product_save.action"})
public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = -4546919567921897728L;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String uri = req.getRequestURI();
        final int lastIndex = uri.lastIndexOf("/");
        final String action = uri.substring(lastIndex + 1);

        String dispatchUrl = null;
        if ("product_input.action".equals(action)) {
            final InputProductController inputProductController = new InputProductController();
            dispatchUrl = inputProductController.handleRequest(req, resp);
        } else if ("product_save.action".equals(action)) {
            final SaveProductController saveProductController = new SaveProductController();
            dispatchUrl = saveProductController.handleRequest(req, resp);
        }
        if (dispatchUrl != null) {
            req.getRequestDispatcher(dispatchUrl).forward(req, resp);
        }
    }
}
