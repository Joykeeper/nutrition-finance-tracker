import java.io.Serializable;

public class Meal implements Serializable {
    private String name;
    private int requiredTime;
    private int portions;
    private Ingredient[] consistsOfIngredients;
    public Meal(String name, int requiredTime, Ingredient[] ingredients, int portions){
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
    public Ingredient[] getIngredients(){
        return this.consistsOfIngredients;
    }
}
