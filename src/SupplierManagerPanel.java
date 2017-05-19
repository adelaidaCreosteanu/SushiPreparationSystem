import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SupplierManagerPanel extends JPanel {
    private BusinessApplication businessApplication;
    private ArrayList<Supplier> suppliers;
    private Supplier newSupplier;

    private JPanel suppliersPanel;
    private JPanel newSupplierPanel;

    public SupplierManagerPanel(BusinessApplication businessApplication) {
        super();
        this.businessApplication = businessApplication;

        suppliersPanel = new JPanel(new GridBagLayout());
        newSupplierPanel = new JPanel(new GridBagLayout());

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JScrollPane scrollPane = new JScrollPane(suppliersPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);
        add(newSupplierPanel);

        displayAllSuppliers();
        displayNewSupplierWizard();
    }

    private void displayAllSuppliers() {
        suppliersPanel.removeAll();
        suppliers = businessApplication.getSuppliers();

        for (Supplier s : suppliers) {
            displaySupplier(s);
        }

        suppliersPanel.revalidate();
        suppliersPanel.repaint();
    }

    private void displaySupplier(Supplier supplier) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 4, 0);
        constraints.anchor = GridBagConstraints.LINE_START;

        // NAME
        JLabel name = new JLabel(supplier.getName());
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        suppliersPanel.add(name, constraints);

        // DISTANCE
        JLabel distance = new JLabel("Distance: " + supplier.getDistance() + " km");
        constraints.gridy = GridBagConstraints.RELATIVE;
        suppliersPanel.add(distance, constraints);

        // REMOVE BUTTON
        JButton removeSupplier = new JButton("Remove supplier");
        removeSupplier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                businessApplication.removeSupplier(supplier);
                displayAllSuppliers();
            }
        });
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0, 0, 40, 0);
        suppliersPanel.add(removeSupplier, constraints);
    }

    private void displayNewSupplierWizard() {
        newSupplierPanel.removeAll();

        newSupplier = new Supplier();
        addSupplierDetails();

        newSupplierPanel.revalidate();
        newSupplierPanel.repaint();
    }

    private void addSupplierDetails() {
        // BOLD TITLE
        JLabel title = new JLabel("New supplier wizard");
        Font f = title.getFont();
        title.setFont(f.deriveFont(f.getStyle() | Font.BOLD));

        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.insets = new Insets(0, 0, 20, 0);
        titleConstraints.anchor = GridBagConstraints.CENTER;
        titleConstraints.gridwidth = 3;
        newSupplierPanel.add(title, titleConstraints);

        // GENERIC CONSTRAINTS
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 4, 0);
        constraints.anchor = GridBagConstraints.LINE_START;

        // NAME
        JTextField name = new JTextField("Enter name");
        name.setMinimumSize(new Dimension(100, 30));
        constraints.gridy = 1;
        newSupplierPanel.add(name, constraints);

        // DISTANCE
        JLabel distanceLabel = new JLabel("Distance:");
        constraints.gridy = 2;
        newSupplierPanel.add(distanceLabel, constraints);

        JSpinner distanceSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 500, 1));
        constraints.gridx = 1;
        newSupplierPanel.add(distanceSpinner, constraints);

        JLabel kmLabel = new JLabel("km");
        constraints.gridx = 2;
        constraints.insets = new Insets(0, 2, 4, 0);
        newSupplierPanel.add(kmLabel, constraints);

        // ADD BUTTON
        JButton addButton = new JButton("Add supplier");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newSupplier.setName(name.getText());
                newSupplier.setDistance((Integer) distanceSpinner.getValue());

                businessApplication.addSupplier(newSupplier);
                displayAllSuppliers();
                displayNewSupplierWizard();
            }
        });

        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(10, 0, 0, 10);
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        newSupplierPanel.add(addButton, constraints);
    }
}
