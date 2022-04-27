package com.haibcaminiproject.springboot.configs;

import com.haibcaminiproject.springboot.models.User;
import com.haibcaminiproject.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.util.List;

@Configuration
public class UserConfig {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner userSeeder(UserRepository userRepository) {
        return args -> {
            User superAdmin = new User("Varrel", "varrel@gmail.com", passwordEncoder.encode("secret"), Instant.now(), Instant.now());
            User admin = new User("Marcel", "marcel@gmail.com", passwordEncoder.encode("secret"), Instant.now(), Instant.now());
            User user = new User("Johanes", "johanes@gmail.com", passwordEncoder.encode("secret"), Instant.now(), Instant.now());
            userRepository.saveAll(List.of(superAdmin, admin, user));
        };
    }
}
