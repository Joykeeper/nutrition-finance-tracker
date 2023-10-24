import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Evaluator {
    public static int countTime(ArrayList<Meal> selectedMeals){
        int sum = 0;
        for ( Meal meal: selectedMeals){
            sum += meal.getRequiredTime();
        }
        return sum;
    }
    public static float countMoney(ArrayList<Meal> selectedMeals, ProductManager productManager){
        float money = 0;

        Map<String, Number> ingredients = getIngredientMap(selectedMeals); // +
        //System.out.println(ingredients.keySet());
        for (String ing: ingredients.keySet()) {
            ArrayList<Product> products = findProductsForIngredient(ing, productManager.getAvailableProducts()); // +

            int index = 0;
            for (Product product: products) {
                int amountOfProductsNeeded = (int) ((float) ingredients.get(ing) / product.getIngredient().getAmount());
                money += product.getCost() * amountOfProductsNeeded;

                //System.out.println("Product: " + product.getName() + "; needed amount:" + amountOfProductsNeeded);
                ingredients.put(ing, (float) ingredients.get(ing) - amountOfProductsNeeded*product.getIngredient().getAmount());
                //System.out.println(ingredients.get(ing));

                if ((index == products.size() - 1)&&((float)ingredients.get(ing) > 0)){
                    money += product.getCost();
                    //System.out.println("Product: " + product.getName() + "; needed amount 0 to 1");
                    //amountOfProductsNeeded += 1;
                }
                //System.out.println("Money: " + money + "(+" + product.getCost() + "*" + amountOfProductsNeeded +")");
                index += 1;
            }
        }
        return money;
    }
    private static ArrayList<Product> findProductsForIngredient(String ingredient, ArrayList<Product> products){
        ArrayList<Product> productsForIngredient = new ArrayList<Product>();
        for (Product p: products){
            if (p.getIngredient().getName().equals(ingredient)){
                productsForIngredient.add(p);
            }
        }

        productsForIngredient.sort((p1, p2)
                -> (int) (p2.getIngredient().getAmount() -
                p1.getIngredient().getAmount()));

        return productsForIngredient;
    }
    private static Map<String, Number> getIngredientMap(ArrayList<Meal> selectedMeals){
        Map<String, Number> ingredients = new HashMap<String, Number>();

        for(Meal meal: selectedMeals){
            for (Ingredient ing:meal.getIngredients()){
                if (ingredients.containsKey(ing.getName())){
                    ingredients.put(ing.getName(), ing.getAmount() + (float) ingredients.get(ing.getName()));
                } else {
                    ingredients.put(ing.getName(), ing.getAmount());
                }
            }
        }


        return ingredients;
    }
}
