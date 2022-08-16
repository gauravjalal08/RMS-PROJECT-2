package entities;

public class RecipeIngredient {
    private String item = null;
    private double quantity = 0.0;

    public RecipeIngredient(String item, double quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
