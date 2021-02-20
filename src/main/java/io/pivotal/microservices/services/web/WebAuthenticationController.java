package io.pivotal.microservices.services.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
public class WebAuthenticationController {

    @Autowired
    protected WebAuthenticationService authenticationService;

    protected Logger logger = Logger.getLogger(WebAuthenticationController.class.getName());

    public WebAuthenticationController(WebAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping("/login")
    public String displayLogin(Model model){
        model.addAttribute(new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user){
        String successful = authenticationService.loginUser(user);
        System.out.println(successful);
        if(successful.equals("true")) {
            authenticationService.setLogged(true);
            return "redirect:/home";
        }
        else {
            authenticationService.setLogged(false);
            return "redirect:/login";
        }
    }

    @RequestMapping("/logout")
    public String displayLogout(Model model){
        model.addAttribute(new User());
        authenticationService.setLogged(false);
        return "redirect:/login";
    }
}
