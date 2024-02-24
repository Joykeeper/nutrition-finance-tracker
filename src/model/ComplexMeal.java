package model;

import model.Meal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ComplexMeal implements Serializable {
    Meal nothing = new Meal("Nothing", 0, new HashMap<>(),0);
    Map<Meal, Integer> meals = new HashMap<>(){
        {
            put(nothing, 1);
        }
    };
    public void addMeal(Meal meal){
        meals.remove(nothing);

        if (meals.containsKey(meal)){
            meals.put(meal, meals.get(meal)+1);
            return;
        }
        meals.put(meal, 1);
    }

    public void removeMeal(Meal meal){
        if (meals.get(meal) > 1){
            meals.put(meal, meals.get(meal)-1);
            return;
        }
        meals.remove(meal);

        if (meals.isEmpty()){
            meals.put(nothing, 1);
        }
    }

    public Map<Meal, Integer> getMeals(){
        return this.meals;
    }
}
