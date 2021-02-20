package io.pivotal.microservices.services.authentication;

import io.pivotal.microservices.authentication.UserRepository;
import io.pivotal.microservices.authentication.UsersConfiguration;
import io.pivotal.microservices.services.accounts.AccountsServer;
import io.pivotal.microservices.services.registration.RegistrationServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import java.util.logging.Logger;

@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(UsersConfiguration.class)
public class AuthenticationServer {
    @Autowired
    UserRepository userRepository;

    protected Logger logger = Logger.getLogger(AuthenticationServer.class.getName());


    public static void main(String[] args) {
        if (System.getProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME) == null)
            System.setProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME, "localhost");
        System.setProperty("spring.config.name", "authentication-server");

        SpringApplication.run(AuthenticationServer.class, args);
    }
}
