import java.io.Serializable;

public class Ingredient implements Serializable {
    private String name;
    private float amount;
    public Ingredient(String name, float amount){
        this.name = name;
        this.amount = amount;
    }
    public String getName(){
        return this.name;
    }
    public float getAmount(){
        return this.amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
