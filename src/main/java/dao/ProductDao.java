package dao;

import model.Product;

import java.util.List;

/**
 * Created by fish on 15/5/26.
 */
public interface ProductDao {
    public List<Product> getAllProducts();
    public void addProduct(Product p);
    public Product getProductById(int productId);
    public void updateProduct(Product p);
    public void deleteProduct(int productId);
}
