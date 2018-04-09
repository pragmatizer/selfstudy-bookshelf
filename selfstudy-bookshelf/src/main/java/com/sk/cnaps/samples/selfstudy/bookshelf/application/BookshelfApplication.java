package com.sk.cnaps.samples.selfstudy.bookshelf.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.sk.cnaps.*"})
@EnableJpaRepositories(basePackages = {"com.sk.cnaps.*"})
@EntityScan(basePackages = {"com.sk.cnaps.*"})
@SpringBootApplication
public class BookshelfApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookshelfApplication.class, args);
    }
}
