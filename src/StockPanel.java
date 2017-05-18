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
        addHeader();
        new DisplayThread().run();
    }

    private void addHeader() {
        JButton home = new JButton("Home");
        home.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                businessApplication.showHomePanel();
            }
        });

        Box header = new Box(BoxLayout.X_AXIS);
        header.add(home);
        add(header, BorderLayout.NORTH);
    }

    private void displayFood(Food food, JPanel panel, int stock) {
        GridBagConstraints constraints = new GridBagConstraints();

        // INGREDIENT NAME
        JLabel name = new JLabel(food.getName() + ":");
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0, 0, 4, 10);
        constraints.anchor = GridBagConstraints.LINE_START;
        panel.add(name, constraints);

        // CURRENT STOCK NEXT TO NAME
        JLabel stockLbl = new JLabel(String.valueOf(stock));
        constraints.gridx = 1;
        panel.add(stockLbl, constraints);

        // RESTOCK LEVEL LABEL ON NEXT LINE
        JLabel restockLbl = new JLabel("Restock level:");
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0, 0, 20, 3);
        panel.add(restockLbl, constraints);

        // RESTOCK SPINNER
        JSpinner restockSpinner = new JSpinner(new SpinnerNumberModel(food.getRestockLevel(), 0, 100, 1));
        restockSpinner.setMinimumSize(new Dimension(50, 20));
        constraints.gridx = 1;
        panel.add(restockSpinner, constraints);

        // SAVE BUTTON
        JButton saveRestockLvl = new JButton("Save");
        saveRestockLvl.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                food.setRestockLevel((Integer) restockSpinner.getValue());
            }
        });
        constraints.gridx = 2;
        constraints.insets = new Insets(0, 5, 20, 0);
        panel.add(saveRestockLvl, constraints);
    }

    private class DisplayThread extends Thread {
        public void run() {
            while (true) {
                try {
                    ingredientStock = businessApplication.getIngredients();
                    dishStock = businessApplication.getDishes();

                    for (Ingredient i : ingredientStock.keySet()) {
                        displayFood(i, ingredientPanel, ingredientStock.get(i));
                    }

                    for (SushiDish d : dishStock.keySet()) {
                        displayFood(d, dishesPanel, dishStock.get(d));
                    }

                    Thread.sleep(1000);     // Wait 1s and refresh

                    ingredientPanel.removeAll();
                    ingredientPanel.repaint();
                    dishesPanel.removeAll();
                    dishesPanel.repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
