package io.pivotal.microservices.services.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
public class WebAuthenticationService {
    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    protected String serviceUrl;

    protected Logger logger = Logger.getLogger(WebAuthenticationService.class.getName());

    public WebAuthenticationService(String serviceUrl) {
        this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl : "http://" + serviceUrl;
    }

    public boolean loginUser(User user){
        try {
            System.out.println("URL is" + this.serviceUrl);
            ResponseEntity<String> result = restTemplate.postForEntity(serviceUrl + "/login", user, String.class);
            System.out.println(result.getBody());
            System.out.println("sent");
            if(result.getBody().equals("true"))
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }
}
