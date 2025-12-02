package com.monbillet.monbillet.controller.admin;

import com.monbillet.monbillet.entity.Role;
import com.monbillet.monbillet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final UserService userService;

    /** Liste des utilisateurs */
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users/list";
    }

    /** Supprimer un utilisateur */
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    /** Donner le rôle ADMIN */
    @GetMapping("/make-admin/{id}")
    public String makeAdmin(@PathVariable Long id) {
        userService.updateRole(id, Role.ADMIN);
        return "redirect:/admin/users";
    }

    /** Donner le rôle USER */
    @GetMapping("/make-user/{id}")
    public String makeUser(@PathVariable Long id) {
        userService.updateRole(id, Role.USER);
        return "redirect:/admin/users";
    }
}

