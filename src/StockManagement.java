import java.util.Hashtable;

// TODO: make this a singleton pattern?

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

    // Used by restockIngredient and useIngredient. Checks stock remains positive integer
    private void updateIngredientStock(Ingredient ingredient, Integer stock) throws Exception {
        if (stock < 0) throw new Exception("Stock cannot be negative!");

        synchronized (ingredient) {
            ingredientStock.put(ingredient, stock);
        }
    }

    public void restockIngredient(Ingredient ingredient, Integer amount) throws Exception {
        updateIngredientStock(ingredient, ingredientStock.get(ingredient) + amount);

        System.out.println("Successfully restocked ingredient " + ingredient.getName() + "\nNew quantity: " + ingredientStock.get(ingredient));
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

    // Used by restockDish and sellDish. Checks stock remains positive integer
    private void updateDishStock(SushiDish sushiDish, Integer stock) throws Exception {
        if (stock < 0) throw new Exception("Stock cannot be negative!");

        synchronized (sushiDish) {
            dishStock.put(sushiDish, stock);
        }
    }

    public void restockDish(SushiDish sushiDish, Integer amount) throws Exception {
        updateDishStock(sushiDish, dishStock.get(sushiDish) + amount);

        System.out.println("Successfully restocked dish " + sushiDish.getName() + "\nNew quantity: " + dishStock.get(sushiDish));
    }

    public void sellDish(SushiDish sushiDish, Integer amount) throws Exception {
        if (!dishStock.containsKey(sushiDish)) throw new Exception("Invalid method arguments!");

        updateDishStock(sushiDish, dishStock.get(sushiDish) - amount);
    }

    public synchronized Hashtable getIngredients() {
        return ingredientStock;
    }

    public synchronized Hashtable getDishes() {
        return dishStock;
    }
}
