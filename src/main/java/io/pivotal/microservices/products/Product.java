package io.pivotal.microservices.products;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Persistent product entity with JPA markup. Products are stored in an H2
 * relational database.
 */
@Entity
@Table(name = "T_PRODUCT")
public class Product implements Serializable{
    private static final long serialVersionUID = 1L;

    public static Long nextId = 0L;

    @Id
    protected Long id;

    protected String number;

    @Column(name = "name")
    protected String name;

    protected BigDecimal price;

    /**
     * This is a very simple, and non-scalable solution to generating unique
     * ids. Not recommended for a real application. Consider using the
     * <tt>@GeneratedValue</tt> annotation and a sequence to generate ids.
     */
    protected static Long getNextId() {
        synchronized (nextId) {
            return nextId++;
        }
    }

    /**
     * Default constructor for JPA only.
     */
    protected Product() {
        price = BigDecimal.ZERO;
    }

    public Product(String number, String name) {
        id = getNextId();
        this.number = number;
        this.name = name;
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

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return number + " [" + name + "]: $" + price;
    }
}

