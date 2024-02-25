package service;

import model.ComplexMeal;
import model.Meal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NutritionService implements Serializable {
    Map<String, ComplexMeal[]> selectedMealsMap = new HashMap<>(){
        {
            put("Monday", new ComplexMeal[0]);
            put("Tuesday", new ComplexMeal[0]);
            put("Wednesday", new ComplexMeal[0]);
            put("Thursday", new ComplexMeal[0]);
            put("Friday", new ComplexMeal[0]);
            put("Saturday", new ComplexMeal[0]);
            put("Sunday", new ComplexMeal[]{new ComplexMeal(), new ComplexMeal(), new ComplexMeal()});
        }
    };
    public void addComplexMeal(ComplexMeal meal, String day, int numberOfFood){
        if (numberOfFood >= selectedMealsMap.get(day).length){
            ComplexMeal[] oldCM = selectedMealsMap.get(day);
            ComplexMeal[] newCM = new ComplexMeal[selectedMealsMap.get(day).length+1];
            for (int i = 0; i < oldCM.length; i++) {
                newCM[i] =oldCM[i];
            }
            newCM[newCM.length-1] = meal;
            selectedMealsMap.put(day, newCM);
        } else{
            selectedMealsMap.get(day)[numberOfFood] = meal;
            System.out.println(selectedMealsMap.values());

        }
    }

    public void deleteComplexMeal(String day, int numberOfFood){
        if (this.selectedMealsMap.get(day).length-1 == 0){
            this.selectedMealsMap.put(day, new ComplexMeal[0]);
            return;
        }
        if (this.selectedMealsMap.get(day).length <= numberOfFood){
            return;
        }

        ComplexMeal[] newCM = new ComplexMeal[this.selectedMealsMap.get(day).length-1];

        for (int i = 0; i < numberOfFood; i++) {
            newCM[i] = this.selectedMealsMap.get(day)[i];
        }
        for (int i = numberOfFood+1; i <= newCM.length; i++) {
            newCM[i-1] = this.selectedMealsMap.get(day)[i];
        }
        this.selectedMealsMap.put(day, newCM);
    }
    private static Meal[] splitComplexIntoSimpleMeals(ComplexMeal[] complexMeals){
        System.out.println(Arrays.toString(complexMeals));
        int mealSum = 0;
        for (int i = 0; i < complexMeals.length; i++) {
            for (ComplexMeal cm: complexMeals) {
                for (Meal m : cm.getMeals().keySet()) {
                    mealSum += cm.getMeals().get(m);
                }
            }
        }
        Meal[] simpleMeals = new Meal[mealSum];
        int filled = 0;
        for (ComplexMeal cm: complexMeals) {
            for (Meal m: cm.getMeals().keySet()) {
                for (int i = 0; i < cm.getMeals().get(m); i++) {
                    simpleMeals[filled++] = m;
                }
            }
        }
        return simpleMeals;
    }

    public Map<Meal, Integer> getMealCountMap(){
        Map<Meal, Integer> mealCountMap = new HashMap<>();
        for (ComplexMeal[] complexMealArr: this.selectedMealsMap.values()){
            for (Meal meal: splitComplexIntoSimpleMeals(complexMealArr)) {
                if (mealCountMap.containsKey(meal)){
                    mealCountMap.put(meal, mealCountMap.get(meal)+1);
                } else {
                    mealCountMap.put(meal, 1);
                }
            }
        }
        return mealCountMap;
    }
    public Map<String, Meal[]> getSelectedMealsMap(){
        Map<String, Meal[]> simpleSelectedMeals = new HashMap<>();
        for (String day:this.selectedMealsMap.keySet()) {
            simpleSelectedMeals.put(day,
                    splitComplexIntoSimpleMeals(this.selectedMealsMap.get(day)));
        }
        return simpleSelectedMeals;
    }
    public Map<String, ComplexMeal[]> getSelectedComplexMealsMap(){
        Map<String, ComplexMeal[]> simpleSelectedMeals = new HashMap<>();
        for (String day:this.selectedMealsMap.keySet()) {
            simpleSelectedMeals.put(day,
                    this.selectedMealsMap.get(day));
        }
        return simpleSelectedMeals;
    }
    public Map<Meal, Integer> getLeftOvers(){
        Map<Meal, Integer> leftoversMap = new HashMap<>();
        int amountOfLeftover;
        for (Meal m: getMealCountMap().keySet()) {
            if (m.getName().equals("Nothing")){
                continue;
            }
            if (getMealCountMap().get(m)%m.getPortions() == 0){
                leftoversMap.remove(m);
            } else {
                amountOfLeftover = m.getPortions() - getMealCountMap().get(m)%m.getPortions();
                leftoversMap.put(m, amountOfLeftover);
            }
        }
        return leftoversMap;

    }
    public ArrayList<Meal> getCookedMeals(){
        ArrayList<Meal> cookedMeals = new ArrayList<>();
        for (Meal meal:getMealCountMap().keySet()) {
            float amountToCook = (float) getMealCountMap().get(meal)/ (float) meal.getPortions();
            if ((float)(int) amountToCook != amountToCook){
                amountToCook = (int) amountToCook + 1;
            } else{
                amountToCook = (int) amountToCook;
            }
            for(int i = 0; i < amountToCook; i++){
                cookedMeals.add(meal);
            }
        }
        return cookedMeals;
    }
}
