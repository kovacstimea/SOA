package io.pivotal.microservices.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
public class UsersController {

    protected Logger logger = Logger.getLogger(UsersController.class.getName());
    protected UserRepository userRepository;

    @Autowired
    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
        logger.info("UserRepository");
    }

    @RequestMapping("/login/{user}/{password}")
    public String loginUser(@PathVariable("user") String username, @PathVariable("password") String password){
        String number = userRepository.findByUsernameAndPassword(username,password).getNumber();
        System.out.println(number);
        if(number != null)
        {
            return "true";
        }
        else{
            return "false";
        }
    }
}
