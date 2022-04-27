package com.haibcaminiproject.springboot.repositories;

import com.haibcaminiproject.springboot.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByAuthorId(Long authorId);

    boolean existsByPublisherId(Long publisherId);

    boolean existsByBookCategoryId(Long bookCategoryId);

    List<Book> findByTitleLike(String title);

    List<Book> findByIsRead(boolean isRead);
}
