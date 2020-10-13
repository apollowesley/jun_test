package fun.oook.c17.domain;

import java.io.Serializable;

/**
 * Product
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/12 15:26
 */
public class Product implements Serializable {

    private static final long serialVersionUID = 2870438265298121956L;

    private String name;
    private String description;
    private float price;

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
