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
        tabbedPane.addTab("Stock Manager", new StockPanel(this));
        tabbedPane.addTab("Food&Supply Manager", new DishesPanel(this));
        // add ordersPanel
        // add staffPanel

        // JFrame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(600, 600));
        setVisible(true);
    }

    public Hashtable<Ingredient, Integer> getIngredients() {
        return stockManagement.getIngredients();
    }

    public Hashtable<SushiDish, Integer> getDishes() {
        return stockManagement.getDishes();
    }
}
