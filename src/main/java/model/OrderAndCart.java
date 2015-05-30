package model;

/**
 * Created by Arun on 30-05-2015.
 */
public class OrderAndCart {

    private Order order;
    private Cart cart;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Cart getCart() {
        return cart;
    }

    public OrderAndCart(Order order, Cart cart) {
        this.order = order;
        this.cart = cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
