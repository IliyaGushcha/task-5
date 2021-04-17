package com.project.task5.controllers;

import com.project.task5.models.Users;
import com.project.task5.rep.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Date;

@Controller
public class MainController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/signup")
    public String signup(Model model) {
        return "signup";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @PostMapping("/signup")
    public String signupPost(@RequestParam String name, @RequestParam String email, @RequestParam String password, Model model) {
        if(!usersRepository.existsByEmail(email)) {
            Users user = new Users(name, email, password);
            usersRepository.save(user);
            model.addAttribute("users", usersRepository.findAll());
            model.addAttribute("id", user.getId());
            return "redirect:spreadsheet/" + user.getId();
        } else
            return "signup";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam String email, @RequestParam String password, Model model) {
        if(usersRepository.existsByEmail(email) && usersRepository.findByEmail(email).getStatus().equals("blocked")) {
            model.addAttribute("error", "You are blocked!");
            return "login";
        }else if(usersRepository.existsByEmail(email) && usersRepository.existsByPassword(password)) {
            Users user = usersRepository.findByEmail(email);
            user.setSignin(new Date());
            usersRepository.save(user);
            model.addAttribute("users", usersRepository.findAll());
            model.addAttribute("id", user.getId());
            return "redirect:spreadsheet/" + user.getId();
        }
        model.addAttribute("error", "Incorrect data!");
        return "login";
    }
}
