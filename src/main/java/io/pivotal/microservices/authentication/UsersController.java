package io.pivotal.microservices.authentication;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class UsersController {

    protected Logger logger = Logger.getLogger(UsersController.class
            .getName());
    protected UserRepository userRepository;

    @RequestMapping("/login")
    public String loginUser(@RequestBody User user){
        User dbUser = new User();
        dbUser.setUsername(user.getUsername());
        dbUser.setPassword(user.getPassword());
        if(userRepository.findByUsernameAndPassword(dbUser.getUsername(),dbUser.getPassword()) != null)
        {
            return "true";
        }
        else{
            return "false";
        }

    }
}
