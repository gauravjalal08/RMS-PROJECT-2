package io;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.*;
import exceptions.InvalidIngredientException;
import exceptions.ReceipeNotFoundException;

public class ReceipeIO {
    public static List<Recipe> readAllReceipes(String filePath) throws FileNotFoundException {
        List<String> lines = CustomFileReader.readFile(filePath);
//        List<Receipe> receipeList = new ArrayList<>();

        List<Recipe> recipes = new ArrayList<>();

        for(String line: lines) {
            String[] splitLine = line.split(" ");
            String recipeName = splitLine[0];
            double amount = Double.parseDouble(splitLine[1]);
            List<RecipeIngredient> recipeIngredients = new ArrayList<>();

            for (int i = 2; i < splitLine.length; i += 2) {
                String ingredientName = splitLine[i];
                double qty = Double.parseDouble(splitLine[i+1]);

                recipeIngredients.add(new RecipeIngredient(ingredientName, qty));
            }

            recipes.add(new Recipe(recipeName, amount, recipeIngredients));
            }

        return recipes;
        }
}