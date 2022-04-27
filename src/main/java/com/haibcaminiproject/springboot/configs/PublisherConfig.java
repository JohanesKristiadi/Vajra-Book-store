package com.haibcaminiproject.springboot.configs;

import com.haibcaminiproject.springboot.models.Publisher;
import com.haibcaminiproject.springboot.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PublisherConfig {

    @Bean
    CommandLineRunner publisherSeeder(PublisherRepository publisherRepository) {
        return args -> {
            Publisher publisher1 = new Publisher("Penguin Random House");
            Publisher publisher2 = new Publisher("HarperCollins");
            publisherRepository.saveAll(List.of(publisher1, publisher2));
        };
    }
}
