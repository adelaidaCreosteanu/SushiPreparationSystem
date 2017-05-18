// This class implements Comparable so the list of ingredients can be sorted alphabetically inside the TreeMap of a SushiDish
public class Ingredient extends Food implements Comparable<Ingredient> {
    private String unit;
    private Supplier supplier;

    public Ingredient(String name, String unit, Supplier supplier, int restockLevel) {
        super(name, restockLevel);
        this.supplier = supplier;

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
}