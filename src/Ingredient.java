// This class implements Comparable so the list of ingredients can be sorted alphabetically inside the TreeMap of a SushiDish
public class Ingredient implements Comparable<Ingredient> {
    private String name;
    private String unit;
    private Supplier supplier;
    private int restockLevel;

    public Ingredient(String name, String unit, Supplier supplier, int restockLevel) {
        this.name = name;
        this.supplier = supplier;
        this.restockLevel = restockLevel;

        switch (unit) {
            case Measurement.GRAM:
            case Measurement.MILILITERS:
            case Measurement.PIECE:
                this.unit = unit;
                break;
            default:
                throw new Error("Invalid measurement unit!");
        }
    }

    public int compareTo(Ingredient that) {
        return this.getName().compareTo(that.getName());
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public int getRestockLevel() {
        return restockLevel;
    }
}
