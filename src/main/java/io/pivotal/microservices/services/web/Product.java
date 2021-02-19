package io.pivotal.microservices.services.web;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Product DTO - used to interact with the {@link WebProductsService}.
 */
@JsonRootName("Product")
public class Product {
    protected Long id;
    protected String number;
    protected String name;
    protected BigDecimal price;

    /**
     * Default constructor for JPA only.
     */
    protected Product() {
        price = BigDecimal.ZERO;
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

    protected void setNumber(String productNumber) {
        this.number = productNumber;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price.setScale(2, RoundingMode.HALF_EVEN);
    }

    protected void setPrice(BigDecimal value) {
        price = value;
        price.setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public String toString() {
        return number + " [" + name + "]: $" + price;
    }
}
