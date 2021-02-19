package io.pivotal.microservices.services.products;

import io.pivotal.microservices.products.ProductRepository;
import io.pivotal.microservices.products.ProductsConfiguration;
import io.pivotal.microservices.services.registration.RegistrationServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import java.util.logging.Logger;

/**
 * Run as a micro-service, registering with the Discovery Server (Eureka).
 * <p>
 * Note that the configuration for this application is imported from
 * {@link ProductsConfiguration}. This is a deliberate separation of concerns.
 * <p>
 * This class declares no beans and current package contains no components for
 * ComponentScan to find.
 */
@SpringBootApplication
@EnableDiscoveryClient
@Import(ProductsConfiguration.class)
public class ProductsServer {
    @Autowired
    protected ProductRepository productRepository;

    protected Logger logger = Logger.getLogger(ProductsServer.class.getName());

    /**
     * Run the application using Spring Boot and an embedded servlet engine.
     */
    public static void main(String[] args) {
        // Default to registration server on localhost
        if (System.getProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME) == null)
            System.setProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME, "localhost");

        // Tell server to look for products-server.properties or
        // products-server.yml
        System.setProperty("spring.config.name", "products-server");

        SpringApplication.run(ProductsServer.class, args);
    }
}
