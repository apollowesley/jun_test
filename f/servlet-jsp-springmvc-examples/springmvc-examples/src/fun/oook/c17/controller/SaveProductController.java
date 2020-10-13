package fun.oook.c17.controller;

import fun.oook.c17.domain.Product;
import fun.oook.c17.domain.ProductForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * SaveProductController
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/12 22:16
 */
public class SaveProductController implements Controller {
    public static final Log log = LogFactory.getLog(SaveProductController.class);

    @Override
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        log.info("SaveProductController called");
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
        return new ModelAndView("/c17/productDetails");
    }
}
