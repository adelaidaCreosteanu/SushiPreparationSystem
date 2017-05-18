import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class BusinessApplication extends Application {
    private StockManagement stockManagement;
    private Container container;

    public BusinessApplication(StockManagement stockManagement) {
        super("Business Application");

        this.stockManagement = stockManagement;
        comms = new Comms(this);
        container = getContentPane();
        
        init();
    }

    private void init() {
        container.add(new BusinessHomePanel(this));
        
        // JFrame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(600, 600));
        setVisible(true);
    }

    public void showStockPanel() {
        container.removeAll();
        container.add(new StockPanel(this));
        container.revalidate();
        container.repaint();
    }

    public void showDishesPanel() {
        container.removeAll();
        container.add(new DishesPanel(this));
        container.revalidate();
        container.repaint();
    }

    public void showOrdersPanel() {
        container.removeAll();
        // add Panel
        container.revalidate();
        container.repaint();
    }

    public void showStaffPanel() {
        container.removeAll();
        // add Panel
        container.revalidate();
        container.repaint();
    }

    public void showHomePanel() {
        container.removeAll();
        container.add(new BusinessHomePanel(this));
        container.revalidate();
        container.repaint();
    }

    public Hashtable<Ingredient, Integer> getIngredients() {
        return stockManagement.getIngredients();
    }

    public Hashtable<SushiDish, Integer> getDishes() {
        return stockManagement.getDishes();
    }
}
