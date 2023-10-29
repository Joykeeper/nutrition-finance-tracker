import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MealManager implements Serializable {
    private Map<String, Meal> availableMeals = new HashMap<>(){
        {
            put("Nothing", new Meal("Nothing", 0, new Ingredient[]{},0));
        }
    };
    public MealManager(Map<String, Meal> availableMeals){
        this.availableMeals = availableMeals;
    }
    public MealManager(){}
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
