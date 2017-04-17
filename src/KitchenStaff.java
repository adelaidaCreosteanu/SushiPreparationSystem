import java.util.Hashtable;
import java.util.Map;

public class KitchenStaff implements Runnable {
    private StockManagement stockManagement;
    private int minTime = 20;
    private int maxTime = 60;

    public KitchenStaff(StockManagement stockManagement) {
        this.stockManagement = stockManagement;
    }

    public void run() {
        while (true) {
            try {
                Hashtable<SushiDish, Integer> dishes;

                synchronized (stockManagement) {
                    stockManagement.wait();

                    System.out.println(Thread.currentThread().getName() + " was notified that dish went under restock level.");

                    dishes = stockManagement.getDishes();
                }

                for (SushiDish dish : dishes.keySet()) {
                    if (dishes.get(dish) < dish.getRestockLevel()) {
                        prepareDish(dish);
                    }
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void prepareDish(SushiDish dish) throws Exception {
        Map<Ingredient, Integer> ingredients = dish.getIngredients();
        Integer timeSpent = (int) (Math.random() * (maxTime - minTime) + minTime);
        System.out.println(Thread.currentThread().getName() + " preparing " + dish.getName() + " for " + timeSpent * 1000 + " ms.");
        Thread.sleep(timeSpent * 1000);

        for (Ingredient ingredient : ingredients.keySet()) {
            Integer amount = ingredients.get(ingredient);
            stockManagement.use(ingredient, amount);
        }

        stockManagement.restockDish(dish, 1);
    }
}
