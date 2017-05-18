import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ShoppingPanel extends JPanel {
    private ClientApplication clientApplication;
    private Comms comms;
    private HashMap<SushiDish, Integer> dishStock;
    private JPanel dishesPanel;
    private JPanel basketPanel;
    private Order order;

    public ShoppingPanel(ClientApplication clientApplication, Client client) {
        super();

        this.clientApplication = clientApplication;
        comms = clientApplication.getComms();
        order = new Order(client);

        setLayout(new BorderLayout());
        dishesPanel = new JPanel(new GridBagLayout());
        basketPanel = new JPanel(new GridBagLayout());
        add(dishesPanel, BorderLayout.CENTER);
        add(basketPanel, BorderLayout.EAST);

        displayAllDishes();
        displayShoppingBasket();
    }

    private void displayAllDishes() {
        while (true) {
            try {
                comms.sendMessage(Message.GET_DISHSTOCK_HASHMAP);
                dishStock = (HashMap<SushiDish, Integer>) comms.receiveMessage();

                for (SushiDish dish : dishStock.keySet()) {
                    displayDish(dish);
                }

                Thread.sleep(2000);     // Wait 2s and refresh page

                dishesPanel.removeAll();
                dishesPanel.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayDish(SushiDish dish) {
        GridBagConstraints constraints = new GridBagConstraints();

        // NAME IN BOLD FONT
        JLabel name = new JLabel(dish.getName());
        Font f = name.getFont();
        name.setFont(f.deriveFont(f.getStyle() | Font.BOLD));

        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0, 0, 4, 10);
        constraints.anchor = GridBagConstraints.LINE_START;
        dishesPanel.add(name, constraints);

        // PRICE IN POUNDS
        JLabel price = new JLabel(String.valueOf(dish.getPrice()) + "£");

        constraints.gridx = 1;
        constraints.insets = new Insets(0, 30, 4, 0);
        constraints.anchor = GridBagConstraints.LINE_END;
        dishesPanel.add(price, constraints);

        // ADD TO BASKET BUTTON
        JButton addToBasket = new JButton("Add to basket");
        addToBasket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                order.addDish(dish);
            }
        });

        constraints.gridx = 2;
        constraints.insets = new Insets(0, 10, 4, 0);
        dishesPanel.add(addToBasket, constraints);

        // DESCRIPTION UNDERNEATH NAME
        JLabel description = new JLabel(dish.getDescription());

        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0, 0, 4, 10);
        constraints.anchor = GridBagConstraints.LINE_START;
        dishesPanel.add(description, constraints);

        // CURRENT STOCK UNDERNEATH DESCRIPTION
        JLabel stock = new JLabel("Stock: " + dishStock.get(dish));

        constraints.gridy = GridBagConstraints.RELATIVE;
        dishesPanel.add(stock, constraints);
    }

    private void displayShoppingBasket() {
        basketPanel.removeAll();
        basketPanel.repaint();

        for (SushiDish dish : order.getDishAmounts().keySet()) {
            int amount = order.getDishAmounts().get(dish);
            displayBasketDish(dish, amount);
        }

        displayBasketTotal();
    }

    private void displayBasketDish(SushiDish dish, Integer amount) {
        GridBagConstraints constraints = new GridBagConstraints();

        // NAME IN BOLD FONT
        JLabel name = new JLabel(dish.getName());
        Font f = name.getFont();
        name.setFont(f.deriveFont(f.getStyle() | Font.BOLD));

        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0, 0, 4, 10);
        constraints.anchor = GridBagConstraints.LINE_START;
        basketPanel.add(name, constraints);

        // AMOUNT
        JLabel amountLbl = new JLabel("x " + String.valueOf(amount));

        constraints.gridx = 1;
        basketPanel.add(amountLbl, constraints);

        // PRICE
        JLabel price = new JLabel(String.valueOf(dish.getPrice() * amount) + "£");

        constraints.gridx = 2;
        constraints.insets = new Insets(0, 20, 4, 0);
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
        constraints.insets = new Insets(0, 0, 4, 20);
        constraints.anchor = GridBagConstraints.LINE_START;
        basketPanel.add(totalLbl, constraints);

        // PRICE LABEL
        JLabel price = new JLabel(String.valueOf(order.getTotalPrice()) + "£");

        constraints.gridx = 2;
        constraints.insets = new Insets(0, 20, 4, 0);
        constraints.anchor = GridBagConstraints.LINE_END;
        basketPanel.add(price, constraints);

        // PLACE ORDER BUTTON
        JButton placeOrder = new JButton("Place order");
        placeOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                order.place();                                                // TODO: Place order
            }
        });

        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        basketPanel.add(placeOrder, constraints);
    }
}
