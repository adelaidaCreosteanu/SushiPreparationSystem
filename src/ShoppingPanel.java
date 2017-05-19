import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ShoppingPanel extends JPanel {
    private ClientApplication clientApplication;
    private Customer customer;
    private Comms comms;
    private Order order;
    private HashMap<SushiDish, Integer> dishStock;

    private JPanel headerPanel;
    private JPanel dishesPanel;
    private JPanel basketPanel;

    private static final int ROW_SPACE = 4;

    public ShoppingPanel(ClientApplication clientApplication, Customer customer) {
        super();

        this.clientApplication = clientApplication;
        this.customer = customer;
        comms = clientApplication.getComms();
        order = new Order(customer);

        headerPanel = new JPanel(new GridBagLayout());
        dishesPanel = new JPanel(new GridBagLayout());
        basketPanel = new JPanel(new GridBagLayout());

        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(dishesPanel, BorderLayout.CENTER);
        add(basketPanel, BorderLayout.EAST);

        displayHeaderPanel();
        new DisplayDishes().start();      // Thread because stock levels need to be refreshed
        displayBasketPanel();
    }

    private void displayHeaderPanel() {
        GridBagConstraints constraints = new GridBagConstraints();

        // WELCOME MESSAGE
        JLabel welcomeClient = new JLabel("Welcome, " + customer.getUsername() + "!");

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 0, ROW_SPACE, 20);
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        headerPanel.add(welcomeClient, constraints);

        // VIEW ORDERS BUTTON
        JButton viewOrders = new JButton("View orders");
        viewOrders.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clientApplication.viewOrders(customer);
            }
        });

        constraints.gridy = 1;
        constraints.insets = new Insets(0, 50, ROW_SPACE, 0);
        constraints.anchor = GridBagConstraints.LAST_LINE_END;
        headerPanel.add(viewOrders, constraints);
    }

    private void displayDish(SushiDish dish) {
        GridBagConstraints constraints = new GridBagConstraints();

        // NAME IN BOLD FONT
        JLabel name = new JLabel(dish.getName());
        Font f = name.getFont();
        name.setFont(f.deriveFont(f.getStyle() | Font.BOLD));

        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0, 0, ROW_SPACE, 10);
        constraints.anchor = GridBagConstraints.LINE_START;
        dishesPanel.add(name, constraints);

        // PRICE IN POUNDS
        JLabel price = new JLabel(String.valueOf(dish.getPrice()) + "£");

        constraints.gridx = 1;
        constraints.insets = new Insets(0, 30, ROW_SPACE, 0);
        constraints.anchor = GridBagConstraints.LINE_END;
        dishesPanel.add(price, constraints);

        // ADD TO BASKET BUTTON
        JButton addToBasket = new JButton("Add to basket");
        addToBasket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                order.addDish(dish);
                displayBasketPanel();    // Refresh
            }
        });

        constraints.gridx = 2;
        constraints.insets = new Insets(0, 10, ROW_SPACE, 0);
        dishesPanel.add(addToBasket, constraints);

        // DESCRIPTION UNDERNEATH NAME
        JLabel description = new JLabel(dish.getDescription());

        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0, 0, ROW_SPACE, 10);
        constraints.anchor = GridBagConstraints.LINE_START;
        dishesPanel.add(description, constraints);

        // CURRENT STOCK UNDERNEATH DESCRIPTION
        JLabel stock = new JLabel("Stock: " + dishStock.get(dish));

        constraints.gridy = GridBagConstraints.RELATIVE;
        dishesPanel.add(stock, constraints);

        dishesPanel.add(Box.createVerticalStrut(20));
    }

    private void displayBasketPanel() {
        basketPanel.removeAll();

        for (SushiDish dish : order.getDishAmounts().keySet()) {
            int amount = order.getDishAmounts().get(dish);
            displayBasketDish(dish, amount);
        }

        displayBasketTotal();

        basketPanel.revalidate();
        basketPanel.repaint();
    }

    private void displayBasketDish(SushiDish dish, Integer amount) {
        GridBagConstraints constraints = new GridBagConstraints();

        // NAME IN BOLD FONT
        JLabel name = new JLabel(dish.getName());
        Font f = name.getFont();
        name.setFont(f.deriveFont(f.getStyle() | Font.BOLD));

        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0, 0, ROW_SPACE, 10);
        constraints.anchor = GridBagConstraints.LINE_START;
        basketPanel.add(name, constraints);

        // AMOUNT
        JLabel amountLbl = new JLabel("x " + String.valueOf(amount));

        constraints.gridx = 1;
        constraints.insets = new Insets(0, 0, ROW_SPACE, 2);
        basketPanel.add(amountLbl, constraints);

        // MINUS
        JButton minus = new JButton("-");
        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                order.removeDish(dish);
                displayBasketPanel();    // Refresh
            }
        });

        constraints.gridx = 2;
        constraints.insets = new Insets(0, 0, ROW_SPACE, 10);
        basketPanel.add(minus, constraints);

        // PRICE
        JLabel price = new JLabel(String.valueOf(dish.getPrice() * amount) + "£");

        constraints.gridx = 3;
        constraints.insets = new Insets(0, 20, ROW_SPACE, 0);
        constraints.anchor = GridBagConstraints.LINE_END;
        basketPanel.add(price, constraints);
    }

    private void displayBasketTotal() {
        GridBagConstraints constraints = new GridBagConstraints();

        // TOTAL IN BOLD
        JLabel totalLbl = new JLabel("TOTAL");
        Font f = totalLbl.getFont();
        totalLbl.setFont(f.deriveFont(f.getStyle() | Font.BOLD));

        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0, 0, ROW_SPACE, 20);
        constraints.anchor = GridBagConstraints.LINE_START;
        basketPanel.add(totalLbl, constraints);

        // PRICE LABEL
        JLabel price = new JLabel(String.valueOf(order.getTotalPrice()) + "£");

        constraints.gridx = 3;
        constraints.insets = new Insets(0, 20, ROW_SPACE, 0);
        constraints.anchor = GridBagConstraints.LINE_END;
        basketPanel.add(price, constraints);

        // PLACE ORDER BUTTON
        JButton placeOrder = new JButton("Place order");
        placeOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                order.place();
                comms.sendMessage(Message.REQUEST_PLACE_ORDER, order);
            }
        });

        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        basketPanel.add(placeOrder, constraints);
    }

    private class DisplayDishes extends Thread {
        public void run() {
            while (true) {
                try {
                    dishesPanel.removeAll();
                    comms.sendMessage(Message.REQUEST_DISHSTOCK_HASHMAP);
                    dishStock = (HashMap<SushiDish, Integer>) comms.receiveMessage();

                    for (SushiDish dish : dishStock.keySet()) {
                        displayDish(dish);
                    }

                    dishesPanel.revalidate();
                    dishesPanel.repaint();

                    Thread.sleep(2000);     // Wait 2s and refresh page
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
