import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class BusinessApplication extends Application {
    private StockManagement stockManagement;
    private JTabbedPane tabbedPane;

    public BusinessApplication(StockManagement stockManagement) {
        super("Business Application");

        this.stockManagement = stockManagement;
        comms = new Comms(this);
        tabbedPane = new JTabbedPane();
        setContentPane(tabbedPane);

        init();
    }

    private void init() {
//        tabbedPane.addTab("Ingredients", new IngredientManagerPanel(this));
        tabbedPane.addTab("Dishes", new DishManagerPanel(stockManagement));
//        tabbedPane.addTab("Suppliers", new SupplierMAnagerPanel(this));
//        tabbedPane.addTab("Orders", new OrderManagerPanel(this));
//        tabbedPane.addTab("Kitchen staff", new KitchenStaffManagerPanel(this));
//        tabbedPane.addTab("Drones", new DroneManagerPanel(this));

        // JFrame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(800, 800));
        setVisible(true);
    }

    protected StockManagement getStockManagement() {
        return stockManagement;
    }
}
