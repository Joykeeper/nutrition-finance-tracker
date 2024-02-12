import java.io.Serializable;
import java.util.ArrayList;

public class ProductManager implements Serializable {
    private ArrayList<Product> availableProducts = new ArrayList<>(){
        {
            add(new Product("10 Eggs", new Ingredient("Egg"), 10f, 7f));
            add(new Product("2 Cheeses", new Ingredient("Cheese"), 2f, 3f));
            add(new Product("15 Cheeses", new Ingredient("Cheese"), 15f, 6f));
            add(new Product("Milk 2l", new Ingredient("Milk"), 2f, 4f));
            add(new Product("Bread 4", new Ingredient("Bread"), 4f, 5.5f));
            add(new Product("Bread 1", new Ingredient("Bread"), 1f, 2.5f));
            add(new Product("Salt 1", new Ingredient("Salt"), 1f, 1f));
        }
    };
    public ProductManager(ArrayList<Product> availableProducts){
        this.availableProducts = availableProducts;
    }
    public ProductManager(){}
    public void addProduct(Product product){
        removeProduct(product);
        this.availableProducts.add(product);
    }
    public void removeProduct(Product product){
        for (int i = 0; i < availableProducts.size(); i++) {
            if (availableProducts.get(i).getName().equals(product.getName())){
                availableProducts.remove(i);
                return;
            }
        }
    }
    public ArrayList<Product> getAvailableProducts(){
        return this.availableProducts;
    }
}
