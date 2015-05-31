package controller;

import dao.CartDAO;
import model.Order;
import model.OrderAndCart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

/**
 * Created by Somesha on 30/05/2015.
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping(method = RequestMethod.GET)
    public String adminOrderHistory(Model model, @ModelAttribute("orders") ArrayList orders) {


        ArrayList<OrderAndCart> orderList = CartDAO.getInstance().getAllCart();

        orders.addAll(orderList);

        model.addAttribute("orderList", orderList);


        return "admin";
    }

    @RequestMapping(value="/changeStatus", method = RequestMethod.POST)
    public String changeStatus(Model model, @RequestParam("orderId") int orderId, @ModelAttribute("orders") ArrayList orders) {


       /* ArrayList<OrderAndCart> orderList = CartDAO.getInstance().getAllCart();

        orders.addAll(orderList);

        model.addAttribute("orderList", orderList);*/

        Order selectedOrder = CartDAO.getInstance().getOrder(orderId);
        CartDAO.getInstance().changeStatus(selectedOrder);

        return "redirect:/admin";

    }

}
