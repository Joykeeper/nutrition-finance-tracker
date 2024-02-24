package model;

import java.io.Serializable;
import java.util.Map;

public class Meal implements Serializable {
    private final String name;
    private final int requiredTime;
    private final int portions;
    private final Map<Ingredient, Float> consistsOfIngredients;
    public Meal(String name, int requiredTime, Map<Ingredient, Float> ingredients, int portions){
        this.name = name;
        this.requiredTime = requiredTime;
        this.consistsOfIngredients = ingredients;
        this.portions = portions;
    }
    public String getName(){
        return this.name;
    }
    public int getRequiredTime(){
        return this.requiredTime;
    }
    public int getPortions(){
        return this.portions;
    }
    public Map<Ingredient, Float> getIngredients(){
        return this.consistsOfIngredients;
    }

    public String toString(){
        return "model.Meal[" + this.name + "]";
    }
}
