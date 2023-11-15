import java.io.*;
import java.util.ArrayList;

public class FoodFinanceTracker{
    public IngredientManager ingredientManager;
    public ProductManager productManager;
    public MealManager mealManager;
    public NutritionManager nutritionManager;
    public FoodFinanceTracker(){
        this.ingredientManager = new IngredientManager();
        this.mealManager = new MealManager();
        this.productManager = new ProductManager();
        this.nutritionManager = new NutritionManager();
    }
    public void run() {

        mealManager.addMeal(new Meal("Scrambled eggs", 25,
                new Ingredient[]{
                        new Ingredient("Egg", 2f),
                        new Ingredient("Milk", 1.25f),
                        new Ingredient("Salt", 0.25f),
                }, 3));
        mealManager.addMeal(new Meal("Sandwich", 5,
                new Ingredient[]{
                        new Ingredient("Bread", 3f),
                        new Ingredient("Cheese", 1f),
                }, 5));

//        for (Meal meal:nutritionManager.getMealCountMap().keySet()) {
//                System.out.println(meal.getName() + " " + nutritionManager.getMealCountMap().get(meal));
//        }

        //System.out.println(Evaluator.countMoney(nutritionManager.getCookedMeals(), productManager));
        //System.out.println(Evaluator.countTime(nutritionManager.getCookedMeals()));
    }
    public void save(File file){
        try {
            FileOutputStream fileStream = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fileStream);
            os.writeObject(this.mealManager);
            os.writeObject(this.productManager);
            os.writeObject(this.ingredientManager);
            os.writeObject(this.nutritionManager);
            os.close();

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    public void load(File file){
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fileIn);

            this.mealManager = (MealManager) ois.readObject();
            this.productManager = (ProductManager) ois.readObject();
            this.ingredientManager = (IngredientManager) ois.readObject();
            this.nutritionManager = (NutritionManager) ois.readObject();
            ois.close();

        } catch(Exception ex) {ex.printStackTrace();}
    }
}
