import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class DishManagerPanel extends JPanel {
    private StockManagement stockManagement;
    private Hashtable<SushiDish, Integer> dishStock;
    private Hashtable<Ingredient, Integer> ingredientStock;
    private SushiDish newDish;

    private JPanel dishPanel;
    private JPanel newDishPanel;


    public DishManagerPanel(StockManagement stockManagement) {
        super();

        this.stockManagement = stockManagement;
        dishPanel = new JPanel(new GridBagLayout());
        newDishPanel = new JPanel(new GridBagLayout());

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JScrollPane scrollPane = new JScrollPane(dishPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);
        add(newDishPanel);

        displayAllDishes();
        displayNewDishWizard();
    }

    private void displayAllDishes() {
        dishPanel.removeAll();
        dishStock = stockManagement.getDishes();

        for (SushiDish d : dishStock.keySet()) {
            displayDish(d);
        }

        dishPanel.revalidate();
        dishPanel.repaint();
    }

    private void displayDish(SushiDish dish) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 4, 0);
        constraints.anchor = GridBagConstraints.LINE_START;

        // NAME AND CURRENT STOCK
        JLabel name = new JLabel(dish.getName() + ": " + String.valueOf(dishStock.get(dish)));
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        dishPanel.add(name, constraints);

        // PRICE
        constraints.gridy = GridBagConstraints.RELATIVE;
        dishPanel.add(getPriceBox(dish), constraints);

        // RESTOCK LEVEL
        constraints.gridy = GridBagConstraints.RELATIVE;
        dishPanel.add(getRestockBox(dish), constraints);

        // DESCRIPTION
        constraints.gridy = GridBagConstraints.RELATIVE;
        dishPanel.add(getDescriptionBox(dish), constraints);

        // REMOVE BUTTON
        JButton removeDish = new JButton("Remove dish");
        removeDish.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stockManagement.removeDish(dish);
                displayAllDishes();
            }
        });
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0, 0, 40, 0);
        dishPanel.add(removeDish, constraints);
    }

    private Box getPriceBox(SushiDish dish) {
        JSpinner priceSpinner = new JSpinner(new SpinnerNumberModel(dish.getPrice(), 1, 100, 1));

        JButton savePrice = new JButton("Save");
        savePrice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dish.setPrice((Integer) priceSpinner.getValue());
            }
        });

        Box priceBox = new Box(BoxLayout.X_AXIS);
        priceBox.setAlignmentX(LEFT_ALIGNMENT);
        priceBox.add(new JLabel("Price:"));
        priceBox.add(Box.createHorizontalStrut(5));
        priceBox.add(priceSpinner);
        priceBox.add(Box.createHorizontalStrut(10));
        priceBox.add(savePrice);

        return priceBox;
    }

    private Box getRestockBox(SushiDish dish) {
        JSpinner restockSpinner = new JSpinner(new SpinnerNumberModel(dish.getRestockLevel(), 0, 100, 1));

        JButton saveRestockLvl = new JButton("Save");
        saveRestockLvl.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dish.setRestockLevel((Integer) restockSpinner.getValue());
            }
        });

        Box restockBox = new Box(BoxLayout.X_AXIS);
        restockBox.setAlignmentX(LEFT_ALIGNMENT);

        restockBox.add(new JLabel("Restock level:"));
        restockBox.add(Box.createHorizontalStrut(5));
        restockBox.add(restockSpinner);
        restockBox.add(Box.createHorizontalStrut(10));
        restockBox.add(saveRestockLvl);

        return restockBox;
    }

    private Box getDescriptionBox(SushiDish dish) {
        JTextField description = new JTextField(dish.getDescription());
        description.setMaximumSize(new Dimension(300, 30));

        JButton saveDescription = new JButton("Save");
        saveDescription.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dish.setDescription(description.getText());
            }
        });

        Box descriptionBox = new Box(BoxLayout.X_AXIS);
        descriptionBox.setAlignmentX(LEFT_ALIGNMENT);

        descriptionBox.add(new JLabel("Description:"));
        descriptionBox.add(Box.createHorizontalStrut(5));
        descriptionBox.add(description);
        descriptionBox.add(Box.createHorizontalStrut(10));
        descriptionBox.add(saveDescription);

        return descriptionBox;
    }

    private void displayNewDishWizard() {
        newDishPanel.removeAll();

        newDish = new SushiDish();
        addDishDetails();

        newDishPanel.revalidate();
        newDishPanel.repaint();
    }

    private void addDishDetails() {
        // BOLD TITLE
        JLabel title = new JLabel("New dish wizard");
        Font f = title.getFont();
        title.setFont(f.deriveFont(f.getStyle() | Font.BOLD));

        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.insets = new Insets(0, 0, 20, 0);
        titleConstraints.anchor = GridBagConstraints.CENTER;
        titleConstraints.gridwidth = 2;
        newDishPanel.add(title, titleConstraints);

        // GENERIC CONSTRAINTS
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 4, 0);
        constraints.anchor = GridBagConstraints.LINE_START;

        // NAME
        JTextField name = new JTextField("Enter name");
        name.setMinimumSize(new Dimension(100, 30));
        constraints.gridy = 1;
        newDishPanel.add(name, constraints);

        // DESCRIPTION
        JTextField description = new JTextField("Enter description");
        description.setMinimumSize(new Dimension(300, 30));
        constraints.gridy = 2;
        newDishPanel.add(description, constraints);

        // PRICE
        JLabel priceLabel = new JLabel("Price:");
        constraints.gridy = 3;
        constraints.insets = new Insets(0, 0, 4, 5);
        newDishPanel.add(priceLabel, constraints);

        JSpinner priceSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        constraints.gridx = 1;
        newDishPanel.add(priceSpinner, constraints);

        // RESTOCK LEVEL
        JLabel restockLabel = new JLabel("Restock level:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.insets = new Insets(0, 0, 10, 5);      // Larger gap between restock level and list of ingredients
        newDishPanel.add(restockLabel, constraints);

        JSpinner restockSpinner = new JSpinner(new SpinnerNumberModel(5, 0, 100, 1));
        constraints.gridx = 1;
        newDishPanel.add(restockSpinner, constraints);

        // INGREDIENTS
        ingredientStock = stockManagement.getIngredients();
        constraints.gridx = 0;
        constraints.insets = new Insets(0, 0, 4, 0);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridwidth = 2;

        for (Ingredient i : ingredientStock.keySet()) {
            constraints.gridy = GridBagConstraints.RELATIVE;
            newDishPanel.add(getIngredientBox(i), constraints);
        }

        // ADD BUTTON
        JButton addButton = new JButton("Add dish");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newDish.setName(name.getText());
                newDish.setDescription(description.getText());
                newDish.setPrice((Integer) priceSpinner.getValue());
                newDish.setRestockLevel((Integer) restockSpinner.getValue());
                stockManagement.addNewDish(newDish);

                displayAllDishes();
                displayNewDishWizard();
            }
        });

        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(10, 0, 0, 10);
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        newDishPanel.add(addButton, constraints);
    }

    private Box getIngredientBox(Ingredient ingredient) {
        JLabel name = new JLabel(ingredient.getName());
        JSpinner amount = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        JLabel unit = new JLabel(ingredient.getUnit());

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newDish.addIngredient(ingredient, (Integer) amount.getValue());         // SOMETHING IS NOT RIGHT HERE
            }
        });

        Box box = new Box(BoxLayout.X_AXIS);
        box.setAlignmentX(CENTER_ALIGNMENT);

        box.add(name);
        box.add(Box.createHorizontalStrut(5));
        box.add(amount);
        box.add(Box.createHorizontalStrut(3));
        box.add(unit);
        box.add(Box.createHorizontalStrut(10));
        box.add(addButton);

        return box;
    }









}
