package service;

import model.Meal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MealService implements Serializable {
    private Map<String, Meal> availableMeals = new HashMap<>(){
        {
            put("Nothing", new Meal("Nothing", 0, new HashMap<>(),0));
        }
    };
    public MealService(Map<String, Meal> availableMeals){
        this.availableMeals = availableMeals;
    }
    public MealService(){}
    public void addMeal(Meal meal){
        this.availableMeals.put(meal.getName(), meal);
    }
    public void removeMeal(Meal meal){
        this.availableMeals.remove(meal.getName());
    }
    public Map<String, Meal> getAvailableMeals(){
        return this.availableMeals;
    }
}
