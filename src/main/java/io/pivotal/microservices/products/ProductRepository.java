package io.pivotal.microservices.products;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Repository for Product data implemented using Spring Data JPA.
 */
public interface ProductRepository extends Repository<Product, Long> {
    /**
     * Find a product with the specified product number.
     */
    public Product findByNumber(String productNumber);

    /**
     * Find products whose name contains the specified string
     */
    public List<Product> findByNameContainingIgnoreCase(String partialName);

    /**
     * Fetch the number of products known to the system.
     */
    @Query("SELECT count(*) from Product")
    public int countProducts();
}
