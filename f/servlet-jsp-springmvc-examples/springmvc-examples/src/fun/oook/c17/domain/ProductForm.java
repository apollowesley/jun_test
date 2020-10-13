package fun.oook.c17.domain;

/**
 * ProductForm
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/12 15:29
 */
public class ProductForm {
    private String name;
    private String description;
    private String price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
