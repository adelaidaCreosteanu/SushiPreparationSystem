import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class SushiDish implements Serializable {
    private String name;
    private String description;
    private int price;
    private int restockLevel;
    private TreeMap<Ingredient, Integer> ingredientAmounts = new TreeMap<>();   // Chose TreeMap so the ingredientAmounts are neatly sorted alphabetically

    public SushiDish() {
        new SushiDish("", "", 0, 0);
    }

    public SushiDish(String name, String description, int price, int restockLevel) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.restockLevel = restockLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setRestockLevel(int restockLevel) {
        this.restockLevel = restockLevel;
    }

    public int getRestockLevel() {
        return restockLevel;
    }

    public void addIngredient(Ingredient ingredient, Integer amount) {
        ingredientAmounts.put(ingredient, amount);
    }

    public synchronized Map getIngredients() {
        return ingredientAmounts;
    }
}
