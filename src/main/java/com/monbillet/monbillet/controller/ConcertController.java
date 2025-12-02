package com.monbillet.monbillet.controller;

import com.monbillet.monbillet.entity.Concert;
import com.monbillet.monbillet.service.ConcertService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/concerts")
public class ConcertController {

    private final ConcertService concertService;

    public ConcertController(ConcertService concertService) {
        this.concertService = concertService;
    }

    /** Liste des concerts pour les utilisateurs */
    @GetMapping
    public String listConcerts(Model model) {
        model.addAttribute("concerts", concertService.findAll());
        return "concert/list";
    }

    /** Détails d'un concert */
    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("concert", concertService.findById(id));
        return "concert/details";
    }
}
