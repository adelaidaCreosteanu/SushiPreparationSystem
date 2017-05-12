public class TestMain {

    public static void main(String args[]) {
        SushiDish salmonNigiri = new SushiDish("Salmon nigiri", "Salmon on rice tied with seaweed.", 3, 5);
        Supplier supplier = new Supplier("BestSupplier", 10);
        Ingredient rice = new Ingredient("Rice", "g", supplier, 10);
        Ingredient salmon = new Ingredient("Salmon", "g", supplier, 10);
        Ingredient seaWeed = new Ingredient("SeaWeed", "cm", supplier, 10);

        salmonNigiri.addIngredient(rice, 10);
        salmonNigiri.addIngredient(salmon, 5);
        salmonNigiri.addIngredient(seaWeed, 6);


        StockManagement management = new StockManagement();

        management.addNewDish(salmonNigiri);
        management.addNewIngredient(rice);
        management.addNewIngredient(salmon);
        management.addNewIngredient(seaWeed);


        try {
            management.restockDish(salmonNigiri, 6);
            management.restockIngredient(rice, 100);
            management.restockIngredient(salmon, 100);
            management.restockIngredient(seaWeed, 100);

            new Thread(new KitchenStaff(management), "Chef").start();
            new Thread(new KitchenStaff(management), "Sous-chef").start();

            management.sellDish(salmonNigiri, 2);
            management.sellDish(salmonNigiri, 1);
            management.sellDish(salmonNigiri, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
