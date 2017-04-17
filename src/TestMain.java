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

        System.out.println(salmonNigiri.getRecipe());

        StockManagement management = new StockManagement();

        management.addDish(salmonNigiri, 6);
        management.addIngredient(rice, 100);
        management.addIngredient(salmon, 100);
        management.addIngredient(seaWeed, 100);

        KitchenStaff chef = new KitchenStaff(management);
        KitchenStaff sousChef = new KitchenStaff(management);

        new Thread(chef, "Chef").start();
        new Thread(sousChef, "Sous-chef").start();

        try {
            management.sell(salmonNigiri, 2);
            management.sell(salmonNigiri, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
