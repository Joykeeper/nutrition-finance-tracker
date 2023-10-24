import java.util.ArrayList;

public class ProductManager {
    private ArrayList<Product> availableProducts;
    public ProductManager(ArrayList<Product> availableProducts){
        this.availableProducts = availableProducts;
    }
    public ProductManager(){}
    public void addProduct(Product product){
        this.availableProducts.add(product);
    }
    public void removeProduct(Product product){
        this.availableProducts.remove(product);
    }
    public ArrayList<Product> getAvailableProducts(){
        return this.availableProducts;
    }
}
