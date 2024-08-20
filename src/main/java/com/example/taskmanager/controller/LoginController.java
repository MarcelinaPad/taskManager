package com.example.taskmanager.controller;

import com.example.taskmanager.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }



}




























//    @PostMapping("/login")
//    public String login (User user) {
//        return "redirect:/tasks";
//    }