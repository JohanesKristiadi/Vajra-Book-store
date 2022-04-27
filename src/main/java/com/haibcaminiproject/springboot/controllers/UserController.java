package com.haibcaminiproject.springboot.controllers;

import com.haibcaminiproject.springboot.models.User;
import com.haibcaminiproject.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("listUser", userService.getUsers());
        return "user.index";
    }

    @GetMapping("new")
    public String newUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user.new";
    }

    @PostMapping
    public String store(@ModelAttribute("user") User user) {
        try {
            userService.addNewUser(user);
        } catch (IllegalStateException e) {
            return "redirect:/user/new?error";
        }

        return "redirect:/user";
    }

    @GetMapping(path = "{userId}")
    public String show(@PathVariable(value = "userId") Long userId, Model model) {
        User user = userService.getUser(userId);
        model.addAttribute("user", user);
        return "user.show";
    }

    @PostMapping("update")
    public String update(@ModelAttribute("user") User user) {
        try {
            userService.updateUser(user.getId(), user.getName(), user.getEmail());
        } catch (IllegalStateException e) {
            return "redirect:/user/" + user.getId().toString() + "?error";
        }

        return "redirect:/user";
    }

    @GetMapping("delete/{userId}")
    public String destroy(@PathVariable(value = "userId") Long userId) {
        //TODO: Handle when user type manually (throw to error page)
        userService.deleteUser(userId);
        return "redirect:/user";
    }
}
