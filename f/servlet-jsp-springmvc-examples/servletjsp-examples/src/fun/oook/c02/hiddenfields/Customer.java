package fun.oook.c02.hiddenfields;

/**
 * Customer
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/10 22:35
 */
public class Customer {
    private int id;
    private String name;
    private String city;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
