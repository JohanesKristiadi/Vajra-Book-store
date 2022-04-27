package com.haibcaminiproject.springboot.controllers;

import com.haibcaminiproject.springboot.models.Author;
import com.haibcaminiproject.springboot.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("listAuthor", authorService.getAuthors());
        return "author.index";
    }

    @GetMapping("new")
    public String newAuthor(Model model) {
        Author author = new Author();
        model.addAttribute("author", author);
        return "author.new";
    }

    @PostMapping
    public String store(@ModelAttribute("author") Author author) {
        try {
            authorService.addNewAuthor(author);
        } catch (IllegalStateException e) {
            return "redirect:/author/new?error";
        }

        return "redirect:/author";
    }

    @GetMapping(path = "{authorId}")
    public String show(@PathVariable(value = "authorId") Long authorId, Model model) {
        Author author = authorService.getAuthor(authorId);
        model.addAttribute("author", author);
        return "author.show";
    }

    @PostMapping("update")
    public String update(@ModelAttribute("author") Author author) {
        try {
            authorService.updateAuthor(author.getId(), author.getName());
        } catch (IllegalStateException e) {
            return "redirect:/author/" + author.getId().toString() + "?error";
        }

        return "redirect:/author";
    }

    @GetMapping("delete/{authorId}")
    public String destroy(@PathVariable(value = "authorId") Long authorId) {
        //TODO: Handle when user type manually (throw to error page)
        //TODO: Handle when user try to delete author with books
        authorService.deleteAuthor(authorId);
        return "redirect:/author";
    }
}
