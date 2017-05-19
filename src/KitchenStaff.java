import java.util.Hashtable;
import java.util.Map;

public class KitchenStaff extends Thread {
    private StockManagement stockManagement;
    private boolean running;

    // Not static or final because different kitchen staff could have different time bounds and some kitchen staff might improve, changing their working time
    private int minTime = 20;
    private int maxTime = 60;

    public KitchenStaff(StockManagement stockManagement) {
        this.stockManagement = stockManagement;
        running = true;
    }

    public void setWorkingTimeBounds(int minTime, int maxTime) {
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void run() {
        Hashtable<SushiDish, Integer> dishes;

        while (running) {
            try {
                dishes = stockManagement.getDishes();

                for (SushiDish dish : dishes.keySet()) {
                    if (dishes.get(dish) < dish.getRestockLevel()) {
                        prepareDish(dish);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void prepareDish(SushiDish dish) throws Exception {
        // Randomising time spent on this dish
        Integer timeSpent = (int) (Math.random() * (maxTime - minTime) + minTime);
        Thread.sleep(timeSpent * 1000);

        Map<Ingredient, Integer> dishIngredients = dish.getIngredients();

        // Using up ingredients
        for (Ingredient ingredient : dishIngredients.keySet()) {
            Integer amount = dishIngredients.get(ingredient);
            stockManagement.useIngredient(ingredient, amount);
        }

        // Creating the dish
        stockManagement.restockDish(dish, 1);
    }
}
