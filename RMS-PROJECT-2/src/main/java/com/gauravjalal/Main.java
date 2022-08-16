package com.gauravjalal;

import entities.*;
import entities.Currency;
import exceptions.InsufficientIngredientException;
import exceptions.InsufficientMoneyException;
import io.AccountsIO;
import io.IngredientIO;
import io.ReceipeIO;
import service.IngredientHandler;

import java.io.FileNotFoundException;
import java.util.*;


public class Main {
    private static  double availableMoney;
    private static IngredientHandler ingredientHandler;
    public static IngredientIO ingredientIO;

    public static void main(String[] args) throws FileNotFoundException {
        ingredientIO = new IngredientIO();
        ingredientHandler = new IngredientHandler();
        CommandType currentCommand = CommandType.NO_COMMAND;
        Map<Ingredient, Double> insufficientIngredients=null;
        //ingredientList = ingredientIO.readIngredientList("resources/ingredients.txt");
        availableMoney = AccountsIO.readAccounts("resources/accounts.txt");

        Inventory inventory = IngredientIO.readIngredientList("resources/ingredients.txt");
        List<Recipe> recipes = ReceipeIO.readAllReceipes("resources/receipe.txt");
        Menu menu = new Menu();
        menu.setRecipes(recipes);

        // set Currencies
        Map<String, String> currencyMap = new HashMap<>();
        currencyMap.put("india", "RS");
        currencyMap.put("US", "$");
        currencyMap.put("UK", "Pound");

        Currency currency = new Currency(currencyMap);

        Scanner scanner = new Scanner(System.in);
        // set restraunts
        List<Restaurant> restaurants = new ArrayList<>();

        Restaurant aggrawal = new Restaurant("Aggrawal sweets", "india", new Account(5000.0), inventory.DeepCopy(), menu.DeepCopy());
        aggrawal.setCurrency(currency.getCurrency("india"));
        restaurants.add(aggrawal);

        Restaurant buffalo = new Restaurant("Buffalo wings", "US", new Account(5000.0), inventory.DeepCopy(), menu.DeepCopy());
        buffalo.setCurrency(currency.getCurrency("US"));
        restaurants.add(buffalo);

        Restaurant kfc = new Restaurant("KFC", "UK", new Account(5000.0), inventory.DeepCopy(), menu.DeepCopy());
        kfc.setCurrency(currency.getCurrency("UK"));
        restaurants.add(kfc);

       // restaurants.add(new Restaurant("KFC", "UK", new Account(5000.0), inventory.DeepCopy(), menu.DeepCopy()));

        System.out.println("Available money is " + availableMoney);

        while (true) {
            try {
                if (currentCommand == CommandType.NO_COMMAND) {
                    int selectNumber = displayPrompt();
                    currentCommand = CommandType.values()[selectNumber];
                } else if (currentCommand == CommandType.VIEW_AVAILABLE_INGREDIENTS) {
                    //ingredientHandler.viewIngredients(ingredientList);
                    System.out.println("Available Ingredients");
                    restaurants.forEach(restaurant -> {
                        System.out.println("Name: " + restaurant.getName());
                        System.out.println("============================================================================");
                        restaurant.getInventory().getItems().forEach(System.out::println);
                        System.out.println("============================================================================");
                    });
                    currentCommand = CommandType.NO_COMMAND;
                } else if (currentCommand == CommandType.ORDER_SPECIFIC_INGREDIENT) {
                    while (true) {
                        selectRestaraunt(restaurants);
                        int inputSelected = scanner.nextInt();
                        if (inputSelected >= 0 && inputSelected < restaurants.size()) {
                            int i = 1;
                            for (Restaurant restaurant: restaurants) {
                                if (i == inputSelected) {
                                    // show menu
//                                    for (Inventory restInv : restaurant.getInventory()) {
//                                        System.out.println("Name : " + restInv.getName() + ", Price : " + recipe.getPrice());
//                                    }

                                    scanner.skip("\\R?");
                                    System.out.println("Item Name : ");
                                    String recipeSelected = scanner.nextLine();
                                    System.out.println("Quantity : ");
                                    double qty = scanner.nextDouble();

                                    restaurant.placeOrder(recipeSelected, qty);
                                    break;
                                }
                                i++;
                            }
                        } else {
                            System.out.println("Invalid Restaraunt Selected");
                        }
                    }
//                    selectIngredient = selectIngredient();   //variable
//                    currentCommand = CommandType.INPUT_INGREDIENTS_QTY;
                } else if (currentCommand == CommandType.INPUT_INGREDIENTS_QTY) {
//                    ingredientQty = inputIngredientQty();  //variable
//                    if (ingredientHandler.isPossibleToOrderIngredient(selectIngredient, ingredientQty, availableMoney)) {
//                        System.out.println("Order Placed successfully!");
//                        purchaseIngredient(selectIngredient, ingredientQty);
//                        currentCommand = CommandType.NO_COMMAND;
//                    } else {
//                        throw new InsufficientMoneyException();
//                    }
                } else if (currentCommand == CommandType.VIEW_TOTAL_SALES) {
                    System.out.println("Total Sales");
                    restaurants.forEach(restaurant -> {
                        System.out.println("Name: " + restaurant.getName() + ", Sales : "+ restaurant.getAccount().getSales());
                    });
                    currentCommand = CommandType.NO_COMMAND;
                } else if (currentCommand == CommandType.VIEW_NET_PROFIT) {
                    System.out.println("Total Profit");
                    restaurants.forEach(restaurant -> {
                        System.out.println("Name: " + restaurant.getName() + ", Profit : "+ restaurant.getAccount().getProfit());
                    });
                    currentCommand = CommandType.NO_COMMAND;
                } else if (currentCommand == CommandType.VIEW_TOTAL_EXPENSES) {
                    System.out.println("Total Profit");
                    restaurants.forEach(restaurant -> {
                        System.out.println("Name: " + restaurant.getName() + ", Expenses : "+ restaurant.getAccount().getExpenses() + " " + restaurant.getCurrency());
                    });
                    currentCommand = CommandType.NO_COMMAND;
                } else if (currentCommand == CommandType.PLACE_ORDER) {
                    selectRestaraunt(restaurants);
                    int inputSelected = scanner.nextInt();
                    if (inputSelected >= 0 && inputSelected < restaurants.size()) {
                        int i = 1;
                        for (Restaurant restaurant: restaurants) {
                            if (i == inputSelected) {
                                // show menu
                                for (Recipe recipe : menu.getRecipes()) {
                                    System.out.println("Name : " + recipe.getName() + ", Price : " + recipe.getPrice());
                                }

                                scanner.skip("\\R?");
                                System.out.println("Item Name : ");
                                String recipeSelected = scanner.nextLine();
                                System.out.println("Quantity : ");
                                double qty = scanner.nextDouble();

                                restaurant.placeOrder(recipeSelected, qty);
                                break;
                            }
                            i++;
                        }
                    } else {
                        System.out.println("Invalid Restaraunt Selected");
                    }
                    currentCommand = CommandType.NO_COMMAND;
                } else if (currentCommand == CommandType.ORDER_MULTIPLE_INGREDIENTS) {
                    ingredientHandler.isPossibleToOrderIngredients(insufficientIngredients, availableMoney);
                    //purchaseIngredients(insufficientIngredients);
                    currentCommand = CommandType.FINALIZE_ORDER;
                }

                if (currentCommand == CommandType.EXIT) {
                    System.exit(0);
                }
            } catch (InsufficientIngredientException ex) {
                insufficientIngredients = ex.getInsufficientIngredients();
                currentCommand = CommandType.ORDER_MULTIPLE_INGREDIENTS;
            } catch (InsufficientMoneyException ex) {
                System.out.println(ex.getMessage());
                currentCommand = CommandType.NO_COMMAND;
            } catch (Exception ex) {  //catches the error and stay at same command
                System.out.println(ex.getMessage());
                ex.printStackTrace();
                currentCommand = CommandType.NO_COMMAND;
            }
        }
//        public static Ingredient selectIngredients() {
//            Scanner scanner = new Scanner(System.in);
//            String ingredientName = scanner.nextLine();
//            for (int i = 0; i < ingredientList.size(); i++) {  // bases on the ingredient name we will try to see which ingredient
//                if (ingredientList.get(i).getName().equals(ingredientName)) {     // is user talking about(use for loop)
//                    return selectIngredients.get(i);
//                }
//            }
//            throw new IngredientNotFoundException("Ingredient " + ingredientName + "Not Found");  // if nothing matches(exception)
//        }

//        public static void selectReceipe() {
//            System.out.println("Please enter the receipe you want to order: ");
//            Scanner scanner = new Scanner(System.in);
//            String receipeName = Scanner.nextLine();
//            for (int i=0; i<receipeList.size(); i++){
//                if(receipeList.get(i).getName().equals(receipeName)){
//                    return receipeList.get(i);
//                }
//            }
//            throw new ReceipeNotFoundException("Receipe " + receipeName + " Not Found");
//        }
    }

