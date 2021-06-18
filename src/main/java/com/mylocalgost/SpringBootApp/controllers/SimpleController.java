package com.mylocalgost.SpringBootApp.controllers;
import com.mylocalgost.SpringBootApp.models.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class SimpleController {


    @GetMapping("/admin")
    public String printAdminPage(ModelMap model) {
        return "admin";
    }

    @GetMapping("/user")
    public String printUser(@AuthenticationPrincipal User user, ModelMap model) {

       model.addAttribute("user", user);
        return "user";
    }
}
