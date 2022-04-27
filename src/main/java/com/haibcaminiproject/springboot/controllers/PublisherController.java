package com.haibcaminiproject.springboot.controllers;

import com.haibcaminiproject.springboot.models.Publisher;
import com.haibcaminiproject.springboot.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/publisher")
public class PublisherController {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("listPublisher", publisherService.getPublishers());
        return "publisher.index";
    }

    @GetMapping("new")
    public String newPublisher(Model model) {
        Publisher publisher = new Publisher();
        model.addAttribute("publisher", publisher);
        return "publisher.new";
    }

    @PostMapping
    public String store(@ModelAttribute("publisher") Publisher publisher) {
        try {
            publisherService.addNewPublisher(publisher);
        } catch (IllegalStateException e) {
            return "redirect:/publisher/new?error";
        }

        return "redirect:/publisher";
    }

    @GetMapping(path = "{publisherId}")
    public String show(@PathVariable(value = "publisherId") Long publisherId, Model model) {
        Publisher publisher = publisherService.getPublisher(publisherId);
        model.addAttribute("publisher", publisher);
        return "publisher.show";
    }

    @PostMapping("update")
    public String update(@ModelAttribute("publisher") Publisher publisher) {
        try {
            publisherService.updatePublisher(publisher.getId(), publisher.getName());
        } catch (IllegalStateException e) {
            return "redirect:/publisher/" + publisher.getId().toString() + "?error";
        }

        return "redirect:/publisher";
    }

    @GetMapping("delete/{publisherId}")
    public String destroy(@PathVariable(value = "publisherId") Long publisherId) {
        //TODO: Handle when user type manually (throw to error page)
        //TODO: Handle when user try to delete publisher with books
        publisherService.deletePublisher(publisherId);
        return "redirect:/publisher";
    }
}
