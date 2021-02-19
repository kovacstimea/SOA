package io.pivotal.microservices.services.web;

import io.pivotal.microservices.exceptions.ProductNotFoundException;
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
public class WebProductsService {
    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    protected String serviceUrl;

    protected Logger logger = Logger.getLogger(WebProductsService.class.getName());

    public WebProductsService(String serviceUrl) {
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

    public Product findByNumber(String productNumber) {
        logger.info("findByNumber() invoked: for " + productNumber);
        try {
            return restTemplate.getForObject(serviceUrl + "/products/{number}", Product.class, productNumber);
        } catch (Exception e) {
            logger.severe(e.getClass() + ": " + e.getLocalizedMessage());
            return null;
        }
    }

    public List<Product> byNameContains(String name) {
        logger.info("byNameContains() invoked:  for " + name);
        Product[] products = null;

        try {
            products = restTemplate.getForObject(serviceUrl + "/products/name/{name}", Product[].class, name);
        } catch (HttpClientErrorException e) { // 404
            // Nothing found
        }

        if (products == null || products.length == 0)
            return null;
        else
            return Arrays.asList(products);
    }

    public Product getByNumber(String productNumber) {
        Product product = restTemplate.getForObject(serviceUrl + "/products/{number}", Product.class, productNumber);

        if (product == null)
            throw new ProductNotFoundException(productNumber);
        else
            return product;
    }
}
