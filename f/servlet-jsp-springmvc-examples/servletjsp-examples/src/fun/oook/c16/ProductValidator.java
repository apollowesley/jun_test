package fun.oook.c16;

import java.util.ArrayList;
import java.util.List;

/**
 * ProductValidator
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/12 18:33
 */
public class ProductValidator {

    public List<String> validate(ProductForm productForm) {
        final List<String> errors = new ArrayList<>();

        final String name = productForm.getName();
        if (name == null || name.trim().isEmpty()) {
            errors.add("Product must have a name");
        }
        final String price = productForm.getPrice();
        if (price == null || price.trim().isEmpty()) {
            errors.add("Product must have a price");
        } else {
            try {
                Float.parseFloat(price);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return errors;
    }
}
