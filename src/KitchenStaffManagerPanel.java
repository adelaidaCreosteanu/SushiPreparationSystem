import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class KitchenStaffManagerPanel extends JPanel {
    private BusinessApplication businessApplication;
    private ArrayList<KitchenStaff> staff;
    private int staffNumber;

    public KitchenStaffManagerPanel(BusinessApplication businessApplication) {
        super();

        this.businessApplication = businessApplication;
        setLayout(new GridBagLayout());

        displayAllStaff();
    }

    private void displayAllStaff() {
        removeAll();
        staffNumber = 0;
        staff = businessApplication.getStaff();

        for (KitchenStaff ks : staff) {
            displayStaff(ks);
        }

        JButton addStaff = new JButton("Add kitchen staff");
        addStaff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                businessApplication.addKitchenStaff();
                displayAllStaff();
            }
        });

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(20, 0, 0, 0);
        constraints.anchor = GridBagConstraints.PAGE_END;
        add(addStaff, constraints);

        revalidate();
        repaint();
    }

    private void displayStaff(KitchenStaff staff) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.LINE_START;

        // NAME
        JLabel name = new JLabel("Kitchen staff #" + String.valueOf(++staffNumber));
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0, 0, 4, 0);
        add(name, constraints);

        // REMOVE BUTTON
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                businessApplication.removeKitchenStaff(staff);

                displayAllStaff();
            }
        });
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(0, 0, 10, 0);
        add(removeButton, constraints);
    }
}
