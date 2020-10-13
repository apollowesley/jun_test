package fun.oook.c04;

/**
 * Employee
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/11 12:09
 */
public class Employee {
    private int id;
    private String name;
    private Address address;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
