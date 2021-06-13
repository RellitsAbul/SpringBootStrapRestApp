package com.mylocalgost.SpringBootApp.controllers;


import com.mylocalgost.SpringBootApp.models.User;
import com.mylocalgost.SpringBootApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class ControllerRest {

    @Autowired
    private UserService userService;

    @GetMapping("/allusers")
    public List<User> list() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @PostMapping("/newUser")
    public User addUserBd(@RequestBody User user) {
        User user1 = user;
        userService.add(user1);
        return user1;
    }

    @PutMapping("/edit")
    public User update(@RequestBody User user) {
        userService.update(user);
        return user;
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        userService.delete(id);
    }
}
