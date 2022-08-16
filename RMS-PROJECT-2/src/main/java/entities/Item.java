package entities;

public class Item {
    private String itemName = null;
    private double price = 0.0;
    private double quantity = 0.0;

    public Item(String itemName, double quantity, double price) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String toString() {
        return "itemName='" + itemName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity;
    }
}
