import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NutritionManager implements Serializable {
    Meal nothing = new Meal("Nothing", 0, new HashMap<>(),0);
    Map<String, ComplexMeal[]> selectedMealsMap = new HashMap<>(){
        {
            put("Monday", new ComplexMeal[]{new ComplexMeal(), new ComplexMeal(), new ComplexMeal()});
            put("Tuesday", new ComplexMeal[]{new ComplexMeal(), new ComplexMeal(), new ComplexMeal()});
            put("Wednesday", new ComplexMeal[]{new ComplexMeal(), new ComplexMeal(), new ComplexMeal()});
            put("Thursday", new ComplexMeal[]{new ComplexMeal(), new ComplexMeal(), new ComplexMeal()});
            put("Friday", new ComplexMeal[]{new ComplexMeal(), new ComplexMeal(), new ComplexMeal()});
            put("Saturday", new ComplexMeal[]{new ComplexMeal(), new ComplexMeal(), new ComplexMeal()});
            put("Sunday", new ComplexMeal[]{new ComplexMeal(), new ComplexMeal(), new ComplexMeal()});
        }
    };
    public void deselectMeal(Meal meal, String day, int numberOfFood){
        selectedMealsMap.get(day)[numberOfFood].removeMeal(meal);
    }
    public void selectMeal(Meal meal, String day, int numberOfFood){
        selectedMealsMap.get(day)[numberOfFood].addMeal(meal);
    }

    private static Meal[] splitComplexIntoSimpleMeals(ComplexMeal[] complexMeals){
        Meal[] simpleMeals = new Meal[complexMeals.length];
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
