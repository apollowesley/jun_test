package fun.oook.c16;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ControllerServlet
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/12 15:30
 */
//@WebServlet(name = "ControllerServlet", urlPatterns = {"/c16/product_input.action", "/c16/product_save.action"})
public class ControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 3062957092768969936L;

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

        if ("product_input.action".equals(action)) {
            // no action class, there is nothing to be done
        } else if ("product_save.action".equals(action)) {
            final ProductForm productForm = new ProductForm();
            productForm.setName(req.getParameter("name"));
            productForm.setDescription(req.getParameter("description"));
            productForm.setPrice(req.getParameter("price"));

            final Product product = new Product();
            product.setName(productForm.getName());
            product.setDescription(productForm.getDescription());

            try {
                product.setPrice(Float.parseFloat(productForm.getPrice()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            System.out.println("saving product: " + product);

            req.setAttribute("product", product);
        }

        String dispatchUrl = null;
        if ("product_input.action".equals(action)) {
            dispatchUrl = "/WEB-INF/c16/productForm.jsp";
        } else if ("product_save.action".equals(action)) {
            dispatchUrl = "/WEB-INF/c16/productDetails.jsp";
        }
        if (dispatchUrl != null) {
            req.getRequestDispatcher(dispatchUrl).forward(req, resp);
        }
    }
}
