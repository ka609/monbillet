package com.monbillet.monbillet.controller.admin;

import com.monbillet.monbillet.entity.Concert;
import com.monbillet.monbillet.service.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/concerts")
@PreAuthorize("hasRole('ADMIN')")
public class AdminConcertController {

    private final ConcertService concertService;

    private final String UPLOAD_DIR = "src/main/resources/static/uploads";

    /** Liste des concerts pour l'admin */
    @GetMapping
    public String listConcerts(Model model) {
        model.addAttribute("concerts", concertService.findAll());
        return "admin/concerts/list";
    }

    /** Formulaire pour créer un nouveau concert */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("concert", new Concert());
        model.addAttribute("action", "/admin/concerts/save");
        model.addAttribute("method", "post");
        model.addAttribute("title", "Ajouter un concert");
        model.addAttribute("buttonText", "Ajouter");
        return "admin/concerts/form";
    }

    /** Formulaire pour modifier un concert existant */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Concert concert = concertService.findById(id);
        model.addAttribute("concert", concert);
        model.addAttribute("action", "/admin/concerts/update/" + id);
        model.addAttribute("method", "post"); // HTML form -> POST
        model.addAttribute("title", "Modifier le concert");
        model.addAttribute("buttonText", "Mettre à jour");
        return "admin/concerts/form";
    }

    /** Enregistrement d'un nouveau concert */
    @PostMapping("/save")
    public String saveConcert(@ModelAttribute Concert concert,
                              @RequestParam("afficheFile") MultipartFile afficheFile) throws IOException {
        handleFileUpload(concert, afficheFile);
        concertService.saveConcert(concert);
        return "redirect:/admin/concerts";
    }

    /** Mise à jour d'un concert existant */
    @PostMapping("/update/{id}")
    public String updateConcert(@PathVariable Long id,
                                @ModelAttribute Concert concert,
                                @RequestParam("afficheFile") MultipartFile afficheFile) throws IOException {

        Concert existing = concertService.findById(id);

        existing.setTitre(concert.getTitre());
        existing.setDescription(concert.getDescription());
        existing.setDateHeure(concert.getDateHeure());
        existing.setLieu(concert.getLieu());
        existing.setPrix(concert.getPrix());
        existing.setPlacesDisponibles(concert.getPlacesDisponibles());

        handleFileUpload(existing, afficheFile);

        concertService.saveConcert(existing);
        return "redirect:/admin/concerts";
    }

    /** Supprimer un concert */
    @GetMapping("/delete/{id}")
    public String deleteConcert(@PathVariable Long id) {
        concertService.deleteConcert(id);
        return "redirect:/admin/concerts";
    }

    /** Méthode privée pour gérer l'upload des affiches */
    private void handleFileUpload(Concert concert, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(file.getInputStream(),
                    uploadPath.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);

            concert.setAfficheUrl("/uploads/" + fileName);
        }
    }
}
