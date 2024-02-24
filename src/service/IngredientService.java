package service;

import java.io.Serializable;
import java.util.ArrayList;

public class IngredientService implements Serializable {
    private ArrayList<String> availableIngredients = new ArrayList<>(){
        {
            add("Egg");
            add("Bread");
            add("Cheese");
            add("Milk");
            add("Salt");
        }
    };
    public IngredientService(ArrayList<String> availableIngredients){
        this.availableIngredients = availableIngredients;
    }
    public IngredientService(){}
    public void addIngredient(String ingredient){
        this.availableIngredients.add(ingredient);
    }
    public void removeIngredient(String ingredient){
        this.availableIngredients.remove(ingredient);
    }
    public ArrayList<String> getAvailableIngredients(){
        return this.availableIngredients;
    }
}
