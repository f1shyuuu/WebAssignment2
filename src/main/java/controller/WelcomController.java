package controller;

import dao.CartDAO;
import model.OrderAndCart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.ws.RequestWrapper;
import java.util.ArrayList;

/**
 * Created by fish on 15/5/27.
 */
@Controller
@RequestMapping("/")
public class WelcomController {
    @RequestMapping(method = RequestMethod.GET )
    public String welcome(ModelMap modelMap) {
        CartDAO cartDAO=CartDAO.getInstance();

        ArrayList<OrderAndCart> displayedOrder=cartDAO.getUserCart(1);
        System.out.println("All the orders :");
        System.out.println(displayedOrder);

        modelMap.addAttribute("userOrders",displayedOrder);
        return "FindProducts";
    }
}
