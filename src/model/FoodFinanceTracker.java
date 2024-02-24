package model;

import model.Ingredient;
import model.Meal;
import service.IngredientService;
import service.MealService;
import service.NutritionService;
import service.ProductService;

import java.io.*;
import java.util.HashMap;

public class FoodFinanceTracker{
    public IngredientService ingredientManager;
    public ProductService productManager;
    public MealService mealManager;
    public NutritionService nutritionManager;
    public FoodFinanceTracker(){
        this.ingredientManager = new IngredientService();
        this.mealManager = new MealService();
        this.productManager = new ProductService();
        this.nutritionManager = new NutritionService();
    }
    public void run() {

        mealManager.addMeal(new Meal("Scrambled eggs", 25,
                new HashMap<>() {
                {
                    put(new Ingredient("Egg"), 2f);
                    put(new Ingredient("Milk"), 1.25f);
                    put(new Ingredient("Salt"),0.25f);
                }
                }, 3));
        mealManager.addMeal(new Meal("Sandwich", 5,
                new HashMap<>() {
                    {
                        put(new Ingredient("Bread"), 4f);
                        put(new Ingredient("Cheese"), 1.25f);
                    }
                }, 5));

//        for (model.Meal meal:nutritionManager.getMealCountMap().keySet()) {
//                System.out.println(meal.getName() + " " + nutritionManager.getMealCountMap().get(meal));
//        }

        //System.out.println(model.Evaluator.countMoney(nutritionManager.getCookedMeals(), productManager));
        //System.out.println(model.Evaluator.countTime(nutritionManager.getCookedMeals()));
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

            this.mealManager = (MealService) ois.readObject();
            this.productManager = (ProductService) ois.readObject();
            this.ingredientManager = (IngredientService) ois.readObject();
            this.nutritionManager = (NutritionService) ois.readObject();
            ois.close();

        } catch(Exception ex) {ex.printStackTrace();}
    }
}
