import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class StockPanel extends JPanel {
    private BusinessApplication businessApplication;
    private Hashtable<Ingredient, Integer> ingredientStock;
    private Hashtable<SushiDish, Integer> dishStock;

    private JPanel ingredientPanel;
    private JPanel dishesPanel;

    public StockPanel(BusinessApplication businessApplication) {
        super();

        this.businessApplication = businessApplication;

        ingredientPanel = new JPanel(new GridBagLayout());
        dishesPanel = new JPanel(new GridBagLayout());
        Box content = new Box(BoxLayout.X_AXIS);
        content.add(ingredientPanel);
        content.add(dishesPanel);

        setLayout(new BorderLayout());
        add(content, BorderLayout.CENTER);

        new DisplayThread().run();
    }


    private void displayFood(Food food, JPanel panel, int stock) {
        GridBagConstraints constraints = new GridBagConstraints();
        Box nameBox = new Box(BoxLayout.X_AXIS);
        Box restockBox = new Box(BoxLayout.X_AXIS);

        // INGREDIENT NAME AND CURRENT STOCK
        JLabel name = new JLabel(food.getName() + ":");
        JLabel stockLbl = new JLabel(String.valueOf(stock));

        nameBox.setAlignmentX(LEFT_ALIGNMENT);
        nameBox.add(name);
        nameBox.add(Box.createHorizontalStrut(5));
        nameBox.add(stockLbl);

        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0, 0, 3, 10);
        constraints.anchor = GridBagConstraints.LINE_START;
        panel.add(nameBox, constraints);


        // RESTOCK LEVEL WITH SAVE BUTTON
        JLabel restockLbl = new JLabel("Restock level:");

        JSpinner restockSpinner = new JSpinner(new SpinnerNumberModel(food.getRestockLevel(), 0, 100, 1));
        restockSpinner.setMinimumSize(new Dimension(40, 20));

        JButton saveRestockLvl = new JButton("Save");
        saveRestockLvl.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                food.setRestockLevel((Integer) restockSpinner.getValue());
            }
        });

        restockBox.setAlignmentX(LEFT_ALIGNMENT);
        restockBox.add(restockLbl);
        restockBox.add(Box.createHorizontalStrut(5));
        restockBox.add(restockSpinner);
        restockBox.add(Box.createHorizontalStrut(7));
        restockBox.add(saveRestockLvl);

        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0, 0, 20, 10);
        panel.add(restockBox, constraints);
    }

    private class DisplayThread extends Thread {
        public void run() {
//            while (true) {                // PROBLEM!!!
                try {
                    ingredientPanel.removeAll();
                    dishesPanel.removeAll();

                    ingredientStock = businessApplication.getIngredients();
                    dishStock = businessApplication.getDishes();

                    for (Ingredient i : ingredientStock.keySet()) {
                        displayFood(i, ingredientPanel, ingredientStock.get(i));
                    }

                    for (SushiDish d : dishStock.keySet()) {
                        displayFood(d, dishesPanel, dishStock.get(d));
                    }

                    ingredientPanel.revalidate();
                    ingredientPanel.repaint();
                    dishesPanel.revalidate();
                    dishesPanel.repaint();

                    Thread.sleep(1000);     // Wait 1s and refresh
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//            }
        }
    }
}
