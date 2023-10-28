import java.util.ArrayList;

public class FoodFinanceTracker {
    public static void main(String[] args) {
        IngredientManager ingredientManager = new IngredientManager();
        MealManager mealManager = new MealManager();
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

        ProductManager productManager = new ProductManager(new ArrayList<Product>(){
            {
                add(new Product("10 Eggs", new Ingredient("Egg", 10f), 7f));
                add(new Product("2 Cheeses", new Ingredient("Cheese", 2f), 3f));
                add(new Product("15 Cheeses", new Ingredient("Cheese", 15f), 6f));
                add(new Product("Milk 2l", new Ingredient("Milk", 2f), 4f));
                add(new Product("Bread 4", new Ingredient("Bread", 4f), 5.5f));
                add(new Product("Bread 1", new Ingredient("Bread", 1f), 2.5f));
                add(new Product("Salt 1", new Ingredient("Salt", 1f), 1f));
            }
        });

        NutritionManager nutritionManager = new NutritionManager();

        nutritionManager.selectMeal(mealManager.getAvailableMeals().get("Scrambled eggs"), "Monday", 2);
        nutritionManager.selectMeal(mealManager.getAvailableMeals().get("Scrambled eggs"), "Monday", 1);
        nutritionManager.selectMeal(mealManager.getAvailableMeals().get("Scrambled eggs"), "Monday", 0);
        nutritionManager.selectMeal(mealManager.getAvailableMeals().get("Sandwich"), "Tuesday", 0);
        nutritionManager.selectMeal(mealManager.getAvailableMeals().get("Sandwich"), "Tuesday", 1);
        nutritionManager.selectMeal(mealManager.getAvailableMeals().get("Scrambled eggs"), "Tuesday", 2);


        for (Meal meal:nutritionManager.getMealCountMap().keySet()) {
                System.out.println(meal.getName() + " " + nutritionManager.getMealCountMap().get(meal));
        }

        GUI gui = new GUI(nutritionManager, productManager, mealManager, ingredientManager);
        gui.setUpGUI();
        gui.setUpCardLayout();

        //System.out.println(Evaluator.countMoney(nutritionManager.getCookedMeals(), productManager));
        //System.out.println(Evaluator.countTime(nutritionManager.getCookedMeals()));
    }

}
