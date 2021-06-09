package com.mylocalgost.SpringBootApp.controllers;
import com.mylocalgost.SpringBootApp.models.User;
import com.mylocalgost.SpringBootApp.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class AdminsController {
    private final UserService userService;

    public AdminsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String printAdminPage(@AuthenticationPrincipal User user, ModelMap model) {
        model.addAttribute("u", user);
        model.addAttribute("users", userService.getAll());
        model.addAttribute("allRoles", userService.getAllRoles());
        model.addAttribute("newUser", new User());
        return "admin";
    }


    @GetMapping("/user")
    public String printUser(@AuthenticationPrincipal User user, ModelMap model) {
        model.addAttribute("user", user);
        return "user";
    }


    @PostMapping("/admin")
    public String create(@ModelAttribute("user") User user, ModelMap model, @RequestParam(value = "select_roles", required = false) String role) {
        user.setRoles(role);
        userService.add(user);
        return "redirect:admin";
    }


    @PatchMapping("/admin/users/{id}")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") long id,
                         @RequestParam(value = "select_roles", required = false) String role) {
        userService.update(id, user , role);
        return "redirect:/admin";

    }

    @DeleteMapping("/admin/users/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }


}
