import java.util.Hashtable;

public final class StockManagement {
    private Hashtable<Ingredient, Integer> ingredients;     // Ingredients with stock levels
    private Hashtable<SushiDish, Integer> dishes;           // Dishes with stock levels

    public StockManagement() {
        ingredients = new Hashtable<>();
        dishes = new Hashtable<>();
    }

    public void addIngredient (Ingredient ingredient, Integer stock) {
        ingredients.put(ingredient, stock);
    }

    public void addIngredient (Ingredient ingredient) {
        addIngredient(ingredient, 0);
    }

    public void addDish (SushiDish sushiDish, Integer stock) {
        dishes.put(sushiDish, stock);
    }

    public void addDish (SushiDish sushiDish) {
        addDish(sushiDish, 0);
    }
}
