package fun.oook.c18.controller;

import fun.oook.c18.domain.Product;
import fun.oook.c18.form.ProductForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ProductController
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/12 23:58
 */
@Controller
@RequestMapping("/c18")
public class ProductController {

    private static final Log log = LogFactory.getLog(ProductController.class);

    @RequestMapping("/product_input")
    public String inputProduct() {
        log.info("inputProduct called");
        return "/c18/productForm";
    }

    @RequestMapping("/product_save")
    public String saveProduct(ProductForm productForm, Model model) {
        log.info("saveProduct called");
        final Product product = new Product();
        product.setName(productForm.getName());
        product.setDescription(productForm.getDescription());
        try {
            product.setPrice(Float.parseFloat(productForm.getPrice()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        model.addAttribute("product", product);
        return "/c18/productDetails";
    }
}
