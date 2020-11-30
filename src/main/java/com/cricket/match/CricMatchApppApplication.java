package com.cricket.match;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages={"com.cricket"})
@ComponentScan(basePackages={"com.cricket"})
@EntityScan( basePackages = {"com.cricket"} )
public class CricMatchApppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CricMatchApppApplication.class, args);
	}

}