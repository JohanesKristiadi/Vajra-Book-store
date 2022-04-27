package com.haibcaminiproject.springboot.controllers;

import com.haibcaminiproject.springboot.models.Book;
import com.haibcaminiproject.springboot.services.AuthorService;
import com.haibcaminiproject.springboot.services.BookCategoryService;
import com.haibcaminiproject.springboot.services.BookService;
import com.haibcaminiproject.springboot.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final BookCategoryService bookCategoryService;

    @Autowired
    public BookController(
            BookService bookService,
            AuthorService authorService,
            PublisherService publisherService,
            BookCategoryService bookCategoryService
    ) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
        this.bookCategoryService = bookCategoryService;
    }

    @GetMapping
    public String index(Model model, @RequestParam(value = "title", required = false) String title) {
        if (title == null || title.equals("")) {
            model.addAttribute("listBook", bookService.getBooks());
        } else {
            model.addAttribute("listBook", bookService.getBooksByTitle(title));
        }
        return "book.index";
    }

    @GetMapping("new")
    public String newBook(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        model.addAttribute("listAuthor", authorService.getAuthors());
        model.addAttribute("listPublisher", publisherService.getPublishers());
        model.addAttribute("listBookCategory", bookCategoryService.getBookCategories());
        return "book.new";
    }

    @PostMapping
    public String store(@ModelAttribute("book") Book book, @RequestParam("file")MultipartFile file) {
        try {
            bookService.addNewBook(book, file);
        } catch (IllegalStateException e) {
            return "redirect:/book/new?error";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/book";
    }

    @GetMapping(path = "{bookId}")
    public String show(@PathVariable(value = "bookId") Long bookId, Model model) {
        Book book = bookService.getBook(bookId);
        model.addAttribute("book", book);
        model.addAttribute("listAuthor", authorService.getAuthors());
        model.addAttribute("listPublisher", publisherService.getPublishers());
        model.addAttribute("listBookCategory", bookCategoryService.getBookCategories());
        return "book.show";
    }

    @PostMapping("update")
    public String update(@ModelAttribute("book") Book book, @RequestParam("file")MultipartFile file) {
        try {
            bookService.updateBook(book, file);
        } catch (IllegalStateException | IOException e) {
            return "redirect:/book/" + book.getId().toString() + "?error";
        }

        return "redirect:/book";
    }

    @GetMapping("delete/{bookId}")
    public String destroy(@PathVariable(value = "bookId") Long bookId) {
        //TODO: Handle when user type manually (throw to error page)
        bookService.deleteBook(bookId);
        return "redirect:/book";
    }
}
