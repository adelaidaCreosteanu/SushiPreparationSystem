import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class SushiDish {
    private String name;
    private String description;
    private int price;
    private Map<Ingredient, Integer> recipe;

    public SushiDish (String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
        recipe = new TreeMap<>();
    }

    public void addIngredient (Ingredient ingredient, int quantity) {
        recipe.put(ingredient, quantity);
    }

    public String getRecipe () {
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
}
