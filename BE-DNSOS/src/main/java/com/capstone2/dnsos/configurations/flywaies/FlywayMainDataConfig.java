package com.capstone2.dnsos.configurations.flywaies;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class FlywayMainDataConfig {

    @Value("${spring.datasource.hikari.first.flyway.locations}")
    private String[] flywayLocations;
//    private String flywayLocations = "/dev.db.migration";

    @Value("${spring.datasource.hikari.first.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.hikari.first.username}")
    private String userName;

    @Value("${spring.datasource.hikari.first.password}")
    private String password;

    private static final Logger LOGGER = LoggerFactory.getLogger(FlywayMainDataConfig.class);

    @Bean
    public Flyway flywayMain() {
        try {
            LOGGER.info("Fly way is check version database main .......................");
            Flyway flyway = Flyway.configure()
                    .dataSource(flywayMainDataSource())
                    .locations(flywayLocations)
                    .baselineOnMigrate(true)// default  baseline v1
                    .baselineVersion("0")
                    .load();
            flyway.migrate();// run .sql file, if version is newer

            return flyway;
        } catch (Exception e) {
            LOGGER.error("Fly way is check version database main false.......................");
            return null;
        }
    }


    @Bean
    public DataSource flywayMainDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(datasourceUrl);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }


}
