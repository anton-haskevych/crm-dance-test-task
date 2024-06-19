package com.example.demo.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FlywayConfiguration {


    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        boolean REINITIALIZE = false;
        return flyway -> {
            if (REINITIALIZE) {
                flyway.clean();
            }
            flyway.migrate();
        };
    }

}
