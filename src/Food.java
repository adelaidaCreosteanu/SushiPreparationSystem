public class Food {
    protected String name;
    protected int restockLevel;

    public Food(String name, int restockLevel) {
        this.name = name;
        this.restockLevel = restockLevel;
    }

    public String getName() {
        return name;
    }

    public int getRestockLevel() {
        return restockLevel;
    }

    public void setRestockLevel(int restockLevel) {
        this.restockLevel = restockLevel;
    }
}
