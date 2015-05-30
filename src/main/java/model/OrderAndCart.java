package model;

import java.util.ArrayList;

/**
 * Created by Arun on 30-05-2015.
 */
public class OrderAndCart {

    private Order order;
    private ArrayList<CartItem> cartItems;

    public OrderAndCart(Order order, ArrayList<CartItem> cartItems) {
        this.order = order;
        this.cartItems = cartItems;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
