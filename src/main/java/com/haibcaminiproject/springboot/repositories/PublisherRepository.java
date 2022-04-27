package com.haibcaminiproject.springboot.repositories;

import com.haibcaminiproject.springboot.models.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    @Query("SELECT p FROM Publisher p WHERE p.name = ?1")
    Optional<Publisher> findByName(String name);
}
