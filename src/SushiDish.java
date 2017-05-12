import java.util.Map;
import java.util.TreeMap;

public class SushiDish {
    private String name;
    private String description;
    private int price;
    private int restockLevel;
    private Map<Ingredient, Integer> ingredients;

    public SushiDish(String name, String description, int price, int restockLevel) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.restockLevel = restockLevel;
        ingredients = new TreeMap<>();           // Chose TreeMap so the ingredients are neatly sorted alphabetically
    }

    public void addIngredient(Ingredient ingredient, Integer amount) {
        ingredients.put(ingredient, amount);
    }

    public String getRecipe() {
        StringBuffer recipeBuffer = new StringBuffer();
//        recipeBuffer.append(this.name + " - " + this.description + "\n");
//
//        for (Ingredient ingredient : ingredients.keySet()) {
//            recipeBuffer.append(ingredient.getName() + ": " + ingredients.get(ingredient) + "\n");
//        }
//
        return recipeBuffer.toString();
    }

    public synchronized Map getIngredients() {
        return ingredients;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getRestockLevel() {
        return restockLevel;
    }
}
