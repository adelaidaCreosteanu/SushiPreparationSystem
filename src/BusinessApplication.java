import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Hashtable;

public class BusinessApplication extends Application {
    private StockManagement stockManagement;

    public BusinessApplication(StockManagement stockManagement) {
        super("Business Application");

        this.stockManagement = stockManagement;
        comms = new Comms(this);

        init();
    }

    private void init() {
        setContentPane(new BusinessHomePanel(this));

        // JFrame settings
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(600, 600));
        setVisible(true);
    }

    public void showStockPanel() {
        setContentPane(new StockPanel(this));
    }

    public void showDishesPanel() {

    }

    public void showOrdersPanel() {

    }

    public void showStaffPanel() {

    }

    public void showHomePanel() {
        setContentPane(new BusinessHomePanel(this));
    }

    public Hashtable<Ingredient, Integer> getIngredients() {
        return stockManagement.getIngredients();
    }

    public Hashtable<SushiDish, Integer> getDishes() {
        return stockManagement.getDishes();
    }
}
