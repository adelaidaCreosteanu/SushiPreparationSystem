import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BusinessApplication extends Application {
    private StockManagement stockManagement;
    private JTabbedPane tabbedPane;

    private ArrayList<Customer> customers;
    private ArrayList<Supplier> suppliers;
    private ArrayList<Order> orders;
    private ArrayList<KitchenStaff> staffArrayList;

    public BusinessApplication() {
        super("Business Application");

        stockManagement = new StockManagement();
        comms = new Comms(this);
        comms.start();
        tabbedPane = new JTabbedPane();
        setContentPane(tabbedPane);

        customers = new ArrayList<>();
        suppliers = new ArrayList<>();
        orders = new ArrayList<>();
        staffArrayList = new ArrayList<>();

        init();
    }

    private void init() {
        tabbedPane.addTab("Ingredients", new IngredientManagerPanel(this));
        tabbedPane.addTab("Dishes", new DishManagerPanel(this));
        tabbedPane.addTab("Suppliers", new SupplierManagerPanel(this));
        tabbedPane.addTab("Orders", new OrderManagerPanel(this));
        tabbedPane.addTab("Kitchen staff", new KitchenStaffManagerPanel(this));
//        tabbedPane.addTab("Drones", new DroneManagerPanel(this));

        // JFrame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(800, 800));
        setVisible(true);
    }

    public void receivedMessage(int request) {
        switch (request) {
            case Message.REQUEST_CUSTOMERS_ARRAYLIST:
                comms.sendMessage(Message.GIVE_CUSTOMERS_ARRAYLIST, customers);
                break;
            case Message.REQUEST_DISHSTOCK_HASHMAP:
                comms.sendMessage(Message.GIVE_DISHSTOCK_HASHMAP, stockManagement.getDishes());
                break;
        }
    }

    public void receivedMessage(int request, Object content) {
        switch (request) {
            case Message.REQUEST_REGISTER_CUSTOMER:
                customers.add((Customer) content);
                break;
            case Message.REQUEST_PLACE_ORDER:
                orders.add((Order) content);
                break;
        }
    }

    public StockManagement getStockManagement() {
        return stockManagement;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Supplier> getSuppliers() {
        return suppliers;
    }

    public Supplier getSupplierByName(String name) {
        for (Supplier s : suppliers) {
            if (s.getName().equals(name)) {
                return s;
            }
        }

        return null;
    }

    public void addSupplier(Supplier supplier) {
        suppliers.add(supplier);
    }

    public void removeSupplier(Supplier supplier) {
        boolean removable = true;

        for (Ingredient i : stockManagement.getIngredients().keySet()) {
            if (i.getSupplier().equals(supplier)) {
                removable = false;
            }
        }

        if (removable) {
            suppliers.remove(supplier);
        }
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    public ArrayList<KitchenStaff> getStaff() {
        return staffArrayList;
    }

    public void addKitchenStaff() {
        KitchenStaff staff = new KitchenStaff(stockManagement);
        staffArrayList.add(staff);
        staff.start();
    }

    public void removeKitchenStaff(KitchenStaff staff) {
        staffArrayList.remove(staff);
        staff.setRunning(false);
    }
}
