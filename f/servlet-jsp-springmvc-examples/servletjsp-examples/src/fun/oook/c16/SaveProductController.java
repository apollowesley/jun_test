package fun.oook.c16;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * SaveProductController
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/12 18:23
 */
public class SaveProductController implements Controller {
    @Override
    public String handleRequest(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println(req.getRequestURI() + " - " + this.getClass().getName());

        final ProductForm productForm = new ProductForm();
        productForm.setName(req.getParameter("name"));
        productForm.setDescription(req.getParameter("description"));
        productForm.setPrice(req.getParameter("price"));

        final ProductValidator productValidator = new ProductValidator();
        final List<String> errors = productValidator.validate(productForm);
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("form", productForm);
            return "/WEB-INF/c16/productForm.jsp";
        }

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
        return "/WEB-INF/c16/productDetails.jsp";
    }
}
