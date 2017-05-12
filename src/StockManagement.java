import java.util.Hashtable;

// TODO: make this a singleton pattern?

public final class StockManagement {
    private Hashtable<Ingredient, Integer> ingredients;     // Ingredients with current stock levels
    private Hashtable<SushiDish, Integer> dishes;           // Dishes with current stock levels

    public StockManagement() {
        ingredients = new Hashtable<>();
        dishes = new Hashtable<>();
    }

    public void addNewIngredient(Ingredient ingredient) {
        synchronized (ingredient) {
            ingredients.put(ingredient, 0);
        }
    }

    // Used by restockIngredient and useIngredient. Checks stock remains positive integer
    private void updateIngredientStock(Ingredient ingredient, Integer stock) throws Exception {
        if (stock < 0) throw new Exception("Stock cannot be negative!");

        synchronized (ingredient) {
            ingredients.put(ingredient, stock);
        }
    }

    public void restockIngredient(Ingredient ingredient, Integer amount) throws Exception {
        updateIngredientStock(ingredient, ingredients.get(ingredient) + amount);

        System.out.println("Successfully restocked ingredient " + ingredient.getName() + "\nNew quantity: " + ingredients.get(ingredient));
    }

    public void useIngredient(Ingredient ingredient, Integer amount) throws Exception {
        if (!ingredients.containsKey(ingredient)) throw new Exception("This ingredient has not yet been added!");

        updateIngredientStock(ingredient, ingredients.get(ingredient) - amount);
    }

    public void addNewDish(SushiDish sushiDish) {
        synchronized (sushiDish) {
            dishes.put(sushiDish, 0);
        }
    }

    // Used by restockDish and sellDish. Checks stock remains positive integer
    private void updateDishStock(SushiDish sushiDish, Integer stock) throws Exception {
        if (stock < 0) throw new Exception("Stock cannot be negative!");

        synchronized (sushiDish) {
            dishes.put(sushiDish, stock);
        }
    }

    public void restockDish(SushiDish sushiDish, Integer amount) throws Exception {
        updateDishStock(sushiDish, dishes.get(sushiDish) + amount);

        System.out.println("Successfully restocked dish " + sushiDish.getName() + "\nNew quantity: " + dishes.get(sushiDish));
    }

    public void sellDish(SushiDish sushiDish, Integer amount) throws Exception {
        if (!dishes.containsKey(sushiDish)) throw new Exception("Invalid method arguments!");

        updateDishStock(sushiDish, dishes.get(sushiDish) - amount);
    }

    public synchronized Hashtable getIngredients() {
        return ingredients;
    }

    public synchronized Hashtable getDishes() {
        return dishes;
    }
}
