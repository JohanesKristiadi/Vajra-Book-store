package com.haibcaminiproject.springboot.services;

import com.haibcaminiproject.springboot.models.BookCategory;
import com.haibcaminiproject.springboot.repositories.BookCategoryRepository;
import com.haibcaminiproject.springboot.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookCategoryService {

    private final BookCategoryRepository bookCategoryRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BookCategoryService(BookCategoryRepository bookCategoryRepository, BookRepository bookRepository) {
        this.bookCategoryRepository = bookCategoryRepository;
        this.bookRepository = bookRepository;
    }

    public List<BookCategory> getBookCategories() {
        return bookCategoryRepository.findAll();
    }

    public BookCategory getBookCategory(Long bookCategoryId) {
        return bookCategoryRepository.findById(bookCategoryId).orElseThrow(() -> new IllegalStateException("Book Category does not exists"));
    }

    public void addNewBookCategory(BookCategory bookCategory) {
        Optional<BookCategory> newBookCategory = bookCategoryRepository.findByName(bookCategory.getName());

        if (newBookCategory.isPresent()) {
            throw new IllegalStateException("Name must be unique");
        }
        bookCategory.setCreatedAt(Instant.now());
        bookCategory.setUpdatedAt(Instant.now());
        bookCategoryRepository.save(bookCategory);
    }

    @Transactional
    public void updateBookCategory(Long bookCategoryId, String name) {
        BookCategory bookCategory = bookCategoryRepository.findById(bookCategoryId).orElseThrow(() -> new IllegalStateException("Book category does not exists"));

        if (name != null && name.length() > 0 && !Objects.equals(bookCategory.getName(), name)) {
            Optional<BookCategory> otherBookCategory = bookCategoryRepository.findByName(name);
            if (otherBookCategory.isPresent()) {
                throw new IllegalStateException("Name taken");
            }
            bookCategory.setName(name);
            bookCategory.setUpdatedAt(Instant.now());
        }
    }

    public void deleteBookCategory(Long bookCategoryId) {
        if (!bookCategoryRepository.existsById(bookCategoryId)) {
            throw new IllegalStateException("Book category does not exists");
        }
        if (bookRepository.existsByBookCategoryId(bookCategoryId)) {
            throw new IllegalStateException("Failed to update/delete, data might have relation with other data.");
        }
        bookCategoryRepository.deleteById(bookCategoryId);
    }
}
