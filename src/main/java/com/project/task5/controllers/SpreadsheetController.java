package com.project.task5.controllers;

import com.project.task5.models.Users;
import com.project.task5.rep.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class SpreadsheetController {
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/spreadsheet/{id}")
    public String main(@PathVariable(value = "id") long id, Model model) {
        if(usersRepository.findById(id).get().getStatus().equals("unlocked")) {
            Iterable<Users> users = usersRepository.findAll();
            model.addAttribute("id", id);
            model.addAttribute("users", users);
            return "spreadsheet";
        } else
            return "redirect:/signin";
    }

    @PostMapping("/spreadsheet/{id}")
    public String main(@PathVariable(value = "id") long id,
                       @RequestParam(value = "user", required = false) List<String> checkboxId,
                       @RequestParam("button") String buttonId, Model model) {
        if(checkboxId == null) {
            model.addAttribute("users", usersRepository.findAll());
            return "spreadsheet";
        }
        if(usersRepository.findById(id).get().getStatus().equals("unlocked")) {
            if (buttonId.equals("delete")) {
                for (String email : checkboxId) {
                    Users user = usersRepository.findByEmail(email);
                    usersRepository.delete(user);
                }
            } else if (buttonId.equals("block")) {
                for (String email : checkboxId) {
                    Users user = usersRepository.findByEmail(email);
                    user.setStatus("blocked");
                    usersRepository.save(user);
                }
            } else if (buttonId.equals("unlock")) {
                for (String email : checkboxId) {
                    Users user = usersRepository.findByEmail(email);
                    user.setStatus("unlocked");
                    usersRepository.save(user);
                }
            }
            Iterable<Users> users = usersRepository.findAll();
            model.addAttribute("users", users);
            return usersRepository.findById(id).get().getStatus().equals("blocked")||
                    !usersRepository.existsById(id)?"redirect:/signin":"spreadsheet";
        } else return "redirect:/signin";
    }



}
