import java.util.Hashtable;
import java.util.Map;

public class KitchenStaff implements Runnable {
    private StockManagement stockManagement;
    private int minTime = 20;
    private int maxTime = 60;

    public KitchenStaff(StockManagement stockManagement) {
        this.stockManagement = stockManagement;
    }

    public void setWorkingTime(int minTime, int maxTime) {
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    public void run() {
        Hashtable<SushiDish, Integer> dishes;

        while (true) {
            try {
                dishes = stockManagement.getDishes();

                for (SushiDish dish : dishes.keySet()) {
                    if (dishes.get(dish) < dish.getRestockLevel()) {
                        prepareDish(dish);
                        System.out.println(Thread.currentThread().getName() + " increased stock to " + dishes.get(dish));
                    }
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void prepareDish(SushiDish dish) throws Exception {
        // Randomising time spent on this dish
        Integer timeSpent = (int) (Math.random() * (maxTime - minTime) + minTime);
        System.out.println(Thread.currentThread().getName() + " preparing " + dish.getName() + " for " + timeSpent * 1000 + " ms.");
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
