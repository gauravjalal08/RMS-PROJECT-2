package entities;

import exceptions.InsufficientIngredientException;
import exceptions.InvalidIngredientException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Restaurant {
    private String name = null;
    private String location = null;
    private Account account = null;
    private Inventory inventory = null;
    private Menu menu = null;
    private boolean isClosed = false;
    private boolean isNewInTown = false;
    private String currency = null;

    public Restaurant(String name, String location, Account account, Inventory inventory, Menu menu) {
        this.name = name;
        this.account = account;
        this.location = location;
        this.inventory = inventory;
        this.menu = menu;
    }

    public void placeOrder (String itemName, double quantity) throws Exception {
        if (isClosed) {
            throw new Exception("Closed!");
        }

        // check if item exists?
        Map<String, Item> inventoryMap = new HashMap<>();
        for (Item item : inventory.getItems())  {
            inventoryMap.put(item.getItemName(), item);
        }

        boolean exists = false;
        List<String> quantityMissingMessages = new ArrayList<>();
        Recipe product = null;
        for (Recipe recipe : this.menu.getRecipes()) {
            if (recipe.getName().equalsIgnoreCase(itemName)) {
                product = recipe;
                exists = true;
                for (RecipeIngredient recipeIngredientAsked : recipe.getRecipeIngredients()) {
                    if (!inventoryMap.containsKey(recipeIngredientAsked.getItem())) {
                        throw new InvalidIngredientException(itemName + " not present in inventory!");
                    }

                    Item inventoryItem  = inventoryMap.get(recipeIngredientAsked.getItem());
                    double delta = inventoryItem.getQuantity() - recipeIngredientAsked.getQuantity();
                    if (delta < 0) {
                        quantityMissingMessages.add(inventoryItem.getItemName() + " quantity not sufficient, needs more " + delta);
                    }
                }

                break;
            }
        }

        if (!exists) {
            throw new Exception("invalid Recipe Specified!");
        }

        if (quantityMissingMessages.size() > 0) {
            quantityMissingMessages.forEach(System.out::println);
            throw new InsufficientIngredientException("Insufficient Quantity found for recipe, Please order given ingrediants");
        }

        double price = product.getPrice();
        this.account.addTransaction(new Transaction(price, TransactionType.CREDIT));
        System.out.println("Order Succesfully placed!");
    }

    public Account getAccount() {
        return this.account;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public String getName() {
        return name;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isNewInTown() {
        return isNewInTown;
    }

    public void setNewInTown(boolean newInTown) {
        isNewInTown = newInTown;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
