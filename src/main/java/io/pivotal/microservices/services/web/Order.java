package io.pivotal.microservices.services.web;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Order DTO - used to interact with the {@link WebOrdersService}.
 */
@JsonRootName("Order")
public class Order {
    protected Long id;
    protected String number;
    protected String client;
    protected BigDecimal total;

    /**
     * Default constructor for JPA only.
     */
    protected Order() {
        total = BigDecimal.ZERO;
    }

    public long getId() {
        return id;
    }

    /**
     * Set JPA id - for testing and JPA only. Not intended for normal use.
     */
    protected void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    protected void setNumber(String orderNumber) {
        this.number = orderNumber;
    }

    public String getClient() {
        return client;
    }

    protected void setClient(String client) {
        this.client = client;
    }

    public BigDecimal getTotal() {
        return total.setScale(2, RoundingMode.HALF_EVEN);
    }

    protected void setTotal(BigDecimal value) {
        total = value;
        total.setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public String toString() {
        return number + " [" + client + "]: $" + total;
    }
}
