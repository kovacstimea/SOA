package io.pivotal.microservices.orders;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

/**
 * The orders Spring configuration.
 */
@Configuration
@ComponentScan
@EntityScan("io.pivotal.microservices.orders")
@EnableJpaRepositories("io.pivotal.microservices.orders")
@PropertySource("classpath:db-config.properties")
public class OrdersConfiguration {
    protected Logger logger;

    public OrdersConfiguration() {
        logger = Logger.getLogger(getClass().getName());
    }

    /**
     * Creates an in-memory "rewards" database populated with test data for fast
     * testing
     */
    @Bean
    public DataSource dataSource() {
        logger.info("dataSource() invoked");

        // Create an in-memory H2 relational database containing some demo
        // products.
        DataSource dataSource = (new EmbeddedDatabaseBuilder()).addScript("classpath:testdb/schemaOrder.sql").addScript("classpath:testdb/dataOrder.sql").build();

        logger.info("dataSource = " + dataSource);

        // Sanity check
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> orders = jdbcTemplate.queryForList("SELECT number FROM T_ORDER");
        logger.info("System has " + orders.size() + " orders");

        // Populate with random prices
        Random rand = new Random();

        for (Map<String, Object> item : orders) {
            String number = (String) item.get("number");
            BigDecimal total = new BigDecimal(rand.nextInt(10000000) / 100.0).setScale(2, RoundingMode.HALF_UP);
            jdbcTemplate.update("UPDATE T_ORDER SET total = ? WHERE number = ?", total, number);
        }

        return dataSource;
    }
}
