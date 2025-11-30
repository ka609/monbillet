package com.monbillet.monbillet.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDateTime dateHeure;

    @Column(nullable = false)
    private String lieu;

    @Column(nullable = false)
    private Double prix;

    @Column(nullable = false)
    private Integer placesDisponibles;

    /**
     * URL complète accessible par le navigateur, ex: /uploads/image123.png
     */
    @Column(name = "affiche_url", length = 500)
    private String afficheUrl;

    /**
     * Relation avec les réservations
     */
    @OneToMany(mappedBy = "concert", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;
}
