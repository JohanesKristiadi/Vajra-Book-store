package com.haibcaminiproject.springboot.repositories;

import com.haibcaminiproject.springboot.models.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {

    @Query("SELECT b FROM BookCategory as b WHERE b.name = ?1")
    Optional<BookCategory> findByName(String name);
}
