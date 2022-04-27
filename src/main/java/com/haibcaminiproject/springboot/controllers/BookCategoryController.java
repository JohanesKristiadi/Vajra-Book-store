package com.haibcaminiproject.springboot.controllers;

import com.haibcaminiproject.springboot.models.BookCategory;
import com.haibcaminiproject.springboot.services.BookCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/book-category")
public class BookCategoryController {

    private final BookCategoryService bookCategoryService;

    @Autowired
    public BookCategoryController(BookCategoryService bookCategoryService) {
        this.bookCategoryService = bookCategoryService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("listBookCategory", bookCategoryService.getBookCategories());
        return "book-category.index";
    }

    @GetMapping("new")
    public String newBookCategory(Model model) {
        BookCategory bookCategory = new BookCategory();
        model.addAttribute("bookCategory", bookCategory);
        return "book-category.new";
    }

    @PostMapping
    public String store(@ModelAttribute("bookCategory") BookCategory bookCategory) {
        try {
            bookCategoryService.addNewBookCategory(bookCategory);
        } catch (IllegalStateException e) {
            return "redirect:/book-category/new?error";
        }

        return "redirect:/book-category";
    }

    @GetMapping(path = "{bookCategoryId}")
    public String show(@PathVariable(value = "bookCategoryId") Long bookCategoryId, Model model) {
        BookCategory bookCategory = bookCategoryService.getBookCategory(bookCategoryId);
        model.addAttribute("bookCategory", bookCategory);
        return "book-category.show";
    }

    @PostMapping("update")
    public String update(@ModelAttribute("bookCategory") BookCategory bookCategory) {
        try {
            bookCategoryService.updateBookCategory(bookCategory.getId(), bookCategory.getName());
        } catch (IllegalStateException e) {
            return "redirect:/book-category/" + bookCategory.getId().toString() + "?error";
        }

        return "redirect:/book-category";
    }

    @GetMapping("delete/{bookCategoryId}")
    public String destroy(@PathVariable(value = "bookCategoryId") Long bookCategoryId) {
        //TODO: Handle when user type manually (throw to error page)
        //TODO: Handle when user try to delete author with books
        bookCategoryService.deleteBookCategory(bookCategoryId);
        return "redirect:/book-category";
    }

}
