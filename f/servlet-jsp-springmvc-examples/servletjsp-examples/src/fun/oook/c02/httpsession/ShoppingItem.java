package fun.oook.c02.httpsession;

/**
 * ShoppingItem
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/11 00:34
 */
public class ShoppingItem {

    private Product product;
    private int quantity;

    public ShoppingItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ShoppingItem{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
