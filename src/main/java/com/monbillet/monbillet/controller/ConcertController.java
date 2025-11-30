package com.monbillet.monbillet.controller;

import com.monbillet.monbillet.entity.Concert;
import com.monbillet.monbillet.service.ConcertService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Controller
@RequestMapping("/concerts")
public class ConcertController {

    private final ConcertService concertService;

    public ConcertController(ConcertService concertService) {
        this.concertService = concertService;
    }

    @GetMapping
    public String listConcerts(Model model) {
        model.addAttribute("concerts", concertService.findAll());
        return "concert/list";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("concert", concertService.findById(id));
        return "concert/details";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("concert", new Concert());
        model.addAttribute("action", "/concerts");
        model.addAttribute("method", "post");
        model.addAttribute("title", "Ajouter un concert");
        model.addAttribute("subtitle", "Remplissez les informations");
        model.addAttribute("buttonText", "Ajouter");
        return "concert/form";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Concert concert = concertService.findById(id);
        model.addAttribute("concert", concert);
        model.addAttribute("action", "/concerts/" + id);
        model.addAttribute("method", "put");
        model.addAttribute("title", "Modifier le concert");
        model.addAttribute("subtitle", "Mettez à jour les informations");
        model.addAttribute("buttonText", "Mettre à jour");
        return "concert/form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public String save(@ModelAttribute Concert concert,
                       @RequestParam("afficheFile") MultipartFile afficheFile) throws IOException {

        if (!afficheFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + afficheFile.getOriginalFilename();
            Path uploadPath = Paths.get("src/main/resources/static/uploads");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(afficheFile.getInputStream(),
                    uploadPath.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);

            concert.setAfficheUrl("/uploads/" + fileName);
        }

        concertService.save(concert);
        return "redirect:/concerts";
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute Concert concert,
                         @RequestParam("afficheFile") MultipartFile afficheFile) throws IOException {

        Concert existing = concertService.findById(id);

        existing.setTitre(concert.getTitre());
        existing.setDescription(concert.getDescription());
        existing.setDateHeure(concert.getDateHeure());
        existing.setLieu(concert.getLieu());
        existing.setPrix(concert.getPrix());
        existing.setPlacesDisponibles(concert.getPlacesDisponibles());

        if (!afficheFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + afficheFile.getOriginalFilename();
            Path uploadPath = Paths.get("src/main/resources/static/uploads");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(afficheFile.getInputStream(),
                    uploadPath.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);

            existing.setAfficheUrl("/uploads/" + fileName);
        }

        concertService.save(existing);
        return "redirect:/concerts";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        concertService.deleteById(id);
        return "redirect:/concerts";
    }
}
