public class TestMain {

    public static void main(String args[]) {
        SushiDish nigiri = new SushiDish("Nigiri", "Salmon on rice tied with seaweed.", 3);
        Supplier supplier = new Supplier("BestSupplier", 10);

        nigiri.addIngredient(new Ingredient("Rice", "g", supplier), 10);
        nigiri.addIngredient(new Ingredient("Salmon", "g", supplier), 5);
        nigiri.addIngredient(new Ingredient("SeaWeed", "cm", supplier), 6);

        System.out.println(nigiri.getRecipe());
    }
}
