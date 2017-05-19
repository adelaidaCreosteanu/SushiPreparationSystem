import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewOrdersPanel extends JPanel {
    private ClientApplication clientApplication;
    private Customer customer;

    private static final int ROW_SPACE = 4;

    public ViewOrdersPanel(ClientApplication clientApplication, Customer customer) {
        super();

        this.clientApplication = clientApplication;
        this.customer = customer;

        setLayout(new GridBagLayout());

        ArrayList<Order> orders = customer.getOrders();

        for (int i = 1; i <= orders.size(); i++) {
            displayOrder(orders.get(i), i);
        }
    }

    private void displayOrder(Order order, int id) {
        GridBagConstraints constraints = new GridBagConstraints();

        // ID
        JLabel orderId = new JLabel("Order #" + String.valueOf(id));

        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0, 0, ROW_SPACE, 20);
        constraints.anchor = GridBagConstraints.LINE_START;
        add(orderId, constraints);

        // PRICE
        JLabel price = new JLabel("Total: " + String.valueOf(order.getTotalPrice()) + "£");

        constraints.gridy = 2;
        add(price, constraints);

        // STATUS
        JLabel status = new JLabel("Status: " + order.getStatus());

        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        add(status, constraints);

        // DISHES
        HashMap<SushiDish, Integer> dishes = order.getDishAmounts();
        for (SushiDish d : dishes.keySet()) {
            displayDish(d, dishes.get(d));
        }

        add(Box.createVerticalStrut(20));
    }

    private void displayDish(SushiDish dish, Integer amount) {
        GridBagConstraints constraints = new GridBagConstraints();

        // NAME IN BOLD FONT
        JLabel name = new JLabel(dish.getName());

        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0, 0, ROW_SPACE, 10);
        constraints.anchor = GridBagConstraints.LINE_START;
        add(name, constraints);

        // AMOUNT
        JLabel amountLbl = new JLabel("x " + String.valueOf(amount));

        constraints.gridx = 1;
        constraints.insets = new Insets(0, 0, ROW_SPACE, 10);
        add(amountLbl, constraints);

        // PRICE
        JLabel price = new JLabel(String.valueOf(dish.getPrice() * amount) + "£");

        constraints.gridx = 2;
        constraints.insets = new Insets(0, 20, ROW_SPACE, 0);
        constraints.anchor = GridBagConstraints.LINE_END;
        add(price, constraints);
    }


}
