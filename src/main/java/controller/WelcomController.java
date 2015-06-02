package controller;

import dao.CartDAO;
import model.OrderAndCart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
        } else if((String)session.getAttribute("userId") == "none"){
            return "login";
        }

        return "redirect:display";
    }

    @RequestMapping(value = "display")
    public String welcome(ModelMap modelMap, HttpSession session) {
        CartDAO cartDAO=CartDAO.getInstance();

        if(session.getAttribute("userId") == null){
            return "redirect:/";
        }

        int id = Integer.parseInt((String) session.getAttribute("userId"));

        ArrayList<OrderAndCart> displayedOrder=cartDAO.getUserCart(id);
        System.out.println("All the orders :");
        System.out.println(displayedOrder);

        modelMap.addAttribute("userOrders",displayedOrder);
        return "FindProducts";
    }

    @RequestMapping(value="authenticate" , method = RequestMethod.POST)
    public ModelAndView authenticate(@RequestParam("username") String userName, @RequestParam("password") String password, HttpSession session){

        HashMap<String, String> usersMap =  CartDAO.getInstance().authenticate(userName, password);

        session.setAttribute("userName", usersMap.get("userName"));
        session.setAttribute("userId", usersMap.get("userId"));
        session.setAttribute("isValid", usersMap.get("isValid"));
        session.setAttribute("isAdmin", usersMap.get("isAdmin"));


        if(usersMap.get("isValid")=="true" && usersMap.get("isAdmin")=="false"){

            return new ModelAndView("redirect:display");
        }
        else if (usersMap.get("isValid")=="true" && usersMap.get("isAdmin")=="true"){

            return new ModelAndView("redirect:/admin");
        }

            return new ModelAndView("loginerror");

    }

    @RequestMapping(value = "logout", method = RequestMethod.GET )
    public String logout(HttpSession session) {

        session.removeAttribute("userName");
        session.removeAttribute("userId");
        session.removeAttribute("isValid");
        session.removeAttribute("isAdmin");

        return "redirect:/";
    }

    @RequestMapping(value="edit/{orderId}", method = RequestMethod.GET)
    public String edit(@PathVariable("orderId") int orderId){



        return "DisplayProducts";
    }

}
