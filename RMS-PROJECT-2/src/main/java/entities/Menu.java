package entities;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<Recipe> recipes = null;

    public Menu DeepCopy () {
        List<Recipe> recipeList = new ArrayList<>();
        this.recipes.forEach(recipe -> {
            List<RecipeIngredient> recipeIngredients = new ArrayList<>();
            recipe.getRecipeIngredients().forEach(recipeIngredient -> {
                recipeIngredients.add(new RecipeIngredient(recipeIngredient.getItem(), recipeIngredient.getQuantity()));
            });

            recipeList.add(new Recipe(recipe.getName(), recipe.getPrice(), recipeIngredients));
        });

        Menu menu = new Menu();
        menu.setRecipes(recipeList);
        return menu;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
