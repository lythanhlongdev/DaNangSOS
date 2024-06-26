package com.capstone2.dnsos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication()
@EntityScan(basePackages = {"com.capstone2.dnsos.models.main", "com.capstone2.dnsos.models.address"})
public class BeDnsosApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeDnsosApplication.class, args);
    }
}
