import java.util.*;

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

        Map<String, Number> neededProducts = new HashMap<>();

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
                    amountOfProductsNeeded += 1;
                }
                neededProducts.put(product.getName(), amountOfProductsNeeded);
                //System.out.println("Money: " + money + "(+" + product.getCost() + "*" + amountOfProductsNeeded +")");
                index += 1;
            }
        }
        return money;
    }
    private static ArrayList<Product> findProductsForIngredient(String ingredient, ArrayList<Product> products){
        ArrayList<Product> productsForIngredient = new ArrayList<>();
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
        Map<String, Number> ingredients = new HashMap<>();

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
    public static Map<String, Number> getNeededProducts(ArrayList<Meal> selectedMeals, ArrayList<Product> availableProducts){
        //not done
        Map<String, Number> neededProducts = new HashMap<>();
        return neededProducts;
    }
    public static float countMoneyPlus(ArrayList<Meal> selectedMeals, ProductManager productManager){
        float money = 0;

        Map<String, Number> ingredients = getIngredientMap(selectedMeals); // +
        //System.out.println(ingredients.keySet());
        for (String ing: ingredients.keySet()) {
            ArrayList<Product> products = findProductsForIngredient(ing, productManager.getAvailableProducts()); // +

            money += countMoneyForOneIngredient((float)ingredients.get(ing), products);
        }

        return money;
    }
    private static float countMoneyForOneIngredient(Float amountOfIngredient, ArrayList<Product> availableProducts){

        //Prioritize
        Map<Product,Number> productCostPerOne = new HashMap<>();
        for (Product product: availableProducts) {
            productCostPerOne.put(product, product.getCost()/product.getIngredient().getAmount());
        }

        ArrayList<Product> priorityList = new ArrayList<>(availableProducts);

        priorityList.sort((p1, p2) -> {
//            System.out.println("P1: " + p1.getName() + " P2: " + p2.getName());
            float j = ((float) productCostPerOne.get(p1) - (float) productCostPerOne.get(p2));
            if (j == 0f){
                float k = p1.getIngredient().getAmount() - p2.getIngredient().getAmount();
//                System.out.println("here");
//                System.out.println(k>=0? p1.getName() + " is higher priority": p2.getName() + " is higher priority");
                return k>0? 1: k==0? 0:-1;
            }
//            System.out.println(j<0? p1.getName() + " is higher priority": p2.getName() + " is higher priority");
            return j>0? 1: -1;
        });

        int i = 1;
        while (i != priorityList.size()){
            if (priorityList.get(i).getIngredient().getAmount() >= priorityList.get(0).getIngredient().getAmount()){
                priorityList.remove(i);
                i = 1;
            }
            i++;
        }

        for (Product p: priorityList) {
            System.out.print(p.getName() + " ");
        }

        //2,3
        Map<Product, Number> neededProducts = new HashMap<>();
        float cost = 0;
        int amountOfProduct;
        int index = 0;
        for (Product product: priorityList) {
            amountOfProduct = (int)(amountOfIngredient / product.getIngredient().getAmount());
            cost += amountOfProduct * product.getCost();
            amountOfIngredient -= amountOfProduct * product.getIngredient().getAmount();
            neededProducts.put(product, amountOfProduct);
            if (index == priorityList.size()-1){
                if ( amountOfIngredient % product.getIngredient().getAmount() != 0){
                    cost += product.getCost();
                }
                System.out.println(cost);
                return cost;
            }
            if ( amountOfIngredient % product.getIngredient().getAmount() == 0){
                System.out.println(cost);
                return cost;
            }
            index++;
        }
        return cost;
    }
}
