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
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

//@Configuration(proxyBeanMethods = false)
@Configuration
@EnableJpaRepositories(
        basePackages = "com.capstone2.dnsos.repositories.main",
        entityManagerFactoryRef = "mainEntityManager",
        transactionManagerRef = "mainTransactionManager"
)
public class MainDatabaseConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainDatabaseConfig.class);

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.hikari.first")
    public DataSourceProperties mainDataSourceProperties() {
        try {
            LOGGER.info("Create datasource Properties of main..........................");
            return new DataSourceProperties();
        } catch (Exception e) {
            LOGGER.error("Create datasource Properties of main false.........................." + e.getMessage());
            return null;
        }

    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.hikari.first.configuration")
    public HikariDataSource mainDataSource(
            @Qualifier("mainDataSourceProperties") DataSourceProperties mainDataSourceProperties) {
        try {
            LOGGER.info("configuration of database main successfully..........................");
            return mainDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        } catch (Exception e) {
            LOGGER.error("configuration of database main False........................." + e.getMessage());
            return null;
        }
    }

    @Primary
    @Bean(name = "mainEntityManager")
    public LocalContainerEntityManagerFactoryBean mainEntityManager(
            EntityManagerFactoryBuilder builder,
            @Qualifier("mainDataSource") DataSource dataSource) {
        try {
            LOGGER.info("Create LocalContainerEntityManagerFactoryBean of database main........");
            return builder
                    .dataSource(dataSource)
                    .packages("com.capstone2.dnsos.models.main")
                    .persistenceUnit("main")
                    .build();
        } catch (Exception e) {
            LOGGER.error("Create LocalContainerEntityManagerFactoryBean of database main false............" + e.getMessage());
            return null;
        }
    }

    @Primary
    @Bean(name = "mainTransactionManager")
    public PlatformTransactionManager mainTransactionManager(
            @Qualifier("mainEntityManager") EntityManagerFactory mainEntityManager) {
        try {
            LOGGER.info("Create PlatformTransactionManager of database main ");
            return new JpaTransactionManager(mainEntityManager);
        } catch (Exception e) {
            LOGGER.error("Create PlatformTransactionManager of database main false,............ " + e.getMessage());
            return null;
        }
    }
}