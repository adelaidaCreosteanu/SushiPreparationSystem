import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public final class StockManagement {
    private Hashtable<Ingredient, Integer> ingredientStock;     // Ingredients with current stock levels
    private Hashtable<SushiDish, Integer> dishStock;           // Dishes with current stock levels

    public StockManagement() {
        ingredientStock = new Hashtable<>();
        dishStock = new Hashtable<>();
    }

    public void addNewIngredient(Ingredient ingredient) {
        synchronized (ingredient) {
            ingredientStock.put(ingredient, 0);
        }
    }

    public void removeIngredient(Ingredient ingredient) {
        boolean removable = true;

        for (SushiDish s : dishStock.keySet()) {
            for (Object i : s.getIngredients().keySet()) {
                removable = false;
            }
        }

        if (removable) {
            ingredientStock.remove(ingredient);
        }
    }


    // Used by restockIngredient and useIngredient. Checks stock remains positive integer
    private void updateIngredientStock(Ingredient ingredient, Integer stock) throws Exception {
        if (stock < 0) throw new Exception("Stock cannot be negative!");

        synchronized (ingredient) {
            ingredientStock.put(ingredient, stock);
        }
    }

    public void restockIngredient(Ingredient ingredient, Integer amount) throws Exception {
        updateIngredientStock(ingredient, ingredientStock.get(ingredient) + amount);
    }

    public void useIngredient(Ingredient ingredient, Integer amount) throws Exception {
        if (!ingredientStock.containsKey(ingredient)) throw new Exception("This ingredient has not yet been added!");

        updateIngredientStock(ingredient, ingredientStock.get(ingredient) - amount);
    }

    public void addNewDish(SushiDish sushiDish) {
        synchronized (sushiDish) {
            dishStock.put(sushiDish, 0);
        }
    }

    public void removeDish(SushiDish dish) {
        synchronized (dishStock) {
            dishStock.remove(dish);
        }
    }

    // Used by restockDish and sellDish. Checks stock remains positive integer
    private void updateDishStock(SushiDish sushiDish, Integer stock) throws Exception {
        if (stock < 0) throw new Exception("Stock cannot be negative!");

        synchronized (sushiDish) {
            dishStock.put(sushiDish, stock);
        }
    }

    public void restockDish(SushiDish sushiDish, Integer amount) throws Exception {
        updateDishStock(sushiDish, dishStock.get(sushiDish) + amount);
    }

    public void sellDish(SushiDish sushiDish, Integer amount) throws Exception {
        if (!dishStock.containsKey(sushiDish)) throw new Exception("Invalid method arguments!");

        updateDishStock(sushiDish, dishStock.get(sushiDish) - amount);
    }

    public Hashtable<Ingredient, Integer> getIngredients() {
        synchronized (ingredientStock) {
            return ingredientStock;
        }
    }

    public Hashtable<SushiDish, Integer> getDishes() {
        synchronized (dishStock) {
            return dishStock;
        }
    }
}
