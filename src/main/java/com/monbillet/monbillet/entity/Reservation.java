package com.monbillet.monbillet.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantite; // nombre de tickets réservés

    /** Concert réservé */
    @ManyToOne
    @JoinColumn(name = "concert_id")
    private Concert concert;

    /** Utilisateur qui réserve (peut être null si pas connecté) */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
