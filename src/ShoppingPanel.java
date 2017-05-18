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

    public ShoppingPanel(ClientApplication clientApplication) {
        super();

        this.clientApplication = clientApplication;
        this.comms = clientApplication.getComms();

        setLayout(new BorderLayout());      // BorderLayout.EAST will be used for the shopping basket
        dishesPanel = new JPanel(new GridBagLayout());
        add(dishesPanel, BorderLayout.CENTER);

        displayAllDishes();
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
                // Add to basket or increase amount
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
}
