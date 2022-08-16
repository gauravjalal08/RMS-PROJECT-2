package entities;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String name = null;
    private double price = 0.0;
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();

    public Recipe (String name, double price, List<RecipeIngredient> recipeIngredients) {
        this.name = name;
        this.recipeIngredients = recipeIngredients;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }
}
