import java.util.HashMap;

public class Order {
    // Order status constants:
    public static final String ORDER_IN_PROGRESS = "order in progress";
    public static final String ORDER_RECEIVED = "order received";
    public static final String ORDER_DISPATCHED = "order dispatched";

    private Client client;
    private String orderStatus;
    private HashMap<SushiDish, Integer> dishAmounts;

    public Order(Client client) {
        this.client = client;
        orderStatus = ORDER_IN_PROGRESS;
        dishAmounts = new HashMap<>();
    }

    public HashMap<SushiDish, Integer> getDishAmounts() {
        return dishAmounts;
    }

    public Integer getTotalPrice() {
        int total = 0;

        for (SushiDish d : dishAmounts.keySet()) {
            total += d.getPrice() * dishAmounts.get(d);
        }

        return total;
    }

    protected void addDish(SushiDish dish) {
        int amount = 1;

        for (SushiDish d : dishAmounts.keySet()) {
            if (d.equals(dish)) {
                amount += dishAmounts.get(dish);
                break;
            }
        }

        dishAmounts.put(dish, amount);
    }

    protected void removeDish(SushiDish dish) {
        dishAmounts.remove(dish);
    }

    protected void place() {
        orderStatus = ORDER_RECEIVED;
    }

    protected void dispatchOrder() {
        orderStatus = ORDER_DISPATCHED;
    }
}
