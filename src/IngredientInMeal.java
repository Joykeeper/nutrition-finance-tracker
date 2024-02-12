public class IngredientInMeal extends Ingredient{
    Ingredient ingredient;
    float amount;
    public IngredientInMeal(Ingredient ing, float amount){
        super(ing.getName());
        this.ingredient = ing;
        this.amount = amount;
    }

    public IngredientInMeal(String ing, float amount){
        super(ing);
        this.ingredient = new Ingredient(ing);
        this.amount = amount;
    }
}