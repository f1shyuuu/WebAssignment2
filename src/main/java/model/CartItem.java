package model;

/**
 * Created by fish on 15/5/25.
 */
public class CartItem {
    private Product product;
    private int quantity;
    public CartItem() {

    }

    public CartItem(Product p, int q){
        this.product = p;
        this.quantity = q;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuentity(){
        quantity++;
    }

    public void decreaseQuantity() {
        quantity--;
    }
}
