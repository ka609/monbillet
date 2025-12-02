package com.monbillet.monbillet.controller;

import com.monbillet.monbillet.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /** Page de login */
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    /** Page d'inscription */
    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    /** Traitement de l'inscription */
    @PostMapping("/register")
    public String processRegister(
            @RequestParam String nomComplet,
            @RequestParam String email,
            @RequestParam String telephone,
            @RequestParam String adresse,
            @RequestParam String username,
            @RequestParam String password,
            Model model
    ) {
        try {
            userService.registerUser(nomComplet, email, telephone, adresse, username, password);
            return "redirect:/login?success";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }
}
