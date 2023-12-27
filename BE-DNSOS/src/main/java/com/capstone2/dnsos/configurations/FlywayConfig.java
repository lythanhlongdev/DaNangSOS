package com.capstone2.dnsos.configurations;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    @Value("${spring.flyway.locations}")
    private String[] flywayLocations;
//    private String flywayLocations = "/dev.db.migration";

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public Flyway flyway() {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource())
                .locations(flywayLocations)
                .baselineOnMigrate(true)// default  baseline v1
                .baselineVersion("0")
                .load();
        flyway.migrate();// run .sql file, if version is newer
        System.out.println("Fly way is check version database  ........................");
        return flyway;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(datasourceUrl);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }
}
