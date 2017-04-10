import java.util.Hashtable;

public final class StockManagement {
    private Hashtable<Ingredient, Integer> ingredients;     // Ingredients with stock levels
    private Hashtable<SushiDish, Integer> dishes;           // Dishes with stock levels

    public StockManagement() {
        ingredients = new Hashtable<>();
        dishes = new Hashtable<>();
    }

    public synchronized void addIngredient(Ingredient ingredient, Integer stock) {
        ingredients.put(ingredient, stock);
    }

    public void addIngredient(Ingredient ingredient) {
        addIngredient(ingredient, 0);
    }

    public synchronized void addDish(SushiDish sushiDish, Integer stock) {
        dishes.put(sushiDish, stock);
    }

    public void addDish(SushiDish sushiDish) {
        addDish(sushiDish, 0);
    }

    public synchronized void restockIngredient(Ingredient ingredient, Integer amount) {
        if (ingredients.containsKey(ingredient)) {
            Integer stock = ingredients.get(ingredient) + amount;
            ingredients.put(ingredient, stock);
        } else {
            addIngredient(ingredient, amount);
        }
    }

    public synchronized void prepareDish(SushiDish sushiDish, Integer amount) {
        if (dishes.containsKey(sushiDish)) {
            Integer stock = dishes.get(sushiDish) + amount;
            dishes.put(sushiDish, stock);
        } else {
            addDish(sushiDish, amount);
        }
    }

    public synchronized void use(Ingredient ingredient, Integer amount) throws Exception {
        if (ingredients.containsKey(ingredient) && amount > 0) {
            Integer stock = ingredients.get(ingredient) - amount;

            if (stock < 0) throw new Exception("Stock level cannot be negative!");      // TODO: Maybe create a custom Exception?

            if (stock < ingredient.getRestockLevel()) {
                System.err.println(ingredient.getName() + " has to be restocked!");
            }

            ingredients.put(ingredient, stock);
        } else {
            throw new Exception("Invalid method arguments!");
        }
    }

    public synchronized void sell(SushiDish sushiDish, Integer amount) throws Exception {
        if (dishes.containsKey(sushiDish) && amount > 0) {
            Integer stock = dishes.get(sushiDish) - amount;

            if (stock < 0) throw new Exception("Stock level cannot be negative");

            if (stock < sushiDish.getRestockLevel()) {
                System.err.println(sushiDish.getName() + " has to be restocked!");
            }

            dishes.put(sushiDish, stock);
        } else {
            throw new Exception("Invalid arguments!");
        }
    }
}
