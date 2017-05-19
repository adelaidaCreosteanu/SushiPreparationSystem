import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable {
    private String username;
    private String password;
    private String address;
    private String postcode;

    private ArrayList<Order> orders;

    public Customer(String username, String password, String address, String postcode) {
        this.username = username;
        this.password = password;
        this.address = address;
        this.postcode = postcode;

        orders = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }
}
