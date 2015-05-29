package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.ws.RequestWrapper;

/**
 * Created by fish on 15/5/27.
 */
@Controller
@RequestMapping("/")
public class WelcomController {
    @RequestMapping(method = RequestMethod.GET)
    public String welcome() {
        return "FindProducts";
    }
}
