import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

public class IngredientManagerPanel extends JPanel {
    private BusinessApplication businessApplication;
    private StockManagement stockManagement;
    private Hashtable<Ingredient, Integer> ingredientStock;

    private JPanel ingredientPanel;
    private JPanel newIngredientPanel;
    private Ingredient newIngredient;

    public IngredientManagerPanel(BusinessApplication businessApplication) {
        super();

        this.businessApplication = businessApplication;
        stockManagement = businessApplication.getStockManagement();

        ingredientPanel = new JPanel(new GridBagLayout());
        newIngredientPanel = new JPanel(new GridBagLayout());

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JScrollPane scrollPane = new JScrollPane(ingredientPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);
        add(newIngredientPanel);

        displayAllIngredients();
        displayNewIngredientWizard();
    }

    private void displayAllIngredients() {
        ingredientPanel.removeAll();
        ingredientStock = stockManagement.getIngredients();

        for (Ingredient i : ingredientStock.keySet()) {
            displayIngredient(i);
        }

        ingredientPanel.revalidate();
        ingredientPanel.repaint();
    }

    private void displayIngredient(Ingredient ingredient) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 4, 0);
        constraints.anchor = GridBagConstraints.LINE_START;

        // NAME AND CURRENT STOCK
        JLabel name = new JLabel(ingredient.getName() + ": " + String.valueOf(ingredientStock.get(ingredient)));
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        ingredientPanel.add(name, constraints);

        // UNIT
        constraints.gridy = GridBagConstraints.RELATIVE;
        ingredientPanel.add(getUnitBox(ingredient), constraints);

        // RESTOCK LEVEL
        constraints.gridy = GridBagConstraints.RELATIVE;
        ingredientPanel.add(getRestockBox(ingredient), constraints);

        // SUPPLIER
        constraints.gridy = GridBagConstraints.RELATIVE;
        ingredientPanel.add(getSupplierBox(ingredient), constraints);

        // REMOVE BUTTON
        JButton removeDish = new JButton("Remove dish");
        removeDish.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stockManagement.removeIngredient(ingredient);
                displayAllIngredients();
            }
        });
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0, 0, 40, 0);
        ingredientPanel.add(removeDish, constraints);
    }

    private Box getUnitBox(Ingredient ingredient) {
        Box unitBox = new Box(BoxLayout.X_AXIS);
        unitBox.setAlignmentX(LEFT_ALIGNMENT);

        ArrayList<String> units = new ArrayList<>(5);
        units.add(Measurement.GRAM);
        units.add(Measurement.MILLILITERS);
        units.add(Measurement.PIECE);

        JSpinner unitSpinner = new JSpinner(new SpinnerListModel(units));
        unitSpinner.setValue(ingredient.getUnit());
        unitSpinner.setMinimumSize(new Dimension(50, 20));
        JButton saveUnitBtn = new JButton("Save");
        saveUnitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ingredient.setUnit((String) unitSpinner.getValue());
            }
        });

        unitBox.add(new JLabel("Unit:"));
        unitBox.add(Box.createHorizontalStrut(5));
        unitBox.add(unitSpinner);
        unitBox.add(Box.createHorizontalStrut(10));
        unitBox.add(saveUnitBtn);

        return unitBox;
    }

    private Box getRestockBox(Ingredient ingredient) {
        Box restockBox = new Box(BoxLayout.X_AXIS);
        restockBox.setAlignmentX(LEFT_ALIGNMENT);

        JSpinner restockSpinner = new JSpinner(new SpinnerNumberModel(ingredient.getRestockLevel(), 0, 100, 1));
        JButton saveRestockLvl = new JButton("Save");
        saveRestockLvl.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ingredient.setRestockLevel((Integer) restockSpinner.getValue());
            }
        });

        restockBox.add(new JLabel("Restock level:"));
        restockBox.add(Box.createHorizontalStrut(5));
        restockBox.add(restockSpinner);
        restockBox.add(Box.createHorizontalStrut(10));
        restockBox.add(saveRestockLvl);

        return restockBox;
    }

    private Box getSupplierBox(Ingredient ingredient) {
        Box supplierBox = new Box(BoxLayout.X_AXIS);
        supplierBox.setAlignmentX(LEFT_ALIGNMENT);

        ArrayList<String> supplierNames = new ArrayList<>();
        for (Supplier s : businessApplication.getSuppliers()) {
            supplierNames.add(s.getName());
        }

        JSpinner supplierSpinner = new JSpinner(new SpinnerListModel(supplierNames));
        supplierSpinner.setValue(ingredient.getSupplier().getName());
        supplierSpinner.setMinimumSize(new Dimension(100, 20));
        JButton saveSupplierBtn = new JButton("Save");
        saveSupplierBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ingredient.setSupplier(businessApplication.getSupplierByName((String) supplierSpinner.getValue()));
            }
        });

        supplierBox.add(new JLabel("Supplier:"));
        supplierBox.add(Box.createHorizontalStrut(5));
        supplierBox.add(supplierSpinner);
        supplierBox.add(Box.createHorizontalStrut(10));
        supplierBox.add(saveSupplierBtn);

        return supplierBox;
    }

    private void displayNewIngredientWizard() {
        newIngredientPanel.removeAll();

        newIngredient = new Ingredient();
        addIngredientDetails();

        newIngredientPanel.revalidate();
        newIngredientPanel.repaint();
    }

    private void addIngredientDetails() {
        // BOLD TITLE
        JLabel title = new JLabel("New ingredient wizard");
        Font f = title.getFont();
        title.setFont(f.deriveFont(f.getStyle() | Font.BOLD));

        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.insets = new Insets(0, 0, 20, 0);
        titleConstraints.anchor = GridBagConstraints.CENTER;
        titleConstraints.gridwidth = 2;
        newIngredientPanel.add(title, titleConstraints);

        // GENERIC CONSTRAINTS
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 4, 0);
        constraints.anchor = GridBagConstraints.LINE_START;

        // NAME
        JTextField name = new JTextField("Enter name");
        name.setMinimumSize(new Dimension(100, 30));
        constraints.gridy = 1;
        newIngredientPanel.add(name, constraints);

        // UNIT
        JLabel unitLabel = new JLabel("Unit:");
        constraints.gridy = 2;
        constraints.insets = new Insets(0, 0, 4, 5);
        newIngredientPanel.add(unitLabel, constraints);

        ArrayList<String> units = new ArrayList<>(5);
        units.add(Measurement.GRAM);
        units.add(Measurement.MILLILITERS);
        units.add(Measurement.PIECE);
        JSpinner unitSpinner = new JSpinner(new SpinnerListModel(units));
        constraints.gridx = 1;
        newIngredientPanel.add(unitSpinner, constraints);

        // RESTOCK LEVEL
        JLabel restockLabel = new JLabel("Restock level:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        newIngredientPanel.add(restockLabel, constraints);

        JSpinner restockSpinner = new JSpinner(new SpinnerNumberModel(5, 0, 100, 1));
        constraints.gridx = 1;
        newIngredientPanel.add(restockSpinner, constraints);

        // SUPPLIER
        JLabel supplierLabel = new JLabel("Supplier:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        newIngredientPanel.add(supplierLabel, constraints);

        ArrayList<String> supplierNames = new ArrayList<>();
        for (Supplier s : businessApplication.getSuppliers()) {
            supplierNames.add(s.getName());
        }
        JSpinner supplierSpinner = new JSpinner(new SpinnerListModel(supplierNames));
        constraints.gridx = 1;
        newIngredientPanel.add(supplierSpinner, constraints);

        // ADD BUTTON
        JButton addButton = new JButton("Add ingredient");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newIngredient.setName(name.getText());
                newIngredient.setUnit((String) unitSpinner.getValue());
                newIngredient.setRestockLevel((Integer) restockSpinner.getValue());
                newIngredient.setSupplier(businessApplication.getSupplierByName((String) supplierSpinner.getValue()));

                stockManagement.addNewIngredient(newIngredient);
                displayAllIngredients();
                displayNewIngredientWizard();
            }
        });

        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(10, 0, 0, 10);
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        newIngredientPanel.add(addButton, constraints);
    }
}
