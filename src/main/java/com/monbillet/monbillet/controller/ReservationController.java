package com.monbillet.monbillet.controller;

import com.monbillet.monbillet.entity.Concert;
import com.monbillet.monbillet.entity.Reservation;
import com.monbillet.monbillet.entity.User;
import com.monbillet.monbillet.service.ConcertService;
import com.monbillet.monbillet.service.ReservationService;
import com.monbillet.monbillet.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ConcertService concertService;
    private final UserService userService;

    public ReservationController(ReservationService reservationService,
                                 ConcertService concertService,
                                 UserService userService) {
        this.reservationService = reservationService;
        this.concertService = concertService;
        this.userService = userService;
    }

    /** Créer une réservation pour un concert */
    @PostMapping("/create/{concertId}")
    public String reserve(
            @PathVariable Long concertId,
            @RequestParam Integer quantite,
            Authentication auth
    ) {
        if (quantite == null || quantite <= 0) {
            return "redirect:/concerts/" + concertId + "?error=quantite";
        }

        Concert concert = concertService.findById(concertId);

        Reservation reservation = new Reservation();
        reservation.setConcert(concert);
        reservation.setQuantite(quantite);

        // Associer l'utilisateur connecté
        if (auth != null && auth.isAuthenticated()) {
            String username = auth.getName();
            User user = userService.findByUsername(username);
            reservation.setUser(user);
        }

        reservationService.saveReservation(reservation);
        return "redirect:/concerts/" + concertId + "?reserved=true";
    }
}
