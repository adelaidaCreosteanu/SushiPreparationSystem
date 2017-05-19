import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrderManagerPanel extends JPanel {
    private BusinessApplication businessApplication;
    private ArrayList<Order> orders;
    private int orderNumber;

    public OrderManagerPanel(BusinessApplication businessApplication) {
        super();

        this.businessApplication = businessApplication;
        setLayout(new GridBagLayout());

        displayAllOrders();
    }

    private void displayAllOrders() {
        removeAll();
        orderNumber = 0;
        orders = businessApplication.getOrders();

        for (Order o : orders) {
            displayOrder(o);
        }

        JButton removeAll = new JButton("Remove all completed orders");
        removeAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (Order o : orders) {
                    if (o.getStatus().equals(Order.ORDER_DISPATCHED)) {
                        businessApplication.removeOrder(o);
                    }
                }

                displayAllOrders();     // Refresh
            }
        });

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(20, 0, 0, 10);
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        add(removeAll, constraints);

        revalidate();
        repaint();
    }

    private void displayOrder(Order order) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 4, 5);
        constraints.anchor = GridBagConstraints.LINE_START;

        // NUMBER
        JLabel name = new JLabel("Order #" + String.valueOf(++orderNumber));
        constraints.gridy = GridBagConstraints.RELATIVE;
        add(name, constraints);

        // STATUS
        JLabel status = new JLabel(order.getStatus());
        constraints.gridx = 1;
        add(status, constraints);

        // CLIENT
        JLabel customer = new JLabel("Customer: " + order.getCustomer().getUsername());
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.gridwidth = 2;

        // REMOVE BUTTON
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                businessApplication.removeOrder(order);
                displayAllOrders();
            }
        });
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0, 0, 40, 0);
        add(removeButton, constraints);
    }
}
