package com.haibcaminiproject.springboot.configs;

import com.haibcaminiproject.springboot.models.BookCategory;
import com.haibcaminiproject.springboot.repositories.BookCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.List;

@Configuration
public class BookCategoryConfig {

    @Bean
    CommandLineRunner bookCategorySeeder(BookCategoryRepository bookCategoryRepository) {
        return args -> {
            BookCategory science = new BookCategory("Fiction", Instant.now(), Instant.now());
            BookCategory history = new BookCategory("History", Instant.now(), Instant.now());
            bookCategoryRepository.saveAll(List.of(science, history));
        };
    }
}
