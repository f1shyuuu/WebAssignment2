package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fish on 15/5/25.
 */
public class Cart {
    private Map<Long, CartItem> carts;

    public Cart() {
        carts = new HashMap<Long, CartItem>();
    }

    public Map<Long, CartItem> getCarts() {
        return carts;
    }

    public void setCarts(Map<Long, CartItem> carts) {
        this.carts = carts;
    }

    public void addItem(Product p) {
        long productId = p.getProductId();
        CartItem ci = carts.get(productId);
        if (ci == null){
            carts.put(productId, new CartItem(p,1));
        }
        else {
            ci.increaseQuentity();

        }
    }

    public void removeItem(Product p) {
        long productId = p.getProductId();
        CartItem ci = carts.get(productId);
        if (ci.getQuantity() == 1){

            carts.remove(productId);
        }
        else {
            ci.decreaseQuantity();
        }
    }

    public Collection<CartItem> getItems() {
        return carts.values();
    }

    public double getTotal(){
        double total = 0.0;
        for (CartItem ci: carts.values()){
            total += ci.getQuantity() * ci.getProduct().getPrice();
        }
        return total;
    }


}
