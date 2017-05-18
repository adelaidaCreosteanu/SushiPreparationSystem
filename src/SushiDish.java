import java.util.Map;
import java.util.TreeMap;

public class SushiDish extends Food {
    private String description;
    private int price;
    private Map<Ingredient, Integer> ingredientAmounts;

    public SushiDish(String name, String description, int price, int restockLevel) {
        super(name, restockLevel);
        this.description = description;
        this.price = price;
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

    public int getPrice() {
        return price;
    }
}
