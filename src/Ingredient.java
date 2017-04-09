public class Ingredient implements Comparable<Ingredient> {
    private String name;
    private String unit;
    private Supplier supplier;

    public Ingredient (String name, String unit, Supplier supplier) {
        this.name = name;
        this.unit = unit;       // TODO: This could lead to mistakes as it's a string
        this.supplier = supplier;
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
