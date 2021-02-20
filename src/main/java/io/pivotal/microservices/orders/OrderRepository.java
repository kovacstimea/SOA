package io.pivotal.microservices.orders;

import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository for Order data implemented using Spring Data JPA.
 */
public interface OrderRepository extends Repository<Order, Long> {
    /**
     * Find a order with the specified order number.
     */
    public Order findByNumber(String orderNumber);

    /**
     * Find orders whose client name contains the specified string
     */
    public List<Order> findByClientContainingIgnoreCase(String partialName);

    /**
     * Fetch the number of orders known to the system.
     */
    @Query("SELECT count(*) from Order")
    public int countOrders();

    /**
     * Find all orders
     */
    public List<Order> findAll();

    /**
     * Delete a specific order by order number
     */
    @Transactional
    public void deleteByNumber(String orderNumber);
}
