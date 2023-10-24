import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IngredientManager {
    private ArrayList<String> availableIngredients = new ArrayList<>(){
        {
            add("Egg");
            add("Bread");
            add("Cheese");
            add("Milk");
            add("Salt");
        }
    };
    public IngredientManager(ArrayList<String> availableIngredients){
        this.availableIngredients = availableIngredients;
    }
    public IngredientManager(){}
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
