import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NutritionManager implements Serializable {
    Meal nothing = new Meal("Nothing", 0, new Ingredient[]{},0);
    Map<String, Meal[]> selectedMealsMap = new HashMap<>(){
        {
            put("Monday", new Meal[]{nothing, nothing, nothing});
            put("Tuesday", new Meal[]{nothing, nothing, nothing});
            put("Wednesday", new Meal[]{nothing, nothing, nothing});
            put("Thursday", new Meal[]{nothing, nothing, nothing});
            put("Friday", new Meal[]{nothing, nothing, nothing});
            put("Saturday", new Meal[]{nothing, nothing, nothing});
            put("Sunday", new Meal[]{nothing, nothing, nothing});
        }
    };
    public void selectNoMeal(String day, int numberOfFood){
        selectedMealsMap.get(day)[numberOfFood] = nothing;
    }
    public void selectMeal(Meal meal, String day, int numberOfFood){
        selectedMealsMap.get(day)[numberOfFood] = meal;
    }

    public Map<Meal, Integer> getMealCountMap(){
        Map<Meal, Integer> mealCountMap = new HashMap<>();
        for (Meal[] mealArr: this.selectedMealsMap.values()){
            for (Meal meal: mealArr) {
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
        return this.selectedMealsMap;
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
