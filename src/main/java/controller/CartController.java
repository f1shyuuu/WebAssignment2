package controller;

import model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import dao.CartDAO;
/**
 * Created by Arun on 29-05-2015.
 */
@Controller
@SessionAttributes({"cart","products"})
@RequestMapping("/cart")
public class CartController {

    private List<CartItemJSON> cartData;

    @RequestMapping(value= "checkout", method = RequestMethod.POST)
    public String checkout(@RequestParam("address") String address, @ModelAttribute("cart") Cart cart, @ModelAttribute("products") ArrayList<Product>products) {

        ArrayList<OrderAndCart> orderAndCartItems = new ArrayList<>();

        if(cart!=null) {

            cartData = new ArrayList<CartItemJSON>();


            int quantity = 0;
            int orderId = 0;
            int total = 0;
            double totalCost = 0;
            boolean isSuccess = false;

            String titles = "";
            String quantities = "";
            String prices = "";


            /*orderId = CartDAO.getInstance().addOrder(address, cart, totalCost);

            isSuccess = CartDAO.getInstance().addCart(cart, orderId);*/


            /*orderAndCartItems = CartDAO.getInstance().getUserCart(1);
            orderAndCartItems = CartDAO.getInstance().getAllCart();*/

            orderAndCartItems = CartDAO.getInstance().getUserCart(1);

            for (OrderAndCart orderCart: orderAndCartItems) {
                Order testOrder = orderCart.getOrder();
                CartDAO.getInstance().changeStatus(testOrder);
            }



        }
        return "Cart_Partial";
    }

}
