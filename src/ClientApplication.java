import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ClientApplication extends JFrame implements Application {
    private ArrayList<Customer> customers;
    private Comms comms;

    public ClientApplication() {
        super("Customer Application");

        customers = new ArrayList<>();
        comms = new Comms(this);
        init();
    }

    public Comms getComms() {
        return comms;
    }

    public void init() {
        setContentPane(new LogInPanel(this));

        // JFrame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(600, 600));
        setVisible(true);
    }

    public void register(String username, String password, String address, String postcode) throws Exception {
        // Check username unique
        for (Customer customer : customers) {
            if (username.equals(customer.getUsername())) throw new Exception("Username not unique!");
        }

        Customer customer = new Customer(username, password, address, postcode);
        customers.add(customer);                                                // TODO: notify business application to add a new customer
        setContentPane(new ShoppingPanel(this, customer));
    }

    public void logIn(String username, String password) throws Exception {
        boolean found = false;

        for (Customer customer : customers) {
            if (username.equals(customer.getUsername()) && password.equals(customer.getPassword())) {
                setContentPane(new ShoppingPanel(this, customer));
                found = true;
                break;
            }
        }

        if (!found) throw new Exception("Wrong username or password!");
    }

    public void viewOrders(Customer customer) {
        // I am sure this customer exists because this method is called by ShoppingPanel which is given a customer from this class
        // so there is no need to iterate over customers and check
        this.setContentPane(new ViewOrdersPanel(this, customer));
    }
}