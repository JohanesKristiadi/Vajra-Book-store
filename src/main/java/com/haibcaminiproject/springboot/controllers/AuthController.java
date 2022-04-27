package com.haibcaminiproject.springboot.controllers;

import com.haibcaminiproject.springboot.models.User;
import com.haibcaminiproject.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Map<String, String> currentUserName(Principal principal) {
        HashMap<String, String> map = new HashMap<>();
        User user = userService.getUserByEmail(principal.getName());
        map.put("name", user.getName());
        return map;
    }
}
