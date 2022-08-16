package entities;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items = new ArrayList<>();

    public Inventory(List<Item> items) {
        this.items = items;
    }

    public Inventory DeepCopy () {
        List<Item> itemsCopied = new ArrayList<>();
        this.items.forEach(item -> {
            itemsCopied.add(new Item(item.getItemName(), item.getQuantity(), item.getPrice()));
        });

        return new Inventory(itemsCopied);
    }

    public List<Item> getItems() {
        return items;
    }

    public Item getItem(String itemName) {
        for (Item item : this.getItems()) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }

        return null;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
