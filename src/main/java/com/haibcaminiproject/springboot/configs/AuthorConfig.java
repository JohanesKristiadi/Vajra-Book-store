package com.haibcaminiproject.springboot.configs;

import com.haibcaminiproject.springboot.models.Author;
import com.haibcaminiproject.springboot.repositories.AuthorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AuthorConfig {

    @Bean
    CommandLineRunner authorSeeder(AuthorRepository authorRepository) {
        return args -> {
            Author author1 = new Author("J. K. Rowling");
            Author author2 = new Author("Stephen King");
            authorRepository.saveAll(List.of(author1, author2));
        };
    }
}
