package com.capstone2.dnsos.configurations.databases;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
@EnableJpaRepositories(
        basePackages = "com.capstone2.dnsos.repositories.address",
        entityManagerFactoryRef = "addressEntityManager",
        transactionManagerRef = "addressTransactionManager"
)
public class AddressDatabaseConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressDatabaseConfig.class);

    @Bean
    @ConfigurationProperties("spring.datasource.hikari.second")
    public DataSourceProperties addressDataSourceProperties() {
        try {
            LOGGER.info("Create datasource Properties of address..........................");
            return new DataSourceProperties();
        } catch (Exception e) {
            LOGGER.error("Create datasource Properties of address false.........................." + e.getMessage());
            return null;
        }

    }

    @Bean
    @ConfigurationProperties("spring.datasource.hikari.second.configuration")
    public HikariDataSource addressDataSource(
            @Qualifier("addressDataSourceProperties") DataSourceProperties addressDataSourceProperties) {
        try {
            LOGGER.info("configuration of database address successfully..........................");
            return addressDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        } catch (Exception e) {
            LOGGER.error("configuration of database address False........................." + e.getMessage());
            return null;
        }
    }

    @Bean(name = "addressEntityManager")
    public LocalContainerEntityManagerFactoryBean addressEntityManager(
            EntityManagerFactoryBuilder builder,
            @Qualifier("addressDataSource") DataSource dataSource) {
        try {
            LOGGER.info("Create LocalContainerEntityManagerFactoryBean of database address........");
            return builder
                    .dataSource(dataSource)
                    .packages("com.capstone2.dnsos.models.address")
                    .persistenceUnit("address")
                    .build();
        } catch (Exception e) {
            LOGGER.error("Create LocalContainerEntityManagerFactoryBean of database address false............" + e.getMessage());
            return null;
        }
    }


    @Bean(name = "addressTransactionManager")
    public PlatformTransactionManager addressTransactionManager(
            @Qualifier("addressEntityManager") EntityManagerFactory addressEntityManager) {
        try {
            LOGGER.info("Create PlatformTransactionManager of database address ");
            return new JpaTransactionManager(addressEntityManager);
        } catch (Exception e) {
            LOGGER.error("Create PlatformTransactionManager of database address false,............ " + e.getMessage());
            return null;
        }
    }
}
