package io.pivotal.microservices.products;

import io.pivotal.microservices.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

/**
 * A RESTFul controller for accessing product information.
 */
@RestController
public class ProductsController {
    protected Logger logger = Logger.getLogger(ProductsController.class.getName());
    protected ProductRepository productRepository;

    /**
     * Create an instance plugging in the repository of Products.
     */
    @Autowired
    public ProductsController(ProductRepository productRepository) {
        this.productRepository = productRepository;
        logger.info("ProductRepository says system has " + productRepository.countProducts() + " products");
    }

    /**
     * Fetch a product with the specified product number.
     */
    @RequestMapping("/products/{productNumber}")
    public Product byNumber(@PathVariable("productNumber") String productNumber) {

        logger.info("products-service byNumber() invoked: " + productNumber);
        Product product = productRepository.findByNumber(productNumber);
        logger.info("products-service byNumber() found: " + product);

        if (product == null)
            throw new ProductNotFoundException(productNumber);
        else {
            return product;
        }
    }

    /**
     * Fetch products with the specified name. A partial case-insensitive match
     * is supported. So <code>http://.../products/name/a</code> will find any
     * products with upper or lower case 'a' in their name.
     */
    @RequestMapping("/products/name/{name}")
    public List<Product> byName(@PathVariable("name") String partialName) {
        logger.info("products-service byName() invoked: " + productRepository.getClass().getName() + " for " + partialName);

        List<Product> products = productRepository.findByNameContainingIgnoreCase(partialName);
        logger.info("products-service byName() found: " + products);

        if (products == null || products.size() == 0)
            throw new ProductNotFoundException(partialName);
        else {
            return products;
        }
    }

    @RequestMapping("/products")
    public List<Product> allProducts() {
        List<Product> products = productRepository.findAll();
        if (products == null || products.size() == 0)
            throw new ProductNotFoundException("empty product list");
        else {
            return products;
        }
    }

    @RequestMapping(value = "/products/deleteProduct/{productNumber}")
    public String deleteProduct(@PathVariable("productNumber") String productNumber) {
        productRepository.deleteByNumber(productNumber);
        return "menu";
    }
}
