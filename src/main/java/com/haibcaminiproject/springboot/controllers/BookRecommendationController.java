package com.haibcaminiproject.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.haibcaminiproject.springboot.services.BookService;

@Controller
@RequestMapping("/book-recommendation")
public class BookRecommendationController {

    private final BookService bookService;

    @Autowired
    public BookRecommendationController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("listBook", bookService.getUnreadBooks());
        return "recommendation.index";
    }
}
