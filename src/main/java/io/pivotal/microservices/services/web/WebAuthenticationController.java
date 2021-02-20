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
        System.out.println(user.getUsername());
        boolean successful = authenticationService.loginUser(user);
        if(successful)
            return "redirect:/index";
        else
            return "redirect:/menu";
    }
}
