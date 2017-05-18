import java.util.Map;
import java.util.TreeMap;

public class SushiDish {
    private String name;
    private String description;
    private int price;
    private int restockLevel;
    private Map<Ingredient, Integer> ingredientAmounts;

    public SushiDish(String name, String description, int price, int restockLevel) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.restockLevel = restockLevel;
        ingredientAmounts = new TreeMap<>();           // Chose TreeMap so the ingredientAmounts are neatly sorted alphabetically
    }

    public void addIngredient(Ingredient ingredient, Integer amount) {
        ingredientAmounts.put(ingredient, amount);
    }

    public synchronized Map getIngredients() {
        return ingredientAmounts;
    }

    public String getDescription() {
        return description;
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
