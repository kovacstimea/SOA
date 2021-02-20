package io.pivotal.microservices.orders;

import io.pivotal.microservices.exceptions.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

/**
 * A RESTFul controller for accessing order information.
 */
@RestController
public class OrdersController {
    protected Logger logger = Logger.getLogger(OrdersController.class.getName());
    protected OrderRepository orderRepository;

    /**
     * Create an instance plugging in the repository of Orders.
     */
    @Autowired
    public OrdersController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        logger.info("OrderRepository says system has " + orderRepository.countOrders() + " orders");
    }

    /**
     * Fetch an order with the specified order number.
     */
    @RequestMapping("/orders/{orderNumber}")
    public Order byNumber(@PathVariable("orderNumber") String orderNumber) {

        logger.info("orders-service byNumber() invoked: " + orderNumber);
        Order order = orderRepository.findByNumber(orderNumber);
        logger.info("orders-service byNumber() found: " + order);

        if (order == null)
            throw new OrderNotFoundException(orderNumber);
        else {
            return order;
        }
    }

    /**
     * Fetch orders with the specified client name. A partial case-insensitive match
     * is supported. So <code>http://.../orders/client/a</code> will find any
     * orders with upper or lower case 'a' in their client name.
     */
    @RequestMapping("/orders/client/{name}")
    public List<Order> byName(@PathVariable("name") String partialName) {
        logger.info("orders-service byName() invoked: " + orderRepository.getClass().getName() + " for " + partialName);

        List<Order> orders = orderRepository.findByClientContainingIgnoreCase(partialName);
        logger.info("orders-service byName() found: " + orders);

        if (orders == null || orders.size() == 0)
            throw new OrderNotFoundException(partialName);
        else {
            return orders;
        }
    }

    @RequestMapping("/orders")
    public List<Order> allOrders() {
        List<Order> orders = orderRepository.findAll();

        if (orders == null || orders.size() == 0)
            throw new OrderNotFoundException("empty order list");
        else {
            return orders;
        }
    }

    @RequestMapping(value = "/orders/deleteOrder/{orderNumber}")
    public String deleteOrder(@PathVariable("orderNumber") String orderNumber) {
        orderRepository.deleteByNumber(orderNumber);
        return "menu";
    }
}
