import java.util.Hashtable;

// TODO: make this a singleton pattern?

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

    public void restockIngredient(Ingredient ingredient, Integer amount) {
        if (ingredients.containsKey(ingredient)) {
            synchronized (this) {
                ingredients.put(ingredient, ingredients.get(ingredient) + amount);
            }
            System.out.println("Successfully restocked ingredient " + ingredient.getName());
        } else {
            addIngredient(ingredient, amount);
        }
    }

    public void restockDish(SushiDish sushiDish, Integer amount) {
        if (dishes.containsKey(sushiDish)) {
            synchronized (this) {
                dishes.put(sushiDish, dishes.get(sushiDish) + amount);
            }
            System.out.println("Successfully restocked dish " + sushiDish.getName() + "\nNew quantity: " + dishes.get(sushiDish));
        } else {
            addDish(sushiDish, amount);
        }
    }

    public void use(Ingredient ingredient, Integer amount) throws Exception {
        if (ingredients.containsKey(ingredient) && amount > 0) {
            Integer stock = ingredients.get(ingredient) - amount;

            if (stock < 0) throw new Exception("Stock level cannot be negative!");      // TODO: Maybe create a custom Exception?

            if (stock < ingredient.getRestockLevel()) {
                System.err.println(ingredient.getName() + " has to be restocked!");     // TODO: notify drones?
            }

            synchronized (this) {
                ingredients.put(ingredient, stock);
            }
        } else {
            throw new Exception("Invalid method arguments!");                           // TODO: throw more specialised exception?
        }
    }

    public void sell(SushiDish sushiDish, Integer amount) throws Exception {
        if (dishes.containsKey(sushiDish) && amount > 0) {
            Integer stock = dishes.get(sushiDish) - amount;

            if (stock < 0) throw new Exception("Stock level cannot be negative");       // TODO: Maybe create a custom Exception?

            synchronized (this) {
                if (stock < sushiDish.getRestockLevel()) {
                    this.notifyAll();
                }

                dishes.put(sushiDish, stock);
            }

        } else {
            throw new Exception("Invalid method arguments!");                           // TODO: throw more specialised exception?
        }
    }

    public synchronized Hashtable getIngredients() {
        return ingredients;
    }

    public synchronized Hashtable getDishes() {
        return dishes;
    }
}
