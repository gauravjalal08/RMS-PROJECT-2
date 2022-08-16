package exceptions;

import java.util.Map;

import entities.Ingredient;

public class InsufficientIngredientException extends RuntimeException {
    private String message = null;
    private Map<Ingredient, Double> insufficientIngredients;

    public InsufficientIngredientException(String message) {
        this.message = message;
    }

    public InsufficientIngredientException(Map<Ingredient, Double> insufficientIngredients) {
        this.insufficientIngredients = insufficientIngredients;
    }

    public Map<Ingredient, Double> getInsufficientIngredients() {
        return insufficientIngredients;
    }
}