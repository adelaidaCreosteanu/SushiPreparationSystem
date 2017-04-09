import java.util.Hashtable;

public final class StockManagement {
    private Hashtable<Ingredient, Integer> ingredients;     // Ingredients with stock levels
    private Hashtable<SushiDish, Integer> dishes;           // Dishes with stock levels

    public StockManagement() {
        ingredients = new Hashtable<>();
        dishes = new Hashtable<>();
    }

    public synchronized void addIngredient (Ingredient ingredient, Integer stock) {
        ingredients.put(ingredient, stock);
    }

    public void addIngredient (Ingredient ingredient) {
        addIngredient(ingredient, 0);
    }

    public synchronized void addDish (SushiDish sushiDish, Integer stock) {
        dishes.put(sushiDish, stock);
    }

    public void addDish (SushiDish sushiDish) {
        addDish(sushiDish, 0);
    }

    public synchronized void restockIngredient (Ingredient ingredient, Integer amount) {
        if (ingredients.contains(ingredient)) {
            Integer stock = ingredients.get(ingredient) + amount;
            ingredients.put(ingredient, stock);
        } else {
            addIngredient(ingredient, amount);
        }
    }

    public synchronized void restockDish (SushiDish sushiDish, Integer amount) {
        if (dishes.contains(sushiDish)) {
            Integer stock = dishes.get(sushiDish) + amount;
            dishes.put(sushiDish, stock);
        } else {
            addDish(sushiDish, amount);
        }
    }
}
