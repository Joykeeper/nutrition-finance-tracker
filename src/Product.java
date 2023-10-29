import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private Ingredient containingIngredient;
    private float cost;

    public Product(String name, Ingredient containingIngredient, float cost){
        this.name = name;
        this.containingIngredient = containingIngredient;
        this.cost = cost;
    }

    public String getName(){
        return this.name;
    }

    public Ingredient getIngredient(){
        return this.containingIngredient;
    }

    public float getCost(){
        return this.cost;
    }
}
