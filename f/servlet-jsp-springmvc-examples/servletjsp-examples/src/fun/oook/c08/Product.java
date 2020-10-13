package fun.oook.c08;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * Product
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/11 15:14
 */
public class Product implements HttpSessionBindingListener {
    private String id;
    private String name;
    private double price;

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        final String name = event.getName();
        System.out.println(name + " valueBound");
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        final String name = event.getName();
        System.out.println(name + " valueUnbound");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
