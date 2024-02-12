import java.io.Serializable;

public class Product implements Serializable {
    private final String name;
    private final Ingredient containingIngredient;
    private final float amountOfIngredient;
    private final float cost;

    public Product(String name, Ingredient containingIngredient, float amountOfIngredient, float cost){
        this.name = name;
        this.containingIngredient = containingIngredient;
        this.amountOfIngredient = amountOfIngredient;
        this.cost = cost;
    }

    public String getName(){
        return this.name;
    }

    public Ingredient getIngredient(){
        return this.containingIngredient;
    }
    public float getAmountOfIngredient(){return this.amountOfIngredient;}

    public float getCost(){
        return this.cost;
    }
}