    public static void selectRestaraunt(List<Restaurant> restaurants) {
        // display restaraunts
        int i = 1;
        for (Restaurant restaurant: restaurants) {
            System.out.println(i + ") " + restaurant.getName());
            i++;
        }
    }

    public static int displayPrompt() {
        System.out.println("Please select on of the following command");
        System.out.println("0. No command");
        System.out.println("1. View Available Ingredients");
        System.out.println("2. Order Specific Ingredients");
        System.out.println("3. View Total Sales");
        System.out.println("4. View Total Expenses");
        System.out.println("5. View Net Profit");
        System.out.println("6. Place Order");
        System.out.println("7. Exit from the program.");

        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

//    public static double inputIngredientQty () {
//        Scanner scanner = new Scanner(System.in);
//        return Scanner.nextDouble();
//    }

//    public static void purchaseIngredient(Ingredient ingredientOrder, double qtyOrder){
//        for(int i=0; i<ingredientList.size();i++){
//            if(ingredientList.get(i).getName().equals(ingredientOrder.getName())){
//                double oldQty = ingredientList.get(i).getQty();
//                ingredientList.get(i).setQty(oldQty + qtyOrder);
//            }
//        }
//        double totalAmount = ingredientOrdered.getRate()*qtyOrdered;
//        Map<Ingredient, Double> composition = new HashMap<>();
//        composition.put(ingredientOrdered, qtyOrdered);
//        PurchaseOrder po = new PurchaseOrder(totalAmount, composition);
//        expenseList.add(new Expense(totalAmount, po, ExpenseType.PURCHASE));
//        availableMoney -= totalAmount;
//
//    }
//    public static void purchaseIngredients(Map<Ingredient, Double> ingredientsToOrder) {
//        double moneySpent = 0.0;
//        for(int i = 0; i < ingredientList.size(); i++) {
//            if (ingredientsToOrder.containsKey(ingredientList.get(i))) {
//                double oldQty = ingredientList.get(i).getQty();
//                double qtyToOrder = ingredientsToOrder.get(ingredientList.get(i));
//                moneySpent += qtyToOrder*ingredientList.get(i).getRate();
//                ingredientList.get(i).setQty(oldQty + qtyToOrder);
//            }
//        }
//        PurchaseOrder po = new PurchaseOrder(moneySpent, ingredientsToOrder);
//        Expense expense = new Expense(moneySpent, po, ExpenseType.PURCHASE);
//        expenseList.add(expense);
//        availableMoney -= moneySpent;
//    }
//    public static void finalizedOrder(Receipe receipe){
//        Map<Ingredient, Double> composition= receipe.getComposition();
//        for (int i=0; i<ingredientList.size(); i++){
//            Ingredient currentIngredient= ingredientList.get(i);
//            if(composition.containsKey(currentIngredient)){
//                double oldQty= currentIngredient.getQty();
//                ingredientList.get(i).setQty(oldQty- composition.get(currentIngredient));
//            }
//        }
//        Order order= new Order(receipe, receipe.getAmount());
//        Sales sales= new Sales(order, receipe.getAmount());
//        salesList.add(sales);
//        availableMoney += receipe.getAmount();
//    }
}





