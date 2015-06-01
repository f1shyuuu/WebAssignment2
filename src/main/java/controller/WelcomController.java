package controller;

import dao.CartDAO;
import model.OrderAndCart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.xml.ws.RequestWrapper;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fish on 15/5/27.
 */
@Controller
@RequestMapping("/")
public class WelcomController {



    @RequestMapping(method = RequestMethod.GET )
    public String login(HttpSession session) {

        Object check = session.getAttribute("isValid");

        if(check == null){
            return "login";
        }

        return "redirect:display";
    }

    @RequestMapping(value = "display")
    public String welcome(ModelMap modelMap) {
        CartDAO cartDAO=CartDAO.getInstance();

        ArrayList<OrderAndCart> displayedOrder=cartDAO.getUserCart(1);
        System.out.println("All the orders :");
        System.out.println(displayedOrder);

        modelMap.addAttribute("userOrders",displayedOrder);
        return "FindProducts";
    }

    @RequestMapping(value="authenticate" , method = RequestMethod.POST)
    public ModelAndView authenticate(@RequestParam("username") String userId, @RequestParam("password") String password){

        HashMap<String, String> usersMap =  CartDAO.getInstance().authenticate(userId, password);

        if(usersMap.get("isValid")=="true" && usersMap.get("isAdmin")=="false"){

            return new ModelAndView("redirect:display");
        }
        else if (usersMap.get("isValid")=="true" && usersMap.get("isAdmin")=="true"){

            return new ModelAndView("redirect:/admin");
        }

            return new ModelAndView("loginerror");

    }


}
