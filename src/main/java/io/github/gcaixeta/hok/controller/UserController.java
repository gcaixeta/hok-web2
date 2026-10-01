package io.github.gcaixeta.hok.controller;

import io.github.gcaixeta.hok.model.User;
import io.github.gcaixeta.hok.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register() {
        return "user/registerUser";
    }

    @GetMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, Model model) {
        Integer userId = userService.saveUser(user);
        String message = "User saved sucessfully: " + userId;
        model.addAttribute("msg", message);
        return "user/registerUser";
    }

    @GetMapping("accessDenied")
    public String accessDenied() {
        return "user/accessDenied";
    }
    
}