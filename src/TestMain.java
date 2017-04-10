public class TestMain {

    public static void main(String args[]) {
        SushiDish salmonNigiri = new SushiDish("Nigiri", "Salmon on rice tied with seaweed.", 3, 5);
        Supplier supplier = new Supplier("BestSupplier", 10);
        Ingredient rice = new Ingredient("Rice", "g", supplier, 10);
        Ingredient salmon = new Ingredient("Salmon", "g", supplier, 10);
        Ingredient seaWeed = new Ingredient("SeaWeed", "cm", supplier, 10);

        salmonNigiri.addIngredient(rice, 10);
        salmonNigiri.addIngredient(salmon, 5);
        salmonNigiri.addIngredient(seaWeed, 6);

        System.out.println(salmonNigiri.getIngredients());

        StockManagement management = new StockManagement();

        management.addDish(salmonNigiri);
        management.addIngredient(rice);
        management.addIngredient(salmon);
        management.addIngredient(seaWeed);

        management.restockIngredient(rice, 100);
        management.restockIngredient(salmon, 50);
        management.restockIngredient(salmon, 5);
        management.restockIngredient(seaWeed, 120);
        management.prepareDish(salmonNigiri, 50);

        try {
            management.use(rice, 90);
            management.sell(salmonNigiri, 40);
            management.use(salmon, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
