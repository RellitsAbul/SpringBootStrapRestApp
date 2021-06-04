package com.mylocalgost.SpringBootApp.controllers;

import com.mylocalgost.SpringBootApp.models.User;
import com.mylocalgost.SpringBootApp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminsController {
    private final UserService userService;

    public AdminsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String printAdminPage(ModelMap model) {
        return "admin";
    }

    @GetMapping("/users")
    public String printUsers(ModelMap model) {
        model.addAttribute("users", userService.getAll());
        return "/users";
    }

    @GetMapping("/users/{id}")
    public String printUser(@PathVariable("id") long id, ModelMap model) {
        model.addAttribute("user", userService.getById(id));
        return "/user";
    }

    @GetMapping("/new")
    public String newUser(ModelMap model) {
        model.addAttribute("role", "ROLE_ADMIN");
        model.addAttribute("user", new User());
        return "/new";
    }

    @PostMapping
    public String create(@ModelAttribute("user") User user, ModelMap model, @RequestParam(value = "role", required = false) String role) {
        user.setRoles(role);
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Password mismatch");
            return "/new";
        }
        if (!userService.add(user)) {
            model.addAttribute("usernameError", "A user with the same name already exists");
            return "/new";
        }
        return "redirect:admin/users";
    }

    @GetMapping("/users/{id}/edit")
    public String editUser(@PathVariable("id") long id, ModelMap model) {
        model.addAttribute("user", userService.getById(id));
        return "/edit";
    }

    @PatchMapping("/users/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userService.update(id, user);
        return "redirect:/admin/users";

    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }


}
