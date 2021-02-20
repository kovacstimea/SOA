package io.pivotal.microservices.services.web;

import io.pivotal.microservices.exceptions.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Hide the access to the microservice inside this local service.
 */
@Service
public class WebOrdersService {
    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    protected String serviceUrl;

    protected Logger logger = Logger.getLogger(WebOrdersService.class.getName());

    public WebOrdersService(String serviceUrl) {
        this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl : "http://" + serviceUrl;
    }

    /**
     * The RestTemplate works because it uses a custom request-factory that uses
     * Ribbon to look-up the service to use. This method simply exists to show this.
     */
    @PostConstruct
    public void demoOnly() {
        // Can't do this in the constructor because the RestTemplate injection
        // happens afterwards.
        logger.warning("The RestTemplate request factory is " + restTemplate.getRequestFactory().getClass());
    }

    public Order findByNumber(String orderNumber) {
        logger.info("findByNumber() invoked: for " + orderNumber);
        try {
            return restTemplate.getForObject(serviceUrl + "/orders/{number}", Order.class, orderNumber);
        } catch (Exception e) {
            logger.severe(e.getClass() + ": " + e.getLocalizedMessage());
            return null;
        }
    }

    public List<Order> byNameContains(String name) {
        logger.info("byNameContains() invoked:  for " + name);
        Order[] orders = null;

        try {
            orders = restTemplate.getForObject(serviceUrl + "/orders/client/{name}", Order[].class, name);
        } catch (HttpClientErrorException e) { // 404
            // Nothing found
        }

        if (orders == null || orders.length == 0)
            return null;
        else
            return Arrays.asList(orders);
    }

    public Order getByNumber(String orderNumber) {
        Order order = restTemplate.getForObject(serviceUrl + "/orders/{number}", Order.class, orderNumber);

        if (order == null)
            throw new OrderNotFoundException(orderNumber);
        else
            return order;
    }

    public List<Order> allOrders() {
        logger.info("allOrders() invoked");
        Order[] orders = null;

        try {
            orders = restTemplate.getForObject(serviceUrl + "/orders", Order[].class);
        } catch (HttpClientErrorException e) { // 404
            // Nothing found
        }

        if (orders == null || orders.length == 0)
            return null;
        else
            return Arrays.asList(orders);
    }

    public void deleteOrder(String orderNumber){
        try {
            restTemplate.getForObject(serviceUrl + "/orders/deleteOrder/{orderNumber}", Order.class, orderNumber);
        } catch (Exception e) {
        }
    }
}
