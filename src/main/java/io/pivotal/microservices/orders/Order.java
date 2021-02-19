package io.pivotal.microservices.orders;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Persistent order entity with JPA markup. Orders are stored in an H2
 * relational database.
 */
@Entity
@Table(name = "T_ORDER")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    public static Long nextId = 0L;

    @Id
    protected Long id;

    protected String number;

    @Column(name = "client")
    protected String client;

    protected BigDecimal total;

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
    protected Order() {
        total = BigDecimal.ZERO;
    }

    public Order(String number, String client) {
        id = getNextId();
        this.number = number;
        this.client = client;
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

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return number + " [" + client + "]: $" + total;
    }
}
