public class TestMain {

    public static void main(String args[]) {
        SushiDish salmonNigiri = new SushiDish("Salmon nigiri", "Salmon on rice tied with seaweed.", 3, 5);
        Supplier supplier = new Supplier("BestSupplier", 10);
        Ingredient rice = new Ingredient("Rice", Measurement.GRAM, supplier, 10);
        Ingredient salmon = new Ingredient("Salmon", Measurement.GRAM, supplier, 10);
        Ingredient seaWeed = new Ingredient("SeaWeed", Measurement.PIECE, supplier, 10);

        salmonNigiri.addIngredient(rice, 10);
        salmonNigiri.addIngredient(salmon, 5);
        salmonNigiri.addIngredient(seaWeed, 6);

        SushiDish tunaNigiri = new SushiDish("Tuna nigiri", "Tuna on rice tied with seaweed.", 5, 5);
        Ingredient tuna = new Ingredient("Tuna", Measurement.GRAM, supplier, 10);

        tunaNigiri.addIngredient(rice, 10);
        tunaNigiri.addIngredient(tuna, 5);
        tunaNigiri.addIngredient(seaWeed, 6);

        StockManagement management = new StockManagement();

        management.addNewDish(salmonNigiri);
        management.addNewIngredient(rice);
        management.addNewIngredient(salmon);
        management.addNewIngredient(seaWeed);
        management.addNewDish(tunaNigiri);
        management.addNewIngredient(tuna);


        try {
            management.restockDish(salmonNigiri, 6);
            management.restockIngredient(rice, 100);
            management.restockIngredient(salmon, 100);
            management.restockIngredient(seaWeed, 100);
            management.restockDish(tunaNigiri, 10);
            management.restockIngredient(tuna, 50);

            new Thread(new KitchenStaff(management), "Chef").start();
            new Thread(new KitchenStaff(management), "Sous-chef").start();

            management.sellDish(salmonNigiri, 2);
            management.sellDish(tunaNigiri, 6);
            management.sellDish(salmonNigiri, 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
