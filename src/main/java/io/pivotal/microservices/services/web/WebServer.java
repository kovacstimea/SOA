package io.pivotal.microservices.services.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import io.pivotal.microservices.services.registration.RegistrationServer;

/**
 * Accounts web-server. Works as a microservice client, fetching data from the
 * Account-Service. Uses the Discovery Server (Eureka) to find the microservice.
 */

@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class, //
        DataSourceAutoConfiguration.class })
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters = false) // Disable component scanner
public class WebServer {

    /**
     * URL uses the logical name of account-service - upper or lower case, doesn't
     * matter.
     */
    public static final String ACCOUNTS_SERVICE_URL = "http://ACCOUNTS-SERVICE";

    /**
     * URL uses the logical name of product-service - upper or lower case, doesn't
     * matter.
     */
    public static final String PRODUCTS_SERVICE_URL = "http://PRODUCTS-SERVICE";

    /**
     * URL uses the logical name of order-service - upper or lower case, doesn't
     * matter.
     */
    public static final String ORDERS_SERVICE_URL = "http://ORDERS-SERVICE";


    /**
     * Run the application using Spring Boot and an embedded servlet engine.
     */
    public static void main(String[] args) {
        // Default to registration server on localhost
        if (System.getProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME) == null)
            System.setProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME, "localhost");

        // Tell server to look for web-server.properties or web-server.yml
        System.setProperty("spring.config.name", "web-server");
        SpringApplication.run(WebServer.class, args);
    }

    /**
     * A customized RestTemplate that has the ribbon load balancer build in. Note
     * that prior to the "Brixton"
     */
    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * The AccountService encapsulates the interaction with the micro-service.
     */
    @Bean
    public WebAccountsService accountsService() {
        return new WebAccountsService(ACCOUNTS_SERVICE_URL);
    }

    /**
     * The ProductService encapsulates the interaction with the micro-service.
     */
    @Bean
    public WebProductsService productsService() {
        return new WebProductsService(PRODUCTS_SERVICE_URL);
    }

    /**
     * The OrderService encapsulates the interaction with the micro-service.
     */
    @Bean
    public WebOrdersService ordersService() {
        return new WebOrdersService(ORDERS_SERVICE_URL);
    }

    /**
     * Create the controller, passing it the {@link WebAccountsService} to use.
     */
    @Bean
    public WebAccountsController accountsController() {
        return new WebAccountsController(accountsService());
    }

    /**
     * Create the controller, passing it the {@link WebProductsService} to use.
     */
    @Bean
    public WebProductsController productsController() {
        return new WebProductsController(productsService());
    }

    /**
     * Create the controller, passing it the {@link WebOrdersService} to use.
     */
    @Bean
    public WebOrdersController ordersController() {
        return new WebOrdersController(ordersService());
    }

    @Bean
    public HomeController homeController() {
        return new HomeController();
    }
}
