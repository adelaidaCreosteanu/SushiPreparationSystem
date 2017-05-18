import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BusinessHomePanel extends JPanel {
    private BusinessApplication businessApplication;

    public BusinessHomePanel(BusinessApplication businessApplication) {
        super();

        this.businessApplication = businessApplication;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(CENTER_ALIGNMENT);

        displayButtons();
    }

    private void displayButtons() {
        JButton stock = new JButton("Stock levels");
        stock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                businessApplication.showStockPanel();
            }
        });
        add(stock);

        JButton dishesAndRelated = new JButton("Ingredients, dishes and suppliers");
        dishesAndRelated.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                businessApplication.showDishesPanel();
            }
        });
        add(dishesAndRelated);

        JButton orders = new JButton("Orders");
        orders.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                businessApplication.showOrdersPanel();
            }
        });
        add(orders);

        JButton staff = new JButton("Kitchen staff and drones");
        staff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                businessApplication.showStaffPanel();
            }
        });
        add(staff);
    }
}
