import java.util.*;

public class Evaluator {
    public static int countTime(ArrayList<Meal> selectedMeals){
        int sum = 0;
        for (Meal meal: selectedMeals){
            sum += meal.getRequiredTime();
        }
        return sum;
    }

    public static float countMoney(ArrayList<Meal> selectedMeals, ProductManager productManager){
        float money = 0;

        Map<String, Number> ingredients = getIngredientMap(selectedMeals); // +
        for (String ing: ingredients.keySet()) {
            ArrayList<Product> products = findProductsForIngredient(ing, productManager.getAvailableProducts()); // +
            money += countMoneyForOneIngredient((float)ingredients.get(ing), products);
        }
        System.out.println(money);
        return money;
    }

    public static ArrayList<Product> findProductsForIngredient(String ingredient, ArrayList<Product> products){
        ArrayList<Product> productsForIngredient = new ArrayList<>();
        for (Product p: products){
            if (p.getIngredient().getName().equals(ingredient)){
                productsForIngredient.add(p);
            }
        }

        productsForIngredient.sort((p1, p2)
                -> (int) (p2.getAmountOfIngredient() -
                p1.getAmountOfIngredient()));

        return productsForIngredient;
    }
    private static Map<String, Number> getIngredientMap(ArrayList<Meal> selectedMeals){
        Map<String, Number> ingredients = new HashMap<>();

        for(Meal meal: selectedMeals){
            for (Ingredient ing:meal.getIngredients().keySet()){
                if (ingredients.containsKey(ing.getName())){
                    ingredients.put(ing.getName(), meal.getIngredients().get(ing) + (float) ingredients.get(ing.getName()));
                } else {
                    ingredients.put(ing.getName(), meal.getIngredients().get(ing));
                }
            }
        }


        return ingredients;
    }
    private static float countMoneyForOneIngredient(Float amountOfIngredient, ArrayList<Product> availableProducts){
        ArrayList<Product> priorityList = makePriorityList(availableProducts);
        return countCostForSet(findBestSet(priorityList, amountOfIngredient));
    }

    public static Map<String, Number> getNeededProducts(ArrayList<Meal> selectedMeals,  ArrayList<Product> availableProducts){
        Map<String, Number> neededProducts = new HashMap<>();

        Map<String, Number> ingredients = getIngredientMap(selectedMeals);
        for (String ing: ingredients.keySet()) {
            ArrayList<Product> products = findProductsForIngredient(ing, availableProducts);
            ArrayList<Product> priorityList = makePriorityList(products);
            Map<Product, Float> productSet = findBestSet(priorityList, (float) ingredients.get(ing));
            for (Product product: productSet.keySet()){
                if (productSet.get(product) != 0){
                    neededProducts.put(product.getName(), productSet.get(product));
                }
            }
        }

        return neededProducts;
    }
    private static ArrayList<Product> makePriorityList(ArrayList<Product> availableProducts){
        // prioritisation algorithm
        Map<Product,Number> productCostPerOne = new HashMap<>();
        for (Product product: availableProducts) {
            productCostPerOne.put(product, product.getCost()/product.getAmountOfIngredient());
        }

        ArrayList<Product> priorityList = new ArrayList<>(availableProducts);

        priorityList.sort((p1, p2) -> {
            float j = ((float) productCostPerOne.get(p1) - (float) productCostPerOne.get(p2));
            if (j == 0f){
                float k = p1.getAmountOfIngredient() - p2.getAmountOfIngredient();
                return k>0? 1: k==0? 0:-1;
            }
            return j>0? 1: -1;
        });

        for (int i = 1; i < priorityList.size(); i++){
            if (priorityList.get(i).getAmountOfIngredient() >= priorityList.get(0).getAmountOfIngredient()){
                priorityList.remove(i);
                i = 1;
            }
        }

        for (Product p: priorityList) {
            System.out.print(p.getName() + " ");
        }

        return priorityList;
    }
    private static Map<Product, Float> findBestSet(ArrayList<Product> priorityList, Float amountOfIngredient){
        Map<Map<Product, Float>, Float> setCostMap = new HashMap<>();

        // main algo with getting going through all priorities
        float cost = 0;
        Map<Product, Float> productSet = new HashMap<>();
        for (int i = 1; i <= priorityList.size(); i++) {
            productSet = setUpASet(new ArrayList<>(priorityList.subList(0 , i)), amountOfIngredient);
            setCostMap.put(productSet, countCostForSet(productSet));
            cost = countCostForSet(productSet);
        }

        float min = cost;
        Map<Product, Float> optimalSet = productSet;
        for (Map<Product, Float> set: setCostMap.keySet()) {
            System.out.println(setCostMap.get(set));
            if (min > setCostMap.get(set)){
                min = setCostMap.get(set);
                optimalSet = set;
            }
        }
        return optimalSet;
    }
    private static Map<Product, Float> setUpASet(ArrayList<Product> priorityList, Float amountOfIngredient){
        //sets up a set using given priorityList

        float amountOfProduct;
        Map<Product, Float> neededProducts = new HashMap<>();
        int index = 0;
        for (Product product: priorityList) {
            amountOfProduct = (int)(amountOfIngredient / product.getAmountOfIngredient());
            amountOfIngredient -= amountOfProduct * product.getAmountOfIngredient();
            neededProducts.put(product, amountOfProduct);
            if (index == priorityList.size()-1){
                if (amountOfIngredient % product.getAmountOfIngredient() != 0){
                    neededProducts.put(product, neededProducts.get(product) + 1);
                }
                return neededProducts;
            }
            if ( amountOfIngredient % product.getAmountOfIngredient() == 0){
                return neededProducts;
            }
            index++;
        }
        return neededProducts;
    }
    private static float countCostForSet(Map<Product, Float> set){
        float cost = 0;
        for (Product p: set.keySet()) {
            cost += p.getCost() * set.get(p);
        }
        return cost;
    }
}
