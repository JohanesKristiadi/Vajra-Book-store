package com.haibcaminiproject.springboot.services;

import com.haibcaminiproject.springboot.models.Author;
import com.haibcaminiproject.springboot.models.Book;
import com.haibcaminiproject.springboot.models.BookCategory;
import com.haibcaminiproject.springboot.models.Publisher;
import com.haibcaminiproject.springboot.repositories.AuthorRepository;
import com.haibcaminiproject.springboot.repositories.BookCategoryRepository;
import com.haibcaminiproject.springboot.repositories.BookRepository;
import com.haibcaminiproject.springboot.repositories.PublisherRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.Instant;
import java.util.Base64;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookCategoryRepository bookCategoryRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    @Autowired
    public BookService(
            BookRepository bookRepository,
            BookCategoryRepository bookCategoryRepository,
            AuthorRepository authorRepository,
            PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.bookCategoryRepository = bookCategoryRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitleLike("%" + title + "%");
    }

    public List<Book> getUnreadBooks() {
        Random rand = new Random();

        List<Book> unreadBooks = bookRepository.findByIsRead(false);
        if (unreadBooks.size() > 0) {
            int randomIndex = rand.nextInt(unreadBooks.size());
            Book randomBook = unreadBooks.get(randomIndex);
            return Arrays.asList(randomBook);
        }
        return unreadBooks;
    }

    public Book getBook(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new IllegalStateException("Book does not exists"));
    }

    public void addNewBook(Book book, @NotNull MultipartFile file) throws IOException {
        validateRequest(book);
        checkForeignKey(book);
        book.setCreatedAt(Instant.now());
        book.setUpdatedAt(Instant.now());

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (fileName.contains("..")) {
            System.out.println("Invalid File");
        }
        book.setImage(Base64.getEncoder().encodeToString(file.getBytes()));

        bookRepository.save(book);
    }

    @Transactional
    public void updateBook(Book book, @Nullable MultipartFile file) throws  IOException {
        validateRequest(book);
        checkForeignKey(book);
        book.setUpdatedAt(Instant.now());

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (fileName.contains("..")) {
            System.out.println("Invalid File");
        }
        book.setImage(Base64.getEncoder().encodeToString(file.getBytes()));

        bookRepository.save(book);
    }

    public void deleteBook(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new IllegalStateException("Book does not exists");
        }
        bookRepository.deleteById(bookId);
    }

    private void checkForeignKey(Book book) {
        Long bookCategoryId = book.getBookCategory().getId();
        Long authorId = book.getAuthor().getId();
        Long publisherId = book.getPublisher().getId();

        if (bookCategoryId != null) {
            BookCategory bookCategory = bookCategoryRepository.findById(bookCategoryId)
                    .orElseThrow(() -> new IllegalStateException("Book category does not exists"));
            book.setBookCategory(bookCategory);
        } else {
            book.setBookCategory(null);
        }

        if (authorId != null) {
            Author author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new IllegalStateException("Author does not exists"));
            book.setAuthor(author);
        } else {
            book.setAuthor(null);
        }

        if (publisherId != null) {
            Publisher publisher = publisherRepository.findById(publisherId)
                    .orElseThrow(() -> new IllegalStateException("Publisher does not exists"));
            book.setPublisher(publisher);
        } else {
            book.setPublisher(null);
        }
    }

    private void validateRequest(Book book) {
        if (book.getTitle() == null) {
            throw new IllegalStateException("Title is required");
        }
        if (book.getPrice() == null) {
            throw new IllegalStateException("Price is required");
        }
    }

}
