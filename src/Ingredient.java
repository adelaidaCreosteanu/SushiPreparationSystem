import java.io.Serializable;

// This class implements Comparable so the list of ingredients can be sorted alphabetically inside the TreeMap of a SushiDish
public class Ingredient implements Comparable<Ingredient>, Serializable {
    private String name;
    private String unit;
    private Supplier supplier;
    private int restockLevel;

    public Ingredient() {
        new Ingredient("", Measurement.GRAM, null, 0);
    }

    public Ingredient(String name, String unit, Supplier supplier, int restockLevel) {
        this.name = name;
        this.supplier = supplier;
        this.restockLevel = restockLevel;

        switch (unit) {
            case Measurement.GRAM:
            case Measurement.MILLILITERS:
            case Measurement.PIECE:
                this.unit = unit;
                break;
            default:
                throw new Error("Invalid measurement unit!");
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public int getRestockLevel() {
        return restockLevel;
    }

    public void setRestockLevel(int restockLevel) {
        this.restockLevel = restockLevel;
    }

    public int compareTo(Ingredient that) {
        return this.getName().compareTo(that.getName());
    }
}