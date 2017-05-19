import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ClientApplication extends Application {    // extends JFrame
    private ArrayList<Customer> customers;
    private JPanel contentPane;

    public ClientApplication() {
        super("Customer Application");

        comms = new Comms(this);
        comms.start();

        getUpdatedCustomers();
        init();
    }

    public void init() {
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.add(new LogInPanel(this));

        // JFrame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(600, 600));
        setVisible(true);
    }

    private void getUpdatedCustomers() {
        comms.sendMessage(Message.REQUEST_CUSTOMERS_ARRAYLIST);
        customers = (ArrayList<Customer>) comms.receiveMessage();
    }

    public void register(String username, String password, String address, String postcode) throws Exception {
        // Check username unique
        for (Customer customer : customers) {
            if (username.equals(customer.getUsername())) throw new Exception("Username not unique!");
        }

        Customer customer = new Customer(username, password, address, postcode);
        comms.sendMessage(Message.REQUEST_REGISTER_CUSTOMER, customer);
        getUpdatedCustomers();

        contentPane.removeAll();
        contentPane.add(new ShoppingPanel(this, customer));
        contentPane.revalidate();
        contentPane.repaint();
    }

    public void logIn(String username, String password) throws Exception {
        boolean found = false;

        for (Customer customer : customers) {
            if (username.equals(customer.getUsername()) && password.equals(customer.getPassword())) {
                contentPane.removeAll();
                contentPane.add(new ShoppingPanel(this, customer));
                contentPane.revalidate();
                contentPane.repaint();

                found = true;
                break;
            }
        }

        if (!found) throw new Exception("Wrong username or password!");
    }

    public void viewOrders(Customer customer) {
        // I am sure this customer exists because this method is called by ShoppingPanel which is given a customer from this class
        // so there is no need to iterate over customers and check
        contentPane.removeAll();
        contentPane.add(new ViewOrdersPanel(this, customer));
        contentPane.revalidate();
        contentPane.repaint();
    }
}