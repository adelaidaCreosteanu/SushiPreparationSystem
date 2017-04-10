import java.util.Map;
import java.util.TreeMap;

public class SushiDish {
    private String name;
    private String description;
    private int price;
    private int restockLevel;
    private Map<Ingredient, Integer> recipe;

    public SushiDish(String name, String description, int price, int restockLevel) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.restockLevel = restockLevel;
        recipe = new TreeMap<>();           // Chose TreeMap so the recipe is neatly sorted alphabetically
    }

    public void addIngredient(Ingredient ingredient, Integer quantity) {
        recipe.put(ingredient, quantity);
    }

    public String getIngredients() {
        StringBuffer recipeBuffer = new StringBuffer();
        recipeBuffer.append(this.name + " - " + this.description + "\n");

        for (Ingredient ingredient : recipe.keySet()) {
            recipeBuffer.append(ingredient.getName() + ": " + recipe.get(ingredient) + "\n");
        }

        return recipeBuffer.toString();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getRestockLevel() {
        return restockLevel;
    }
}
